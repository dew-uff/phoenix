package br.uff.ic.gems.phoenix.similarity;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import br.uff.ic.gems.phoenix.SettingsHelper;
import br.uff.ic.gems.phoenix.diff.AttributeDiffNode;
import br.uff.ic.gems.phoenix.diff.CommonAttributeDiffNode;
import br.uff.ic.gems.phoenix.diff.CommonElementDiffNode;
import br.uff.ic.gems.phoenix.diff.DiffNode;
import br.uff.ic.gems.phoenix.diff.ElementDiffNode;
import br.uff.ic.gems.phoenix.diff.OneSideAttributeDiffNode;
import br.uff.ic.gems.phoenix.diff.OneSideElementDiffNode;
import br.uff.ic.gems.phoenix.exception.ComparisonException;

public class ElementSimilarity {
    
    private static Logger LOG = Logger.getLogger(ElementSimilarity.class.getName());

    private static float MAXIMUM_SIMILARITY = 1.0f;
    private static float SKIP_SIMILARITY = -1.0f;

    private double similarity = 0.0;
    
    private AttributeDiffNode[] attributes;
    private ElementDiffNode[] children;
    
    public ElementSimilarity() {
    }
    
    public ElementSimilarityResult compare(Element e1, Element e2) throws ComparisonException {
        
        if (e1 == null || e2 == null) {
            throw new ComparisonException("At least of the elements is null");
        }
        
        LOG.finer("Comparing elements: " + e1.getNodeName() + " <> " + e2.getNodeName());

        similarity = 0.0;
        
        double elementNameSimilarity = 0.0,
               attributeSimilarity = 0.0,
               valueSimilarity = 0.0,
               childrenSimilarity = 0.0;

        elementNameSimilarity = elementsNameSimilarity(e1, e2);

        // skip means the elements names are equal.
        // it is set with this value to later decide whether this component should
        // be considered or not, depending on the ignoreTrivial parameter.
        if (elementNameSimilarity == SKIP_SIMILARITY) {

            childrenSimilarity = elementsChildrenSimilarity(e1, e2);
            
            attributeSimilarity = elementsAttributesSimilarity(e1, e2);

            valueSimilarity = elementsValueSimilarity(e1, e2);

            similarity = calculateSimilarity(elementNameSimilarity,
                    attributeSimilarity, valueSimilarity, childrenSimilarity);
        }
          
        // round number to deal with floating point precision
        NumberFormat df = DecimalFormat.getInstance(Locale.ENGLISH);
        df.setMaximumFractionDigits(3);
        similarity = Double.parseDouble(df.format(similarity));
        
        return constructResult(e1,e2);
    } 

    private ElementSimilarityResult constructResult(Node e1, Node e2) {
        
        double threshold = SettingsHelper.getSimilarityThreshold();
        
        ElementSimilarityResult esr = null;
        
        if (similarity < threshold) {
            // elements are dissimilar
            OneSideElementDiffNode left = new OneSideElementDiffNode(e1,DiffNode.SIDE_LEFT);
            OneSideElementDiffNode right = new OneSideElementDiffNode(e2, DiffNode.SIDE_RIGHT);
            
            esr = new ElementSimilarityResult(left, right, similarity);
            
            children = null;
            attributes = null;
        }
        else {
            CommonElementDiffNode diff = new CommonElementDiffNode(e1, e2);
            diff.addAttributes(attributes);
            diff.addChildren(children);
            diff.setSimilarity(similarity);
            esr = new ElementSimilarityResult(diff, similarity);
        }
        
        return esr;
    }

    private static double elementsNameSimilarity(Element e1, Element e2) throws ComparisonException {
        
        String name1 = e1.getNodeName();
        String name2 = e2.getNodeName();
        
        return (name1.equals(name2))?SKIP_SIMILARITY:0.0;
    }
    
    private static double elementsValueSimilarity(Element e1, Element e2) throws ComparisonException {
        
        String value1 = e1.hasChildNodes()?e1.getFirstChild().getNodeValue():null;
        String value2 = e2.hasChildNodes()?e2.getFirstChild().getNodeValue():null;     
        
        if (value1 == null && value2 == null) {
            return SKIP_SIMILARITY;
        } 

        if (value1 != null && value2 != null) {
            return LcsSimilarity.calculateStringSimilarity(value1, value2);
        }

        return 0.0;
    }

    private double elementsAttributesSimilarity(Element e1, Element e2) throws ComparisonException {
        
        // if both don't have attributes, this shouldn't be considered
        // at similarity calculus
        if (!e1.hasAttributes() && !e2.hasAttributes()) {
            return SKIP_SIMILARITY;
        } 
        
        float similarity = 0;

        NamedNodeMap attrsLeft = e1.getAttributes();
        NamedNodeMap attrsRight = e2.getAttributes();

        // get all attributes names, from both nodes
        List<String> allAttr = getAllAttributesNames(attrsLeft, attrsRight);        

        attributes = new AttributeDiffNode[allAttr.size()];
        
        // go through all attributes and for each one, check if it's 
        // present in one or both nodes, and if value is the same.
        for (int i=0; i < attributes.length; i++) {
        
            String attributeName = allAttr.get(i);
                    
            Node attributeLeft = (attrsLeft != null)?attrsLeft.getNamedItem(attributeName):null;
            Node attributeRight = (attrsRight != null)?attrsRight.getNamedItem(attributeName):null;
                    
            String attributeLeftValue = (attributeLeft != null)?attributeLeft.getNodeValue():"";
            String attributeRightValue = (attributeRight != null)?attributeRight.getNodeValue():"";
            
            // we only compare if attribute exist in both sides. if not, similarity is zero for that attribute
            if (!attributeLeftValue.isEmpty() && !attributeRightValue.isEmpty()) {
                
                attributes[i] = new CommonAttributeDiffNode(attributeLeft, attributeRight);

                // if both values are equal
                if (attributeLeftValue.equals(attributeRightValue)) {
                    similarity += MAXIMUM_SIMILARITY;
                } else {
                    // calculate similarity based on LCS between values
                    similarity += LcsSimilarity.calculateStringSimilarity(attributeLeftValue, attributeRightValue);
                }
            }
            else {
                if (!attributeLeftValue.isEmpty()) {
                    attributes[i] = new OneSideAttributeDiffNode(attributeLeft, DiffNode.SIDE_LEFT);
                } else {
                    attributes[i] = new OneSideAttributeDiffNode(attributeRight, DiffNode.SIDE_RIGHT);
                }
            }
        }

        // each attribute contributes equally to the 
        // similarity accounting
        similarity = similarity / allAttr.size();
        
        return similarity;
    }

    private double elementsChildrenSimilarity(Element e1, Element e2) throws ComparisonException {
        
        // both nodes do not have children nodes
        if (!e1.hasChildNodes() && !e2.hasChildNodes()) {
            return SKIP_SIMILARITY;
        }

        List<Node> leftSubElements = getElementNodes(e1.getChildNodes());
        List<Node> rightSubElements = getElementNodes(e2.getChildNodes());

        if ((leftSubElements.size() == 0 && rightSubElements.size() == 0)) {
            return SKIP_SIMILARITY;
        }
        
        double similarity = 0;

        if ((leftSubElements.size() != 0 && rightSubElements.size() != 0)) {
                
            HungarianSimilarity hs = new HungarianSimilarity(leftSubElements, rightSubElements);
                
            similarity = hs.calculateSimilarity();
            
            children = hs.getResult();

        } else {
            if (leftSubElements.size() != 0) {
                children = new ElementDiffNode[leftSubElements.size()];
                for (int i=0; i < leftSubElements.size(); i++) {
                    children[i] = new OneSideElementDiffNode(leftSubElements.get(i), DiffNode.SIDE_LEFT);
                }
            } else {
                children = new ElementDiffNode[rightSubElements.size()];
                for (int i=0; i < rightSubElements.size(); i++) {
                    children[i] = new OneSideElementDiffNode(rightSubElements.get(i), DiffNode.SIDE_RIGHT);
                }
            }
        }
        return similarity;
    }
    
    public static List<Node> getElementNodes(NodeList nodeList) {
        List<Node> elementNodes = new ArrayList<Node>();
        
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node item = nodeList.item(i);

            if (item.getNodeType() == Node.ELEMENT_NODE) {
                elementNodes.add(item);
            }
        }

        return elementNodes;
    }

    private double calculateSimilarity(double elementNameSimilarity,
            double attributeSimilarity, double valueSimilarity,
            double childrenSimilarity) {

        double similarity = 0.0;

        boolean ignoreTrivial = SettingsHelper.getIgnoreTrivialSimilarities();

        double nameWeight = 1;
        double valueWeight = 1;
        double attributeWeight = 1;
        double childrenWeight = 1;

        if (!SettingsHelper.getAutomaticWeightAllocation()) {
            nameWeight = SettingsHelper.getNameSimilarityWeight();
            valueWeight = SettingsHelper.getValueSimilarityWeight();
            attributeWeight = SettingsHelper.getAttributeSimilarityWeight();
            childrenWeight = SettingsHelper.getChildrenSimilarityWeight();
        }
        
        if (elementNameSimilarity == SKIP_SIMILARITY) {
            if (ignoreTrivial) {
                nameWeight = 0;
            }
            else {
                elementNameSimilarity = MAXIMUM_SIMILARITY;
            }
        }
            
        if (attributeSimilarity == SKIP_SIMILARITY) {
            if (ignoreTrivial) {
                attributeWeight = 0;
            }
            else {
                attributeSimilarity = MAXIMUM_SIMILARITY;
            }
        }

        if (valueSimilarity == SKIP_SIMILARITY) {
            if (ignoreTrivial) {
                valueWeight = 0;
            }
            else {
                valueSimilarity = MAXIMUM_SIMILARITY;
            }
        }

        if (childrenSimilarity == SKIP_SIMILARITY) {
            if (ignoreTrivial) {
                childrenWeight = 0;
            }
            else {
                childrenSimilarity = MAXIMUM_SIMILARITY;
            }
        }

        double totalWeight = nameWeight + attributeWeight + valueWeight + childrenWeight;
        // if all components are to be skipped, they are equal.
        if (totalWeight == 0) {
            similarity = MAXIMUM_SIMILARITY;
        }
        else {
            similarity = 
                     (elementNameSimilarity*nameWeight +
                      attributeSimilarity*attributeWeight +
                      valueSimilarity*valueWeight + 
                    + childrenSimilarity*childrenWeight)/totalWeight;
        }

        return similarity;
    }
    
    protected static List<String> getAllAttributesNames(NamedNodeMap left, 
            NamedNodeMap right) {

        List<String> attributes = new ArrayList<String>();

        if (left != null) {
            for (int i = 0; i < left.getLength(); i++) {
                Node item = left.item(i);
                attributes.add(item.getNodeName());
            }
        }
        if (right != null) {
            for (int i = 0; i < right.getLength(); i++) {
                Node item = right.item(i);
                if (!attributes.contains(item.getNodeName())) {
                    attributes.add(item.getNodeName());
                }
            }
        }
        
        return attributes;
    }
}

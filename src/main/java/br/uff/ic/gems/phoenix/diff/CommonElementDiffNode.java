package br.uff.ic.gems.phoenix.diff;

import br.uff.ic.gems.phoenix.similarity.ElementSimilarity;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class CommonElementDiffNode extends ElementDiffNode{

    private Node left, right;
    
    private AttributeDiffNode[] attributes = null;
    private ElementDiffNode[] children = null;
    
    protected double valueSimilarity = 0.0;
    
    public void setValueSimilarity(double similarity) {
        this.valueSimilarity = similarity;
    }
    
    public double getValueSimilarity() {
        return valueSimilarity;
    }
    
    public CommonElementDiffNode(Node left, Node right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public int getType() {
        return DiffNode.TYPE_COMMONELEMENT;
    }

    public void addAttributes(AttributeDiffNode[] attributes) {
        this.attributes = attributes;
    }

    public void addChildren(ElementDiffNode[] children) {
        this.children = children;
    }

    @Override
    public Element toXmlTree(Document doc) {

        Element newElem = null;

        // handle name
        // TODO optimize this. we should not compare again to construct result
        String nameLeft = left.getNodeName();
        String nameRight = right.getNodeName();        
        if (nameLeft.equals(nameRight)) {
            newElem = doc.createElement(nameLeft);
        } else {
            newElem = doc.createElement(DIFF_ELEMENT);
            newElem.setAttributeNS(DIFF_NAMESPACE, DIFF_LEFT, nameLeft);
            newElem.setAttributeNS(DIFF_NAMESPACE, DIFF_RIGHT, nameRight);
        }
        
        // add similarity attribute
        newElem.setAttributeNS(DIFF_NAMESPACE, DIFF_SIMILARITY, Double.toString(getSimilarity()));
        
        // handle attributes
        if (attributes != null) {
            for (AttributeDiffNode diff : attributes) {
                diff.linkToElement(doc,newElem);
            }
        }
        
        // handle value
        // TODO optimize this. we should not compare again to construct result
        String valueLeft = left.hasChildNodes()?left.getFirstChild().getNodeValue():null;
        String valueRight = right.hasChildNodes()?right.getFirstChild().getNodeValue():null;
        if (ElementSimilarity.MAXIMUM_SIMILARITY == getValueSimilarity()) {
            newElem.setTextContent(valueLeft);
        }
        else if (valueLeft != null || valueRight != null) {
            Element valueElem = doc.createElement(DIFF_VALUE);
            if (valueLeft != null) {
                valueElem.setAttributeNS(DIFF_NAMESPACE, DIFF_LEFT, valueLeft);
            }
            if (valueRight != null) {
                valueElem.setAttributeNS(DIFF_NAMESPACE, DIFF_RIGHT, valueRight);
            }
            newElem.appendChild(valueElem);
        }
        
        // handle children
        if (children != null) {
            for (ElementDiffNode diff : children) {
                Element c = diff.toXmlTree(doc);
                newElem.appendChild(c);
            }
        }
        
        return newElem;
    }
}

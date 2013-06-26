package gems.ic.uff.br.modelo.similar;

import com.sun.org.apache.xerces.internal.dom.DocumentImpl;
import gems.ic.uff.br.modelo.Diff;
import gems.ic.uff.br.modelo.ThreeWayDiff;
import gems.ic.uff.br.modelo.DiffXML;
import gems.ic.uff.br.modelo.HungarianList;
import gems.ic.uff.br.modelo.LcsString;
import gems.ic.uff.br.settings.SettingsHelper;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sun.org.apache.xpath.internal.NodeSet;

import org.w3c.dom.*;

/**
 * Classe que encapsula a classe Node do DOM, adicionando um comportamento
 * de similaridade a ela.
 */
public class SimilarNode extends Similar<SimilarNode> {
    
    public static float MAXIMUM_SIMILARITY = 1.0f;
    private static float SKIP_SIMILARITY = -1.0f;

    //elemento do lado esquerdo do documento
    private Node leftNode;
    
    public SimilarNode(Node node) {
        this.leftNode = node;
    }

    @Override
    public Diff similar(SimilarNode y) {

        Node rightNode = y.getNode();

        Diff diff = new Diff(leftNode, rightNode);

        float similarity = 0.0f;

        if (leftNode != null && rightNode != null) {

            float elementNameSimilarity = 0.0f, 
                  attributeSimilarity = 0.0f, 
                  valueSimilarity = 0.0f, 
                  childrenSimilarity = 0.0f;

            elementNameSimilarity = elementsNameSimilarity(rightNode);

            boolean nameSimilarityRequired = SettingsHelper
                    .getNameSimilarityRequired();

            // only continue if necessary
            if ((nameSimilarityRequired && elementNameSimilarity == MAXIMUM_SIMILARITY) 
                    || (!nameSimilarityRequired)) {

                attributeSimilarity = elementsAttributesSimilarity(rightNode,
                        diff);

                valueSimilarity = elementsValueSimilarity(rightNode, diff);

                childrenSimilarity = elementsChildrenSimilarity(rightNode, diff);

                similarity = calculateSimilarity(elementNameSimilarity,
                        attributeSimilarity, valueSimilarity, childrenSimilarity);
            }
            else {
                /*
                 * Se chegou nesta condição, então o elemento da esquerda é
                 * diferente do elemento da direita. Logo, esta informação deve ser
                 * exposta para usuário. Este método trata esta requisição.
                 */
                diff = varreTodosSubelementosParaMostrar(diff, leftNode, "left");
            }
        } 

        // round number to deal with floating point precision
        NumberFormat df = DecimalFormat.getInstance(Locale.ENGLISH);
        df.setMaximumFractionDigits(3);
        similarity = Float.parseFloat(df.format(similarity));
        
        diff.setSimilarity(similarity);
        return diff;
    }
    
    private Diff similar(Node rightNode) 
    {

        Diff diff = new Diff(leftNode, rightNode);

        float similarity = 0.0f;

        if (leftNode != null && rightNode != null) {

            float elementNameSimilarity = 0.0f, 
                  attributeSimilarity = 0.0f, 
                  valueSimilarity = 0.0f, 
                  childrenSimilarity = 0.0f;

            elementNameSimilarity = elementsNameSimilarity(rightNode);

            boolean nameSimilarityRequired = SettingsHelper
                    .getNameSimilarityRequired();

            // only continue if necessary
            if ((nameSimilarityRequired && elementNameSimilarity == MAXIMUM_SIMILARITY) 
                    || (!nameSimilarityRequired)) {

                attributeSimilarity = elementsAttributesSimilarity(rightNode,
                        diff);

                valueSimilarity = elementsValueSimilarity(rightNode, diff);

                childrenSimilarity = elementsChildrenSimilarity(rightNode, diff);

                similarity = calculateSimilarity(elementNameSimilarity,
                        attributeSimilarity, valueSimilarity, childrenSimilarity);
            }
            else {
                /*
                 * Se chegou nesta condição, então o elemento da esquerda é
                 * diferente do elemento da direita. Logo, esta informação deve ser
                 * exposta para usuário. Este método trata esta requisição.
                 */
                diff = varreTodosSubelementosParaMostrar(diff, leftNode, "left");
            }
        } 

        // round number to deal with floating point precision
        NumberFormat df = DecimalFormat.getInstance(Locale.ENGLISH);
        df.setMaximumFractionDigits(3);
        similarity = Float.parseFloat(df.format(similarity));
        
        diff.setSimilarity(similarity);
        return diff;
    }
    
    
    public ThreeWayDiff similarAncestral(SimilarNode son1,SimilarNode son2) {

        Node son1Node = son1.getNode();
        Node son2Node = son2.getNode();

        //Diff diff = new Diff(leftNode, son1Node);
        ThreeWayDiff diff = new ThreeWayDiff(leftNode, son1Node, son2Node);

        float similaritySon1 = 0.0f;
        float similaritySon2 = 0.0f;

        if (leftNode != null && son1Node != null && son2Node != null) 
        {

            float elementNameSimilarity = 0.0f,
                  elementNameSimilaritySon1 = 0.0f,
                  elementNameSimilaritySon2 = 0.0f,
                  attributeSimilaritySon1 = 0.0f,
                  attributeSimilaritySon2 = 0.0f,
                  childrenSimilaritySon1 = 0.0f,
                  childrenSimilaritySon2 = 0.0f;
            
            float[] valueSimilarity = {0.0f,0.0f};
            float[] childrenSimilarity = {0.0f,0.0f};

            elementNameSimilaritySon1 = elementsNameSimilarity(son1Node);
            elementNameSimilaritySon2 = elementsNameSimilarity(son2Node);
             
            boolean nameSimilarityRequired = SettingsHelper
                    .getNameSimilarityRequired();

            // only continue if necessary
            if ((nameSimilarityRequired && elementNameSimilaritySon1 == MAXIMUM_SIMILARITY && elementNameSimilaritySon2 == MAXIMUM_SIMILARITY) 
                    || (!nameSimilarityRequired)) {
                
                
                attributeSimilaritySon1 = threeWayElementsAttributesSimilarity(diff, son1Node);
                attributeSimilaritySon2 = threeWayElementsAttributesSimilarity(diff,son2Node);
                        
                valueSimilarity = threeWayElementsValueSimilarity(diff,son1Node,son2Node );
                

                childrenSimilarity = threeWayElementsChildrenSimilarity(diff,son1Node,son2Node);
                
                similaritySon1 = calculateSimilarity(elementNameSimilaritySon1,
                        attributeSimilaritySon1, valueSimilarity[0], childrenSimilarity[0]);
                
                similaritySon2 = calculateSimilarity(elementNameSimilaritySon2,
                        attributeSimilaritySon2, valueSimilarity[1], childrenSimilarity[1]);

                //similarity = calculateSimilarity(elementNameSimilaritySon1,
                        //attributeSimilaritySon1, valueSimilaritySon1, childrenSimilaritySon1);
            }
            else 
            {
                /*
                 * Se chegou nesta condição, então o elemento da esquerda é
                 * diferente do elemento da direita. Logo, esta informação deve ser
                 * exposta para usuário. Este método trata esta requisição.
                 */
                boolean bS1 = nameSimilarityRequired && elementNameSimilaritySon1 == MAXIMUM_SIMILARITY;
                boolean bS2 = nameSimilarityRequired && elementNameSimilaritySon2 == MAXIMUM_SIMILARITY;
                if(!bS1 && bS2)
                {
                    //Diff usualDiff = new Diff(leftNode,son2Node);
                    Diff usualDiff  = similar(son2Node);
                    diff.addChildren(usualDiff);
                    diff = varreTodosSubelementosParaMostrar(diff, son1Node, "left","son2");
                }
                else if(!bS2 && bS1)
                {
                    //diff  = similar(son1Node);
                    Diff usualDiff  = similar(son1Node);
                    diff.addChildren(usualDiff);
                    diff = varreTodosSubelementosParaMostrar(diff, son2Node,"right", "son1");
                    
                } 
                else
                {
                    diff = varreTodosSubelementosParaMostrar(diff, son1Node, "left","son1");
                    diff = varreTodosSubelementosParaMostrar(diff, leftNode, "center","ancestral");
                    diff = varreTodosSubelementosParaMostrar(diff, son2Node, "right", "son2");
                }
            }
                 
        } 
        
        // round number to deal with floating point precision
        NumberFormat df = DecimalFormat.getInstance(Locale.ENGLISH);
        df.setMaximumFractionDigits(3);
        similaritySon1 = Float.parseFloat(df.format(similaritySon1));
        similaritySon2 = Float.parseFloat(df.format(similaritySon2));
        
        //diff.setSimilaritySon1(similaritySon1);
        //diff.setSimilaritySon1(similaritySon2);
       diff.addSimilarityAttribute(similaritySon1,similaritySon2);
        return diff;
    }
    
    
     public ThreeWayDiff mergeAncestral(SimilarNode son1,SimilarNode son2) {

        Node son1Node = son1.getNode();
        Node son2Node = son2.getNode();

        //Diff diff = new Diff(leftNode, son1Node);
        ThreeWayDiff diff = new ThreeWayDiff(leftNode, son1Node, son2Node);

        float similaritySon1 = 0.0f;
        float similaritySon2 = 0.0f;

        if (leftNode != null && son1Node != null && son2Node != null) 
        {

            float elementNameSimilarity = 0.0f,
                  elementNameSimilaritySon1 = 0.0f,
                  elementNameSimilaritySon2 = 0.0f,
                  attributeSimilaritySon1 = 0.0f,
                  attributeSimilaritySon2 = 0.0f,
                  childrenSimilaritySon1 = 0.0f,
                  childrenSimilaritySon2 = 0.0f;
            
            float[] valueSimilarity = {0.0f,0.0f};
            float[] childrenSimilarity = {0.0f,0.0f};

            elementNameSimilaritySon1 = elementsNameSimilarity(son1Node);
            elementNameSimilaritySon2 = elementsNameSimilarity(son2Node);
             
            boolean nameSimilarityRequired = SettingsHelper
                    .getNameSimilarityRequired();

            // only continue if necessary
            if ((nameSimilarityRequired && elementNameSimilaritySon1 == MAXIMUM_SIMILARITY && elementNameSimilaritySon2 == MAXIMUM_SIMILARITY) 
                    || (!nameSimilarityRequired)) {
                
                
                attributeSimilaritySon1 = threeWayElementsAttributesSimilarity(diff, son1Node);
                attributeSimilaritySon2 = threeWayElementsAttributesSimilarity(diff,son2Node);
                        
                valueSimilarity = threeWayElementsValueSimilarity(diff,son1Node,son2Node );
                

                childrenSimilarity = threeWayElementsChildrenMerge(diff,son1Node,son2Node);
                
                similaritySon1 = calculateSimilarity(elementNameSimilaritySon1,
                        attributeSimilaritySon1, valueSimilarity[0], childrenSimilarity[0]);
                
                similaritySon2 = calculateSimilarity(elementNameSimilaritySon2,
                        attributeSimilaritySon2, valueSimilarity[1], childrenSimilarity[1]);

                //similarity = calculateSimilarity(elementNameSimilaritySon1,
                        //attributeSimilaritySon1, valueSimilaritySon1, childrenSimilaritySon1);
            }
            else 
            {
                /*
                 * Se chegou nesta condição, então o elemento da esquerda é
                 * diferente do elemento da direita. Logo, esta informação deve ser
                 * exposta para usuário. Este método trata esta requisição.
                 */
                boolean bS1 = nameSimilarityRequired && elementNameSimilaritySon1 == MAXIMUM_SIMILARITY;
                boolean bS2 = nameSimilarityRequired && elementNameSimilaritySon2 == MAXIMUM_SIMILARITY;
                if(!bS1 && bS2)
                {
                    //Diff usualDiff = new Diff(leftNode,son2Node);
                    Diff usualDiff  = similar(son2Node);
                    diff.addChildren(usualDiff);
                    diff = varreTodosSubelementosParaMostrar(diff, son1Node, "left","son2");
                }
                else if(!bS2 && bS1)
                {
                    //diff  = similar(son1Node);
                    Diff usualDiff  = similar(son1Node);
                    diff.addChildren(usualDiff);
                    diff = varreTodosSubelementosParaMostrar(diff, son2Node,"right", "son1");
                    
                } 
                else
                {
                    diff = varreTodosSubelementosParaMostrar(diff, son1Node, "left","son1");
                   // diff = varreTodosSubelementosParaMostrar(diff, leftNode, "center","ancestral");
                    diff = varreTodosSubelementosParaMostrar(diff, son2Node, "right", "son2");
                }
            }
                 
        } 
        
        // round number to deal with floating point precision
        NumberFormat df = DecimalFormat.getInstance(Locale.ENGLISH);
        df.setMaximumFractionDigits(3);
        similaritySon1 = Float.parseFloat(df.format(similaritySon1));
        similaritySon2 = Float.parseFloat(df.format(similaritySon2));
        
        diff.addSimilarityAttribute(similaritySon1,similaritySon2);
        return diff;
    }


    /**
     * Helper method to calculate similarity based on values for each similarity 
     * element and settings chosen by user.
     * 
     * @param elementNameSimilarity
     * @param attributeSimilarity
     * @param valueSimilarity
     * @param childrenSimilarity
     * 
     * @return the similarity
     */
    private float calculateSimilarity(float elementNameSimilarity,
            float attributeSimilarity, float valueSimilarity, float childrenSimilarity) {

        float similarity = 0.0f;

        boolean ignoreTrivial = SettingsHelper.getIgnoreTrivialSimilarities();
        boolean nameSimilarityRequired = SettingsHelper.getNameSimilarityRequired();

        float nameWeight = SettingsHelper.getNameSimilarityWeight();
        float valueWeight = SettingsHelper.getValueSimilarityWeight();
        float attributeWeight = SettingsHelper.getAttributeSimilarityWeight();
        float childrenWeight = SettingsHelper.getChildrenSimilarityWeight();

        if (nameSimilarityRequired) {
            nameWeight = 0;
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

        float totalWeight = nameWeight + attributeWeight + valueWeight + childrenWeight;
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

    /**
     * Função para calcular quanto os nomes dos elementos são iguais. 
     * @param rightNode nome do elemento do lado direito do documento
     * @return retorna o percentual de similaridade encontrado entre 0 e 1
     */
    protected float elementsNameSimilarity(Node rightNode) {

        LcsString cmp = new LcsString(leftNode.getNodeName(), 
                rightNode.getNodeName()); 
        return cmp.similaridade();
    }

    /**
     * Compara a similaridade do conteúdo dos elementos. A comparação é feita
     * usando o algoritmo LCS.
     * @param rightNode conteudo do lado direito
     * @param diff diff corrente 
     * @return total de similaridade encontrado entre os conteúdos entre 0 e 1.
     * @throws DOMException 
     */
    protected float elementsValueSimilarity(Node rightNode, Diff diff) 
            throws DOMException {
        
        float similarity = 0;

        String leftNodeValue = leftNode.hasChildNodes()?
                leftNode.getFirstChild().getNodeValue():
                null;
        
        String rightNodeValue = rightNode.hasChildNodes()?
                rightNode.getFirstChild().getNodeValue():
                null;        
        
        if (leftNodeValue == null && rightNodeValue == null) {
            similarity = SKIP_SIMILARITY;
        } else {
            diff.setValue(leftNodeValue, rightNodeValue);

            if (leftNodeValue != null && rightNodeValue != null) {
                LcsString cmp = new LcsString(leftNodeValue, rightNodeValue); 
                similarity = cmp.similaridade();
            }
        }

        return similarity;
    }
    
    
     protected float [] threeWayElementsValueSimilarity(ThreeWayDiff diff, Node son1Node, Node son2Node) 
            throws DOMException {
        
        //float similarity = 0;
        float[] similarity = {SKIP_SIMILARITY,SKIP_SIMILARITY};

        String ancestralNodeValue = leftNode.hasChildNodes()?
                leftNode.getFirstChild().getNodeValue():
                null;
        
        String Son1NodeValue = son1Node.hasChildNodes()?
                son1Node.getFirstChild().getNodeValue():
                null;
        
        String Son2NodeValue = son2Node.hasChildNodes()?
                son2Node.getFirstChild().getNodeValue():
                null;
        
        if (ancestralNodeValue == null && Son1NodeValue == null && Son2NodeValue == null ) {
            //similarity = SKIP_SIMILARITY;
        } 
        else 
        {
            diff.setValue(ancestralNodeValue, Son1NodeValue,Son2NodeValue);
            
             float similarity1 = 0;
             float similarity2 = 0;

            if (ancestralNodeValue != null && Son1NodeValue != null) 
            {
                LcsString cmp = new LcsString(ancestralNodeValue, Son1NodeValue); 
                similarity1 = cmp.similaridade();
            }
            
            if (ancestralNodeValue != null && Son2NodeValue != null) 
            {
                LcsString cmp = new LcsString(ancestralNodeValue, Son1NodeValue); 
                similarity2 = cmp.similaridade();
            }
            
            similarity[0] = similarity1;//;(similarity1+similarity2)/2;
            similarity[1] = similarity2;        
        }

        return similarity;
    }

    /**
     * Comparação de similaridade dos atributos do elemento corrente. A comparação
     * é feita agrupando todos os nomes dos atributos pertencentes aos elementos, 
     * tanto do elemento da esquerda quanto do da direita, e colocado em uma lista. 
     * Feito isto a lista é percorrida e, para cada elemento:
     * - se só está presente em um dos elementos, similaridade deste atributo = 0;
     * - se esta nos dois lados, faz se a comparação com LCS;
     * Após percorrer toda lista, a soma de todas as similaridades, dividida pelo 
     * número de atributos nos dá a similaridade resultante desta componente.
     * 
     * @param rightNode Elemento do lado direito do documento
     * @param diff Diff corrente até o momento
     * @return Retorna a similaridade total encontrada entre os atributos.
     */
    protected float elementsAttributesSimilarity(Node rightNode, Diff diff) {
        float similarity = 0;

        // if both don't have attributes, this shouldn't be considered
        // at similarity calculus
        if (!leftNode.hasAttributes() && !rightNode.hasAttributes()) {
            similarity = SKIP_SIMILARITY;
        } else {
            // if one of them don't have attributes, the similarity is zero!
            if (leftNode.hasAttributes() && rightNode.hasAttributes()) {
                
                NamedNodeMap attrsLeft = leftNode.getAttributes();
                NamedNodeMap attrsRight = rightNode.getAttributes();
                
                // get all attributes names, from both nodes
                List<String> allAttr = 
                        getAllAttributesNames(attrsLeft, attrsRight);
                
                // go through all attributes and for each one, check if it's 
                // present in one or both nodes, and if value is the same.
                Iterator<String> iter = allAttr.iterator();
                while (iter.hasNext()) {

                    String attributeName = iter.next();
                    
                    Node attributeLeft = attrsLeft.getNamedItem(attributeName);
                    Node attributeRight = attrsRight.getNamedItem(attributeName);
                    
                    // if it is not present at one side, the similarity for 
                    // this particular attribute is zero.
                    if (attributeLeft != null && attributeRight != null) {
                        
                        String attributeLeftValue = attributeLeft.getNodeValue();
                        String attributeRightValue = attributeRight.getNodeValue();
                        
                        diff.addAttribute(attributeName, attributeLeftValue, 
                                attributeRightValue);
                        
                        // if both values are equal
                        if (attributeLeftValue.equals(attributeRightValue)) {
                            similarity += MAXIMUM_SIMILARITY;
                        } else {
                            // calculate similarity based on LCS between values
                            similarity += (new LcsString(
                                    new SimilarString(attributeLeftValue),
                                    new SimilarString(attributeRightValue)))
                                    .similaridade();
                        }
                    }
                }
                // each attribute contributes equally to the 
                // similarity accounting
                similarity = similarity / allAttr.size();
            }
        }

        return similarity;
    }
   
    protected float threeWayElementsAttributesSimilarity(ThreeWayDiff diff,Node sonNode) 
    {
        float similarity = 0;

        // if both don't have attributes, this shouldn't be considered
        // at similarity calculus
        if (!leftNode.hasAttributes() && !sonNode.hasAttributes()) 
        {
            similarity = SKIP_SIMILARITY;
        } 
        else 
        {
            // if one of them don't have attributes, the similarity is zero!
            if (leftNode.hasAttributes() && sonNode.hasAttributes()) 
            {
                
                NamedNodeMap attrsLeft = leftNode.getAttributes();
                NamedNodeMap attrsRight = sonNode.getAttributes();
                
                // get all attributes names, from both nodes
                List<String> allAttr = 
                        getAllAttributesNames(attrsLeft, attrsRight);
                
                // go through all attributes and for each one, check if it's 
                // present in one or both nodes, and if value is the same.
                Iterator<String> iter = allAttr.iterator();
                while (iter.hasNext()) {

                    String attributeName = iter.next();
                    
                    Node attributeLeft = attrsLeft.getNamedItem(attributeName);
                    Node attributeRight = attrsRight.getNamedItem(attributeName);
                    
                    // if it is not present at one side, the similarity for 
                    // this particular attribute is zero.
                    if (attributeLeft != null && attributeRight != null) {
                        
                        String attributeLeftValue = attributeLeft.getNodeValue();
                        String attributeRightValue = attributeRight.getNodeValue();
                        
                        diff.addAttribute(attributeName, attributeLeftValue, 
                                attributeRightValue);
                        
                        // if both values are equal
                        if (attributeLeftValue.equals(attributeRightValue)) {
                            similarity += MAXIMUM_SIMILARITY;
                        } else {
                            // calculate similarity based on LCS between values
                            similarity += (new LcsString(
                                    new SimilarString(attributeLeftValue),
                                    new SimilarString(attributeRightValue)))
                                    .similaridade();
                        }
                    }
                }
                // each attribute contributes equally to the 
                // similarity accounting
                similarity = similarity / allAttr.size();
            }
        }

        return similarity;
    }

    
    /**
     * Método de comparação dos subelementos. Este método agrupa todos os elementos
     * do lado esquerdo do documento, depois agrupa todos os elementos do lado
     * direito do documento e faz uma comparação entre eles.
     * @param rightNode Elemento do lado direito do documento
     * @param diff Diff corrente até o momento
     * @return 
     */
    protected float elementsChildrenSimilarity(Node rightNode, Diff diff) {

        float similarity = 0;

        // no children nodes, why compare them or use them to 
        // calculate similarity?
        if (!leftNode.hasChildNodes() && !rightNode.hasChildNodes()) {
            similarity = SKIP_SIMILARITY;
        } else {
            
            NodeSet leftSubElements = getElementNodes(leftNode.getChildNodes());
            NodeSet rightSubElements = getElementNodes(rightNode.getChildNodes());

            // none of the children nodes was a element
            if ((leftSubElements.size() == 0 && rightSubElements.size() == 0)) {
                similarity = SKIP_SIMILARITY;
            } else if ((leftSubElements.size() != 0 && rightSubElements.size() != 0)) {
                HungarianList hungarianList = new HungarianList(leftSubElements, rightSubElements);
                diff = hungarianList.calcularSimilaridadeDosSubElementos(diff);
                similarity = hungarianList.similaridade();

            } else {
                if (leftSubElements.size() != 0) {
                    diff = varreTodosSubelementosParaMostrar(diff, leftNode, "left");

                } else {
                    diff = varreTodosSubelementosParaMostrar(diff, rightNode, "right");
                }

            }
        }

        diff.setSimilarity(similarity);
        return similarity;
    }
    
    protected float [] threeWayElementsChildrenSimilarity( ThreeWayDiff diff, Node son1Node, Node son2Node) {

        float [] similarity = {SKIP_SIMILARITY,SKIP_SIMILARITY};

        // no children nodes, why compare them or use them to 
        // calculate similarity?
        if (!leftNode.hasChildNodes() && !son1Node.hasChildNodes() && !son2Node.hasChildNodes()) 
        {
            //similarity = SKIP_SIMILARITY;
        } 
        else 
        {
            
            NodeSet ancestralSubElements = getElementNodes(leftNode.getChildNodes());
            NodeSet son1SubElements = getElementNodes(son1Node.getChildNodes());
            NodeSet son2SubElements = getElementNodes(son2Node.getChildNodes());

            // none of the children nodes was a element
            if ((ancestralSubElements.size() == 0 && son1SubElements.size() == 0 && son2SubElements.size() == 0)) 
            {
                //similarity = SKIP_SIMILARITY;
            } 
            else 
            {       
                    Diff diff1 = null;
                    if ((ancestralSubElements.size() != 0 && son1SubElements.size() != 0)) 
                    {
                        HungarianList hungarianList = new HungarianList(ancestralSubElements, son1SubElements);
                        Diff usualDiff = new Diff(son1Node);
                        usualDiff = hungarianList.calcularSimilaridadeDosSubElementos(usualDiff);
                        //diff.updateDiffPrefix(usualDiff.getDiffNode(),ThreeWayDiff.DIFF_ANCESTRAL, ThreeWayDiff.DIFF_SON1, ThreeWayDiff.DIFF_ANCESTRAL_SON1 ) ;
                        //diff.addChildren(usualDiff);
                        diff1 = usualDiff;
                        similarity[0] = hungarianList.similaridade();
                    }
                    
                    Diff diff2 = null;
                    if((ancestralSubElements.size() != 0 && son2SubElements.size() != 0))
                    {
                        HungarianList hungarianList = new HungarianList(ancestralSubElements, son2SubElements);
                        Diff usualDiff = new Diff(son2Node);
                        usualDiff = hungarianList.calcularSimilaridadeDosSubElementos(usualDiff);
                        //diff.updateDiffPrefix(usualDiff.getDiffNode(),ThreeWayDiff.DIFF_ANCESTRAL, ThreeWayDiff.DIFF_SON2, ThreeWayDiff.DIFF_ANCESTRAL_SON2 ) ;
                        
                        //diff.addChildren(usualDiff);
                        diff2 = usualDiff;
                        similarity[1] = hungarianList.similaridade();
                    }
                    
                    if(diff1 != null && diff2 !=null)
                    {               
                        Document doc = diff.getDiffNode().getOwnerDocument();
                        
                        Document doc1 = diff1.getDiffNode().getOwnerDocument();
                        Document doc2 = diff2.getDiffNode().getOwnerDocument();
                       
                         
                        //Node root1 = doc1.getDocumentElement();
                        /*NodeList rootchildren1 = diff1.getDiffNode().getChildNodes();
                        Element newroot1 = doc1.createElement(diff1.getDiffNode().getNodeName());
                        for (int i=0;i<rootchildren1.getLength();i++) {
                            newroot1.appendChild(rootchildren1.item(i).cloneNode(true));
                        }
                        doc1.replaceChild(newroot1, diff1.getDiffNode());
                        
                        
                       
                        //Node root2 = doc2.getDocumentElement();
                        NodeList rootchildren2 = diff2.getDiffNode().getChildNodes();
                        Element newroot2 = doc1.createElement(diff2.getDiffNode().getNodeName());
                        for (int i=0;i<rootchildren2.getLength();i++) {
                            newroot2.appendChild(rootchildren2.item(i).cloneNode(true));
                        }
                        doc1.replaceChild(newroot2, diff2.getDiffNode());*/
                        
                        //Node node1 =doc1.importNode(diff1.getDiffNode(),true);
                       // Node node2 = doc2.importNode(diff2.getDiffNode(),true);
                         // TransformerFactory tFactory = TransformerFactory.newInstance();
                         // Transformer transformer = tFactory.newTransformer();
                         // transformer.setOutputProperty(OutputKeys.INDENT, "yes"); 
                          //transformer.transform(source, result); */
                        
                        //Node root1 = doc1.createElement("Teste");
                        //Node root2 = doc2.createElement("Teste2");
                        //diff.copyTree(diff1.getDiffNode(),root1);
                        //diff.copyTree(diff2.getDiffNode(),root2);
                       //diff.updateDiffPrefix(diff1.getDiffNode(),diff.DIFF_SON1, diff.DIFF_ANCESTRAL,diff.DIFF_ANCESTRAL_SON1 );
                       //diff.updateDiffPrefix(diff2.getDiffNode(),diff.DIFF_SON2, diff.DIFF_ANCESTRAL,diff.DIFF_ANCESTRAL_SON2 );
                        //HungarianList hungarianList = new HungarianList(getElementNodes(diff1.getDiffNode().getChildNodes()), getElementNodes(diff2.getDiffNode().getChildNodes()));
                        //Diff usualDiff = new Diff(diff1.getDiffNode(),diff2.getDiffNode());
                        //usualDiff = hungarianList.calcularSimilaridadeDosSubElementos(usualDiff);
                        //diff.addChildren(usualDiff);
                        //Diff finalDiff = new Diff(diff1.getDiffNode());
                        
                        //float a[] = threeWayElementsValueSimilarity( diff ,diff1.getDiffNode(),diff2.getDiffNode());
                        //Node finalNode = diff.mergeTree(diff1.getDiffNode(),diff2.getDiffNode());
                        
                        
//                        Document doc = new DocumentImpl();  
//                        Element root = doc.createElement( "MergedDocument" );
//                        doc.importNode( diff1.getDiffNode(),true);
//                        doc.importNode( diff2.getDiffNode(),true); 
//                        NodeSet diff1SubElements = getElementNodes(nodeDiff1.getChildNodes());
//                        NodeSet diff2SubElements = getElementNodes(nodeDiff2.getChildNodes());
//                        
//                        int diff1Size = diff1SubElements.size();
//                        int diff2Size = diff2SubElements.size();
//                        if(diff1Size>=diff2Size)
//                        {
//                            
//                        }
//                        else
//                        {
//                            
//                        }
                        
                       diff.mergeTree(diff1.getDiffNode(), diff2.getDiffNode(), diff.getDiffNode(),true,diff);
                    }
                    
                    
                    /*
                    if((son1SubElements.size() != 0 && son2SubElements.size() != 0) && ancestralSubElements.size() == 0)
                    {
                        HungarianList hungarianList = new HungarianList(son1SubElements, son2SubElements);
                        Diff usualDiff = new Diff(son1Node,son2Node);
                        usualDiff = hungarianList.calcularSimilaridadeDosSubElementos(usualDiff);
                        diff.updateDiffPrefix(usualDiff.getDiffNode(),ThreeWayDiff.DIFF_SON1, ThreeWayDiff.DIFF_SON2 ) ;
                        diff.addChildren(usualDiff);
                        //Não possuem simililaridade com o Pai
                    }*/
                    /*
                    if (ancestralSubElements.size() != 0) 
                    {
                        diff = varreTodosSubelementosParaMostrar(diff, leftNode, "center","ancetral");
                    } 
                    else if(son1SubElements.size() != 0)
                    {
                        diff = varreTodosSubelementosParaMostrar(diff, son1Node, "left","son1");
                    }
                    else if(son2SubElements.size() != 0)
                    {
                        diff = varreTodosSubelementosParaMostrar(diff, son2Node, "right","son2");
                    }
                    */
            }
        }

        //diff.setSimilaritySon1(similarity[0]);
        //diff.setSimilaritySon2(similarity[1]);
        return similarity;
    }
    
    
    protected float [] threeWayElementsChildrenMerge( ThreeWayDiff diff, Node son1Node, Node son2Node) {

        float [] similarity = {SKIP_SIMILARITY,SKIP_SIMILARITY};

        // no children nodes, why compare them or use them to 
        // calculate similarity?
        if (!leftNode.hasChildNodes() && !son1Node.hasChildNodes() && !son2Node.hasChildNodes()) 
        {
            //similarity = SKIP_SIMILARITY;
        } 
        else 
        {
            
            NodeSet ancestralSubElements = getElementNodes(leftNode.getChildNodes());
            NodeSet son1SubElements = getElementNodes(son1Node.getChildNodes());
            NodeSet son2SubElements = getElementNodes(son2Node.getChildNodes());

            // none of the children nodes was a element
            if ((ancestralSubElements.size() == 0 && son1SubElements.size() == 0 && son2SubElements.size() == 0)) 
            {
                //similarity = SKIP_SIMILARITY;
            } 
            else 
            {       
                    Diff diff1 = null;
                    if ((ancestralSubElements.size() != 0 && son1SubElements.size() != 0)) 
                    {
                        HungarianList hungarianList = new HungarianList(ancestralSubElements, son1SubElements);
                        Diff usualDiff = new Diff(son1Node);
                        usualDiff = hungarianList.calcularSimilaridadeDosSubElementos(usualDiff);
                        //diff.updateDiffPrefix(usualDiff.getDiffNode(),ThreeWayDiff.DIFF_ANCESTRAL, ThreeWayDiff.DIFF_SON1, ThreeWayDiff.DIFF_ANCESTRAL_SON1 ) ;
                        //diff.addChildren(usualDiff);
                        diff1 = usualDiff;
                        similarity[0] = hungarianList.similaridade();
                    }
                    
                    Diff diff2 = null;
                    if((ancestralSubElements.size() != 0 && son2SubElements.size() != 0))
                    {
                        HungarianList hungarianList = new HungarianList(ancestralSubElements, son2SubElements);
                        Diff usualDiff = new Diff(son2Node);
                        usualDiff = hungarianList.calcularSimilaridadeDosSubElementos(usualDiff);
                        //diff.updateDiffPrefix(usualDiff.getDiffNode(),ThreeWayDiff.DIFF_ANCESTRAL, ThreeWayDiff.DIFF_SON2, ThreeWayDiff.DIFF_ANCESTRAL_SON2 ) ;
                        
                        //diff.addChildren(usualDiff);
                        diff2 = usualDiff;
                        similarity[1] = hungarianList.similaridade();
                    }
                    
                    if(diff1 != null && diff2 !=null)
                    {               
                        //Document doc = diff.getDiffNode().getOwnerDocument();
                        
                        //Document doc1 = diff1.getDiffNode().getOwnerDocument();
                        //Document doc2 = diff2.getDiffNode().getOwnerDocument();
                        
                        diff.mergeTree(diff1.getDiffNode(), diff2.getDiffNode(), diff.getDiffNode(),false,diff);
                        
                    }
            }
        }

        //diff.setSimilaritySon1(similarity[0]);
       // diff.setSimilaritySon2(similarity[1]);
        return similarity;
    }

    /**
     * Retornar os nós de um NodeList que são do tipo elemento.
     *
     * @param nodeList
     * @return
     * @see Node
     */
    public static NodeSet getElementNodes(NodeList nodeList) {
        NodeSet elementNodes = new NodeSet();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node item = nodeList.item(i);

            if (item.getNodeType() == Node.ELEMENT_NODE) {
                elementNodes.addNode(item);
            }
        }

        return elementNodes;
    }

    
    /**
     *  Método que extrai todos os atributos pertencentes a ambos os lados do 
     * documento.
     * @param ladoEsquerdo - atributos que estão no lado esquerdo do documento
     * @param ladoDireito - atributos que estão no lado direito do documento
     * @return
     *  Retorna uma biblioteca com todos os atributos de ambos os lados dos documentos
     * com seus respectivos conteúdos.
     */
    protected List<String> getAllAttributesNames(NamedNodeMap left, 
            NamedNodeMap right) {

        List<String> attributes = new ArrayList<String>();

        for (int i = 0; i < left.getLength(); i++) {
            Node item = left.item(i);
            attributes.add(item.getNodeName());
        }
        for (int i = 0; i < right.getLength(); i++) {
            Node item = right.item(i);
            if (!attributes.contains(item.getNodeName())) {
                attributes.add(item.getNodeName());
            }
        }

        return attributes;
    }

    public Node getNode() {
        return leftNode;
    }

    @Override
    public String toString() {
        return leftNode.getNodeName();
    }

    private boolean isElementValue(String valor) {
        return (valor.contains("\n") && valor.contains("        ")) ? false : true;
    }

    /**
     *  Cria um objeto do tipo Diff com todos os elementos e subelmentos que não
     * aparece no outro lado do documento.
     * @param diff - diff corrente até o momento
     * @param nodeCorrente - elemento que deve ser criado
     * @param nodeSide - informa de qual lado do documento o nodeCorrente pertence
     * @return 
     *  Retorna um novo objeto Diff com todos os subelementos que pertence aquele
     * elemento.
     */
    private Diff varreTodosSubelementosParaMostrar(Diff diff, Node nodeCorrente, String nodeSide) {
        Diff x = diff;
        Diff novoDiff = null;
        if (nodeCorrente.hasChildNodes()) {
            NodeList subElementos = nodeCorrente.getChildNodes();
            for (int i = 0; i < subElementos.getLength(); i++) {
                Node filho = subElementos.item(i);
                String sideElementValue = filho.getNodeValue();
                if (filho.getNodeType() == Node.TEXT_NODE
                        && sideElementValue != null
                        && isElementValue(sideElementValue)) {

                    Element valueNode = (Element) DiffXML.createNode("value");
                    valueNode.setAttributeNS(Diff.NAMESPACE, Diff.DIFF_PREFIX + nodeSide, sideElementValue);
                    x.getDiffNode().appendChild(valueNode);
                } else if (filho.getNodeType() == Node.ELEMENT_NODE) {
                    novoDiff = new Diff(filho);
                    novoDiff.setSimilarity(0);
                    novoDiff.addSideAttribute(nodeSide);
                    novoDiff = inserirAtributosNosElementos(novoDiff, filho);

                    if (filho.hasChildNodes()) {
                        novoDiff = varreTodosSubelementosParaMostrar(novoDiff, filho, nodeSide);
                    }
                    x.addChildren(novoDiff);
                }
            }
        }
        return x;
    }
    
    
    private ThreeWayDiff varreTodosSubelementosParaMostrar(ThreeWayDiff diff, Node nodeCorrente, String nodeSide,String diffRelation) {
        ThreeWayDiff x = diff;
        ThreeWayDiff novoDiff = null;
        if (nodeCorrente.hasChildNodes()) {
            NodeList subElementos = nodeCorrente.getChildNodes();
            for (int i = 0; i < subElementos.getLength(); i++) {
                Node filho = subElementos.item(i);
                String sideElementValue = filho.getNodeValue();
                if (filho.getNodeType() == Node.TEXT_NODE
                        && sideElementValue != null
                        && isElementValue(sideElementValue)) {

                    Element valueNode = (Element) DiffXML.createNode("value");
                    valueNode.setAttributeNS(Diff.NAMESPACE, Diff.DIFF_PREFIX + nodeSide, sideElementValue);
                    //valueNode.setAttributeNS(Diff.NAMESPACE, Diff.DIFF_PREFIX + nodeSide, sideElementValue);
                    x.getDiffNode().appendChild(valueNode);
                } else if (filho.getNodeType() == Node.ELEMENT_NODE) {
                    novoDiff = new ThreeWayDiff(filho);
                    //novoDiff.setSimilaritySon1(0);
                    novoDiff.addSideAttribute(nodeSide);
                    novoDiff.addDiffRelation(diffRelation);
                    novoDiff = inserirAtributosNosElementos(novoDiff, filho);

                    if (filho.hasChildNodes()) {
                        novoDiff = varreTodosSubelementosParaMostrar(novoDiff, filho, nodeSide,diffRelation);
                    }
                    x.addChildren(novoDiff);
                }
            }
        }
        return x;
    }


    private Diff inserirAtributosNosElementos(Diff diff, Node elemento) {
        NamedNodeMap attributes = elemento.getAttributes();
        if (elemento.hasAttributes()) {
            for (int i = 0; i < attributes.getLength(); i++) {
                diff.addAtribute(attributes.item(i));
            }
        }
        return diff;
    }
    
    private ThreeWayDiff inserirAtributosNosElementos(ThreeWayDiff diff, Node elemento) {
        NamedNodeMap attributes = elemento.getAttributes();
        if (elemento.hasAttributes()) {
            for (int i = 0; i < attributes.getLength(); i++) {
                diff.addAtribute(attributes.item(i));
            }
        }
        return diff;
    }
}

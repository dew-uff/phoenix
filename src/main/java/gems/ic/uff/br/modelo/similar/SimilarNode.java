package gems.ic.uff.br.modelo.similar;

import gems.ic.uff.br.modelo.Diff;
import gems.ic.uff.br.modelo.DiffXML;
import gems.ic.uff.br.modelo.HungarianList;
import gems.ic.uff.br.modelo.LcsString;
import gems.ic.uff.br.settings.SettingsHelper;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sun.org.apache.xpath.internal.NodeSet;

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
        } else {
            /*
             * Se chegou nesta condição, então o elemento da esquerda é
             * diferente do elemento da direita. Logo, esta informação deve ser
             * exposta para usuário. Este método trata esta requisição.
             */
            diff = varreTodosSubelementosParaMostrar(diff, leftNode, "left");
        }

        // round number to deal with floating point precision
        DecimalFormat df = new DecimalFormat("#.###");
        similarity = Float.parseFloat(df.format(similarity));
        
        diff.setSimilarity(similarity);
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
        int consideredSimilarities = 4;
        
        boolean ignoreTrivial = SettingsHelper.getIgnoreTrivialSimilarities();
        boolean nameSimilarityRequired = SettingsHelper.getNameSimilarityRequired();

        float nameWeight = SettingsHelper.getNameSimilarityWeight();
        float valueWeight = SettingsHelper.getValueSimilarityWeight();
        float attributeWeight = SettingsHelper.getAttributeSimilarityWeight();
        float childrenWeight = SettingsHelper.getChildrenSimilarityWeight();
        
        float totalWeight = 1.0f;

        // if name similarity is required it is not considered to calculate weight
        if (nameSimilarityRequired) {
            elementNameSimilarity = 0.0f;
            totalWeight -= nameWeight;
            consideredSimilarities--;
        }

        if (attributeSimilarity == SKIP_SIMILARITY) {
            if (ignoreTrivial) {
                attributeSimilarity = 0.0f;
                totalWeight -= attributeWeight;
                consideredSimilarities--;
            }
            else {
                attributeSimilarity = 1.0f;
            }
        }

        if (valueSimilarity == SKIP_SIMILARITY) {
            if (ignoreTrivial) {
                valueSimilarity = 0.0f;
                totalWeight -= valueWeight;
                consideredSimilarities--;
            }
            else {
                valueSimilarity = 1.0f;
            }
        }

        if (childrenSimilarity == SKIP_SIMILARITY) {
            if (ignoreTrivial) {
                childrenSimilarity = 0.0f;
                totalWeight -= childrenWeight;
                consideredSimilarities--;
            }
            else {
                childrenSimilarity = 1.0f;
            }
        }

        if (consideredSimilarities == 0) {
            similarity = 1.0f;
        }
        else {
            similarity = 
                     (elementNameSimilarity*SettingsHelper.getNameSimilarityWeight()
                    + attributeSimilarity * SettingsHelper.getAttributeSimilarityWeight()
                    + valueSimilarity * SettingsHelper.getValueSimilarityWeight()
                    + childrenSimilarity * SettingsHelper.getChildrenSimilarityWeight())
                    /totalWeight;
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

    /**
     * TODO revisar comentário
     * Comparação de similaridade dos atributos do elemento corrente. A comparação
     * é feita agrupando todos os atributos pertencentes aos elementos, tanto da
     * esquerda quanto da direita, e colocado em uma classe Map, onde a chave é
     * o nome do elemento e o valor é uma lista do tipo SimilarString. Dentro 
     * dessa lista, a posição 0 indica o conteudo do lado esquerdo do documento
     * comparado, e a posição 1 indica o conteudo do lado direito.
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
                            similarity = (new LcsString(
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

    private Diff inserirAtributosNosElementos(Diff diff, Node elemento) {
        NamedNodeMap attributes = elemento.getAttributes();
        if (elemento.hasAttributes()) {
            for (int i = 0; i < attributes.getLength(); i++) {
                diff.addAtribute(attributes.item(i));
            }
        }
        return diff;
    }
}

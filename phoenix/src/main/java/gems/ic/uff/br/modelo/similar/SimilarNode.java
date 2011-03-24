package gems.ic.uff.br.modelo.similar;

import com.sun.org.apache.xpath.internal.NodeSet;
import gems.ic.uff.br.modelo.LcsBatch;
import gems.ic.uff.br.modelo.MongeElkanList;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.DOMException;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Classe que encapsula a classe Node do DOM, adicionando um comportamento
 * de similaridade a ela.
 */
public class SimilarNode extends Similar<SimilarNode> {

    private Node node;
    //Depois de definidos, testar os valores padrões.
    public static final double ATTRIBUTE_WEIGTH = 0.4;
    public static final double ELEMENT_VALUE_WEIGTH = 0.5;
    public static final double ELEMENT_NAME_WEIGTH = 1;
    public static final double ELEMENT_CHILDREN_WEIGTH = 0.6;
//    public static final double ELEMENT_TYPE = 0.6;

    public SimilarNode(Node node) {
        this.node = node;
    }

    @Override
    public double similar(SimilarNode y) {
        double similarity = 1;
        Node otherNode = y.getNode();

        if (node == null || otherNode == null) {
            similarity = 0;
        } else {
            similarity = elementsNameSimilarity(otherNode, similarity);
            similarity = elementsValueSimilarity(otherNode, similarity);
            similarity = elementsAttributesSimilarity(otherNode, similarity);
            similarity = elementsChildrenSimilarity(otherNode, similarity);
        }

        //TODO: Testar
        return similarity > 0 ? similarity : 0;
    }

    public double elementsNameSimilarity(Node otherNode, double similarity) {
        if (!node.getNodeName().equals(otherNode.getNodeName())) {
            similarity -= ELEMENT_NAME_WEIGTH;
        }

        return similarity;
    }

    public double elementsValueSimilarity(Node otherNode, double similarity) throws DOMException {
        if (node.hasChildNodes() || otherNode.hasChildNodes()) {
            String nodeValue = null;
            String otherNodeValue = null;

            if (node.hasChildNodes()) {
                //O valor do elemento é um de seus filhos.
                nodeValue = node.getFirstChild().getNodeValue();
            }

            if (otherNode.hasChildNodes()) {
                //O valor do elemento é um de seus filhos.
                otherNodeValue = otherNode.getFirstChild().getNodeValue();
            }

            if (nodeValue != null || otherNodeValue != null) {
                if (nodeValue == null || otherNodeValue == null || !nodeValue.equals(otherNodeValue)) {
                    similarity -= ELEMENT_VALUE_WEIGTH;
                }
            }
        }

        return similarity;
    }

    public double elementsAttributesSimilarity(Node otherNode, double similarity) {
        if (node.hasAttributes() || otherNode.hasAttributes()) {
            if (node.hasAttributes() && otherNode.hasAttributes()) {
                NamedNodeMap attributesFromNode = node.getAttributes();
                NamedNodeMap attributesFromOtherNode = otherNode.getAttributes();

                List<List<Similar>> attributes = getAttributesFromNamedNodeMaps(attributesFromNode, attributesFromOtherNode);
                similarity -= (1 - new LcsBatch(attributes.get(0), attributes.get(1)).similaridade()) * ATTRIBUTE_WEIGTH;
                            
            } else {
                similarity -= ATTRIBUTE_WEIGTH;
            }
        }

        return similarity;
    }

    public double elementsChildrenSimilarity(Node otherNode, double similarity) {
        NodeList childNodes = node.getChildNodes();
        NodeList otherChildNodes = otherNode.getChildNodes();

        if (childNodes != null && otherChildNodes != null) {
            NodeSet elementNodes = getElementNodes(childNodes);
            NodeSet otherElementNodes = getElementNodes(otherChildNodes);

            if ((elementNodes.size() != 0 && otherElementNodes.size() != 0)) {
                MongeElkanList mongeElkanList = new MongeElkanList(elementNodes, otherElementNodes);
                similarity -= (1 - mongeElkanList.similaridade()) * ELEMENT_CHILDREN_WEIGTH;
            } else {
                if (!(elementNodes.size() == 0 && otherElementNodes.size() == 0)) {
                    similarity -= ELEMENT_CHILDREN_WEIGTH;
                }
            }
        } else {
            similarity -= ELEMENT_CHILDREN_WEIGTH;
        }

        return similarity;
    }

    private NodeSet getElementNodes(NodeList nodeList) {
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
     * Gera duas listas de strings com os valores dos atributos dos elementos.
     * Lista1: [month='3', year='2011']
     * Lista2: [attribute='yes', month='4', year='2011']
     * Retorno: [['3', '2011', ''], ['4', '2011', 'yes']]
     * @param nodeMap
     * @param otherNodeMap
     * @return 
     */
    private List<List<Similar>> getAttributesFromNamedNodeMaps(NamedNodeMap nodeMap, NamedNodeMap otherNodeMap) {
        int nodeMapLength = nodeMap.getLength();
        int otherNodeMapLength = otherNodeMap.getLength();

        //TODO: O tamanho do array não dá para ser descoberto, utilizar outra implementação de List?
        List<Similar> attributesFromNode = new ArrayList<Similar>(nodeMapLength + otherNodeMapLength);
        List<Similar> attributesFromOtherNode = new ArrayList<Similar>(nodeMapLength + otherNodeMapLength);
        
        List<List<Similar>> attributesList = new ArrayList<List<Similar>>(2);
        attributesList.add(attributesFromNode);
        attributesList.add(attributesFromOtherNode);

        for (int i = 0; i < nodeMapLength; i++) {
            Node item = nodeMap.item(i);
            Node otherItem = otherNodeMap.getNamedItem(item.getNodeName());

            //Adiciona todos os elementos da primeira lista.
            attributesFromNode.add(new SimilarString(item.getNodeValue()));
            
            //Insere o elemento da/na segunda lista caso este exista na segunda lista.
            if (otherItem != null) {
                attributesFromOtherNode.add(new SimilarString(otherItem.getNodeValue()));
            //Caso contrário, insere uma String vazia.
            } else {
                attributesFromOtherNode.add(new SimilarString(""));
            }
        }

        for (int i = 0; i < otherNodeMapLength; i++) {
            Node otherItem = otherNodeMap.item(i);
            Node item = nodeMap.getNamedItem(otherItem.getNodeName());

            //Adiciona todos os elementos da/na segunda lista que não existem
            //na primeira lista. E insere uma String vazia na primeira lista.
            if (item == null) {
                attributesFromOtherNode.add(new SimilarString(otherItem.getNodeValue()));
                attributesFromNode.add(new SimilarString(""));
            }
        }

        return attributesList;
    }

    public Node getNode() {
        return node;
    }

    @Override
    public String toString() {
        return node.toString();
    }
}

package gems.ic.uff.br.modelo.similar;

import com.sun.org.apache.xpath.internal.NodeSet;
import gems.ic.uff.br.modelo.*;

import java.util.*;

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
    public static float ATTRIBUTE_WEIGTH = 0.4f;
    public static float ELEMENT_VALUE_WEIGTH = 0.5f;
    public static float ELEMENT_NAME_WEIGTH = 1f;
    public static float ELEMENT_CHILDREN_WEIGTH = 0.6f;
    //    public static float ELEMENT_TYPE = 0.6;
    private Result result;


    public SimilarNode(Node node) {
        this.node = node;
        this.result = new Result(node);
    }

    @Override
    public Result similar(SimilarNode y) {
        Node otherNode = y.getNode();
        float similarity = 0;

        if (node == null || otherNode == null) {
        } else {
            similarity += elementsNameSimilarity(otherNode);

            //Se não houver similaridade no nome, então não são o mesmo elemento,
            //portanto, não é necessário continuar a calcular a similaridade.
            if (similarity != 0) {
                similarity += elementsValueSimilarity(otherNode);
                similarity += elementsAttributesSimilarity(otherNode);
                similarity += elementsChildrenSimilarity(otherNode);
            }
        }

        result.setSimilarity(similarity > 1 ? 1 : similarity);

        return result;
    }

    protected float elementsNameSimilarity(Node otherNode) {
        float similarity = 0;

        if (node.getNodeName().equals(otherNode.getNodeName())) {
            similarity = ELEMENT_NAME_WEIGTH;
        }

        return similarity;
    }

    protected float elementsValueSimilarity(Node otherNode) throws DOMException {
        float similarity = 0;

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

            if (nodeValue == null && otherNodeValue == null) {
                similarity = ELEMENT_VALUE_WEIGTH;
            } else {
                Node node;

                if (nodeValue != null && otherNodeValue != null) {
                    if (nodeValue.equals(otherNodeValue)) {
                        result.getNode().setTextContent(nodeValue);
                        similarity = ELEMENT_VALUE_WEIGTH;
                    } else {
                        node = ResultXML.createLeftNode("value");
                        node.setTextContent(nodeValue);
                        result.getNode().appendChild(node);

                        node = ResultXML.createRightNode("value");
                        node.setTextContent(otherNodeValue);
                        result.getNode().appendChild(node);
                    }
                } else {
                    if (nodeValue != null) {
                        node = ResultXML.createLeftNode("value");
                    } else {
                        node = ResultXML.createRightNode("value");
                    }

                    node.setTextContent(nodeValue);
                    result.getNode().appendChild(node);
                }
            }
        }

        return similarity;
    }

    protected float elementsAttributesSimilarity(Node otherNode) {
        float similarity = 0;

        if (!node.hasAttributes() && !otherNode.hasAttributes()) {
            similarity = ATTRIBUTE_WEIGTH;
        } else if (node.hasAttributes() && otherNode.hasAttributes()) {
            NamedNodeMap attributesFromNode = node.getAttributes();
            NamedNodeMap attributesFromOtherNode = otherNode.getAttributes();

            Map<String, List<Similar>> attributesMap = getAttributesFromNamedNodeMaps(attributesFromNode, attributesFromOtherNode);
            Iterator mapIterator = attributesMap.entrySet().iterator();

            while (mapIterator.hasNext()) {
                Map.Entry<String, List<Similar>> entry = (Map.Entry) mapIterator.next();
                similarity += new LcsString(entry.getValue().get(0).toString(), entry.getValue().get(1).toString()).similaridade();
            }

            similarity = similarity / attributesMap.keySet().size() * ATTRIBUTE_WEIGTH;
        }

        return similarity;
    }

    protected float elementsChildrenSimilarity(Node otherNode) {
        NodeList childNodes = node.getChildNodes();
        NodeList otherChildNodes = otherNode.getChildNodes();
        float similarity = 0;

        if (childNodes == null && otherChildNodes == null) {
            similarity = ELEMENT_CHILDREN_WEIGTH;

        } else if (childNodes != null && otherChildNodes != null) {
            NodeSet elementNodes = getElementNodes(childNodes);
            NodeSet otherElementNodes = getElementNodes(otherChildNodes);

            if ((elementNodes.size() == 0 && otherElementNodes.size() == 0)) {
                similarity = ELEMENT_CHILDREN_WEIGTH;
            } else if ((elementNodes.size() != 0 && otherElementNodes.size() != 0)) {
                HungarianList hungarianList = new HungarianList(elementNodes, otherElementNodes);
                similarity = hungarianList.similaridade() * ELEMENT_CHILDREN_WEIGTH;
            }
        }

        return similarity;
    }

    /**
     * Retornar os nós de um NodeList que são do tipo elemento.
     *
     * @param nodeList
     * @return
     * @see Node
     */
    protected NodeSet getElementNodes(NodeList nodeList) {
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
     *
     * @param nodeMap
     * @param otherNodeMap
     * @return
     */
    protected Map<String, List<Similar>> getAttributesFromNamedNodeMaps(NamedNodeMap nodeMap, NamedNodeMap otherNodeMap) {

        Map<String, List<Similar>> attributesMap = new TreeMap<String, List<Similar>>();

        for (int i = 0; i < nodeMap.getLength(); i++) {
            Node item = nodeMap.item(i);

            List<Similar> similarList = new ArrayList<Similar>(2);
            similarList.add(new SimilarString(item.getNodeValue()));
            similarList.add(new SimilarString(""));

            attributesMap.put(item.getNodeName(), similarList);
        }
        for (int i = 0; i < otherNodeMap.getLength(); i++) {
            Node item = otherNodeMap.item(i);

            List<Similar> similarList = attributesMap.get(item.getNodeName());
            if (similarList != null) {
                similarList.remove(1);
                similarList.add(new SimilarString(item.getNodeValue()));
            } else {
                similarList = new ArrayList<Similar>(2);
                similarList.add(new SimilarString(""));
                similarList.add(new SimilarString(item.getNodeValue()));

                attributesMap.put(item.getNodeName(), similarList);
            }
        }

        return attributesMap;
    }

    public Node getNode() {
        return node;
    }

    @Override
    public String toString() {
        return node.getNodeName();
    }

}

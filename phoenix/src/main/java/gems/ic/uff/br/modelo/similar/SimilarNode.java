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
    public static float ATTRIBUTE_WEIGTH = 0.25f;
    public static float ELEMENT_VALUE_WEIGTH = 0.25f;
    public static float ELEMENT_NAME_WEIGTH = 0.25f;
    public static float ELEMENT_CHILDREN_WEIGTH = 0.25f;
    //    public static float ELEMENT_TYPE = 0.6;

    public SimilarNode(Node node) {
        this.node = node;
    }

    @Override
    public Diff similar(SimilarNode y) {
        Node otherNode = y.getNode();
        
        Diff diff = new Diff(node, otherNode);
        float similarity = 0;

        if (node == null || otherNode == null) {
        } else {
            similarity += elementsNameSimilarity(otherNode);

            //Se não houver similaridade no nome, então não são o mesmo elemento,
            //portanto, não é necessário continuar a calcular a similaridade.
            if (similarity != 0) {
                similarity += elementsValueSimilarity(otherNode, diff);
                similarity += elementsAttributesSimilarity(otherNode, diff);
                similarity += elementsChildrenSimilarity(otherNode, diff);
            }
        }

        diff.setSimilarity(similarity);

        return diff;
    }

    protected float elementsNameSimilarity(Node otherNode) {
        float similarity = 0;

        if (node.getNodeName().equals(otherNode.getNodeName())) {
            similarity = ELEMENT_NAME_WEIGTH;
        }

        return similarity;
    }

    protected float elementsValueSimilarity(Node otherNode, Diff diff) throws DOMException {
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
                diff.setValue(nodeValue, otherNodeValue);

                if (nodeValue != null && otherNodeValue != null) {
                    similarity += new LcsString(nodeValue, otherNodeValue).similaridade() * ELEMENT_VALUE_WEIGTH;
                }
            }
        } else {
            similarity = ELEMENT_VALUE_WEIGTH;
        }

        return similarity;
    }

    protected float elementsAttributesSimilarity(Node otherNode, Diff diff) {
        float similarity = 0;

        if (!node.hasAttributes() && !otherNode.hasAttributes()) {
            similarity = ATTRIBUTE_WEIGTH;
        } else {
            if (node.hasAttributes() && otherNode.hasAttributes()) {
                NamedNodeMap attributesFromNode = node.getAttributes();
                NamedNodeMap attributesFromOtherNode = otherNode.getAttributes();

                Map<String, List<SimilarString>> attributesMap = getAttributesFromNamedNodeMaps(attributesFromNode, attributesFromOtherNode);
                Iterator mapIterator = attributesMap.entrySet().iterator();

                while (mapIterator.hasNext()) {
                    Map.Entry<String, List<SimilarString>> entry = (Map.Entry) mapIterator.next();

                    String attributeName = entry.getKey();
                    SimilarString firstElementAttributeValue = entry.getValue().get(0);
                    SimilarString secondElementAttributeValue = entry.getValue().get(1);

                    diff.addAttribute(attributeName, firstElementAttributeValue.toString(), secondElementAttributeValue.toString());
                    similarity += new LcsString(firstElementAttributeValue, secondElementAttributeValue).similaridade();
                }

                similarity = similarity / attributesMap.keySet().size() * ATTRIBUTE_WEIGTH;
            } else {

            }
        }

        return similarity;
    }

    protected float elementsChildrenSimilarity(Node otherNode, Diff diff) {
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
                hungarianList.addResultParent(diff);
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

    protected Map<String, List<SimilarString>> getAttributesFromNamedNodeMaps(NamedNodeMap nodeMap, NamedNodeMap otherNodeMap) {

        Map<String, List<SimilarString>> attributesMap = new TreeMap<String, List<SimilarString>>();

        for (int i = 0; i < nodeMap.getLength(); i++) {
            Node item = nodeMap.item(i);

            List<SimilarString> similarList = new ArrayList<SimilarString>(2);
            similarList.add(new SimilarString(item.getNodeValue()));
            similarList.add(new SimilarString(""));

            attributesMap.put(item.getNodeName(), similarList);
        }
        for (int i = 0; i < otherNodeMap.getLength(); i++) {
            Node item = otherNodeMap.item(i);

            List<SimilarString> similarList = attributesMap.get(item.getNodeName());
            if (similarList != null) {
                similarList.remove(1);
                similarList.add(new SimilarString(item.getNodeValue()));
            } else {
                similarList = new ArrayList<SimilarString>(2);
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

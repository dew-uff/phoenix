package gems.ic.uff.br.modelo.similar;

import com.sun.org.apache.xpath.internal.NodeSet;
import gems.ic.uff.br.modelo.*;

import java.util.*;

import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Classe que encapsula a classe Node do DOM, adicionando um comportamento
 * de similaridade a ela.
 */
public class SimilarNode extends Similar<SimilarNode> {

    private Node leftNode;
    public static float ATTRIBUTE_WEIGTH = 0.25f;
    public static float ELEMENT_VALUE_WEIGTH = 0.25f;
    public static float ELEMENT_NAME_WEIGTH = 0.25f;
    public static float ELEMENT_CHILDREN_WEIGTH = 0.25f;

    public SimilarNode(Node node) {
        this.leftNode = node;
    }

    @Override
    public Diff similar(SimilarNode y) {
        Node rightNode = y.getNode();

        Diff diff = new Diff(leftNode, rightNode);
        float similarity = 0;

        //nao sei pra que serve essa condiç
        if (leftNode == null || rightNode == null) {
            //no momento, o sistema nao esta protegendo isso
        } else {
            similarity += elementsNameSimilarity(rightNode);

            //Se não houver similaridade no nome, então não são o mesmo elemento,
            //portanto, não é necessário continuar a calcular a similaridade.
            if (similarity != 0) {
                similarity += elementsValueSimilarity(rightNode, diff);
                similarity += elementsAttributesSimilarity(rightNode, diff);
                similarity += elementsChildrenSimilarity(rightNode, diff);

            } else {
                diff = varreTodosSubelementosParaMostrar(diff, leftNode, "left");
            }
        }
        diff.setSimilarity(similarity);
        return diff;
    }

    protected float elementsNameSimilarity(Node rightNode) {
        float similarity = 0;

        if (leftNode.getNodeName().equals(rightNode.getNodeName())) {
            similarity = ELEMENT_NAME_WEIGTH;
        }

        return similarity;
    }

    protected float elementsValueSimilarity(Node rightNode, Diff diff) throws DOMException {
        float similarity = 0;

        if (leftNode.hasChildNodes() || rightNode.hasChildNodes()) {
            String leftNodeValue = null;
            String rightNodeValue = null;

            if (leftNode.hasChildNodes()) {
                //O valor do elemento é um de seus filhos.
                leftNodeValue = leftNode.getFirstChild().getNodeValue();
            }

            if (rightNode.hasChildNodes()) {
                //O valor do elemento é um de seus filhos.
                rightNodeValue = rightNode.getFirstChild().getNodeValue();
            }

            if (leftNodeValue == null && rightNodeValue == null) {
                similarity = ELEMENT_VALUE_WEIGTH;
            } else {
                diff.setValue(leftNodeValue, rightNodeValue);

                if (leftNodeValue != null && rightNodeValue != null) {
                    similarity += new LcsString(leftNodeValue, rightNodeValue).similaridade() * ELEMENT_VALUE_WEIGTH;
                }
            }
        } else {
            similarity = ELEMENT_VALUE_WEIGTH;
        }

        return similarity;
    }

    protected float elementsAttributesSimilarity(Node rightNode, Diff diff) {
        float similarity = 0;

        if (!leftNode.hasAttributes() && !rightNode.hasAttributes()) {
            similarity = ATTRIBUTE_WEIGTH;
        } else {
            if (leftNode.hasAttributes() && rightNode.hasAttributes()) {
                NamedNodeMap attributesFromLeftNode = leftNode.getAttributes();
                NamedNodeMap attributesFromRightNode = rightNode.getAttributes();

                Map<String, List<SimilarString>> attributesMap = getAttributesFromNamedNodeMaps(attributesFromLeftNode, attributesFromRightNode);
                Iterator mapIterator = attributesMap.entrySet().iterator();

                while (mapIterator.hasNext()) {
                    Map.Entry<String, List<SimilarString>> entry = (Map.Entry) mapIterator.next();

                    String attributeName = entry.getKey();
                    SimilarString leftElementAttributeValue = entry.getValue().get(0);
                    SimilarString rightElementAttributeValue = entry.getValue().get(1);

                    diff.addAttribute(attributeName, leftElementAttributeValue.toString(), rightElementAttributeValue.toString());
                    //Alguns atributos vem com seu conteudo vazio. Logo, esta
                    //condição é necessária senão pode ocorrer erro de divisao(NaN).
                    if (leftElementAttributeValue.getString().isEmpty()
                            && rightElementAttributeValue.getString().isEmpty()) {
                        similarity += 1.0f;
                    } else {
                        similarity += new LcsString(leftElementAttributeValue, rightElementAttributeValue).similaridade();
                    }
                }

                similarity = similarity / attributesMap.keySet().size() * ATTRIBUTE_WEIGTH;
            } else {
            }
        }

        return similarity;
    }

    protected float elementsChildrenSimilarity(Node rightNode, Diff diff) {

        float similarity = 0;

        if (!leftNode.hasChildNodes() && !rightNode.hasChildNodes()) {
            similarity = ELEMENT_CHILDREN_WEIGTH;
        } else {
            NodeSet leftSubElements = getElementNodes(leftNode.getChildNodes());
            NodeSet rightSubElements = getElementNodes(rightNode.getChildNodes());

            if ((leftSubElements.size() == 0 && rightSubElements.size() == 0)) {
                similarity = ELEMENT_CHILDREN_WEIGTH;
            } else if ((leftSubElements.size() != 0 && rightSubElements.size() != 0)) {
                HungarianList hungarianList = new HungarianList(leftSubElements, rightSubElements);
                diff = hungarianList.calcularSimilaridadeDosSubElementos(diff);
                similarity = hungarianList.similaridade() * ELEMENT_CHILDREN_WEIGTH;

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
        return leftNode;
    }

    @Override
    public String toString() {
        return leftNode.getNodeName();
    }

    private boolean isElementValue(String valor) {
        return (valor.contains("\n") && valor.contains("        ")) ? false : true;
    }

    private Diff varreTodosSubelementosParaMostrar(Diff diff, Node leftNode, String side) {
        Diff x = diff;
        Diff novoDiff = null;
        if (leftNode.hasChildNodes()) {
            NodeList subElementos = leftNode.getChildNodes();
            for (int i = 0; i < subElementos.getLength(); i++) {
                Node filho = subElementos.item(i);
                String sideElementValue = filho.getNodeValue();
                if (filho.getNodeType() == Node.TEXT_NODE
                        && sideElementValue != null
                        && isElementValue(sideElementValue)) {

                    Element valueNode = (Element) DiffXML.createNode("value");
                    valueNode.setAttributeNS(Diff.NAMESPACE, Diff.DIFF_PREFIX + side, sideElementValue);
                    x.getDiffNode().appendChild(valueNode);
                } else if (filho.getNodeType() == Node.ELEMENT_NODE) {
                    novoDiff = new Diff(filho);
                    novoDiff.setSimilarity(0);
                    novoDiff.addSideAttribute(side);
                    novoDiff = inserirAtributosNosElementos(novoDiff, filho);

                    if (filho.hasChildNodes()) {
                        novoDiff = varreTodosSubelementosParaMostrar(novoDiff, filho, side);
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

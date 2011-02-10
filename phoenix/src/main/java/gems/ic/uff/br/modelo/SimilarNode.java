package gems.ic.uff.br.modelo;

import org.w3c.dom.Node;

/**
 * Classe que encapsula a classe Node do DOM, adicionando um comportamento
 * de similaridade a ela.
 */
public class SimilarNode extends Similar<SimilarNode> {

    private Node node;
    public static final double ATTRIBUTE_WEIGTH = 0.5;
    public static final double ELEMENT_VALUE_WEIGTH = 0.5;
    public static final double ELEMENT_WEIGTH = 1;

    public SimilarNode(Node node) {
        this.node = node;
    }

    @Override
    public double similar(SimilarNode y) {
        Node otherNode = y.getNode();
        double similarity = 1;

        if (node.getNodeName().equals(otherNode.getNodeName())) {
            if (node.hasAttributes() || otherNode.hasAttributes()) {
                if (node.hasAttributes() && otherNode.hasAttributes()) {
                    //TODO: Usar um método de conjunto para medir a similaridade
//                    NamedNodeMap attributesFromNode = node.getAttributes();
//                    NamedNodeMap attributesFromOtherNode = otherNode.getAttributes();
                } else {
                    similarity -= ATTRIBUTE_WEIGTH;
                }
            } else {
                String nodeValue = node.getTextContent();
                String otherNodeValue = otherNode.getTextContent();

                if (nodeValue != null || otherNodeValue != null) {
                    if (nodeValue == null || otherNodeValue == null || !nodeValue.equals(otherNodeValue)) {
                        similarity -= ELEMENT_VALUE_WEIGTH;
                    }
                }
            }
        } else {
            similarity -= ELEMENT_WEIGTH;
        }

        return similarity;
    }

    public Node getNode() {
        return node;
    }

    @Override
    public String toString() {
        return node.toString();
    }
}

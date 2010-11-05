package modelo;

import org.w3c.dom.Node;

/**
 * Classe que encapsula a classe Node do DOM, adicionando um comportamento
 * de similaridade a ela.
 */
public class SimilarNode extends Similar<SimilarNode> {

    public static final double ATTRIBUTE_WEIGTH = 0.5;
    private Node node;

    public SimilarNode(Node node) {
        this.node = node;
    }

    @Override
    public double similar(SimilarNode y) {
        Node otherNode = y.getNode();

        if (node.getNodeName().equals(otherNode.getNodeName())) {
            if (node.hasAttributes() || otherNode.hasAttributes()) {
                if (node.hasAttributes() && otherNode.hasAttributes()) {
                    //TODO: Usar um método de conjunto para medir a similaridade
//                    NamedNodeMap attributesFromNode = node.getAttributes();
//                    NamedNodeMap attributesFromOtherNode = otherNode.getAttributes();
                    return 1;
                } else {
                    return 0.5;
                }
            } else {
                return 1;
            }
        } else {
            return 0;
        }
    }

    public Node getNode() {
        return node;
    }

    @Override
    public String toString() {
        return node.toString();
    }
}

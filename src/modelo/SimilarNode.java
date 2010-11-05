package modelo;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

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
                    NamedNodeMap attributesFromNode = node.getAttributes();
                    NamedNodeMap attributesFromOtherNode = otherNode.getAttributes();
                    
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
}

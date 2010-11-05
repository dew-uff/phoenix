package modelo;

import org.w3c.dom.Node;

public class SimilarNode extends Similar<SimilarNode> {

    private Node node;

    public SimilarNode(Node node) {
        this.node = node;
    }

    @Override
    public double similar(SimilarNode y) {
        Node otherNode = y.getNode();
        
        if (node.getNodeName().equals(otherNode.getNodeName())) {
            return 1;
        } else {
            return 0.1;
        }
    }

    public Node getNode() {
        return node;
    }
}

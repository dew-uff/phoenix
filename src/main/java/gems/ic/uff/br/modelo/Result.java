package gems.ic.uff.br.modelo;

import org.w3c.dom.Node;

public class Result {
    private float similarity;
    private Node node;

    public Result(float similarity) {
        this.similarity = similarity;
    }

    public Result(Node node) {
        this.node = ResultXML.getInstance().getDocument().createElement(node.getNodeName());
    }

    public float getSimilarity() {
        return similarity;
    }

    public void setSimilarity(float similarity) {
        this.similarity = similarity;
    }

    public Node getNode() {
        return node;
    }
}

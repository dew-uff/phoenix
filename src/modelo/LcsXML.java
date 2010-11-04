package modelo;

import org.w3c.dom.Node;
import util.XML;

public class LcsXML extends LongestCommonSubsequence<Node> {

    private XML x;
    private XML y;

    public LcsXML(XML from, XML to) {
        this.x = from;
        this.y = to;
    }

    @Override
    protected int lengthOfX() {
        return x.getDocument().getDocumentElement().getChildNodes().getLength();
    }

    @Override
    protected int lengthOfY() {
        return y.getDocument().getDocumentElement().getChildNodes().getLength();
    }

    @Override
    protected Node valueOfX(int index) {
        return x.getDocument().getDocumentElement().getChildNodes().item(index);
    }

    @Override
    protected Node valueOfY(int index) {
        return y.getDocument().getDocumentElement().getChildNodes().item(index);
    }

    @Override
    protected boolean equals(Node x1, Node y1) {
        return (null == x1 && null == y1) || x1.getNodeName().equals(y1.getNodeName());
    }
}

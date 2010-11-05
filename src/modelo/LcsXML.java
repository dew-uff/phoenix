package modelo;

public class LcsXML extends LongestCommonSubsequence<SimilarNode> {

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
    protected SimilarNode valueOfX(int index) {
        return new SimilarNode(x.getDocument().getDocumentElement().getChildNodes().item(index));
    }

    @Override
    protected SimilarNode valueOfY(int index) {
        return new SimilarNode(y.getDocument().getDocumentElement().getChildNodes().item(index));
    }
}

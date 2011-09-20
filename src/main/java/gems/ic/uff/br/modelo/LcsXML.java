package gems.ic.uff.br.modelo;

import gems.ic.uff.br.modelo.algorithm.LongestCommonSubsequence;
import gems.ic.uff.br.modelo.similar.SimilarNode;
import gems.ic.uff.br.modelo.similar.Similar;

public class LcsXML extends LongestCommonSubsequence<Similar> {
    private XML x;
    private XML y;
    private XML diffXML;

    public LcsXML(XML from, XML to) {
        this.x = from;
        this.y = to;
        this.diffXML = calculateDiff();
    }
    
    private XML calculateDiff() {
        return null;
    }

    @Override
    protected int lengthOfX() {
        return x.getDocument().getDocumentElement().getChildNodes().getLength() + 1; //+1 Elemento root
    }

    @Override
    protected int lengthOfY() {
        return y.getDocument().getDocumentElement().getChildNodes().getLength() + 1; //+1 Elemento Root
    }

    @Override
    protected SimilarNode valueOfX(int index) {
        if (index == 0) {
            return new SimilarNode(x.getDocument().getDocumentElement()); //Root
        } else {
            return new SimilarNode(x.getDocument().getDocumentElement().getChildNodes().item(index - 1)); //Tem que diminuir de 1 por causa do root (+1)
        }
    }

    @Override
    protected SimilarNode valueOfY(int index) {
        if (index == 0) {
            return new SimilarNode(y.getDocument().getDocumentElement()); //Root
        } else {
            return new SimilarNode(y.getDocument().getDocumentElement().getChildNodes().item(index - 1)); //Tem que diminuir de 1 por causa do root (+1)
        }
    }
}

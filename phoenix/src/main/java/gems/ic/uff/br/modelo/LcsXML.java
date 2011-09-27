package gems.ic.uff.br.modelo;

import com.sun.org.apache.xpath.internal.NodeSet;
import gems.ic.uff.br.modelo.algorithm.LongestCommonSubsequence;
import gems.ic.uff.br.modelo.similar.SimilarNode;
import gems.ic.uff.br.modelo.similar.Similar;
import org.w3c.dom.Node;

public class LcsXML extends LongestCommonSubsequence<Similar> {

    private XML diffXML;
    private NodeSet x;
    private NodeSet y;

    public LcsXML(XML from, XML to) {
        this.x = SimilarNode.getElementNodes(from.getDocument().getChildNodes());
        this.y = SimilarNode.getElementNodes(to.getDocument().getChildNodes());
        this.diffXML = calculateDiff();
    }

    private XML calculateDiff() {
        DiffXML.restart();
        diffXML = DiffXML.getInstance();

        float[][] result = resultWithSimilarity();

        for (int i = 0; i < result.length; i++) {
            if (result[i][0] < lengthOfX() && result[i][1] < lengthOfY()) {
                SimilarNode firstNode = valueOfX((int) result[i][0]);
                Diff otherDiff = firstNode.similar((SimilarNode) valueOfY((int) result[i][1])); //TODO: Aqui estamos refazendo o diff.

                diffXML.addChildren(otherDiff);
            } else {
                if (result[i][0] < lengthOfX()) {
                    //TODO: Adicionar os elementos que não são similares.
//                    diff.addChildren(((SimilarNode) x.get(result[i][0])).getDiff());
                } else {
                    //TODO: Adicionar os elementos que não são similares.
//                    diff.addChildren(((SimilarNode) y.get(result[i][1])).getDiff());
                }
            }
        }

        return diffXML;
    }

    @Override
    protected int lengthOfX() {
        return x.getLength();
    }

    @Override
    protected int lengthOfY() {
        return y.getLength();
    }

    @Override
    protected SimilarNode valueOfX(int index) {
        return new SimilarNode(x.item(index));
    }

    @Override
    protected SimilarNode valueOfY(int index) {
        return new SimilarNode(y.item(index));
    }

    public XML getDiffXML() {
        return diffXML;
    }

    public float similaridade() {
        float similarity = 0;

        if (diffXML.getDocument().getFirstChild() != null) {
            if (diffXML.getDocument().getFirstChild().hasAttributes()) {
                Node similarityNode = diffXML.getDocument().getFirstChild().getAttributes().getNamedItemNS("diff", "similarity");

                if (similarityNode != null) {
                    similarity = new Float(similarityNode.getNodeValue());
                }
            }
        }

        return similarity;
    }
}

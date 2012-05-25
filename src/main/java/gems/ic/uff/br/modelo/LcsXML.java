package gems.ic.uff.br.modelo;

import com.sun.org.apache.xpath.internal.NodeSet;
import gems.ic.uff.br.modelo.algorithm.LongestCommonSubsequence;
import gems.ic.uff.br.modelo.similar.Similar;
import gems.ic.uff.br.modelo.similar.SimilarNode;
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

        //pega o elemento raiz do documento esquerdo 
        SimilarNode leftRoot = (SimilarNode) valueOfX(0);
        //pega o elemento raiz do documento direita
        SimilarNode rightRoot = (SimilarNode) valueOfY(0);
        
        //compara a similaridade entre os documentos
        Diff otherDiff = leftRoot.similar(rightRoot);
        diffXML.addChildren(otherDiff);
        
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

    @Override
    public float similaridade() {
        float similarity = 0;

        if (diffXML.getDocument().getFirstChild() != null) {
            if (diffXML.getDocument().getFirstChild().hasAttributes()) {
                Node similarityNode = diffXML.getDocument().getFirstChild().getAttributes().getNamedItem("diff:similarity");

                if (similarityNode != null) {
                    similarity = new Float(similarityNode.getNodeValue());
                }
            }
        }

        return similarity;
    }
}

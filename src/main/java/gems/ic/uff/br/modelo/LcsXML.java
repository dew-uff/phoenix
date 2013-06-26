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
    private NodeSet ancestral;
    private boolean bConflito;
    
    public LcsXML(XML from, XML to) {
        this.x = SimilarNode.getElementNodes(from.getDocument().getChildNodes());
        this.y = SimilarNode.getElementNodes(to.getDocument().getChildNodes());
        this.diffXML = calculateDiff();
    }
    
    public LcsXML(XML ancestral, XML son1, XML son2,boolean bMerge) {
        this.x = SimilarNode.getElementNodes(son1.getDocument().getChildNodes());
        this.y = SimilarNode.getElementNodes(son2.getDocument().getChildNodes());
        this.ancestral = SimilarNode.getElementNodes(ancestral.getDocument().getChildNodes());
        
        if(!bMerge)
         this.diffXML = calculateThreeWayDiff();
        else
          this.diffXML = calculateThreeWayMerge();  
    }
    
    private XML calculateThreeWayDiff() {
        
        DiffXML.restart();
        diffXML = DiffXML.getInstance();

        //pega o elemento raiz do documento esquerdo 
        SimilarNode son1 = (SimilarNode) valueOfX(0);
        //pega o elemento raiz do documento direita
        SimilarNode son2 = (SimilarNode) valueOfY(0);
        
        SimilarNode ancestralRoot = (SimilarNode) valueOfAncestral(0);
        
        //compara a similaridade entre os documentos
        ThreeWayDiff otherDiff = ancestralRoot.similarAncestral(son1,son2);
        diffXML.addChildren(otherDiff);
        
        return diffXML;
    }
    
    private XML calculateThreeWayMerge() {
        
        DiffXML.restart();
        diffXML = DiffXML.getInstance();

        //pega o elemento raiz do documento esquerdo 
        SimilarNode son1 = (SimilarNode) valueOfX(0);
        //pega o elemento raiz do documento direita
        SimilarNode son2 = (SimilarNode) valueOfY(0);
        
        SimilarNode ancestralRoot = (SimilarNode) valueOfAncestral(0);
        
      
        ThreeWayDiff otherDiff = ancestralRoot.mergeAncestral(son1,son2);
        bConflito = otherDiff.getConflito();
        diffXML.addChildren(otherDiff);
        
        return diffXML;
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
    
    protected int lengthOfAncestral() {
        return ancestral.getLength();
    }
    
    @Override
    protected SimilarNode valueOfX(int index) {
        return new SimilarNode(x.item(index));
    }

    @Override
    protected SimilarNode valueOfY(int index) {
        return new SimilarNode(y.item(index));
    }
   
    protected SimilarNode valueOfAncestral(int index) {
        return new SimilarNode(ancestral.item(index));
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

    /**
     * @return the bConflito
     */
    public boolean isbConflito() {
        return bConflito;
    }

    /**
     * @param bConflito the bConflito to set
     */
    public void setbConflito(boolean bConflito) {
        this.bConflito = bConflito;
    }
}

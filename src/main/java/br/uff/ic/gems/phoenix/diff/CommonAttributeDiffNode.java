package br.uff.ic.gems.phoenix.diff;

import br.uff.ic.gems.phoenix.similarity.ElementSimilarity;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class CommonAttributeDiffNode extends AttributeDiffNode {

    private Node left, right;
    
    public CommonAttributeDiffNode(Node left, Node right) {
        this.left = left;
        this.right = right;
    }
    
    @Override
    public int getType() {
        return DiffNode.TYPE_COMMONATTRIBUTE;
    }

    @Override
    public void linkToElement(Document doc, Element parent) {
        
        String valueLeft = left.getNodeValue();
        //String valueRight = right.getNodeValue();
        if (ElementSimilarity.MAXIMUM_SIMILARITY == getSimilarity()) {
            parent.setAttribute(left.getNodeName(), valueLeft);
        } else {
            parent.setAttributeNS(DIFF_NAMESPACE_LEFT, LEFT_PREFIX + left.getNodeName(), left.getNodeValue());
            parent.setAttributeNS(DIFF_NAMESPACE_RIGHT, RIGHT_PREFIX + right.getNodeName(), right.getNodeValue());
        }
    }
}

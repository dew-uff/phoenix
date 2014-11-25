package br.uff.ic.gems.phoenix.diff;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class OneSideElementDiffNode extends ElementDiffNode {

    private Node node = null;
    private int side = DiffNode.SIDE_LEFT;
    
    public OneSideElementDiffNode(Node node, int side) {
        this.node = node;
        this.side = side;
    }
    
    public int getSide() {
        return side;
    }
    
    public void setSide(int side) {
        this.side = side;
    }

    @Override
    public int getType() {
        return DiffNode.TYPE_ONESIDEELEMENT;
    }

    @Override
    public Element toXmlTree(Document doc) {
        Element elm = (Element) doc.importNode(node, true);
        elm.setAttributeNS(DiffNode.DIFF_NAMESPACE, DiffNode.DIFF_SIDE, (side==DiffNode.SIDE_LEFT)?"left":"right");
        elm.setAttributeNS(DiffNode.DIFF_NAMESPACE, DiffNode.DIFF_SIMILARITY, Double.toString(similarity));
        return elm;
    }
}

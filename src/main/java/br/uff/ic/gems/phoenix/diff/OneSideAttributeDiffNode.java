package br.uff.ic.gems.phoenix.diff;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class OneSideAttributeDiffNode extends AttributeDiffNode {

    private Node node = null;
    private int side = SIDE_LEFT;
    
    public OneSideAttributeDiffNode(Node node, int side) {
        this.node = node;
        this.side = side;
    }
    
    @Override
    public int getType() {
        return TYPE_ONESIDEATTRIBUTE;
    }

    @Override
    public void linkToElement(Document doc, Element parent) {
        String ns = (side == SIDE_LEFT)?DIFF_NAMESPACE_LEFT:DIFF_NAMESPACE_RIGHT;
        String preffix = (side == SIDE_LEFT)?LEFT_PREFIX:RIGHT_PREFIX;
        parent.setAttributeNS(ns, preffix + this.node.getNodeName(), this.node.getNodeValue());
    }
}

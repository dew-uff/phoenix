package br.uff.ic.gems.phoenix.diff;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class AttributeDiffNode extends DiffNode {
    
    public abstract void linkToElement(Document doc, Element node);
}

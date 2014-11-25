package br.uff.ic.gems.phoenix.diff;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class ElementDiffNode extends DiffNode {
    
    public abstract Element toXmlTree(Document doc);
}

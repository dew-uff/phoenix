/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gems.ic.uff.br.newView;

import javax.swing.text.StyledEditorKit;
import javax.swing.text.ViewFactory;

public class XmlEditorKit extends StyledEditorKit {
 
    private static final long serialVersionUID = 2969169649596107757L;
    private ViewFactory xmlViewFactory;
 
    public XmlEditorKit() {
        xmlViewFactory = new XmlViewFactory();
    }
     
    @Override
    public ViewFactory getViewFactory() {
        return xmlViewFactory;
    }
 
    @Override
    public String getContentType() {
        return "text/xml";
    }
}

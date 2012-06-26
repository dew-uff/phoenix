/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gems.ic.uff.br.newView;

import javax.swing.JTextPane;
import xmlEditorKit.XMLEditorKit;



/**
 *
 * @author Douglas
 */
public class XmlTextPane extends JTextPane {
 
    private static final long serialVersionUID = 6270183148379328084L;
 
    public XmlTextPane() {
         
        // Set editor kit
        this.setEditorKitForContentType("text/xml", new XMLEditorKit());
        this.setContentType("text/xml");
    }
     
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gems.ic.uff.br.newView;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import gems.ic.uff.br.modelo.LcsXML;
import gems.ic.uff.br.modelo.XML;
import gems.ic.uff.br.newView.MyTabbedPane;
import gems.ic.uff.br.view.VisualizarDiffXML;
import gems.ic.uff.br.view.VisualizarThreeWayDiffXML;
import javax.swing.JTextArea;

/**
 *
 * @author Leonardo
 */
public class DiffThreeWayPanel extends JPanel {
    
        XML xml1, xml2,xmlPai;
        String xmlAncestral;
        LcsXML lcsXML;
        VisualizarThreeWayDiffXML diffGrafico;
        JPanel treesPanel;
        TitledBorder title;
        
         public DiffThreeWayPanel (){
       
         }
        
        public DiffThreeWayPanel (MyTabbedPane tabbedPane){
        
            
        xml1 = new XML(tabbedPane.getDocumentsPanel().getTextAreaDoc1().getText());
        xml2 = new XML(tabbedPane.getDocumentsPanel().getTextAreaDoc2().getText());
        xmlPai = new XML(tabbedPane.getDocumentsPanel().getTextAreaAncestral().getText());  
       
        lcsXML = new LcsXML(xmlPai,xml1, xml2,false);
        
        createTitle();
        diffGrafico = new VisualizarThreeWayDiffXML(lcsXML.getDiffXML().toString());
        diffGrafico.setBorder(new BevelBorder(BevelBorder.LOWERED));
        diffGrafico.setBorder(title);
        setLayout(new BorderLayout());
        add(diffGrafico, BorderLayout.CENTER);
    }
        
        
         private void createTitle() {
        title = BorderFactory.createTitledBorder("XML Difference Tree With Comum Ancestral");
        title.setTitleJustification(TitledBorder.LEFT);
        title.setTitleColor(new Color(1, 122, 168));
    }
     
     public VisualizarThreeWayDiffXML getDiffGrafico(){
         return diffGrafico;
     }
     
      public void setTextAncestral(String str){
        xmlAncestral = str;
    }
      
       public String getTextAncestral(){
        return xmlAncestral ;
    }
    
}

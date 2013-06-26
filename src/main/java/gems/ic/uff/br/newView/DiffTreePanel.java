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
import gems.ic.uff.br.view.VisualizarDiffXML;

/**
 *
 * @author Douglas
 */
public class DiffTreePanel extends JPanel {
    
    XML xml1, xml2;
    LcsXML lcsXML;
    VisualizarDiffXML diffGrafico;
    JPanel treesPanel;
    TitledBorder title;
    
    public DiffTreePanel (){
       
    }
    
    /*public DiffTreePanel (MyTabbedPane tabbedPane){
        
        XML xmlPai = new XML("F:\\ESTUDOS\\MESTRADO\\GDSE\\Pai.xml");
        xml1 = new XML("F:\\ESTUDOS\\MESTRADO\\GDSE\\Filho1.xml");
        xml2 = new XML("F:\\ESTUDOS\\MESTRADO\\GDSE\\Filho2.xml");
              
        lcsXML = new LcsXML(xmlPai,xml1, xml2);
                
        createTitle();
        diffGrafico = new VisualizarDiffXML(lcsXML.getDiffXML().toString());
        diffGrafico.setBorder(new BevelBorder(BevelBorder.LOWERED));
        diffGrafico.setBorder(title);
        setLayout(new BorderLayout());
        add(diffGrafico, BorderLayout.CENTER);
    }*/
    
    public DiffTreePanel (MyTabbedPane tabbedPane){
        xml1 = new XML(tabbedPane.getDocumentsPanel().getTextAreaDoc1().getText());
        xml2 = new XML(tabbedPane.getDocumentsPanel().getTextAreaDoc2().getText());
              
        lcsXML = new LcsXML(xml1, xml2);
        
        createTitle();
        diffGrafico = new VisualizarDiffXML(lcsXML.getDiffXML().toString());
        diffGrafico.setBorder(new BevelBorder(BevelBorder.LOWERED));
        diffGrafico.setBorder(title);
        setLayout(new BorderLayout());
        add(diffGrafico, BorderLayout.CENTER);
    }
    
     private void createTitle() {
        title = BorderFactory.createTitledBorder("XML Difference Tree");
        title.setTitleJustification(TitledBorder.LEFT);
        title.setTitleColor(new Color(1, 122, 168));
    }
     
     public VisualizarDiffXML getDiffGrafico(){
         return diffGrafico;
     }
}

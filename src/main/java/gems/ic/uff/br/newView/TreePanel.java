/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gems.ic.uff.br.newView;

import gems.ic.uff.br.view.VisualizarXML;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Douglas
 */
public class TreePanel extends JPanel {
    
    JSplitPane splitPane;
    TitledBorder title1;
    TitledBorder title2;
    VisualizarXML v1, v2;
    
    public TreePanel(){
        
    }
    public TreePanel(MyTabbedPane tabbedPane){
        v1 = new VisualizarXML(tabbedPane.getDocumentsPanel().getTextAreaDoc1().getText(), Color.RED);
        v2 = new VisualizarXML(tabbedPane.getDocumentsPanel().getTextAreaDoc2().getText(), Color.GREEN);
        createTitle();
        v1.setBorder(title1);
        v2.setBorder(title2);
        
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, v1, v2);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(265);
        setLayout(new BorderLayout());
        add(splitPane, BorderLayout.CENTER);
    }
    
    private void createTitle() {
        title1 = BorderFactory.createTitledBorder("XML Tree of The Document 1");
        title1.setTitleJustification(TitledBorder.LEFT);
        title1.setTitleColor(new Color(1, 122, 168));
        title2 = BorderFactory.createTitledBorder("XML Tree of The Document 2");
        title2.setTitleJustification(TitledBorder.LEFT);
        title2.setTitleColor(new Color(1, 122, 168));
    }
    
    public VisualizarXML getVisualizarXML1(){
        return v1;
    }
    public VisualizarXML getVisualizarXML2(){
        return v2;
    }
    
}

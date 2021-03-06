/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gems.ic.uff.br.newView;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.*;

/**
 *
 * @author Douglas
 */
public class NewInitialScreen extends JFrame {
    
    BorderLayout layout;
    JScrollPane scrollPane;
    MyTree tree;
    ButtonPanel buttonsPanel;
    MyTabbedPane tabbedPane;
 
    static JSplitPane splitPane;
    
    
     public NewInitialScreen() {
    
        super("PHOENIX");       
        setSize(800, 600);
        setLocationRelativeTo(null);
        layout = new BorderLayout();
        setLayout(layout);

        setIconImage(new ImageIcon(getClass().getResource("images/Phoenix.png")).getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        tabbedPane = new MyTabbedPane();
        tree = new MyTree("",MyTree.TIPO_DIFF);
 
        buttonsPanel = new ButtonPanel(this, tree, tabbedPane);
        
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, null, tabbedPane.getJTabbedPane());
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(230);
        
        Dimension minimumSize = new Dimension(50, 50);
        tree.getJTree().setMinimumSize(minimumSize);

        add(buttonsPanel, BorderLayout.NORTH);
        add(splitPane, BorderLayout.CENTER);
        
        setVisible(true);
     }  
}

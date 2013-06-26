/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gems.ic.uff.br.newView;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;
/**
 *
 * @author Douglas
 */
class MyTabbedPane{
    
    JTabbedPane tabbedPane;
    ImageIcon iconDoc, iconTree, iconDiffTree, iconSimilarity;
    DocumentsPanel documentsPanel;
    TreePanel treePanel;
    DiffTreePanel diffTreePanel;
    DiffThreeWayPanel threeWayDiffTreePanel;
    DiffThreeWayPanel threeWayMergeTreePanel;
    SimilarityPanel similarityPanel;
    
    public MyTabbedPane(){
        
        tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(Color.white);
        
        iconDoc = new ImageIcon("images/documents.png");
        iconTree = new ImageIcon("images/tree.png");
        iconDiffTree = new ImageIcon("images/diff.png");
        iconSimilarity = new ImageIcon("images/similarityIcon.png");
        
        documentsPanel = new DocumentsPanel();
        treePanel = new TreePanel();
        diffTreePanel = new DiffTreePanel();
        
        threeWayDiffTreePanel = new DiffThreeWayPanel();
        threeWayMergeTreePanel= new DiffThreeWayPanel();
        
        tabbedPane.addTab("Documents", iconDoc, documentsPanel,"Documents XML");
        tabbedPane.addTab("Trees", iconTree, treePanel,"XML Trees");
        tabbedPane.addTab("Diff Tree", iconDiffTree, diffTreePanel,"Difference Tree");
        tabbedPane.addTab("Three Way Diff", iconDiffTree, threeWayDiffTreePanel,"Difference tree with comum ancestral");
        tabbedPane.addTab("Three Way Merge", iconDiffTree, threeWayMergeTreePanel,"Merge Tree versions with comum ancestral");
        tabbedPane.addTab("Similarity", iconSimilarity, similarityPanel,"XMLs Similarity");
        
        
        tabbedPane.setEnabledAt(1, false);
        tabbedPane.setEnabledAt(2, false);
        tabbedPane.setEnabledAt(3, false);
        tabbedPane.setEnabledAt(4, false);
        tabbedPane.setEnabledAt(5, false);
    }
    
     public JTabbedPane getJTabbedPane(){
        return tabbedPane;
    }
     
     public DocumentsPanel getDocumentsPanel(){
        return documentsPanel;
     }
     public TreePanel getTreePanel(){
        return treePanel;
     }
     public DiffThreeWayPanel getThreeWayDiffTreePanel(){
        return threeWayDiffTreePanel;
     }
     
      public DiffThreeWayPanel getThreeWayMergeTreePanel(){
        return threeWayMergeTreePanel;
     }
     
     
     public DiffTreePanel getdiffTreePanel(){
        return diffTreePanel;
     }
     
     public int getSelectedIndex(){
         return tabbedPane.getSelectedIndex();
     }
    
}


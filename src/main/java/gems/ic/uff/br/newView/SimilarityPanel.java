/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gems.ic.uff.br.newView;

import gems.ic.uff.br.Exception.LcsXMLFilesException;
import gems.ic.uff.br.modelo.LcsXMLFiles;
import gems.ic.uff.br.view.DrawSimilarityGraph;
import gems.ic.uff.br.view.SimilarityDiff;
import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

/**
 *
 * @author tiago
 */
class SimilarityPanel extends JPanel{
    
    String directoryPath;
    JPanel dsgVvPanel;
    JSlider jSliderSimilarity;
    JLabel jSliderSimilarityLabel;
    LcsXMLFiles lcs;
    DrawSimilarityGraph dsg;
    TitledBorder title;
    private Boolean choosenFolderEmpty = true;
    
    public SimilarityPanel(){
        createTitle();
        createJsSlider();
        chooseFilesAndDrawSimilarityGraph();
        createListener();

        this.setBorder(new BevelBorder(BevelBorder.LOWERED));
        this.setBorder(title);
        
        this.add(jSliderSimilarity);
        this.add(jSliderSimilarityLabel);
        this.add(dsgVvPanel);
    }
    
    private void createTitle() {
        title = BorderFactory.createTitledBorder("XML Similarity Graph");
        title.setTitleJustification(TitledBorder.LEFT);
        title.setTitleColor(new Color(1, 122, 168));
    }
    
    private void createJsSlider(){
        jSliderSimilarity = new JSlider();
        jSliderSimilarity.setValue(50);
        jSliderSimilarityLabel = new JLabel("50%");
    }
    
    private void chooseFilesAndDrawSimilarityGraph(){
        JFileChooser caminho = new JFileChooser();
        caminho.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        if (caminho.showOpenDialog(this) == javax.swing.JFileChooser.APPROVE_OPTION) {
            directoryPath = caminho.getSelectedFile().toString();
        }
        
        lcs = new LcsXMLFiles(directoryPath);
        
        try {
            lcs.similarityDirectory();
        } catch (LcsXMLFilesException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } catch (FileNotFoundException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } catch (IOException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        
        //Verify if choosen path is directory or not empty
        File choosenDirectory = new File(directoryPath);
        if(choosenDirectory != null && choosenDirectory.isDirectory()){
            choosenFolderEmpty = choosenDirectory.listFiles().length == 0;
        }
        
        dsg = new DrawSimilarityGraph();
        dsg.draw(lcs.getSimilarityMatrix(), lcs.getFiles(), Float.parseFloat((jSliderSimilarity.getValue() / 100.00) + ""));
        
        dsgVvPanel = new JPanel();
        dsgVvPanel.add(dsg.getVv());
    }
    
    public void openXMLs(){
        JFileChooser caminho = new JFileChooser();
        caminho.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        if (caminho.showOpenDialog(this) == javax.swing.JFileChooser.APPROVE_OPTION) {
            directoryPath = caminho.getSelectedFile().toString();
        }

        try {

            lcs = new LcsXMLFiles(directoryPath);
            try {
                lcs.similarityDirectory();
            } catch (LcsXMLFilesException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
                return;
            }
            
            dsg = new DrawSimilarityGraph();
            dsg.draw(lcs.getSimilarityMatrix(), lcs.getFiles(), Float.parseFloat((jSliderSimilarity.getValue() / 100.00) + ""));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SimilarityDiff.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SimilarityDiff.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void createListener(){
        jSliderSimilarity.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSliderSimilarityLabel.setText(jSliderSimilarity.getValue() + "%");
                
                dsg = new DrawSimilarityGraph();
                dsg.draw(lcs.getSimilarityMatrix(), lcs.getFiles(), Float.parseFloat((jSliderSimilarity.getValue() / 100.00) + ""));
                
                //Rerender graph
                remove(dsgVvPanel);
                
                dsgVvPanel = new JPanel();
                dsgVvPanel.add(dsg.getVv());
                
                add(dsgVvPanel);
            }
        });
    }
    
    public Boolean isChoosenDirectoryEmpty(){
        return choosenFolderEmpty;
    }
}

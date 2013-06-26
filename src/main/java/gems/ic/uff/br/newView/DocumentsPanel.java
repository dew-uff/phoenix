/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gems.ic.uff.br.newView;

import gems.ic.uff.br.xmlEditorKit.XMLEditorKit;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Douglas
 */
public class DocumentsPanel extends JPanel {
    
    JTextArea xmlComparable1, xmlComparable2,xmlAncestral;
    JEditorPane xmlComparable1Edit, xmlComparable2Edit;
    JScrollPane scrollXML1, scrollXML2;
    JSplitPane splitPane;
    TitledBorder title1, title2;
    Boolean changed;
    
    public DocumentsPanel(){
        
        xmlComparable1 = new JTextArea();//abrir algum documento aqui, se quiser.
        xmlComparable2 = new JTextArea();
        xmlAncestral= new JTextArea();
        xmlComparable1Edit = new JEditorPane();
        xmlComparable2Edit = new JEditorPane();
        
        changed = false;
        createListener();
        createTitle();
        
        xmlComparable1Edit.setBorder(title1);
        xmlComparable1Edit.setEditorKit(new XMLEditorKit());
        xmlComparable2Edit.setBorder(title2);
        xmlComparable2Edit.setEditorKit(new XMLEditorKit());
        
        xmlComparable1.setBorder(title1);
        xmlComparable2.setBorder(title2);
        
        scrollXML1 = new JScrollPane();
        scrollXML2 = new JScrollPane();
        scrollXML1.setViewportView(xmlComparable1);
        scrollXML2.setViewportView(xmlComparable2);
//        scrollXML1.setViewportView(xmlComparable1Edit);
//        scrollXML2.setViewportView(xmlComparable2Edit);
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollXML1, scrollXML2);
        splitPane.setDividerLocation(250);
        splitPane.setOneTouchExpandable(true);
        configureLayout();
    }
    
    private void configureLayout(){
        setLayout(new BorderLayout());
        add(splitPane,BorderLayout.CENTER);
    }

    private void createTitle() {
        title1 = BorderFactory.createTitledBorder("Documento 1");
        title1.setTitleJustification(TitledBorder.LEFT);
        title1.setTitleColor(new Color(1, 122, 168));
        title2 = BorderFactory.createTitledBorder("Documento 2");
        title2.setTitleJustification(TitledBorder.LEFT);
        title2.setTitleColor(new Color(1, 122, 168));
    }
    
    public void changeScrollToText(){
        scrollXML1.setViewportView(xmlComparable1);
        scrollXML2.setViewportView(xmlComparable2);
        
    }
    public void changeScrollToEdit(){
        scrollXML1.setViewportView(xmlComparable1Edit);
        scrollXML2.setViewportView(xmlComparable2Edit);
    }
    
    public void updateEditPane(){
        xmlComparable1Edit.setText(xmlComparable1.getText());
        xmlComparable2Edit.setText(xmlComparable2.getText());
    }
    
    public JTextArea getTextAreaDoc1(){
        return xmlComparable1;
    }
    
    public JTextArea getTextAreaDoc2(){
        return xmlComparable2;
    }
    
     public JTextArea getTextAreaAncestral(){
        return xmlAncestral;
    }
    
    private void createListener() {
        xmlComparable1.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                
            }

            @Override
            public void keyPressed(KeyEvent e) {
                changed = true;
            }

            @Override
            public void keyReleased(KeyEvent e) {
               
            }
        });
        xmlComparable2.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                
            }

            @Override
            public void keyPressed(KeyEvent e) {
                changed = true;
            }

            @Override
            public void keyReleased(KeyEvent e) {
               
            }
        });
    }
}

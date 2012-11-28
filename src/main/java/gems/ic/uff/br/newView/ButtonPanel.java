package gems.ic.uff.br.newView;

import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;
import edu.uci.ics.jung.visualization.control.ScalingControl;
import gems.ic.uff.br.settings.SettingsHelper;
import gems.ic.uff.br.xmlEditorKit.XMLEditorKit;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.tree.DefaultMutableTreeNode;

/*
 * PainelBotoes.java
 *
 * Painel onde os botoes estão contidos com layout Group.
 *
 * @author douglas
 */

public class ButtonPanel extends JPanel {
    
    private static final int FRAME_WIDTH = 640;
    private static final int FRAME_HEIGHT = 480;
    
    MyButton buttonImport, buttonCompare, buttonZoomIn, buttonZoomOut,buttonHide, 
             buttonExpand, buttonHelp, buttonSimilarity, buttonEditText, buttonView,
             buttonSettings;
    JFileChooser fc;
    ImageIcon img;
    JLabel labelImg;
    ImageIcon icon;
    DefaultMutableTreeNode contents;
    Boolean contains;
    JSeparator sep1, sep2, sep3;
    Dimension sepDim;
    TreePanel treePanel;
    DiffTreePanel diffTreePanel;
    SimilarityPanel similarityPanel;
    static MyTree tree1;
    static MyTree tree2;
 
    
    
    public ButtonPanel(NewInitialScreen initialScreen, MyTree tree, final MyTabbedPane tabbedPane){
        
        buttonImport = new MyButton(getClass().getResource("images/open.png"));
        buttonCompare = new MyButton(getClass().getResource("images/compare.png"));  
        buttonZoomIn = new MyButton(getClass().getResource("images/zoom+.png"));
        buttonZoomOut = new MyButton(getClass().getResource("images/zoom-.png"));
        buttonHide = new MyButton(getClass().getResource("images/hide.png"));
        buttonExpand = new MyButton(getClass().getResource("images/expand.png"));
        buttonHelp = new MyButton(getClass().getResource("images/help.png"));
        buttonSimilarity = new MyButton(getClass().getResource("images/similarity.png"));
        buttonEditText = new MyButton(getClass().getResource("images/edit.png"));
        buttonView = new MyButton(getClass().getResource("images/view.png"));
        buttonSettings = new MyButton(getClass().getResource("images/settings.png"));
        
        buttonImport.setToolTipText("Import XML files");
        buttonCompare.setToolTipText("Compare XML documents");
        buttonZoomIn.setToolTipText("Zoom in");
        buttonZoomOut.setToolTipText("Zoom out");
        buttonExpand.setToolTipText("Expand all the nodes of the tree");
        buttonHide.setToolTipText("Hide all the nodes of the tree");
        buttonHelp.setToolTipText("Help");
        buttonSimilarity.setToolTipText("Compare similarity between XMLs");
        buttonEditText.setToolTipText("Edit XML documents.");
        buttonView.setToolTipText("View XML documents.");
        buttonSettings.setToolTipText("Edit application settings");
       
        buttonZoomIn.setEnabled(false);
        buttonZoomOut.setEnabled(false);
        buttonExpand.setEnabled(false);
        buttonHide.setEnabled(false);
        buttonEditText.setEnabled(false);

        configureLayout();
        buttonsActions(initialScreen, tree, tabbedPane);
        addListenerTabbedPane(tabbedPane);
         
    }

    private void configureLayout(){

        setLayout(new FlowLayout(FlowLayout.LEFT));
        sep1 = new JSeparator(SwingConstants.VERTICAL);
        sep2 = new JSeparator(SwingConstants.VERTICAL);
        sep3 = new JSeparator(SwingConstants.VERTICAL);
        sepDim = new Dimension(1, 30);
        sep1.setPreferredSize(sepDim);
        sep2.setPreferredSize(sepDim);
        sep3.setPreferredSize(sepDim);
        
        add(buttonImport);
        add(buttonCompare);
        add(buttonSimilarity);
        add(sep3);
        add(buttonEditText);
        add(buttonView);
        add(sep1);
        add(buttonZoomIn);
        add(buttonZoomOut);
        add(buttonExpand);
        add(buttonHide);
        add(sep2);
        add(buttonSettings);
        add(buttonHelp);

    }

    private void buttonsActions(final Window initialScreen, final MyTree tree, final MyTabbedPane tabbedPane) {

        buttonImport.addActionListener(new ActionListener() {
            @Override

            public void actionPerformed(ActionEvent e) {
                
                Object[] options = {"Document 1","Document 2"};
                int op = JOptionPane.showOptionDialog(null,"Do you want import the document 1 or document 2?", 
                        "Import XML Document",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
                fc = new JFileChooser();
                fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                
                if(fc.showOpenDialog(fc) == JFileChooser.APPROVE_OPTION){
                    File selecionado = fc.getSelectedFile();   //pegar doc xml e preencher primeiro txtare1 depois o segundo               
                    try {
                        FileReader in = new FileReader(selecionado);
                        BufferedReader br = new BufferedReader(in); 
                        String s,text=""; 
                        try {
                            while((s = br.readLine()) != null) {
                                text = text+s+"\n";   
                            }
                            if(op==0){
                                buttonEditText.setEnabled(true);
                                buttonView.setEnabled(false);
                                tabbedPane.getDocumentsPanel().changeScrollToEdit();
                                tabbedPane.getDocumentsPanel().xmlComparable1.setText(text);
                                tabbedPane.getDocumentsPanel().xmlComparable1Edit.setEditorKit(new XMLEditorKit());
                                tabbedPane.getDocumentsPanel().xmlComparable1Edit.setText(text);
                            }else{
                                buttonEditText.setEnabled(true);
                                buttonView.setEnabled(false);
                                tabbedPane.getDocumentsPanel().changeScrollToEdit();
                                tabbedPane.getDocumentsPanel().xmlComparable2.setText(text);
                                tabbedPane.getDocumentsPanel().xmlComparable2Edit.setEditorKit(new XMLEditorKit());
                                tabbedPane.getDocumentsPanel().xmlComparable2Edit.setText(text);
                            }
                            
                            
                        } catch (IOException ex) {
                            System.out.println("erro no while");
                        }
                    } catch (FileNotFoundException ex) {
                        System.out.println("erro de leitura do arquivo");
                    }
                    
                    //Pego as string dos xmls
                    String xml1 = tabbedPane.getDocumentsPanel().xmlComparable1.getText();
                    String xml2 = tabbedPane.getDocumentsPanel().xmlComparable2.getText();

                    // Crio as arvores de busca para serem adicionados a esquerda
                    tree1 = new MyTree(xml1, MyTree.TIPO_ESQUERDA);
                    tree2 = new MyTree(xml2, MyTree.TIPO_DIREITA);

                    //Cria JScrollPanel para árvores terem scroll vertical e horizontal quando necssário
                    JScrollPane scrollTree1 = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                    JScrollPane scrollTree2 = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

                    scrollTree1.setViewportView(tree1.getJTree());
                    scrollTree2.setViewportView(tree2.getJTree());

                    // Criar o painel para armazenar as 2 arvores
                    JSplitPane splitPaneArvores = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollTree1, scrollTree2);  
                    splitPaneArvores.setDividerLocation(250);

                    //Uso a variavel splitPane como variavel statica
                    NewInitialScreen.splitPane.setLeftComponent(splitPaneArvores);
                    NewInitialScreen.splitPane.setDividerLocation(230);

                    //Deixa os botões expandir e colapsar habilitados
                    buttonExpand.setEnabled(true);
                    buttonHide.setEnabled(true);
                        
                }   
            }
        });

        buttonCompare.addActionListener(new ActionListener() {
            @Override

            public void actionPerformed(ActionEvent e) {
                
                if (!SettingsHelper.areSettingsOk()) {
                    SettingsHelper.showError(initialScreen);
                    return;
                }
                
                tabbedPane.getDocumentsPanel().changed = false;
                
                treePanel = new TreePanel(tabbedPane);
                tabbedPane.getJTabbedPane().setEnabledAt(1, true);
                tabbedPane.getJTabbedPane().setComponentAt(1,treePanel);
                
                diffTreePanel = new DiffTreePanel(tabbedPane);
                tabbedPane.getJTabbedPane().setEnabledAt(2, true);
                tabbedPane.getJTabbedPane().setComponentAt(2,diffTreePanel);
            }
       });
        
        buttonZoomIn.addActionListener(new ActionListener() {
            @Override

            public void actionPerformed(ActionEvent e) {
                final ScalingControl scaler = new CrossoverScalingControl();
                if(tabbedPane.getSelectedIndex()==1){
                    scaler.scale(treePanel.getVisualizarXML1().getVisualizationViewerXML(), 1.1f, treePanel.getVisualizarXML1().getVisualizationViewerXML().getCenter());
                    scaler.scale(treePanel.getVisualizarXML2().getVisualizationViewerXML(), 1.1f, treePanel.getVisualizarXML2().getVisualizationViewerXML().getCenter());
                }else if(tabbedPane.getSelectedIndex()==2){
                    scaler.scale(diffTreePanel.getDiffGrafico().getVisualizationViewer(), 1.1f, diffTreePanel.getDiffGrafico().getVisualizationViewer().getCenter());
                }else if(tabbedPane.getSelectedIndex()==3){
                    scaler.scale(similarityPanel.dsg.getVv(), 1.1f, similarityPanel.dsg.getVv().getCenter());
                }
            }                     
            
        });
        buttonZoomOut.addActionListener(new ActionListener() {
            @Override

            public void actionPerformed(ActionEvent e) {
                final ScalingControl scaler = new CrossoverScalingControl();
                if(tabbedPane.getSelectedIndex()==1){
                    scaler.scale(treePanel.getVisualizarXML1().getVisualizationViewerXML(), 1/1.1f, treePanel.getVisualizarXML1().getVisualizationViewerXML().getCenter());
                scaler.scale(treePanel.getVisualizarXML2().getVisualizationViewerXML(), 1/1.1f, treePanel.getVisualizarXML2().getVisualizationViewerXML().getCenter());
                }else if(tabbedPane.getSelectedIndex()==2){
                    scaler.scale(diffTreePanel.getDiffGrafico().getVisualizationViewer(), 1/1.1f, diffTreePanel.getDiffGrafico().getVisualizationViewer().getCenter());
                }else if(tabbedPane.getSelectedIndex()==3){
                    scaler.scale(similarityPanel.dsg.getVv(), 1/1.1f, similarityPanel.dsg.getVv().getCenter());
                }     
            }
        });
        buttonExpand.addActionListener(new ActionListener() {
            @Override

            public void actionPerformed(ActionEvent e) {
               tree1.expandAll();
               tree2.expandAll();
            }
        });
        buttonHide.addActionListener(new ActionListener() {
            @Override

            public void actionPerformed(ActionEvent e) {
               tree1.collapseAll();
               tree2.collapseAll();
            }
        });
        buttonSimilarity.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                
                if (!SettingsHelper.areSettingsOk()) {
                    SettingsHelper.showError(initialScreen);
                    return;
                }
                
                similarityPanel = new SimilarityPanel();
                
                if(!similarityPanel.isChoosenDirectoryEmpty()){
                    tabbedPane.getJTabbedPane().setEnabledAt(3, true);    
                }else{
                    tabbedPane.getJTabbedPane().setEnabledAt(3, false);
                    tabbedPane.getJTabbedPane().setSelectedIndex(0);
                }
                
                tabbedPane.getJTabbedPane().setComponentAt(3,similarityPanel);
            }
        });
        buttonEditText.addActionListener(new ActionListener() {
            @Override

            public void actionPerformed(ActionEvent e) {
                tabbedPane.getDocumentsPanel().changeScrollToText();
                buttonEditText.setEnabled(false);
                buttonView.setEnabled(true);
            }
        });
        buttonView.addActionListener(new ActionListener() {
            @Override

            public void actionPerformed(ActionEvent e) {
                tabbedPane.getDocumentsPanel().xmlComparable1Edit.setEditorKit(new XMLEditorKit());
                tabbedPane.getDocumentsPanel().xmlComparable1Edit.setText(tabbedPane.getDocumentsPanel().xmlComparable1.getText());
                tabbedPane.getDocumentsPanel().xmlComparable2Edit.setEditorKit(new XMLEditorKit());
                tabbedPane.getDocumentsPanel().xmlComparable2Edit.setText(tabbedPane.getDocumentsPanel().xmlComparable2.getText());
                tabbedPane.getDocumentsPanel().changeScrollToEdit();
                buttonEditText.setEnabled(true);
                buttonView.setEnabled(false);
                //Pego as string dos xmls
                String xml1 = tabbedPane.getDocumentsPanel().xmlComparable1.getText();
                String xml2 = tabbedPane.getDocumentsPanel().xmlComparable2.getText();
                
                // Crio as arvores de busca para serem adicionados a esquerda
                tree1 = new MyTree(xml1, MyTree.TIPO_ESQUERDA);
                tree2 = new MyTree(xml2, MyTree.TIPO_DIREITA);
                
                //Cria JScrollPanel para árvores terem scroll vertical e horizontal quando necssário
                JScrollPane scrollTree1 = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                JScrollPane scrollTree2 = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                
                scrollTree1.setViewportView(tree1.getJTree());
                scrollTree2.setViewportView(tree2.getJTree());

                // Criar o painel para armazenar as 2 arvores
                JSplitPane splitPaneArvores = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollTree1, scrollTree2);  
                splitPaneArvores.setDividerLocation(250);
                
                //Uso a variavel splitPane como variavel statica
                NewInitialScreen.splitPane.setLeftComponent(splitPaneArvores);
                NewInitialScreen.splitPane.setDividerLocation(230);
                
                //Deixa os botões expandir e colapsar habilitados
                buttonExpand.setEnabled(true);
                buttonHide.setEnabled(true);
            }
        });
        buttonHelp.addActionListener(new ActionListener() {
            @Override

            public void actionPerformed(ActionEvent e) {
                JFrame frameHelp = new JFrame("Help");
                frameHelp.setSize(FRAME_WIDTH, FRAME_HEIGHT);
                frameHelp.setLocationRelativeTo(null);
                frameHelp.setLayout(new BorderLayout());
                frameHelp.setIconImage(new ImageIcon(getClass().getResource("images/help.png")).getImage());
                frameHelp.setVisible(true);
                
                //Create program help with an HTML file
                JScrollPane helpText = createHelpTextWithHTMLFile(getClass().getResourceAsStream("html/help.html"),FRAME_WIDTH,FRAME_HEIGHT);
                frameHelp.add(helpText);
            }
        });
        
        buttonSettings.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                showSettingsFrame(initialScreen);
            }
        });
    }
    
    protected void showSettingsFrame(Window parent) {
        SettingsDialog settingsFrame = new SettingsDialog(parent);
        settingsFrame.show();
        
    }

    /**
     * create an JEditorPane with html help text for the PHOENIX interface
     * @param htmlInputStream path from the HTML file
     * @return return the jEditorPane formated as HTML
     */
    private JScrollPane createHelpTextWithHTMLFile(InputStream htmlInputStream, Integer frameHelpWidth, Integer frameHelpHeigth){
        JScrollPane scrollPane = new JScrollPane();
        final JEditorPane editorPane = new JEditorPane();
        
        //Read html File
        String htmlHelpText;
        
        try {
             htmlHelpText = new java.util.Scanner(htmlInputStream).useDelimiter("\\A").next();
        } catch (java.util.NoSuchElementException e) {
            htmlHelpText = "";
        }
               
        //Create help text editor pane
        editorPane.setContentType("text/html");
        editorPane.setText(htmlHelpText);
        editorPane.setEditable(false);
        editorPane.setCaretPosition(0);
        
        editorPane.addHyperlinkListener(new HyperlinkListener() {
            public void hyperlinkUpdate(HyperlinkEvent e) {
                if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                    editorPane.scrollToReference(e.getDescription());
                }
            }
        });
        
        scrollPane.setViewportView(editorPane);
        
        return scrollPane;
    }

    private void addListenerTabbedPane(final MyTabbedPane tabbedPane) {
        tabbedPane.getJTabbedPane().addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                if(tabbedPane.getSelectedIndex()==0){
                  buttonZoomIn.setEnabled(false);
                  buttonZoomOut.setEnabled(false);
                }else if(tabbedPane.getSelectedIndex()==1){
                  if(tabbedPane.getDocumentsPanel().changed==true){
                      JOptionPane.showMessageDialog(null, "Some of the documents has been changed."+"\n"+
                                                          "The trees don't match the current documents."+"\n"+
                                                          "Compare the XMLs documents again.", "Warning", JOptionPane.WARNING_MESSAGE);
                  }
                  buttonZoomIn.setEnabled(true);
                  buttonZoomOut.setEnabled(true);
                }else if(tabbedPane.getSelectedIndex()==2){
                    if(tabbedPane.getDocumentsPanel().changed==true){
                      JOptionPane.showMessageDialog(null, "Some of the documents has been changed."+"\n"+
                                                          "The Difference Tree don't match the comparation of documents."+"\n"+
                                                          "Compare the XMLs documents again.","Warning", JOptionPane.WARNING_MESSAGE);
                  }
                  buttonZoomIn.setEnabled(true);
                  buttonZoomOut.setEnabled(true);
                }else if(tabbedPane.getSelectedIndex()==3){
                    if(tabbedPane.getDocumentsPanel().changed==true){
                      JOptionPane.showMessageDialog(null, "Some of the documents has been changed."+"\n"+
                                                          "The Difference Tree don't match the comparation of documents."+"\n"+
                                                          "Compare the XMLs documents again.","Warning", JOptionPane.WARNING_MESSAGE);
                  }
                  buttonZoomIn.setEnabled(true);
                  buttonZoomOut.setEnabled(true);
                }else{
                  buttonZoomIn.setEnabled(false);
                  buttonZoomOut.setEnabled(false);  
                }
            }
        });
    }
}

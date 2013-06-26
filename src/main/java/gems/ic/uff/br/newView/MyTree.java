package gems.ic.uff.br.newView;

import java.awt.Component;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
/*
 * MyTree.java
 *
 * MyTree dos documentos XML.
 *
 * @author douglas
 */

public final class MyTree {
    
    //Constantes de contrução
    public final static int TIPO_DIFF = 0;
    public final static int TIPO_ESQUERDA = 1;
    public final static int TIPO_DIREITA = 2;
    
    private DefaultTreeModel treeModel;
    private JTree tree;
    private DefaultMutableTreeNode topo, imagem, video, audio, texto, outros, conteudo;
    private ArrayList<DefaultMutableTreeNode> lista = new ArrayList();
    private int contador = 0;
    private TreeSelectionListener treeListener;
    
    // TIPO: 0:DIFF, 1:Documento1, 2:Documento2
    public MyTree(String xml, int tipo){
        
        //Monta o arquivo inicial
        if (tipo != 0)
            lista.add(new DefaultMutableTreeNode("Documento " + tipo));
        else
           lista.add(new DefaultMutableTreeNode("Diff")); 
        
        
        treeModel = new DefaultTreeModel(lista.get(0));
        tree = new JTree(treeModel); 
            
        try {
            //Instancia o parser
            DocumentBuilderFactory b = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = b.newDocumentBuilder();

            //Constroi um documento xml apartir de uma string
            InputSource is = new InputSource(new StringReader(xml));

            //Verifica se a string é diferente de vazio
            if (!xml.equals("")){
                //Faz o parsing do documento
                Document meuDocumento = (Document) builder.parse(is);  

                //Faz o processamento do documento xml
                processa(meuDocumento);
            }

        } catch( Exception e ) {}

        //Renderiza as imagens
        RenderizarTree render = new RenderizarTree();
        render.setLeafIcon(null);
        tree.setCellRenderer(render); 
        
    }

    public void insere(){ //filhos do documento XML

//        conteudo = new DefaultMutableTreeNode();//colocar o objeto filho dentro dessa instancia.(DefaultMutableTreeNode(Filho))
//        treeModel.insertNodeInto(conteudo, imagem, posicao);
              
    }

    public void remove(){ //param no a ser removido             
        
//        DefaultMutableTreeNode aux;  
//        aux = (DefaultMutableTreeNode) imagem.getChildAt(posicao);
//        imagem.remove(aux);
//        treeModel.reload(imagem);
        
    }
    
    public void removeTodos(){//parametro no a ser removido seus filhos
        
        imagem.removeAllChildren();             
        treeModel.reload();
  
    }
    
    public int tamanho(){//entrar como parametro o no para saber seu tamanho.
        
        return imagem.getChildCount();
        
    }

      
    public JTree getJTree(){
        
        return tree;
        
    }
    
    public DefaultTreeModel getTreeModel(){
        
        return treeModel;
        
    }
    
     void processa(Document doc) {
         
        //Recupera elementos
        Element raiz = doc.getDocumentElement();
        imprimeFilhos((Node) raiz, contador, 0, 0);
    }

    int imprimeFilhos(Node n, int pai, int index, int offSet) {
        
        int posicao = ++contador;
        int qtdEspacoEmBranco = 0;
        
        if (n.getNodeValue() == null){
            lista.add(new DefaultMutableTreeNode(n.getNodeName()));
            treeModel.insertNodeInto(lista.get(posicao), lista.get(pai), index - offSet);
        }
        else {
            lista.add(new DefaultMutableTreeNode(n.getNodeValue()));
            if (!(n.getNodeValue().trim().equals(""))){
                treeModel.insertNodeInto(lista.get(posicao), lista.get(pai), index - offSet);
            } else {
                qtdEspacoEmBranco++;
            }
       }
            
        if (n.hasChildNodes()) {
            NodeList filhos = n.getChildNodes();
            int espacosEMBranco = 0;
            for (int i = 0; i < filhos.getLength(); i++) {
                espacosEMBranco = espacosEMBranco + imprimeFilhos(filhos.item(i), posicao, i, espacosEMBranco);
            }
        } else {
            
            //Só cai aqui em elementos vazios, deixando um arquivo em branco para ele
            if (n.getNodeValue() == null){
                posicao = ++contador;
                lista.add(new DefaultMutableTreeNode(""));
                treeModel.insertNodeInto(lista.get(posicao), lista.get(posicao-1), 0);
            }
        }
        
        return qtdEspacoEmBranco;
        
    }
    
    public void expandAll() {
        int row = 0;
        while (row < tree.getRowCount()) {
            tree.expandRow(row);
            row++;
        }
    }


    public void expandToLast() {
        DefaultMutableTreeNode  root;
        root = (DefaultMutableTreeNode) tree.getModel().getRoot();
        tree.scrollPathToVisible(new TreePath(root.getLastLeaf().getPath()));
    }
    
    public void collapseAll() {
        int row = tree.getRowCount() - 1;
        while (row >= 0) {
            tree.collapseRow(row);
            row--;
        }
    }
    
    //Classe para trocar as imagens dos nodes
    class RenderizarTree extends DefaultTreeCellRenderer implements TreeCellRenderer {  
        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {  
            super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);  

            //URL xmlIconURL = getClass().getResource("images/xmlIcon.png");
            //URL tagIconURL = getClass().getResource("images/tagIcon.png");
            //URL leafIconURL = getClass().getResource("images/leafIcon.png");
            
            
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;  
            if(node.toString().equals("Documento 1")) {  
                setIcon(new ImageIcon("images/xmlIcon.png"));  
            } else if (node.toString().equals("Documento 2")) {  
               setIcon(new ImageIcon("images/xmlIcon.png")); 
            } else if (!node.isLeaf()) { 
                 setIcon(new ImageIcon("images/tagIcon.png")); 
            } else if (node.isLeaf()){
                setIcon(new ImageIcon("images/leafIcon.png")); 
            }
            return this;  
        }
    }
}

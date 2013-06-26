/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gems.ic.uff.br.view;

import edu.uci.ics.jung.algorithms.layout.TreeLayout;
import edu.uci.ics.jung.graph.DelegateTree;
import edu.uci.ics.jung.graph.Forest;
import edu.uci.ics.jung.visualization.GraphZoomScrollPane;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse.Mode;
import edu.uci.ics.jung.visualization.decorators.EdgeShape;
import edu.uci.ics.jung.visualization.renderers.VertexLabelAsShapeRenderer;
import gems.ic.uff.br.modelo.XML;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import org.apache.commons.collections15.Factory;
import org.apache.commons.collections15.Transformer;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author campello
 */
public class VisualizarDiffXML extends JPanel {

    private Forest<Node, String> floresta;
    private VisualizationViewer<Node, String> vv;
    private TreeLayout<Node, String> treeLayout;
    private XML xml;
    private int tam = 1;
    private Factory<String> edgeFactory = new Factory<String>() {

        public String create() {
            return "" + tam++;
        }
    };
    private Transformer<Node, String> mudarRotulo = new Transformer<Node, String>() {

        public String transform(Node arg0) {
            if (arg0.getNodeType() != Node.TEXT_NODE) {
                if (arg0.getNodeName().contains("diff:left") || arg0.getNodeName().contains("diff:right") || arg0.getNodeName().contains("diff:center") ) {
                    return arg0.getNodeValue();
                } else {
                    return arg0.getNodeName();
                }
            } else {
                return arg0.getNodeValue();
            }
        }
    };
    private Transformer<Node, String> criarToolTip = new Transformer<Node, String>() {

        public String transform(Node arg0) {
            if (arg0.hasAttributes()) {
                NamedNodeMap atributos = arg0.getAttributes();
                String atributosConcatenados = "";
                for (int i = 0; i < atributos.getLength(); i++) {
                    atributosConcatenados +=" "+ atributos.item(i).getNodeName() + " = ";
                    if (atributos.item(i).getNodeValue().isEmpty()) {
                        atributosConcatenados += " Valor nulo \n";
                    } else {
                        atributosConcatenados += " " + atributos.item(i).getNodeValue() + "\n";
                    }
                }

                return atributosConcatenados;
            }
            return "Sem atributos";
        }
    };

    public VisualizarDiffXML(String caminhoOuTextoXML) {
        Transformer<Node, Paint> changeColor = new Transformer<Node, Paint>() {

            public Paint transform(Node arg0) {
                                    
                if (arg0.hasAttributes()) 
                {
                    Node nodeRelationT = arg0.getAttributes().getNamedItem("diff:relation");
                   
                    Node nodeSimilaridade = arg0.getAttributes().getNamedItem("diff:similarity");
                    if (nodeSimilaridade != null) 
                    {
                        String similaridade = nodeSimilaridade.getNodeValue();
                        if(similaridade.contains("son1"))
                        {
                            similaridade = similaridade.replace("son1:", "");
                            similaridade = similaridade.replace("son2: ", "");
                            String [] vetSim = similaridade.split(" ");
                            float sim1 =1;
                            float sim2 =0;
                            if(vetSim.length>=2)
                            {
                                 sim1 = Float.parseFloat(vetSim[2]  );
                                 sim2 = Float.parseFloat(vetSim[3]  );
                            }
                            
                            similaridade = String.valueOf((sim1+sim2)/2);
                        }
                        
                        if (similaridade.equals("1.0")) {
                            return Color.WHITE;
                        } else if (similaridade.equals("0.0")) {
                            
                            Node nodeRelation = arg0.getAttributes().getNamedItem("diff:relation");
                            Node nodeSide = arg0.getAttributes().getNamedItem("diff:side");
                            
                            if(nodeRelation != null)
                            {
                                if (nodeRelation.getNodeValue().equals("son1")) 
                                {
                                     System.out.println("son1 !!!");
                                    return Color.RED;
                                }
                                if(nodeRelation.getNodeValue().equals("son2"))
                                {
                                    System.out.println("son2 !!!");
                                    return Color.PINK;
                                }
                                
                                
                                if(nodeRelation.getNodeValue().equals("ancestral"))
                                {
                                    System.out.println("ancestral !!!");
                                    return Color.GREEN;
                                }
                                
                                if(nodeRelation.getNodeValue().equals("ancestralSon1"))
                                {
                                    return Color.BLUE;
                                }
                                
                                if(nodeRelation.getNodeValue().equals("ancestralSon2"))
                                {
                                    return Color.YELLOW;
                                }
                                if(nodeRelation.getNodeValue().equals("son1Son2"))
                                {
                                    return Color.ORANGE;
                                }
                            }
                            else
                            {
                                if (nodeSide != null && nodeSide.getNodeValue().equals("left")) {
                                    return Color.RED;
                                } else {

                                    if (nodeSide != null && nodeSide.getNodeValue().equals("right")) {
                                        return Color.GREEN;
                                    }
                                    else
                                    {
                                        return Color.MAGENTA;
                                    }
                                }
                            }
                        } else {
                            int escalaTrucada = Math.round(255 * Float.parseFloat(similaridade));
                            return new Color(escalaTrucada, escalaTrucada, escalaTrucada); // similaridade diferente de 0 e 1;
//                            return new Color((255 * Float.parseFloat(nodeSimilaridade.getNodeValue())),
//                                    (255 * Float.parseFloat(nodeSimilaridade.getNodeValue())),
//                                    (255 * Float.parseFloat(nodeSimilaridade.getNodeValue()))); // similaridade diferente de 0 e 1;
                        }
                    }
                }
                if (arg0.getNodeName().contains("diff:left")) {
                    return Color.RED;
                } else if (arg0.getNodeName().contains("diff:right")) {
                    return Color.GREEN;
                }
                else if (arg0.getNodeName().contains("diff:center")) {
                    return Color.YELLOW;
                }
                if (arg0.getNodeType() == Node.TEXT_NODE) {
                    return Color.WHITE;
                }
                return Color.BLACK; // verificar 
            }
        };

        xml = new XML(caminhoOuTextoXML);

        xml.removeWhiteSpaces(xml.getDocument()); //nao esta funcionando
        floresta = new DelegateTree<Node, String>();
        Node raiz = xml.getDocument().getDocumentElement();

        floresta.addVertex(raiz);

        insereFilhos(raiz);
        treeLayout = new TreeLayout<Node, String>(floresta);
//        treeLayout = new RadialTreeLayout<Node, String>(floresta);
        vv = new VisualizationViewer<Node, String>(treeLayout, new Dimension(1080, 580));
        VertexLabelAsShapeRenderer<Node, String> vlasr = new VertexLabelAsShapeRenderer<Node, String>(vv.getRenderContext());

        vv.getRenderContext().setEdgeShapeTransformer(new EdgeShape.Line());
        vv.getRenderContext().setVertexLabelTransformer(mudarRotulo);
        vv.getRenderContext().setVertexFillPaintTransformer(changeColor);


        vv.getRenderer().setVertexLabelRenderer(vlasr);
        vv.setVertexToolTipTransformer(criarToolTip);
        GraphZoomScrollPane panel = new GraphZoomScrollPane(vv);
        final DefaultModalGraphMouse graphMouse = new DefaultModalGraphMouse();

        graphMouse.setMode(Mode.PICKING);

        vv.setGraphMouse(graphMouse);
        
        JPanel geral = new JPanel(new BorderLayout());
        geral.add(panel, BorderLayout.CENTER);

        insereLegendas(vv);

        setLayout(new BorderLayout());
        this.add(geral, BorderLayout.CENTER);

    }
    
    private void insereFilhos(Node item) {
        NodeList nl = item.getChildNodes();
        for (int i = 0; i < nl.getLength(); i++) {
            if (nl.item(i).getNodeName().contains("diff:value")) {
                Node noEsquerdo = nl.item(i).getAttributes().getNamedItem("diff:left");
                Node noDireito = nl.item(i).getAttributes().getNamedItem("diff:right");
                Node noCentral = nl.item(i).getAttributes().getNamedItem("diff:center");
                if (noEsquerdo != null) {
                    floresta.addEdge(edgeFactory.create(), item, noEsquerdo);
                }
                if (noDireito != null) {
                    floresta.addEdge(edgeFactory.create(), item, noDireito);
                }
                if (noCentral != null) {
                    floresta.addEdge(edgeFactory.create(), item, noCentral);
                }
            } else {
                floresta.addEdge(edgeFactory.create(), item, nl.item(i));
            }
            if (nl.item(i).hasChildNodes()) {
                insereFilhos(nl.item(i));
            }
        }
    }
    
    private void insereLegendas(JPanel frame){
        JPanel containerSubtitles = new JPanel();

        String diferencasString = null;
        try {
            diferencasString = new String("Diferenças".getBytes(), "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(VisualizarDiffXML.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        setColorAndTextForSubtitle(containerSubtitles, Color.red, "Documento 1");
        setColorAndTextForSubtitle(containerSubtitles, Color.green, "Documento 2");           
        setColorAndTextForSubtitle(containerSubtitles, Color.decode("0xDFDFDF"), diferencasString);
        setColorAndTextForSubtitle(containerSubtitles, Color.white, "Igual");

        frame.add(containerSubtitles);
    }
    
    private void setColorAndTextForSubtitle(JPanel containerSubtitles,Color color,String description){
        JPanel legend = new JPanel();
        JLabel label = new JLabel();

        label.setText(description);
        label.setVisible(true);
        
        Border legendBorder = BorderFactory.createLineBorder(Color.black);
        legend.setBorder(legendBorder);
        legend.setBackground(color);
        legend.setVisible(true);
        
        containerSubtitles.add(legend);
        containerSubtitles.add(label);
    }
    
    public VisualizationViewer<Node, String> getVisualizationViewer(){
        return vv;
    }
}

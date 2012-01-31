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
import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse.Mode;
import edu.uci.ics.jung.visualization.control.ScalingControl;
import edu.uci.ics.jung.visualization.decorators.EdgeShape;
import edu.uci.ics.jung.visualization.renderers.VertexLabelAsShapeRenderer;
import gems.ic.uff.br.modelo.XML;
import gems.ic.uff.br.util.GBC;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
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
                if (arg0.getNodeName().contains("diff:left")
                        || arg0.getNodeName().contains("diff:right")) {
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
                    atributosConcatenados += atributos.item(i).getNodeName() + " = ";
                    if (atributos.item(i).getNodeValue().isEmpty()) {
                        atributosConcatenados += "Valor nulo \n\n";
                    } else {
                        atributosConcatenados += "" + atributos.item(i).getNodeValue() + "  \n\n";
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
                if(arg0.hasAttributes()){
                    Node nodeSimilaridade = arg0.getAttributes().getNamedItem("diff:similarity");
                    if (nodeSimilaridade != null
                            && nodeSimilaridade.getNodeValue().equals("1.0")) {
                        return Color.GRAY;
                    }
                }
                if (arg0.getNodeName().contains("diff:left")) {
                    return Color.ORANGE;
                } else if (arg0.getNodeName().contains("diff:right")) {
                    return Color.WHITE;
                }
                if (arg0.getNodeType() == Node.TEXT_NODE) {
                    return Color.GRAY;
                }
                return Color.RED;
            }
        };

        xml = new XML(caminhoOuTextoXML);
        xml.removeWhiteSpaces(xml.getDocument());
        floresta = new DelegateTree<Node, String>();
        Node raiz = xml.getDocument().getDocumentElement();
        floresta.addVertex(raiz);
        insereFilhos(raiz);
        treeLayout = new TreeLayout<Node, String>(floresta);
//        treeLayout = new RadialTreeLayout<Node, String>(floresta);

        vv = new VisualizationViewer<Node, String>(treeLayout, new Dimension(400, 400));
        VertexLabelAsShapeRenderer<Node, String> vlasr = new VertexLabelAsShapeRenderer<Node, String>(vv.getRenderContext());
        vv.getRenderContext().setEdgeShapeTransformer(new EdgeShape.Line());
        vv.getRenderContext().setVertexLabelTransformer(mudarRotulo);
        vv.getRenderContext().setVertexFillPaintTransformer(changeColor);
        vv.setBackground(Color.decode("0xffffbb"));


        vv.getRenderer().setVertexLabelRenderer(vlasr);
        vv.setVertexToolTipTransformer(criarToolTip);

        GraphZoomScrollPane panel = new GraphZoomScrollPane(vv);

        final DefaultModalGraphMouse graphMouse = new DefaultModalGraphMouse();
        graphMouse.setMode(Mode.PICKING);
        vv.setGraphMouse(graphMouse);

        final ScalingControl scaler = new CrossoverScalingControl();

        JButton zoomIn = new JButton("+");
        zoomIn.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                scaler.scale(vv, 1.1f, vv.getCenter());
            }
        });
        JButton zoomOut = new JButton("-");
        zoomOut.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                scaler.scale(vv, 1 / 1.1f, vv.getCenter());
            }
        });

        GBC gbc = new GBC();
        JPanel controls = new JPanel(new GridBagLayout());
        JPanel zoomControls = new JPanel(new GridBagLayout());

        zoomControls.setBorder(BorderFactory.createTitledBorder("Zoom"));
        zoomControls.add(zoomIn, gbc.adicionarComponente(0, 0, 1, 1));
        zoomControls.add(zoomOut, gbc.adicionarComponente(1, 0, 1, 1));

        controls.add(zoomControls, gbc.adicionarComponente(0, 0, 1, 1));
        JPanel geral = new JPanel(new GridBagLayout());
        geral.add(panel, gbc.adicionarComponente(0, 0, 2, 2));
        geral.add(controls, gbc.adicionarComponente(1, 3, 1, 1));


        this.add(geral);

    }

    private void insereFilhos(Node item) {
        NodeList nl = item.getChildNodes();
        for (int i = 0; i < nl.getLength(); i++) {
            if (nl.item(i).getNodeName().contains("diff:value")) {
                Node noEsquerdo = nl.item(i).getAttributes().getNamedItem("diff:left");
                Node noDireito = nl.item(i).getAttributes().getNamedItem("diff:right");
                if (noEsquerdo != null) {
                    floresta.addEdge(edgeFactory.create(), item, noEsquerdo);
                }
                if (noDireito != null) {
                    floresta.addEdge(edgeFactory.create(), item, noDireito);
                }
            } else {
                floresta.addEdge(edgeFactory.create(), item, nl.item(i));
            }
            if (nl.item(i).hasChildNodes()) {
                insereFilhos(nl.item(i));
            }
        }
    }
}

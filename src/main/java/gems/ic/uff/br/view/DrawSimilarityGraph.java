/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gems.ic.uff.br.view;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.renderers.Renderer;
import java.awt.Dimension;
import java.io.File;
import org.apache.commons.collections15.Transformer;

/**
 *
 * @author gmenezes
 */
public class DrawSimilarityGraph {

    Graph<File, String> graph;
//    BasicVisualizationServer<File, String> vv;
    VisualizationViewer<File, String> vv;
    Layout<File, String> layout;
    int edge = 0;

    public DrawSimilarityGraph() {

        graph = new SparseGraph<File, String>();
        edge = 0;
    }
    private Transformer<File, String> changeLabel = new Transformer<File, String>() {

        public String transform(File arg0) {
            return arg0.getName();
        }
    };

    public void draw(float[][] matrix, File[] files, float threshold) {

        int size = files.length;

        for (File file : files) {
            graph.addVertex(file);
        }

        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {

                if (matrix[i][j] >= threshold) {
                    graph.addEdge((edge++) + "", files[i], files[j], EdgeType.UNDIRECTED);
                }

            }
        }

        layout = new CircleLayout(graph);
        layout.setSize(new Dimension(1000, 600)); // sets the initial size of the space
        vv = new VisualizationViewer<File, String>(layout);


        vv.setPreferredSize(new Dimension(1050, 650)); //Sets the viewing area size


        //Changing vertex label
        vv.getRenderContext().setVertexLabelTransformer(changeLabel);
        vv.getRenderer().getVertexLabelRenderer().setPosition(Renderer.VertexLabel.Position.CNTR);

        //providing mouse iteractivity
        final DefaultModalGraphMouse graphMouse = new DefaultModalGraphMouse();
        graphMouse.setMode(ModalGraphMouse.Mode.PICKING);
        vv.setGraphMouse(graphMouse);
    }

    public void updateGraph(float[][] matrix, File[] files, float threshold) {

        int size = files.length;

        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {

                if (matrix[i][j] >= threshold) {
                    graph.addEdge((edge++) + "", files[i], files[j], EdgeType.UNDIRECTED);
                }

            }
        }

        // The Layout<V, E> is parameterized by the vertex and edge types
        layout = new CircleLayout(graph);
        layout.setSize(new Dimension(1000, 600)); // sets the initial size of the space
        // The BasicVisualizationServer<V,E> is parameterized by the edge types
//        vv = new BasicVisualizationServer<File, String>(layout);
        vv = new VisualizationViewer<File, String>(layout);


        vv.setPreferredSize(new Dimension(1050, 650)); //Sets the viewing area size


        //Changing vertex label
        vv.getRenderContext().setVertexLabelTransformer(changeLabel);
        vv.getRenderer().getVertexLabelRenderer().setPosition(Renderer.VertexLabel.Position.CNTR);

        //providing mouse iteractivity
        final DefaultModalGraphMouse graphMouse = new DefaultModalGraphMouse();
        graphMouse.setMode(ModalGraphMouse.Mode.PICKING);
        vv.setGraphMouse(graphMouse);
    }

    //GETTERS and SETTERS
    public Graph<File, String> getGraph() {
        return graph;
    }

    public void setGraph(Graph<File, String> graph) {
        this.graph = graph;
    }

    public BasicVisualizationServer<File, String> getVv() {
        return vv;
    }

    public void setVv(VisualizationViewer<File, String> vv) {
        this.vv = vv;
    }
}

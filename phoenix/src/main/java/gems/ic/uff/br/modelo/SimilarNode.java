package gems.ic.uff.br.modelo;

import com.sun.org.apache.xpath.internal.NodeSet;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import uk.ac.shef.wit.simmetrics.similaritymetrics.EuclideanDistance;
import uk.ac.shef.wit.simmetrics.similaritymetrics.MongeElkan;

/**
 * Classe que encapsula a classe Node do DOM, adicionando um comportamento
 * de similaridade a ela.
 */
public class SimilarNode extends Similar<SimilarNode> {

    private Node node;
    //Depois de definidos, testar os valores padrões.
    public static final double ATTRIBUTE_WEIGTH = 0.4;
    public static final double ELEMENT_VALUE_WEIGTH = 0.5;
    public static final double ELEMENT_NAME_WEIGTH = 1;
    public static final double ELEMENT_CHILDREN_WEIGTH = 0.6;

    public SimilarNode(Node node) {
        this.node = node;
    }

    @Override
    public double similar(SimilarNode y) {
        double similarity = 1;
        Node otherNode = y.getNode();

        //TODO: Validar com a Vanessa.
        if (node == null || otherNode == null) {
            throw new IllegalArgumentException("O XML não é válido pois possui valores no elemento root.");
        }

        similarity = elementsNameSimilarity(otherNode, similarity);
        similarity = elementsValueSimilarity(otherNode, similarity);
        similarity = elementsAttributesSimilarity(otherNode, similarity);
        similarity = elementsChildrenSimilarity(otherNode, similarity);

        return similarity > 0 ? similarity : 0; //Não testado.
    }

    public double elementsNameSimilarity(Node otherNode, double similarity) {
        if (!node.getNodeName().equals(otherNode.getNodeName())) {
            similarity -= ELEMENT_NAME_WEIGTH;
        }

        return similarity;
    }

    public double elementsValueSimilarity(Node otherNode, double similarity) throws DOMException {
        if (node.hasChildNodes() || otherNode.hasChildNodes()) {
            String nodeValue = null;
            String otherNodeValue = null;

            if (node.hasChildNodes()) {
                //O valor do elemento é um de seus filhos.
                nodeValue = node.getFirstChild().getNodeValue();
            }

            if (otherNode.hasChildNodes()) {
                //O valor do elemento é um de seus filhos.
                otherNodeValue = otherNode.getFirstChild().getNodeValue();
            }

            if (nodeValue != null || otherNodeValue != null) {
                if (nodeValue == null || otherNodeValue == null || !nodeValue.equals(otherNodeValue)) {
                    similarity -= ELEMENT_VALUE_WEIGTH;
                }
            }
        }

        return similarity;
    }

    public double elementsAttributesSimilarity(Node otherNode, double similarity) {
        if (node.hasAttributes() || otherNode.hasAttributes()) {
            if (node.hasAttributes() && otherNode.hasAttributes()) {
                //TODO: Usar um método de conjunto para medir a similaridade
                //NamedNodeMap attributesFromNode = node.getAttributes();
                //NamedNodeMap attributesFromOtherNode = otherNode.getAttributes();
            } else {
                similarity -= ATTRIBUTE_WEIGTH;
            }
        }

        return similarity;
    }

    public double elementsChildrenSimilarity(Node otherNode, double similarity) {
        NodeList childNodes = node.getChildNodes();
        NodeList otherChildNodes = otherNode.getChildNodes();

        if (childNodes != null && otherChildNodes != null) {
            NodeSet elementNodes = getElementNodes(childNodes);
            NodeSet otherElementNodes = getElementNodes(otherChildNodes);

            if ((elementNodes.size() != 0 && otherElementNodes.size() == 0) || (elementNodes.size() == 0 && otherElementNodes.size() != 0)) {
                similarity -= ELEMENT_CHILDREN_WEIGTH;
            } else {

                //testando
                List<String> elementoNodes = new ArrayList();
                for (int i = 0; i < elementNodes.getLength(); i++) {
                    elementoNodes.add(elementNodes.item(i).getNodeName());
                }
                List<String> otherElementoNodes = new ArrayList();
//                MongeElkan me =  new MongeElkan(new EuclideanDistance());
                for (int i = 0; i < otherElementNodes.getLength(); i++) {
                    otherElementoNodes.add(otherElementNodes.item(i).getNodeName());
                }

                System.out.println("ANTESSSSSSS     " + otherElementoNodes.size());
                otherElementoNodes.removeAll(elementoNodes);
                System.out.println("DEPOISSSSSS     " + otherElementoNodes.size());


            }

        } else {
            similarity -= ELEMENT_CHILDREN_WEIGTH;
        }

        return similarity;
    }

    private NodeSet getElementNodes(NodeList nodeList) {
        NodeSet elementNodes = new NodeSet();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node item = nodeList.item(i);

            if (item.getNodeType() == Node.ELEMENT_NODE) {
                elementNodes.addNode(item);
            }
        }

        return elementNodes;
    }

    public Node getNode() {
        return node;
    }

    @Override
    public String toString() {
        return node.toString();
    }
}

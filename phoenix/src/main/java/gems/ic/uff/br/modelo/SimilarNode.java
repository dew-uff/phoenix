package gems.ic.uff.br.modelo;

import org.w3c.dom.Node;

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
    //Esse método deveria ser quebrado em vários?
    //Testar se o valor da similaridade é menor do que 0 durante o código para
    //aumentar o desempenho?
    public double similar(SimilarNode y) {
        double similarity = 1;
        Node otherNode = y.getNode();
        
        if (node == null || otherNode == null) {
            throw new IllegalArgumentException("O XML não é válido pois possui valores na TAG root."); //TODO: Validar com a Vanessa.
        }

        if (!node.getNodeName().equals(otherNode.getNodeName())) {
            similarity -= ELEMENT_NAME_WEIGTH;
        }

        if (node.hasChildNodes() || otherNode.hasChildNodes()) {
            String nodeValue = null;
            String otherNodeValue = null;

            //O valor do elemento é um de seus filhos.
            if (node.hasChildNodes()) {
                nodeValue = node.getFirstChild().getNodeValue();

                if (nodeValue != null) {
                    //É necessário removê-lo para depois comparar os elementos filhos.
                    node.removeChild(node.getFirstChild());
                }
            }

            if (otherNode.hasChildNodes()) {
                otherNodeValue = otherNode.getFirstChild().getNodeValue();

                if (otherNodeValue != null) {
                    otherNode.removeChild(otherNode.getFirstChild());
                }
            }

            if (nodeValue != null || otherNodeValue != null) {
                if (nodeValue == null || otherNodeValue == null || !nodeValue.equals(otherNodeValue)) {
                    similarity -= ELEMENT_VALUE_WEIGTH;
                }
            }
        }

        if (node.hasAttributes() || otherNode.hasAttributes()) {
            if (node.hasAttributes() && otherNode.hasAttributes()) {
                //TODO: Usar um método de conjunto para medir a similaridade
                //NamedNodeMap attributesFromNode = node.getAttributes();
                //NamedNodeMap attributesFromOtherNode = otherNode.getAttributes();
            } else {
                similarity -= ATTRIBUTE_WEIGTH;
            }
        }

        if (node.hasChildNodes() || otherNode.hasChildNodes()) {
            if (node.hasChildNodes() && otherNode.hasChildNodes()) {
                //TODO: Usar um método de conjunto para medir a similaridade
                //NodeList nodeChildren = node.getChildNodes();
                //NodeList otherNodeChildren = node.getChildNodes();
            } else {
                similarity -= ELEMENT_CHILDREN_WEIGTH;
            }
        }

        return similarity > 0 ? similarity : 0; //Não testado.
    }

    public Node getNode() {
        return node;
    }

    @Override
    public String toString() {
        return node.toString();
    }
}

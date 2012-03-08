package gems.ic.uff.br.modelo;

import gems.ic.uff.br.modelo.similar.SimilarNode;
import java.util.Stack;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Diff {

    public static final String NAMESPACE = "ic.uff.br/xmldiff";
    public static final String DIFF_PREFIX = "diff:";
    public static final String DIFF_SIDE = "diff:side";
    public static final String LEFT_PREFIX = "left:";
    public static final String RIGHT_PREFIX = "right:";
    private float similarity;
    private Node diffNode;
    private SimilarNode leftNode;
    private SimilarNode rightNode;

    //Esses construtores são POGs!
    public Diff(float similarity) {
        this.similarity = similarity;
    }

    public Diff(Node node) {
        this.diffNode = DiffXML.getInstance().getDocument().createElement(node.getNodeName());
        this.leftNode = new SimilarNode(node);
    }

    public Diff(Node leftNode, Node rightNode) {
        this.diffNode = DiffXML.getInstance().getDocument().createElement(leftNode.getNodeName());
        this.leftNode = new SimilarNode(leftNode);
        this.rightNode = new SimilarNode(rightNode);
    }

    public Diff(SimilarNode left, SimilarNode right) {
        this.diffNode = DiffXML.getInstance().getDocument().createElement(left.getNode().getNodeName());
        this.leftNode = left;
        this.rightNode = right;
    }

    private boolean isElementValue(String valor) {
        return (valor.contains("\n") || 
                valor.contains("      ") ||
                valor.contains("&#10")) ? false : true;
    }

    public void setValue(String leftElementValue, String rightElementValue) {
        if (leftElementValue != null || rightElementValue != null) {
            if (leftElementValue != null && rightElementValue != null) {
                if (leftElementValue.equals(rightElementValue)) {
                    this.diffNode.setTextContent(leftElementValue);
                } else {
                    if (isElementValue(leftElementValue)) {

                        Element valueNode = (Element) DiffXML.createNode("value");
                        valueNode.setAttributeNS(NAMESPACE, DIFF_PREFIX + "left", leftElementValue);
                        if (isElementValue(rightElementValue)) {
                            valueNode.setAttributeNS(NAMESPACE, DIFF_PREFIX + "right", rightElementValue);
                        }
                        this.diffNode.appendChild(valueNode);

                    } else if (isElementValue(rightElementValue)) {
                        Element valueNode = (Element) DiffXML.createNode("value");
                        valueNode.setAttributeNS(NAMESPACE, DIFF_PREFIX + "right", rightElementValue);

                        this.diffNode.appendChild(valueNode);
                    }
                }
            } else {
                if (leftElementValue != null) {
                    this.diffNode.setTextContent(leftElementValue);
                } else {
                    this.diffNode.setTextContent(rightElementValue);
                }
            }
        }
    }

    /**
     * Este método é usado para indicar os valores dos atributos, tanto esquerdo
     * quanto direito, do elemento comparado.
     * 
     * @param attributeName
     * Nome do atributo
     * @param leftElementAttributeValue
     * Valor do atributo correspondente ao lado esquerdo do documento
     * @param rightElementAttributeValue 
     * Valor do atributo correspondente ao lado direito do documento
     */
    public void addAttribute(String attributeName, String leftElementAttributeValue, String rightElementAttributeValue) {
        //condição necessária para verificar se os elementos roots dos documentos possui namedspace ou não.
        //Se possuir, deve ser criado cada namespace encontrado no diff resultante.
        // No futuro, criar esta verificação a parte para que não possa ser perguntado a todos os seus filhos.
        if (attributeName.contains("xmlns:")) {
            if (leftElementAttributeValue.equals(rightElementAttributeValue)) {
                ((Element) this.diffNode).setAttributeNS(XML.ENDERECO_NAMESPACE, attributeName, leftElementAttributeValue);
            } else {
                if (!leftElementAttributeValue.isEmpty()) {
                    ((Element) this.diffNode).setAttributeNS(XML.ENDERECO_NAMESPACE, attributeName, leftElementAttributeValue);
                }
                if (!rightElementAttributeValue.isEmpty()) {
                    ((Element) this.diffNode).setAttributeNS(XML.ENDERECO_NAMESPACE, attributeName, rightElementAttributeValue);
                }
            }
            // *** fim ***    
            if (leftElementAttributeValue.isEmpty() || rightElementAttributeValue.isEmpty()) {
                if (leftElementAttributeValue.isEmpty()) {
                    ((Element) this.diffNode).setAttributeNS(NAMESPACE, LEFT_PREFIX + attributeName, leftElementAttributeValue);
                } else {
                    ((Element) this.diffNode).setAttributeNS(NAMESPACE, RIGHT_PREFIX + attributeName, rightElementAttributeValue);
                }
            } else {
                if (leftElementAttributeValue.equals(rightElementAttributeValue)) {
                    ((Element) this.diffNode).setAttribute(attributeName, leftElementAttributeValue);
                } else {
                    ((Element) this.diffNode).setAttributeNS(NAMESPACE, LEFT_PREFIX + attributeName, leftElementAttributeValue);
                    ((Element) this.diffNode).setAttributeNS(NAMESPACE, RIGHT_PREFIX + attributeName, rightElementAttributeValue);
                }
            }
        }
    }

    public void addSimilarityAttribute() {
        ((Element) this.diffNode).setAttributeNS(NAMESPACE, DIFF_PREFIX + "similarity", Float.toString(similarity));
    }

    public void addSideAttribute(String nodeSide) {
        ((Element) this.diffNode).setAttributeNS(NAMESPACE, DIFF_SIDE, nodeSide);
    }

    /**
     * Método criado para inserir todos os atributos em um elemento. A diferença
     * deste método com os demais é que este só será usado para indicar os
     * atributos que pertence a um determinado elemento sendo que este é...
     * 
     * @param atributo 
     */
    public void addAtribute(Node atributo) {
        ((Element) this.diffNode).setAttributeNS(NAMESPACE, atributo.getNodeName(), atributo.getNodeValue());
    }

    public void addChildren(Diff anotherDiff) {
        this.diffNode.appendChild(anotherDiff.getDiffNode());
    }

    public float getSimilarity() {
        return similarity;
    }

    public Node getDiffNode() {
        return diffNode;
    }

    public void setSimilarity(float similarity) {
        this.similarity = similarity > 1 ? 1 : similarity;
        addSimilarityAttribute();
    }

    public Diff varreTodosSubelementosParaMostrar(Node nodeCorrente) {
        if (nodeCorrente.hasChildNodes()) {
            NodeList subElementos = nodeCorrente.getChildNodes();
            for (int i = 0; i < subElementos.getLength(); i++) {
                Node filho = subElementos.item(i);
                if (filho.getNodeType() == Node.TEXT_NODE) {
                    this.diffNode.setTextContent(filho.getNodeValue());
                } else if (filho.getNodeType() == Node.ELEMENT_NODE) {
                    if (filho.hasChildNodes()) {
//                        novoDiff = varreTodosSubelementosParaMostrar(novoDiff, filho);
                    }
                    this.addChildren(new Diff(nodeCorrente.getChildNodes().item(i)));
                }
            }
            return this;
        }
        return this;
    }
}

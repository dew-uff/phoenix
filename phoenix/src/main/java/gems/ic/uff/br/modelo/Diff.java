package gems.ic.uff.br.modelo;

import gems.ic.uff.br.modelo.similar.SimilarNode;
import java.util.Stack;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

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

    //TODO:CRIAR SEMPRE OS ELEMENTOS, MESMO QUE UM TENHA STRING E O OUTRO NAO.
    public void setValue(String leftElementValue, String rightElementValue) {
        if (leftElementValue != null || rightElementValue != null) {
            if (leftElementValue != null && rightElementValue != null) {
                if (leftElementValue.equals(rightElementValue)) {
                    this.diffNode.setTextContent(leftElementValue);
                } else {
                    Element valueNode = (Element) DiffXML.createNode("value");
                    valueNode.setAttributeNS(NAMESPACE, DIFF_PREFIX + "left",
                            (leftElementValue != null
                            && !leftElementValue.contains("\n")) ? leftElementValue : "null");
                    
                    valueNode.setAttributeNS(NAMESPACE, DIFF_PREFIX + "right",
                            (rightElementValue != null
                            && !rightElementValue.contains("\n")) ? rightElementValue : "null");
                    this.diffNode.appendChild(valueNode);
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

    public void addAttribute(String attributeName, String firstElementAttributeValue, String secondElementAttributeValue) {
        if (firstElementAttributeValue.isEmpty() || secondElementAttributeValue.isEmpty()) {
            if (firstElementAttributeValue.isEmpty()) {
                ((Element) this.diffNode).setAttributeNS(NAMESPACE, LEFT_PREFIX + attributeName, firstElementAttributeValue);
            } else {
                ((Element) this.diffNode).setAttributeNS(NAMESPACE, RIGHT_PREFIX + attributeName, secondElementAttributeValue);
            }
        } else {
            if (firstElementAttributeValue.equals(secondElementAttributeValue)) {
                ((Element) this.diffNode).setAttribute(attributeName, firstElementAttributeValue);
            } else {
                ((Element) this.diffNode).setAttributeNS(NAMESPACE, LEFT_PREFIX + attributeName, firstElementAttributeValue);
                ((Element) this.diffNode).setAttributeNS(NAMESPACE, RIGHT_PREFIX + attributeName, secondElementAttributeValue);
            }
        }
    }

    public void addSimilarityAttribute() {
        ((Element) this.diffNode).setAttributeNS(NAMESPACE, DIFF_PREFIX + "similarity", Float.toString(similarity));
    }

    public void addSideAttribute(String nodeSide) {
        ((Element) this.diffNode).setAttributeNS(NAMESPACE, DIFF_SIDE, nodeSide);
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
}

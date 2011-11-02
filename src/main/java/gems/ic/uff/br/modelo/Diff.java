package gems.ic.uff.br.modelo;

import gems.ic.uff.br.modelo.similar.SimilarNode;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class Diff {
    
    public static final String NAMESPACE = "ic.uff.br/xmldiff";
    public static final String DIFF_PREFIX = "diff:";
    public static final String LEFT_PREFIX = "left:";
    public static final String RIGHT_PREFIX = "right:";

    private float similarity;
    private Node diffNode;
    private SimilarNode firstNode;
    private SimilarNode secondNode;

    //Esses construtores são POGs!
    public Diff(float similarity) {
        this.similarity = similarity;
    }

    public Diff(Node node) {
        this.diffNode = DiffXML.getInstance().getDocument().createElement(node.getNodeName());
        this.firstNode = new SimilarNode(node);
    }

    public Diff(Node firstNode, Node otherNode) {
        this.diffNode = DiffXML.getInstance().getDocument().createElement(firstNode.getNodeName());
        this.firstNode = new SimilarNode(firstNode);
        this.secondNode = new SimilarNode(otherNode);
    }

    public Diff(SimilarNode firstNode, SimilarNode otherNode) {
        this.diffNode = DiffXML.getInstance().getDocument().createElement(firstNode.getNode().getNodeName());
        this.firstNode = firstNode;
        this.secondNode = otherNode;
    }

    public void setValue(String firstElementValue, String secondElementValue) {
        if (firstElementValue != null || secondElementValue != null) {
            if (firstElementValue != null && secondElementValue != null) {
                if (firstElementValue.equals(secondElementValue)) {
                    this.diffNode.setTextContent(firstElementValue);
                } else {
                    Element valueNode = (Element) DiffXML.createNode("value");
                    valueNode.setAttributeNS(NAMESPACE, DIFF_PREFIX + "left", firstElementValue);
                    valueNode.setAttributeNS(NAMESPACE, DIFF_PREFIX + "right", secondElementValue);
                    this.diffNode.appendChild(valueNode);
                }
            } else {
                if (firstElementValue != null) {
                    this.diffNode.setTextContent(firstElementValue);
                } else {
                    this.diffNode.setTextContent(secondElementValue);
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

    private void addSimilarityAttribute() {
        ((Element) this.diffNode).setAttributeNS(NAMESPACE, DIFF_PREFIX + "similarity", Float.toString(similarity));
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

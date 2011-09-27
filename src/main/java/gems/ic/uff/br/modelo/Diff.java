package gems.ic.uff.br.modelo;

import gems.ic.uff.br.modelo.similar.SimilarNode;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class Diff {

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
                    Node valueNode = DiffXML.createLeftSideNode("value");
                    valueNode.setTextContent(firstElementValue);
                    this.diffNode.appendChild(valueNode);

                    valueNode = DiffXML.createRightSideNode("value");
                    valueNode.setTextContent(secondElementValue);
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
                ((Element) this.diffNode).setAttributeNS("left", attributeName, firstElementAttributeValue);
            } else {
                ((Element) this.diffNode).setAttributeNS("right", attributeName, secondElementAttributeValue);
            }
        } else {
            if (firstElementAttributeValue.equals(secondElementAttributeValue)) {
                ((Element) this.diffNode).setAttribute(attributeName, firstElementAttributeValue);
            } else {
                ((Element) this.diffNode).setAttributeNS("left", attributeName, firstElementAttributeValue);
                ((Element) this.diffNode).setAttributeNS("right", attributeName, secondElementAttributeValue);
            }
        }
    }

    private void addSimilarityAttribute() {
        ((Element) this.diffNode).setAttributeNS("diff", "similarity", Float.toString(similarity));
    }

    public void addChildren(Diff anotherDiff) {
        this.diffNode.appendChild(anotherDiff.getDiffNode());
    }

    public float getSimilarity() {
        return similarity;
    }

    public void setSimilarity(float similarity) {
        this.similarity = similarity > 1 ? 1 : similarity;
        addSimilarityAttribute();
    }

    public Node getDiffNode() {
        return diffNode;
    }
}

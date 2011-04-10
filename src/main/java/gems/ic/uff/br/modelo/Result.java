package gems.ic.uff.br.modelo;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class Result {
    private float similarity;
    private Node node;

    public Result(float similarity) {
        this.similarity = similarity;
    }

    public Result(Node node) {
        this.node = ResultXML.getInstance().getDocument().createElement(node.getNodeName());
    }

    public void setValue(String firstElementValue, String secondElementValue) {
        if (firstElementValue != null || secondElementValue != null) {
            if (firstElementValue != null && secondElementValue != null) {
                if (firstElementValue.equals(secondElementValue)) {
                    this.node.setTextContent(firstElementValue);
                } else {
                    Node valueNode = ResultXML.createLeftNode("value");
                    valueNode.setTextContent(firstElementValue);
                    this.node.appendChild(valueNode);

                    valueNode = ResultXML.createRightNode("value");
                    valueNode.setTextContent(secondElementValue);
                    this.node.appendChild(valueNode);
                }
            } else {
                if (firstElementValue != null) {
                    this.node.setTextContent(firstElementValue);
                } else {
                    this.node.setTextContent(secondElementValue);
                }
            }
        }
    }


    public void addAttribute(String attributeName, String firstElementAttributeValue, String secondElementAttributeValue) {
        if (firstElementAttributeValue.isEmpty() || secondElementAttributeValue.isEmpty()) {
            if (firstElementAttributeValue.isEmpty()) {
                ((Element) this.node).setAttributeNS("left", attributeName, firstElementAttributeValue);
            } else {
                ((Element) this.node).setAttributeNS("right", attributeName, secondElementAttributeValue);
            }
        } else {
            if (firstElementAttributeValue.equals(secondElementAttributeValue)) {
                ((Element) this.node).setAttribute(attributeName, firstElementAttributeValue);
            } else {
                ((Element) this.node).setAttributeNS("left", attributeName, firstElementAttributeValue);
                ((Element) this.node).setAttributeNS("right", attributeName, secondElementAttributeValue);
            }
        }
    }

    public float getSimilarity() {
        return similarity;
    }

    public void setSimilarity(float similarity) {
        this.similarity = similarity;
    }

    public Node getNode() {
        return node;
    }
}

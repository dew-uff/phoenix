package gems.ic.uff.br.modelo;

import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

//TODO: Quando a classe LcsXML for refatorada, integrar estas duas classes.
public class ResultXML {
    private static XML diff;

    public static XML getInstance() {
        if (diff == null) {
            try {
                diff = new XML(DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }
        }

        return diff;
    }

    private static Node createNode(String nodeName, boolean left) {
        String nameSpace = left ? "left" : "right";
        return getInstance().getDocument().createElementNS(nameSpace, nodeName);
    }

    public static Node createLeftNode(String nodeName) {
        return createNode(nodeName, true);
    }

    public static Node createRightNode(String nodeName) {
        return createNode(nodeName, false);
    }
}

package gems.ic.uff.br.modelo;

import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

//TODO: Quando a classe LcsXML for refatorada, integrar estas duas classes.
public class DiffXML {
    private static XML diff;
    //TODO: Colocar num enum.
    private static final String LEFT_SIDE = "left";
    private static final String RIGHT_SIDE = "right";
    private static final String BOTH_SIDE = "";

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

    private static Node createNode(String nodeName, String namespace) {
        if (namespace.equals(BOTH_SIDE)) {
            return getInstance().getDocument().createElement(nodeName);
        } else {
            return getInstance().getDocument().createElementNS(namespace, nodeName);
        }
    }

    public static Node createLeftSideNode(String nodeName) {
        return createNode(nodeName, LEFT_SIDE);
    }

    public static Node createRightSideNode(String nodeName) {
        return createNode(nodeName, RIGHT_SIDE);
    }

    public static Node createBothSideNode(String nodeName) {
        return createNode(nodeName, BOTH_SIDE);
    }
}

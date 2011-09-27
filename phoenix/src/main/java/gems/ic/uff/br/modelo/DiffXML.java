package gems.ic.uff.br.modelo;

import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

//TODO: Quando a classe LcsXML for refatorada, integrar estas duas classes.
public class DiffXML {

    private static XML diffXML;

    public static XML getInstance() {
        if (diffXML == null) {
            try {
                diffXML = new XML(DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }
        }

        return diffXML;
    }

    public static void restart() {
        try {
            diffXML = new XML(DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static Node createNode(String nodeName) {
        return getInstance().getDocument().createElementNS("diff", nodeName);
    }
}

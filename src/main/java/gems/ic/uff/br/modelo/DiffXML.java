package gems.ic.uff.br.modelo;

import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

//TODO: Quando a classe LcsXML for refatorada, integrar estas duas classes.
public class DiffXML {

    private static XML diffXML;

    public static XML getInstance() {
        if (diffXML == null) {
            restart();
        }

        return diffXML;
    }

    public static void restart() {
        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            docBuilderFactory.setNamespaceAware(true);

            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            DOMImplementation domImpl = docBuilder.getDOMImplementation();
            Document document = docBuilder.newDocument();
            
            //Essa seria a forma correta de se criar o DiffXML, entretanto, dessa forma,
            //teríamos que tratar o root de forma diferente. Pois usando essa forma,
            //um objeto root é criado ao criar o documento e o algoritmo de diff também
            //cria um outro root.
//            Document document = domImpl.createDocument("ic.uff.br/xmldiff", "diff:root", null);
//            document.getDocumentElement().setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:left", "ic.uff.br/xmldiff");
//            document.getDocumentElement().setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:right", "ic.uff.br/xmldiff");

            diffXML = new XML(document);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static Node createNode(String nodeName) {
        return getInstance().getDocument().createElementNS(Diff.NAMESPACE, Diff.DIFF_PREFIX + nodeName);
    }
}

package gems.ic.uff.br.modelo;

import java.io.*;
import javax.xml.parsers.*;
import javax.xml.xpath.*;
import org.w3c.dom.*;
//import org.w3c.dom.traversal.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.xml.sax.InputSource;

public class XML {
    
    public static final String ENDERECO_NAMESPACE = "http://www.w3.org/2000/xmlns/";

    private final Document document;
    private final XPathFactory factory;

    public XML(String file) {
        this.document = createDOMDocument(file);
        this.factory = XPathFactory.newInstance();
    }

    public XML(Document document) {
        this.document = document;
        this.factory = XPathFactory.newInstance();
    }

    private Document createDOMDocument(String file) {
        Document doc = null;

        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            docBuilderFactory.setNamespaceAware(true);

            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();

            if (file.endsWith(".xml")) { //Arquivo
                doc = docBuilder.parse(file);
            } else { //String
                doc = docBuilder.parse(new InputSource(new StringReader(file)));
            }

            removeWhiteSpaces(doc); //TODO: Testar! Utilizando o nosso comparador?
        } catch (Exception ignoredException) {
//            System.out.println(ignoredException.getMessage());
        }

        return doc;
    }

    public void removeWhiteSpaces(Document doc) {
//        doc.getDocumentElement().normalize();

        try {
            // XPath para procurar por nós com textos vazios
            XPathExpression xpathExp = factory.newXPath().compile("//text()[normalize-space(.) = '']");
            NodeList emptyTextNodes = (NodeList) xpathExp.evaluate(doc, XPathConstants.NODESET);

            // Remove cada texto vazio do documento
            for (int i = 0; i < emptyTextNodes.getLength(); i++) {
                Node emptyTextNode = emptyTextNodes.item(i);
                emptyTextNode.getParentNode().removeChild(emptyTextNode);
            }
        } catch (Exception ignoredException) {
//            System.out.println(""+ ignoredException.getMessage());
        }
    }

    public void addChildren(Diff anotherDiff) {
        document.appendChild(anotherDiff.getDiffNode());
    }

    public Document getDocument() {
        return document;
    }

    @Override
    public String toString() {
        Element root = document.getDocumentElement();
        
        //Adicionando os namespaces.
        if (root != null) {
            root.setAttributeNS(ENDERECO_NAMESPACE, "xmlns:diff", "ic.uff.br/xmldiff");
            root.setAttributeNS(ENDERECO_NAMESPACE, "xmlns:left", "ic.uff.br/xmldiff");
            root.setAttributeNS(ENDERECO_NAMESPACE, "xmlns:right", "ic.uff.br/xmldiff");
        }

        StringWriter writer = new StringWriter();

        try {
            Transformer serializer = TransformerFactory.newInstance().newTransformer();
            serializer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            serializer.setOutputProperty(OutputKeys.INDENT, "yes");
            serializer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            serializer.transform(new DOMSource(document), new StreamResult(writer));
        } catch (TransformerException ex) {
//            System.out.println(ex.getMessage());
        }

        return writer.toString();
    }
}

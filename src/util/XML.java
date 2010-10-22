package util;

import java.io.*;
import javax.xml.parsers.*;
import javax.xml.xpath.*;
import org.xml.sax.*;
import org.w3c.dom.*;
//import org.w3c.dom.traversal.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

public class XML {

    private final Document document;
    private final XPathFactory factory;

    public XML(String file) {
        this.document = createDOMDocument(file);
        this.factory = XPathFactory.newInstance();
    }

    private Document createDOMDocument(String file) {
        Document doc = null;

        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();

            //Parse the XML file and build the Document object in RAM
            if (file.endsWith(".xml")) {
                //Arquivo XML.
                doc = docBuilder.parse(file);
            } else {
                //String.
                doc = docBuilder.parse(new InputSource(new StringReader(file)));
            }

            //Normalize text representation.
            //Collapses adjacent text nodes into one node.
            //doc.getDocumentElement().normalize();
        } catch (Exception ex) {
            //TODO: Todas as exceções estão sendo 'tratadas', quando não conseguir
            //criar um XML, o que deveria acontecer?
            System.out.println(ex.getMessage());
        }

        return doc;
    }

    @Override
    public String toString() {
        StringWriter writer = new StringWriter();

        try {
            Transformer serializer = TransformerFactory.newInstance().newTransformer();
            //Omitindo a declaração do XML
            serializer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            
            serializer.transform(new DOMSource(document), new StreamResult(writer));
        } catch (TransformerException ex) {
            System.out.println(ex.getMessage());
        }

        return writer.toString();
    }

    public Document getDocument() {
        return document;
    }
}

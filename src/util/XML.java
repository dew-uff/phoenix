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

    public XML(String enderecoDoArquivo) throws ParserConfigurationException, SAXException,
            IOException, XPathExpressionException {
        this.document = readFile(enderecoDoArquivo);
        this.factory = XPathFactory.newInstance();
    }

    private Document readFile(String filePath) throws ParserConfigurationException,
            SAXException, IOException {
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();

        // Parse the XML file and build the Document object in RAM
        Document doc = docBuilder.parse(filePath);

        // Normalize text representation.
        // Collapses adjacent text nodes into one node.
        // doc.getDocumentElement().normalize();

        return doc;
    }

    @Override
    public String toString() {
        StringWriter writer = new StringWriter();

        try {
            Transformer serializer = TransformerFactory.newInstance().newTransformer();
            serializer.transform(new DOMSource(document), new StreamResult(writer));

        } catch (TransformerException ex) {
            System.out.println(ex.getMessage());
        }

        return writer.toString();
    }
}

package br.uff.ic.gems.phoenix;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import br.uff.ic.gems.phoenix.exception.XmlParserException;
import java.io.StringReader;
import org.xml.sax.InputSource;

public class XmlParser {
    
    public static Document createDOMDocument(String file) throws XmlParserException {
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

            removeWhitespaceNodes(doc.getDocumentElement());
        }
        catch (ParserConfigurationException ex) {
            throw new XmlParserException("Something wrong during parser creation: " + ex.getMessage(), ex);
        }
        catch (SAXException ex) {
            throw new XmlParserException("Something went wrong during xml parsing of '" + file + "': " + ex.getMessage(), ex);
        }
        catch (IOException ex) {
            throw new XmlParserException("Something went wrong while reading '" + file + "': " + ex.getMessage(), ex);
        }

        return doc;
    }
    
    private static void removeWhitespaceNodes(Element e) {
        NodeList children = e.getChildNodes();
        for (int i = children.getLength() - 1; i >= 0; i--) {
            Node child = children.item(i);
            if (child instanceof Text
                    && ((Text) child).getData().trim().length() == 0) {
                e.removeChild(child);
            } else if (child instanceof Element) {
                removeWhitespaceNodes((Element) child);
            }
        }
    }
}

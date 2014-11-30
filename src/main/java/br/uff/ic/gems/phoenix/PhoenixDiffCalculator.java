package br.uff.ic.gems.phoenix;

import java.io.OutputStream;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import br.uff.ic.gems.phoenix.diff.DiffNode;
import br.uff.ic.gems.phoenix.diff.ElementDiffNode;
import br.uff.ic.gems.phoenix.exception.ComparisonException;
import br.uff.ic.gems.phoenix.exception.PhoenixDiffException;
import br.uff.ic.gems.phoenix.exception.XmlParserException;
import br.uff.ic.gems.phoenix.similarity.ElementSimilarity;
import br.uff.ic.gems.phoenix.similarity.ElementSimilarityResult;

public class PhoenixDiffCalculator {
    
    private static Logger LOG = Logger.getLogger(PhoenixDiffCalculator.class.getName());
    
    public static final String ENDERECO_NAMESPACE = "http://www.w3.org/2000/xmlns/";

    private Document document1 = null,
                     document2 = null;

    // default is System.out
    private OutputStream out = System.out;
    
    /**
     * Constructor.
     * 
     * @param filepath1 File path for xml file 1
     * @param filepath2 File path for xml file 2
     */
    public PhoenixDiffCalculator(String filepath1, String filepath2) throws PhoenixDiffException {
        
        if (filepath1 == null || filepath2 == null ||
                filepath1.isEmpty() || filepath2.isEmpty()) {
            throw new PhoenixDiffException("At least one of the xml file paths is null or empty");
        }
        
        try {
            document1 = XmlParser.createDOMDocument(filepath1);
            
            document2 = XmlParser.createDOMDocument(filepath2);
        }
        catch (XmlParserException ex) {
            throw new PhoenixDiffException("Something wrong while parsing xml files!", ex.getCause());
        }
    }

    public void setOutputStream(OutputStream out) {
        this.out = out;
        
    }

    public void executeComparison() throws ComparisonException, PhoenixDiffException {
        
        long timestamp = System.currentTimeMillis();

        // compare diff roots
        Element e1 = document1.getDocumentElement();
        Element e2 = document2.getDocumentElement();
        ElementSimilarity ec = new ElementSimilarity(SettingsHelper.getIgnoreThresholdOnRoot());
        ElementSimilarityResult result = ec.compare(e1, e2);

        LOG.info("Time to compare the documents: " + (System.currentTimeMillis() - timestamp) + " ms.");
        timestamp = System.currentTimeMillis();
        
        processResult(result);
        
        LOG.info("Time to create result diff: " + (System.currentTimeMillis() - timestamp) + " ms.");
    }

    private void processResult(ElementSimilarityResult result) throws PhoenixDiffException {
        
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        
        try {
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            
            Document doc = docBuilder.newDocument();

            Element diffroot = doc.createElement("diffresult");
            doc.appendChild(diffroot);
            
            diffroot.setAttributeNS(ENDERECO_NAMESPACE, "xmlns:diff", DiffNode.DIFF_NAMESPACE);
            diffroot.setAttributeNS(ENDERECO_NAMESPACE, "xmlns:left", DiffNode.DIFF_NAMESPACE_LEFT);
            diffroot.setAttributeNS(ENDERECO_NAMESPACE, "xmlns:right", DiffNode.DIFF_NAMESPACE_RIGHT);
            diffroot.setAttributeNS(DiffNode.DIFF_NAMESPACE, DiffNode.DIFF_SIMILARITY, Double.toString(result.getRealSimilarity()));

            ElementDiffNode[] nodes = result.getResult();
            for (ElementDiffNode node : nodes) {
                Element elm = node.toXmlTree(doc);
                diffroot.appendChild(elm);
            }        
            
            printXmlToOutput(doc);
            
        } catch (DOMException e) {
            throw new PhoenixDiffException("Something went wrongn while creating output xml", e);
        } catch (ParserConfigurationException e) {
            throw new PhoenixDiffException("Something went wrongn while creating output xml", e);
        }
    }
    
    private void printXmlToOutput(Document doc) throws PhoenixDiffException {
        
        try {
            Transformer serializer = TransformerFactory.newInstance().newTransformer();
            serializer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            serializer.setOutputProperty(OutputKeys.INDENT, "yes");
            serializer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            serializer.transform(new DOMSource(doc), new StreamResult(out));
        } catch (TransformerException ex) {
            throw new PhoenixDiffException("Something went wrongn while creating output xml", ex);
        }
    }
}

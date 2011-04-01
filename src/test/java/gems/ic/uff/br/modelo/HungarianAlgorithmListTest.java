package gems.ic.uff.br.modelo;

import com.sun.org.apache.xpath.internal.NodeSet;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.junit.Before;
import java.util.List;
import gems.ic.uff.br.modelo.similar.Similar;
import gems.ic.uff.br.modelo.similar.SimilarNode;
import gems.ic.uff.br.modelo.similar.SimilarString;
import java.io.StringReader;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import static org.junit.Assert.*;
import org.xml.sax.SAXException;

public class HungarianAlgorithmListTest {

    private List<Similar> similarList;
    private List<Similar> otherSimilarList;

    public HungarianAlgorithmListTest() {
    }

    @Before
    public void setUp() {
        similarList = new ArrayList<Similar>();
        otherSimilarList = new ArrayList<Similar>();
    }

    public SimilarNode createSimilarNode(String xml) {
        try {
            return new SimilarNode(DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(xml))).getDocumentElement());
        } catch (Exception ex) {
            System.out.println(ex.getClass() + ": " + ex.getMessage());
            return null;
        }
    }

//    @Test
    public void similaridadeEntreElementosIguais() {
        similarList.add(new SimilarString("a"));

        assertEquals(1, new HungarianList(similarList, similarList).similaridade(), 0);
    }

//    @Test
    public void similaridadeEntreElementosDiferentes() {
        similarList.add(new SimilarString("a"));
        otherSimilarList.add(new SimilarString("b"));

        assertEquals(0, new HungarianList(similarList, otherSimilarList).similaridade(), 0);

    }

    @Test
    public void similaridadeEntreUmElementoIgualEUmDiferente() throws ParserConfigurationException, SAXException, IOException {
        similarList.add(new SimilarString("a"));
        similarList.add(new SimilarString("b"));
        similarList.add(new SimilarString("c"));
        otherSimilarList.add(new SimilarString("b"));
        otherSimilarList.add(new SimilarString("a"));
        otherSimilarList.add(new SimilarString("c"));
        assertEquals(0, new HungarianList(similarList, otherSimilarList).similaridade(), 0);
    }
}

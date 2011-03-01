package gems.ic.uff.br.modelo;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.InputSource;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.w3c.dom.Node;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import static org.junit.Assert.*;
import org.xml.sax.SAXException;
import uk.ac.shef.wit.simmetrics.similaritymetrics.*;

public class SimilarNodeTest {

    @Mock
    private Node mockNode;
    @Mock
    private Node mockNode2;

    public SimilarNodeTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() {
    }

    public SimilarNode createSimilarNode(String xml) {
        try {
            return new SimilarNode(DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(xml))).getDocumentElement());
        } catch (Exception ex) {
            System.out.println(ex.getClass() + ": " + ex.getMessage());
            return null;
        }
    }

//    @Test(expected = IllegalArgumentException.class)
    public void similaridadeEntreElementosInvalidos() {
        SimilarNode similarNode = createSimilarNode("<igual/>");
        similarNode.similar(new SimilarNode(null));
    }

//    @Test
    public void similaridadeEntreNomes() {
        SimilarNode similarNode = createSimilarNode("<igual/>");

        assertEquals(1, similarNode.elementsNameSimilarity(similarNode.getNode(), 1), 0);
    }

//    @Test
    public void similaridadeEntreNomes2() {
        SimilarNode similarNode = createSimilarNode("<igual></igual>");
        assertEquals(1, similarNode.elementsNameSimilarity(similarNode.getNode(), 1), 0);
    }

//    @Test
    public void similaridadeEntreNomes3() {
        SimilarNode similarNode = createSimilarNode("<diferente/>");
        SimilarNode similarNode2 = createSimilarNode("<diferente2/>");

        assertEquals(1 - SimilarNode.ELEMENT_NAME_WEIGTH, similarNode.elementsNameSimilarity(similarNode2.getNode(), 1), 0);
    }

//    @Test
    public void similaridadeEntreConteudoDasTags() {
        SimilarNode similarNode = createSimilarNode("<tag>Texto</tag>");
        SimilarNode similarNode2 = createSimilarNode("<tag>Texto</tag>");

        assertEquals(1, similarNode.elementsValueSimilarity(similarNode2.getNode(), 1), 0);
    }

//    @Test
    public void similaridadeEntreConteudoDasTags2() {
        SimilarNode similarNode = createSimilarNode("<tag>Texto</tag>");
        SimilarNode similarNode2 = createSimilarNode("<tag>Texto2</tag>");
        assertEquals(1 - SimilarNode.ELEMENT_VALUE_WEIGTH, similarNode.elementsValueSimilarity(similarNode2.getNode(), 1), 0);
    }

//    @Test
    public void similaridadeEntreConteudoDasTags3() {
        SimilarNode similarNode = createSimilarNode("<tag>Texto</tag>");
        SimilarNode similarNode2 = createSimilarNode("<tag></tag>");

        assertEquals(1 - SimilarNode.ELEMENT_VALUE_WEIGTH, similarNode.elementsValueSimilarity(similarNode2.getNode(), 1), 0);
    }

//    @Test
    public void similaridadeEntreConteudoDasTags4() {
        SimilarNode similarNode = createSimilarNode("<tag></tag>");
        SimilarNode similarNode2 = createSimilarNode("<tag>Texto</tag>");

        assertEquals(1 - SimilarNode.ELEMENT_VALUE_WEIGTH, similarNode.elementsValueSimilarity(similarNode2.getNode(), 1), 0);
    }

//    @Test
    public void similaridadeEntreAtributos() {
        SimilarNode similarNode = createSimilarNode("<igual atributo='sim'></igual>");
        SimilarNode similarNode2 = createSimilarNode("<igual></igual>");

        assertEquals(1 - SimilarNode.ATTRIBUTE_WEIGTH, similarNode.elementsAttributesSimilarity(similarNode2.getNode(), 1), 0);
    }

//    @Test
    public void similaridadeEntreAtributos2() {
        SimilarNode similarNode = createSimilarNode("<igual></igual>");
        SimilarNode similarNode2 = createSimilarNode("<igual atributo='sim'></igual>");

        assertEquals(1 - SimilarNode.ATTRIBUTE_WEIGTH, similarNode.elementsAttributesSimilarity(similarNode2.getNode(), 1), 0);
    }

//    @Test
    public void similaridadeEntreFilhosDoElemento() {
        SimilarNode similarNode = createSimilarNode("<father><son></son></father>");
        SimilarNode similarNode2 = createSimilarNode("<father><son></son></father>");

        assertEquals(1, similarNode.elementsChildrenSimilarity(similarNode2.getNode(), 1), 0);
    }

//    @Test
    public void similaridadeEntreFilhosDoElemento2() {
        SimilarNode similarNode = createSimilarNode("<father><son></son></father>");
        SimilarNode similarNode2 = createSimilarNode("<father><son2></son2></father>");

        assertEquals(1 - SimilarNode.ELEMENT_CHILDREN_WEIGTH, similarNode.elementsChildrenSimilarity(similarNode2.getNode(), 1), 0);
    }

//    @Test
    public void similaridadeEntreFilhosDoElementoNaoDeveriaContarElementosSemSerDoTipoElementNode() {
        SimilarNode similarNode = createSimilarNode("<father><son></son></father>");
        SimilarNode similarNode2 = createSimilarNode("<father>Texto<son></son></father>");

        assertEquals(1, similarNode.elementsChildrenSimilarity(similarNode2.getNode(), 1), 0);
    }

//    @Test
    public void similaridadeNoMinimoIgualA0() {
        double similaridade = 1;
        similaridade -= SimilarNode.ELEMENT_NAME_WEIGTH;
        similaridade -= SimilarNode.ELEMENT_VALUE_WEIGTH;
        similaridade -= SimilarNode.ATTRIBUTE_WEIGTH;

        assertTrue(similaridade < 0);


        SimilarNode similarNode = createSimilarNode("<tag attribute='yes'>Texto</tag>");
        SimilarNode similarNode2 = createSimilarNode("<tag2>TextoDiferente</tag2>");

        assertEquals(0, similarNode.similar(similarNode2), 0);
    }

    @Test
    public void similaridadeEntreConjuntos() {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document d = db.parse(new InputSource(new StringReader("<raiz><nome></nome><end><rua></rua><cidade></cidade></end></raiz>")));
            Document d2 = db.parse(new InputSource(new StringReader("<raiz><CPF></CPF><end><rua></rua><cidade></cidade></end><nome></nome></raiz>")));

            int contadorDeSimilaridade = 0;
            NodeList nl = d.getElementsByTagName("*");
            NodeList nl2 = d.getElementsByTagName("*");
            if (nl.getLength() <= nl2.getLength()) {

                for (int i = 0; i < nl.getLength(); i++) {
                    NodeList noCorrente = d2.getElementsByTagName("" + nl.item(i).getNodeName());
                    if (noCorrente != null) {
                        contadorDeSimilaridade++;
                    }
                    noCorrente = null;
                }
            } else {
                for (int i = 0; i < nl2.getLength(); i++) {
                    NodeList noCorrente = d.getElementsByTagName("" + nl2.item(i).getNodeName());
                    if (noCorrente == null) {
                        contadorDeSimilaridade++;
                    }
                    noCorrente = null;
                }
            }
            float x = nl.getLength();
            System.out.println("PERCENTUAL DE SIMILARIDADE =>  " + contadorDeSimilaridade / x);


//        SimilarNode similarNode = createSimilarNode("<raiz><nome></nome><CPF></CPF><end><rua></rua><cidade></cidade></end></raiz>");
//        SimilarNode similarNode2 = createSimilarNode("<raiz><nome></nome><CPF></CPF><end><rua></rua><cidade></cidade></end></raiz>");
//
//        assertEquals(1, similarNode.elementsChildrenSimilarity(similarNode2.getNode(), 1), 0);
            } catch (SAXException ex) {
            Logger.getLogger(SimilarNodeTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SimilarNodeTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(SimilarNodeTest.class.getName()).log(Level.SEVERE, null, ex);
        }
//        SimilarNode similarNode = createSimilarNode("<raiz><nome></nome><CPF></CPF><end><rua></rua><cidade></cidade></end></raiz>");
//        SimilarNode similarNode2 = createSimilarNode("<raiz><nome></nome><CPF></CPF><end><rua></rua><cidade></cidade></end></raiz>");
//
//        assertEquals(1, similarNode.elementsChildrenSimilarity(similarNode2.getNode(), 1), 0);
    }

//    @Test
    public void imprimirTodosFilhos() throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document d = db.parse(new InputSource(new StringReader("<raiz><nome></nome><CPF></CPF><end><rua></rua><cidade></cidade></end></raiz>")));

        NodeList nl = d.getElementsByTagName("*");
        Node n = nl.item(0);
        System.out.println("RAIZ ========== ");

        System.out.println("" + n.getNodeName());
        if (n.hasChildNodes()) {
            imprimir(n.getFirstChild());
        }
        System.out.println("FIMMMMMMMMMMM");
    }

    private void imprimir(Node firstChild) {
        System.out.println("PROXIMO NO ===========");
        if (firstChild != null) {
            System.out.println("" + firstChild.getNodeName());
            if (firstChild.hasChildNodes()) {
                imprimir(firstChild.getFirstChild());
            } else {
                imprimir(firstChild.getNextSibling());
            }

        }
    }
}

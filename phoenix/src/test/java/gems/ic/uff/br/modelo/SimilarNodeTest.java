package gems.ic.uff.br.modelo;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.InputSource;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilderFactory;
import gems.ic.uff.br.modelo.SimilarNode;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.w3c.dom.Node;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.xml.sax.SAXException;

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

    @Test
    public void oPesoDoAtributoDeveriaSer05() {
        assertEquals(0.5, SimilarNode.ATTRIBUTE_WEIGTH, 0);
    }

    @Test
    public void deveriaTerSimilaridade1CasoOsDoisPossuamApenasUmaTagVaziaEIgual() {
        SimilarNode similarNode = createSimilarNode("<igual/>");

        assertEquals(1, similarNode.similar(similarNode), 0);
    }

    @Test
    public void deveriaTerSimilaridade0CasoNenhumaTagSejaIgual() {
        SimilarNode similarNode = createSimilarNode("<diferente/>");
        SimilarNode similarNode2 = createSimilarNode("<diferente2/>");

        assertEquals(0, similarNode.similar(similarNode2), 0);
    }

    @Test
    public void deveriaTerSimilaridade1CasoAsTagsSejamIguaisENaoPossuamAtributos() {
        SimilarNode similarNode = createSimilarNode("<igual></igual>");

        assertEquals(1, similarNode.similar(similarNode), 0);
    }

    @Test
    public void deveriaTerSimilaridade05CasoAsTagsSejamIguaisMasApenasOPrimeiroTenhaAtributo() {
        SimilarNode similarNode = createSimilarNode("<igual atributo='sim'></igual>");
        SimilarNode similarNode2 = createSimilarNode("<igual></igual>");

        assertEquals(0.5, similarNode.similar(similarNode2), 0);
    }

    @Test
    public void deveriaTerSimilaridade05CasoAsTagsSejamIguaisMasApenasOSegundoTenhaAtributo() {
        SimilarNode similarNode = createSimilarNode("<igual></igual>");
        SimilarNode similarNode2 = createSimilarNode("<igual atributo='sim'></igual>");

        assertEquals(0.5, similarNode.similar(similarNode2), 0);
    }

    @Test
    public void similaridadeEntreConteudoDasTags() {
        SimilarNode similarNode = createSimilarNode("<tag>Texto</tag>");
        SimilarNode similarNode2 = createSimilarNode("<tag>Texto</tag>");

        assertEquals(1, similarNode.similar(similarNode2), 0);
    }
    
    @Test
    public void similaridadeEntreConteudoDasTags2() {
        SimilarNode similarNode = createSimilarNode("<tag>Texto</tag>");
        SimilarNode similarNode2 = createSimilarNode("<tag>Texto2</tag>");

        assertEquals(SimilarNode.ELEMENT_VALUE_WEIGTH, similarNode.similar(similarNode2), 0);
    }
    
    @Test
    public void similaridadeEntreConteudoDasTags3() {
        SimilarNode similarNode = createSimilarNode("<tag>Texto</tag>");
        SimilarNode similarNode2 = createSimilarNode("<tag></tag>");

        assertEquals(SimilarNode.ELEMENT_VALUE_WEIGTH, similarNode.similar(similarNode2), 0);
    }
    
    @Test
    public void similaridadeEntreConteudoDasTags4() {
        SimilarNode similarNode = createSimilarNode("<tag></tag>");
        SimilarNode similarNode2 = createSimilarNode("<tag>Texto</tag>");

        assertEquals(SimilarNode.ELEMENT_VALUE_WEIGTH, similarNode.similar(similarNode2), 0);
    }
}

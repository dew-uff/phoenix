package util;

import gems.ic.uff.br.modelo.XML;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class XMLTest {

    public XMLTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void deveriaCriarUmXmlAtravesDeUmXmlEmString() {
        XML xml = new XML("<tag>content</tag>");
        assertNotNull(xml.getDocument());
    }

    //TODO: Tem como remover a dependencia do arquivo?
    //ou é necessário criar esse arquivo na pasta de teste?
    @Test
    public void deveriaCriarUmXmlAtravesDeUmArquivoXML() {
        XML xml = new XML("books.xml");
        assertNotNull(xml.getDocument());
    }

    @Test
    public void naoDeveriaCriarUmXmlSeOArquivoXMLNaoExistir() {
        XML xml = new XML("arquivo.xml");
        assertNull(xml.getDocument());
    }

    @Test
    public void deveriaImprimirOXmlIgualAoOriginal() {
        XML xml = new XML("<tag>content</tag>");

        assertEquals("<tag>content</tag>", xml.toString());
    }
}

package gems.ic.uff.br.modelo;

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
    public void deveriaSerCriadoAtravesDeUmaString() {
        XML xml = new XML("<root><tag/></root>");
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
}

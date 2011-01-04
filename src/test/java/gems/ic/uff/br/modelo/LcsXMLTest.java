package gems.ic.uff.br.modelo;

import gems.ic.uff.br.modelo.XML;
import gems.ic.uff.br.modelo.LcsXML;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.w3c.dom.Node;

public class LcsXMLTest {

    private XML xml1;
    private XML xml2;
    private XML xmlInterno1;
    private XML xmlInterno2;

    public LcsXMLTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        xml1 = new XML("<root><tag>content</tag></root>");
        xml2 = new XML("<root><tag>content</tag></root>");
    }

    @After
    public void tearDown() {
    }

    @Test
    public void deveriaTerOTamanhoDeXComoSendoONumeroDeNosDoPrimeiroXML() {
        xmlInterno1 = new XML("<root><tag>content</tag></root>");

        LcsXML lcs = new LcsXML(xmlInterno1, xml2);
        assertEquals(1, lcs.lengthOfX());
    }

    @Test
    public void deveriaTerOTamanhoDeYComoSendoONumeroDeNosDoSegundoXML() {
        xmlInterno2 = new XML("<root><tag>content</tag><tag2>content2</tag2></root>");

        LcsXML lcs = new LcsXML(xml1, xmlInterno2);
        assertEquals(2, lcs.lengthOfY());
    }

    @Test
    public void naoDeveriaContarParaOTamanhoOsNosEncadeados() {
        xmlInterno1 = new XML("<root><tag><nestedTag>content</nestedTag></tag></root>");

        LcsXML lcs = new LcsXML(xmlInterno1, xml2);
        assertEquals(1, lcs.lengthOfX());
    }

    @Test
    public void deveriaRetornarONoDoIndicePassadoPorParametroEmX() {
        xmlInterno1 = new XML("<root><tag>content</tag><tag2>content2</tag2></root>");


        LcsXML lcs = new LcsXML(xmlInterno1, xml2);
        assertEquals("[tag: null]", lcs.valueOfX(0).toString());
    }

    @Test
    public void deveriaRetornarONoDoIndicePassadoPorParametroEmY() {
        xmlInterno2 = new XML("<root><tag>content</tag><tag2>content2</tag2></root>");


        LcsXML lcs = new LcsXML(xml1, xmlInterno2);
        assertEquals("[tag2: null]", lcs.valueOfY(1).toString());
    }

    @Test
    public void deveriaTerOValorDoLcsIgualAoNumeroDeNosSeOsXmlsForemIguais() {
        xmlInterno1 = new XML("<root><tag>content</tag><tag2>content2</tag2></root>");


        LcsXML lcs = new LcsXML(xmlInterno1, xmlInterno1);
        assertEquals(2, lcs.getLcsLength());
    }

    @Test
    public void similaridade100Porcento() {
        xmlInterno1 = new XML("<root><tag>content</tag><tag2>content2</tag2></root>");
        xmlInterno2 = new XML("<root><tag>content</tag><tag2>content2</tag2></root>");

        LcsXML lcs = new LcsXML(xmlInterno1, xmlInterno2);
        assertEquals(1, lcs.similaridade(), 0);
    }

    @Test
    public void similaridade50Porcento() {
        xmlInterno1 = new XML("<root><tag>content</tag><tag2>content2</tag2></root>");
        xmlInterno2 = new XML("<root><tag>content</tag><tag3>content2</tag3></root>");

        LcsXML lcs = new LcsXML(xmlInterno1, xmlInterno2);
        assertEquals(0.5, lcs.similaridade(), 0);
    }

    @Test
    public void similaridade66Porcento() {
        xmlInterno1 = new XML("<root><tag>content</tag><tag2>content2</tag2></root>");
        xmlInterno2 = new XML("<root><tag>content</tag></root>");

        LcsXML lcs = new LcsXML(xmlInterno1, xmlInterno2);
        assertEquals(0.6, lcs.similaridade(), 0.1);
    }
}

package gems.ic.uff.br.modelo;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

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
    public void deveriaTerOTamanhoDeXComoSendo1CasoSoPossuaElementoRoot() {
        xmlInterno1 = new XML("<root></root>");

        LcsXML lcs = new LcsXML(xmlInterno1, xml2);
        assertEquals(1, lcs.lengthOfX());
    }
    
    @Test
    public void deveriaTerOTamanhoDeXComoSendoONumeroDeNosDoPrimeiroXMLMaisORoot() {
        xmlInterno1 = new XML("<root><tag>content</tag></root>");

        LcsXML lcs = new LcsXML(xmlInterno1, xml2);
        assertEquals(2, lcs.lengthOfX());
    }

    @Test
    public void deveriaTerOTamanhoDeYComoSendoONumeroDeNosDoSegundoXMLMaisORoot() {
        xmlInterno2 = new XML("<root><tag>content</tag><tag2>content2</tag2></root>");

        LcsXML lcs = new LcsXML(xml1, xmlInterno2);
        assertEquals(3, lcs.lengthOfY());
    }

    @Test
    public void naoDeveriaContarParaOTamanhoOsNosEncadeados() {
        xmlInterno1 = new XML("<root><tag><nestedTag>content</nestedTag></tag></root>");

        LcsXML lcs = new LcsXML(xmlInterno1, xml2);
        assertEquals(2, lcs.lengthOfX());
    }

    @Test
    public void deveriaRetornarORootCasoOIndicePassadoPorParametroEmXSeja0() {
        xmlInterno1 = new XML("<root><tag>content</tag><tag2>content2</tag2></root>");


        LcsXML lcs = new LcsXML(xmlInterno1, xml2);
        assertEquals("[root: null]", lcs.valueOfX(0).toString());
    }
    
    @Test
    public void deveriaRetornarONoDoIndicePassadoPorParametroEmX() {
        xmlInterno1 = new XML("<root><tag>content</tag><tag2>content2</tag2></root>");


        LcsXML lcs = new LcsXML(xmlInterno1, xml2);
        assertEquals("[tag: null]", lcs.valueOfX(1).toString());
    }

    @Test
    public void deveriaRetornarONoDoIndicePassadoPorParametroEmY() {
        xmlInterno2 = new XML("<root><tag>content</tag><tag2>content2</tag2></root>");


        LcsXML lcs = new LcsXML(xml1, xmlInterno2);
        assertEquals("[tag2: null]", lcs.valueOfY(2).toString());
    }

    @Test
    public void deveriaTerOValorDoLcsIgualAoNumeroDeNosMaisORootSeOsXmlsForemIguais() {
        xmlInterno1 = new XML("<root><tag>content</tag><tag2>content2</tag2></root>");


        LcsXML lcs = new LcsXML(xmlInterno1, xmlInterno1);
        assertEquals(3, lcs.getLcsLength());
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
        xmlInterno1 = new XML("<root><tag>content</tag></root>");
        xmlInterno2 = new XML("<root><tag2>content</tag2></root>");

        LcsXML lcs = new LcsXML(xmlInterno1, xmlInterno2);
        assertEquals(0.5, lcs.similaridade(), 0);
    }

    @Test
    public void similaridade66Porcento() {
        xmlInterno1 = new XML("<root><tag>content</tag></root>");
        xmlInterno2 = new XML("<root></root>");

        LcsXML lcs = new LcsXML(xmlInterno1, xmlInterno2);
        assertEquals(0.6, lcs.similaridade(), 0.1);
    }
    
    /*
     * Testes de XMLs específicos
     */
    
    @Test
    public void similaridadeEntreRoots() {
        xmlInterno1 = new XML("<root></root>");
        xmlInterno2 = new XML("<root></root>");

        LcsXML lcs = new LcsXML(xmlInterno1, xmlInterno2);
        assertEquals(1, lcs.similaridade(), 0);
    }
    
    @Test
    public void similaridadeEntreRoots2() {
        xmlInterno1 = new XML("<root></root>");
        xmlInterno2 = new XML("<root2></root2>");

        LcsXML lcs = new LcsXML(xmlInterno1, xmlInterno2);
        assertEquals(0, lcs.similaridade(), 0);
    }
    
    //Tags - Início
    
    @Test
    public void similaridadeEntreTagsVazias() {
        xmlInterno1 = new XML("<root><tag></tag></root>");
        xmlInterno2 = new XML("<root><tag></tag></root>");

        LcsXML lcs = new LcsXML(xmlInterno1, xmlInterno2);
        assertEquals(1, lcs.similaridade(), 0);
    }
    
    @Test
    public void similaridadeEntreTagsVazias2() {
        xmlInterno1 = new XML("<root><tag></tag></root>");
        xmlInterno2 = new XML("<root><tag2></tag2></root>");

        LcsXML lcs = new LcsXML(xmlInterno1, xmlInterno2);
        assertEquals(0.5, lcs.similaridade(), 0);
    }
    
    @Test
    public void similaridadeEntreTagsVazias3() {
        xmlInterno1 = new XML("<root><tag/></root>");
        xmlInterno2 = new XML("<root><tag2/></root>");

        LcsXML lcs = new LcsXML(xmlInterno1, xmlInterno2);
        assertEquals(0.5, lcs.similaridade(), 0);
    }
    
//    @Test
//    public void similaridadeEntreConteudoDasTags() {
//        xmlInterno1 = new XML("<root><tag>Texto</tag></root>");
//        xmlInterno2 = new XML("<root><tag>Texto</tag></root>");
//
//        LcsXML lcs = new LcsXML(xmlInterno1, xmlInterno2);
//        assertEquals(0.5, lcs.similaridade(), 0);
//    }
    
    //Tags - Fim
}

package gems.ic.uff.br.modelo;

import gems.ic.uff.br.modelo.similar.SimilarNode;
import gems.ic.uff.br.settings.SettingsHelper;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class LcsXMLTest {

    private XML xml1;
    private XML xml2;

    public LcsXMLTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        SettingsHelper.setNameSimilarityRequired(true);
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
    public void deveriaTerOTamanhoDeXComoSendo1CasoSoPossuaElementoRoot() {
        xml1 = new XML("<root></root>");
        xml2 = new XML("<root><tag>content</tag></root>");

        LcsXML lcs = new LcsXML(xml1, xml2);
        assertEquals(1, lcs.lengthOfX());
    }

    @Test
    public void deveriaTerOTamanhoDeXComoSendo1CasoPossuaOutrosElementosAlemDoRoot() {
        xml1 = new XML("<root><tag>content</tag></root>");
        xml2 = new XML("<root><tag>content</tag></root>");

        LcsXML lcs = new LcsXML(xml1, xml2);
        assertEquals(1, lcs.lengthOfX());
    }

    @Test
    public void deveriaRetornarORootCasoOIndicePassadoPorParametroEmXSeja0() {
        xml1 = new XML("<root><tag>content</tag><tag2>content2</tag2></root>");
        xml2 = new XML("<root><tag>content</tag></root>");


        LcsXML lcs = new LcsXML(xml1, xml2);
        assertEquals("root", lcs.valueOfX(0).toString());
    }

    @Test
    public void testeCompleto() {
        xml1 = new XML("<root></root>");
        xml2 = new XML("<root></root>");

        LcsXML lcs = new LcsXML(xml1, xml2);
        assertEquals(1, lcs.similaridade(), 0);
    }

    @Test
    public void testeCompleto2() {
        xml1 = new XML("<root></root>");
        xml2 = new XML("<root2></root2>");

        LcsXML lcs = new LcsXML(xml1, xml2);
        assertEquals(0, lcs.similaridade(), 0);
    }

    @Test
    public void testeCompleto3() {
        xml1 = new XML("<root><tag>content</tag><tag2>content2</tag2></root>");
        xml2 = new XML("<root><tag>content</tag><tag2>content2</tag2></root>");

        LcsXML lcs = new LcsXML(xml1, xml2);
        assertEquals(1, lcs.similaridade(), 0);
    }

//    @Test
//    public void testeCompleto4() {
//        xml1 = new XML("<root><tag></tag><tag2></tag2></root>");
//        xml2 = new XML("<root><tag><tag2></tag2></tag></root>");
//
//        LcsXML lcs = new LcsXML(xml1, xml2);
//        assertEquals(1, lcs.similaridade(), 0);
//    }
//    @Test
//    public void testeCompleto4() {
//        xml1 = new XML("<root><tag>content</tag></root>");
//        xml2 = new XML("<root><tag2>content</tag2></root>");
//        LcsXML lcs = new LcsXML(xml1, xml2);
//
//        double experado = (1 - (1 - SimilarNode.ELEMENT_NAME_WEIGTH)) / 2;
//        assertEquals(experado, lcs.similaridade(), 0);
//    }
//    @Test
//    public void testeCompleto5() {
//        xmlInterno1 = new XML("<root><tag attribute='yes'>content</tag></root>");
//        xmlInterno2 = new XML("<root><tag>content</tag></root>");
//        LcsXML lcs = new LcsXML(xmlInterno1, xmlInterno2);
//        
//        double experado = (1 - (1 - SimilarNode.ATTRIBUTE_WEIGTH)) / 2;
//        assertEquals(experado, lcs.similaridade(), 0);
//    }
//
//    @Test
//    public void testeCompleto6() {
//        xmlInterno1 = new XML("<root><tag attribute='yes'>content2</tag></root>");
//        xmlInterno2 = new XML("<root><tag>content</tag></root>");
//        LcsXML lcs = new LcsXML(xmlInterno1, xmlInterno2);
//        
//        double experado = (1 - (1 - SimilarNode.ATTRIBUTE_WEIGTH - SimilarNode.ELEMENT_VALUE_WEIGTH)) / 2;
//        assertEquals(experado, lcs.similaridade(), 0);
//    }
//
//    @Test
//    public void testeCompleto7() {
//        xmlInterno1 = new XML("<root><tag attribute='yes'>content2</tag></root>");
//        xmlInterno2 = new XML("<root><tag>content</tag><tag2></tag2></root>");
//        LcsXML lcs = new LcsXML(xmlInterno1, xmlInterno2);
//        
//        double experado = (1 - (1 - SimilarNode.ATTRIBUTE_WEIGTH - SimilarNode.ELEMENT_VALUE_WEIGTH)) / 2;
//        assertEquals(experado, lcs.similaridade(), 0);
//    }
//
//    @Test
//    public void testeCompleto8() {
//        xmlInterno1 = new XML("<root><tag attribute='yes'>content2</tag><tag2></tag2></root>");
//        xmlInterno2 = new XML("<root><tag>content</tag><tag2></tag2></root>");
//        LcsXML lcs = new LcsXML(xmlInterno1, xmlInterno2);
//        
//        double experado = (1 - (1 - SimilarNode.ATTRIBUTE_WEIGTH - SimilarNode.ELEMENT_VALUE_WEIGTH) + 1) / 2;
//        assertEquals(experado, lcs.similaridade(), 0);
//    }
}

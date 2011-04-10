package gems.ic.uff.br.modelo.similar;

import gems.ic.uff.br.modelo.Result;
import org.w3c.dom.NamedNodeMap;
import org.xml.sax.InputSource;

import java.io.StringReader;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Test;

import static org.junit.Assert.*;

public class SimilarNodeTest {

    public SimilarNodeTest() {
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
    public void similaridadeNoMaximoIgualA1() {
        float similaridade = 1;
        similaridade += SimilarNode.ELEMENT_NAME_WEIGTH;
        similaridade += SimilarNode.ELEMENT_VALUE_WEIGTH;
        similaridade += SimilarNode.ATTRIBUTE_WEIGTH;

        assertTrue(similaridade > 1);


        SimilarNode similarNode = createSimilarNode("<tag attribute='yes'>Texto</tag>");

        assertEquals(1, similarNode.similar(similarNode).getSimilarity(), 1);
    }

    @Test
    public void similaridadeEntreNomes() {
        SimilarNode similarNode = createSimilarNode("<igual/>");

        assertEquals(SimilarNode.ELEMENT_NAME_WEIGTH, similarNode.elementsNameSimilarity(similarNode.getNode()), 0);
    }

    @Test
    public void similaridadeEntreNomes2() {
        SimilarNode similarNode = createSimilarNode("<igual></igual>");

        assertEquals(SimilarNode.ELEMENT_NAME_WEIGTH, similarNode.elementsNameSimilarity(similarNode.getNode()), 0);
    }

    @Test
    public void similaridadeEntreNomes3() {
        SimilarNode similarNode = createSimilarNode("<diferente/>");
        SimilarNode similarNode2 = createSimilarNode("<diferente2/>");

        assertEquals(0, similarNode.elementsNameSimilarity(similarNode2.getNode()), 0);
    }

    @Test
    public void similaridadeEntreConteudoDasTags() {
        SimilarNode similarNode = createSimilarNode("<tag>Texto</tag>");

        assertEquals(SimilarNode.ELEMENT_VALUE_WEIGTH, similarNode.elementsValueSimilarity(similarNode.getNode()), 0);
    }

    @Test
    public void similaridadeEntreConteudoDasTags2() {
        SimilarNode similarNode = createSimilarNode("<tag>Texto</tag>");
        SimilarNode similarNode2 = createSimilarNode("<tag>Texto2</tag>");

        assertEquals(0, similarNode.elementsValueSimilarity(similarNode2.getNode()), 0);
    }

    @Test
    public void similaridadeEntreConteudoDasTags3() {
        SimilarNode similarNode = createSimilarNode("<tag>Texto</tag>");
        SimilarNode similarNode2 = createSimilarNode("<tag></tag>");

        assertEquals(0, similarNode.elementsValueSimilarity(similarNode2.getNode()), 0);
    }

    @Test
    public void similaridadeEntreConteudoDasTags4() {
        SimilarNode similarNode = createSimilarNode("<tag></tag>");
        SimilarNode similarNode2 = createSimilarNode("<tag>Texto</tag>");

        assertEquals(0, similarNode.elementsValueSimilarity(similarNode2.getNode()), 0);
    }

    @Test
    public void similaridadeEntreAtributos() {
        SimilarNode similarNode = createSimilarNode("<igual atributo='sim'></igual>");
        SimilarNode similarNode2 = createSimilarNode("<igual></igual>");

        assertEquals(0, similarNode.elementsAttributesSimilarity(similarNode2.getNode()), 0);
    }

    @Test
    public void similaridadeEntreAtributos2() {
        SimilarNode similarNode = createSimilarNode("<igual></igual>");
        SimilarNode similarNode2 = createSimilarNode("<igual atributo='sim'></igual>");

        assertEquals(0, similarNode.elementsAttributesSimilarity(similarNode2.getNode()), 0);
    }

    @Test
    public void similaridadeEntreAtributos3() {
        SimilarNode similarNode = createSimilarNode("<igual atributo='sim'></igual>");

        assertEquals(SimilarNode.ATTRIBUTE_WEIGTH, similarNode.elementsAttributesSimilarity(similarNode.getNode()), 0);
    }

    @Test
    public void similaridadeEntreAtributos4() {
        SimilarNode similarNode = createSimilarNode("<igual atributo='nao'></igual>");
        SimilarNode similarNode2 = createSimilarNode("<igual atributo='sim'></igual>");

        assertEquals(0, similarNode.elementsAttributesSimilarity(similarNode2.getNode()), 0);
    }

    @Test
    public void similaridadeEntreAtributos5() {
        SimilarNode similarNode = createSimilarNode("<igual atributo='nao'></igual>");
        SimilarNode similarNode2 = createSimilarNode("<igual atributo='nao' outro='sim'></igual>");

        assertEquals(SimilarNode.ATTRIBUTE_WEIGTH / 2, similarNode.elementsAttributesSimilarity(similarNode2.getNode()), 0);
    }

    @Test
    public void similaridadeEntreFilhosDoElemento() {
        SimilarNode similarNode = createSimilarNode("<father><son></son></father>");
        SimilarNode similarNode2 = createSimilarNode("<father><son></son></father>");

        assertEquals(SimilarNode.ELEMENT_CHILDREN_WEIGTH, similarNode.elementsChildrenSimilarity(similarNode2.getNode()), 0);
    }

    @Test
    public void similaridadeEntreFilhosDoElemento2() {
        SimilarNode similarNode = createSimilarNode("<father><son></son></father>");
        SimilarNode similarNode2 = createSimilarNode("<father><son2></son2></father>");

        assertEquals(0, similarNode.elementsChildrenSimilarity(similarNode2.getNode()), 0);
    }

    @Test
    public void similaridadeEntreFilhosDoElemento3() {
        SimilarNode similarNode = createSimilarNode("<father><son></son><a/><b/><c/><d/><e/></father>");
        SimilarNode similarNode2 = createSimilarNode("<father><son></son><a/><b/><c/><d/><e/></father>");

        assertEquals(SimilarNode.ELEMENT_CHILDREN_WEIGTH, similarNode.elementsChildrenSimilarity(similarNode2.getNode()), 0.01);
    }

    //    @Test
    public void similaridadeEntreFilhosDoElemento4() {
        SimilarNode similarNode = createSimilarNode("<teste><nome>Fernando</nome><cpf>123</cpf><idade></idade><as></as><bs></bs><cidade></cidade></teste>");
        SimilarNode similarNode2 = createSimilarNode("<teste><nome>Fernando</nome><cpf>123</cpf><idade></idade><cidade></cidade></teste>");

        assertEquals(1, similarNode.elementsChildrenSimilarity(similarNode2.getNode()), 0.01);
    }

    @Test
    public void similaridadeEntreFilhosDoElementoNaoDeveriaContarElementosSemSerDoTipoElementNode() {
        SimilarNode similarNode = createSimilarNode("<father>Texto</father>");
        SimilarNode similarNode2 = createSimilarNode("<father>Texto<son></son></father>");

        assertEquals(0, similarNode.elementsChildrenSimilarity(similarNode2.getNode()), 0);
    }

    //----------- Result ---------------//

    @Test
    public void resultDeveriaTerUmFilhoCasoApenasUmElementoTenhaValor() {
        SimilarNode similarNode = createSimilarNode("<element>Value</element>");
        SimilarNode similarNode2 = createSimilarNode("<element></element>");
        Result result = similarNode.similar(similarNode2);

        assertEquals(1, result.getNode().getChildNodes().getLength());
    }

    @Test
    public void resultDeveriaTerUmFilhoComOMesmoValorDoElementoComValor() {
        SimilarNode similarNode = createSimilarNode("<element>Value</element>");
        SimilarNode similarNode2 = createSimilarNode("<element></element>");

        Result result = similarNode.similar(similarNode2);
        assertEquals("Value", result.getNode().getFirstChild().getFirstChild().getNodeValue());
    }

    @Test
    public void resultDeveriaTerOMesmoValorCasoOsElementosSejamIguais() {
        SimilarNode similarNode = createSimilarNode("<element>Value</element>");
        Result result = similarNode.similar(similarNode);

        assertEquals("Value", result.getNode().getFirstChild().getNodeValue());
    }

    @Test
    public void resultDeveriaTerOMesmoValorCasoOsElementosTenhamValoresIguais() {
        SimilarNode similarNode = createSimilarNode("<element>Value</element>");
        SimilarNode similarNode2 = createSimilarNode("<element>Value</element>");
        Result result = similarNode.similar(similarNode2);

        assertEquals("Value", result.getNode().getFirstChild().getNodeValue());
    }

    @Test
    public void resultDeveriaTerDoisFilhoCasoOsDoisElementosTenhamValoresDistintos() {
        SimilarNode similarNode = createSimilarNode("<element>Value</element>");
        SimilarNode similarNode2 = createSimilarNode("<element>AnotherValue</element>");
        Result result = similarNode.similar(similarNode2);

        assertEquals(2, result.getNode().getChildNodes().getLength());
    }

    @Test
    public void resultDeveriaTerDoisFilhosComOsValoresDeCadaElementoCasoOsDoisElementosTenhamValoresDistintos() {
        SimilarNode similarNode = createSimilarNode("<element>Value</element>");
        SimilarNode similarNode2 = createSimilarNode("<element>AnotherValue</element>");
        Result result = similarNode.similar(similarNode2);

        assertEquals("Value", result.getNode().getChildNodes().item(0).getFirstChild().getNodeValue());
        assertEquals("AnotherValue", result.getNode().getChildNodes().item(1).getFirstChild().getNodeValue());

    }

//    @Test
    public void deveriaTerAMesmaQuantidadeDeAtributosCasoSejaOMesmoElemento() {
        SimilarNode similarNode = createSimilarNode("<element attribute='one'></element>");
        Result result = similarNode.similar(similarNode);

        NamedNodeMap attributes = result.getNode().getAttributes();
        assertEquals(1, attributes.getLength());
    }

    //    @Test
    public void deveriaTerOsMesmosAtributosCasoSejaOMesmoElemento() {
        SimilarNode similarNode = createSimilarNode("<element attribute='one'></element>");
        Result result = similarNode.similar(similarNode);

        NamedNodeMap attributes = result.getNode().getAttributes();

        for (int i = 0; i < attributes.getLength(); i++) {
            assertEquals(similarNode.getNode().getAttributes().item(i).getNodeName(), attributes.item(i).getNodeName());
            assertEquals(similarNode.getNode().getAttributes().item(i).getNodeValue(), attributes.item(i).getNodeValue());
        }
    }
}

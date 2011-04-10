package gems.ic.uff.br.modelo;

import gems.ic.uff.br.modelo.similar.SimilarNode;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.NamedNodeMap;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class ResultTest {

    public SimilarNode createSimilarNode(String xml) {
        try {
            return new SimilarNode(DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(xml))).getDocumentElement());
        } catch (Exception ex) {
            System.out.println(ex.getClass() + ": " + ex.getMessage());
            return null;
        }
    }


    @Test
    public void deveriaTerOMesmoNomeDoPrimeiroElemento() {
        SimilarNode similarNode = createSimilarNode("<nome/>");

        assertEquals("nome", new Result(similarNode.getNode()).getNode().getNodeName());
    }

    @Test
    public void deveriaTerUmFilhoCasoApenasUmElementoTenhaValor() {
        SimilarNode similarNode = createSimilarNode("<element>Value</element>");
        SimilarNode similarNode2 = createSimilarNode("<element></element>");
        Result result = similarNode.similar(similarNode2);

        assertEquals(1, result.getNode().getChildNodes().getLength());
    }

    @Test
    public void deveriaTerUmFilhoComOMesmoValorDoElementoComValor() {
        SimilarNode similarNode = createSimilarNode("<element>Value</element>");
        SimilarNode similarNode2 = createSimilarNode("<element></element>");

        Result result = similarNode.similar(similarNode2);
        assertEquals("Value", result.getNode().getFirstChild().getNodeValue());
    }

    @Test
    public void deveriaTerOMesmoValorCasoOsElementosSejamIguais() {
        SimilarNode similarNode = createSimilarNode("<element>Value</element>");
        Result result = similarNode.similar(similarNode);

        assertEquals("Value", result.getNode().getFirstChild().getNodeValue());
    }

    @Test
    public void deveriaTerOMesmoValorCasoOsElementosTenhamValoresIguais() {
        SimilarNode similarNode = createSimilarNode("<element>Value</element>");
        SimilarNode similarNode2 = createSimilarNode("<element>Value</element>");
        Result result = similarNode.similar(similarNode2);

        assertEquals("Value", result.getNode().getFirstChild().getNodeValue());
    }

    @Test
    public void deveriaTerDoisFilhoCasoOsDoisElementosTenhamValoresDistintos() {
        SimilarNode similarNode = createSimilarNode("<element>Value</element>");
        SimilarNode similarNode2 = createSimilarNode("<element>AnotherValue</element>");
        Result result = similarNode.similar(similarNode2);

        assertEquals(2, result.getNode().getChildNodes().getLength());
    }

    @Test
    public void deveriaTerDoisFilhosComOsValoresDeCadaElementoCasoOsDoisElementosTenhamValoresDistintos() {
        SimilarNode similarNode = createSimilarNode("<element>Value</element>");
        SimilarNode similarNode2 = createSimilarNode("<element>AnotherValue</element>");
        Result result = similarNode.similar(similarNode2);

        assertEquals("Value", result.getNode().getChildNodes().item(0).getFirstChild().getNodeValue());
        assertEquals("AnotherValue", result.getNode().getChildNodes().item(1).getFirstChild().getNodeValue());

    }

    @Test
    public void deveriaTerAMesmaQuantidadeDeAtributosCasoSejaOMesmoElemento() {
        SimilarNode similarNode = createSimilarNode("<element attribute='one'></element>");
        Result result = similarNode.similar(similarNode);

        NamedNodeMap attributes = result.getNode().getAttributes();
        assertEquals(1, attributes.getLength());
    }

    @Test
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

package gems.ic.uff.br.modelo;

import gems.ic.uff.br.modelo.similar.SimilarNode;
import org.junit.Test;
import org.w3c.dom.NamedNodeMap;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DiffTest {

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

        assertEquals("nome", new Diff(similarNode.getNode()).getDiffNode().getNodeName());
    }

    @Test
    public void deveriaTerUmFilhoCasoApenasUmElementoTenhaValor() {
        SimilarNode similarNode = createSimilarNode("<element>Value</element>");
        SimilarNode similarNode2 = createSimilarNode("<element></element>");
        Diff diff = similarNode.similar(similarNode2);

        assertEquals(1, diff.getDiffNode().getChildNodes().getLength());
    }

    @Test
    public void deveriaTerUmFilhoComOMesmoValorDoElementoComValor() {
        SimilarNode similarNode = createSimilarNode("<element>Value</element>");
        SimilarNode similarNode2 = createSimilarNode("<element></element>");

        Diff diff = similarNode.similar(similarNode2);
        assertEquals("Value", diff.getDiffNode().getFirstChild().getNodeValue());
    }

    @Test
    public void deveriaTerOMesmoValorCasoOsElementosSejamIguais() {
        SimilarNode similarNode = createSimilarNode("<element>Value</element>");
        Diff diff = similarNode.similar(similarNode);

        assertEquals("Value", diff.getDiffNode().getFirstChild().getNodeValue());
    }

    @Test
    public void deveriaTerOMesmoValorCasoOsElementosTenhamValoresIguais() {
        SimilarNode similarNode = createSimilarNode("<element>Value</element>");
        SimilarNode similarNode2 = createSimilarNode("<element>Value</element>");
        Diff diff = similarNode.similar(similarNode2);

        assertEquals("Value", diff.getDiffNode().getFirstChild().getNodeValue());
    }

    @Test
    public void deveriaTerUmFilhoCasoOsDoisElementosTenhamValoresDistintos() {
        SimilarNode similarNode = createSimilarNode("<element>Value</element>");
        SimilarNode similarNode2 = createSimilarNode("<element>AnotherValue</element>");
        Diff diff = similarNode.similar(similarNode2);

        assertEquals(1, diff.getDiffNode().getChildNodes().getLength());
    }

    @Test
    public void deveriaTerUmFilhoComOsValoresDeCadaElementoCasoOsDoisElementosTenhamValoresDistintos() {
        SimilarNode similarNode = createSimilarNode("<element>Value</element>");
        SimilarNode similarNode2 = createSimilarNode("<element>AnotherValue</element>");
        Diff diff = similarNode.similar(similarNode2);

        assertEquals("Value", diff.getDiffNode().getChildNodes().item(0).getAttributes().item(0).getNodeValue());
        assertEquals("AnotherValue", diff.getDiffNode().getChildNodes().item(0).getAttributes().item(1).getNodeValue());

    }

    @Test
    public void deveriaTerOAtributoSimilaridade() {
        SimilarNode similarNode = createSimilarNode("<element attribute='one'></element>");
        Diff diff = similarNode.similar(similarNode);

        NamedNodeMap attributes = diff.getDiffNode().getAttributes();
        assertNotNull(attributes.getNamedItem("diff:similarity").getNodeValue());
    }

    @Test
    public void deveriaTerOsMesmosAtributosCasoSejaOMesmoElemento() {
        SimilarNode similarNode = createSimilarNode("<element attribute='one'></element>");
        Diff diff = similarNode.similar(similarNode);

        assertEquals("attribute=\"one\"", similarNode.getNode().getAttributes().getNamedItem("attribute").toString());
    }
}

package gems.ic.uff.br.modelo;

import gems.ic.uff.br.modelo.similar.SimilarNode;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class XMLTest {

    @Before
    public void setUp() {
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
    public void deveriaCriarUmXmlAtravesDeUmArquivoXML2() {
        XML xml = new XML("artigo.xml");
        assertNotNull(xml.getDocument());
    }

    @Test
    public void naoDeveriaCriarUmXmlSeOArquivoXMLNaoExistir() {
        XML xml = new XML("arquivo.xml");
        assertNull(xml.getDocument());
    }

    @Test
    public void comparacaoDoisArquivosXMLIguais() {
        XML xml = new XML("books.xml");

//        System.out.println(xml.toString());

        SimilarNode similarNode1 = new SimilarNode(xml.getDocument().getDocumentElement());
        SimilarNode similarNode2 = new SimilarNode(xml.getDocument().getDocumentElement());
        assertEquals(1, similarNode1.similar(similarNode2).getSimilarity(), 0.1);
    }

//    @Test
    public void comparacaoDoisArquivosXMLDiferente() {
        XML xml = new XML("books.xml");
        XML xml2 = new XML("artigo.xml");

        System.out.println(xml.toString());

        SimilarNode similarNode1 = new SimilarNode(xml.getDocument().getDocumentElement());
        SimilarNode similarNode2 = new SimilarNode(xml2.getDocument().getDocumentElement());
//        assertEquals(1, similarNode1.similar(similarNode2), 0.1);
        System.out.println("SIMILARIDADE => " + similarNode1.similar(similarNode2));
    }
}

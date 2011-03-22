package gems.ic.uff.br.modelo;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.InputSource;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.w3c.dom.Node;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import static org.junit.Assert.*;
import org.xml.sax.SAXException;
import uk.ac.shef.wit.simmetrics.similaritymetrics.*;

public class SimilarNodeTest {

    @Mock
    private Node mockNode;
    @Mock
    private Node mockNode2;
    private int qtdSimilaridadesEncontradas = 0;
    public static final double DESCONTO = 0.1;

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

//    @Test(expected = IllegalArgumentException.class)
    public void similaridadeEntreElementosInvalidos() {
        SimilarNode similarNode = createSimilarNode("<igual/>");
        similarNode.similar(new SimilarNode(null));
    }

//    @Test
    public void similaridadeEntreNomes() {
        SimilarNode similarNode = createSimilarNode("<igual/>");

        assertEquals(1, similarNode.elementsNameSimilarity(similarNode.getNode(), 1), 0);
    }

//    @Test
    public void similaridadeEntreNomes2() {
        SimilarNode similarNode = createSimilarNode("<igual></igual>");
        assertEquals(1, similarNode.elementsNameSimilarity(similarNode.getNode(), 1), 0);
    }

//    @Test
    public void similaridadeEntreNomes3() {
        SimilarNode similarNode = createSimilarNode("<diferente/>");
        SimilarNode similarNode2 = createSimilarNode("<diferente2/>");

        assertEquals(1 - SimilarNode.ELEMENT_NAME_WEIGTH, similarNode.elementsNameSimilarity(similarNode2.getNode(), 1), 0);
    }

//    @Test
    public void similaridadeEntreConteudoDasTags() {
        SimilarNode similarNode = createSimilarNode("<tag>Texto</tag>");
        SimilarNode similarNode2 = createSimilarNode("<tag>Texto</tag>");

        assertEquals(1, similarNode.elementsValueSimilarity(similarNode2.getNode(), 1), 0);
    }

//    @Test
    public void similaridadeEntreConteudoDasTags2() {
        SimilarNode similarNode = createSimilarNode("<tag>Texto</tag>");
        SimilarNode similarNode2 = createSimilarNode("<tag>Texto2</tag>");
        assertEquals(1 - SimilarNode.ELEMENT_VALUE_WEIGTH, similarNode.elementsValueSimilarity(similarNode2.getNode(), 1), 0);
    }

//    @Test
    public void similaridadeEntreConteudoDasTags3() {
        SimilarNode similarNode = createSimilarNode("<tag>Texto</tag>");
        SimilarNode similarNode2 = createSimilarNode("<tag></tag>");

        assertEquals(1 - SimilarNode.ELEMENT_VALUE_WEIGTH, similarNode.elementsValueSimilarity(similarNode2.getNode(), 1), 0);
    }

//    @Test
    public void similaridadeEntreConteudoDasTags4() {
        SimilarNode similarNode = createSimilarNode("<tag></tag>");
        SimilarNode similarNode2 = createSimilarNode("<tag>Texto</tag>");

        assertEquals(1 - SimilarNode.ELEMENT_VALUE_WEIGTH, similarNode.elementsValueSimilarity(similarNode2.getNode(), 1), 0);
    }

//    @Test
    public void similaridadeEntreAtributos() {
        SimilarNode similarNode = createSimilarNode("<igual atributo='sim'></igual>");
        SimilarNode similarNode2 = createSimilarNode("<igual></igual>");

        assertEquals(1 - SimilarNode.ATTRIBUTE_WEIGTH, similarNode.elementsAttributesSimilarity(similarNode2.getNode(), 1), 0);
    }

//    @Test
    public void similaridadeEntreAtributos2() {
        SimilarNode similarNode = createSimilarNode("<igual></igual>");
        SimilarNode similarNode2 = createSimilarNode("<igual atributo='sim'></igual>");

        assertEquals(1 - SimilarNode.ATTRIBUTE_WEIGTH, similarNode.elementsAttributesSimilarity(similarNode2.getNode(), 1), 0);
    }

//    @Test
    public void similaridadeEntreFilhosDoElemento() {
        SimilarNode similarNode = createSimilarNode("<father><son></son></father>");
        SimilarNode similarNode2 = createSimilarNode("<father><son></son></father>");

        assertEquals(1, similarNode.elementsChildrenSimilarity(similarNode2.getNode(), 1), 0);
    }

//    @Test
    public void similaridadeEntreFilhosDoElemento2() {
        SimilarNode similarNode = createSimilarNode("<father><son></son></father>");
        SimilarNode similarNode2 = createSimilarNode("<father><son2></son2></father>");

        assertEquals(1 - SimilarNode.ELEMENT_CHILDREN_WEIGTH, similarNode.elementsChildrenSimilarity(similarNode2.getNode(), 1), 0);
    }

    @Test
    public void testeBobo() {
        List<String> lista1 = new ArrayList<String>();
        List<String> lista2 = new ArrayList<String>();
        lista1.add("a");
        lista1.add("b");
        lista2.add("c");
        lista2.addAll(lista1);
        System.out.println("LISTAAAAAAAA    " + lista2);

    }

//    @Test
    public void similaridadeDeconjuntosComDoisXmlDeMesmoTamanho() throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
//        Document xml1 = db.parse(new InputSource(new StringReader("<raiz><elemento1/><porta/><carta/></raiz>")));
//        Document xml2 = db.parse(new InputSource(new StringReader("<raiz><torta/><pata/><elemento1/></raiz>")));
        Document xml1 = db.parse(new InputSource(new StringReader("<raiz><elemento1/><elemento2/><elemento3/></raiz>")));
        Document xml2 = db.parse(new InputSource(new StringReader("<raiz><elemento3/><elemento1/><elemento2/></raiz>")));

        NodeList listaXml1 = xml1.getElementsByTagName("*");
        NodeList listaXml2 = xml2.getElementsByTagName("*");
        List<DadosEstatisticos> resultadoGeral = procurarSimilaridades(listaXml1, listaXml2);
        for (DadosEstatisticos dadosEstatisticos : resultadoGeral) {
            System.out.printf("%s   <====>   %s  percentual de similaridade  = %.3f\n", dadosEstatisticos.getNode1(), dadosEstatisticos.getNode2(), dadosEstatisticos.getPercentual());
        }
        System.out.printf("\n\n");
        System.out.println("SIMILARIDADES ENTRE NODES COM O PERCENTUAL ACIMA DO ESTABELECIDO:");
        for (DadosEstatisticos dadosEstatisticos : resultadoGeral) {
            if (dadosEstatisticos.getPercentual() > 0.7) {
                System.out.printf("%s   <====>   %s  percentual de similaridade  = %.3f\n", dadosEstatisticos.getNode1(), dadosEstatisticos.getNode2(), dadosEstatisticos.getPercentual());
            }
        }


//        System.out.println("TODOSSSSS OS VALORESSSSS        " + procurarSimilaridades(listaXml1.item(0).getChildNodes(), listaXml2.item(0).getChildNodes(), resultadoGeral));
//        System.out.println("TOTAL DE SIMILARIDADES ENCONTRADAS     " + qtdSimilaridadesEncontradas);
    }

//    @Test
    public void similaridadeDeconjuntosComDoisXmlComMesmosElementosMasTamanhoDiferentes() throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document xml1 = db.parse(new InputSource(new StringReader("<raiz><a/><a/><a/><b/><b/><c/></raiz>")));
        Document xml2 = db.parse(new InputSource(new StringReader("<raiz><a/><b/><c/></raiz>")));

        NodeList listaXml1 = xml1.getElementsByTagName("*");
        NodeList listaXml2 = xml2.getElementsByTagName("*");
        List<DadosEstatisticos> resultadoGeral = procurarSimilaridades(listaXml1.item(0).getChildNodes(), listaXml2.item(0).getChildNodes());

        for (DadosEstatisticos dadosEstatisticos : resultadoGeral) {
            System.out.printf("%s   <====>   %s  percentual de similaridade  = %.3f\n", dadosEstatisticos.getNode1(), dadosEstatisticos.getNode2(), dadosEstatisticos.getPercentual());
        }
        System.out.printf("\n\n");
        System.out.println("SIMILARIDADES ENTRE NODES COM O PERCENTUAL ACIMA DO ESTABELECIDO:");
        int totalDeElementosAcimaPercentual = 0;
        double somatorioSimilaridades = 0.0;
        for (DadosEstatisticos dadosEstatisticos : resultadoGeral) {
            if (dadosEstatisticos.getPercentual() > 0.7) {
                System.out.printf("%s   <====>   %s  percentual de similaridade  = %.3f\n", dadosEstatisticos.getNode1(), dadosEstatisticos.getNode2(), dadosEstatisticos.getPercentual());
                totalDeElementosAcimaPercentual++;
                somatorioSimilaridades += dadosEstatisticos.getPercentual();
            }
        }
        System.out.printf("Total da similaridade do conjunto : %.3f\n", somatorioSimilaridades / totalDeElementosAcimaPercentual);
    }

    @Test
    public void similaridadeDeconjuntosComDoisXmlComMesmosElementosComFilhos() throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document xml1 = db.parse(new InputSource(new StringReader("<raiz><a/><b/><c><d/><e/></c></raiz>")));
        Document xml2 = db.parse(new InputSource(new StringReader("<raiz><a/><b/><c><d/><e/></c></raiz>")));

        NodeList listaXml1 = xml1.getElementsByTagName("*");
        NodeList listaXml2 = xml2.getElementsByTagName("*");
        List<DadosEstatisticos> resultadoGeral = procurarSimilaridades(listaXml1.item(0).getChildNodes(), listaXml2.item(0).getChildNodes());

        for (DadosEstatisticos dadosEstatisticos : resultadoGeral) {
            System.out.printf("%s   <====>   %s  percentual de similaridade  = %.3f\n", dadosEstatisticos.getNode1(), dadosEstatisticos.getNode2(), dadosEstatisticos.getPercentual());
        }
        System.out.printf("\n\n");
        System.out.println("SIMILARIDADES ENTRE NODES COM O PERCENTUAL ACIMA DO ESTABELECIDO:");
        int totalDeElementosAcimaPercentual = 0;
        double somatorioSimilaridades = 0.0;
        for (DadosEstatisticos dadosEstatisticos : resultadoGeral) {
            if (dadosEstatisticos.getPercentual() > 0.7) {
                System.out.printf("%s   <====>   %s  percentual de similaridade  = %.3f\n", dadosEstatisticos.getNode1(), dadosEstatisticos.getNode2(), dadosEstatisticos.getPercentual());
                totalDeElementosAcimaPercentual++;
                somatorioSimilaridades += dadosEstatisticos.getPercentual();
            }
        }
        System.out.printf("Total da similaridade do conjunto : %.3f\n", somatorioSimilaridades / totalDeElementosAcimaPercentual);
    }

//    @Test
    public void similaridadeNoMinimoIgualA0() {
        double similaridade = 1;
        similaridade -= SimilarNode.ELEMENT_NAME_WEIGTH;
        similaridade -= SimilarNode.ELEMENT_VALUE_WEIGTH;
        similaridade -= SimilarNode.ATTRIBUTE_WEIGTH;

        assertTrue(similaridade < 0);


        SimilarNode similarNode = createSimilarNode("<tag attribute='yes'>Texto</tag>");
        SimilarNode similarNode2 = createSimilarNode("<tag2>TextoDiferente</tag2>");

        assertEquals(0, similarNode.similar(similarNode2), 0);
    }

    private List<DadosEstatisticos> procurarSimilaridades(NodeList lista1, NodeList lista2) {
        List<DadosEstatisticos> resultado = new ArrayList<DadosEstatisticos>();
        MongeElkan me = new MongeElkan();
        for (int i = 0; i < lista1.getLength(); i++) {
            for (int j = 0; j < lista2.getLength(); j++) {
                if (lista1.item(i).hasChildNodes() && lista2.item(j).hasChildNodes()) {
                    resultado.addAll(procurarSimilaridades(lista1.item(i).getChildNodes(), lista2.item(j).getChildNodes()));
                } else if (!lista1.item(i).hasChildNodes() && !lista2.item(j).hasChildNodes()) {
                    double similaridadeCorrente = me.getSimilarity(lista1.item(i).getNodeName(), lista2.item(j).getNodeName());
                    DadosEstatisticos dadosEstatisticos = new DadosEstatisticos();
                    dadosEstatisticos.setNode1(lista1.item(i).getNodeName());
                    dadosEstatisticos.setNode2(lista2.item(j).getNodeName());
                    dadosEstatisticos.setPercentual(similaridadeCorrente);
                    resultado.add(dadosEstatisticos);
                }
            }
        }
        int totalDeDesconto = Math.abs(lista1.getLength() - lista2.getLength());
        System.out.println("TOTAL DE DESCONTO POR EXCESSO DE FILHOS    " + totalDeDesconto);
        return resultado;
//        return similaridade / (lista1.getLength() * lista1.getLength() - (totalDeDesconto * DESCONTO));
        }
}

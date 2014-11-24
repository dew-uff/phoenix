package gems.ic.uff.br.modelo.similar;

import static org.junit.Assert.assertEquals;
import gems.ic.uff.br.modelo.Diff;
import gems.ic.uff.br.settings.SettingsHelper;

import java.io.File;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.InputSource;

public class SimilarNodeTest {

    public SimilarNodeTest() {
    }
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        SettingsHelper.setNameSimilarityRequired(true);
        SettingsHelper.setIgnoreTrivialSimilarities(false);
        SettingsHelper.setAutomaticWeightAllocation(false);
        SettingsHelper.setNameSimilarityWeight(0.25f);
        SettingsHelper.setAttributeSimilarityWeight(0.25f);
        SettingsHelper.setChildrenSimilarityWeight(0.25f);
        SettingsHelper.setValueSimilarityWeight(0.25f);
    }
    
    @AfterClass
    public static void cleanAfterClass() {
        // make sure settings file does not exist
        File file = new File(SettingsHelper.SETTINGS_FILEPATH);
        if (file.exists()) {
            file.delete();
        }
        SettingsHelper.dispose();
    }

    public SimilarNode createSimilarNode(String xml) {
        try {
            return new SimilarNode(DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(xml))).getDocumentElement());
        } catch (Exception ex) {
            System.out.println(ex.getClass() + ": " + ex.getMessage());
            return null;
        }
    }

//    @Test
//    public void similaridadeNoMaximoIgualA1() {
//        float similaridade = 0;
//        similaridade += SimilarNode.ELEMENT_NAME_WEIGHT;
//        similaridade += SimilarNode.ELEMENT_VALUE_WEIGTH;
//        similaridade += SimilarNode.ATTRIBUTE_WEIGTH;
//        similaridade += SimilarNode.ELEMENT_CHILDREN_WEIGTH;
//
//        assertTrue(similaridade == 1);
//    }

    @Test
    public void igualdadeEntreNomes() {
        SimilarNode equalNode = createSimilarNode("<igual/>");
        float elementsNameSimilarity = equalNode.elementsNameSimilarity(equalNode.getNode());

        assertEquals(SimilarNode.MAXIMUM_SIMILARITY, elementsNameSimilarity, 0);
    }

    @Test
    public void desigualdadeEntreNomes() {
        SimilarNode node = createSimilarNode("<aaaaaaaa/>");
        SimilarNode unequalNode = createSimilarNode("<bbbbbbbb/>");
        float elementsNameSimilarity = node.elementsNameSimilarity(unequalNode.getNode());

        assertEquals(0, elementsNameSimilarity, 0);
    }

    @Test
    public void igualdadeEntreConteudoDasTags() {
        SimilarNode equalNode = createSimilarNode("<tag>Texto</tag>");
        Diff diff = new Diff(equalNode.getNode());
        float elementsValueSimilarity = equalNode.elementsValueSimilarity(equalNode.getNode(), diff);

        assertEquals(SimilarNode.MAXIMUM_SIMILARITY, elementsValueSimilarity, 0);
    }

    @Test
    public void similaridadeEntreConteudoDasTags() {
        SimilarNode node = createSimilarNode("<tag>Text1</tag>");
        SimilarNode similarNode = createSimilarNode("<tag>Text2</tag>");
        Diff diff = new Diff(node.getNode());
        float elementsValueSimilarity = node.elementsValueSimilarity(similarNode.getNode(), diff);

        assertEquals((4.0/5.0), elementsValueSimilarity, 0.01);
    }

    @Test
    public void desigualdadeEntreConteudoDasTags() {
        SimilarNode node = createSimilarNode("<tag>Texto</tag>");
        SimilarNode unequalNode = createSimilarNode("<tag></tag>");

        Diff diff = new Diff(node.getNode());
        assertEquals(0, node.elementsValueSimilarity(unequalNode.getNode(), diff), 0);
    }

    @Test
    public void desigualdadeEntreConteudoDasTags2() {
        SimilarNode node = createSimilarNode("<tag></tag>");
        SimilarNode unequalNode = createSimilarNode("<tag>Texto</tag>");

        Diff diff = new Diff(node.getNode());
        assertEquals(0, node.elementsValueSimilarity(unequalNode.getNode(), diff), 0);
    }
    
    
    @Test
    public void igualdadeEntreAtributos() {
        SimilarNode equalNode = createSimilarNode("<igual atributo='sim'></igual>");

        Diff diff = new Diff(equalNode.getNode());
        assertEquals(SimilarNode.MAXIMUM_SIMILARITY, equalNode.elementsAttributesSimilarity(equalNode.getNode(), diff), 0);
    }

    @Test
    public void desigualdadeEntreAtributos() {
        SimilarNode node = createSimilarNode("<igual atributo='sim'></igual>");
        SimilarNode unequalNode = createSimilarNode("<igual></igual>");

        Diff diff = new Diff(node.getNode());
        assertEquals(0, node.elementsAttributesSimilarity(unequalNode.getNode(), diff), 0);
    }

    @Test
    public void desigualdadeEntreAtributos2() {
        SimilarNode node = createSimilarNode("<igual></igual>");
        SimilarNode unequalNode = createSimilarNode("<igual atributo='sim'></igual>");

        Diff diff = new Diff(node.getNode());
        assertEquals(0, node.elementsAttributesSimilarity(unequalNode.getNode(), diff), 0);
    }

    @Test
    public void desigualdadeEntreAtributos3() {
        SimilarNode node = createSimilarNode("<igual atributo='nao'></igual>");
        SimilarNode unequalNode = createSimilarNode("<igual atributo='sim'></igual>");

        Diff diff = new Diff(node.getNode());
        assertEquals(0, node.elementsAttributesSimilarity(unequalNode.getNode(), diff), 0);
    }

    @Test
    public void similaridadeEntreAtributos() {
        SimilarNode node = createSimilarNode("<igual atributo='nao'></igual>");
        SimilarNode similarNode = createSimilarNode("<igual atributo='nao' outro='sim'></igual>");

        Diff diff = new Diff(node.getNode());
        assertEquals(SimilarNode.MAXIMUM_SIMILARITY / 2, node.elementsAttributesSimilarity(similarNode.getNode(), diff), 0);
    }

    @Test
    public void igualdadeEntreFilhosDosElementos() {
        SimilarNode node = createSimilarNode("<father><son></son></father>");
        SimilarNode equalNode = createSimilarNode("<father><son></son></father>");

        Diff diff = new Diff(node.getNode());
        assertEquals(SimilarNode.MAXIMUM_SIMILARITY, node.elementsChildrenSimilarity(equalNode.getNode(), diff), 0);
    }

    @Test
    public void desigualdadeEntreFilhosDosElementos() {
        SimilarNode node = createSimilarNode("<father><son></son></father>");
        SimilarNode unequalNode = createSimilarNode("<father><son2></son2></father>");

        Diff diff = new Diff(node.getNode());
        assertEquals(0, node.elementsChildrenSimilarity(unequalNode.getNode(), diff), 0);
    }

    @Test
    public void similaridadeEntreFilhosDosElementos() {
        SimilarNode node = createSimilarNode("<father><son></son><a/><b/><c/><d/><e/></father>");
        SimilarNode similarNode = createSimilarNode("<father><son></son><a/><b/><c/><d/><e/></father>");

        Diff diff = new Diff(node.getNode());
        assertEquals(SimilarNode.MAXIMUM_SIMILARITY, node.elementsChildrenSimilarity(similarNode.getNode(), diff), 0.01);
    }
    
    @Test
    public void similaridadeEntreFilhosDosElementosNaoLevaEmConsideracaoOrdenacao() {
        SimilarNode node = createSimilarNode("<father><son></son><a/><b/><c/><d/><e/></father>");
        SimilarNode similarNode = createSimilarNode("<father><son></son><c/><a/><b/><e/><d/></father>");

        Diff diff = new Diff(node.getNode());
        assertEquals(SimilarNode.MAXIMUM_SIMILARITY, node.elementsChildrenSimilarity(similarNode.getNode(), diff), 0.01);
    }

    @Test
    public void similaridadeEntreFilhosDosElementosNaoDeveriaContarElementosSemSerDoTipoElementNode() {
        SimilarNode node = createSimilarNode("<father atributo='valor'>Texto</father>");
        SimilarNode unequalNode = createSimilarNode("<father>Texto<son></son></father>");

        Diff diff = new Diff(node.getNode());
        assertEquals(0, node.elementsChildrenSimilarity(unequalNode.getNode(), diff), 0);
    }
}

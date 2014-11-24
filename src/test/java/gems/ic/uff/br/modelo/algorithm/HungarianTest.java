package gems.ic.uff.br.modelo.algorithm;

import gems.ic.uff.br.modelo.HungarianList;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import java.util.List;

import gems.ic.uff.br.modelo.similar.Similar;
import gems.ic.uff.br.modelo.similar.SimilarNode;
import gems.ic.uff.br.settings.SettingsHelper;

import java.io.File;
import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Test;
import org.xml.sax.InputSource;

import static org.junit.Assert.*;

public class HungarianTest {

    private List<Similar> similarList;
    private List<Similar> otherSimilarList;

    public HungarianTest() {
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

    @Before
    public void setUp() {
        similarList = new ArrayList<Similar>();
        otherSimilarList = new ArrayList<Similar>();
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
    public void similaridadeEntreElementosIguais() {
        similarList.add(createSimilarNode("<igual/>"));

        assertEquals(1, new HungarianList(similarList, similarList).similaridade(), 0);
    }

    @Test
    public void similaridadeEntreElementosDiferentes() {
        similarList.add(createSimilarNode("<a/>"));
        similarList.add(createSimilarNode("<b/>"));

        assertEquals(0, new HungarianList(similarList, otherSimilarList).similaridade(), 0);

    }

    @Test
    public void similaridadeEntreUmElementoIgualEUmDiferente() {
        similarList.add(createSimilarNode("<igual/>"));
        similarList.add(createSimilarNode("<a/>"));
        otherSimilarList.add(createSimilarNode("<igual/>"));
        otherSimilarList.add(createSimilarNode("<z/>"));
        assertEquals(1.0 / (4.0 / 2.0), new HungarianList(similarList, otherSimilarList).similaridade(), 0);
    }

    @Test
    public void similaridadeEntreDoisElementosIguaisEUmDiferente() {
        similarList.add(createSimilarNode("<igual/>"));
        similarList.add(createSimilarNode("<igual2/>"));
        similarList.add(createSimilarNode("<a/>"));
        otherSimilarList.add(createSimilarNode("<igual/>"));
        otherSimilarList.add(createSimilarNode("<igual2/>"));
        otherSimilarList.add(createSimilarNode("<z/>"));
        assertEquals(2.0 / (6.0 / 2.0), new HungarianList(similarList, otherSimilarList).similaridade(), 0.01);
    }
}

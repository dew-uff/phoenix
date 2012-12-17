package gems.ic.uff.br.settings;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileWriter;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * This test will verify the SettingsHelper when the file is not created yet.
 * 
 * @author gabriel
 *
 */

public class SettingsHelperRetrieveTest {
    
    private static final float SETTINGS_NAME_TEST = 0.18f;
    private static final float SETTINGS_ATTRIBUTE_TEST = 0.22f;
    private static final float SETTINGS_VALUE_TEST = 0.40f;
    private static final float SETTINGS_CHILDREN_TEST = 0.20f;
    private static final boolean SETTINGS_NAME_SIMILARITY_REQUIRED_TEST = true;
    private static final boolean AUTOMATIC_WEIGHT_ALLOCATION_TEST = true;
    private static final boolean IGNORE_TRIVIAL_SIMILARITIES_TEST = true;
    
    private final static String SETTINGS_FILE_TEXT = 
            SettingsHelper.NAME_WEIGHT_LABEL + "=" + SETTINGS_NAME_TEST + "\n" +
            SettingsHelper.VALUE_WEIGHT_LABEL + "=" + SETTINGS_VALUE_TEST + "\n" +
            SettingsHelper.ATTRIBUTE_WEIGHT_LABEL + "=" + SETTINGS_ATTRIBUTE_TEST + "\n" +
            SettingsHelper.CHILDREN_WEIGHT_LABEL + "=" + SETTINGS_CHILDREN_TEST + "\n" +
            SettingsHelper.NAME_SIMILARITY_REQUIRED_LABEL + "=" + SETTINGS_NAME_SIMILARITY_REQUIRED_TEST + "\n" +
            SettingsHelper.AUTOMATIC_WEIGHT_ALLOCATION_LABEL + "=" + AUTOMATIC_WEIGHT_ALLOCATION_TEST + "\n" +
            SettingsHelper.IGNORE_TRIVIAL_SIMILARITIES_LABEL + "=" + IGNORE_TRIVIAL_SIMILARITIES_TEST;
            
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        // make sure settings file exists
        File file = new File(SettingsHelper.SETTINGS_FILEPATH);
        if (file.exists()) {
            file.delete();
        }
        FileWriter fw = new FileWriter(file);
        fw.write(SETTINGS_FILE_TEXT);
        fw.close();
        SettingsHelper.dispose();
    }
    
    @Test
    public void testGetNameSimilarityRequired() {
        assertEquals(SettingsHelper.getNameSimilarityRequired(),
                SETTINGS_NAME_SIMILARITY_REQUIRED_TEST);
    }

    @Test
    public void testGetAutomaticWeightAllocation() {
        assertEquals(SettingsHelper.getAutomaticWeightAllocation(),
                AUTOMATIC_WEIGHT_ALLOCATION_TEST);
    }
    
    @Test
    public void testGetIgnoreTrivialSimilarities() {
        assertEquals(SettingsHelper.getIgnoreTrivialSimilarities(),
                IGNORE_TRIVIAL_SIMILARITIES_TEST);
    }

    @Test
    public void testGetNameSimilarityWeight() {
        assertEquals(SettingsHelper.getNameSimilarityWeight(),
                SETTINGS_NAME_TEST,0);
    }

    @Test
    public void testGetValueSimilarityWeight() {
        assertEquals(SettingsHelper.getValueSimilarityWeight(),
                SETTINGS_VALUE_TEST,0);
    }

    @Test
    public void testGetAttributeSimilarityWeight() {
        assertEquals(SettingsHelper.getAttributeSimilarityWeight(),
                SETTINGS_ATTRIBUTE_TEST,0);
    }

    @Test
    public void testGetChildrenSimilarityWeight() {
        assertEquals(SettingsHelper.getChildrenSimilarityWeight(),
                SETTINGS_CHILDREN_TEST,0);
    }
}

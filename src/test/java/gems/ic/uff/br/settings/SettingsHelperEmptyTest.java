package gems.ic.uff.br.settings;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

/**
 * This test will verify the SettingsHelper when the file is not created yet.
 * 
 * @author gabriel
 *
 */
public class SettingsHelperEmptyTest {
    
    @Before
    public void setUp() {
        // make sure settings file does not exist
        File file = new File(SettingsHelper.SETTINGS_FILEPATH);
        if (file.exists()) {
            file.delete();
        }
    }
    
    @Test
    public void testGetNameSimilarityRequired() {
        assertEquals(SettingsHelper.NAME_SIMILARITY_REQUIRED_HARD_DEFAULT,
                SettingsHelper.getNameSimilarityRequired());
    }

    @Test
    public void testGetDynamicWeightAllocation() {
        assertEquals(SettingsHelper.AUTOMATIC_WEIGHT_ALLOCATION_HARD_DEFAULT,
                SettingsHelper.getAutomaticWeightAllocation());
    }

    @Test
    public void testGetNameSimilarityWeight() {
        assertEquals(SettingsHelper.NAME_WEIGHT_HARD_DEFAULT,
                SettingsHelper.getNameSimilarityWeight(),0);
    }

    @Test
    public void testGetValueSimilarityWeight() {
        assertEquals(SettingsHelper.VALUE_WEIGHT_HARD_DEFAULT,
                SettingsHelper.getValueSimilarityWeight(),0);
    }

    @Test
    public void testGetAttributeSimilarityWeight() {
        assertEquals(SettingsHelper.ATTRIBUTE__WEIGHT_HARD_DEFAULT,
                SettingsHelper.getAttributeSimilarityWeight(),0);
    }

    @Test
    public void testGetChildrenSimilarityWeight() {
        assertEquals(SettingsHelper.CHILDREN_WEIGHT_HARD_DEFAULT,
                SettingsHelper.getChildrenSimilarityWeight(),0);
    }
}

package gems.ic.uff.br.settings;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

public class SettingsHelperSetTest {

    @Before
    public void setUp() throws Exception {
        File file = new File(SettingsHelper.SETTINGS_FILEPATH);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void testSetNameSimilarityRequired() {
        boolean value = true;
        SettingsHelper.setNameSimilarityRequired(value);
        assertEquals(value, SettingsHelper.getNameSimilarityRequired());
    }

    @Test
    public void testDynamicWeightAllocation() {
        boolean value = true;
        SettingsHelper.setDynamicWeightAllocation(value);
        assertEquals(value, SettingsHelper.getDynamicWeightAllocation());
    }

    @Test
    public void testSetNameSimilarityWeight() {
        float value = 0.78f;
        SettingsHelper.setNameSimilarityWeight(value);
        assertEquals(value, SettingsHelper.getNameSimilarityWeight(),0);
    }

    @Test
    public void testSetValueSimilarityWeight() {
        float value = 0.43f;
        SettingsHelper.setValueSimilarityWeight(value);
        assertEquals(value, SettingsHelper.getValueSimilarityWeight(),0);
    }
    
    @Test
    public void testSetAttributeSimilarityWeight() {
        float value = 0.24f;
        SettingsHelper.setAttributeSimilarityWeight(value);
        assertEquals(value, SettingsHelper.getAttributeSimilarityWeight(),0);
    }

    @Test
    public void testSetChildrenSimilarityWeight() {
        float value = 0.89f;
        SettingsHelper.setChildrenSimilarityWeight(value);
        assertEquals(value, SettingsHelper.getChildrenSimilarityWeight(),0);
    }

}

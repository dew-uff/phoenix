package gems.ic.uff.br.settings;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

/**
 * Singleton to isolate the file access logic from the application logic.
 * Every setting has a setter and getter in this class.
 * 
 * @author gabriel
 * 
 */
public class SettingsHelper {

    // settings file name 
    public static final String SETTINGS_FILEPATH = ".phoenix_settings";

    // identifiers of the settings inside the file
    public static final String SETTINGS_NAME_LABEL = "NameSimilarityWeight";
    public static final String SETTINGS_ATTRIBUTE_LABEL = "AttributeSimilarityWeight";
    public static final String SETTINGS_VALUE_LABEL = "ValueSimilarityWeight";
    public static final String SETTINGS_CHILDREN_LABEL = "ChildrenSimilarityWeight";
    public static final String SETTINGS_NAME_SIMILARITY_REQUIRED_LABEL = "NameSimilarityRequired";
    public static final String SETTINGS_DYNAMIC_WEIGHT_ALLOCATION_LABEL = "DynamicWeightAllocation";

    // default values for each setting
    public static final float SETTINGS_NAME_HARD_DEFAULT = 0.25f;
    public static final float SETTINGS_ATTRIBUTE_HARD_DEFAULT = 0.25f;
    public static final float SETTINGS_VALUE_HARD_DEFAULT = 0.25f;
    public static final float SETTINGS_CHILDREN_HARD_DEFAULT = 0.25f;
    public static final boolean SETTINGS_NAME_SIMILARITY_REQUIRED_HARD_DEFAULT = false;
    public static final boolean SETTINGS_DYNAMIC_WEIGHT_ALLOCATION_HARD_DEFAULT = false;

    // this singleton instance
    private static Properties properties = null;

    /**
     * Internal method to create singleton instance
     */
    private static void init() {
        if (properties == null) {
            try {
                properties = new Properties();
                properties.load(new FileInputStream(SETTINGS_FILEPATH));
            } catch (FileNotFoundException e) {
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Internal method to persist all the settings to the file
     */
    private static void save() {

        if (properties != null) {
            try {
                properties.store(new FileWriter(SETTINGS_FILEPATH), "");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * The value for the Name Similarity Required setting
     * 
     * @return true if setting is turned on, false otherwise
     */
    public static boolean getNameSimilarityRequired() {
        if (properties == null) {
            init();
        }
        String value = properties
                .getProperty(SETTINGS_NAME_SIMILARITY_REQUIRED_LABEL);
        return (value != null) ? Boolean.parseBoolean(value)
                : SETTINGS_NAME_SIMILARITY_REQUIRED_HARD_DEFAULT;
    }

    /**
     * The value for the Dynamic Weight Allocation setting
     * 
     * @return true if setting is turned on, false otherwise
     */
    public static boolean getDynamicWeightAllocation() {
        if (properties == null) {
            init();
        }
        String value = properties
                .getProperty(SETTINGS_DYNAMIC_WEIGHT_ALLOCATION_LABEL);
        return (value != null) ? Boolean.parseBoolean(value)
                : SETTINGS_DYNAMIC_WEIGHT_ALLOCATION_HARD_DEFAULT;
    }

    /**
     * The value for the Name Similarity Weight setting
     * 
     * @return the value between 0 and 1.
     */
    public static float getNameSimilarityWeight() {
        if (properties == null) {
            init();
        }
        String value = properties.getProperty(SETTINGS_NAME_LABEL);
        return (value != null) ? Float.parseFloat(value)
                : SETTINGS_NAME_HARD_DEFAULT;
    }

    /**
     * The value for the Value Similarity Weight setting
     * 
     * @return the value between 0 and 1.
     */
    public static float getValueSimilarityWeight() {
        if (properties == null) {
            init();
        }
        String value = properties.getProperty(SETTINGS_VALUE_LABEL);
        return (value != null) ? Float.parseFloat(value)
                : SETTINGS_VALUE_HARD_DEFAULT;
    }

    /**
     * The value for the Attribute Similarity Weight setting
     * 
     * @return the value between 0 and 1.
     */
    public static float getAttributeSimilarityWeight() {
        if (properties == null) {
            init();
        }
        String value = properties.getProperty(SETTINGS_ATTRIBUTE_LABEL);
        return (value != null) ? Float.parseFloat(value)
                : SETTINGS_ATTRIBUTE_HARD_DEFAULT;
    }

    /**
     * The value for the Children Similarity Weight setting
     * 
     * @return the value between 0 and 1.
     */
    public static float getChildrenSimilarityWeight() {
        if (properties == null) {
            init();
        }
        String value = properties.getProperty(SETTINGS_CHILDREN_LABEL);
        return (value != null) ? Float.parseFloat(value)
                : SETTINGS_CHILDREN_HARD_DEFAULT;
    }

    /**
     * Set value for the Name Similarity Required setting
     * 
     * @param value the value for the setting
     */
    public static void setNameSimilarityRequired(boolean value) {
        if (properties == null) {
            init();
        }
        properties.setProperty(SETTINGS_NAME_SIMILARITY_REQUIRED_LABEL,
                (new Boolean(value)).toString());
        save();
    }

    /**
     * Set value for the Dynamic Weight Allocation setting
     * 
     * @param value the value for the setting
     */
    public static void setDynamicWeightAllocation(boolean value) {
        if (properties == null) {
            init();
        }
        properties.setProperty(SETTINGS_DYNAMIC_WEIGHT_ALLOCATION_LABEL,
                (new Boolean(value)).toString());
        save();
    }

    /**
     * Set value for the Name Similarity Weight setting
     * 
     * @param value the value for the setting
     */
    public static void setNameSimilarityWeight(float value) {
        if (properties == null) {
            init();
        }
        properties.setProperty(SETTINGS_NAME_LABEL,
                (new Float(value)).toString());
        save();
    }

    /**
     * Set value for the Value Similarity Weight setting
     * 
     * @param value the value for the setting
     */
    public static void setValueSimilarityWeight(float value) {
        if (properties == null) {
            init();
        }
        properties.setProperty(SETTINGS_VALUE_LABEL,
                (new Float(value)).toString());
        save();
    }

    /**
     * Set value for the Attribute Similarity Weight setting
     * 
     * @param value the value for the setting
     */
    public static void setAttributeSimilarityWeight(float value) {
        if (properties == null) {
            init();
        }
        properties.setProperty(SETTINGS_ATTRIBUTE_LABEL,
                (new Float(value)).toString());
        save();
    }

    /**
     * Set value for the Children Similarity Weight setting
     * 
     * @param value the value for the setting
     */
    public static void setChildrenSimilarityWeight(float value) {
        if (properties == null) {
            init();
        }
        properties.setProperty(SETTINGS_CHILDREN_LABEL,
                (new Float(value)).toString());
        save();
    }

}

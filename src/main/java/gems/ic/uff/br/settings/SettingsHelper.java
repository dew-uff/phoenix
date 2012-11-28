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
    public static final String NAME_WEIGHT_LABEL = "NameSimilarityWeight";
    public static final String ATTRIBUTE_WEIGHT_LABEL = "AttributeSimilarityWeight";
    public static final String VALUE_WEIGHT_LABEL = "ValueSimilarityWeight";
    public static final String CHILDREN_WEIGHT_LABEL = "ChildrenSimilarityWeight";
    public static final String NAME_SIMILARITY_REQUIRED_LABEL = "NameSimilarityRequired";
    public static final String AUTOMATIC_WEIGHT_ALLOCATION_LABEL = "AutomaticWeightAllocation";
    public static final String IGNORE_TRIVIAL_SIMILARITIES_LABEL = "IgnoreTrivialSimilarities";

    // default values for each setting
    public static final float NAME_WEIGHT_HARD_DEFAULT = 0.25f;
    public static final float ATTRIBUTE__WEIGHT_HARD_DEFAULT = 0.25f;
    public static final float VALUE_WEIGHT_HARD_DEFAULT = 0.25f;
    public static final float CHILDREN_WEIGHT_HARD_DEFAULT = 0.25f;
    public static final boolean NAME_SIMILARITY_REQUIRED_HARD_DEFAULT = false;
    public static final boolean AUTOMATIC_WEIGHT_ALLOCATION_HARD_DEFAULT = false;
    public static final boolean IGNORE_TRIVIAL_SIMILARITIES_HARD_DEFAULT = false;

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
                .getProperty(NAME_SIMILARITY_REQUIRED_LABEL);
        return (value != null) ? Boolean.parseBoolean(value)
                : NAME_SIMILARITY_REQUIRED_HARD_DEFAULT;
    }

    /**
     * The value for the Automatic Weight Allocation setting
     * 
     * @return true if setting is turned on, false otherwise
     */
    public static boolean getAutomaticWeightAllocation() {
        if (properties == null) {
            init();
        }
        String value = properties
                .getProperty(AUTOMATIC_WEIGHT_ALLOCATION_LABEL);
        return (value != null) ? Boolean.parseBoolean(value)
                : AUTOMATIC_WEIGHT_ALLOCATION_HARD_DEFAULT;
    }

    /**
     * The value for the Ignore Trivial Similarities setting
     * 
     * @return true if setting is turned on, false otherwise
     */
    public static boolean getIgnoreTrivialSimilarities() {
        if (properties == null) {
            init();
        }
        String value = properties
                .getProperty(IGNORE_TRIVIAL_SIMILARITIES_LABEL);
        return (value != null) ? Boolean.parseBoolean(value)
                : IGNORE_TRIVIAL_SIMILARITIES_HARD_DEFAULT;
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
        String value = properties.getProperty(NAME_WEIGHT_LABEL);
        return (value != null) ? Float.parseFloat(value)
                : NAME_WEIGHT_HARD_DEFAULT;
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
        String value = properties.getProperty(VALUE_WEIGHT_LABEL);
        return (value != null) ? Float.parseFloat(value)
                : VALUE_WEIGHT_HARD_DEFAULT;
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
        String value = properties.getProperty(ATTRIBUTE_WEIGHT_LABEL);
        return (value != null) ? Float.parseFloat(value)
                : ATTRIBUTE__WEIGHT_HARD_DEFAULT;
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
        String value = properties.getProperty(CHILDREN_WEIGHT_LABEL);
        return (value != null) ? Float.parseFloat(value)
                : CHILDREN_WEIGHT_HARD_DEFAULT;
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
        properties.setProperty(NAME_SIMILARITY_REQUIRED_LABEL,
                (new Boolean(value)).toString());
        save();
    }

    /**
     * Set value for the Automatic Weight Allocation setting
     * 
     * @param value the value for the setting
     */
    public static void setAutomaticWeightAllocation(boolean value) {
        if (properties == null) {
            init();
        }
        properties.setProperty(AUTOMATIC_WEIGHT_ALLOCATION_LABEL,
                (new Boolean(value)).toString());
        save();
    }

    /**
     * Set value for the Ignore Trivial Similarities setting
     * 
     * @param value the value for the setting
     */
    public static void setIgnoreTrivialSimilarities(boolean value) {
        if (properties == null) {
            init();
        }
        properties.setProperty(IGNORE_TRIVIAL_SIMILARITIES_LABEL,
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
        properties.setProperty(NAME_WEIGHT_LABEL,
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
        properties.setProperty(VALUE_WEIGHT_LABEL,
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
        properties.setProperty(ATTRIBUTE_WEIGHT_LABEL,
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
        properties.setProperty(CHILDREN_WEIGHT_LABEL,
                (new Float(value)).toString());
        save();
    }

}

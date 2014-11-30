package br.uff.ic.gems.phoenix;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Singleton to isolate the file access logic from the application logic.
 * Every setting has a setter and getter in this class.
 * 
 * @author gabriel
 * 
 */
public class SettingsHelper {
    
    private static Logger LOG = Logger.getLogger(SettingsHelper.class.getName());

    // settings file name 
    public static final String SETTINGS_FILEPATH = ".phoenix_settings";

    // identifiers of the settings inside the file
    public static final String NAME_WEIGHT_LABEL = "NameSimilarityWeight";
    public static final String ATTRIBUTE_WEIGHT_LABEL = "AttributeSimilarityWeight";
    public static final String VALUE_WEIGHT_LABEL = "ValueSimilarityWeight";
    public static final String CHILDREN_WEIGHT_LABEL = "ChildrenSimilarityWeight";
    public static final String AUTOMATIC_WEIGHT_ALLOCATION_LABEL = "AutomaticWeightAllocation";
    public static final String IGNORE_TRIVIAL_SIMILARITIES_LABEL = "IgnoreTrivialSimilarities";
    public static final String THRESHOLD_LABEL = "SimilarityThreshold";

    // default values for each setting
    public static final double NAME_WEIGHT_HARD_DEFAULT = 0.25;
    public static final double ATTRIBUTE__WEIGHT_HARD_DEFAULT = 0.25;
    public static final double VALUE_WEIGHT_HARD_DEFAULT = 0.25;
    public static final double CHILDREN_WEIGHT_HARD_DEFAULT = 0.25;
    public static final boolean AUTOMATIC_WEIGHT_ALLOCATION_HARD_DEFAULT = false;
    public static final boolean IGNORE_TRIVIAL_SIMILARITIES_HARD_DEFAULT = false;
    public static final double THRESHOLD_HARD_DEFAULT = 0.0;

    // this singleton instance
    private static Properties properties = null;

    /**
     * Internal method to create singleton instance
     */
    private static void init() {
        if (properties == null) {
            try {
                String userHome = System.getProperty("user.home",".");
                String settingsFilepath = userHome + '/' + SETTINGS_FILEPATH;
                properties = new Properties();
                properties.load(new FileInputStream(settingsFilepath));
                LOG.fine("Readed settings file from: " + settingsFilepath);
            } catch (FileNotFoundException e) {
                LOG.warning("Settings file not found");
            } catch (IOException e) {
                LOG.severe("Something went wrong reading settings file!");
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
                String userHome = System.getProperty("user.home",".");
                String settingsFilepath = userHome + '/' + SETTINGS_FILEPATH;
                properties.store(new FileWriter(settingsFilepath), "");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Only for tests. Do not use it.
     */
    static public void dispose() {
        properties = null;
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
    public static double getNameSimilarityWeight() {
        if (properties == null) {
            init();
        }
        String value = properties.getProperty(NAME_WEIGHT_LABEL);
        return (value != null) ? Double.parseDouble(value)
                : NAME_WEIGHT_HARD_DEFAULT;
    }

    /**
     * The value for the Value Similarity Weight setting
     * 
     * @return the value between 0 and 1.
     */
    public static double getValueSimilarityWeight() {
        if (properties == null) {
            init();
        }
        String value = properties.getProperty(VALUE_WEIGHT_LABEL);
        return (value != null) ? Double.parseDouble(value)
                : VALUE_WEIGHT_HARD_DEFAULT;
    }

    /**
     * The value for the Attribute Similarity Weight setting
     * 
     * @return the value between 0 and 1.
     */
    public static double getAttributeSimilarityWeight() {
        if (properties == null) {
            init();
        }
        String value = properties.getProperty(ATTRIBUTE_WEIGHT_LABEL);
        return (value != null) ? Double.parseDouble(value)
                : ATTRIBUTE__WEIGHT_HARD_DEFAULT;
    }

    /**
     * The value for the Children Similarity Weight setting
     * 
     * @return the value between 0 and 1.
     */
    public static double getChildrenSimilarityWeight() {
        if (properties == null) {
            init();
        }
        String value = properties.getProperty(CHILDREN_WEIGHT_LABEL);
        return (value != null) ? Double.parseDouble(value)
                : CHILDREN_WEIGHT_HARD_DEFAULT;
    }
    
    /**
     * The value for the Similarity Threshold setting
     * 
     * @return the value between 0 and 1
     */
    public static double getSimilarityThreshold() {
        if (properties == null) {
            init();
        }
        String value = properties.getProperty(THRESHOLD_LABEL);
        return (value != null) ? Double.parseDouble(value)
                : THRESHOLD_HARD_DEFAULT;
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
    public static void setNameSimilarityWeight(double value) {
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
    public static void setValueSimilarityWeight(double value) {
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
    public static void setAttributeSimilarityWeight(double value) {
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
    public static void setChildrenSimilarityWeight(double value) {
        if (properties == null) {
            init();
        }
        properties.setProperty(CHILDREN_WEIGHT_LABEL,
                (new Float(value)).toString());
        save();
    }
    
    /**
     * Set value for the Similarity Threshold setting
     * 
     * @param value the value for the setting
     */
    public static void setSimilarityThreshold(double value) {
        if (properties == null) {
            init();
        }
        properties.setProperty(THRESHOLD_LABEL,
                (new Float(value)).toString());
        save();
    }
    
    /**
     * Basically guarantees that all weight values sum to 1.0.
     * 
     * @return true if settings are OK, false otherwise.
     */
    public static boolean areSettingsOk() {

        boolean automaticAllocation = getAutomaticWeightAllocation();
        double nameWeight = getNameSimilarityWeight();
        double valueWeight = getValueSimilarityWeight();
        double attributeWeight = getAttributeSimilarityWeight();
        double childrenWeight = getChildrenSimilarityWeight();

        if (automaticAllocation) {
            return true;
        } else {
            return (nameWeight + valueWeight + attributeWeight + childrenWeight) == 1.0;
        }
    }
}

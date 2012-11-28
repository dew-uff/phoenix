package gems.ic.uff.br.settings;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

/**
 * 
 * @author gabriel
 *
 */
public class SettingsHelper {
    
    public static final String SETTINGS_FILEPATH = ".phoenix_settings";
    
    public static final String SETTINGS_NAME_LABEL = "NameSimilarityWeight";
    public static final String SETTINGS_ATTRIBUTE_LABEL = "AttributeSimilarityWeight";
    public static final String SETTINGS_VALUE_LABEL = "ValueSimilarityWeight";
    public static final String SETTINGS_CHILDREN_LABEL = "ChildrenSimilarityWeight";
    public static final String SETTINGS_NAME_SIMILARITY_REQUIRED_LABEL = "NameSimilarityRequired";
    public static final String SETTINGS_DYNAMIC_WEIGHT_ALLOCATION_LABEL = "DynamicWeightAllocation";
    
    public static final float SETTINGS_NAME_HARD_DEFAULT = 0.25f;
    public static final float SETTINGS_ATTRIBUTE_HARD_DEFAULT = 0.25f;
    public static final float SETTINGS_VALUE_HARD_DEFAULT = 0.25f;
    public static final float SETTINGS_CHILDREN_HARD_DEFAULT = 0.25f;
    public static final boolean SETTINGS_NAME_SIMILARITY_REQUIRED_HARD_DEFAULT = false;
    public static final boolean SETTINGS_DYNAMIC_WEIGHT_ALLOCATION_HARD_DEFAULT = false;
    
    private static Properties properties = null;
    
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
    
    private static void save() {
        
        if (properties != null) {
            try {
                properties.store(new FileWriter(SETTINGS_FILEPATH), "");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean getNameSimilarityRequired() {
        if (properties == null) {
            init();
        }
        String value = properties.getProperty(
                SETTINGS_NAME_SIMILARITY_REQUIRED_LABEL);
        return (value != null)?Boolean.parseBoolean(value): 
                SETTINGS_NAME_SIMILARITY_REQUIRED_HARD_DEFAULT;
    }
    
    public static boolean getDynamicWeightAllocation() {
        if (properties == null) {
            init();
        }
        String value = properties.getProperty(
                SETTINGS_DYNAMIC_WEIGHT_ALLOCATION_LABEL);
        return (value != null)?Boolean.parseBoolean(value): 
                SETTINGS_DYNAMIC_WEIGHT_ALLOCATION_HARD_DEFAULT;
    }
    
    public static float getNameSimilarityWeight() {
        if (properties == null) {
            init();
        }
        String value = properties.getProperty(
                SETTINGS_NAME_LABEL);
        return (value != null)?Float.parseFloat(value): 
                SETTINGS_NAME_HARD_DEFAULT;
    }
    
    public static float getValueSimilarityWeight() {
        if (properties == null) {
            init();
        }
        String value = properties.getProperty(
                SETTINGS_VALUE_LABEL);
        return (value != null)?Float.parseFloat(value): 
                SETTINGS_VALUE_HARD_DEFAULT;
    }

    public static float getAttributeSimilarityWeight() {
        if (properties == null) {
            init();
        }
        String value = properties.getProperty(
                SETTINGS_ATTRIBUTE_LABEL);
        return (value != null)?Float.parseFloat(value): 
                SETTINGS_ATTRIBUTE_HARD_DEFAULT;
    }

    public static float getChildrenSimilarityWeight() {
        if (properties == null) {
            init();
        }
        String value = properties.getProperty(
                SETTINGS_CHILDREN_LABEL);
        return (value != null)?Float.parseFloat(value): 
                SETTINGS_CHILDREN_HARD_DEFAULT;
    }

    public static void setNameSimilarityRequired(boolean value) {
        if (properties == null) {
            init();
        }
        properties.setProperty(SETTINGS_NAME_SIMILARITY_REQUIRED_LABEL, 
                (new Boolean(value)).toString());
        save();
    }
    
    public static void setDynamicWeightAllocation(boolean value) {
        if (properties == null) {
            init();
        }
        properties.setProperty(SETTINGS_DYNAMIC_WEIGHT_ALLOCATION_LABEL, 
                (new Boolean(value)).toString());
        save();
    }
    
    public static void setNameSimilarityWeight(float value) {
        if (properties == null) {
            init();
        }
        properties.setProperty(SETTINGS_NAME_LABEL, 
                (new Float(value)).toString());
        save();
    }
    
    public static void setValueSimilarityWeight(float value) {
        if (properties == null) {
            init();
        }
        properties.setProperty(SETTINGS_VALUE_LABEL, 
                (new Float(value)).toString());
        save();
    }
    
    public static void setAttributeSimilarityWeight(float value) {
        if (properties == null) {
            init();
        }
        properties.setProperty(SETTINGS_ATTRIBUTE_LABEL, 
                (new Float(value)).toString());
        save();
    }
    
    public static void setChildrenSimilarityWeight(float value) {
        if (properties == null) {
            init();
        }
        properties.setProperty(SETTINGS_CHILDREN_LABEL, 
                (new Float(value)).toString());
        save();
    }

}

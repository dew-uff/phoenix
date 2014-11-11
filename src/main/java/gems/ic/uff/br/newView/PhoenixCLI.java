package gems.ic.uff.br.newView;

import gems.ic.uff.br.modelo.LcsXML;
import gems.ic.uff.br.modelo.XML;
import gems.ic.uff.br.settings.SettingsHelper;

public class PhoenixCLI {
    
    private static String xmlfilepath1 = null, 
                          xmlfilepath2 = null;
    
    private static float threshold = 0.7f;
    
    private static boolean nameRequired = true,
                           ignoreTrivial = true,
                           automaticAllocation = true;

    public static void main(String[] args) {
        
        if (args.length < 1) {
            showUsage();
            System.exit(1);
        }
        
        processArguments(args);
        
        SettingsHelper.setNameSimilarityRequired(nameRequired);
        SettingsHelper.setIgnoreTrivialSimilarities(ignoreTrivial);
        SettingsHelper.setAutomaticWeightAllocation(automaticAllocation);
        SettingsHelper.setSimilarityThreshold(threshold);
        
        if (xmlfilepath1 == null || xmlfilepath2 == null) {
            showErrorAndExit("Missing argument(s)");
        }
        
        XML xml1 = new XML(xmlfilepath1);
        XML xml2 = new XML(xmlfilepath2);
        
        LcsXML lcs = new LcsXML(xml1, xml2);
        
        XML diff = lcs.getDiffXML();

        System.out.println(diff);
    }

    private static void processArguments(String[] args) {
        for (String arg : args) {
            if (arg.charAt(0) == '-') {
                processOption(arg);
            }
            else if (xmlfilepath1 == null) {
                xmlfilepath1 = arg;
            }
            else if (xmlfilepath2 == null) {
                xmlfilepath2 = arg;
            }
            else {
                showErrorAndExit("Invalid parameter: " + arg);
                System.exit(1);
            }
        }
    }

    private static void processOption(String arg) {
        switch (arg.charAt(1)) {
            case 'h':
            case 'H':
                showOptions();
                break;
                
            case 't':
            case 'T':
                try {
                    threshold = Float.parseFloat(arg.substring(2));
                    if (threshold < 0 || threshold > 1) {
                        throw new Exception();
                    }
                }
                catch (Exception e) {
                    showErrorAndExit("Wrong value for option 'Threshold': must be a number in [0..1] range!");
                }
                break;
                
            case 'n':
            case 'N':
                try {
                    nameRequired = Boolean.parseBoolean(arg.substring(2));
                }
                catch (Exception e) {
                    showErrorAndExit("Wrong value for option 'NameSimilarityRequired': must be 'true' or 'false'!");
                }
                break;
            
            case 'i':
            case 'I':
                try {
                    ignoreTrivial = Boolean.parseBoolean(arg.substring(2));
                }
                catch (Exception e) {
                    showErrorAndExit("Wrong value for option 'IgnoreTrivialSimilarities': must be 'true' or 'false'!");
                }
                break;
                
            case 'a':
            case 'A':
                try {
                    automaticAllocation = Boolean.parseBoolean(arg.substring(2));
                }
                catch (Exception e) {
                    showErrorAndExit("Wrong value for option 'AutomaticWeightAllocation': must be 'true' or 'false'!");
                }
                break;
                
            default:
                showErrorAndExit("Invalid Option: " + arg);
                
        }
    }

    private static void showOptions() {
        System.out.println("\nUsage: PhoenixCLI [options] <xmlfile1> <xmlfile2>");
        System.out.println("\n\tOptions:\n");
        System.out.println("\t-h      : This help.");
        System.out.println("\t-tVALUE : Similarity threshold value. VALUE must be a number in [0..1] range. (Default: 0.7)");
        System.out.println("\t-nVALUE : Name similarity Required. VALUE must be a 'true' or 'false'. (Default: true)");
        System.out.println("\t-iVALUE : Ignore trivial similarities. VALUE must be a 'true' or 'false'. (Default: true)");
        System.out.println("\t-nVALUE : Automatic weight allocation. VALUE must be a 'true' or 'false'. (Default: true)");
        System.out.println();
        System.exit(0);
    }

    private static void showUsage() {
        System.out.println("\nUsage: PhoenixCLI [options] <xmlfile1> <xmlfile2>");
        System.out.println("\tUse '-h' for options listing.");
        System.exit(0);
    }
    
    private static void showErrorAndExit(String message) {
        System.err.println("Erro! " + message);
        System.exit(1);
    }

}

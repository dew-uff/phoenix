package br.uff.ic.gems.phoenix.similarity;

import br.uff.ic.gems.phoenix.SettingsHelper;
import br.uff.ic.gems.phoenix.algorithm.DataTypeSimilarity;
import br.uff.ic.gems.phoenix.exception.ComparisonException;

public class SimilarityWrapper {
    
    private static final DataTypeSimilarity DataTypeSim = new DataTypeSimilarity();
            
    public static double calculateSimilarity(String s1, String s2) throws ComparisonException {
        
        if(SettingsHelper.getIgnoreCaseOnSimilarity()) {
            s1 = s1.toLowerCase();
            s2 = s2.toLowerCase();
        }
        
        if(SettingsHelper.getAllowDataTypeSimilarity())
            return DataTypeSim.verifyTypeAndCalculateSimilarity(s1, s2);
        else
            return LcsSimilarity.calculateStringSimilarity(s1, s2);
                
    }
}

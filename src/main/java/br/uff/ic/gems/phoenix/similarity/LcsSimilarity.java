package br.uff.ic.gems.phoenix.similarity;

import br.uff.ic.gems.phoenix.algorithm.LcsAlgorithm;
import br.uff.ic.gems.phoenix.exception.ComparisonException;

public class LcsSimilarity {
    
    public static double calculateStringSimilarity(String s1, String s2) throws ComparisonException {
        
        if (s1 == null || s2 == null) {
            throw new ComparisonException("One of the strings is null");
        }
        
        int lcslength = LcsAlgorithm.calculateLcsLength(s1, s2);
        
        return lcslength / ((s1.length()+s2.length())/2.0f);
    }
}

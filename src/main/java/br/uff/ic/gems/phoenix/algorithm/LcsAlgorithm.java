package br.uff.ic.gems.phoenix.algorithm;

import static java.lang.Math.max;
import br.uff.ic.gems.phoenix.exception.ComparisonException;

/**
 * LCS Algorithm adapted from Wikipedia:
 * http://en.wikipedia.org/wiki/Longest_common_subsequence_problem
 *
 * @author jhess
 */
public class LcsAlgorithm {

    public static int calculateLcsLength(String s1, String s2) throws ComparisonException {
        
        if (s1 == null || s2 == null) {
            throw new ComparisonException("One of the strings is null!");
        }
        
        if (s1.isEmpty() || s2.isEmpty()) {
            // nothing to do. LCS is empty;
            return 0;
        }
        
        int[][] result = new int[s1.length()+1][s2.length()+1];
        
        for (int i = 1; i < result.length; i++) {
            for (int j = 1; j < result[i].length; j++) {
                if (s1.charAt(i-1) == s2.charAt(j-1)) {
                    result[i][j] = result[i - 1][j - 1] + 1;
                } else {
                    result[i][j] = max(result[i][j - 1], result[i - 1][j]);
                }
            }
        }
        
        return result[s1.length()][s2.length()];
    }
}

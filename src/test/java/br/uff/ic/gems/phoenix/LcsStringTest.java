package br.uff.ic.gems.phoenix;

import br.uff.ic.gems.phoenix.algorithm.LcsAlgorithm;
import br.uff.ic.gems.phoenix.exception.ComparisonException;
import junit.framework.TestCase;

public class LcsStringTest extends TestCase {

    public void testCalculateLcsLength() {
        String one = "AAABBBAAACCC";
        String two = "AABDDACEE";
        // LCS is "AABAC"
        // LCS length is 5
        try {
            int lcslength = LcsAlgorithm.calculateLcsLength(one, two);
            assertEquals(5, lcslength);
        } catch (ComparisonException e) {
            fail("Should not throw exception");
        }
    }
}

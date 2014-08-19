package gems.ic.uff.br.modelo.algorithm;

import org.junit.Test;

import static org.junit.Assert.*;

public class HungarianAlgorithmTest {

    public HungarianAlgorithmTest() {
    }

    @Test
    public void wikipedia() {
        //      Clean bathroom  Sweep floors    Wash windows
        //Jim       $1              $2              $3
        //Steve     $3              $3              $3
        //Alan      $3              $3              $2
        double[][] matrix = {{1, 2, 3}, {3, 3, 3}, {3, 3, 2}};
        HungarianAlgorithm algorithm = new HungarianAlgorithm(matrix);
        int[] result = algorithm.execute();

        assertEquals(result[0], 0); //Jim with Clean bathroom - Fixed

        assertEquals(result[1], 1); //Steve with Sweep floors - Fixed

        assertEquals(result[2], 2); //Alan with Wash windows - Fixed
    }

    @Test
    public void wikipediaModified() {
        //      Clean bathroom  Sweep floors    Wash windows
        //Jim       $3              $2              $3
        //Steve     $1              $3              $3
        //Alan      $3              $3              $2
        double[][] matrix = {{3, 2, 3}, {1, 3, 3}, {3, 3, 2}};
        HungarianAlgorithm algorithm = new HungarianAlgorithm(matrix);
        int[] result = algorithm.execute();
        
        assertEquals(result[0], 1); //Steve and Clean bathroom - Fixed

        assertEquals(result[1], 0); //Jim and Sweep floors - Fixed

        assertEquals(result[2], 2); //Alan and Wash windows - Fixed
    }
}

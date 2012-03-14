package gems.ic.uff.br.modelo.algorithm;

import org.junit.Test;

import static org.junit.Assert.*;

public class HungarianAlgorithmTest {

    private HungarianAlgorithm algorithm;

    public HungarianAlgorithmTest() {
        algorithm = new HungarianAlgorithm();
    }

    @Test
    public void wikipedia() {
        //      Clean bathroom  Sweep floors    Wash windows
        //Jim       $1              $2              $3
        //Steve     $3              $3              $3
        //Alan      $3              $3              $2
        float[][] matrix = {{1, 2, 3}, {3, 3, 3}, {3, 3, 2}};
        int[][] result = algorithm.computeAssignments(matrix);

        assertEquals(result[0][0], 0); //Jim
        assertEquals(result[0][1], 0); //Clean bathroom - Fixed

        assertEquals(result[1][0], 1); //Steve
        assertEquals(result[1][1], 1); //Sweep floors - Fixed

        assertEquals(result[2][0], 2); //Alan
        assertEquals(result[2][1], 2); //Wash windows - Fixed
    }

    @Test
    public void wikipediaModified() {
        //      Clean bathroom  Sweep floors    Wash windows
        //Jim       $3              $2              $3
        //Steve     $1              $3              $3
        //Alan      $3              $3              $2
        float[][] matrix = {{3, 2, 3}, {1, 3, 3}, {3, 3, 2}};
        int[][] result = algorithm.computeAssignments(matrix);
        
        assertEquals(result[0][0], 1); //Steve
        assertEquals(result[0][1], 0); //Clean bathroom - Fixed

        assertEquals(result[1][0], 0); //Jim
        assertEquals(result[1][1], 1); //Sweep floors - Fixed

        assertEquals(result[2][0], 2); //Alan
        assertEquals(result[2][1], 2); //Wash windows - Fixed
    }
}

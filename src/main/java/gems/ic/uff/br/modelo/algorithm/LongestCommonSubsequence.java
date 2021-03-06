package gems.ic.uff.br.modelo.algorithm;

import gems.ic.uff.br.modelo.similar.Similar;

import static java.lang.Math.*;

/**
 * LCS Algorithm from Wikipedia:
 * http://en.wikipedia.org/wiki/Longest_common_subsequence_problem
 *
 * @author jhess
 */
public abstract class LongestCommonSubsequence<VALUE extends Similar> extends AbstractAlgorithm {

    private int[][] result;
    private float[][] backtrack;

    /**
     * Esse método monta a tabela LCS.
     * Ex.: AGCAT e GAC
     *          Ø	A	G	C	A	T
     * Ø	0	0	0	0	0	0
     * G	0	0	1	1	1	1
     * A	0	1	1	1	2	2
     * C	0	1	1	2	2	2
     */
    private void calculateLcs() {
        if (result == null) {
            //Cria a tabela.
            result = new int[lengthOfX() + 1][lengthOfY() + 1];

            //Seta os valores na tabela.
            for (int i = 1; i < result.length; i++) {
                for (int j = 1; j < result[i].length; j++) {
                    if (isXYSimilar(i, j)) {
                        result[i][j] = result[i - 1][j - 1] + 1;
                    } else {
                        result[i][j] = max(result[i][j - 1], result[i - 1][j]);
                    }
                }
            }
        }
    }

    public float[][] resultWithSimilarity() {
        if (backtrack == null) {
            calculateLcs();

            int i = lengthOfX();
            int j = lengthOfY();
            int pos = getLcsLength();
            
            backtrack = new float[pos][3];

            while (i != 0 && j != 0) {
                if (isXYSimilar(i, j)) {
                    i = i - 1;
                    j = j - 1;
                    pos = pos - 1;
                    
                    backtrack[pos][0] = i;
                    backtrack[pos][1] = j;
                    backtrack[pos][2] = similar(valueOfX(i), valueOfY(j));
                } else {
                    if (result[i][j - 1] > result[i - 1][j]) {
                        j = j - 1;
                    } else {
                        i = i - 1;
                    }
                }
            }
        }

        return backtrack;
    }

    /**
     * @return o tamanho da maior sequência comum.
     */
    private int getLcsLength() {
        calculateLcs();

        return result[lengthOfX()][lengthOfY()];
    }

    public float similaridade() {
        calculateLcs();
        
        Float resultado = getLcsLength() / ((lengthOfX() + lengthOfY()) / 2f);
        
        return Float.isNaN(resultado) ? 0 : resultado;

//        return getLcsLength() / ((lengthOfX() + lengthOfY()) / 2f);
    }
}

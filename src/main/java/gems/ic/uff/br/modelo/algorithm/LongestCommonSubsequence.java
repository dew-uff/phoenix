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

    private int[][] table;

    /**
     * Esse método monta a tabela LCS.
     * Ex.: AGCAT e GAC
     *          Ø	A	G	C	A	T
    Ø	0	0	0	0	0	0
    G	0	0	1	1	1	1
    A	0	1	1	1	2	2
    C	0	1	1	2	2	2
     */
    public void calculateLcs() {
        //Verifica se a tabela já foi montada anteriormente.
        if (table != null) {
            return;
        }

        //Cria a tabela.
        table = new int[lengthOfX() + 1][];
        for (int i = 0; i < table.length; i++) {
            table[i] = new int[lengthOfY() + 1];
        }

        //Seta os valores na tabela.
        for (int i = 1; i < table.length; i++) {
            for (int j = 1; j < table[i].length; j++) {
                if (ixXYSimilar(i, j)) {
                    table[i][j] = table[i - 1][j - 1] + 1;
                } else {
                    table[i][j] = max(table[i][j - 1], table[i - 1][j]);
                }
            }
        }
    }

    /**
     * @return o tamanho da maior sequência comum.
     */
    public int getLcsLength() {
        calculateLcs();

        return table[lengthOfX()][lengthOfY()];
    }

    public double similaridade() {
        calculateLcs();
        
        int lcsLength = getLcsLength();
        
        return lcsLength / ((lengthOfX() + lengthOfY()) / 2.0);
    }
}

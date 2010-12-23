package gems.ic.uff.br.modelo;

import static java.lang.Math.*;

import java.util.ArrayList;
//import java.util.Collections;
import java.util.List;

/**
 * A class to compute the longest common subsequence in two strings.  
 * Algorithms from Wikipedia:
 * http://en.wikipedia.org/wiki/Longest_common_subsequence_problem
 * 
 * @author jhess
 *
 */
public abstract class LongestCommonSubsequence<VALUE extends Similar> {

    private int[][] c;
//    private ArrayList<DiffEntry<VALUE>> diff;
    private ArrayList<VALUE> backtrack;

    /**
     * A constructor for classes inheriting this one, allowing them to 
     * do some initialization before setting the values of X and Y.  Once 
     * the initialization is complete, the inheriting class must call
     * initValues(VALUE[] x, VALUE[] y)
     */
    protected LongestCommonSubsequence() {
    }

    protected abstract int lengthOfX();

    protected abstract int lengthOfY();

    protected abstract VALUE valueOfX(int index);

    protected abstract VALUE valueOfY(int index);
    
    protected double similar(VALUE x1, VALUE y1) {
        double similarity = 0;
        
        if (x1 != null && y1 != null) {
            similarity = x1.similar(y1);
        }
        
        return similarity;
    }

    private boolean ixXYSimilar(int i, int j) {
        return similar(valueOfXInternal(i), valueOfYInternal(j)) > 0.5;
    }

    private VALUE valueOfXInternal(int i) {
        return valueOfX(i - 1);
    }

    private VALUE valueOfYInternal(int j) {
        return valueOfY(j - 1);
    }

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
        if (c != null) {
            return;
        }

        //Cria a tabela.
        c = new int[lengthOfX() + 1][];
        for (int i = 0; i < c.length; i++) {
            c[i] = new int[lengthOfY() + 1];
        }

        //Seta os valores na tabela.
        for (int i = 1; i < c.length; i++) {
            for (int j = 1; j < c[i].length; j++) {
                if (ixXYSimilar(i, j)) {
                    c[i][j] = c[i - 1][j - 1] + 1;
                } else {
                    c[i][j] = max(c[i][j - 1], c[i - 1][j]);
                }
            }
        }
    }

    /**
     * @return o tamanho da maior sequência comum.
     */
    public int getLcsLength() {
        calculateLcs();

        return c[lengthOfX()][lengthOfY()];
    }

    /**
     * A distância de Edição entre dois objetos é o número de operações requeridas
     * para transformar um objeto em outro.
     * @return quantidade de modificações
     */
    public int getMinEditDistance() {
        calculateLcs();

        return lengthOfX() + lengthOfY() - 2 * abs(getLcsLength());
    }

    /**
     * Caminho percorrido para montar a maior sequência comum.
     * @return os objetos contidos no LCS.
     */
    public List<VALUE> backtrack() {
        calculateLcs();

        if (this.backtrack == null) {
            this.backtrack = new ArrayList<VALUE>();
            backtrack(lengthOfX(), lengthOfY());
        }

        return this.backtrack;
    }

    /**
     * Percorre a tabela LCS pelo caminho do LCS e salva os objetos contidos
     * neste caminho na variável backtrack.
     * @param i
     * @param j
     */
    public void backtrack(int i, int j) {
        calculateLcs();

        if (i == 0 || j == 0) {
            return;
        } else if (ixXYSimilar(i, j)) {
            backtrack(i - 1, j - 1);
            backtrack.add(valueOfXInternal(i));
        } else {
            if (c[i][j - 1] > c[i - 1][j]) {
                backtrack(i, j - 1);
            } else {
                backtrack(i - 1, j);
            }
        }
    }
    
    public double similaridade() {
        calculateLcs();
        
        int lcsLength = getLcsLength();
        
        return lcsLength / ((lengthOfX() + lengthOfY()) / 2.0);
    }

//    public List<DiffEntry<VALUE>> diff() {
//        calculateLcs();
//
//        if (this.diff == null) {
//            this.diff = new ArrayList<DiffEntry<VALUE>>();
//            diff(lengthOfX(), lengthOfY());
//        }
//
//        return this.diff;
//    }
//
//    private void diff(int i, int j) {
//        calculateLcs();
//
//        while (!(i == 0 && j == 0)) {
//            if (i > 0 && j > 0 && isXYEqual(i, j)) {
//                this.diff.add(new DiffEntry<VALUE>(DiffType.NONE, valueOfXInternal(i)));
//                i--;
//                j--;
//
//            } else {
//                if (j > 0 && (i == 0 || c[i][j - 1] >= c[i - 1][j])) {
//                    this.diff.add(new DiffEntry<VALUE>(DiffType.ADD, valueOfYInternal(j)));
//                    j--;
//
//                } else if (i > 0 && (j == 0 || c[i][j - 1] < c[i - 1][j])) {
//                    this.diff.add(new DiffEntry<VALUE>(DiffType.REMOVE, valueOfXInternal(i)));
//                    i--;
//                }
//            }
//        }
//
//        Collections.reverse(this.diff);
//    }
//
//    @Override
//    public String toString() {
//        calculateLcs();
//
//        StringBuilder buf = new StringBuilder();
//        buf.append("  ");
//
//        for (int j = 1; j <= lengthOfY(); j++) {
//            buf.append(valueOfYInternal(j));
//        }
//
//        buf.append("\n");
//        buf.append(" ");
//
//        for (int j = 0; j < c[0].length; j++) {
//            buf.append(Integer.toString(c[0][j]));
//        }
//
//        buf.append("\n");
//
//        for (int i = 1; i < c.length; i++) {
//            buf.append(valueOfXInternal(i));
//
//            for (int j = 0; j < c[i].length; j++) {
//                buf.append(Integer.toString(c[i][j]));
//            }
//
//            buf.append("\n");
//        }
//
//        return buf.toString();
//    }
//
//    public static enum DiffType {
//
//        ADD("+", "add"), REMOVE("-", "remove"), NONE(" ", "none");
//        private String val;
//        private String name;
//
//        DiffType(String val, String name) {
//            this.val = val;
//            this.name = name;
//        }
//
//        @Override
//        public String toString() {
//            return val;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public String getVal() {
//            return val;
//        }
//    }
//
//    public static class DiffEntry<VALUE> {
//
//        private DiffType type;
//        private VALUE value;
//
//        public DiffEntry(DiffType type, VALUE value) {
//            super();
//            this.type = type;
//            this.value = value;
//        }
//
//        public DiffType getType() {
//            return type;
//        }
//
//        public void setType(DiffType type) {
//            this.type = type;
//        }
//
//        public VALUE getValue() {
//            return value;
//        }
//
//        public void setValue(VALUE value) {
//            this.value = value;
//        }
//
//        @Override
//        public String toString() {
//            return type.toString() + value.toString();
//        }
//    }
}

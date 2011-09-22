package gems.ic.uff.br.modelo;

import gems.ic.uff.br.modelo.algorithm.LongestCommonSubsequence;
import gems.ic.uff.br.modelo.similar.SimilarCharacter;
import gems.ic.uff.br.modelo.similar.SimilarString;

public class LcsString extends LongestCommonSubsequence<SimilarCharacter> {

    private String x;
    private String y;

    public LcsString(String from, String to) {
        this.x = from;
        this.y = to;
    }
    public LcsString(SimilarString from, SimilarString to) {
        this.x = from.toString();
        this.y = to.toString();
    }

    protected int lengthOfY() {
        return y.length();
    }

    protected int lengthOfX() {
        return x.length();
    }

    public SimilarCharacter valueOfX(int index) {
        return new SimilarCharacter(x.charAt(index));
    }

    public SimilarCharacter valueOfY(int index) {
        return new SimilarCharacter(y.charAt(index));
    }
}

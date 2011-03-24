package gems.ic.uff.br.modelo.algorithm;

import gems.ic.uff.br.modelo.similar.Similar;
import org.w3c.dom.NodeList;

public abstract class AbstractAlgorithm<VALUE extends Similar> {

    protected abstract int lengthOfX();

    protected abstract int lengthOfY();

    protected abstract VALUE valueOfX(int index);

    protected abstract VALUE valueOfY(int index);

    private VALUE valueOfXInternal(int i) {
        return valueOfX(i - 1);
    }

    private VALUE valueOfYInternal(int j) {
        return valueOfY(j - 1);
    }

    protected double similar(VALUE x1, VALUE y1) {
        double similarity = 0;

        if (x1 != null && y1 != null) {
            similarity = x1.similar(y1);
        }

        return similarity;
    }

    protected boolean ixXYSimilar(int i, int j) {
        return similar(valueOfXInternal(i), valueOfYInternal(j)) > 0.5;
    }
    
    public abstract double similaridade();
    
//    public abstract double similaridadeCriandoDiffs(NodeList diff1, NodeList diff2);
}

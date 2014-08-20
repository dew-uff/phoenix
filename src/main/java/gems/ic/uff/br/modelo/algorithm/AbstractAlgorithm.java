package gems.ic.uff.br.modelo.algorithm;

import gems.ic.uff.br.modelo.similar.Similar;
import gems.ic.uff.br.settings.SettingsHelper;

public abstract class AbstractAlgorithm<VALUE extends Similar> {

    float similarThreshold = SettingsHelper.getSimilarityThreshold();

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

    protected float similar(VALUE x1, VALUE y1) {
        float similarity = 0;

        if (x1 != null && y1 != null) {
            similarity = x1.similar(y1).getSimilarity();
        }

        return similarity;
    }

    protected boolean isXYSimilar(int i, int j) {
        return similar(valueOfXInternal(i), valueOfYInternal(j)) >= similarThreshold;
    }

    public abstract float similaridade();
}

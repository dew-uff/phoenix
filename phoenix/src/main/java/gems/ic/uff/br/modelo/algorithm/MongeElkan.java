package gems.ic.uff.br.modelo.algorithm;

import gems.ic.uff.br.modelo.similar.Similar;

/**
 * Monge Elkan Algorithm from:
 * http://www.dcs.shef.ac.uk/~sam/stringmetrics.html
 * 
 * @author Sam Chapman <a href="http://www.dcs.shef.ac.uk/~sam/">Website</a>, <a href="mailto:sam@dcs.shef.ac.uk">Email</a>.
 */
public abstract class MongeElkan<VALUE extends Similar> extends AbstractAlgorithm {

    public float similaridade() {
        float sumMatches = 0f;
        for (int i = 0; i < this.lengthOfX(); i++) {
            float maxFound = 0f;
            Similar valueX = valueOfX(i);

            for (int j = 0; j < this.lengthOfY(); j++) {
                Similar valueY = valueOfY(j);

                final float found = valueX.similar(valueY);
                if (found > maxFound) {
                    maxFound = found;
                }
            }
            sumMatches += maxFound;
        }

        return sumMatches / ((lengthOfX() + lengthOfY()) / 2f);
    }
}

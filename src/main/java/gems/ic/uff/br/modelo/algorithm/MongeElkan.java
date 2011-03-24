package gems.ic.uff.br.modelo.algorithm;

import gems.ic.uff.br.modelo.similar.Similar;

/**
 * Monge Elkan Algorithm from:
 * http://www.dcs.shef.ac.uk/~sam/stringmetrics.html
 * 
 * @author Sam Chapman <a href="http://www.dcs.shef.ac.uk/~sam/">Website</a>, <a href="mailto:sam@dcs.shef.ac.uk">Email</a>.
 */
public abstract class MongeElkan<VALUE extends Similar> extends AbstractAlgorithm {

    public double similaridade() {
        double sumMatches = 0.0;
        for (int i = 0; i < this.lengthOfX(); i++) {
            double maxFound = 0.0;
            Similar valueX = valueOfX(i);

            for (int j = 0; j < this.lengthOfY(); j++) {
                Similar valueY = valueOfY(j);

                final double found = valueX.similar(valueY);
                if (found > maxFound) {
                    maxFound = found;
                }
            }
            sumMatches += maxFound;
        }

        return sumMatches / ((lengthOfX() + lengthOfY()) / 2.0);
    }
}

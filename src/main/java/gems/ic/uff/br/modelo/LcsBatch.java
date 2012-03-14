package gems.ic.uff.br.modelo;

import gems.ic.uff.br.modelo.similar.Similar;
import gems.ic.uff.br.modelo.similar.SimilarString;
import java.util.List;

public class LcsBatch {

    private List<Similar> x;
    private List<Similar> y;

    public LcsBatch(List<Similar> from, List<Similar> to) {
        this.x = from;
        this.y = to;
    }

    public float similaridade() {
        float similaridade = 0;

        //TODO: É melhor deixar dessa forma?
        if (x.get(0) instanceof SimilarString) {
            for (int i = 0; i < x.size(); i++) {
                LcsString lcs = new LcsString(((SimilarString) x.get(i)).getString(), ((SimilarString) y.get(i)).getString());
                similaridade += lcs.similaridade();
            }
        }

        return similaridade / x.size();
    }
}

package gems.ic.uff.br.modelo;

import com.sun.org.apache.xpath.internal.NodeSet;
import gems.ic.uff.br.modelo.algorithm.Hungarian;
import gems.ic.uff.br.modelo.similar.Similar;
import gems.ic.uff.br.modelo.similar.SimilarNode;

import java.util.ArrayList;
import java.util.List;

public class HungarianList extends Hungarian<Similar> {

    private List<Similar> x;
    private List<Similar> y;

    public HungarianList(List<Similar> from, List<Similar> to) {
        this.x = from;
        this.y = to;
    }

    public HungarianList(NodeSet from, NodeSet to) {
        this.x = new ArrayList<Similar>(from.getLength());
        this.y = new ArrayList<Similar>(to.getLength());

        for (int i = 0; i < from.getLength(); i++) {
            x.add(new SimilarNode(from.item(i)));
        }

        for (int i = 0; i < to.getLength(); i++) {
            y.add(new SimilarNode(to.item(i)));
        }
    }

    @Override
    protected int lengthOfX() {
        return x.size();
    }

    @Override
    protected int lengthOfY() {
        return y.size();
    }

    @Override
    protected Similar valueOfX(int index) {
        return x.get(index);
    }

    @Override
    protected Similar valueOfY(int index) {
        return y.get(index);
    }

    //    TODO: @Override
    public void addResultParent(Diff diff) {
        for (int i = 0; i < result.length; i++) {
            if (result[i][0] < lengthOfX() && result[i][1] < lengthOfY()) {
                SimilarNode firstNode = (SimilarNode) x.get(result[i][0]);
                Diff otherDiff = firstNode.similar((SimilarNode) y.get(result[i][1])); //TODO: Aqui estamos refazendo o diff.

                diff.addChildren(otherDiff);
            } else {
                if (result[i][0] < lengthOfX()) {
                    //TODO: Adicionar os elementos que não são similares.
//                    diff.addChildren(((SimilarNode) x.get(result[i][0])).getDiff());
                } else {
                    //TODO: Adicionar os elementos que não são similares.
//                    diff.addChildren(((SimilarNode) y.get(result[i][1])).getDiff());
                }
            }
        }
    }
}

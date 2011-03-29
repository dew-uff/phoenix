package gems.ic.uff.br.modelo;

import com.sun.org.apache.xpath.internal.NodeSet;
import gems.ic.uff.br.modelo.algorithm.MongeElkan;
import gems.ic.uff.br.modelo.similar.Similar;
import gems.ic.uff.br.modelo.similar.SimilarNode;
import java.util.ArrayList;
import java.util.List;

public class HungarianList extends MongeElkan<Similar> {

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
}

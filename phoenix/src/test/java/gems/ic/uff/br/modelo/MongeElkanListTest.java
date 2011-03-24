package gems.ic.uff.br.modelo;

import org.junit.Before;
import java.util.List;
import gems.ic.uff.br.modelo.similar.Similar;
import gems.ic.uff.br.modelo.similar.SimilarString;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

public class MongeElkanListTest {
    
    private List<Similar> similarList;
    private List<Similar> otherSimilarList;

    public MongeElkanListTest() {
    }
    
    @Before
    public void setUp() {
        similarList = new ArrayList<Similar>();
        otherSimilarList = new ArrayList<Similar>();
    }
    
    
    @Test
    public void similaridadeEntreElementosIguais() {
        similarList.add(new SimilarString("a"));

        assertEquals(1, new MongeElkanList(similarList, similarList).similaridade(), 0);
    }
    
    @Test
    public void similaridadeEntreElementosDiferentes() {
        similarList.add(new SimilarString("a"));
        otherSimilarList.add(new SimilarString("b"));

        assertEquals(0, new MongeElkanList(similarList, otherSimilarList).similaridade(), 0);
    }
    
    @Test
    public void similaridadeEntreUmElementoIgualEUmDiferente() {
        similarList.add(new SimilarString("a"));
        similarList.add(new SimilarString("b"));
        otherSimilarList.add(new SimilarString("b"));

        assertEquals(0.6, new MongeElkanList(similarList, otherSimilarList).similaridade(), 0.1);
    }
}

package gems.ic.uff.br.modelo;

import gems.ic.uff.br.modelo.SimilarNode;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.w3c.dom.Node;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class SimilarNodeTest {
    
    @Mock
    private Node mockNode;
    @Mock
    private Node mockNode2;

    public SimilarNodeTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void oPesoDoAtributoDeveriaSer05() {
        assertEquals(0.5, SimilarNode.ATTRIBUTE_WEIGTH, 0);
    }

    @Test
    public void deveriaTerSimilaridade1CasoOsDoisPossuamApenasUmaTagVaziaEIgual() {
        SimilarNode similarNode = new SimilarNode(mockNode);
        when(mockNode.getNodeName()).thenReturn("igual");

        SimilarNode similarNode2 = new SimilarNode(mockNode2);
        when(mockNode2.getNodeName()).thenReturn("igual");

        assertEquals(1, similarNode.similar(similarNode2), 0);
    }

    @Test
    public void deveriaTerSimilaridade0CasoNenhumaTagSejaIgual() {
        SimilarNode similarNode = new SimilarNode(mockNode);
        when(mockNode.getNodeName()).thenReturn("diferente1");

        SimilarNode similarNode2 = new SimilarNode(mockNode2);
        when(mockNode2.getNodeName()).thenReturn("diferente2");

        assertEquals(0, similarNode.similar(similarNode2), 0);
    }

    @Test
    public void deveriaTerSimilaridade1CasoAsTagsSejamIguaisENaoPossuamAtributos() {
        SimilarNode similarNode = new SimilarNode(mockNode);
        when(mockNode.getNodeName()).thenReturn("igual");
        when(mockNode.hasAttributes()).thenReturn(false);

        SimilarNode similarNode2 = new SimilarNode(mockNode2);
        when(mockNode2.getNodeName()).thenReturn("igual");
        when(mockNode2.hasAttributes()).thenReturn(false);

        assertEquals(1, similarNode.similar(similarNode2), 0);
    }
    
    @Test
    public void deveriaTerSimilaridade05CasoAsTagsSejamIguaisMasApenasOPrimeiroTenhaAtributo() {
        SimilarNode similarNode = new SimilarNode(mockNode);
        when(mockNode.getNodeName()).thenReturn("igual");
        when(mockNode.hasAttributes()).thenReturn(true);

        SimilarNode similarNode2 = new SimilarNode(mockNode2);
        when(mockNode2.getNodeName()).thenReturn("igual");
        when(mockNode2.hasAttributes()).thenReturn(false);

        assertEquals(0.5, similarNode.similar(similarNode2), 0);
    }

    @Test
    public void deveriaTerSimilaridade05CasoAsTagsSejamIguaisMasApenasOSegundoTenhaAtributo() {
        SimilarNode similarNode = new SimilarNode(mockNode);
        when(mockNode.getNodeName()).thenReturn("igual");
        when(mockNode.hasAttributes()).thenReturn(false);

        SimilarNode similarNode2 = new SimilarNode(mockNode2);
        when(mockNode2.getNodeName()).thenReturn("igual");
        when(mockNode2.hasAttributes()).thenReturn(true);

        assertEquals(0.5, similarNode.similar(similarNode2), 0);
    }
}

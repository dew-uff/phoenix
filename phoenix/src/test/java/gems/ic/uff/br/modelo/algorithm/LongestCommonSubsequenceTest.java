package gems.ic.uff.br.modelo.algorithm;

import gems.ic.uff.br.modelo.LcsString;
import gems.ic.uff.br.modelo.HungarianList;
import org.junit.Before;

import java.util.List;

import gems.ic.uff.br.modelo.similar.Similar;
import gems.ic.uff.br.modelo.similar.SimilarCharacter;
import gems.ic.uff.br.modelo.similar.SimilarNode;

import java.io.StringReader;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Test;
import org.xml.sax.InputSource;

import static org.junit.Assert.*;

public class LongestCommonSubsequenceTest {

    private String equal;
    private String unequal;
    private String anotherUnequal;
    private String similar;
    private String anotherSimilar;

    public LongestCommonSubsequenceTest() {
    }

    @Before
    public void setUp() {
    }

    @Test
    public void similaridadeEntreElementosIguais() {
        equal = "equal";

        assertEquals(1, new LcsString(equal, equal).similaridade(), 0);

        LcsString algorithm = new LcsString(equal, equal);
        float[][] result = algorithm.resultWithSimilarity();

        assertEquals(new SimilarCharacter('e'), algorithm.valueOfX((int) result[0][0])); //Caracter 'e'
        assertEquals(new SimilarCharacter('e'), algorithm.valueOfY((int) result[0][1])); //Caracter 'e'
        assertEquals(result[0][2], 1, 0); //Similaridade

        assertEquals(new SimilarCharacter('l'), algorithm.valueOfX((int) result[4][0])); //Caracter 'l'
        assertEquals(new SimilarCharacter('l'), algorithm.valueOfY((int) result[4][1])); //Caracter 'l'
        assertEquals(result[4][2], 1, 0); //Similaridade
    }

    @Test
    public void desigualdadeEntreElementosDiferentes() {
        unequal = "a";
        anotherUnequal = "b";

        assertEquals(0, new LcsString(unequal, anotherUnequal).similaridade(), 0);

        LcsString algorithm = new LcsString(unequal, anotherUnequal);
        float[][] result = algorithm.resultWithSimilarity();

        assertEquals(result.length, 0); //Não há similaridade
    }

    @Test
    public void similaridadeEntreUmElementoIgualEUmDiferente() {
        similar = "ab";
        anotherSimilar = "bc";

        assertEquals(1.0 / (4.0 / 2.0), new LcsString(similar, anotherSimilar).similaridade(), 0);

        LcsString algorithm = new LcsString(similar, anotherSimilar);
        float[][] result = algorithm.resultWithSimilarity();

        assertEquals(new SimilarCharacter('b'), algorithm.valueOfX((int) result[0][0])); //Caracter 'b'
        assertEquals(new SimilarCharacter('b'), algorithm.valueOfY((int) result[0][1])); //Caracter 'b'
        assertEquals(result[0][2], 1, 0); //Similaridade
    }

    @Test
    public void similaridadeEntreDoisElementosIguaisEUmDiferente() {
        similar = "abc";
        anotherSimilar = "bcd";
        assertEquals(2.0 / (6.0 / 2.0), new LcsString(similar, anotherSimilar).similaridade(), 0.01);

        LcsString algorithm = new LcsString(similar, anotherSimilar);
        float[][] result = algorithm.resultWithSimilarity();

        assertEquals(new SimilarCharacter('b'), algorithm.valueOfX((int) result[0][0])); //Caracter 'b'
        assertEquals(new SimilarCharacter('b'), algorithm.valueOfY((int) result[0][1])); //Caracter 'b'
        assertEquals(result[0][2], 1, 0); //Similaridade
        
        assertEquals(new SimilarCharacter('c'), algorithm.valueOfX((int) result[1][0])); //Caracter 'c'
        assertEquals(new SimilarCharacter('c'), algorithm.valueOfY((int) result[1][1])); //Caracter 'c'
        assertEquals(result[1][2], 1, 0); //Similaridade
    }
}
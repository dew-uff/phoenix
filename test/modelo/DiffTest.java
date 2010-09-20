package modelo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class DiffTest {
    
    public DiffTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    // ------------------- Teste de XMLs com tags apenas. -------------------
    @Test
    public void deveriaRetornar1CasoAsTagsSejamIguais() {
        double percentual = DiffXML.calcularPercentual("<tag>", "<tag>");
        assertEquals(1.0, percentual, 0.0);
    }
    
    @Test
    public void deveriaRetornar1CasoAsTagsNaoSejamIguais() {
        double percentual = DiffXML.calcularPercentual("<tag>", "<tagDiferente>");
        assertEquals(0.0, percentual, 0.0);
    }
}
package modelo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AtributoTest {

    public AtributoTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void deveriaPegarAChaveQueVemAntesDoIgual() {
        Atributo atributo = new Atributo("chave='1'");
        assertEquals("chave", atributo.getChave());
    }
    
    @Test
    public void deveriaPegarAChaveQueVemAntesDoIgualEntreChavesDuplas() {
        Atributo atributo = new Atributo("chave=\"1\"");
        assertEquals("chave", atributo.getChave());
    }
    
    @Test
    public void deveriaPegarAChaveSeTiverUmEspacoAntes() {
        Atributo atributo = new Atributo(" chave='1'");
        assertEquals("chave", atributo.getChave());
    }

    @Test
    public void deveriaPegarOValorQueVemDepoisDoIgual() {
        Atributo atributo = new Atributo("chave='1'");
        assertEquals("1", atributo.getValor());
    }
    
    @Test
    public void deveriaPegarOValorQueVemDepoisDoIgualEEntreChavesDuplas() {
        Atributo atributo = new Atributo("chave=\"1\"");
        assertEquals("1", atributo.getValor());
    }
    
    @Test
    public void deveriaPegarOValorSeTiverUmEspacoDepois() {
        Atributo atributo = new Atributo("chave='1' ");
        assertEquals("1", atributo.getValor());
    }
}
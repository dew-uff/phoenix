package modelo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

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
        assertThat(atributo.getChave(), is("chave"));
    }
    
    @Test
    public void deveriaPegarAChaveQueVemAntesDoIgualEntreChavesDuplas() {
        Atributo atributo = new Atributo("chave=\"1\"");
        assertThat(atributo.getChave(), is("chave"));
    }
    
    @Test
    public void deveriaPegarAChaveSeTiverUmEspacoAntes() {
        Atributo atributo = new Atributo(" chave='1'");
        assertThat(atributo.getChave(), is("chave"));
    }

    @Test
    public void deveriaPegarOValorQueVemDepoisDoIgual() {
        Atributo atributo = new Atributo("chave='1'");
        assertThat(atributo.getValor(), is("1"));
    }
    
    @Test
    public void deveriaPegarOValorQueVemDepoisDoIgualEEntreChavesDuplas() {
        Atributo atributo = new Atributo("chave=\"1\"");
        assertThat(atributo.getValor(), is("1"));
    }
    
    @Test
    public void deveriaPegarOValorSeTiverUmEspacoDepois() {
        Atributo atributo = new Atributo("chave='1' ");
        assertThat(atributo.getValor(), is("1"));
    }
    
    @Test
    public void deveriaSerIgualSeTiverAChaveEOValorIgual() {
        Atributo atributo1 = new Atributo("chave='1'");
        Atributo atributo2 = new Atributo("chave='1'");
        
        assertThat(atributo1, is(atributo2));
    }
    
    @Test
    public void naoDeveriaSerIgualSeTiverAChaveDiferente() {
        Atributo atributo1 = new Atributo("chave='1'");
        Atributo atributo2 = new Atributo("chave2='1'");
        
        assertThat(atributo1, is(not(atributo2)));
    }
    
    @Test
    public void naoDeveriaSerIgualSeTiverOValorDiferente() {
        Atributo atributo1 = new Atributo("chave='1'");
        Atributo atributo2 = new Atributo("chave='2'");
        
        assertThat(atributo1, is(not(atributo2)));
    }
    
    @Test
    public void deveriaTerUmToStringDefinido() {
        Atributo atributo = new Atributo("chave='1'");
        
        assertThat(atributo.toString(), is("Atributo{chave=chave, valor=1}"));
    }
}
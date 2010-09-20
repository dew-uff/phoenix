package modelo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TagTest {

    public TagTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void deveriaTerONomeDeUmaTagDeEntrada() {
        Tag tag = new Tag("<start-tag>");
        assertEquals("start-tag", tag.getNome());
    }

    @Test
    public void deveriaTerONomeDeUmaTagDeSaida() {
        Tag tag = new Tag("</end-tag>");
        assertEquals("end-tag", tag.getNome());
    }
    
    @Test
    public void deveriaTerONomeDeUmaTagVazia() {
        Tag tag = new Tag("<empty-tag/>");
        assertEquals("empty-tag", tag.getNome());
    }
    
    @Test
    public void deveriaSerUmaTagVaziaSeNaoTiverNenhumAtributo() {
        Tag tag = new Tag("<empty-tag/>");
        assertTrue(tag.isVazia());
    }
    
    //TODO @Test
    public void naoDeveriaSerUmaTagVaziaSeTiverAlgumAtributo() {
        Tag tag = new Tag("<tag id='1'>");
        assertFalse(tag.isVazia());
    }
    
    //TODO @Test
    public void naoDeveriaSerUmaTagVaziaSeTiverAlgumConteudo() {
        Tag tag = new Tag("<tag>Conteudo</tag>");
        assertFalse(tag.isVazia());
    }
}
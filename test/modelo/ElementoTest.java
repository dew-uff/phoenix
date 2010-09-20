package modelo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ElementoTest {

    public ElementoTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void deveriaTerUmaTagVazia() {
        Elemento elemento = new Elemento("<tag-vazia>");
        elemento.getTag();
    }

}
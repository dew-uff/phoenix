
import controlador.Diffs;
import java.util.Scanner;
import org.junit.Test;
import static org.testng.Assert.*;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author campello
 */

public class TesteDiffsTextual {

    String linha1 = "ceu e azul";
    String linha2= "ceu e roxo";



    @Test
    public void testeSimilaridadeStringsIgualsRetornandoSeuPercentual(){
        Diffs diffs =  new Diffs();
        assertEquals(diffs.diffTextual3(linha1,linha1),100);
    }
    @Test
    public void testeSimilaridadeStringsDifRetornandoSeuPercentual(){
        Diffs diffs =  new Diffs();
        assertEquals(diffs.diffTextual3(linha1,linha2),50);
    }
   
}

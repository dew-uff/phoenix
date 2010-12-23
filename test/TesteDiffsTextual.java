
import gems.ic.uff.br.controlador.Diffs;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author campello
 */
public class TesteDiffsTextual {

    String linha1 = "ceu e azul";
    String linha2 = "ceu e roxo";

    @Test
    public void testeSimilaridadeStringsIgualsRetornandoSeuPercentual() {
        assertEquals(Diffs.diffTextual(linha1, linha1), 100);
    }

    @Test
    public void testeSimilaridadeStringsDifRetornandoSeuPercentual() {
        assertEquals(Diffs.diffTextual(linha1, linha2), 50);
    }
}

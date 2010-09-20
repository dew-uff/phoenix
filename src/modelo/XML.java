package modelo;

import java.util.ArrayList;
import java.util.List;

public class XML {
    
    private List<Elemento> tags;

    /**
     * Cria um XML a partir de um texto.
     * @param texto
     */
    public XML(String texto) {
        tags = new ArrayList<Elemento>();
    }
}

package gems.ic.uff.br.modelo.similar;

import gems.ic.uff.br.modelo.Diff;

/**
 * Classe que encapsula a classe String, adicionando um comportamento
 * de similaridade a ela.
 */
public class SimilarString extends Similar<SimilarString> {

    private String string;

    public SimilarString(String string) {
        this.string = string;
    }

    @Override
    public Diff similar(SimilarString otherString) {
        return new Diff(this.string.equals(otherString.getString())? 1 : 0);
    }

    public String getString() {
        return this.string;
    }
    
    @Override
    public String toString() {
        return string.toString();
    }
}

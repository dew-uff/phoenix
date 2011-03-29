package gems.ic.uff.br.modelo.similar;

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
    public float similar(SimilarString otherString) {
        return this.string.equals(otherString.getString())? 1 : 0;
    }

    public String getString() {
        return this.string;
    }
    
    @Override
    public String toString() {
        return string.toString();
    }
}

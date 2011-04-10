package gems.ic.uff.br.modelo.similar;

import gems.ic.uff.br.modelo.Result;

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
    public Result similar(SimilarString otherString) {
        return new Result(this.string.equals(otherString.getString())? 1 : 0);
    }

    public String getString() {
        return this.string;
    }
    
    @Override
    public String toString() {
        return string.toString();
    }
}

package gems.ic.uff.br.modelo.similar;

import gems.ic.uff.br.modelo.Result;

/**
 * Classe que encapsula a classe Character, adicionando um comportamento
 * de similaridade a ela.
 */
public class SimilarCharacter extends Similar<SimilarCharacter> {

    private Character character;

    public SimilarCharacter(Character character) {
        this.character = character;
    }

    @Override
    public Result similar(SimilarCharacter otherCharacter) {
        return new Result(this.character.equals(otherCharacter.getChar())? 1 : 0);
    }

    public Character getChar() {
        return this.character;
    }
    
    @Override
    public String toString() {
        return character.toString();
    }
}

package gems.ic.uff.br.modelo.similar;

import gems.ic.uff.br.modelo.Diff;

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
    public Diff similar(SimilarCharacter otherCharacter) {
        return new Diff(this.character.equals(otherCharacter.getChar())? 1 : 0);
    }

    public Character getChar() {
        return this.character;
    }
    
    @Override
    public String toString() {
        return character.toString();
    }
}

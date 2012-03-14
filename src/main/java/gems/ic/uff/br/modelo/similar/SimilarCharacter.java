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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SimilarCharacter other = (SimilarCharacter) obj;
        if (this.character != other.character && (this.character == null || !this.character.equals(other.character))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + (this.character != null ? this.character.hashCode() : 0);
        return hash;
    }
}

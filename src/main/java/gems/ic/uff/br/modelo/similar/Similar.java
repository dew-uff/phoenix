package gems.ic.uff.br.modelo.similar;

import gems.ic.uff.br.modelo.Diff;

/**
 * Classe criada para usar a similaridade ao invéz da igualdade nos algoritmos
 * de comparação.
 * @param <T> classe que irá extender Similar
 */
public abstract class Similar<T extends Similar> {

    /**
     * Dado dois objetos calcula a similaridade entre eles.
     * @param y objeto a ser comparado
     * @return similaridade entre os objetos
     */
    public abstract Diff similar(T y);
}

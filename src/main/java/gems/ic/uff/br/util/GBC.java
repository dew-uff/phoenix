/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gems.ic.uff.br.util;

import java.awt.GridBagConstraints;
import java.awt.Insets;

/**
 *
 * @author campello
 */
public class GBC extends GridBagConstraints {

    public GridBagConstraints adicionarComponente(int coluna, int linha,
            int colunasOcupadas, int linhasOcupadas) {

        this.gridx = coluna;
        this.gridy = linha;
        this.gridheight = linhasOcupadas;
        this.gridwidth = colunasOcupadas;
        return this;
    }

    public GridBagConstraints adicionarComponenteComIsents(int coluna, int linha,
            int colunasOcupadas, int linhasOcupadas, Insets insets) {

        this.fill = GridBagConstraints.BOTH;
        this.gridx = coluna;
        this.gridy = linha;
        this.gridheight = linhasOcupadas;
        this.gridwidth = colunasOcupadas;
        this.insets = insets;
        return this;
    }
//    /**
//     * Construtor padrï¿½o
//     *
//     * @param gridx
//     *            x da grade
//     * @param gridy
//     *            y da grade
//     */
//    public GBC(int gridx, int gridy) {
//        this.gridx = gridx;
//        this.gridy = gridy;
//        this.insets = new Insets(2, 2, 2, 2);
//    }
//
//    /**
//     * Expansï¿½o horizontal
//     *
//     * @return
//     */
//    public GBC horizontal() {
//        this.weightx = 1;
//        this.fill = GridBagConstraints.HORIZONTAL;
//        return this;
//    }
//
//    /**
//     * Expansï¿½o vertical
//     *
//     * @return
//     */
//    public GBC vertical() {
//        this.weighty = 1;
//        this.fill = GridBagConstraints.VERTICAL;
//        return this;
//    }
//
//    /**
//     * Espansï¿½o em ambos os lados
//     *
//     * @return
//     */
//    public GBC both() {
//        this.weightx = 1;
//        this.weighty = 1;
//        this.fill = GridBagConstraints.BOTH;
//        return this;
//    }
//
//    /**
//     * Merge de linhas e colunas
//     *
//     * @param w
//     * @param h
//     * @return
//     */
//    public GBC gridwh(int w, int h) {
//        this.gridwidth = w;
//        this.gridheight = h;
//        return this;
//    }
//
//    /**
//     * Alinha para direita
//     *
//     * @return
//     */
//    public GBC right() {
//        this.anchor = GridBagConstraints.EAST;
//        return this;
//    }
//
//    public GBC insets(int value) {
//        this.insets = new Insets(value, value, value, value);
//        return this;
//    }
//
//    public GBC insetsHorizontal(int value) {
//        this.insets = new Insets(0, value, 0, value);
//        return this;
//    }
//
//    public GBC insetsVertical(int value) {
//        this.insets = new Insets(value, 0, value, 0);
//        return this;
//    }
}

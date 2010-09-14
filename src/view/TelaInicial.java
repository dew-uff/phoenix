/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 *
 * @author campello
 */
public class TelaInicial extends JFrame {

    private JTextArea area1;
    private JTextArea area2;
    private JTextArea resultadoComparacao;
    private JButton btnDiff;
    private final int TAMANHO_HORIZONTAL_AREA = 30;
    private final int TAMANHO_VERTICAL_AREA = 30;

    public TelaInicial() {
        inicializarVariaveis();
        this.setVisible(true);
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void inicializarVariaveis() {
        JPanel panelNorte = new JPanel(new FlowLayout());
        area1 = new JTextArea();
        area2 = new JTextArea();
        area1.setColumns(TAMANHO_HORIZONTAL_AREA);
        area1.setRows(TAMANHO_VERTICAL_AREA);
        area2.setColumns(TAMANHO_HORIZONTAL_AREA);
        area2.setRows(TAMANHO_VERTICAL_AREA);
        btnDiff = new JButton("Comparar");
        panelNorte.add(area1);
        panelNorte.add(btnDiff);
        panelNorte.add(area2);
        this.add(panelNorte, BorderLayout.NORTH);
    }
}

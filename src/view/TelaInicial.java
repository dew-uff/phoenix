/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.border.Border;

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
    private final int TAMANHO_VERTICAL_AREA = 20;
    private String textoTeste = "<? xml version=“1.0” ?>\n" +
            "<empregados>\n" +
            "   <empregado cod=“E01” dept=“D01”>\n " +
            "       <nome>João</nome>\n" +
            "       <inicial-meio>S.</inicial-meio>\n" +
            "       <sobrenome>Santos</sobrenome>\n" +
            "   </empregado>\n" +
            "   <empregado cod=“E02” dept=“D01”>\n" +
            "       <nome>Ana</nome>\n" +
            "       <sobrenome>Ferraz</sobrenome>\n" +
            "   </empregado>\n" +
            "</empregados>\n";

    public TelaInicial() {
        inicializarVariaveis();
        this.setVisible(true);
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void inicializarVariaveis() {
        JPanel panelNorte = new JPanel(new FlowLayout());
        JPanel panelSul = new JPanel(new FlowLayout());
        area1 = new JTextArea(textoTeste);
        area2 = new JTextArea(textoTeste);
        area1.setColumns(TAMANHO_HORIZONTAL_AREA);
        area1.setRows(TAMANHO_VERTICAL_AREA);
        area2.setColumns(TAMANHO_HORIZONTAL_AREA);
        area2.setRows(TAMANHO_VERTICAL_AREA);
        btnDiff = new JButton("Comparar");
        panelNorte.add(area1);
        panelNorte.add(btnDiff);
        panelNorte.add(area2);
        this.add(panelNorte, BorderLayout.NORTH);
        JProgressBar percentual = new JProgressBar();
        percentual.setStringPainted(true);
        Border borda = BorderFactory.createTitledBorder("Similaridade");
        percentual.setBorder(borda);
        panelSul.add(percentual);
        JButton btnLog = new JButton("Visualizar log");
        panelSul.add(btnLog);
        this.add(panelSul,BorderLayout.SOUTH);

    }
}

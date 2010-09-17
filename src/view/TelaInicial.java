/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import util.GBC;

/**
 *
 * @author campello
 */
public class TelaInicial extends JFrame {

    private GBC gbc;
    private JTextArea area1;
    private JTextArea area2;
    private JTextArea resultadoComparacao;
    private JPanel pnlCentral;
    private JPanel pnlSul;
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
        this.pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private void inicializarVariaveis() {

        gbc = new GBC();
        pnlCentral = new JPanel(new GridBagLayout());
        area1 = new JTextArea(TAMANHO_HORIZONTAL_AREA, TAMANHO_VERTICAL_AREA);
        area1.setText(textoTeste);
        JScrollPane scroll = new JScrollPane(area1);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        pnlCentral.add(new JLabel("Texto 1"), gbc.adicionarComponente(0, 0, 1, 1));
        gbc.fill = GBC.BOTH;
        pnlCentral.add(scroll, gbc.adicionarComponente(0, 1, 1, 1));

        area2 = new JTextArea(TAMANHO_HORIZONTAL_AREA, TAMANHO_VERTICAL_AREA);
        area2.setText(textoTeste);
        pnlCentral.add(new JLabel("Texto 2", JLabel.CENTER), gbc.adicionarComponente(1, 0, 1, 1));
        JScrollPane scroll2 = new JScrollPane(area2);
        scroll2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        pnlCentral.add(scroll2, gbc.adicionarComponenteComIsents(1, 1, 0, 0, new Insets(0, 15, 45, 0)));

        btnDiff = new JButton("Comparar");
        btnDiff.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                System.out.println("ACAOOOOOOOOOOOOOOO ENTRA AQUI");
            }
        });
        pnlSul = new JPanel();
        pnlSul.add(btnDiff);
        pnlSul.setAlignmentY(JComponent.CENTER_ALIGNMENT);

        pnlCentral.add(pnlSul,gbc.adicionarComponenteComIsents(0, 2, 1, 10,new Insets(10, 10, 0, 0)));
//        pnlSul.add(btnDiff);


        this.add(pnlCentral, BorderLayout.CENTER);
//        this.add(pnlSul, BorderLayout.SOUTH);
//        JPanel panelNorte = new JPanel(new FlowLayout());
//        JPanel panelSul = new JPanel(new FlowLayout());
//        area1 = new JTextArea(textoTeste);
//        area2 = new JTextArea(textoTeste);
//        area1.setColumns(TAMANHO_HORIZONTAL_AREA);
//        area1.setRows(TAMANHO_VERTICAL_AREA);
//        area2.setColumns(TAMANHO_HORIZONTAL_AREA);
//        area2.setRows(TAMANHO_VERTICAL_AREA);
//        btnDiff = new JButton("Comparar");
//        panelNorte.add(area1);
//        panelNorte.add(btnDiff);
//        panelNorte.add(area2);
//        this.add(panelNorte, BorderLayout.NORTH);
        JProgressBar percentual = new JProgressBar();
        percentual.setValue(78);
        percentual.setStringPainted(true);
        Border borda = BorderFactory.createTitledBorder("Similaridade");
        percentual.setBorder(borda);
        JPanel panelSul = new JPanel(new GridBagLayout());
        panelSul.add(percentual,gbc.adicionarComponenteComIsents(0, 0, 1, 1,new Insets(0, 0, 20, 0)));
        JButton btnLog = new JButton("Visualizar log");
        panelSul.add(btnLog,gbc.adicionarComponenteComIsents(1, 0, 1, 1, new Insets(0, 10, 20, 0)));
        this.add(panelSul,BorderLayout.SOUTH);
//        this.add(panelSul,BorderLayout.SOUTH);

    }

    public static void main(String[] args) {
        TelaInicial t = new TelaInicial();
    }
}

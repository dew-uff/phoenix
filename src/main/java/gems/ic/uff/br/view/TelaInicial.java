package gems.ic.uff.br.view;

import java.awt.BorderLayout;
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
import javax.swing.border.TitledBorder;
import gems.ic.uff.br.modelo.LcsXML;
import gems.ic.uff.br.modelo.XML;
import gems.ic.uff.br.util.GBC;
import java.awt.*;
import javax.swing.border.BevelBorder;

/**
 *
 * @author campello
 */
public class TelaInicial extends JFrame {

    private GBC gbc;
    private JTextArea txtAreaComparador;
    private JTextArea txtAreaASerComparado;
    private JPanel pnlPrincipal;
    private JPanel pnlComparacao;
    private JButton btnComparacao;
    private PnlPonderacoes pnlPonderacoes;
    private final int TAMANHO_HORIZONTAL_AREA = 20;
    private final int TAMANHO_VERTICAL_AREA = 20;
    private JProgressBar percentual;
    private XML xml;
    private XML xml2;
    private String filePath = "books.xml";

    public TelaInicial() {
        try {
            xml = new XML("<usuario><nome>João Silva</nome><cidade>São José de Ribamar</cidade><estado>Maranhão</estado></usuario>");
            xml2 = new XML("<usuario><nome>Maria da Silva</nome><cidade>São Luiz</cidade><estado>Maranhão</estado><pais>Brasil</pais></usuario>");
        } catch (Exception ex) {
//            System.out.println(ex.getMessage());
        }
        inicializarAreaDeTextos();
        inicializarAreaDeComparacao();

        this.pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    /**
     * Inicializa o painel principal com os seus subcomponentes.
     */
    private void inicializarAreaDeTextos() {
        pnlPrincipal = new JPanel(new GridBagLayout());
        gbc = new GBC();

        inicializarAreaDeTextoDoArquivoComparador();
        inicializarAreaDeTextoDoArquivoASerComparado();

        this.add(pnlPrincipal, BorderLayout.CENTER);
    }

    /**
     * Inicializa a área de exibição do texto do arquivo comparador.
     */
    private void inicializarAreaDeTextoDoArquivoComparador() {
        //TextArea
        txtAreaComparador = new JTextArea(TAMANHO_HORIZONTAL_AREA, TAMANHO_VERTICAL_AREA);

        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        pnlPrincipal.add(new JLabel("Documento 1", JLabel.CENTER), gbc);

        //Barra de Scroll
        JScrollPane scroll = new JScrollPane(txtAreaComparador);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        gbc.gridx = 0;
        gbc.gridy = 1;
        pnlPrincipal.add(scroll, gbc);
    }

    /**
     * Inicializa a área de exibição do texto do arquivo a ser comparado.
     */
    private void inicializarAreaDeTextoDoArquivoASerComparado() {
        //TextArea
        txtAreaASerComparado = new JTextArea(TAMANHO_HORIZONTAL_AREA, TAMANHO_VERTICAL_AREA);
        txtAreaASerComparado.setText(xml2.toString());

        gbc.gridx = 1;
        gbc.gridy = 0;
        Insets insetsConparado = new Insets(0, 10, 5, 0);
        gbc.insets = insetsConparado;
        pnlPrincipal.add(new JLabel("Documento 2", JLabel.CENTER), gbc);
        //Barra de Scroll
        JScrollPane scroll = new JScrollPane(txtAreaASerComparado);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridheight = 3;
        gbc.gridwidth = 3;

        pnlPrincipal.add(scroll, gbc);
    }

    /**
     * Inicialize a área de ação/relatório da comparação.
     */
    private void inicializarAreaDeComparacao() {
        pnlComparacao = new JPanel();

        inicializarBotaoDeComparacao();
        inicializarBarraDePercentualDeComparacao();
    }

    /**
     * Inicializa o botão de comparação dos arquivos a serem comparados.
     */
    private void inicializarBotaoDeComparacao() {
        btnComparacao = new JButton("Comparar");
        btnComparacao.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                xml = new XML(txtAreaComparador.getText());
                xml2 = new XML(txtAreaASerComparado.getText());

                LcsXML lcsXML = new LcsXML(xml, xml2);

                JFrame telaDiff = new JFrame("Diff Resultante");

                String testeDiffResultante = lcsXML.getDiffXML().toString();
                
                visualizarXMLsGrafos();
                
                VisualizarDiffXML diffGrafico = new VisualizarDiffXML(lcsXML.getDiffXML().toString());
                diffGrafico.setBorder(new BevelBorder(BevelBorder.LOWERED));
                JPanel pnlGraficoDiff = new JPanel(new GridBagLayout());
                pnlGraficoDiff.add(diffGrafico, gbc.adicionarComponente(0, 0, 1, 1));
                telaDiff.getContentPane().add(pnlGraficoDiff);
                telaDiff.setLocationRelativeTo(null);
                telaDiff.setVisible(true);
                telaDiff.pack();
            }
        });

        pnlComparacao.add(btnComparacao);
        pnlComparacao.setAlignmentY(JComponent.CENTER_ALIGNMENT);
//        JButton btnVisualizarGraficos = new JButton("Visualizar Grafo");
//        btnVisualizarGraficos.addActionListener(new ActionListener() {
//
//            public void actionPerformed(ActionEvent e) {
//                visualizarXMLsGrafos();
//            }
//        });
        
//        pnlComparacao.add(btnVisualizarGraficos);
        gbc.gridx = 0;
        gbc.gridy = 4;
        pnlPrincipal.add(pnlComparacao, gbc);
//        pnlPrincipal.add(this)
    }
    
    private void visualizarXMLsGrafos() throws HeadlessException {
                JFrame visualizar = new JFrame("Grafico");
                VisualizarXML v = new VisualizarXML(txtAreaComparador.getText(), Color.RED);
                VisualizarXML v2 = new VisualizarXML(txtAreaASerComparado.getText(), Color.GREEN);
                v.setBorder(new BevelBorder(BevelBorder.LOWERED));
                v2.setBorder(new BevelBorder(BevelBorder.LOWERED));
                JPanel pnlGraficos = new JPanel(new GridBagLayout());
                gbc = new GBC();
                JLabel txtTexto1 = new JLabel("Texto 1");
                JLabel txtTexto2 = new JLabel("Texto 2");
                gbc.weightx=1;
                gbc.weighty=1;
                pnlGraficos.add(txtTexto1, gbc.adicionarComponente(0, 0, 1, 1));
                pnlGraficos.add(txtTexto2, gbc.adicionarComponente(1, 0, 1, 1));
                pnlGraficos.add(v, gbc.adicionarComponente(0, 1, 1, 1));
                pnlGraficos.add(v2, gbc.adicionarComponente(1, 1, 1, 1));
                visualizar.getContentPane().add(pnlGraficos);
                visualizar.setLocationRelativeTo(null);
                visualizar.setVisible(true);
                visualizar.pack();
            }

    /**
     * Inicializa a barra de percentual de comparação dos arquivos.
     */
    private void inicializarBarraDePercentualDeComparacao() {
        //Barra de percentual
        percentual = new JProgressBar();
        percentual.setStringPainted(true);

        //Borda
        Border borda = BorderFactory.createTitledBorder(null, "Similaridade", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION);
        percentual.setBorder(borda);
    }

    public static void main(String[] args) {
        TelaInicial t = new TelaInicial();
    }
}

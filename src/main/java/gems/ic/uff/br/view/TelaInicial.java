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
import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import javax.swing.border.BevelBorder;

/**
 *
 * @author campello
 */
public class TelaInicial extends JFrame {

    public static final String DIFF_XML_TESTE = "<xml><a><left_a>texto 1</left_a><right_a>texto 2</right_a></a><c>texto 3</c><d><left_e>texto 5</left_e><right_e>texto 7</right_e><left_f>texto 6</left_f><right_f>texto 8</right_f></d></xml>";
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
            System.out.println(ex.getMessage());
        }
        inicializarAreaDeTextos();
        inicializarAreaDeComparacao();
        visualizarPercentualSimilaridade();
//        inicializarAreaPesos();

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

    private void inicializarAreaPesos() {
        pnlComparacao = new PnlPonderacoes();
        this.add(pnlComparacao, BorderLayout.WEST);
    }

    /**
     * Inicializa a área de exibição do texto do arquivo comparador.
     */
    private void inicializarAreaDeTextoDoArquivoComparador() {
        //TextArea
        txtAreaComparador = new JTextArea(TAMANHO_HORIZONTAL_AREA, TAMANHO_VERTICAL_AREA);
//        txtAreaComparador.setText(xml.toString());

        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        pnlPrincipal.add(new JLabel("Texto Comparador"), gbc);

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
        pnlPrincipal.add(new JLabel("Texto A Ser Comparado"), gbc);
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
//                System.out.println(lcsXML.getDiffXML());
//                percentual.setValue((int) (lcsXML.similaridade() * 100));
                System.out.println("********************");
                System.out.println("********************");
                System.out.println("RESULTADOOOO TOTALLLLL => " + lcsXML.similaridade());
                System.out.println("********************");
                System.out.println("********************");

                JFrame telaDiff = new JFrame("Diff Resultante");
                
//                lcsXML.getDiffXML().removeWhiteSpaces(lcsXML.getDiffXML().getDocument());
                String testeDiffResultante = lcsXML.getDiffXML().toString();
                System.out.println(" ***  IMPRIME XML RESULTANTE ***");
                System.out.println("" + testeDiffResultante);
                System.out.println(" *** FIM *** ");
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
        JButton btnVisualizarGraficos = new JButton("Visualizar Grafo");
        btnVisualizarGraficos.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JFrame visualizar = new JFrame("Grafico");
                VisualizarXML v = new VisualizarXML(txtAreaComparador.getText(), Color.ORANGE);
                VisualizarXML v2 = new VisualizarXML(txtAreaComparador.getText(), Color.WHITE);
                v.setBorder(new BevelBorder(BevelBorder.LOWERED));
                v2.setBorder(new BevelBorder(BevelBorder.LOWERED));
                JPanel pnlGraficos = new JPanel(new GridBagLayout());
                gbc = new GBC();
                JLabel txtTexto1 = new JLabel("Texto 1");
                JLabel txtTexto2 = new JLabel("Texto 2");
                pnlGraficos.add(txtTexto1, gbc.adicionarComponente(0, 0, 1, 1));
                pnlGraficos.add(txtTexto2, gbc.adicionarComponente(1, 0, 1, 1));
                pnlGraficos.add(v, gbc.adicionarComponente(0, 1, 1, 1));
                pnlGraficos.add(v2, gbc.adicionarComponente(1, 1, 1, 1));
                visualizar.getContentPane().add(pnlGraficos);
                visualizar.setLocationRelativeTo(null);
                visualizar.setVisible(true);
                visualizar.pack();

                JFrame telaDiff = new JFrame("Diff Resultante");
                VisualizarDiffXML diffGrafico = new VisualizarDiffXML(DIFF_XML_TESTE);
                diffGrafico.setBorder(new BevelBorder(BevelBorder.LOWERED));
                JPanel pnlGraficoDiff = new JPanel(new GridBagLayout());
                pnlGraficoDiff.add(diffGrafico, gbc.adicionarComponente(0, 0, 1, 1));
                telaDiff.getContentPane().add(pnlGraficoDiff);
                telaDiff.setLocationRelativeTo(null);
                telaDiff.setVisible(true);
                telaDiff.pack();
            }
        });
        pnlComparacao.add(btnVisualizarGraficos);
        gbc.gridx = 0;
        gbc.gridy = 4;
        pnlPrincipal.add(pnlComparacao, gbc);
//        pnlPrincipal.add(this)
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

    private void visualizarPercentualSimilaridade() {
        gbc.fill = GBC.BOTH;

        //TODO remover o panelSul e adicionar os seus componentes ao painel de comparação.
//        JPanel panelSul = new JPanel(new GridBagLayout());
//        JPanel pnlEsquerdo = new JPanel(new GridLayout(3, 1));
//        pnlEsquerdo.add(new JButton("Comparar"));
//        pnlEsquerdo.add(new JButton("Visualizar"));
//        pnlEsquerdo.add(new JButton("Log"));
//
//        panelSul.add(percentual, gbc.adicionarComponenteComIsents(0, 0, 1, 1, new Insets(0, 0, 20, 0)));
//        this.add(pnlEsquerdo, BorderLayout.WEST);
    }

    public static void main(String[] args) {
        TelaInicial t = new TelaInicial();
    }
}

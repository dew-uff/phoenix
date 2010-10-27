package view;

import controlador.Diffs;
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
import lcs.LcsString;
import util.XML;
import util.GBC;

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
    private final int TAMANHO_HORIZONTAL_AREA = 30;
    private final int TAMANHO_VERTICAL_AREA = 20;
    private JProgressBar percentual;
    private XML xml;
    private XML xml2;
    private String filePath = "books.xml";

    public TelaInicial() {
        try {
            xml = new XML(filePath);
            xml2 = new XML("<inventory>Teste</inventory>");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        
        inicializarAreaDeTextos();
        inicializarAreaDeComparacao();
        inicializarVariaveis();

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
        txtAreaComparador.setText(xml.toString());
        txtAreaComparador.setEditable(false);

        pnlPrincipal.add(new JLabel("Texto Comparador"), gbc.adicionarComponente(0, 0, 1, 1));

        //Barra de Scroll
        JScrollPane scroll = new JScrollPane(txtAreaComparador);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        pnlPrincipal.add(scroll, gbc.adicionarComponente(0, 1, 1, 1));
    }

    /**
     * Inicializa a área de exibição do texto do arquivo a ser comparado.
     */
    private void inicializarAreaDeTextoDoArquivoASerComparado() {
        //TextArea
        txtAreaASerComparado = new JTextArea(TAMANHO_HORIZONTAL_AREA, TAMANHO_VERTICAL_AREA);
        txtAreaASerComparado.setText(xml2.toString());

        pnlPrincipal.add(new JLabel("Texto A Ser Comparado"), gbc.adicionarComponente(1, 0, 1, 1));

        //Barra de Scroll
        JScrollPane scroll = new JScrollPane(txtAreaASerComparado);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        pnlPrincipal.add(scroll, gbc.adicionarComponenteComIsents(1, 1, 0, 0, new Insets(0, 15, 45, 0)));
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
                LcsString seq = new LcsString(txtAreaComparador.getText(), txtAreaASerComparado.getText());
                percentual.setValue(seq.getLcsLength());
            }
        });

        pnlComparacao.add(btnComparacao);
        pnlComparacao.setAlignmentY(JComponent.CENTER_ALIGNMENT);

        pnlPrincipal.add(pnlComparacao, gbc.adicionarComponenteComIsents(0, 2, 1, 10, new Insets(10, 10, 0, 0)));
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

    private void inicializarVariaveis() {
        gbc.fill = GBC.BOTH;

        //TODO remover o panelSul e adicionar os seus componentes ao painel de comparação.
        JPanel panelSul = new JPanel(new GridBagLayout());
        panelSul.add(percentual, gbc.adicionarComponenteComIsents(0, 0, 1, 1, new Insets(0, 0, 20, 0)));
        JButton btnLog = new JButton("Visualizar log");
        panelSul.add(btnLog, gbc.adicionarComponenteComIsents(1, 0, 1, 1, new Insets(0, 10, 20, 0)));
        this.add(panelSul, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        TelaInicial t = new TelaInicial();
    }
}

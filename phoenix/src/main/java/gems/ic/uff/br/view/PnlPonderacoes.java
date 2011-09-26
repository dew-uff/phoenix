/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gems.ic.uff.br.view;

import gems.ic.uff.br.util.GBC;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

/**
 *
 * @author campello
 */
public class PnlPonderacoes extends JPanel {

    private GBC gbc;
    private JLabel[] pesos;
    private JTextArea[] txtPesos;
    private final int COLUNA1 = 0;
    private final int COLUNA2 = 1;

    public PnlPonderacoes() {
        this.setLayout(new GridBagLayout());
        gbc = new GBC();
        pesos = new JLabel[4];
        txtPesos = new JTextArea[4];
        pesos[0] = new JLabel("Nome dos elementos");
        pesos[1] = new JLabel("Atributo dos elementos");
        pesos[2] = new JLabel("Conteudo dos elementos ");
        pesos[3] = new JLabel("Filho dos elementos");
        txtPesos[0] = new JTextArea("0.25");
        txtPesos[1] = new JTextArea("0.25");
        txtPesos[2] = new JTextArea("0.25");
        txtPesos[3] = new JTextArea("0.25");
        for (int i = 0; i < pesos.length; i++) {
            this.add(pesos[i], gbc.adicionarComponente(COLUNA1, i + 1, 1, 1));
            this.add(txtPesos[i], gbc.adicionarComponente(COLUNA2, i + 1, 1, 1));
        }
        this.setVisible(true);
        this.setBorder(new BevelBorder(BevelBorder.LOWERED));
    }
}

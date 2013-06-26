package gems.ic.uff.br.newView;


import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.BevelBorder;

/*
 * MyButton.java
 *
 * Meus botoes.
 *
 * @author douglas
 */

public class MyButton extends JButton{
  
    ImageIcon icon;
    MouseListener mouseListener;
    
    public MyButton(URL caminho){
              
        icon = new ImageIcon(caminho);
        //icon = new ImageIcon(getClass().getResource(caminho));
        setIcon(icon);
        setBorder(new BevelBorder(BevelBorder.RAISED));
        setBorderPainted(false);
        setBackground(new Color (getBackground().getRGB()));
        
        criaInteracao();
           
    }
    
    public MyButton(String caminho){
              
        icon = new ImageIcon(caminho);
        //icon = new ImageIcon(getClass().getResource(caminho));
        setIcon(icon);
        setBorder(new BevelBorder(BevelBorder.RAISED));
        setBorderPainted(false);
        setBackground(new Color (getBackground().getRGB()));
        
        criaInteracao();
           
    }
    
    private void criaInteracao(){
        
        mouseListener = new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                
            }

            @Override
            public void mousePressed(MouseEvent e) {
               
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                
            }

            @Override
            public void mouseEntered(MouseEvent e) {                               

               if(isEnabled()){
                   
                   setBorder(new BevelBorder(BevelBorder.RAISED));
                   setBorderPainted(true);
                   
               }
               
            }

            @Override
            public void mouseExited(MouseEvent e) {
              
                setBorderPainted(false);
                
            }
        };
        
        addMouseListener(mouseListener);
        
    }
    
}

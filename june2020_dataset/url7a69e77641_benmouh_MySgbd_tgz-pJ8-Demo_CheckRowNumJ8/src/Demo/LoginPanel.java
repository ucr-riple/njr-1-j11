package Demo;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JComponent;

public class LoginPanel extends JComponent{
	
	  /** variable de classe contenant l'image à afficher en fond */
    private Image bg;

    /** Surcharge de la fonction paintComponent() pour afficher notre image */
     public void paintComponent(Graphics g) {
            g.drawImage(bg,0,0,null);
    } 

}

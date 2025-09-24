package User_Interface;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * This class is used to create a custom JPanel that has an image for the background.
 * 
 * @author Alex Candler, 300257532, candlealex
 * 
 */
public class CustomPanel extends JPanel{
	private BufferedImage background;
	
	public CustomPanel(String filename){
		// Load Image
		try {
	        background = ImageIO.read(new File(filename+".png"));
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		setPreferredSize(new Dimension(background.getWidth(), background.getHeight()));
		setOpaque(false);
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		int x = (this.getWidth() - background.getWidth(null)) / 2;
		int y = (this.getHeight() - background.getHeight(null)) / 2;
	    g.drawImage(background, x, y, null);
	    
	}
}

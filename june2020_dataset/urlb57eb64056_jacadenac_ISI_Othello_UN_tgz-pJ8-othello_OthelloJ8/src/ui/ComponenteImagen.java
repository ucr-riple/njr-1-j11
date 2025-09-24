package ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;

import javax.swing.JComponent;

public class ComponenteImagen extends JComponent {
	private static final long serialVersionUID = 1L;
	Image imagen;
	Dimension size;
	
	public ComponenteImagen(Image imagen) {
		super();
		this.imagen = imagen;
		MediaTracker mt = new MediaTracker(this);
		mt.addImage(imagen, 0);
		try {
			mt.waitForAll();
		} catch (InterruptedException e) {
			
		}
		this.size = new Dimension(imagen.getWidth(null),imagen.getHeight(null));
		this.setSize(size);
	}
	
	@Override
	public void paint(Graphics g) {
		g.drawImage(imagen,  0, 0, this);
	}
	
	@Override
	public Dimension getPreferredSize(){
		return size;
	}
	
	
}

package User_Interface;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

/**
 * This class is used to create custom buttons that have rollover effects so
 * that they display different images when a user hovers or presses on them.
 * 
 * @author Alex Candler, 300257532, candlealex
 * 
 */
public class CustomButton extends JComponent implements MouseListener {
	private BufferedImage image;
	private BufferedImage image_pressed;
	private BufferedImage image_hover;
	private String action = "Normal";

	public CustomButton(String filename) {
		super();
		enableInputMethods(true);
		addMouseListener(this);

		// Load Image
		try {
			image = ImageIO.read(new File(filename + ".png"));
			image_pressed = ImageIO.read(new File(filename + "_Pressed.png"));
			image_hover = ImageIO.read(new File(filename + "_Hover.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(image.getWidth(), image.getHeight());
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (action.equals("Normal")) {
			g.drawImage(image, 0, 0, null);
		} else if (action.equalsIgnoreCase("Pressed")) {
			g.drawImage(image_pressed, 0, 0, null);
		} else if (action.equalsIgnoreCase("Hover")) {
			g.drawImage(image_hover, 0, 0, null);
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		action = "Hover";
		repaint();
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		action = "Normal";
		repaint();
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		action = "Pressed";
		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		action = "Hover";
		repaint();

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void setAction(String action) {
		this.action = action;
	}
}

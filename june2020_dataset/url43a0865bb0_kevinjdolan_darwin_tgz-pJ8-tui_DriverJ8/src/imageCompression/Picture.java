package imageCompression;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import javax.swing.*;
import darwin.population.Individual;
import darwin.population.Node;

public class Picture extends JComponent {

	private static final long serialVersionUID = 2241387376546336473L;
	private Individual individual;
	private BufferedImage image;
	private int width, height;
	
	public Picture(Individual individual, BufferedImage image) {
		this.individual = individual;
		this.image = image;
		
		width = image.getWidth();
		height = image.getHeight();
		setPreferredSize(new Dimension(3 * width, height));
	}
	
	public void paint(Graphics gr) {		
		Image generated = createImage(width, height);
		Image difference = createImage(width, height);
		Graphics g1 = generated.getGraphics();
		Graphics g2 = difference.getGraphics();
		
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				
				int c = image.getRGB(x,y);
				int  red = (c & 0x00ff0000) >> 16;
				int  green = (c & 0x0000ff00) >> 8;
				int  blue = c & 0x000000ff;

				double r = red / 255.;
				double g = green / 255.;
				double b = blue / 255.;
				
				double xProp = ((double) x) / width;
				double yProp = ((double) y) / height;
				
				Point2D.Double point = new Point2D.Double(xProp, yProp);
				
				Node redNode = individual.getTree(0);
				Node greenNode = individual.getTree(1);
				Node blueNode = individual.getTree(2);
				
				double redValue = (Double) redNode.getValue(point);
				double greenValue = (Double) greenNode.getValue(point);
				double blueValue = (Double) blueNode.getValue(point);
				redValue -= Math.floor(redValue);
				greenValue -= Math.floor(greenValue);
				blueValue -= Math.floor(blueValue);
				
				int genRed = (int) (255 * redValue);
				int genGreen = (int) (255 * greenValue);
				int genBlue = (int) (255 * blueValue);
				
				double redDiff = redValue - r;
				double greenDiff = greenValue - g;
				double blueDiff = blueValue - b;
				
				double diff = redDiff * redDiff + greenDiff * greenDiff + blueDiff * blueDiff;
				int diffEq = (int) (255 * diff / 3);
				
				g1.setColor(new Color(genRed, genGreen, genBlue));
				g1.drawRect(x, y, 1, 1);
				g2.setColor(new Color(diffEq, diffEq, diffEq));
				g2.drawRect(x, y, 1, 1);
			}
		}
		
		gr.drawImage(generated, 0, 0, null);
		gr.drawImage(image, width, 0, null);
		gr.drawImage(difference, 2 * width, 0, null);
		
	}
	
}

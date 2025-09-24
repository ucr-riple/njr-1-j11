package glass.drawing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import javax.swing.JComponent;

public class Picture extends JComponent {

	private static final long serialVersionUID = 1942640313255833527L;
	private ShapeSet shapes;
	
	public Picture(ShapeSet shapes) {
		this.shapes = shapes;
	}
	
	public void paint(Graphics g) {
		int width = getWidth();
		int height = getHeight();
		
		for(double x = 0; x < width; x++) {
			for(double y = 0; y < height; y++) {
				Point2D.Double p = new Point2D.Double(x / (width - 1), y / (height - 1));
				Color color = shapes.getPointColor(p);
				g.setColor(color);
				g.drawRect((int)x, (int)y, 1, 1);
			}
		}
	}
	
}

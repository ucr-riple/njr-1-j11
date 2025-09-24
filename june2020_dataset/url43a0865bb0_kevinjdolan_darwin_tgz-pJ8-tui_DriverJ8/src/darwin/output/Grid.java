package darwin.output;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.Graphics2D;
import javax.swing.JComponent;

public abstract class Grid extends JComponent {

	private static final long serialVersionUID = 2244499003330728208L;
	
	public static final int TOP_LEFT = 0;
	public static final int TOP_RIGHT = 1;
	public static final int BOTTOM_RIGHT = 2;
	public static final int BOTTOM_LEFT = 3;
	
	//Set values
	protected int anchor, horizontalLines, verticalLines;
	protected double heightProportion, widthProportion;
	protected Color backgroundColor, gridColor;
	
	//Calculated values
	protected int width = getWidth();
	protected int height = getHeight();
	protected int left, right, top, bottom;
	protected int xSpace, ySpace;
	protected double minX, maxX, minY, maxY;
	protected double xRange, yRange;
	
	public Grid() {
		anchor = TOP_RIGHT;
		heightProportion = 0.9;
		widthProportion = 0.9;
		backgroundColor = new Color(255,255,255);
		gridColor = new Color(0,0,0);
		horizontalLines = 10;
		verticalLines = 10;
	}
	
	/**
	 * Set the anchor value
	 * @param anchor default Grid.TOP_RIGHT
	 */
	public void setAnchor(int anchor) {
		this.anchor = anchor;
	}

	/**
	 * Set the number of horizontal lines
	 * @param horizontalLines default 10
	 */
	public void setHorizontalLines(int horizontalLines) {
		this.horizontalLines = horizontalLines;
	}
	
	/**
	 * Set the number of vertical lines
	 * @param verticalLines default 10
	 */
	public void setVerticalLines(int verticalLines) {
		this.verticalLines = verticalLines;
	}

	/**
	 * Set the proportion of the screen taken up by the graph, vertically
	 * @param heightProportion default 4/5
	 */
	public void setHeightProportion(double heightProportion) {
		this.heightProportion = heightProportion;
	}

	/**
	 * Set the proportion of the screen taken up by the graph, horizontally
	 * @param widthProportion default 4/5
	 */
	public void setWidthProportion(double widthProportion) {
		this.widthProportion = widthProportion;
	}

	/**
	 * Set the background color
	 * @param backgroundColor default white
	 */
	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	/**
	 * Set the grid color
	 * @param gridColor default black
	 */
	public void setGridColor(Color gridColor) {
		this.gridColor = gridColor;
	}
	
	/**
	 * Calculate all the calculated values
	 */
	protected void calculateValues() {
		width = getWidth();
		height = getHeight();
		
		if(anchor == TOP_LEFT || anchor == BOTTOM_LEFT) {
			left = 0;
			right = (int) (widthProportion * width);
		}
		else {
			left = (int) ((1 - widthProportion) * width);
			right = width - 1;
		}
		xSpace = right - left;
		
		if(anchor == TOP_LEFT || anchor == TOP_RIGHT) {
			top = 0;
			bottom = (int) (heightProportion * height);
		}
		else {
			top = (int) ((1 - heightProportion) * height);
			bottom = height - 1;
		}
		
		ySpace = bottom - top;
	}
	
	/**
	 * Transform an x world value to screen value
	 * @param x the real-world x-value
	 * @return  the screen version
	 */
	public int getXPixel(double x) {
		if(xRange == 0)
			return width / 2;
		double proportion = (x - minX) / xRange;
		return (int) interpolate(left, right, proportion);
	}
	
	/**
	 * Transform an y world value to screen value
	 * @param y the real-world y-value
	 * @return  the screen version
	 */
	public int getYPixel(double y) {
		if(yRange == 0)
			return height / 2;
		double proportion = (y - minY) / yRange;
		return (int) interpolate((bottom-1), top, proportion);
	}
	
	/**
	 * Transform an x screen value to world value
	 * @param x the screen x value
	 * @return  the real-world version
	 */
	public double getXCoord(int x) {
		if(xRange == 0)
			return 0;
		double proportion = ((double) x - left) / xSpace;
		return interpolate(minX, maxX, proportion);
	}
	
	/**
	 * Transform an y screen value to world value
	 * @param y the screen y value
	 * @return  the real-world version
	 */
	public double getYCoord(int y) {
		if(yRange == 0)
			return 0;
		double proportion = ((double) bottom - y) / ySpace;
		return interpolate(minY, maxY, proportion);
	}
	
	/**
	 * Interpolate between two points
	 * @param v1 		 the minimum point
	 * @param v2 		 the maximum point
	 * @param proportion the proportion to interpolate
	 * @return			 the interpolated value
	 */
	protected double interpolate(double v1, double v2, double proportion) {
		double range = v2 - v1;
		return proportion * range + v1;
	}
	
	@Override
	public void paint(Graphics gb) {	
		calculateValues();
		
		Graphics2D g = (Graphics2D)gb;
		
		//Paint the background
		g.setColor(backgroundColor);
		g.fillRect(0, 0, width, height);
		
		//Draw the grid
		g.setColor(gridColor);
		
		//Draw the x labels
		int captionHeight = (int) ((1 - heightProportion) * height - 10);
		int where;
		if(anchor == BOTTOM_RIGHT || anchor == BOTTOM_LEFT)
			where = 5;
		else
			where = height - captionHeight - 5;
		for(int i = 0; i < verticalLines; i++) {
			double prop = ((double) i) / (verticalLines - 1);
			int x = (int) interpolate(left, right, prop);
			g.drawLine(x, top, x, bottom);
			
			if(captionHeight > 0) {
				Image img = createImage(captionHeight, 10);
				Graphics g2 = img.getGraphics();
				g2.setColor(backgroundColor);
				g2.fillRect(0, 0, captionHeight, 10);
				g2.setColor(gridColor);
				g2.drawString("" + getXCoord(x), 0, 10);
				
				AffineTransform rot = new AffineTransform();
				rot.translate(x + 5, where);
		        rot.rotate(Math.toRadians(90));
		        
		        g.drawImage(img, rot, null);
			}
		}
		
		//Draw the y labels
		int captionWidth = (int) ((1 - widthProportion) * width - 10);
		if(anchor == TOP_RIGHT || anchor == BOTTOM_RIGHT)
			where = 5;
		else
			where = width - captionWidth - 5;
		for(int i = 0; i < horizontalLines; i++) {
			double prop = ((double) i) / (horizontalLines - 1);
			int y = (int) interpolate(top, bottom - 1, prop);
			g.drawLine(left, y, right, y);
			
			if(captionWidth > 0) {
				Image img = createImage(captionWidth, 10);
				Graphics g2 = img.getGraphics();
				g2.setColor(backgroundColor);
				g2.fillRect(0, 0, captionWidth, 10);
				g2.setColor(gridColor);
				g2.drawString("" + getYCoord(y), 0, 10);
				
				g.drawImage(img, where, y - 5, null);
			}
		}
	}
}

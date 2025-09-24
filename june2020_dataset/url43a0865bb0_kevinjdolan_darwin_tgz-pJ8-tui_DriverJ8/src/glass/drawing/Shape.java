package glass.drawing;

import java.awt.Color;
import java.awt.geom.Point2D;

/**
 * The shape class maintains a minimalist representation of a shape
 * for evolution of the picture
 * 
 * @author Kevin Dolan
 */
public abstract class Shape {
	
	private Color color;
	private double opacity;
	
	/**
	 * Construct a new shape
	 * @param color   the color of the shape
	 * @param opacity the opacity of the shape [0,1]
	 */
	public Shape (Color color, double opacity) {
		this.color = color;
		this.opacity = opacity;
	}
	
	/**
	 * @return the color of the shape
	 */
	public Color getColor() {
		return color;
	}
	
	/**
	 * @return the opacity of the shape
	 */
	public double getOpacity() {
		return opacity;
	}
	
	/**
	 * Should be overidden to contain the logic of whether or not this
	 * shape contains a given point
	 * @param point the point with x,y values between 0 and 1
	 * @return		true if this shape contains the point
	 */
	public abstract boolean containsPoint(Point2D.Double point);
	
}

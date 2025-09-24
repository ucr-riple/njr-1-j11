package glass.drawing;

import java.awt.Color;
import java.awt.geom.Point2D;

public class Triangle extends Shape {

	private Point2D.Double a, b, c;
	
	/**
	 * Construct a new triangle
	 * @param a		  the point of the first vertex
	 * @param b		  the point of the second vertex
	 * @param c		  the point of the third vertex
	 * @param color	  the color of the shape
	 * @param opacity the shape's opacity
	 */
	public Triangle(Point2D.Double a, Point2D.Double b, Point2D.Double c, 
			Color color, double opacity) {
		super(color, opacity);
		this.a = a;
		this.b = b;
		this.c = c;
	}
	
	@Override
	public boolean containsPoint(Point2D.Double point) {
		return sameSide(point,a,b,c) && 
			sameSide(point,b,a,c) &&
			sameSide(point,c,a,b);
	}
	
	private boolean sameSide(Point2D.Double p1, Point2D.Double p2, 
			Point2D.Double a, Point2D.Double b) {
		
		Point2D.Double b_a = new Point2D.Double(b.x - a.x, b.y - a.y);
		Point2D.Double p1_a = new Point2D.Double(p1.x - a.x, p1.y - a.y);
		Point2D.Double p2_a = new Point2D.Double(p2.x - a.x, p2.y - a.y);
		
		double cp1 = crossProduct(b_a, p1_a);
		double cp2 = crossProduct(b_a, p2_a);
		
		return cp1 * cp2 >= 0;
	}
	
	private double crossProduct(Point2D.Double v, Point2D.Double w) {
		return v.x * w.y - v.y * w.x;
	}

}

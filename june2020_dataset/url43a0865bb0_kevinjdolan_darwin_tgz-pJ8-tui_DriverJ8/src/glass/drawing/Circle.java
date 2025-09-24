package glass.drawing;

import java.awt.Color;
import java.awt.geom.Point2D;

public class Circle extends Shape {

	private Point2D.Double center;
	private double radiusSquared;
	
	public Circle(Point2D.Double center, double radius, Color color, double opacity) {
		super(color, opacity);
		this.center = center;
		radiusSquared = radius * radius;
	}
	
	@Override
	public boolean containsPoint(Point2D.Double point) {
		double xDiff = center.x - point.x;
		double yDiff = center.y - point.y;
		double dist = xDiff * xDiff + yDiff * yDiff;
		return dist <= radiusSquared;
	}

}

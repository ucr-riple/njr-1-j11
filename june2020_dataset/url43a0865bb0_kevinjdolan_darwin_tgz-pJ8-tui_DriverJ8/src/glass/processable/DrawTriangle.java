package glass.processable;

import glass.drawing.ShapeSet;
import glass.drawing.Triangle;
import java.awt.geom.Point2D;
import darwin.common.Unit;
import darwin.population.Node;
import darwin.problemObject.Processable;

public class DrawTriangle extends Processable<Unit>{
		
	@Override
	public Unit getValue(Object parameters, Node[] childNodes) {
		Point2D.Double a = (Point2D.Double) (childNodes[0].getValue(parameters));
		Point2D.Double b = (Point2D.Double) (childNodes[1].getValue(parameters));
		Point2D.Double c = (Point2D.Double) (childNodes[2].getValue(parameters));
		java.awt.Color color = (java.awt.Color) (childNodes[3].getValue(parameters));
		Double opacity = (Double) (childNodes[4].getValue(parameters));
		
		ShapeSet shapes = (ShapeSet) parameters;
		Triangle triangle = new Triangle(a, b, c, color, opacity);
		shapes.addShape(triangle);
		
		childNodes[5].getValue(parameters);		
		return new Unit();
	}
	
	@Override
	public Processable<Unit> clone() {
		return new DrawTriangle();
	}

}

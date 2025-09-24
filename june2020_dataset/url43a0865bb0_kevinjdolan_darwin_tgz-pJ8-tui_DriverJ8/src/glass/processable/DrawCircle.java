package glass.processable;

import glass.drawing.Circle;
import glass.drawing.ShapeSet;
import java.awt.geom.Point2D;
import darwin.common.Unit;
import darwin.population.Node;
import darwin.problemObject.Processable;

public class DrawCircle extends Processable<Unit>{
		
	@Override
	public Unit getValue(Object parameters, Node[] childNodes) {
		Point2D.Double center = (Point2D.Double) (childNodes[0].getValue(parameters));
		Double radius = (Double) (childNodes[1].getValue(parameters));
		java.awt.Color color = (java.awt.Color) (childNodes[2].getValue(parameters));
		Double opacity = (Double) (childNodes[3].getValue(parameters));
		
		ShapeSet shapes = (ShapeSet) parameters;
		Circle circle = new Circle(center, radius, color, opacity);
		shapes.addShape(circle);
		
		return (Unit) (childNodes[4].getValue(parameters));
	}
	
	@Override
	public Processable<Unit> clone() {
		return new DrawCircle();
	}

}

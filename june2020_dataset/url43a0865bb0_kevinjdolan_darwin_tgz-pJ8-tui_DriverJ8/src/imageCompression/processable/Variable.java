package imageCompression.processable;

import java.awt.geom.Point2D;
import darwin.population.Node;
import darwin.problemObject.Processable;

public class Variable extends Processable<Double>{

	public static final int X = 0;
	public static final int Y = 1;
	
	private int value;
	
	public Variable(int value) {
		this.value = value;
	}
	
	@Override
	public Double getValue(Object parameters, Node[] childNodes) {
		Point2D.Double point = (Point2D.Double) parameters;
		
		if(value == X)
			return point.x;
		else
			return point.y;
	}
	
	@Override
	public Processable<Double> clone() {
		return new Variable(value);
	}

}

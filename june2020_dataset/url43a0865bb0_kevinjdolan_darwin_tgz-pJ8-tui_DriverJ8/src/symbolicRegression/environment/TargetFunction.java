package symbolicRegression.environment;

import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Discretize a function
 * @author Kevin Dolan
 */
public class TargetFunction implements XYValues{

	ArrayList<Point2D.Double> values;
	
	public TargetFunction(Function function, double minX, double maxX, int steps) {
		values = new ArrayList<Point2D.Double>();
		for(double i = 0; i < steps; i += 1) {
			double x = (i / (steps - 1)) * ( maxX - minX ) + minX;
			double y = function.getY(x);
			values.add(new Point2D.Double(x, y));
		}
			
	}
	
	@Override
	public ArrayList<Point2D.Double> getValues() {
		return values;
	}

}
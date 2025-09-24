package symbolicRegression.environment;

import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Hold a set of x and y values for regressing symbolically ;)
 * @author Kevin Dolan
 */
public interface XYValues {

	/**
	 * @return the list of (x,y) values
	 */
	public ArrayList<Point2D.Double> getValues();
	
}

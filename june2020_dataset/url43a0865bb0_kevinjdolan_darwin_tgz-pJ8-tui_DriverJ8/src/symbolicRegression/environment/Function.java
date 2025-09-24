package symbolicRegression.environment;

/**
 * Hold an x->y function
 * @author Kevin Dolan
 */
public interface Function {
	
	/**
	 * Calculate f(x)
	 * @param x coordinate parameter
	 * @return  y coordinate
	 */
	public double getY(double x);
	
}

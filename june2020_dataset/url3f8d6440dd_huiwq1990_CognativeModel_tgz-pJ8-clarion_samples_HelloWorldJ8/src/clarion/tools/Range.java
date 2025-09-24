package clarion.tools;

import clarion.system.Value;

/**
 * This class implements a range for use within the TrainableImplicitModulePreTrainer class.
 * It extends the Value class.
 * <p>
 * <b>Usage:</b>
 * <p>
 * This class is used for training a trainable implicit module to report the correct
 * output activations based on a range of input activations from a lower bound to an upper bound
 * at a precision determined by the increment constant.
 * <p>
 * Since this class is a subclass of Value, it can be used within the dimensions of a dimension-value
 * collection as a replacement for a Value. However, it should be noted that a Range is meant for "offline" 
 * training purposes ONLY. You should NOT use this class to define the input space of your task 
 * (although the system will not be affected if you do).
 * <p>
 * This class contains both global (static) and local constants. The default is to use the local 
 * constants. If you want to change any of the global constants, you need to do so before any
 * instances of this class are initialized.
 * @version 6.0.6
 * @author Nick Wilson
 */

public class Range extends Value{
	
	/**The lower bound of the range.*/
	private double lowerbound;
	/**The upper bound of the range.*/
	private double upperbound;
	
	/**The increment at which to traverse through the range*/
	public static double GLOBAL_INCREMENT = .01;
	/**The increment at which to traverse through the range*/
	public double INCREMENT = GLOBAL_INCREMENT;
	
	/**
	 * Initializes a range with the specified ID, lower and upper bounds.
	 * @param id The ID to set for the range.
	 * @param lower The lower bound.
	 * @param upper The upper bound.
	 * @throws IllegalArgumentException If the upper bound is less than the lower bound.
	 */
	public Range (Object id, double lower, double upper) throws IllegalArgumentException
	{
		super(id);
		if(upper < lower)
			throw new IllegalArgumentException ("The upper bound cannot be less than the " +
					"lower bound");
		lowerbound = lower;
		upperbound = upper;
	}
	
	/**
	 * Gets the lower bound of the range.
	 * @return The lower bound.
	 */
	public double getLowerBound ()
	{
		return lowerbound;
	}
	
	/**
	 * Sets the lower bound for the range.
	 * @param lower The lower bound.
	 * @throws IllegalArgumentException If the lower bound specified is greater than the
	 * upper bound.
	 */
	public void setLowerBound (double lower) throws IllegalArgumentException
	{
		if(upperbound < lower)
			throw new IllegalArgumentException ("The lower bound cannot be greater than the " +
					"upper bound");
		lowerbound = lower;
	}
	
	/**
	 * Gets the upper bound of the range.
	 * @return The upper bound.
	 */
	public double getUpperBound ()
	{
		return upperbound;
	}
	
	/**
	 * Sets the upper bound for the range.
	 * @param upper The upper bound.
	 * @throws IllegalArgumentException If the upper bound specified is less than the
	 * lower bound.
	 */
	public void setUpperBound (double upper) throws IllegalArgumentException
	{
		if(upper < lowerbound)
			throw new IllegalArgumentException ("The upper bound cannot be less than the " +
					"lower bound");
		upperbound = upper;
	}
	
	/**
	 * Clones the range.
	 * @return A copy of the range.
	 */
	public Range clone ()
	{
		Range a = new Range (ID, lowerbound, upperbound);
		a.Activation = Activation;
		a.hash = hash;
		a.FULL_ACTIVATION_THRESHOLD = FULL_ACTIVATION_THRESHOLD;
		a.MINIMUM_ACTIVATION_THRESHOLD = MINIMUM_ACTIVATION_THRESHOLD;
		a.lowerbound = lowerbound;
		a.upperbound = upperbound;
		a.INCREMENT = INCREMENT;
		return a;
	}
	
	public String toString()
	{
		if(!(ID == null) && !(ID instanceof Value))
			return "Range ID - " + ID.toString() + ": " + Activation + "\nLower Bound: " + lowerbound + ", Upper Bound: "
			+ upperbound + ", Increment: " + INCREMENT;
		return "Range ID - ?: " + Activation + "\nLower Bound: " + lowerbound + ", Upper Bound: "
		+ upperbound + ", Increment: " + INCREMENT;
	}
}

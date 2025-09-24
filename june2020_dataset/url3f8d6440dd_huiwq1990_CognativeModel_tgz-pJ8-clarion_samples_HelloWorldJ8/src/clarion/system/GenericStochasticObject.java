package clarion.system;

/**
 * This class implements a generic stochastic object within CLARION.
 * It implements the InterfaceStochasticallySelectable interface.
 * <p>
 * <b>Usage:</b>
 * <p>
 * This class is used by the StochasticSelector class when you want to select over a group of objects that are not
 * necessarily an output chunk, which is what most often gets stochastically selected within CLARION.
 * <p>
 * Most often, this class is used by the ACS to stochastically decide which type (BL, RER, IRL, FR) to use for action
 * decision making.
 * @version 6.0.4
 * @author Nick Wilson
 */
public class GenericStochasticObject implements
		InterfaceStochasticallySelectable {
	
	/**How close two final selection measures must be in order to be considered equal. Needed due to the nature
	 * of double precision arithmetic.*/
	public static double GLOBAL_FINAL_SELECTION_MEASURE_EPSILON = .0001;
	
	/**The measure to use for stochastic selection.*/
	private double measure = 0;
	
	/**
	 * Initializes a generic stochastic object with the specified measure.
	 * @param m The measure to be used within the generic stochastic object for stochastic selection.
	 */
	public GenericStochasticObject (double m)
	{
		measure = m;
	}
	
	/**
	 * Sets the selection measure.
	 * @param m The measure to set.
	 */
	public void setFinalSelectionMeasure(double m)
	{
		measure = m;
	}
	
	/**
	 * Gets the selection measure.
	 * @return The selection measure.
	 */
	public double getFinalSelectionMeasure() {
		return measure;
	}
	
	/**
	 * Compares the final selection measure of this object to the specified stochastically selectable object.
	 * @param o The stochastically selectable object to compare.
	 * @return A negative integer, 0, or positive integer if the final selection measure of this object is 
	 * less than, equal to, or greater than the final selection measure of the specified object.
	 */
	public int compareTo(InterfaceStochasticallySelectable o) {
		if(Math.abs(o.getFinalSelectionMeasure() - measure) <= GLOBAL_FINAL_SELECTION_MEASURE_EPSILON)
			return 0;
		if(o.getFinalSelectionMeasure() > measure)
			return -1;
		else
			return 1;
	}
	
	public boolean equals (Object s)
	{
		if(!(s instanceof GenericStochasticObject))
			return false;
		return Math.abs(((GenericStochasticObject)s).measure - measure) <= GLOBAL_FINAL_SELECTION_MEASURE_EPSILON;
	}
}

package clarion.system;

/**
 * This class implements a match calculator within CLARION.
 * This class is abstract and therefore cannot be instantiated on its own.
 * <p>
 * <b>Usage:</b>
 * <p>
 * This class acts as a framework for building match calculators to use within the subsystems of CLARION (mainly the ACS).
 * It is used for calculating match statistics for any components within the CLARION Library that implement 
 * InterfaceTracksMatchStatistics. Usually the subsystem itself will contain a match calculator which is used communally by
 * all of the components within that subsystem. However, if a component implements InterfaceHasMatchCalculator, then
 * that component will use its own match calculator.
 * <p>
 * <b>Known Subclasses:</b><br>
 * <ul>
 * <li>DefaultMatchCalculator</li>
 * <li>QLearningMatchCalculator</li>
 * </ul>
 * @version 6.0.4
 * @author Nick Wilson
 */
public abstract class AbstractMatchCalculator {
	
	/**
	 * Checks to see if an outcome was positive based on the feedback received and a specified pre-defined 
	 * threshold parameter. It is up to the user to define the algorithm here that is used for determining positivity. 
	 * @param feedback The feedback received.
	 * @param threshold The threshold that must be passed in order for the outcome to be considered positive.
	 * @return True if the outcome was positive, otherwise false.
	 */
	public abstract boolean isPositive (double feedback, double threshold);
	
	//protected abstract AbstractMatchCalculator internalClone ();
	
	//public abstract AbstractMatchCalculator clone ();
	
	//public abstract String toString();
}

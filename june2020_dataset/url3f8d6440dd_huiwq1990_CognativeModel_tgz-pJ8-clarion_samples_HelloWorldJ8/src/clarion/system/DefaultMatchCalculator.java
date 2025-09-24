package clarion.system;

/**
 * This class implements a default match calculator within CLARION. It extends the AbstractMatchCalculator class.
 * <p>
 * <b>Usage:</b>
 * <p>
 * This class acts as the default match calculator that can be used within the subsystems of CLARION (mainly the ACS).
 * It is used for calculating match statistics for any components within the CLARION Library that implement 
 * InterfaceTracksMatchStatistics. Usually the subsystem itself will contain a match calculator which is used communally by
 * all of the components within that subsystem. However, if a component implements InterfaceHasMatchCalculator, then
 * that component will use its own match calculator.
 * <p>
 * The method used by the default match calculator for determining positivity is:
 * <p>
 * <b> Feedback >= Positive Match Threshold </b>
 * @version 6.0.4
 * @author Nick Wilson
 */
public class DefaultMatchCalculator extends AbstractMatchCalculator {

	/**
	 * Checks to see if an outcome was positive based on the feedback received and a specified pre-defined 
	 * threshold parameter.
	 * <p>
	 * The method used by the default match calculator for determining positivity is:
	 * <p>
	 * <b> Feedback >= Positive Match Threshold </b>
	 * @param feedback The feedback received.
	 * @param threshold The threshold that must be passed in order for the outcome to be considered positive.
	 * @return True if the outcome was positive, otherwise false.
	 */
	public boolean isPositive(double feedback, double threshold)
	{
		if (feedback >= threshold)
			return true;
		else
			return false;
	}

}

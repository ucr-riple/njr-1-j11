package clarion.system;

/**
 * This interface is implemented by classes that wish to use their own match calculator within CLARION.
 * <p>
 * <b>Usage:</b>
 * <p>
 * Any class that implements this interface will use its own match calculator when updating match statistics
 * instead of using the subsystem-wide match calculator.
 * <p>
 * <b>Known classes implementing this interface:</b><br>
 * <ul>
 * <li>QBPNet</li>
 * </ul>
 * @version 6.0.4
 * @author Nick Wilson
 */
public interface InterfaceHasMatchCalculator {
	
	/**
	 * Gets the match calculator used by the class. The match calculator returned by this
	 * method is usually passed directly into the class's updateMatchStatistics method.
	 * @return The match calculator.
	 */
	AbstractMatchCalculator getMatchCalculator ();
	
	/**
	 * Sets the match calculator.
	 * @param MatchCalculator The match calculator to assign to the class.
	 */
	void setMatchCalculator(AbstractMatchCalculator MatchCalculator);
}

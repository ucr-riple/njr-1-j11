package clarion.system;

/**
 * This class implements a Q-learning match calculator within CLARION. It extends the AbstractMatchCalculator class.
 * <p>
 * <b>Usage:</b>
 * <p>
 * This class can be used within the subsystems of CLARION (mainly the ACS) for calculating match statistics for any components 
 * within the CLARION Library that implement InterfaceTracksMatchStatistics. Usually the subsystem itself will contain a match 
 * calculator which is used communally by all of the components within that subsystem. However, if a component implements 
 * InterfaceHasMatchCalculator, then that component will use its own match calculator.
 * <p>
 * The method used by the Q-learning match calculator for determining positivity is:
 * <p>
 * <b> Discount * Max(Q(y,b)) + Feedback - Q(x,a) >= Positive Match Threshold </b>
 * @version 6.0.4
 * @author Nick Wilson
 */
public class QLearningMatchCalculator extends AbstractMatchCalculator {
	
	/**The Q-learning object that is used for calculating positivity. This can be anything that implements the 
	 * InterfaceUsesQLearning interface (although most commonly it is a QBPNet).*/
	private InterfaceUsesQLearning Q;
	
	/**
	 * Initializes the Q-learning match calculator with Q-learning object specified.
	 * @param q The Q-learning object to use for determining positivity.
	 */
	public QLearningMatchCalculator (InterfaceUsesQLearning q)
	{
		Q = q;
	}
	
	/**
	 * Checks to see if an outcome was positive based on the feedback received and a specified pre-defined 
	 * threshold parameter.
	 * <p>
	 * The method used by the Q-learning match calculator for determining positivity is:
	 * <p>
	 * <b> Discount * Max(Q(y,b)) + Feedback - Q(x,a) >= Positive Match Threshold </b>
	 * @param feedback The feedback received.
	 * @param threshold The threshold that must be passed in order for the outcome to be considered positive.
	 * @return True if the outcome was positive, otherwise false.
	 */
	public boolean isPositive(double feedback, double threshold) {
		double Ey = Q.getDiscount() * Q.getMaxQ();
    	if(Ey + feedback - Q.getChosenOutput().getActivation() > threshold)
    		return true;
    	else
    		return false;
	}

}

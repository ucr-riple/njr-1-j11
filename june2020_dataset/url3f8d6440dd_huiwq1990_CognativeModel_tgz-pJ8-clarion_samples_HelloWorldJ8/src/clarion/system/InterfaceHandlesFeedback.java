package clarion.system;

/**
 * This interface is implemented by classes that handle feedback within CLARION.
 * <p>
 * <b>Usage:</b>
 * <p>
 * Any class that implements this interface will be delivered feedback by CLARION when feedback
 * is received.
 * <p>
 * <b>Known Subinterfaces:</b><br>
 * <ul>
 * <li>InterfaceHandlesFeedbackWithTime</li>
 * <li>InterfaceRuntimeTrainable</li>
 * </ul>
 * <p>
 * <b>Known classes implementing this interface:</b><br>
 * <ul>
 * <li>AbstractEquation</li>
 * <li>ACSLevelProbabilitySettingEquation</li>
 * <li>DriveEquation</li>
 * <li>GoalSelectionEquation</li>
 * <li>ImplicitModuleCollection</li>
 * <li>RuleCollection</li>
 * </ul>
 * @version 6.0.4
 * @author Nick Wilson
 */
public interface InterfaceHandlesFeedback{
	
	/**
	 * Gets the feedback.
	 * @return The feedback.
	 */
	double getFeedback();
	
	/**
	 * Sets the feedback to the value specified.
	 * @param R The value to set for the feedback.
	 */
	void setFeedback (double R);
}

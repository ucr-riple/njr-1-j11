package clarion.system;

/**
 * This interface is implemented by classes that use the Q-learning algorithm for learning and updating of
 * match statistics within CLARION. It extends the InterfaceRuntimeTrainable interface.
 * <p>
 * <b>Usage:</b>
 * <p>
 * Any class that implements this interface will perform learning and updating of match statistics within 
 * CLARION using the q-learning method (see Sun Tutorial, 2003).
 * <p>
 * <b>Known classes implementing this interface:</b><br>
 * <ul>
 * <li>QBPNet</li>
 * </ul>
 * @version 6.0.4
 * @author Nick Wilson
 */
public interface InterfaceUsesQLearning extends InterfaceRuntimeTrainable{
	
	/**
	 * Gets the maximum Q-value for the new time step. This function is otherwise known as Max(Q(y,b)) in the 
	 * Q-learning literature.
	 * @return The maximum Q-value for the new time step.
	 */
	double getMaxQ ();
	
	/**
	 * Gets the discount factor that is used as part of the Q-learning algorithm (see Sun Tutorial, 2003).
	 * @return The discount factor.
	 */
	double getDiscount ();
	
	/**
	 * Gets the chosen output from the previous time step.
	 * @return The chosen output.
	 */
	AbstractOutputChunk getChosenOutput ();
}

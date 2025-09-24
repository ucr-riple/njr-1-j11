package clarion.system;

import java.util.Collection;

/**
 * This interface is implemented by classes that are capable of handling new input within CLARION.
 * <p>
 * <b>Usage:</b>
 * <p>
 * Any class that implements this interface will be given the "new input" during the training phase (to 
 * facilitate training) after perception has occurred, but before action decision making takes place.
 * <p>
 * <b>Known classes implementing this interface:</b><br>
 * <ul>
 * <li>QBPNet</li>
 * </ul>
 * @version 6.0.4
 * @author Nick Wilson
 */
public interface InterfaceHandlesNewInput {
	
	/**
	 * Gets the new inputs.
	 * @return The new input.
	 */
	Collection <Dimension> getNewInput();
	
	/**
	 * Sets the new input.
	 * @param input The input to set as new input.
	 */
	void setNewInput (Collection <Dimension> input);
}

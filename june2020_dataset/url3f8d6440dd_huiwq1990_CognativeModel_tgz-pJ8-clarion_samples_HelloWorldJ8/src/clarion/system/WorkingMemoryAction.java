package clarion.system;

import java.util.Collection;
import java.util.Map;

/**
 * This class implements a working memory action within CLARION. It extends the AbstractAction class.
 * <p>
 * <b>Usage:</b>
 * <p>
 * A working memory action is an action that is performed internally. Working memory actions are used internally by CLARION to
 * set, reset, remove, or change the activations of the values for a working memory chunks in the working memory.
 * <p>
 * An action chunk is attached to the output layer of the bottom level implicit modules in the ACS as well as to rules 
 * on the top level. Actions are not required to have dimension-value pairs, but it is advised they be provided as
 * they act as the "instructions" that dictate how an action is to be performed.
 * <p>
 * Activated values within the action chunk indicate the parts of the action that should be performed. Non-activated values in the 
 * action chunk are used to specify those components that should NOT be performed. Therefore, both activation and non-activation of 
 * values provide important information for how to handle the action.
 * <p>
 * Currently, working memory actions are delivered to the task environment in which the agent exists and it is the user's 
 * responsibility to handle the directives of the working memory action. However, in future versions of this Library, the performing 
 * of these actions will be internalized.
 * @version 6.0.4
 * @author Nick Wilson
 */
public class WorkingMemoryAction extends AbstractAction {
	private static final long serialVersionUID = 451118511479567108L;
	
	/**The hash for this object. When this item is cloned, so is this hash.*/
	private int hash;
	
	/**
	 * Initializes the working memory action with the ID specified.
	 * @param id The ID of the working memory action.
	 */
	public WorkingMemoryAction (Object id)
	{
		super(id);
		hash = System.identityHashCode(this);
	}
	
	/**
	 * Initializes the working memory action with the specified ID and dimensions.
	 * @param id The ID of the working memory action.
	 * @param dims The dimensions for the working memory action.
	 */
	public WorkingMemoryAction (Object id, Collection <? extends Dimension> dims)
	{
		super(id, dims);
		hash = System.identityHashCode(this);
	}
	
	/**
	 * Initializes the working memory action with the specified ID and map of dimensions.
	 * @param id The ID of the working memory action.
	 * @param dims The map of dimensions for the working memory action.
	 */
	public WorkingMemoryAction (Object id, Map <? extends Object, ? extends Dimension> dims)
	{
		super(id, dims);
		hash = System.identityHashCode(this);
	}
	
	public int hashCode()
	{
		return hash;
	}
	
	/**
	 * Clones the working memory action (including all of it's dimensions).
	 * @return A copy of the working memory action.
	 */
	public WorkingMemoryAction clone ()
	{
		WorkingMemoryAction a = new WorkingMemoryAction(getID());
		a.setActivation(getActivation());
		for(Dimension i : values())
		{
			Dimension d = i.clone();
			a.put(d.getID(),d);
		}
		a.hash = hash;
		a.INITIAL_BLA = INITIAL_BLA;
		a.C = C;
		a.D = D;
		a.T = T.clone();
		a.LatestTimeStamp = LatestTimeStamp;
		return a;
	}
}

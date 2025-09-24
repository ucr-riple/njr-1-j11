package clarion.system;

import java.util.Collection;
import java.util.Map;

/**
 * This class implements an external action within CLARION. It extends the AbstractAction class.
 * <p>
 * <b>Usage:</b>
 * <p>
 * An external action is an action that is performed externally. External actions are delivered to a task environment (or
 * a module outside of the CLARION Library that acts as a another part of the agent's body) that is capable of implementing
 * the action's directives.
 * <p>
 * An action chunk is attached to the output layer of the bottom level implicit modules in the ACS as well as to rules 
 * on the top level. Actions are not required to have dimension-value pairs, but it is advised they be provided as
 * they act as the "instructions" that dictate how an action is to be performed.
 * <p>
 * Activated values within the action chunk indicate the parts of the action that should be performed. Non-activated values in the 
 * action chunk are used to specify those components that should NOT be performed. Therefore, both activation and non-activation of 
 * values provide important information for how to handle the action.
 * @version 6.0.4
 * @author Nick Wilson
 */
public class ExternalAction extends AbstractAction {
	private static final long serialVersionUID = -7450090124418148676L;
	
	/**The hash for this object. When this item is cloned, so is this hash.*/
	private int hash;

	/**
	 * Initializes the external action with the ID specified.
	 * @param id The ID of the external action.
	 */
	public ExternalAction (Object id)
	{
		super(id);
		hash = System.identityHashCode(this);
	}
	
	/**
	 * Initializes the external action with the specified ID and dimensions.
	 * @param id The ID of the external action.
	 * @param dims The dimensions for the external action.
	 */
	public ExternalAction (Object id, Collection <? extends Dimension> dims)
	{
		super(id, dims);
		hash = System.identityHashCode(this);
	}
	
	/**
	 * Initializes the external action with the specified ID and map of dimensions.
	 * @param id The ID of the external action.
	 * @param dims The map of dimensions for the external action.
	 */
	public ExternalAction (Object id, Map <? extends Object, ? extends Dimension> dims)
	{
		super(id, dims);
		hash = System.identityHashCode(this);
	}
	
	public int hashCode()
	{
		return hash;
	}
	
	/**
	 * Clones the external action (including all of it's dimensions).
	 * @return A copy of the external action.
	 */
	public ExternalAction clone ()
	{
		ExternalAction a = new ExternalAction(getID());
		a.setActivation(getActivation());
		for(Dimension i : values())
		{
			Dimension d = i.clone();
			a.put(d.getID(),d);
		}
		a.INITIAL_BLA = INITIAL_BLA;
		a.C = C;
		a.D = D;
		a.T = T.clone();
		a.LatestTimeStamp = LatestTimeStamp;
		a.hash = hash;
		return a;
	}
}

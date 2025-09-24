package clarion.system;

import java.util.Collection;
import java.util.Map;

/**
 * This class implements an action chunk within CLARION. It extends the AbstractOutputChunk class.
 * This class is abstract and therefore cannot be instantiated on its own.
 * <p>
 * <b>Usage:</b>
 * <p>
 * An action chunk is attached to the output layer of the bottom level implicit modules in the ACS as well as to rules 
 * on the top level. Actions are not required to have dimension-value pairs, but it is advised they be provided as
 * they act as the "instructions" that dictate how an action is to be performed.
 * <p>
 * Activated values within the action chunk indicate the parts of the action that should be performed. Non-activated values in the 
 * action chunk are used to specify those components that should NOT be performed. Therefore, both activation and non-activation of 
 * values provide important information for how to handle the action.
 * <p>
 * <b>Known Subclasses:</b><br>
 * <ul>
 * <li>ExternalActions</li>
 * <li>WorkingMemoryActions</li>
 * <li>GoalActions</li>
 * </ul>
 * @version 6.0.6
 * @author Nick Wilson
 */
public abstract class AbstractAction extends AbstractOutputChunk {
	private static final long serialVersionUID = 901821869871702369L;

	/**
	 * Initializes the action with the ID specified.
	 * @param id The ID of the action.
	 */
	public AbstractAction (Object id)
	{
		super(id);
	}
	
	/**
	 * Initializes the action with the specified ID and dimensions.
	 * @param id The ID of the action.
	 * @param dims The dimensions for the action.
	 */
	public AbstractAction (Object id, Collection <? extends Dimension> dims)
	{
		super(id, dims);
	}
	
	/**
	 * Initializes the action with the specified ID and map of dimensions.
	 * @param id The ID of the action.
	 * @param dims The map of dimensions for the action.
	 */
	public AbstractAction (Object id, Map <? extends Object, ? extends Dimension> dims)
	{
		super(id, dims);
	}
	
	/**
	 * Puts the dimension into the action as long as the dimension is not already in the action. This 
	 * method also sets the activations of all of the values in the dimension to the minimum 
	 * activation threshold (i.e. to the off position). If the dimension is already in the 
	 * action, this method throws an exception. If the specified key is not the ID of the 
	 * specified dimension, this method throws an exception.
	 * @param key The key with which the specified dimension is to be associated. This MUST be the ID
	 * of the specified dimension.
	 * @param dim The dimension to add to the action.
	 * @return The result of putting the dimension in the action. This will 
	 * always return null (meaning the dimension did not previously exist in the map). This is 
	 * because you are not allowed to put a dimension into an action that already contains that 
	 * dimension.
	 * @throws IllegalArgumentException If the dimension is already in the action
	 * or the specified key is not the ID of the specified dimension.
	 */
	public Dimension put (Object key, Dimension dim) throws IllegalArgumentException
	{
		if(containsKey(key) || containsValue(dim) || !key.equals(dim.getID()))
			throw new IllegalArgumentException ("The specified dimension is already in this " +
					"action or the specified key is not the ID of the specified dimension.");
		for(Value v : dim.values())
			v.setActivation(v.MINIMUM_ACTIVATION_THRESHOLD);
		return super.put(key,dim);
	}
	
	/**
	 * Puts all of the dimensions in the specified map into the action as long as the 
	 * dimensions are not already in the action.
	 * @param map The map of dimensions to add.
	 */
	public void putAll (Map <? extends Object, ? extends Dimension> map)
	{
		for(Map.Entry<? extends Object, ? extends Dimension> e : map.entrySet())
			put(e.getKey(),e.getValue());
	}
	
	public abstract AbstractAction clone ();
}

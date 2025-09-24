package clarion.system;

import java.util.Collection;
import java.util.EnumMap;
import java.util.HashMap;

/**
 * This class implements an intermediate module within CLARION. It extends the HashMap class.
 * This class is abstract and therefore cannot be instantiated on its own.
 * <p>
 * <b>Usage:</b>
 * <p>
 * This class is used as the foundation for modules within CLARION that exist between (or within multiple)
 * subsystems (such as the working memory, episodic memory, and the goal structure). This class is mostly used for 
 * internal development purposes related to the CLARION Library. Currently there is no simple method for user-defined
 * intermediate modules. The addition of any intermediate module to the CLARION Library would require major
 * structural changes to the system itself as well as implications for the CLARION theory.
 * <p>
 * <b>Known Subclasses:</b><br>
 * <ul>
 * <li>EpisodicMemory</li>
 * <li>GoalStructure</li>
 * <li>WorkingMemory</li>
 * </ul>
 * @version 6.0.6
 * @author Nick Wilson
 */
public abstract class AbstractIntermediateModule <K extends Enum <K>,V> extends HashMap <Long, EnumMap<K, V>>{
	private static final long serialVersionUID = -5885464414882616577L;
	
	/**Points to the input space from the instance of the CLARION class to which this
	 * instance of the intermediate system is attached.*/
	protected DimensionValueCollection InputSpace;
	
	/**
	 * Initializes the intermediate system. During initialization this instance of the intermediate
	 * system will attach itself to the CLARION agent you specify.
	 * @param Agent The agent to which the intermediate system is being attached.
	 */
	public AbstractIntermediateModule (CLARION Agent) 
	{
		InputSpace = Agent.getInputSpace();
		attachSelfToAgent(Agent);
	}
	
	/**
	 * Attaches the intermediate system to the specified CLARION agent.
	 * @param Agent The agent to which this intermediate system will be attached.
	 */
	protected abstract void attachSelfToAgent (CLARION Agent);
	
	/**
	 * Updates the input space based on the specified collection of dimension-value pairs. If any
	 * dimensions or values exist within the specified collection that are not currently in the 
	 * input space, those dimensions and/or values are added to the input space.
	 * @param c The collection of dimension-value pairs with which to update the input space.
	 */
	protected void updateInputSpace (Collection <Dimension> c)
	{
		for(Dimension d : c)
		{
			Dimension inputdim = InputSpace.get(d.getID());
			if(inputdim == null)
			{
				inputdim = d.clone();
				for(Value v : inputdim.values())
					v.setActivation(v.FULL_ACTIVATION_THRESHOLD);
				InputSpace.put(inputdim.getID(),inputdim);
			}
			else
			{
				for(Value v : d.values())
				{
					if(!inputdim.containsKey(v.getID()))
					{
						Value av = v.clone();
						av.setActivation(av.FULL_ACTIVATION_THRESHOLD);
						inputdim.put(av.getID(),av);
					}
				}
			}
		}
	}
	
	public abstract void add (V o, Long TimeStamp, K type);

	public abstract V remove (Long TimeStamp);
	
	public abstract void remove (V o, K type);
	
	public abstract V get (Long TimeStamp);
	
	public abstract V get (Long TimeStamp, K type);
	
	public abstract Collection <V> get (K type);
	
	public abstract Collection <? extends V> getAll ();
}

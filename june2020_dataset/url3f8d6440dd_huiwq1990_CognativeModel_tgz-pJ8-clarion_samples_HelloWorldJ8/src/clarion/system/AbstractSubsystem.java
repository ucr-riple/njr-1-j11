package clarion.system;

import java.util.Collection;

/**
 * This class implements a subsystem within CLARION. This class is abstract and therefore cannot be instantiated 
 * on its own.
 * <p>
 * <b>Usage:</b>
 * <p>
 * This class is used as the foundation for the subsystems of CLARION. This class is mostly used for internal 
 * development purposes related to the CLARION Library. Currently there is no simple method for user-defined
 * subsystems. The addition of any subsystem to the CLARION Library would require major structural changes to the 
 * system itself as well as major implications for the CLARION theory.
 * <p>
 * <b>Known Subclasses:</b><br>
 * <ul>
 * <li>ACS</li>
 * <li>MCS</li>
 * <li>MS</li>
 * <li>NACS</li>
 * </ul>
 * @version 6.0.4
 * @author Nick Wilson
 */
public abstract class AbstractSubsystem {
	
	/**Points to the input space from the instance of the CLARION class to which this
	 * instance of the subsystem is attached.*/
	protected DimensionValueCollection InputSpace;
	
	/**
	 * Initializes the subsystem and attaches it to the specified CLARION Agent. During initialization
	 * this instance of the subsystem will attach itself to the CLARION agent you specify.
	 * @param Agent The agent to which the subsystem is being attached.
	 */
	public AbstractSubsystem (CLARION Agent) 
	{
		InputSpace = Agent.getInputSpace();
		attachSelfToAgent(Agent);
	}
	
	/**
	 * Attaches the subsystem to the specified CLARION agent.
	 * @param Agent The agent to which this subsystem will be attached.
	 */
	protected abstract void attachSelfToAgent (CLARION Agent);
	
	/**
	 * Performs the appropriate end of episode instructions for the subsystem. This method is called by the
	 * CLARION class its endEpisode method is called.
	 * @param Input A collection of various information to be used for ending the episode.
	 * @param TimeStamp The current time stamp.
	 */
	protected abstract void endEpisode (DimensionValueCollection Input, long TimeStamp);
	
	//protected abstract AbstractSubsystem internalClone ();
	
	//public abstract AbstractSubsystem clone ();
	
	//public abstract String toString();
	
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
}

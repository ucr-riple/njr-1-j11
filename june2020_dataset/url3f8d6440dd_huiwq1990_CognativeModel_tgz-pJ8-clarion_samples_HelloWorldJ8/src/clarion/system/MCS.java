package clarion.system;

import java.util.HashSet;
import java.util.HashMap;
import java.util.Collection;

/**
 * This class implements the Meta Cognitive Subsystem (MCS) within CLARION. 
 * It extends the AbstractSubsystem class.
 * <p>
 * <b>Usage:</b>
 * <p>
 * This class contains the modules of the MCS along with methods for getting and setting those modules.
 * The user can decide, as desired, which modules to attach to the MCS and the CLARION Library will
 * use those modules if they are attached, otherwise it wont. In this sense, the CLARION Library
 * is dynamic and flexible about which modules appear within the MCS.
 * <p>
 * The modules of the MCS are accessed most commonly by the CLARION class through the perceive and act methods.
 * Note that if a developer wished to write a currently unimplemented module into the MCS, the only ways
 * to do so would be to either edit the CLARION library (not recommended) or write their own
 * MCS and CLARION classes by extending those classes and then adding/overriding (respectively) the new 
 * modules/perceive and act functions (again respectively).
 * <p>
 * Currently, the following modules have been implemented, and can be attached, as needed, within the MCS:
 * <ul>
 * <li>The ACS Level Probability Setting Module</li>
 * <li>The Goal Selection Module </li>
 * <li>The Judgment Correction Module </li>
 * </ul>
 * <p>
 * Note that it is not required that a CLARION agent have an MCS at all. If the task being performed
 * by the agent does not require an MCS, the user can choose simply not to attach the MCS to the
 * CLARION agent and that agent will be able to operate quite successfully without this subsystem being
 * specified (for some tasks).
 * @version 6.0.6
 * @author Nick Wilson
 */
public class MCS extends AbstractSubsystem{
	
	private HashMap <Class<? extends InterfaceMCSRunTimes>, HashSet <AbstractMetaCognitiveModule>> Modules = 
		new HashMap<Class<? extends InterfaceMCSRunTimes>, HashSet <AbstractMetaCognitiveModule>> ();
	
	/**
	 * Minimally initializes the MCS. This constructor builds a "frame" for this instance of
	 * the MS from which all desired components can be attached. During initialization this
	 * instance of the MCS will attach itself to the CLARION agent you specify.
	 * @param Agent The agent to which the MCS is being attached.
	 */
	public MCS (CLARION Agent) 
	{
		super(Agent);
	}
	
	/**
	 * Adds the specified module to the MCS.
	 * @param module The module to add.
	 */
	public void addModule (AbstractMetaCognitiveModule module)
	{
		if(module instanceof InterfaceMCSRunsAfterACS)
		{
			if(!Modules.containsKey(InterfaceMCSRunsAfterACS.class))
				Modules.put(InterfaceMCSRunsAfterACS.class, new HashSet<AbstractMetaCognitiveModule>());
			Modules.get(InterfaceMCSRunsAfterACS.class).add(module);
		}
		if(module instanceof InterfaceMCSRunsAfterEarlyPerceptionStage)
		{
			if(!Modules.containsKey(InterfaceMCSRunsAfterEarlyPerceptionStage.class))
				Modules.put(InterfaceMCSRunsAfterEarlyPerceptionStage.class, new HashSet<AbstractMetaCognitiveModule>());
			Modules.get(InterfaceMCSRunsAfterEarlyPerceptionStage.class).add(module);
		}
		if(module instanceof InterfaceMCSRunsAfterLearning)
		{
			if(!Modules.containsKey(InterfaceMCSRunsAfterLearning.class))
				Modules.put(InterfaceMCSRunsAfterLearning.class, new HashSet<AbstractMetaCognitiveModule>());
			Modules.get(InterfaceMCSRunsAfterLearning.class).add(module);
		}
		if(module instanceof InterfaceMCSRunsAfterNACS)
		{
			if(!Modules.containsKey(InterfaceMCSRunsAfterNACS.class))
				Modules.put(InterfaceMCSRunsAfterNACS.class, new HashSet<AbstractMetaCognitiveModule>());
			Modules.get(InterfaceMCSRunsAfterNACS.class).add(module);
		}
		if(module instanceof InterfaceMCSRunsAtEpisodeEnd)
		{
			if(!Modules.containsKey(InterfaceMCSRunsAtEpisodeEnd.class))
				Modules.put(InterfaceMCSRunsAtEpisodeEnd.class, new HashSet<AbstractMetaCognitiveModule>());
			Modules.get(InterfaceMCSRunsAtEpisodeEnd.class).add(module);
		}
		if(module instanceof InterfaceMCSRunsAtEpisodeStart)
		{
			if(!Modules.containsKey(InterfaceMCSRunsAtEpisodeStart.class))
				Modules.put(InterfaceMCSRunsAtEpisodeStart.class, new HashSet<AbstractMetaCognitiveModule>());
			Modules.get(InterfaceMCSRunsAtEpisodeStart.class).add(module);
		}
		if(module instanceof InterfaceMCSRunsAtPerception)
		{
			if(!Modules.containsKey(InterfaceMCSRunsAtPerception.class))
				Modules.put(InterfaceMCSRunsAtPerception.class, new HashSet<AbstractMetaCognitiveModule>());
			Modules.get(InterfaceMCSRunsAtPerception.class).add(module);
		}
		if(module instanceof InterfaceMCSRunsBeforeACS)
		{
			if(!Modules.containsKey(InterfaceMCSRunsBeforeACS.class))
				Modules.put(InterfaceMCSRunsBeforeACS.class, new HashSet<AbstractMetaCognitiveModule>());
			Modules.get(InterfaceMCSRunsBeforeACS.class).add(module);
		}
		if(module instanceof InterfaceMCSRunsBeforeNACS)
		{
			if(!Modules.containsKey(InterfaceMCSRunsBeforeNACS.class))
				Modules.put(InterfaceMCSRunsBeforeNACS.class, new HashSet<AbstractMetaCognitiveModule>());
			Modules.get(InterfaceMCSRunsBeforeNACS.class).add(module);
		}
	}
	
	/**
	 * Adds the specified collection of modules to the MCS.
	 * @param mods The modules to add.
	 */
	public void addModules (Collection <? extends AbstractMetaCognitiveModule> mods)
	{
		for(AbstractMetaCognitiveModule a : mods)
			addModule(a);
	}
	
	/**
	 * Gets all of the modules in the MCS.
	 * @return A collection containing all of the modules in the MCS.
	 */
	public Collection <AbstractMetaCognitiveModule> getModules ()
	{
		HashSet <AbstractMetaCognitiveModule> m = new HashSet<AbstractMetaCognitiveModule> ();
		for(HashSet <AbstractMetaCognitiveModule> a : Modules.values())
			m.addAll(a);
		if(m.size() > 0)
			return m;
		else
			return null;
	}
	
	/**
	 * Gets the modules in the MCS that are of the specified class type.
	 * @param ID The class identifier for the interface related to the group of
	 * modules you wish to get.
	 * @return A collection containing the modules that implement the specified class in the MCS.
	 */
	public Collection <AbstractMetaCognitiveModule> getModules (Class <? extends InterfaceMCSRunTimes> ID)
	{
		if(Modules.containsKey(ID))
			return new HashSet<AbstractMetaCognitiveModule> (Modules.get(ID));
		else
			return null;
	}

	
	/**
	 * Attaches the MCS to the specified CLARION agent.
	 * @param Agent The agent to wish this MCS will be attached.
	 */
	protected void attachSelfToAgent(CLARION Agent) {
		Agent.attachMCS(this);
	}

	/**
	 * Performs the appropriate end of episode instructions for the MCS. This method is called by the
	 * CLARION class when its endEpisode method is called.
	 * @param Input A collection of various information to be used for ending the episode.
	 * @param TimeStamp The current time stamp.
	 */
	protected void endEpisode(DimensionValueCollection Input, long TimeStamp) {
		if(Modules.containsKey(InterfaceMCSRunsAtEpisodeEnd.class))
		{
			for(AbstractMetaCognitiveModule m : Modules.get(InterfaceMCSRunsAtEpisodeEnd.class))
				m.performMetaCognition(Input, TimeStamp);
		}
	}
}
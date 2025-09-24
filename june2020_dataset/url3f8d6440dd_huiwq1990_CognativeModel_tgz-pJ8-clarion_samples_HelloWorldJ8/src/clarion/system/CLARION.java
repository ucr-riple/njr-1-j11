package clarion.system;

import java.util.Collections;
import java.util.Collection;

import clarion.system.EpisodicMemory.InfoStored;


/**
 * This is the main class of the CLARION Library. All agents within a task environment must have
 * their own instance of this class.
 * <p>
 * <b>Usage:</b>
 * <p>
 * The CLARION class acts as the "brain" for a CLARION agent. The "brain" of this agent interacts with the task
 * environment using the "perceive" and "act" functions. If setup correctly, only these two methods should need
 * to be called during runtime (after the CLARION agent has been initialized).
 * <p>
 * The constructor of this class builds a "frame" from which all desired subsystems can be attached.
 * After a CLARION instance has been fully setup, only those subsystems that have been attached 
 * will be used when performing action-decision making.
 * <p>
 * Subsystems and intermediate modules (i.e. the working memory and goal structure) for CLARION must be attached 
 * in the following order:
 * <ol>
 * <li>MS</li>
 * <li>MCS</li>
 * <li>GoalStructure</li>
 * <li>ACS</li>
 * <li>NACS</li>
 * <li>WorkingMemory</li>
 * </ol>
 * <p>
 * Every instance of CLARION MUST at least have an ACS attached to it in order to operate
 * correctly. This is the MINIMUM requirement for setting up a CLARION agent. 
 * <p>
 * Also, keep in mind that each instance of CLARION needs to have its own instances of the subsystems
 * it uses. CLARION should NOT share instances of subsystems (or any other components within those
 * subsystems for that matter). Doing so would be like saying that two agents share the same brain or 
 * parts of the same brain. Also, any component that is given to an instance of CLARION MUST NOT be 
 * tied to another instance of CLARION.
 * <p>
 * While CLARION will automatically create its own internal representation for some components,
 * it is HIGHLY recommended that you create UNIQUE instances for EVERYTHING that is provided and/or
 * attached to a CLARION agent (with the notable exception of the sensory information).
 * <p>
 * Many of the components located within CLARION (including the subsystems) have options and parameters
 * (in the form of constants) that can be adjusted from their defaults as is deemed necessary by the task 
 * being performed. All parameters come in two varieties:<br>
 * <ul>
 * <li>Global (static) - setting the parameter globally changes the default value for ALL instances
 * of that component.*</li>
 * <li>Local - setting the parameter locally changes the default value for that particular instance
 * of the component ONLY.</li>
 * </ul>
 * <i>*If you want to set the parameters globally you MUST do so before ANY instances of that component
 * have been initialized.</i>
 * <p>
 * The parameters can be found within their appropriate components. If you are having trouble locating a
 * parameter, it is advised that you consult the documentation for assistance.
 * <p>
 * Requirements for using the CLARION Library:
 * <ul>
 * <li>A firm understanding of the Java programming language.</li>
 * <li>A functional understanding of the CLARION cognitive architecture (see Sun Tutorial, 2003 and 
 * related addendums).</li>
 * </ul>
 * @version 6.0.5
 * @author Nick Wilson
 */
public class CLARION {
	/**The Action Centered Subsystem.*/
	private ACS acs;
	/**The Non Action Centered Subsystem.*/
	private NACS nacs;
	/**The Motivational Subsystem.*/
	private MS ms;
	/**The Meta Cognitive Subsystem.*/
	private MCS mcs;
	/**The Goal Structure. Conceptually, this system exists on the top level of the MS. However,
	 * implementationally it is logical to specify it as an intermediate module since it is used
	 * by several subsystems.*/
	private GoalStructure gs;
	/**The Working Memory. Conceptually, this system exists between the ACS and the NACS. However,
	 * implementationally it is logical to specify it as an intermediate module since it is used
	 * by several subsystems.*/
	private WorkingMemory wm;
	/**The Episodic Memory. Conceptually, this system exists within the NACS. However,
	 * implementationally it is logical to specify it as an intermediate module since it is used
	 * by several subsystems.*/
	private EpisodicMemory em;
	
	/**A dimension-value collection that contains all of the dimension-value pairs of which
	 * this instance of CLARION is currently aware.*/
	protected DimensionValueCollection InputSpace;
	/**The current input (including perceived sensory information, current goal, and chunks in 
	 * working memory) for this instance of CLARION.*/
	private DimensionValueCollection CurrentInput;
	
	/**The action performed when the "act" method was last called*/
	private AbstractAction PerformedAction;
	/**The current goal*/
	private Goal CurrentGoal;
	
	/**The time-stamp taken from the internal-clock at the moment the CLARION agent in instantiated.*/
	private long Birthdate;
	/**The number of time-steps (incremented whenever an action is performed) that the agent has been alive.*/
	private long InternalClock;
	
	/**
	 * Minimally initializes a CLARION agent. This constructor builds a "frame" for this instance of
	 * CLARION from which all desired subsystems can be attached. After the CLARION instance
	 * has been fully setup, only those subsystems that have been attached to this CLARION
	 * instance will be used when performing action-decision making.
	 * @param SensoryInformationSpace A collection that contains all of the possible 
	 * dimension-values that can be specified as sensory information to be perceived by a CLARION
	 * agent.
	 */
	public CLARION (DimensionValueCollection SensoryInformationSpace)
	{
		Birthdate = System.currentTimeMillis();
		InternalClock = 0;
		InputSpace = new DimensionValueCollection ();
		updateInputSpace(SensoryInformationSpace.values());
	}
	
	/**
	 * Perceives the new sensory information and performs learning. 
	 * <p>
	 * If you are using the drive equation (or its equivalent) for calculating drive strengths within 
	 * the MS, the sensory information should also include a dimension with the ID "STIMULUS" that 
	 * contains the stimulus values for each of the drives. The stimulus for the drives is simply a 
	 * translation from the sensory information (or context) into a single value to be used 
	 * by the drive equation (etc.) for calculating the drive strength.
	 * @param NewSensoryInformation The new sensory information to be perceived.
	 * @throws MissingACSException If no instance of the ACS has been initialized.
	 */
	public void perceive (DimensionValueCollection NewSensoryInformation) throws MissingACSException
	{
		Long ts = Birthdate + InternalClock;
		if(acs == null)
			throw new MissingACSException ("This instance of CLARION does not contain an ACS. At " +
					"a MINIMUM, every instance of CLARION MUST contain an ACS.");
		DimensionValueCollection PreInput = generatePreInput(NewSensoryInformation);
		if(mcs != null)
		{
			Collection <AbstractMetaCognitiveModule> m = mcs.getModules(InterfaceMCSRunsAtPerception.class);
			if(m != null)
			{
				for(AbstractMetaCognitiveModule a : m)
				{
					if(a.checkEligibility())
						a.performMetaCognition(PreInput, ts);
				}
			}
		}
		if(gs != null)
			CurrentGoal = gs.getCurrentGoal();
		DimensionValueCollection NewInput = generateCurrentInput(NewSensoryInformation);
		if(em != null)
		{
			em.add(NewInput, ts, EpisodicMemory.InfoStored.NEW_STATE);
			em.addFeedback(PerformedAction.getFinalSelectionMeasure(), ts);
		}
		if(mcs != null)
		{
			Collection <AbstractMetaCognitiveModule> m = mcs.getModules(InterfaceMCSRunsAfterEarlyPerceptionStage.class);
			if(m != null)
			{
				for(AbstractMetaCognitiveModule a : m)
				{
					if(a.checkEligibility())
						a.performMetaCognition(NewInput, ts);
				}
			}
		}
		acs.learn(NewInput, ts);
		CurrentInput = NewInput;
		if(mcs != null)
		{
			Collection <AbstractMetaCognitiveModule> m = mcs.getModules(InterfaceMCSRunsAfterLearning.class);
			if(m != null)
			{
				for(AbstractMetaCognitiveModule a : m)
				{
					if(a.checkEligibility())
						a.performMetaCognition(CurrentInput, ts);
				}
			}
		}
		++InternalClock;
	}
	
	/**
	 * Perceives the new sensory information as well as any feedback that is to be received
	 * for performing the last action. In addition, this method performs learning based on the
	 * feedback. 
	 * <p>
	 * If you are using the drive equation (or its equivalent) for calculating drive strengths within 
	 * the MS, the sensory information should also include a dimension with the ID "STIMULUS" that 
	 * contains the stimulus values for each of the drives. The stimulus for the drives is simply a 
	 * translation from the sensory information (or context) into a single value to be used 
	 * by the drive equation (etc.) for calculating the drive strength.
	 * @param NewSensoryInformation The new sensory information to be perceived.
	 * @param feedback The feedback received for performing the last action.
	 * @throws MissingACSException If no instance of the ACS has been initialized.
	 */
	public void perceive (DimensionValueCollection NewSensoryInformation, double feedback) throws MissingACSException
	{
		Long ts = Birthdate + InternalClock;
		if(acs == null)
			throw new MissingACSException ("This instance of CLARION does not contain an ACS. At " +
					"a MINIMUM, every instance of CLARION MUST contain an ACS.");
		DimensionValueCollection PreInput = generatePreInput(NewSensoryInformation);
		if(mcs != null)
		{
			Collection <AbstractMetaCognitiveModule> m = mcs.getModules(InterfaceMCSRunsAtPerception.class);
			if(m != null)
			{
				for(AbstractMetaCognitiveModule a : m)
				{
					if(a.checkEligibility())
						a.performMetaCognition(PreInput, ts);
				}
			}
		}
		if(gs != null)
			CurrentGoal = gs.getCurrentGoal();
		DimensionValueCollection NewInput = generateCurrentInput(NewSensoryInformation);
		if(em != null)
		{
			em.add(NewInput, ts, EpisodicMemory.InfoStored.NEW_STATE);
			em.addFeedback(feedback, ts);
		}
		if(mcs != null)
		{
			Collection <AbstractMetaCognitiveModule> m = mcs.getModules(InterfaceMCSRunsAfterEarlyPerceptionStage.class);
			if(m != null)
			{
				for(AbstractMetaCognitiveModule a : m)
				{
					if(a.checkEligibility())
						a.performMetaCognition(NewInput, ts);
				}
			}
		}
		acs.learn(NewInput, feedback, ts);
		CurrentInput = NewInput;
		if(mcs != null)
		{
			Collection <AbstractMetaCognitiveModule> m = mcs.getModules(InterfaceMCSRunsAfterLearning.class);
			if(m != null)
			{
				for(AbstractMetaCognitiveModule a : m)
				{
					if(a.checkEligibility())
						a.performMetaCognition(CurrentInput, ts);
				}
			}
		}
		++InternalClock;
	}
	
	/**
	 * Determines the action to perform given what was perceived. If this instance of CLARION
	 * does not contain an ACS or no sensory information has been perceived (by calling the perceive
	 * method) this method will throw an exception.
	 * @return The performed action.
	 * @throws MissingACSException If no instance of the ACS has been initialized.
	 * @throws MissingSensoryInformationException If no sensory information has been perceived.
	 */
	public AbstractAction act ()  throws MissingACSException, MissingSensoryInformationException
	{
		Long ts = Birthdate + InternalClock;
		if(acs == null)
			throw new MissingACSException ("This instance of CLARION does not contain an ACS. At " +
					"at MINIMUM, every instance of CLARION MUST contain an ACS.");
		
		if(CurrentInput == null)
			throw new MissingSensoryInformationException ("Sensory Information has not been provided to " +
					"this CLARION Agent. Please provide the agent with sensory information by calling the " +
					"perceive method before calling the act method.");
		
		if(mcs != null)
		{
			Collection <AbstractMetaCognitiveModule> m = mcs.getModules(InterfaceMCSRunsBeforeACS.class);
			if(m != null)
			{
				for(AbstractMetaCognitiveModule a : m)
				{
					if(a.checkEligibility())
						a.performMetaCognition(CurrentInput, ts);
				}
			}
		}
		
		AbstractAction ChosenAction = acs.chooseAction(CurrentInput, ts);
		if(em != null)
		{
			em.add(CurrentInput, ts, InfoStored.CURRENT_STATE);
			em.add(ChosenAction.clone(), ts, InfoStored.PERFORMED_ACTION);
		}
		if(ms != null)
		{
			Collection <Drive> dc = ms.getAllDrives();
			if(dc != null)
			{
				for(Drive d : dc)
					d.updateDeficit();
			}
		}
		if(mcs != null)
		{
			Collection <AbstractMetaCognitiveModule> m = mcs.getModules(InterfaceMCSRunsAfterACS.class);
			if(m != null)
			{
				for(AbstractMetaCognitiveModule a : m)
				{
					if(a.checkEligibility())
						a.performMetaCognition(CurrentInput, ts);
				}
			}
		}
		if(em != null)
		{
			ChosenAction = em.getPerformedAction(ts);
			ChosenAction.setActivation(PerformedAction.FULL_ACTIVATION_LEVEL);
			PerformedAction = acs.getPossibleActions().get(ChosenAction.getID());
		}
		else
			PerformedAction = ChosenAction;
		PerformedAction.addTimeStamp(ts);
		for(Dimension d : CurrentInput.values())
		{
			if(d.getNumActivatedVals() > 0)
			{
				Dimension ind = InputSpace.get(d.getID());
				ind.addTimeStamp(ts);
				for(Value v: d.values())
				{
					if(v.isActivated())
					{
						ind.get(v.getID()).addTimeStamp(ts);
					}
				}
			}
		}
		return PerformedAction;
	}
	
	/**
	 * Generates the pre-input to use inside of CLARION for meta-cognition performed in the
	 * early stages of perception.
	 * @param NewSensoryInformation The new sensory information being perceived.
	 * @return The pre-input for use within CLARION.
	 */
	private DimensionValueCollection generatePreInput (DimensionValueCollection NewSensoryInformation)
	{
		DimensionValueCollection preInput = NewSensoryInformation.clone();
		if(ms != null)
		{
			preInput.putAll(ms.calculateAllDriveStrengths(NewSensoryInformation).toDimensionValueCollection());
		}
		return preInput;
	}
	
	/**
	 * Generates the current input to use inside of CLARION given the current sensory information,
	 * the current goal, the episodic memory, the MS, and the items in working memory.
	 * @param CurrentSensoryInformation The current sensory information being perceived.
	 * @return The current input for use within CLARION.
	 */
	private DimensionValueCollection generateCurrentInput (DimensionValueCollection CurrentSensoryInformation)
	{
		DimensionValueCollection current = CurrentSensoryInformation.clone();
		//Populate the current input with the current goal from the goal structure.
		if(gs != null && gs.getCurrentGoal() != null)
		{
			for(Dimension d : gs.getCurrentGoal().values())
			{
				if(current.containsKey(d.getID()))
				{
					Dimension cd = current.get(d.getID());
					for(Value v : d.values())
					{
						if(cd.containsKey(v.getID()))
							cd.get(v.getID()).setActivation(v.getActivation());
						else
							cd.put(v.getID(), v.clone());
					}
				}
				else
					current.put(d.getID(), d.clone());
			}
		}
		//Populate the current input with any chunks in working memory. For all chunks in
		//working memory that contain sensory information, the activations of the 
		//dimension-value pairs from those chunks will only replace the activations of the 
		//corresponding dimension-value pairs in the current input if those activations are less
		//than the normalized activations of the dimension-value pairs in working memory.
		if(wm != null)
		{
			for(GenericChunkCollection gc : wm.get(WorkingMemory.InfoStored.FROM_NACS))
			{
				for(AbstractChunk c : gc.values())
				{
					for(Dimension d : c.values())
					{
						if(current.containsKey(d.getID()))
						{
							Dimension cd = current.get(d.getID());
							for(Value v : d.values())
							{
								double act = v.getActivation() * InputSpace.get(d.getID()).get(v.getID()).getNormalizedBLA(Birthdate + InternalClock);
								Value cv = cd.get(v.getID());
								if(cv == null)
								{
									Value nv = v.clone();
									nv.setActivation(act);
									cd.put(nv.getID(),nv);
								}
								else if (cv.getActivation() < act)
									cv.setActivation(act);
							}
						}
						else
							current.put(d.getID(), d.clone());
					}
				}
			}
			for(GenericChunkCollection gc : wm.get(WorkingMemory.InfoStored.GENERIC_CHUNKS))
			{
				for(AbstractChunk c : gc.values())
				{
					for(Dimension d : c.values())
					{
						if(current.containsKey(d.getID()))
						{
							Dimension cd = current.get(d.getID());
							for(Value v : d.values())
							{
								double act = v.getActivation() * InputSpace.get(d.getID()).get(v.getID()).getNormalizedBLA(Birthdate + InternalClock);
								Value cv = cd.get(v.getID());
								if(cv == null)
								{
									Value nv = v.clone();
									nv.setActivation(act);
									cd.put(nv.getID(),nv);
								}
								else if (cv.getActivation() < act)
									cv.setActivation(act);								
							}
						}
						else
							current.put(d.getID(), d.clone());
					}
				}
			}
		}
		//Populate the current input with the episodic memory. For all state/action pairs in
		//the episodic memory that contain sensory information, the activations of the 
		//dimension-value pairs from episodic memory will only replace the activations of the 
		//corresponding dimension-value pairs in the current input if those activations are less
		//than the normalized activations of the dimension-value pairs in episodic memory.
		if(em != null)
		{
			DimensionValueCollection c = em.getAllAsDimensionValueCollection();
			if(c != null)
			{
				for(Dimension d : c.values())
				{
					if(current.containsKey(d.getID()))
					{
						Dimension cd = current.get(d.getID());
						for(Value v : d.values())
						{
							double act = v.getActivation() * InputSpace.get(d.getID()).get(v.getID()).getNormalizedBLA(Birthdate + InternalClock);
							Value cv = cd.get(v.getID());
							if(cv == null)
							{
								Value nv = v.clone();
								nv.setActivation(act);
								cd.put(nv.getID(),nv);
							}
							else if (cv.getActivation() < act)
								cv.setActivation(act);
						}
					}
					else
						current.put(d.getID(), d.clone());
				}
			}
		}
		
		if(ms != null)
		{
			current.putAll(ms.calculateAllDriveStrengths(CurrentSensoryInformation).toDimensionValueCollection());
		}
		return current;
	}
	
	/**
	 * Attaches the goal structure to this instance of CLARION. This method is called by the 
	 * GoalStructure class when it is first initialized. When the GoalStructure class is initialized 
	 * it will automatically attach itself to this instance of CLARION. Conceptually, this can be 
	 * conceived of as the goal structure "growing out of" the CLARION agent.
	 * @param GS The goal structure to be attached.
	 */
	protected void attachGoalStructure (GoalStructure GS)
	{
		gs = GS;
	}
	
	/**
	 * Attaches the working memory to this instance of CLARION. This method is called by the WorkingMemory 
	 * class when it is first initialized. When the WorkingMemory class is initialized it will
	 * automatically attach itself to this instance of CLARION. Conceptually, this can be conceived
	 * of as the working memory "growing out of" the CLARION agent.
	 * @param WM The working memory to be attached.
	 */
	protected void attachWorkingMemory (WorkingMemory WM)
	{
		wm = WM;
		acs.WM = WM;
	}
	
	/**
	 * Attaches the episodic memory to this instance of CLARION. This method is called by the 
	 * EpisodicMemory class when it is first initialized. When the EpisodicMemory class is initialized 
	 * it will automatically attach itself to this instance of CLARION. Conceptually, this can be 
	 * conceived of as the episodic memory "growing out of" the CLARION agent.
	 * @param EM The episodic memory to be attached.
	 */
	protected void attachEpisodicMemory (EpisodicMemory EM)
	{
		em = EM;
	}
	
	/**
	 * Attaches the action centered subsystem to this instance of CLARION. This method is called by 
	 * the ACS class when it is first initialized. When the ACS class is initialized it will
	 * automatically attach itself to this instance of CLARION. Conceptually, this can be conceived
	 * of as the ACS "growing out of" the CLARION agent.
	 * @param Acs The action centered subsystem to be attached.
	 */
	protected void attachACS (ACS Acs)
	{
		acs = Acs;
	}
	
	/**
	 * Attaches the motivational subsystem to this instance of CLARION. This method is called by 
	 * the MS class when it is first initialized. When the MS class is initialized it will
	 * automatically attach itself to this instance of CLARION. Conceptually, this can be conceived
	 * of as the MS "growing out of" the CLARION agent.
	 * @param Ms The motivational subsystem to be attached.
	 */
	protected void attachMS (MS Ms)
	{
		ms = Ms;
	}
	
	/**
	 * Attaches the meta-cognitive subsystem to this instance of CLARION. This method is called by 
	 * the MCS class when it is first initialized. When the MCS class is initialized it will
	 * automatically attach itself to this instance of CLARION. Conceptually, this can be conceived
	 * of as the MCS "growing out of" the CLARION agent.
	 * @param Mcs The meta-cognitive subsystem to be attached.
	 */
	protected void attachMCS (MCS Mcs)
	{
		mcs = Mcs;
	}
	
	/**
	 * Attaches the non-action centered subsystem to this instance of CLARION. This method is called by 
	 * the NACS class when it is first initialized. When the NACS class is initialized it will
	 * automatically attach itself to this instance of CLARION. Conceptually, this can be conceived
	 * of as the NACS "growing out of" the CLARION agent.
	 * @param Nacs The non-action centered subsystem to be attached.
	 */
	protected void attachNACS (NACS Nacs)
	{
		nacs = Nacs;
	}
	
	/**
	 * Gets the ACS.
	 * @return the ACS.
	 */
	public ACS getACS ()
	{
		return acs;
	}
	
	/**
	 * Gets the MS.
	 * @return the MS.
	 */
	public MS getMS ()
	{
		return ms;
	}
	
	/**
	 * Gets the MCS.
	 * @return the MCS.
	 */
	public MCS getMCS ()
	{
		return mcs;
	}
	
	/**
	 * Gets the NACS.
	 * @return the NACS.
	 */
	public NACS getNACS ()
	{
		return nacs;
	}
	
	/**
	 * Gets the goal structure.
	 * @return the goal structure.
	 */
	public GoalStructure getGoalStructure ()
	{
		return gs;
	}
	
	/**
	 * Gets the working memory.
	 * @return the working memory.
	 */
	public WorkingMemory getWorkingMemory ()
	{
		return wm;
	}
	
	/**
	 * Gets the episodic memory.
	 * @return the episodic memory.
	 */
	public EpisodicMemory getEpisodicMemory ()
	{
		return em;
	}
	
	/**
	 * Gets the internal representation of the input space from this instance of CLARION. This method
	 * is used by the subsystems (and intermediate modules) as part of attaching themselves to an instance
	 * of CLARION.
	 * @return The internal input space.
	 */
	protected DimensionValueCollection getInputSpace ()
	{
		return InputSpace;
	}
	
	/**
	 * Gets the internal representation of the input space from this instance of CLARION. The 
	 * collection returned is unmodifiable and is meant for reporting the internal state only.
	 * @return An unmodifiable collection containing this instance of CLARION's internal
	 * representation of the input space.
	 */
	public Collection <Dimension> getInternalInputSpace ()
	{
		return Collections.unmodifiableCollection(InputSpace.values());
	}
	
	/**
	 * Gets the last action performed by this instance of CLARION.
	 * @return The last action performed.
	 */
	public AbstractAction getLastAction ()
	{
		return PerformedAction;
	}
	
	/**
	 * Gets the current goal. If goals are not being used by this instance of CLARION
	 * then this method will return null.
	 * @return The current goal.
	 */
	public Goal getCurrentGoal ()
	{
		if(gs == null || (CurrentGoal == null && gs.getCurrentGoal() == null))
			return null;
		else
			return gs.getCurrentGoal();
	}
	
	/**
	 * Resets the last performed action.
	 */
	public void resetLastAction ()
	{
		if(em != null)
			em.remove(PerformedAction, EpisodicMemory.InfoStored.PERFORMED_ACTION);
		PerformedAction = null;
		acs.resetChosenAction();
	}
	
	/**
	 * Resets the current goal.
	 */
	public void resetGoal ()
	{
		CurrentGoal = null;
		if(gs != null)
			gs.clear();
	}
	
	/**
	 * Gets the age (the number of time steps since this instance of CLARION was initialized)
	 * of the agent.
	 * @return The age of the agent.
	 */
	public long getAge ()
	{
		return InternalClock;
	}
	
	/**
	 * Gets the birthdate (the time stamp when this instance of CLARION was initialized)
	 * of the agent.
	 * @return The birthdate of the agent.
	 */
	public long getBirthdate ()
	{
		return Birthdate;
	}
	
	/**
	 * Marks the end of an "episode". The CLARION agent will perform a series of 
	 * internal instructions related to ending an episode when this method is called. Calling
	 * this method does not increment the age of the agent and is therefore considered to be
	 * a process that occurs pseudo-automatically without needing the agent to expend time to
	 * perform.
	 */
	public void endEpisode ()
	{
		Long ts = Birthdate + InternalClock;
		ms.endEpisode(CurrentInput, ts);
		mcs.endEpisode(CurrentInput, ts);
		acs.endEpisode(CurrentInput, Birthdate + InternalClock);
		nacs.endEpisode(CurrentInput, ts);
	}
	
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
				InputSpace.put(inputdim.getID(), inputdim);
			}
			else
			{
				for(Value v : d.values())
				{
					if(!inputdim.containsKey(v.getID()))
					{
						Value av = v.clone();
						av.setActivation(av.FULL_ACTIVATION_THRESHOLD);
						inputdim.put(av.getID(), av);
					}
				}
			}
		}
	}
}

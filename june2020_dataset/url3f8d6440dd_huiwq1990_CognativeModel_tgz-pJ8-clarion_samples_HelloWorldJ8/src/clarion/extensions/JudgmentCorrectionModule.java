package clarion.extensions;

import clarion.system.*;

import java.util.EnumMap;

/**
 * This class implements a judgment correction module within CLARION. It extends AbstractMetaCognitiveModule and 
 * implements InterfaceMCSRunsAfterACS. 
 * <p>
 * <b>Usage:</b>
 * <p>
 * The judgment correction module is a domain-specific module used to make meta-cognitive adjustments to ratings (made
 * by the ACS) of declarative information (e.g., persons/places/things) sensed as part of sensory input. A judgment
 * correction occurs when an agent is made aware that a previous rating of a piece of declarative information (as stored in 
 * episodic memory) is affecting their judgment of a current piece of declarative information. This
 * awareness results in the setting of a goal to try to correct for this supposed bias. In CLARION, it is posited that 
 * this correction would occur as a meta-cognitive process following action decision-making.
 * <p>
 * This module can be optionally added to the MCS if the user wishes to have the MCS handle judgment correction. 
 * This module uses an implicit module to determine how to perform a judgment correction. The user can decide, during 
 * initialization, the implicit module they wish to use.
 * <p>
 * NOTE:
 * <ul>
 * <li>To use this module, several components of the system must be setup in a specific fashion: <ol>
 * <li>The input space of the implicit module of this meta-cognitive module must have two dimensions with the IDs: "PRIMER"
 * and "TARGET" (of the enumerated type RequiredInputDimensions).</li>
 * <li>Any actions in the ACS that you want to be eligible for judgment correction MUST contain a dimension with the ID
 * "RATING" (of the enumerated type RequiredActionDimensions).</li>
 * <li>The values contained within the dimensions of both the input space (see 1 above) and the eligible actions
 * (see 2 above) MUST have the same IDs. These IDs MUST be of a type that can be parsed into the primitive type double 
 * (e.g., Integer*, Float, a numeric String, etc.).</li>
 * <i>* recommended</i>
 * </ol></li>
 * <li>After construction, but before this module can be used, the user MUST specify the following:<ul>
 * <li>The instance of the GoalStructure that this module is to act upon. Failure to attach a goal structure to this 
 * module (using the attachGoalStructure method) during initialization will result in an exception being thrown 
 * during runtime.</li>
 * <li>The instance of episodic memory that this module is to act upon. Failure to attach the episodic memory to this 
 * module (using the attachEpisodicMemory method) during initialization will result in an exception being thrown 
 * during runtime.</li>
 * <li>If the episodic memory has more than 1 slot (i.e., it is tracking more than one time-step), the 
 * "RATING" value for the "PRIMER" dimension in the required inputs for this module will be set to the average
 * of all actions in the episodic memory that contain the "RATING" dimension (see above).</li>
 * </ul></li>
 * </ul>
 * If this module is initialized without specifying an implicit module to use, the JudgmentCorrectionEquation 
 * will be used.
 * @version 6.0.6
 * @author Nick Wilson
 */
public class JudgmentCorrectionModule extends AbstractMetaCognitiveModule
		implements InterfaceMCSRunsAfterACS {
	
	/**The enumerator to use for the IDs of the dimensions that are required for the input of this module.*/
	public enum RequiredInputDimensions {PRIMER, TARGET};
	/**The enumerator to use for the IDs of the values within the input dimensions of this module. Additionally,
	 * any actions in the ACS that are eligible for judgment correction MUST contain a dimension with an ID of
	 * this enumerated type.*/
	public enum RequiredActionDimensions {RATING};

	/**The episodic memory.*/
	private EpisodicMemory EM;
	/**The goal structure.*/
	private GoalStructure GS;
	
	/**The target goal that indicates that this module is eligible for use.*/
	private Goal TargetGoal;
	
	/**The stochastic selector for action correction.*/
	public StochasticSelector Selector = new StochasticSelector();
	
	/**How close two objects of primitive type double must be in order to be considered equal. 
	 * Needed due to the nature of double precision arithmetic.*/
	public static double GLOBAL_EPSILON = .0001;
	
	/**
	 * Initializes the judgment correction module using the judgment correction equation.
	 * <p>
	 * To use this module, several components of the system must be setup in a specific fashion:
	 * <ol>
	 * <li>The input space of this meta-cognitive module must have two dimensions with the IDs: "PRIMER"
	 * and "TARGET" (of the enumerated type RequiredInputDimensions).</li>
	 * <li>Any actions in the ACS that you want to be eligible for judgment correction MUST contain a dimension with the ID
	 * "RATING" (of the enumerated type RequiredActionDimensions).</li>
	 * <li>The values contained within the dimensions of both the input space (see 1 above) and the eligible actions
	 * (see 2 above) MUST have the same IDs. These IDs MUST be of a type that can be parsed into the primitive type double 
	 * (e.g., Integer*, Float, a numeric String, etc.).</li>
	 * <i>* recommended</i>
	 * </ol>
	 * @param ModuleInputSpace A dimension-value collection that contains "PRIMER" and "TARGET" dimensions.
	 * @param Actions A collection of all the actions that are eligible to be considered for judgment correction.
	 * @param Target The target goal that indicates that a judgment correction needs to be performed.
	 */
	public JudgmentCorrectionModule (DimensionValueCollection ModuleInputSpace, ActionCollection Actions, Goal Target)
	{
		super(new JudgmentCorrectionEquation(ModuleInputSpace.values(), Actions));
		TargetGoal = Target;
	}
	
	/**
	 * Initializes the judgment correction module given the implicit module specified.
	 * <p>
	 * To use this module, several components of the system must be setup in a specific fashion: <ol>
	 * <li>The input space of the specified implicit module must have two dimensions with the IDs: "PRIMER"
	 * and "TARGET" (of the enumerated type RequiredInputDimensions).</li>
	 * <li>Any actions in the ACS that you want to be eligible for judgment correction MUST contain a dimension with the ID
	 * "RATING" (of the enumerated type RequiredActionDimensions).</li>
	 * <li>The values contained within the dimensions of both the input space (see 1 above) and the eligible actions
	 * (see 2 above) MUST have the same IDs. These IDs MUST be of a type that can be parsed into the primitive type double 
	 * (e.g., Integer*, Float, a numeric String, etc.).</li>
	 * <i>* recommended</i>
	 * <li>The specified implicit module MUST have outputs for all of the possible actions that 
	 * are to be involved in judgment correction.</li>
	 * </ol>
	 * @param im The implicit module to use for judgment correction.
	 * @param Target The target goal for judgment correction eligibility.
	 */
	public JudgmentCorrectionModule (AbstractImplicitModule im, Goal Target)
	{
		super(im);
		TargetGoal = Target;
	}

	/**
	 * Performs a judgment correction on the action chosen by the ACS (and stored in episodic memory)
	 * before it is performed by the CLARION agent.
	 * @param ModuleInput The input for the judgment correction module.
	 * @param TimeStamp The current time stamp.
	 * @throws MissingGoalStructureException If no instance of the goal structure has been attached to this module.
	 * @throws MissingEpisodicMemoryException If no instance of the episodic memory has been attached to this module.
	 */
	public void performMetaCognition(DimensionValueCollection ModuleInput, long TimeStamp)  throws MissingGoalStructureException, MissingEpisodicMemoryException
	{
		if(GS == null || GS.getPossibleGoals() == null)
			throw new MissingGoalStructureException ("The Judgment Correction Module does not have a goal structure specified. " +
					"To specify a goal structure for this module, use the attachGoalStructure method.");
		if(EM == null)
			throw new MissingEpisodicMemoryException ("The Judgment Correciton Module does not have an episodic memory specified. " +
					"To specify an episodic memory for this module, use the attachEpisodicMemory method.");
		
		DimensionValueCollection Acts = new DimensionValueCollection ();
		
		int count = 0;
		double sum = 0;
		for(EnumMap<EpisodicMemory.InfoStored, DimensionValueCollection> e : EM.values())
		{
			AbstractAction a = (AbstractAction)e.get(EpisodicMemory.InfoStored.PERFORMED_ACTION);
			if(a.containsKey(RequiredActionDimensions.RATING))
			{
				++count;
				for(Value v : a.get(RequiredActionDimensions.RATING).values())
				{
					if(v.isFullyActivated())
					{
						sum += Double.parseDouble(v.getID().toString());
						break;
					}
				}
			}
		}
		
		sum = Math.round(sum/count);
		
		AbstractAction Rp = null;
		for(AbstractOutputChunk o : ImplicitModule.getOutput())
		{
			boolean check = false;
			for(Value v : o.get(JudgmentCorrectionModule.RequiredActionDimensions.RATING).values())
			{
				double dv = Double.parseDouble(v.getID().toString());
				if(v.isFullyActivated() && ((v.getID() instanceof Integer && v.equals((int)sum)) 
						|| Math.abs(dv - sum) <= GLOBAL_EPSILON))
				{
					check = true;
					break;
				}
			}
			
			if(check)
			{
				Rp = (AbstractAction)o;
				break;
			}
		}
		
		if(Rp == null)
			Rp = (AbstractAction)EM.get((Object)(TimeStamp-1)).get(EpisodicMemory.InfoStored.PERFORMED_ACTION);
		Dimension p = new Dimension(RequiredInputDimensions.PRIMER);
		p.putAll(Rp.get(RequiredActionDimensions.RATING));
		Acts.put(p.getID(), p);
		
		AbstractAction Rt = (AbstractAction)EM.get((Object)TimeStamp).get(EpisodicMemory.InfoStored.PERFORMED_ACTION);
		Dimension t = new Dimension(RequiredInputDimensions.TARGET);
		t.putAll(Rt.get(RequiredActionDimensions.RATING));
		Acts.put(t.getID(), t);
		
		ImplicitModule.setInput(Acts);
		ImplicitModule.forwardPass();
		
		for(AbstractOutputChunk a : ImplicitModule.getOutput())
		{
			a.setBLSelectionMeasure(a.getActivation());
		}
		
		EM.get((Object)TimeStamp).put(EpisodicMemory.InfoStored.PERFORMED_ACTION, (AbstractAction)Selector.select(ImplicitModule.getOutput()));
	}
	
	/**
	 * Attaches the specified episodic memory to this module.
	 * <p>
	 * NOTE: The episodic memory specified MUST be the SAME episodic memory
	 * that is contained within the CLARION agent that intends to use this module.
	 * @param em The episodic memory to attach.
	 */
	public void attachEpisodicMemory (EpisodicMemory em)
	{
		EM = em;
	}
	
	/**
	 * Attaches the specified goal structure to this module.
	 * <p>
	 * NOTE: The goal structure specified MUST be the SAME goal structure
	 * that is contained within the CLARION agent that intends to use this module.
	 * @param gs The goal structure to attach.
	 */
	public void attachGoalStructure (GoalStructure gs)
	{
		GS = gs;
	}
	
	/**
	 * Checks to see if the judgment correction module is eligible to be used based on the target goal.
	 * @return True if the current goal equals the target goal.
	 */
	public boolean checkEligibility()
	{
		if(TargetGoal == null)
			return false;
		return TargetGoal.equalsID(GS.getCurrentGoal().getID());
	}
}

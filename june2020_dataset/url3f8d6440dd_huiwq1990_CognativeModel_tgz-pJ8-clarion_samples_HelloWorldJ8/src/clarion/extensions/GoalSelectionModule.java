package clarion.extensions;

import clarion.system.*;

/**
 * This class implements a goal selection module within CLARION. It extends AbstractMetaCognitiveModule and implements 
 * InterfaceMCSRunsAtPerception.
 * <p>
 * <b>Usage:</b>
 * <p>
 * This module can be optionally added to the MCS if the user wishes to have the MCS handle goal setting. 
 * This module uses an implicit module to determine the goal activation level. 
 * The user can decide, during initialization, the implicit module they wish to use.
 * <p>
 * NOTE: After construction, but before this module can be used, the user MUST specify the instance of the GoalStructure
 * that this module is to act upon. Failure to attach a GoalStructure to this module (using the attachGoalStructure method)
 * during initialization will result in an exception being thrown during runtime.
 * <p>
 * If this module is initialized without specifying an implicit module to use, the GoalSelectionEquation 
 * will be used.
 * @version 6.0.6
 * @author Nick Wilson
 */
public class GoalSelectionModule extends AbstractMetaCognitiveModule implements InterfaceMCSRunsAtPerception{

	/**The goal structure.*/
	private GoalStructure GS;
	/**The stochastic selector to use for goal selection.*/
	public StochasticSelector SELECTOR = new StochasticSelector();
	
	/**
	 * Initializes the goal selection module to use the goal selection equation. The goal selection equation 
	 * will select from the specified goals using the specified drive strengths.
	 * <p>
	 * NOTE: The drive strengths and goals specified MUST be the SAME drive strengths / goals
	 * that are contained within the CLARION agent that intends to use this module.
	 * @param RelevantDrives The drive strengths for the relevant drives used to make goal selections.
	 * @param Goals The goals from which to select.
	 */
	public GoalSelectionModule (DriveStrengthCollection RelevantDrives, GoalCollection Goals)
	{
		super(new GoalSelectionEquation (RelevantDrives.toDimensionValueCollection().values(), Goals));
	}
	
	/**
	 * Initializes the goal selection module given the specified implicit module.
	 * <p>
     * Note that the specified implicit module MUST have output chunks each of 
     * which is a possible goals in the goal structure. The goal selection implicit module will 
     * only select over the possible goals that are specified as an output chunk on the output 
     * of the specified implicit module.
     * <p>
     * If the goal selection implicit module contains any output nodes that are of a type other than Goal, 
     * this constructor will throw an exception.
	 * @param im The goal selection implicit module to set for this module.
	 * @throws InvalidFormatException If the output layer contains nodes of a type other than Goal.
	 */
	public GoalSelectionModule (AbstractImplicitModule im) throws InvalidFormatException
	{
		super(im);
		for(AbstractOutputChunk o : im.getOutput())
			if(!(o instanceof Goal))
				throw new InvalidFormatException ("The output layer of the specified implicit module " +
						"contains one or more nodes that are of a type other than Goal.");
	}
	
	/**
	 * Attaches the specified goal structure to this module.
	 * <p>
	 * NOTE: The goal structure specified MUST be the SAME goal structure
	 * that is contained within the CLARION agent that intends to use this module.
	 * @param gs The goal structure.
	 */
	public void attachGoalStructure (GoalStructure gs)
	{
		GS = gs;
	}

	/**
	 * Selects a goal from all of the possible goals tied to the output layer of implicit module in this 
	 * module using the specified collection of drive strengths.
	 * <p>
	 * If a new goal is selected that is different than the currently chosen goal then the currently chosen 
	 * goal is removed from the goal structure and the new goal is set as the currently chosen goal. If the goal 
	 * structure has not been initialized or does not contain any possible goals, this method does nothing.
	 * @param ModuleInput The drive strengths of relevant drives.
	 * @param TimeStamp The current time stamp.
	 * @throws MissingGoalStructureException If no instance of the goal structure has been attached to this module.
	 */
	public void performMetaCognition(DimensionValueCollection ModuleInput, long TimeStamp)  throws MissingGoalStructureException
	{
		if(GS == null || GS.getPossibleGoals() == null)
			throw new MissingGoalStructureException ("The Goal Selection Module does not have a goal structure specified or " +
					"the goal structure does not contain a list of possible goals over which to select. " +
					"To specify a goal structure for this module, use the attachGoalStructure method. To add possible goals " +
					"to the goal structure, use the addPossibleGoal methods in the GoalStructure class.");
		
		ImplicitModule.setInput(ModuleInput);
		ImplicitModule.forwardPass();
		
		if(GS.getCurrentGoal() != null)
		{
			for(AbstractOutputChunk g : ImplicitModule.getOutput())
			{
				Goal pg = GS.getPossibleGoals().get(g.getID());
				pg.setActivation(g.getActivation());
				if(GS.getCurrentGoal().equalsID(g))
					pg.setBLSelectionMeasure(g.adjustSelectionMeasure(g.getActivation(), g.FULL_ACTIVATION_LEVEL));
				else
					pg.setBLSelectionMeasure(g.adjustSelectionMeasure(g.getActivation(), g.MINIMUM_ACTIVATION_THRESHOLD));
			}
			GS.remove(GS.getCurrentGoal());
		}
		Goal newgoal = (Goal)SELECTOR.select(GS.getPossibleGoals().values());
		GS.add(newgoal,TimeStamp);
	}
}

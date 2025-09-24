package sim;

import clarion.extensions.*;
import clarion.system.*;
import clarion.tools.BaseSimulationTemplate;

import java.util.ArrayList;

/**
 * This is a sample simulation running a very simple task we call the "Hello World" task. This
 * simulation is meant to demonstrate how the CLARION library can be setup. It uses mostly default
 * settings, so it should not be considered as being the definitive method for setting up a simulation
 * using the CLARION Library. It is, however, a good place to start for learning how to setup a 
 * simulation.
 * <p>
 * <u>Overview of the task:</u><br>
 * This task simulates the "Hello World" task. It uses a single agent who's job is to respond either 
 * "hello" or "goodbye" based on the salutation you give it and the salutation it has the goal to 
 * wants to give. The objective is for the agent to respond with the same salutation as the salutation it is 
 * given.
 * <p>
 * The agent has been setup using the following:
 * <ul>
 * <li>The Motivational Subsystem (MS) using the GENERIC personality and the drive strength equation 
 * (without the neural net). However, only the deficits for AFFILIATION_AND_BELONGINGNESS and 
 * AUTONOMY are specified.</li>
 * <li>The Meta Cognitive Subsystem (MCS) using the goal selecting module and the ACS level 
 * probability setting module (both using the equation, not a neural net).</li>
 * <li>The Action Centered Subsystem (ACS) with a neural network in the bottom level and RER 
 * turned on.<br>
 * The neural network uses simplified Q-learning with backpropagation and has been setup as follows:
 * </li>
 * <ul><li>Four inputs - </li>
 * <ul><li>Desired_Salutation: Hello (internally set by a goal)</li>
 * <li>Desired_Salutation: Goodbye (internally set by a goal)</li>
 * <li>Salutation: Hello (from the outside world)</li>
 * <li>Salutation: Goodbye (from the outside world)</li></ul>
 * <li>Two outputs -</li>
 * <ul><li>Hello</li>
 * <li>Goodbye</li></ul></ul>
 * </ul>
 * <p>
 * In addition, there are also a few optional settings. These options are:
 * <ul>
 * <li> Turning on the benefit equation (in initializeAgentInternalSpace)</li>
 * <li> Specifying a thresholds for generalization and specialization (in initializeCLARIONAgent)</li>
 * <li> Specifying the information gain measure to use (in initializeCLARIONAgent)</li>
 * <li> Adjusting the learning-rate and momentum parameters for the neural network in the ACS (in initializeCLARIONAgent)</li>
 * <li> Supplying a deficit change processor (updateDeficit method which is called in run).</li>
 * </ul>
 * If you would like to see how these optional settings affect the simulation, simply add/remove
 * comments in the initializeAgentInternalSpace and run methods to include/exclude those settings.
 * <p>
 * It is highly recommended that you explore the documentation to get a sense of where all
 * of the various parameters are located and what options you have for configuring the system. The 
 * documentation also provides a plethora of information on how to setup various pieces of the architecture.
 * If you are having difficulty figuring out how a part of the library works you can contact the 
 * CLARION library support team at:<br>
 * <a href="mailto:clarion.support@gmail.com">clarion.support@gmail.com</a>
 * <br>
 * It is advised that you check the documentation before contacting the support team as it may be
 * able to answer your question.
 * <p>
 * <b>Level of difficulty:</b>
 * <ul>Easy</ul>
 * @version Configured to run with the CLARION Library, version: 6.0.6
 * @author Nick Wilson
 */
public class Hello implements BaseSimulationTemplate {
	/**The sensory information.*/
	public DimensionValueCollection SensoryInputSpace;
	/**The combination of the sensory input space and the stimulus for all of the drives.*/
	public DimensionValueCollection CombinedInputSpace;
	/**The actions that can be performed in this task*/
	public ActionCollection Actions;
	/**The agent who is running this task.*/
	public CLARION Agent;
	/**The goals the agent is able to set throughout the course of the task*/
	public GoalCollection Goals;
	
	/**A counter to keep track of how many trials the agent gets correct*/
	public int CorrectCounter = 0;
	/**The number of trials to be run.*/
	public int NumberTrials = 10000;
	
	/**
	 * The main entry point for the simulation. Your task/simulation must have one of these in order
	 * to run. The CLARION Library, itself is not an executable program. It is a library to be used
	 * for building your own program.
	 * @param args
	 */
	public static void main(String[] args)
	{
		//Initialize the task.
		Hello hw = new Hello ();
		//Initialize the sensory information space.
		//初始化感知信息
		hw.initializeSensoryInformationSpace();
		//Initialize the agent's internal space.
		hw.initializeAgentInternalSpace();
		//Initialize the agent.
		hw.initializeCLARIONAgent(hw.Agent);
		//Have the agent perform the task.
		hw.run(hw.Agent);
		//Report the results.
		hw.reportResults();
	}
	
	/**
	 * This method initializes the sensory input space and the combined input space.
	 */
	public void initializeSensoryInformationSpace() {
		//BEGIN initialization of the sensory input space.
		//Initialize a new dimension-value collection.
		SensoryInputSpace = new DimensionValueCollection ();
		//Add a new dimension.
		SensoryInputSpace.put("Salutations",new Dimension ("Salutations"));
		//Add new values to the dimension.
		SensoryInputSpace.get("Salutations").put("GoodMorning", new Value("Good Morning"));
		SensoryInputSpace.get("Salutations").put("GoodAfternoon", new Value("Good Afternoon"));
		SensoryInputSpace.get("Salutations").put("GoodEvening", new Value("Good Evening"));
		/*Initialize the combined input space with a new dimension-value that contains the
		 * sensory input space.*/
		CombinedInputSpace = new DimensionValueCollection (SensoryInputSpace);
		//END initialization of the sensory input space.
		
		//BEGIN initialization of the drive input space.
		for(int i = 0; i < Personality.PrimaryDrives.values().length; i++)
		{
			/*For each drive in the list of drives for the personality model, add an
			 * input of type STIMULUS to the combined input space.*/
			Dimension d = new Dimension (Personality.PrimaryDrives.values()[i]);
			d.put(Drive.TypicalInputs.STIMULUS,
					new Value (Drive.TypicalInputs.STIMULUS));
			CombinedInputSpace.put(d.getID(), d);
		}
	}
	
	/**
	 * This method initializes the agent's internal space including the actions and goals, etc. 
	 */
	public void initializeAgentInternalSpace() {
		//BEGIN initialize possible actions.
		//Initialize a new action collection.
		Actions = new ActionCollection ();
		//Add a new external action to the action collection for the action "Hello".
		Actions.put("GoodMorning", new ExternalAction ("GoodMorning"));
		Actions.put("GoodAfternoon", new ExternalAction ("GoodAfternoon"));
		Actions.put("GoodEvening", new ExternalAction ("GoodEvening"));
		
		//END initialize possible actions.
		
		//BEGIN initialize possible goals.
		//Initialize a new collection of goals.
		Goals = new GoalCollection ();
		//Add a goal to the list of possible goals called "Greet".
		Goals.put("M", new Goal ("M"));
		//Add a dimension to the goal "Greet" called "Desired_Salutation".
		Goals.get("M").put("Desired_Salutation", new Dimension("Desired_Salutation"));
		//Add a value to the dimension "Desired_Salutation" called "Hello".
		Goals.get("M").get("Desired_Salutation").put("GoodMorning", new Value("Good Morning"));
		
		Goals.put("A", new Goal ("A"));
		Goals.get("A").put("Desired_Salutation", new Dimension("Desired_Salutation"));
		Goals.get("A").get("Desired_Salutation").put("GoodAfternoon", new Value("Good Afternoon"));
	
		Goals.put("E", new Goal ("E"));
		Goals.get("E").put("Desired_Salutation", new Dimension("Desired_Salutation"));
		Goals.get("E").get("Desired_Salutation").put("GoodEvening", new Value("Good Evening"));
		
		/*Set an (optional) GLOBAL parameter in the Rule class to use the utility equation 
		 * instead of using constants.*/
		//AbstractRule.GLOBAL_UTILITY_OPTION = AbstractRule.UtilityOptions.EQUATION;
		
		//Initialize the agent providing it with the combined input space.
		Agent = new CLARION (CombinedInputSpace);
	}

	/**
	 * This method initializes the components and subsystems within the CALRION agent that are going 
	 * to be used by the agent to perform this task.
	 */
	public void initializeCLARIONAgent(CLARION Agent) {
		/*Initialize the MS. Specify the agent to which the MS will be attached, 
		 * and the personality to use for the agent (optional).*/
		MS ms = new MS (Agent);
		ms.addDrivesToBAS(Personality.PersonalityTypes.GENERIC.Drives.get(Personality.BehavioralSystemMembers.BAS_ONLY).values());
		ms.addDrivesToBIS(Personality.PersonalityTypes.GENERIC.Drives.get(Personality.BehavioralSystemMembers.BIS_ONLY).values());
		ms.addDrivesToBothSystems(Personality.PersonalityTypes.GENERIC.Drives.get(Personality.BehavioralSystemMembers.BOTH_SYSTEMS).values());
		//Initialize the MCS. Specify the agent to which the MCS will be attached.
		MCS mcs = new MCS (Agent);
		//Attach a new ACS level probability setting module to the MCS.
		ACSLevelProbabilitySettingModule aMod = new ACSLevelProbabilitySettingModule (ms.getBISDriveStrengths());
		mcs.addModule(aMod);
		
		//BEGIN initialize relevances for "Greet" Goal.
		/*Initialize a new collection of values. This will be used to define the relevance
		 * each drive has to the "Greet" goal.*/
		ArrayList <Value> rel_A = new ArrayList <Value> ();
		/*Specify the relevance AFFILIATION_AND_BELONGINGNESS has to the "Greet" goal.*/
		rel_A.add(new Value (Personality.PrimaryDrives.AFFILIATION_AND_BELONGINGNESS,1.0));
		/*Specify the relevance AUTONOMY has to the "Greet" goal.*/
		rel_A.add(new Value (Personality.PrimaryDrives.AUTONOMY,0));
		/*Set the relevances for the "Greet" goal.*/
		Goals.get("A").setRelevances(rel_A);
		/*Initialize a new collection of values. This will be used to define the relevance
		 * each drive has to the "BidFarewell" goal.*/
		ArrayList <Value> rel_M = new ArrayList <Value> ();
		/*Specify the relevance AFFILIATION_AND_BELONGINGNESS has to the "BidFarewell" goal.*/
		rel_M.add(new Value (Personality.PrimaryDrives.AFFILIATION_AND_BELONGINGNESS,0));
		/*Specify the relevance AUTONOMY has to the "BidFarewell" goal.*/
		rel_M.add(new Value (Personality.PrimaryDrives.AUTONOMY,1.0));
		/*Set the relevances for the "BidFarewell" goal.*/
		Goals.get("E").setRelevances(rel_M);
		
		ArrayList <Value> rel_E = new ArrayList <Value> ();
		/*Specify the relevance AFFILIATION_AND_BELONGINGNESS has to the "BidFarewell" goal.*/
		rel_E.add(new Value (Personality.PrimaryDrives.AFFILIATION_AND_BELONGINGNESS,0));
		/*Specify the relevance AUTONOMY has to the "BidFarewell" goal.*/
		rel_E.add(new Value (Personality.PrimaryDrives.AUTONOMY,1.0));
		/*Set the relevances for the "BidFarewell" goal.*/
		Goals.get("E").setRelevances(rel_E);
		//Initialize the Goal Structure. Specify the agent to which the GS will be attached. 
		GoalStructure gs = new GoalStructure (Agent);
		//Add the list of possible goals to the goal structure.
		gs.addPossibleGoals(Goals.values());
		//Attach a new goal selection module to the MCS.
		GoalSelectionModule gMod = new GoalSelectionModule (ms.getAllDriveStrengths(), gs.getPossibleGoals());
		gMod.attachGoalStructure(gs);
		mcs.addModule(gMod);
		//Initialize the ACS. Specify the agent to which the ACS will be attached.
		ACS acs = new ACS (Agent);
		
		/*Set an (optional) local parameter in the ACS that was just initialized for task specific
		 * specialization and generalization thresholds.*/
		acs.REFINER.SPECIALIZATION_THRESHOLD1 = - .6;
		acs.REFINER.GENERALIZATION_THRESHOLD1 = - .1;
		acs.REFINER.IG_OPTION = RuleRefiner.IG_OPTIONS.PERFECT;
		
		//Attach the ACS to the ACS Level Probability Setting Module in the MCS.
		aMod.attachACS(acs);
		
		/*Initialize a new dimension-value collection to setup the input layer of the neural net for 
		 * the bottom level of the ACS. Specify the sensory input space.*/
		DimensionValueCollection netInput = new DimensionValueCollection (SensoryInputSpace);
		/*Add the goals to the net input collection so the neural net can handle the goals 
		 * when they are set.*/
		for(Goal g : Goals.values())
			netInput.putAll(g);
		/*Initialize a simplified Q-learning backpropagation neural net to be used in the bottom 
		 * level of the ACS.*/
		SimplifiedQBPNet acsbp = new SimplifiedQBPNet(netInput.values(),2,Actions);
		/*Set the (optional) learning rate and momentum parameters.*/
		//acsbp.MOMENTUM = .5;
		//acsbp.LEARNING_RATE = .2;
		 
		//Add the neural net to the bottom level of the ACS.
		acs.addBLModule(acsbp);
	}
	
	/**
	 * This method runs the agent through the Hello World task.
	 */
	public void run(CLARION Agent) {
		//For keeping track of the feedback.
		int feedback = 0;
		//For keeping track of the chosen action.
		AbstractAction ChosenAction;
		//Run the task for the specified number of trials.
		for(int i = 0; i < NumberTrials; i++)
		{
			double random = Math.random();
			//Randomly choose a salutation to give.
			if(random > 0.6)
			{
				//Specify the salutation to give as "Hello".
				CombinedInputSpace.get("Salutations").get("GoodMorning").setActivation(1);
				CombinedInputSpace.get("Salutations").get("GoodAfternoon").setActivation(0);
				CombinedInputSpace.get("Salutations").get("GoodAfternoon").setActivation(0);
				
				//Specify the stimulus for the AFFILIATON_AND BELONGINNESS drive.
				CombinedInputSpace.get(Personality.PrimaryDrives.AFFILIATION_AND_BELONGINGNESS)
					.get(Drive.TypicalInputs.STIMULUS).setActivation(1);
				//Specify the stimulus for the AUTONOMY drive.
				CombinedInputSpace.get(Personality.PrimaryDrives.AUTONOMY)
					.get(Drive.TypicalInputs.STIMULUS).setActivation(0);
				System.out.print("You say, \"Good Morning\". ");
			}
			else if(random > 0.3)
			{
				//Specify the salutation to give as "Goodbye".
				CombinedInputSpace.get("Salutations").get("GoodMorning").setActivation(0);
				CombinedInputSpace.get("Salutations").get("GoodAfternoon").setActivation(1);
				CombinedInputSpace.get("Salutations").get("GoodAfternoon").setActivation(0);
				//Specify the stimulus for the AFFILIATON_AND BELONGINNESS drive.
				CombinedInputSpace.get(Personality.PrimaryDrives.AFFILIATION_AND_BELONGINGNESS)
					.get(Drive.TypicalInputs.STIMULUS).setActivation(0);
				//Specify the stimulus for the AUTONOMY drive.
				CombinedInputSpace.get(Personality.PrimaryDrives.AUTONOMY)
					.get(Drive.TypicalInputs.STIMULUS).setActivation(1);
				System.out.print("You say, \"Good Afternoon\". ");
			}else{
				//Specify the salutation to give as "Goodbye".
				CombinedInputSpace.get("Salutations").get("GoodMorning").setActivation(0);
				CombinedInputSpace.get("Salutations").get("GoodAfternoon").setActivation(0);
				CombinedInputSpace.get("Salutations").get("GoodAfternoon").setActivation(1);
				//Specify the stimulus for the AFFILIATON_AND BELONGINNESS drive.
				CombinedInputSpace.get(Personality.PrimaryDrives.AFFILIATION_AND_BELONGINGNESS)
					.get(Drive.TypicalInputs.STIMULUS).setActivation(0);
				//Specify the stimulus for the AUTONOMY drive.
				CombinedInputSpace.get(Personality.PrimaryDrives.AUTONOMY)
					.get(Drive.TypicalInputs.STIMULUS).setActivation(1);
				System.out.print("You say, \"Good Afternoon\". ");
			}
			if(i == 0)
			{
				/*Have the agent perceive the world. We are at trial 0, so no feedback has been 
				 * provided yet.*/
				Agent.perceive(CombinedInputSpace);
			}
			else
			{
				/*Have the agent perceive the world including the feedback for the last 
				action it performed.*/
				Agent.perceive(CombinedInputSpace, feedback);
			}
			//Have the agent choose an action.
			ChosenAction = Agent.act();
			if(ChosenAction.equalsID(Actions.get("Hello")))
			{
				//The agent said "Hello".
				System.out.print("The agent said, \"Hello\". ");
				if(CombinedInputSpace.get("Salutations").get("Hello").getActivation() == 1.0)
				{
					//The agent was right.
					System.out.println("The agent was correct.");
					//Record the agent's success.
					CorrectCounter++;
					//Give positive feedback.
					feedback = 1;
				}
				else
				{
					//The agent was wrong.
					System.out.println("The agent was incorrect.");
					//Give negative feedback.
					feedback = 0;
				}
			}
			if(ChosenAction.equalsID(Actions.get("GoodMorning")))
			{
				//The agent said "Goodbye".
				System.out.print("The agent said, \"Good Morning\". ");
				if(CombinedInputSpace.get("Salutations").get("Goodbye").getActivation() == 1.0)
				{
					//The agent was right.
					System.out.println("The agent was correct.");
					//Record the agent's success.
					CorrectCounter++;
					//Give positive feedback.
					feedback = 1;
				}
				else
				{
					//The agent was wrong.
					System.out.println("The agent was incorrect.");
					//Give negative feedback.
					feedback = 0;
				}
			}
			/*Optionally, call the deficit change processor to update the deficits for the agent
			 * based on the chosen action.*/
			updateDeficit(Agent, ChosenAction);
		}
	}
	
	/**
	 * This method reports the results of the simulation.
	 */
	public void reportResults() {
		System.out.println("The agent gave the correct salutation " + CorrectCounter + " times.");
		System.out.println("The agent learned the following rules through RER:");
		for(AbstractRule r : Agent.getACS().getRERRules())
		{
			//Report the rules the agent learned using RER.
			System.out.println(r.toString());
		}
	}
	
	/**
	 * This is an optional method for adjusting the deficit based on task specific information.
	 * This method is known as the "Deficit Change Processor".
	 * @param Agent The agent whose deficit you wish to change.
	 * @param ChosenAction The chosen action.
	 */
	public void updateDeficit (CLARION Agent, AbstractAction ChosenAction)
	{
		Drive ab  = Agent.getMS().getDrive(Personality.PrimaryDrives.AFFILIATION_AND_BELONGINGNESS);
		Drive a  = Agent.getMS().getDrive(Personality.PrimaryDrives.AUTONOMY);
		if(ChosenAction.getID().equals("Hello"))
		{
			/*If the agent said "Hello", decrease the rate of change for the 
			 * AFFILIATON_AND_BELONGINGNESS drive. Increase the rate of change for the 
			 * AUTONOMY drive.*/
			ab.setDeficitChangeRate(.99);
			a.setDeficitChangeRate(1.01);
			/*Adjust the deficit so it cannot be greater than 1. This is not a requirement, but is
			 * mainly intended to demonstrate that the drive, itself, can also be changed
			 * in the deficit change processor.*/
			if(a.getDeficit() > 1)
				a.setDeficit(1);
		}
		else 
		{
			if(ChosenAction.getID().equals("Goodbye"))
			{
				/*If the agent said "Goodbye", decrease the rate of change for the 
				 * AUTONOMY drive. Increase the rate of change for the 
				 * AFFILIATON_AND_BELONGINGNESS drive.*/
				a.setDeficitChangeRate(.99);
				ab.setDeficitChangeRate(1.01);
				/*Adjust the deficit so it cannot be greater than 1.*/
				if(ab.getDeficit() > 1)
					ab.setDeficit(1);
			}
		}
		
	}
}

package clarion.samples;

import clarion.system.*;

import clarion.tools.BaseSimulationTemplate;
import java.lang.Math;


import java.math.BigDecimal;

/**
 * This is a sample simulation running the "XOR" task. This simulation is meant to demonstrate a very simple,
 * well known, simulation using the CLARION library. It uses mostly default settings, so it should not be 
 * considered as demonstrating anything more than an example of a simulation using the CLARION Library. 
 * This sample shows (perhaps) the easiest simulation that can be run using a cognitive architecture.
 * <p>
 * <u>Overview of the task:</u><br>
 * This task simulates the "XOR" task. It initializes an arbitrary number of agents (as specified by the static
 * variable "NumberAgents") to learn to correctly respond "true" or "false" based on the settings of two 
 * boolean inputs.
 * <p>
 * Agents are setup using the following:
 * <ul>
 * <li>The Action Centered Subsystem (ACS) with a neural network in the bottom level and RER 
 * turned on.<br>
 * The neural network uses simplified Q-learning with backpropagation and has been setup as follows:
 * </li>
 * <ul><li>Four inputs -</li>
 * <ul><li>Boolean 1: True</li>
 * <li>Boolean 1: False</li>
 * <li>Boolean 2: True</li>
 * <li>Boolean 2: False</li></ul>
 * <li>Two outputs -</li>
 * <ul><li>True</li>
 * <li>False</li></ul></ul>
 * </ul>
 * <p>
 * In addition, there are also a few optional settings. These options are:
 * <ul>
 * <li> Turning on the benefit equation (in initializeAgentInternalSpace)</li>
 * <li> Specifying a thresholds for generalization and specialization (in initializeCLARIONAgent)</li>
 * <li> Specifying the information gain measure to use (in initializeCLARIONAgent)</li>
 * <li> Adjusting the learning-rate and momentum parameters for the neural network in the ACS (in initializeCLARIONAgent)</li>
 * </ul>
 * If you would like to see how these optional settings affect the simulation, simply add/remove
 * comments in the initializeAgentInternalSpace method to include/exclude those settings.
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
 * @author Josh Safran
 */
public class XOR implements BaseSimulationTemplate {
	/**The sensory information.*/
	public DimensionValueCollection SensoryInputSpace;
	/**The combination of the sensory input space and the stimulus for all of the drives.*/
	public DimensionValueCollection CombinedInputSpace;
	/**The actions that can be performed in this task*/
	public ActionCollection Actions;
	/**The agent who is running this task.*/
	public CLARION Agent;
	
	/**Variables for running tests*/
	public double CorrectCounter = 0;
	public int CurrentTest = 0;
	/**The number of trials to be run.*/
	public double NumberTrials = 20000;
	//This extra level of testing is to see accuracy throughout a run
	public int NumberTestRuns = 20;
	public static int NumberAgents = 10;
	
	public double Accuracy;
	public int DecimalPlace = 1;
	public double AvgAccuracyNumerator  = 0.0;
	
	public boolean useVerboseLogging = true;

	//Used to grab values of dimension space for shorter representation
	public double Bool1False;
	public double Bool1True;
	public double Bool2False;
	public double Bool2True;
		
	/**
	 * The main entry point for the simulation. Your task/simulation must have one of these in order
	 * to run. The CLARION Library, itself is not an executable program. It is a library to be used
	 * for building your own program.
	 * @param args
	 */
	public static void main(String[] args)
	{
		XOR xt = new XOR ();
		//Initialize the sensory information space.
		xt.initializeSensoryInformationSpace();
		//Initialize the agent's internal space.
		xt.initializeAgentInternalSpace();
		//Initialize the agent.
		
		for(int i = 0;i<NumberAgents;i++){
			//Initialize the task.
			xt.initializeCLARIONAgent(xt.Agent);
			//Have the agent perform the task.
			xt.run(xt.Agent);
		}
		xt.reportFinalResults();
	}

	/**
	 * This method initializes the agent's internal space including the actions and goals, etc. 
	 */
	public void initializeAgentInternalSpace() {
		
		//BEGIN initialize possible actions.
		//Initialize a new action collection.
		Actions = new ActionCollection ();
		//Add a new external action to the action collection for the action "True".
		Actions.put("True", new ExternalAction ("True"));
		//Add a new external action to the action collection for the action "False".
		Actions.put("False", new ExternalAction ("False"));
		//END initialize possible actions.
		
		/*Set a GLOBAL parameter in the Rule class to use the utility equation 
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
		
		//Initialize the ACS. Specify the agent to which the ACS will be attached.
		ACS acs = new ACS (Agent);
		
		/*Set an (optional) local parameter in the ACS that was just initialized for task specific
		 * specialization and generalization thresholds.*/
		acs.REFINER.SPECIALIZATION_THRESHOLD1 = - 1;
		acs.REFINER.GENERALIZATION_THRESHOLD1 = -.1;
		acs.REFINER.IG_OPTION = RuleRefiner.IG_OPTIONS.PERFECT;
		
		/*Initialize a new dimension-value collection to setup the input layer of the neural net for 
		 * the bottom level of the ACS. Specify the sensory input space.*/
		DimensionValueCollection netInput = new DimensionValueCollection (SensoryInputSpace);
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
	 * This method initializes the sensory input space and the combined input space.
	 */
	public void initializeSensoryInformationSpace() {
		//BEGIN initialization of the sensory input space.
		//Initialize a new dimension-value collection.
		SensoryInputSpace = new DimensionValueCollection ();
		//Add a new dimension.
		SensoryInputSpace.put("Boolean1",new Dimension ("Boolean1"));
		//Add new values to the dimension.
		SensoryInputSpace.get("Boolean1").put("True", new Value("True"));
		SensoryInputSpace.get("Boolean1").put("False", new Value("False"));
		
		//Add a new dimension.
		SensoryInputSpace.put("Boolean2",new Dimension ("Boolean2"));
		//Add new values to the dimension.
		SensoryInputSpace.get("Boolean2").put("True", new Value("True"));
		SensoryInputSpace.get("Boolean2").put("False", new Value("False"));
		
		/*Initialize the combined input space with a new dimension-value that contains the
		 * sensory input space.*/
		CombinedInputSpace = new DimensionValueCollection (SensoryInputSpace);
		//END initialization of the sensory input space.
	}

	/**
	 * This method reports the results of each agent during the simulation.
	 */
	public void reportResults() {
		Accuracy = CorrectCounter/NumberTrials;
		CorrectCounter = 0;
		double percentage = Accuracy*100.0;
		BigDecimal bd = new BigDecimal( Double.toString(percentage) );
		bd = bd.setScale( DecimalPlace, BigDecimal.ROUND_HALF_UP );

		if (CurrentTest == NumberTestRuns){
			System.out.println(" |"+bd.doubleValue()+"|- run complete");
			if (useVerboseLogging){
				System.out.println("The agent learned the following rules through RER:");
				for(AbstractRule r : Agent.getACS().getRERRules())
				{
					//Report the rules the agent learned using RER.
					System.out.println(r.toString());		
				}
			}
		}
		else
			System.out.print(" |"+bd.doubleValue()+"| ");
			
	}
		
	/**
	 * This method reports the final results after all agents have been run.
	 */
	public void reportFinalResults(){
		System.out.println("All tests and trials complete.");
		double percentAccuracy = (AvgAccuracyNumerator/NumberAgents)*100.0;
		CorrectCounter = 0;
		BigDecimal bd = new BigDecimal( Double.toString(percentAccuracy) );
		bd = bd.setScale( DecimalPlace, BigDecimal.ROUND_HALF_UP );
		
		System.out.println("Number of Trials per Test: "+ NumberTrials);
		System.out.println("Number of Tests per Run: "+NumberTestRuns);
		System.out.println("Number of Trials per Run:"+ NumberTestRuns*NumberTrials);
		System.out.println("Number of Agents: "+ NumberAgents);
		System.out.println("Avg (accuracy after all tests) over all agents: "+ bd.doubleValue());
	}
	
	/**
	 * This method runs an agent through the XOR task.
	 */
	public void run(CLARION Agent) {
		//For keeping track of the feedback.
		int feedback = 0;
		//For keeping track of the chosen action.
		AbstractAction ChosenAction;
		//Run the task for the specified number of trials.
		
		for(CurrentTest = 1;CurrentTest<=NumberTestRuns;CurrentTest++){
			for(int i = 0; i < NumberTrials; i++)
			{
				//Randomly choose an input to give.
				if(Math.random() > .5)
				{
					if(Math.random()>.5)
					{
					//Specify the input to give as "True True".
						CombinedInputSpace.get("Boolean1").get("True").setActivation(1);
						CombinedInputSpace.get("Boolean1").get("False").setActivation(0);
						CombinedInputSpace.get("Boolean2").get("True").setActivation(1);
						CombinedInputSpace.get("Boolean2").get("False").setActivation(0);
				
					}else{
						//Specify the input to give as "False False".
						CombinedInputSpace.get("Boolean1").get("True").setActivation(0);
						CombinedInputSpace.get("Boolean1").get("False").setActivation(1);
						CombinedInputSpace.get("Boolean2").get("True").setActivation(0);
						CombinedInputSpace.get("Boolean2").get("False").setActivation(1);
				
					}
				}
				else
				{
					if(Math.random()>.5)
					{
						//Specify the input to give as "True False".
						CombinedInputSpace.get("Boolean1").get("True").setActivation(1);
						CombinedInputSpace.get("Boolean1").get("False").setActivation(0);
						CombinedInputSpace.get("Boolean2").get("True").setActivation(0);
						CombinedInputSpace.get("Boolean2").get("False").setActivation(1);
					
					}else{
						//Specify the input to give as "False True".
						CombinedInputSpace.get("Boolean1").get("True").setActivation(0);
						CombinedInputSpace.get("Boolean1").get("False").setActivation(1);
						CombinedInputSpace.get("Boolean2").get("True").setActivation(1);
						CombinedInputSpace.get("Boolean2").get("False").setActivation(0);
				
					}
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
				Bool1False = 	CombinedInputSpace.get("Boolean1").get("False").getActivation();
				Bool1True = 	CombinedInputSpace.get("Boolean1").get("True").getActivation();
				Bool2False = 	CombinedInputSpace.get("Boolean2").get("False").getActivation();
				Bool2True = 	CombinedInputSpace.get("Boolean2").get("True").getActivation();
				
				if((Bool1False == 1.0 && Bool2True== 1.0)||
						(Bool1True ==1.0&& Bool2False == 1.0))
				{
					if(ChosenAction.equalsID(Actions.get("True")))
					{
						//Record the agent's success.
						CorrectCounter++;
						//Give positive feedback.
						feedback = 1;
					}else
					{
						//Give negative feedback.
						feedback = 0;
					}
				}else if ((Bool1False ==1.0&& Bool2False == 1.0)||
						(Bool1True ==1.0 && Bool2True == 1.0)){
					if(ChosenAction.equalsID(Actions.get("False")))
					{
						//Record the agent's success.
						CorrectCounter++;
						//Give positive feedback.
						feedback = 1;
					}
					else
					{
						//Give negative feedback.
						feedback = 0;
					}	
				}
			}
			reportResults();
		}
		AvgAccuracyNumerator+=Accuracy;
	}

}

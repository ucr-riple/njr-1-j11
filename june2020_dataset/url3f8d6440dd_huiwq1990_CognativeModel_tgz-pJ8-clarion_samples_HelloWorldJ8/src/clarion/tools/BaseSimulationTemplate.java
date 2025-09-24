package clarion.tools;

import clarion.system.*;

/**
 * This interface provides a base template to help with the construction of
 * simulations using the CLARION library.
 * <p>
 * <b>Usage:</b>
 * <p>
 * The order for constructing a simulation is:<br>
 * <ol>
 * <li>Construct the input space. This includes:</li>
 * <ul><li>The space of all sensory information</li></ul>
 * <li>Construct the space of all possible internal information that can be used by your agents.
 * This includes:</li><ul>
 * <li>The actions that can be performed</li>
 * <li>The goals (if being used)</li>
 * <li>Specialized working memory chunks (if being used)</li>
 * <li>Input specific to the drives (if being used):</li>
 * <ul><li>stimulus</li>
 * <li>deficit</li></ul>
 * <li>Adjusting the global parameters you want adjusted by accessing them statically within their 
 * appropriate class (optional).</li></ul>
 * <li>Initialize your CLARION agents.</li>
 * <li>Initialize an instance of the MS (if being used).</li>
 * <ul><li>The instance of the MS you just initialized will be automatically attached to the Agent
 * with whom it is initialized</li></ul>
 * <li>Initialize the drives either by:</li>
 * <ul><li>Specifying a personality.</li>
 * <li>Initializing drives and adding them to the appropriate behavior systems in the MS.</li></ul>
 * If initializing drives to use an implicit module, then:
 * <ol><li>Initialize the implicit module.</li>
 * <li>Train the implicit module (if needed).</li>
 * <li>Attach implicit module to the drive.</li></ol>
 * <li>Initialize an instance of the MCS (if being used).</li>
 * <ul><li>The instance of the MCS you just initialized will be automatically attached to the Agent
 * with whom it is initialized</li></ul>
 * <ol><li>Initialize an instance of the goal selection module (if being used).</li>
 * If using an implicit module for goal selection, then:
 * <ol><li>Initialize the implicit module.</li>
 * <li>Train the implicit module (if needed).</li>
 * <li>Attach the implicit module to the goal selection module.</ol>
 * <li>Initialize an instance of the ACS level probability setting module (if being used).</li>
 * If using an implicit module for probability setting, then:
 * <ol><li>Initialize the implicit module.</li>
 * <li>Train implicit module (if needed).</li>
 * <li>Attach the implicit module to the ACS level probability setting module.</li></ol></ol>
 * <li>Initialize an instance of the goal structure (if being used).</li>
 * <ul><li>The instance of the goal structure you just initialized will be automatically attached 
 * to the Agent with whom it is initialized</li></ul>
 * <ol><li>Specify the relevance each drive has to each goal.</li>
 * <li>Add the goals to the list of possible goals in the goal structure.</li></ol>
 * <li>Initialize an instance of the ACS.</li>
 * <ul><li>The instance of the ACS you just initialized will be automatically attached to the Agent
 * with whom it is initialized</li></ul>
 * <ol><li>Initialize and train (if needed) one or more implicit modules (REQUIRED).</li>
 * <li>Initialize any IRL or fixed rules (if being used).</li>
 * <li>Add the implicit modules, rules, and actions to the ACS.</li></ol>
 * <li>Initialize an instance of the working memory (if being used).</li>
 * <ul><li>The instance of the working memory you just initialized will be automatically attached to 
 * the Agent with whom it is initialized</li></ul>
 * <ul><li>Add any specialized working memory memory chunks to the list of specialized working memory
 * chunks.</li></ul></ol>
 * To run the simulation:
 * <ul><li>At each time increment:</li>
 * <ol><li>Have the CLARION agent perceive the current sensory information including drive 
 * specific input (if being used) and any feedback from the last time increment (if available).</li>
 * <li>Ask the CLARION agent to act given what it perceived.</li>
 * <li>Adjust the current sensory information (including drive specific input), and repeat.</li>
 * </ul></ol>
 * Reporting the internal results:
 * <ul><li>Essentially all components of CLARION can be viewed at any point during the simulation
 * if you want to report the internal state of a CLARION agent.</li></ul>
 * The following internal operations are acceptable to perform during the simulation:
 * <ol><li>Presenting CLARION with new sensory information (through perception) not originally
 * specified within the sensory information space during initialization.</li>
 * <li>Adding new actions, implicit modules, or rules to the ACS.</li>
 * <li>Adding new goals to the list of possible goals in the goal structure.</li>
 * <li>Adding new drives to the MS (not recommended).</li>
 * <li>Adding new specialized working memory chunks to the specialized working memory chunk list
 * in the working memory.</li>
 * <li>Changing the LOCAL parameters for various components inside an agent.</li></ol>
 * @version 6.0.4
 * @author Nick Wilson
 */
public interface BaseSimulationTemplate {
	
	
	/**
	 * This method is where you construct the world in which your CLARION agents will live.
	 * To accomplish this you must do the following:
	 * <ul><li>Construct the input space. This includes:</li>
	 * <ul><li>The space of all sensory information</li>
	 * <li>Input specific to the drives (if being used):</li>
	 * <ul><li>stimulus</li></ul></ul>
	 */
	public void initializeSensoryInformationSpace ();
	
	/**
	 * This method is where you construct the space of all possible internal information that can be
	 * used by your agents. Remember that each agent needs its own instance of the items you specify
	 * here. Make sure you "clone" everything you specify in this method before attaching it to
	 * an Agent.
	 * <p>
	 * This includes:
	 * <ul><li>The actions that can be performed</li>
	 * <li>The goals (if being used)</li>
	 * <li>Specialized working memory chunks (if being used)</li>
	 * <li>Adjusting the global parameters you want adjusted by accessing them statically within their 
	 * appropriate class (optional).</li>
	 * <li>Initializing you CLARION agents.</li></ul>
	 */
	public void initializeAgentInternalSpace ();
	
	/**
	 * This method is where all of the agent specific initialization takes place for each agent
	 * in your environment.
	 * <p>
	 * The order for constructing a particular CLARION agent is:<br>
	 * <ol>
	 * <li>Initialize an instance of the MS (if being used).</li>
	 * <ul><li>The instance of the MS you just initialized will be automatically attached to the Agent
	 * with whom it is initialized</li></ul>
	 * <li>Initialize the drives either by:</li>
	 * <ul><li>Specifying a personality.</li>
	 * <li>Initializing drives and adding them to the appropriate behavior systems in the MS.</li></ul>
	 * If initializing drives to use an implicit module, then:
	 * <ol><li>Initialize the implicit module.</li>
	 * <li>Train the implicit module (if needed).</li>
	 * <li>Attach implicit module to the drive.</li></ol>
	 * <li>Initialize an instance of the MCS (if being used).</li>
	 * <ul><li>The instance of the MCS you just initialized will be automatically attached to the Agent
	 * with whom it is initialized</li></ul>
	 * <ol><li>Initialize an instance of the goal selection module (if being used).</li>
	 * If using an implicit module for goal selection, then:
	 * <ol><li>Initialize the implicit module.</li>
	 * <li>Train the implicit module (if needed).</li>
	 * <li>Attach the implicit module to the goal selection module.</ol>
	 * <li>Initialize an instance of the ACS level probability setting module (if being used).</li>
	 * If using an implicit module for probability setting, then:
	 * <ol><li>Initialize the implicit module.</li>
	 * <li>Train implicit module (if needed).</li>
	 * <li>Attach the implicit module to the ACS level probability setting module.</li></ol></ol>
	 * <li>Initialize an instance of the goal structure (if being used).</li>
	 * <ul><li>The instance of the goal structure you just initialized will be automatically attached 
	 * to the Agent with whom it is initialized</li></ul>
	 * <ol><li>Specify the relevance each drive has to each goal.</li>
	 * <li>Add the goals to the list of possible goals in the goal structure.</li></ol>
	 * <li>Initialize an instance of the ACS.</li>
	 * <ul><li>The instance of the ACS you just initialized will be automatically attached to the Agent
	 * with whom it is initialized</li></ul>
	 * <ol><li>Initialize and train (if needed) one or more implicit modules (REQUIRED).</li>
	 * <li>Initialize any IRL or fixed rules (if being used).</li>
	 * <li>Add the implicit modules, rules, and actions to the ACS.</li></ol>
	 * <li>Initialize an instance of the working memory (if being used).</li>
	 * <ul><li>The instance of the working memory you just initialized will be automatically attached to 
	 * the Agent with whom it is initialized</li></ul>
	 * <ul><li>Add any specialized working memory memory chunks to the list of specialized working memory
	 * chunks.</li></ul></ol>
	 * @param Agent The agent to initialize.
	 */
	public abstract void initializeCLARIONAgent (CLARION Agent);
	
	/**
	 * This method is used to run the task. It is up to you to decide how many steps of the simulation
	 * the run method should run. You can setup run to run each agent through the entire task, or you
	 * can just have the agent run 1 step of the task.
	 * <p>
	 * To run the simulation:
	 * <ul><li>At each time increment:</li>
	 * <ol><li>Have the CLARION agent perceive the current sensory information including drive 
	 * specific input (if being used) and any feedback from the last time increment (if available).</li>
	 * <li>Ask the CLARION agent to act given what it perceived.</li>
	 * <li>Adjust the current sensory information (including drive specific input), and repeat.</li>
	 * </ul></ol>
	 * The following internal operations are acceptable to perform during the simulation:
	 * <ol><li>Presenting CLARION with new sensory information (through perception) not originally
	 * specified within the sensory information space during initialization.</li>
	 * <li>Adding new actions, implicit modules, or rules to the ACS.</li>
	 * <li>Adding new goals to the list of possible goals in the goal structure.</li>
	 * <li>Adding new drives to the MS (not recommended).</li>
	 * <li>Adding new specialized working memory chunks to the specialized working memory chunk list
	 * in the working memory.</li>
	 * <li>Changing the LOCAL parameters for various components inside an agent.</li></ol>
	 * @param Agent The agent to run.
	 */
	public abstract void run (CLARION Agent);
	
	/**
	 * Use this method to report the results of your simulation. It is up to you how you would like
	 * to output the results. You can save the results to a file, or print the results to the screen.
	 * The options for outputting results are virtually limit less.
	 * <p>
	 * Reporting the internal results:
	 * <ul><li>Essentially all components of CLARION can be viewed at any point during the simulation
	 * if you want to report the internal state of a CLARION agent.</li></ul>
	 */
	public abstract void reportResults ();
}

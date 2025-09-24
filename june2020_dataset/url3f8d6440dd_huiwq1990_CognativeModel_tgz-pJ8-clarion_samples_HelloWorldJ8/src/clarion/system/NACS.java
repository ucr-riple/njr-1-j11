package clarion.system;

/**
 * This class implements the Non-Action Centered Subsystem (NACS) within CLARION. 
 * It extends the AbstractSubsystem class.
 * <p>
 * <b>Usage:</b>
 * <p>
 * The NACS is not currently implemented in the CLARION Library. The NACS will be implemented in version
 * 6.1 of library.
 * <p>
 * Note that it is not required that a CLARION agent even have an NACS at all. If the task being performed
 * by the agent does not require an NACS, the user can choose simply not to attach the NACS to the
 * CLARION agent, and the agent will be able to operate quite successfully without this subsystem being
 * specified (for some tasks).
 * @version 6.0.4
 * @author Nick Wilson
 */
public class NACS extends AbstractSubsystem{

	/**
	 * Minimally initializes the NACS. This constructor builds a "frame" for this instance of
	 * the NACS from which all desired components can be attached. During initialization
	 * this instance of the subsystem will attach itself to the CLARION agent you specify.
	 * @param Agent The agent to which the subsystem is being attached.
	 */
	public NACS(CLARION Agent) {
		super(Agent);
	}
	
	/**
	 * Attaches the NACS to the specified CLARION agent.
	 * @param Agent The agent to wish this NACS will be attached.
	 */
	protected void attachSelfToAgent(CLARION Agent) {
		Agent.attachNACS(this);
	}

	/**
	 * Performs the appropriate end of episode instructions for the NACS. This method is called by the
	 * CLARION class when its endEpisode method is called.
	 * @param Input A collection of various information to be used for ending the episode.
	 * @param TimeStamp The current time stamp.
	 */
	protected void endEpisode(DimensionValueCollection Input, long TimeStamp) {
		return;
	}
}

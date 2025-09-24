package clarion.system;

/**
 * This class implements a missing goal structure exception in CLARION. It extends the RuntimeException class.
 * <p>
 * <b>Usage:</b>
 * <p>
 * This class is thrown if the goal structure has not been correctly attached within CLARION.
 * @version 6.0.6
 * @author Nick Wilson
 */
public class MissingGoalStructureException extends RuntimeException {
	private static final long serialVersionUID = -8321537592917251384L;

	/**
	 * @param e - The message to be assigned to this exception.
	 */
	public MissingGoalStructureException (String e)
	{
		super(e);
	}
	
	public MissingGoalStructureException ()
	{
		super();
	}
}

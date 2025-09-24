package clarion.system;

/**
 * This class implements a missing state/action buffer exception in CLARION. It extends the 
 * RuntimeException class.
 * <p>
 * <b>Usage:</b>
 * <p>
 * This class is thrown if the state/action buffer has not been correctly attached within CLARION.
 * @version 6.0.4
 * @author Nick Wilson
 */
public class MissingEpisodicMemoryException extends RuntimeException {
	private static final long serialVersionUID = -8321537592917251384L;

	/**
	 * @param e - The message to be assigned to this exception.
	 */
	public MissingEpisodicMemoryException (String e)
	{
		super(e);
	}
	
	public MissingEpisodicMemoryException ()
	{
		super();
	}
}

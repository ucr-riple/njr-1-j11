package clarion.system;

/**
 * This class implements a missing ACS exception in CLARION. It extends the RuntimeException class.
 * <p>
 * <b>Usage:</b>
 * <p>
 * This class is thrown if the ACS has not been correctly attached within CLARION.
 * @version 6.0.4
 * @author Nick Wilson
 */
public class MissingACSException extends RuntimeException {
	private static final long serialVersionUID = -8321537592917251384L;

	/**
	 * @param e - The message to be assigned to this exception.
	 */
	public MissingACSException (String e)
	{
		super(e);
	}
	
	public MissingACSException ()
	{
		super();
	}
}

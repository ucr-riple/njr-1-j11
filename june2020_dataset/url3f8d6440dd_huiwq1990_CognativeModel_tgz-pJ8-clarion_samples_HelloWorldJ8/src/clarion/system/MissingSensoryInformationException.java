package clarion.system;

/**
 * This class implements a missing sensory information exception in CLARION. It extends the RuntimeException class.
 * <p>
 * <b>Usage:</b>
 * <p>
 * This class is thrown if no sensory information has been provided to an instance of CLARION.
 * @version 6.0.4
 * @author Nick Wilson
 */
public class MissingSensoryInformationException extends RuntimeException {
	private static final long serialVersionUID = -8321537592917251384L;

	/**
	 * @param e - The message to be assigned to this exception.
	 */
	public MissingSensoryInformationException (String e)
	{
		super(e);
	}
	
	public MissingSensoryInformationException ()
	{
		super();
	}
}

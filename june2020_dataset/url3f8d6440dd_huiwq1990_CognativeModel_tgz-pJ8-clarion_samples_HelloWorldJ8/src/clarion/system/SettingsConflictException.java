package clarion.system;

/**
 * This class implements a settings conflict exception within CLARION. It extends the RuntimeException class.
 * <p>
 * <b>Usage:</b>
 * <p>
 * This class is thrown if two of more settings within CLARION conflict with one another.
 * @version 6.0.6
 * @author Nick Wilson
 */
public class SettingsConflictException extends RuntimeException {
	private static final long serialVersionUID = 3466529356211512523L;
	
	/**
	 * @param e The message to be assigned to this exception.
	 */
	public SettingsConflictException (String e)
	{
		super(e);
	}
	
	public SettingsConflictException ()
	{
		super();
	}
}

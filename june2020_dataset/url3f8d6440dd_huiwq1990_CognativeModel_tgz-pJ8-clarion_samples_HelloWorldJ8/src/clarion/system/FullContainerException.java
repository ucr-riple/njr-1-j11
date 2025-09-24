package clarion.system;

/**
 * This class implements a full container exception within CLARION. It extends the RuntimeException class.
 * <p>
 * <b>Usage:</b>
 * <p>
 * This class is thrown if a container within CLARION (such as the Working Memory or Goal Structure)
 * is full.
 * @version 6.0.4
 * @author Nick Wilson
 */
public class FullContainerException extends RuntimeException {
	private static final long serialVersionUID = 3466529356211512523L;
	
	/**
	 * @param e The message to be assigned to this exception.
	 */
	public FullContainerException (String e)
	{
		super(e);
	}
	
	public FullContainerException ()
	{
		super();
	}
}

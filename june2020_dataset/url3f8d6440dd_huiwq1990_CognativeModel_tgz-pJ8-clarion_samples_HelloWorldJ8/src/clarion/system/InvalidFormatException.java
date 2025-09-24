package clarion.system;

/**
 * This class implements an invalid format exception in CLARION. It extends the RuntimeException class.
 * <p>
 * <b>Usage:</b>
 * <p>
 * This class is thrown if CLARION requires something to be specified in a particular format, and the required 
 * format is not followed.
 * @version 6.0.4
 * @author Nick Wilson
 */
public class InvalidFormatException extends RuntimeException{
	private static final long serialVersionUID = -2768240649265233292L;
	
	/**
	 * @param e - The message to be assigned to this exception.
	 */
	public InvalidFormatException (String e)
	{
		super(e);
	}
	
	public InvalidFormatException ()
	{
		super();
	}
}

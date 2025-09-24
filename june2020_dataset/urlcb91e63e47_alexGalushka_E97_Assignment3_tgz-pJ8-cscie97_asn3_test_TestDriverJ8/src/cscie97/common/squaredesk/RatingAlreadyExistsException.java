package cscie97.common.squaredesk;


/**
 * The Class RatingAlreadyExistsException.
 */
public class RatingAlreadyExistsException extends Exception
{	
	
	/** serialization. */
	private static final long serialVersionUID = -883996508627597612L;	
	
	/**
	 * Instantiates a new rating already exists exception.
	 */
	public RatingAlreadyExistsException()
	{ 
		super();
	}
	
	/**
	 * Instantiates a new rating already exists exception.
	 *
	 * @param message the message
	 */
	public RatingAlreadyExistsException(String message) 
	{
		super(message);
	}
}
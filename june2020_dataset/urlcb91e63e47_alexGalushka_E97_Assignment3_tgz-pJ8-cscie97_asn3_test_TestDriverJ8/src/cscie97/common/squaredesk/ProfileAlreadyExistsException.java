package cscie97.common.squaredesk;


/**
 * The Class ProviderAlreadyExistsException.
 */
public class ProfileAlreadyExistsException extends Exception
{	
	
	/** serialization. */
	private static final long serialVersionUID = 8222616516878201026L;

	/**
	 * Instantiates a new provider already exists exception.
	 */
	public ProfileAlreadyExistsException()
	{ 
		super();
	}
	
	/**
	 * Instantiates a new provider already exists exception.
	 *
	 * @param message the message
	 */
	public ProfileAlreadyExistsException( String message ) 
	{
		super( message );
	}
}

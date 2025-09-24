package cscie97.common.squaredesk;


/**
 * The Class ProviderNotFoundException.
 */
public class ProfileNotFoundException extends Exception
{	
	
	/** serialization. */
	private static final long serialVersionUID = 8530344751450677081L;

	/**
	 * Instantiates a new provider not found exception.
	 */
	public ProfileNotFoundException()
	{ 
		super();
	}
	
	/**
	 * Instantiates a new provider not found exception.
	 *
	 * @param message the message
	 */
	public ProfileNotFoundException( String message ) 
	{
		super( message );
	}
}
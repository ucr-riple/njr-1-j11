package cscie97.common.squaredesk;


/**
 * The Class FeatureNotFoundException.
 */
public class FeatureNotFoundException extends Exception
{	
	
	/** serialization. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new feature not found exception.
	 */
	public FeatureNotFoundException()
	{ 
		super();
	}
	
	/**
	 * Instantiates a new feature not found exception.
	 *
	 * @param message the message
	 */
	public FeatureNotFoundException( String message ) 
	{
		super( message );
	}
}

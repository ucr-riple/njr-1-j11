package cscie97.asn2.squaredesk.provider;


/**
 * The Class OfficeSpaceNotFoundException.
 */
public class OfficeSpaceNotFoundException extends Exception
{	
	
	/** serialization. */
	private static final long serialVersionUID = -5700009222647459810L;

	/**
	 * Instantiates a new office space not found exception.
	 */
	public OfficeSpaceNotFoundException()
	{ 
		super();
	}
	
	/**
	 * Instantiates a new office space not found exception.
	 *
	 * @param message the message
	 */
	public OfficeSpaceNotFoundException(String message) 
	{
		super(message);
	}
}

package cscie97.asn3.squaredesk.renter;

/**
 * 
 * @author apgalush
 *
 */
public class BookingException extends Exception
{

	/** serialization. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new feature not found exception.
	 */
	public BookingException()
	{ 
		super();
	}
	
	/**
	 * Instantiates a new feature not found exception.
	 *
	 * @param message the message
	 */
	public BookingException( String message ) 
	{
		super( message );
	}
}

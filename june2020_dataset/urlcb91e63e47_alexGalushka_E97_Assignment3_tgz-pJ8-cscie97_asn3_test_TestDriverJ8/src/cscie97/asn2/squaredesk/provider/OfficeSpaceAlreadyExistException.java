package cscie97.asn2.squaredesk.provider;


/**
 * The Class OfficeSpaceAlreadyExistException.
 */
public class OfficeSpaceAlreadyExistException extends Exception
{	
	
	/** serialization. */
	private static final long serialVersionUID = -8552991948167999990L;

	/**
	 * Instantiates a new office space already exist exception.
	 */
	public OfficeSpaceAlreadyExistException()
	{ 
		super();
	}
	
	/**
	 * Instantiates a new office space already exist exception.
	 *
	 * @param message the message
	 */
	public OfficeSpaceAlreadyExistException(String message) 
	{
		super(message);
	}
}


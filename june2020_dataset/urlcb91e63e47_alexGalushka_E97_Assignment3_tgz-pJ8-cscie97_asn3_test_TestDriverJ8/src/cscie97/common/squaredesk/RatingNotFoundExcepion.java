package cscie97.common.squaredesk;


/**
 * The Class RatingNotFoundExcepion.
 */
public class RatingNotFoundExcepion extends Exception
{	
	
	/** serialization. */
	private static final long serialVersionUID = 5398777165845179444L;
	
	/**
	 * Instantiates a new rating not found excepion.
	 */
	public RatingNotFoundExcepion()
	{ 
		super();
	}
	
	/**
	 * Instantiates a new rating not found excepion.
	 *
	 * @param message the message
	 */
	public RatingNotFoundExcepion(String message) 
	{
		super(message);
	}
}
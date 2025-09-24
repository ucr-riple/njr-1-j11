package cscie97.common.squaredesk;


/**
 * The Class Rating.
 */
public class Rating 
{

	/** The authors name. */
	private String authorsName;
	
	/** The authors id. */
	private String authorsId;
	
	/** The comment. */
	private String comment;
	
	/** The date. */
	private String date;
	
	/** The stars. */
	private Integer stars;
	
	/**
	 * Instantiates a new rating.
	 *
	 * @param authorsName the authors name
	 * @param comment the comment
	 * @param date the date
	 * @param stars the stars
	 */
	public Rating (	String authorsName, String comment,
			        String date, Integer stars )
	{
		this.authorsName = authorsName;
		this.comment = comment;
		this.date = date;
		this.stars = stars;
	}
	
	/**
	 * mutator method for authorsName attribute.
	 *
	 * @param authorsName the new authors name
	 */
	public void setAuthorsName ( String authorsName )
	{
		this.authorsName = authorsName;
	}
	
	/**
	 * accessor method for authorsName attribute.
	 *
	 * @return String
	 */
	public String getAuthorsName ()
	{
		return this.authorsName;
	}
	
	/**
	 * mutator method for authorsId attribute.
	 *
	 * @param authorsId the new authors id
	 */
	public void setAuthorsId ( String authorsId )
	{
		this.authorsId = authorsId;
	}
	
	/**
	 * accessor method for authorsId attribute.
	 *
	 * @return String
	 */
	public String getAuthorsId ()
	{
		return this.authorsId;
	}
	
	/**
	 * mutator method for comment attribute.
	 *
	 * @param comment the new comment
	 */
	public void setComment ( String comment )
	{
		this.comment = comment;
	}
	
	/**
	 * accessor method for comment attribute.
	 *
	 * @return String
	 */
	public String getComment ()
	{
		return this.comment;
	}
	
	/**
	 * mutator method for date attribute.
	 *
	 * @param date the new date
	 */
	public void setDate ( String date )
	{
		this.date = date;
	}
	
	/**
	 * accessor method for date attribute.
	 *
	 * @return String
	 */
	public String getDate ()
	{
		return this.date;
	}
	
	/**
	 * mutator method for stars attribute.
	 *
	 * @param stars the new stars
	 */
	public void setStars ( Integer stars )
	{
		this.stars = stars;
	}
	
	/**
	 * accessor method for stars attribute.
	 *
	 * @return Integer
	 */
	public Integer getStars ()
	{
		return this.stars;
	}
}

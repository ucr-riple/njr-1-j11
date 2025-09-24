package cscie97.common.squaredesk;


/**
 * The Class Capacity.
 */
public class Capacity
{
	
	/** The number of people. */
	private Integer numberOfPeople;
	
	/** The work spaces. */
	private Integer workSpaces;
	
	/** The square footage. */
	private Float squareFootage;
	
	/**
	 * Instantiates a new capacity.
	 *
	 * @param numberOfPeople the number of people
	 * @param workSpaces the work spaces
	 * @param squareFootage the square footage
	 */
	public Capacity ( Integer numberOfPeople, Integer workSpaces, Float squareFootage )
	{
		this.workSpaces = workSpaces;
		this.numberOfPeople = numberOfPeople;
		this.squareFootage = squareFootage;
	}
	
	/**
	 * mutator method for numberOfPeople attribute.
	 *
	 * @param numberOfPeople the new number of people
	 */
	public void setNumberOfPeople ( Integer numberOfPeople )
	{
		this.numberOfPeople = numberOfPeople;
	}
	
	/**
	 * accessor method for numberOfPeople attribute.
	 *
	 * @return Integer
	 */
	public Integer getNumberOfPeople ()
	{
		return this.numberOfPeople;
	}
	
	/**
	 * mutator method for workSpaces attribute.
	 *
	 * @param workSpaces the new work spaces
	 */
	public void setWorkSpaces ( Integer workSpaces )
	{
		this.workSpaces = workSpaces;
	}
	
	/**
	 * accessor method for workSpaces attribute.
	 *
	 * @return Integer
	 */
	public Integer getWorkSpaces ()
	{
		return this.workSpaces;
	}
	
	/**
	 * mutator method for squareFootage attribute.
	 *
	 * @param squareFootage the new square footage
	 */
	public void setSquareFootage ( Float squareFootage )
	{
		this.squareFootage = squareFootage;
	}
	
	/**
	 * accessor method for squareFootage attribute.
	 *
	 * @return Integer
	 */
	public Float getSquareFootage ()
	{
		return this.squareFootage;
	}
}

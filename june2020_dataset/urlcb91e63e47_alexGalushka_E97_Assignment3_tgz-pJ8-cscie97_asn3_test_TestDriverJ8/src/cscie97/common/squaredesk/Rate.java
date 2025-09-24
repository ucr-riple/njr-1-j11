package cscie97.common.squaredesk;


/**
 * The Class Rate.
 */
public class Rate
{
	//any period the customer can imagine: hour, day, week, month, year, weekend
	/** The period. */
	private String period;
	//rate in USD, can be even or with cents tail
	/** The rate. */
	private Float rate;
	
	/**
	 * Instantiates a new rate.
	 *
	 * @param period the period
	 * @param rate the rate
	 */
	public Rate (String period, Float rate)
	{
		this.period = period;
		this.rate = rate;
	}
	
	public Rate()
	{
		this.period = "";
		this.rate = (float) 0.0;
	}

	/**
	 * mutator method for period attribute.
	 *
	 * @param period the new period
	 */
	public void setPeriod ( String period )
	{
		this.period = period;
	}
	
	/**
	 * accessor method for period attribute.
	 *
	 * @return String
	 */
	public String getPeriod ()
	{
		return this.period;
	}
	
	/**
	 * mutator method for period attribute.
	 *
	 * @param rate the new rate
	 */
	public void setRate ( Float rate )
	{
		this.rate = rate;
	}
	
	/**
	 * accessor method for rate attribute.
	 *
	 * @return Float
	 */
	public Float getRate ()
	{
		return this.rate;
	}
	
}

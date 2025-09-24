package cscie97.common.squaredesk;


/**
 * The Class Address.
 */
public class Address

{
	//ISO format java.util.Locale
	/** The country code. */
	private String countryCode;
	
	/** The street1. */
	private String street1; 
	
	/** The street2. */
	private String street2;
	
	/** The city. */
	private String city;
	
	/** The state. */
	private String state;
	
	/** The zipcode. */
	private String zipcode;
	
	/**
	 * Instantiates a new address.
	 *
	 * @param countryCode the country code
	 * @param street1 the street1
	 * @param street2 the street2
	 * @param city the city
	 * @param state the state
	 * @param zipcode the zipcode
	 */
	public Address (String countryCode, String street1, String street2,
			        String city, String state, String zipcode)
	{
		this.countryCode = countryCode;
		this.street1 = street1;
		this.street2 = street2;
		this.city = city;
		this.state = state;
		this.zipcode = zipcode;
	}
	
	/**
	 * mutator method for countryCode attribute.
	 *
	 * @param countryCode the new country code
	 */
	public void setCountryCode ( String countryCode )
	{
		this.countryCode = countryCode;
	}
	
	/**
	 * accessor method for countryCode attribute.
	 *
	 * @return String
	 */
	public String getCountryCode ()
	{
		return this.countryCode;
	}
	
	/**
	 * mutator method for street1 attribute.
	 *
	 * @param street1 the new street1
	 */
	public void setStreet1 ( String street1 )
	{
		this.street1 = street1;
	}
	
	/**
	 * accessor method for street1 attribute.
	 *
	 * @return String
	 */
	public String getStreet1 ()
	{
		return this.street1;
	}
	
	/**
	 * mutator method for street2 attribute.
	 *
	 * @param street2 the new street2
	 */
	public void setStreet2 ( String street2 )
	{
		this.street2 = street2;
	}
	
	/**
	 * accessor method for street2 attribute.
	 *
	 * @return String
	 */
	public String getStreet2 ()
	{
		return this.street2;
	}
	
	/**
	 * mutator method for city attribute.
	 *
	 * @param city the new city
	 */
	public void setCity ( String city )
	{
		this.city = city;
	}
	
	/**
	 * accessor method for city attribute.
	 *
	 * @return String
	 */
	public String getCity ()
	{
		return this.city;
	}
	
	/**
	 * mutator method for state attribute.
	 *
	 * @param state the new state
	 */
	public void setState ( String state )
	{
		this.state = state;
	}
	
	/**
	 * accessor method for state attribute.
	 *
	 * @return String
	 */
	public String getState ()
	{
		return this.state;
	}
	
	/**
	 * mutator method for zipcode attribute.
	 *
	 * @param zipcode the new zipcode
	 */
	public void setZipcode ( String zipcode )
	{
		this.zipcode = zipcode;
	}
	
	/**
	 * accessor method for zipcode attribute.
	 *
	 * @return String
	 */
	public String getZipcode ()
	{
		return this.zipcode;
	}
	
}

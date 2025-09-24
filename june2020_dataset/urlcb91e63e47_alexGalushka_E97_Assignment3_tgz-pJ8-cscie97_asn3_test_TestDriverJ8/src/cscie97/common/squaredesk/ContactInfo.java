package cscie97.common.squaredesk;


/**
 * The Class ContactInfo.
 */
public class ContactInfo
{
	
	/** The first name. */
	private String firstName;
	
	/** The last name. */
	private String lastName;
	
	/** The email. */
	private String email;
	//the following format should be enforced: +1(617)-777-7777
	/** The phone number. */
	private String phoneNumber;
	
	/** The date of birth. */
	private String dateOfBirth;
	
	/** The address. */
	private Address address;
	
	
	/**
	 * Instantiates a new contact info.
	 *
	 * @param firstName the first name
	 * @param lastName the last name
	 * @param email the email
	 * @param phoneNumber the phone number
	 * @param dateOfBirth the date of birth
	 * @param address the address
	 */
	public ContactInfo (String firstName, String lastName, String email, 
						String phoneNumber, String dateOfBirth, Address address)
	{
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.dateOfBirth = dateOfBirth;
	}
	
	/**
	 * mutator method for firstName attribute.
	 *
	 * @param firstName the new first name
	 */
	public void setFirstName ( String firstName )
	{
		this.firstName = firstName;
	}
	
	/**
	 * accessor method for firstName attribute.
	 *
	 * @return String
	 */
	public String getFirstName ()
	{
		return this.firstName;
	}
	
	/**
	 * mutator method for firstName attribute.
	 *
	 * @param lastName the new last name
	 */
	public void setLastName ( String lastName )
	{
		this.lastName = lastName;
	}
	
	/**
	 * accessor method for lastName attribute.
	 *
	 * @return String
	 */
	public String getLastName ()
	{
		return this.lastName;
	}
	
	/**
	 * mutator method for email attribute.
	 *
	 * @param email the new email
	 */
	public void setEmail ( String email )
	{
		this.email = email;
	}
	
	/**
	 * accessor method for email attribute.
	 *
	 * @return String
	 */
	public String getEmail ()
	{
		return this.email;
	}
	
	/**
	 * mutator method for phoneNumber attribute.
	 *
	 * @param phoneNumber the new phone number
	 */
	public void setPhoneNumber ( String phoneNumber )
	{
		this.phoneNumber = phoneNumber;
	}
	
	/**
	 * accessor method for phoneNumber attribute.
	 *
	 * @return String
	 */
	public String getPhoneNumber ()
	{
		return this.phoneNumber;
	}
	
	/**
	 * mutator method for address attribute.
	 *
	 * @param address the new address
	 */
	public void setAddress ( Address address )
	{
		this.address = address;
	}
	
	/**
	 * accessor method for address attribute.
	 *
	 * @return Address
	 */
	public Address getAddress ()
	{
		return this.address;
	}
	
	/**
	 * mutator method for dateOfBirth attribute.
	 *
	 * @param dateOfBirth the new date of birth
	 */
	public void setDateOfBirth ( String dateOfBirth )
	{
		this.dateOfBirth = dateOfBirth;
	}
	
	/**
	 * accessor method for dateOfBirth attribute.
	 *
	 * @return String
	 */
	public String getDateOfBirth ()
	{
		return this.dateOfBirth;
	}
	
	
}

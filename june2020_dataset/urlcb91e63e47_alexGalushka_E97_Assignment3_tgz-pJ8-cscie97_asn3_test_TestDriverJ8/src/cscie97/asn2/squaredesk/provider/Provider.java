package cscie97.asn2.squaredesk.provider;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;




import cscie97.asn3.squaredesk.renter.Criteria;
import cscie97.common.squaredesk.Account;
import cscie97.common.squaredesk.ContactInfo;
import cscie97.common.squaredesk.Gender;
import cscie97.common.squaredesk.Profile;
import cscie97.common.squaredesk.Rating;


/**
 * The Class OfficeProvider.
 */
public class Provider implements Profile
{
	
	/** The office spaces map. */
	private Map<String, OfficeSpace> officeSpacesMap;
	
	/** The provider ratings map. */
	private Map<String, Rating> ratingsMap;
	
	private List<String> officeSpacesIds;
	
	/** The picture. */
	private URI picture;
	
	/** The contact. */
	private ContactInfo contact;
	
	/** The account. */
	private Account account;
	
	/** The office provider guid. */
	private String guid;
	
	/**
	 * Instantiates a new office provider.
	 */
	public Provider ()
	{
		this.officeSpacesMap = new HashMap<String, OfficeSpace>();
		this.ratingsMap = new HashMap<String, Rating>();
		this.guid = "";
		this.setOfficeSpacesIds(new LinkedList<String>());
	}
	
	/**
	 * Instantiates a new office provider.
	 * @param officeSpaces the office spaces
	 * @param guid the office provider guid
	 */
	public Provider ( URI picture, ContactInfo contact,
			          Map<String, OfficeSpace> officeSpaces, Account account )
	{
		this.officeSpacesMap = officeSpaces;
		this.guid = "";
		this.ratingsMap = new HashMap<String, Rating>();
	}

	
	/**
	 * @return the picture
	 */
	public URI getPicture()
	{
		return picture;
	}

	/**
	 * @param picture the picture to set
	 */
	public void setPicture(URI picture)
	{
		this.picture = picture;
	}

	/**
	 * @return the contact
	 */
	public ContactInfo getContact()
	{
		return contact;
	}

	/**
	 * @param contact the contact to set
	 */
	public void setContact(ContactInfo contact)
	{
		this.contact = contact;
	}

	/**
	 * @return the account
	 */
	public Account getAccount() 
	{
		return account;
	}

	/**
	 * @param account the account to set
	 */
	public void setAccount(Account account) {
		this.account = account;
	}

	/**
	 * mutator method for guid attribute.
	 *
	 * @return String
	 */
	public void setGuid ( String guid )
	{
		this.guid = guid;
	}
	
	/**
	 * accessor method for guid attribute.
	 *
	 * @return String
	 */
	public String getGuid ()
	{
		return this.guid;
	}
	
	/**
	 * mutator method for officeSpacesMap attribute.
	 *
	 * @param officeSpacesMap the office spaces map
	 */
	public void setOfficeSpaces ( Map<String, OfficeSpace> officeSpacesMap )
	{
		this.officeSpacesMap = officeSpacesMap;
	}
	
	/**
	 * accessor method for officeSpacesMap attribute.
	 *
	 * @return Map<String, OfficeSpace>
	 */
	public Map<String, OfficeSpace> getOfficeSpaces ()
	{
		return this.officeSpacesMap;
	}

	
	public List<OfficeSpace> getOfficeSpacesList()
	{
		List<OfficeSpace> tempListOfficeSpace = new LinkedList<OfficeSpace>();
		for (OfficeSpace os : this.officeSpacesMap.values() )
		{
			tempListOfficeSpace.add( os );
		}
		return tempListOfficeSpace;
	}
	/**
	 * mutator method for ratingsMap attribute.
	 *
	 * @param ratingsMap the provider ratings map
	 */
	public void setRatingsMap ( Map<String, Rating> ratingsMap )
	{
		this.ratingsMap = ratingsMap;
	}
	
	/**
	 * accessor method for ratingsMap attribute.
	 *
	 * @return Map<String, Rating>
	 */
	public Map<String, Rating> getRatingsMap ()
	{
		return this.ratingsMap;
	}
	
	/**
	 * getter method for all ratings per office provider.
	 *
	 * @return List<Rating>
	 */
	public List<Rating> getAllRatings()
	{
		Collection<Rating> tempSet;
		tempSet = ratingsMap.values();
		List<Rating> officeProviderRatingsList = new ArrayList<Rating> ( tempSet );
		return officeProviderRatingsList;
	}

	/**
	 * @return the officeSpacesIds
	 */
	public List<String> getOfficeSpacesIds()
	{
		return officeSpacesIds;
	}

	/**
	 * @param officeSpacesIds the officeSpacesIds to set
	 */
	public void setOfficeSpacesIds( List<String> officeSpacesIds )
	{
		this.officeSpacesIds = officeSpacesIds;
	}
	
	
	/**
	 * add officeSpace Id to officeSpacesIds
	 * @param String: officeSpacesId
	 */
	public void addOfficeSpacesIdToList( String officeSpacesId )
	{
		this.officeSpacesIds.add( officeSpacesId );
	}

	@Override
	public void setCriteria(Criteria uutCriteria)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setGender(Gender female)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public Criteria getCriteria() {
		// TODO Auto-generated method stub
		return null;
	}

}

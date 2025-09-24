package cscie97.common.squaredesk;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class User
{
	/** The picture. */
	private URI picture;
	
	/** The contact. */
	private ContactInfo contact;
	
	/** The account. */
	private Account account;
	
	/** The office provider guid. */
	private String guid;
	
	/** List of Profile: User can be Provider, Renter or both */
	private Map<String, Profile> profileMap;
	
	public User()
	{
		picture = null;
		contact = null;
		account = null;
		guid = GuidGenerator.getInstance().generateProviderGuid();
		profileMap = new HashMap<String, Profile>();
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
	 * adds a new profile to the Map of Profiles
	 * @param type
	 * @param profile
	 * @throws ProfileAlreadyExistsException 
	 */
	public void addProfile (String type, Profile profile) throws ProfileAlreadyExistsException
	{
		if ( !profileMap.containsKey(type) )
		{
			profileMap.put(type, profile);
		}
		else
		{
			throw new ProfileAlreadyExistsException();
		}
					
	}
	
	/**
	 * Profile getter method
	 * @param type
	 * @throws ProfileNotFoundException
	 */
	public Profile  getProfile (String type) throws ProfileNotFoundException 
	{
		if ( profileMap.containsKey(type) )
		{
			return profileMap.get(type);
		}
		else
		{
			throw new ProfileNotFoundException();
		}
	}
	
	/**
	 * delete profile from the Map of Profiles
	 * @throws ProfileNotFoundException 
	 * @throws ProfileAlreadyExistsException 
	 */
	
	public void deleteProfile (String type) throws ProfileNotFoundException 
	{
		if ( profileMap.containsKey(type) )
		{
			profileMap.remove(type);
		}
		else
		{
			throw new ProfileNotFoundException();
		}
		
	}
	
	/**
	 * update profile of the Map of Profiles
	 * @throws ProfileNotFoundException 
	 * @throws ProfileAlreadyExistsException 
	 */
	
	public void updateProfile (String type, Profile profile) throws ProfileNotFoundException 
	{
		if ( profileMap.containsKey(type) )
		{
			profileMap.put(type, profile);
		}
		else
		{
			throw new ProfileNotFoundException();
		}
		
	}
	
	/**
	 * mutator method for picture attribute.
	 *
	 * @param picture the new picture
	 */
	public void setPicture ( URI picture )
	{
		this.picture = picture;
	}
	
	/**
	 * accessor method for picture attribute.
	 *
	 * @return URI
	 */
	public URI getPicture ()
	{
		return this.picture;
	}
	
	/**
	 * mutator method for contact attribute.
	 *
	 * @param contact the new contact
	 */
	public void setContact ( ContactInfo contact )
	{
		this.contact = contact;
	}
	
	/**
	 * accessor method for contact attribute.
	 *
	 * @return ContactInfo
	 */
	public ContactInfo getContact ()
	{
		return this.contact;
	}
	
	/**
	 * mutator method for account attribute.
	 *
	 * @param account the new account
	 */
	public void setAccount ( Account account )
	{
		this.account = account;
	}
	
	/**
	 * accessor method for account attribute.
	 *
	 * @return Account
	 */
	public Account getAccount ()
	{
		return this.account;
	}


	/**
	 * @return the profileMap
	 */
	public Map<String, Profile> getProfileMap() {
		return profileMap;
	}


	/**
	 * @param profileMap the profileMap to set
	 */
	public void setProfileMap(Map<String, Profile> profileMap)
	{
		this.profileMap = profileMap;
	}
	
}

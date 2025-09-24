package cscie97.common.squaredesk;

import java.net.URI;
import java.util.List;
import java.util.Map;

import cscie97.asn2.squaredesk.provider.OfficeSpace;
import cscie97.asn3.squaredesk.renter.Criteria;
import cscie97.common.squaredesk.Rating;

public interface Profile
{
	
	/**
	 * accessor method for officeProviderGuid attribute.
	 *
	 * @return String
	 */
	public String getGuid ();
	
	public void setGuid ( String guid );
	
	public void setOfficeSpaces ( Map<String, OfficeSpace> officeSpacesMap );
	
	/**
	 * accessor method for officeSpacesMap attribute.
	 *
	 * @return Map<String, OfficeSpace>
	 */
	public Map<String, OfficeSpace> getOfficeSpaces ();
	
	public List<OfficeSpace> getOfficeSpacesList();

	/**
	 * mutator method for providerRatingsMap attribute.
	 *
	 * @param providerRatingsMap the provider ratings map
	 */
	public void setRatingsMap ( Map<String, Rating> ratingsMap );
	
	/**
	 * accessor method for providerRatingsMap attribute.
	 *
	 * @return Map<String, Rating>
	 */
	public Map<String, Rating> getRatingsMap ();
	
	/**
	 * getter method for all ratings per office provider.
	 *
	 * @return List<Rating>
	 */
	public List<Rating> getAllRatings();
	
	/**
	 * @return the officeSpacesIds
	 */
	public List<String> getOfficeSpacesIds();

	/**
	 * @param officeSpacesIds the officeSpacesIds to set
	 */
	public void setOfficeSpacesIds(List<String> officeSpacesIds);
	
	/**
	 * add officeSpace Id to officeSpacesIds
	 * @param String: officeSpacesId
	 */
	public void addOfficeSpacesIdToList( String officeSpacesId );
	
	/**
	 * @return the picture
	 */
	public URI getPicture();

	/**
	 * @param picture the picture to set
	 */
	public void setPicture(URI picture);

	/**
	 * @return the contact
	 */
	public ContactInfo getContact();

	/**
	 * @param contact the contact to set
	 */
	public void setContact(ContactInfo contact);

	/**
	 * @return the account
	 */
	public Account getAccount();

	/**
	 * @param account the account to set
	 */
	public void setAccount(Account account);

	public void setCriteria(Criteria uutCriteria);

	public void setGender(Gender female);

	public Criteria getCriteria();
}

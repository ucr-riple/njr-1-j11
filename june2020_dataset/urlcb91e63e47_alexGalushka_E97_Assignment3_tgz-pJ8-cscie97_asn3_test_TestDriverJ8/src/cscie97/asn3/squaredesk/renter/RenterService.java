package cscie97.asn3.squaredesk.renter;

import java.util.List;
import java.util.Map;

import cscie97.asn2.squaredesk.provider.OfficeSpaceNotFoundException;
import cscie97.common.squaredesk.Profile;
import cscie97.common.squaredesk.ProfileAlreadyExistsException;
import cscie97.common.squaredesk.ProfileNotFoundException;
import cscie97.common.squaredesk.Rate;
import cscie97.common.squaredesk.Rating;
import cscie97.common.squaredesk.RatingAlreadyExistsException;
import cscie97.common.squaredesk.RatingNotFoundExcepion;
import cscie97.common.squaredesk.User;

public interface RenterService
{

	public Boolean bookOfficeSpace ( String authToken, Renter renter, Rate rate, PaymentStatus paymentStatus ) throws BookingException;
	
	/**
	 * accessor method
	 * @return the renterMap
	 */
	public Map<String, User> getRenterMap();


	/**
	 * mutator method
	 * @param renterMap the renterMap to set
	 */
	public void setRenterMap(Map<String, User> renterMap);
	
	/**
	 * creates are new Renter
	 * @param authToken
	 * @param profile
	 * @return
	 * @throws ProfileAlreadyExistsException
	 */
	public String createRenter ( String authToken, Profile  profile ) throws ProfileAlreadyExistsException;
	
	/**
	 * returns renter Profile
	 * @param authToken
	 * @param renterId
	 * @return
	 * @throws ProfileNotFoundException
	 */
	public Profile getRenter( String authToken, String renterId ) throws ProfileNotFoundException;
	
	/**
	 * Returns whole list of renters.
	 *
	 * @param authToken the auth token
	 * @return List<Renter>
	 */
	public List<Profile> getRenterList ( String authToken );
	
	/**
	 * Updates the renter, new renter instance has to be passed in.
	 * If renterId not found, throws ProfileNotFoundException.
	 *
	 * @param authToken the auth token
	 * @param renterId the renter id
	 * @param renter the renter
	 * @throws ProfileNotFoundException the renter not found exception
	 */
	public void updateRenter ( String authToken, Profile renter ) throws ProfileNotFoundException;
	
	/**
	 * Deleted the renter.
	 * If renterId not found, throws ProfileNotFoundException.
	 * @param authToken the auth token
	 * @param renterId the renter id
	 * @throws ProfileNotFoundException the renter not found exception
	 * @throws OfficeSpaceNotFoundException 
	 */
	public void deleteRenter ( String authToken, String renterId ) throws ProfileNotFoundException;
	
	/**
	 * Rate the renter. Rating is an integer from 0 to 5. The rating value is added to officeRenterRatingsMap.
	 * if it is found throw RatingAlreadyExistsException. renterId is checked as well if it's not found
	 *  - ProfileNotFoundException is thrown 
	 *
	 * @param authToken the auth token
	 * @param renterId the renter id
	 * @param providerId the provider id
	 * @param rating the rating
	 * @throws RatingAlreadyExistsException the rating already exists exception
	 * @throws ProfileNotFoundException the renter not found exception
	 */
	public void rateRenter ( String authToken, String renterId,
			                 String providerId , Rating rating ) throws RatingAlreadyExistsException, ProfileNotFoundException;

	
	/**
	 * The Rating correspondent to providerId is to be removed from officeProviderRatingMap within the officeSpaceMap,
	 * if office space id is not found - ProfileNotFoundException is thrown;
	 * if renterId is not found - RatingNotFoundExcepion is thrown.
	 *
	 * @param authToken the auth token
	 * @param providerId the provider id
	 * @param renterId the renter id
	 * @throws RatingNotFoundExcepion the rating not found excepion
	 * @throws ProfileNotFoundException the provider not found exception
	 */
	public void removeRenterRating ( String authToken, String renterId,
			                           String providerId) throws RatingNotFoundExcepion, ProfileNotFoundException;
	
	/**
	 * Gets the rating list.
	 *
	 * @param authToken the auth token
	 * @param renterId the renter id
	 * @return the rating list
	 * @throws OfficeSpaceNotFoundException the office space not found exception
	 * @throws ProfileNotFoundException 
	 */
	public List<Rating> getRatingList ( String authToken, String renterId ) throws OfficeSpaceNotFoundException, ProfileNotFoundException;
	
}

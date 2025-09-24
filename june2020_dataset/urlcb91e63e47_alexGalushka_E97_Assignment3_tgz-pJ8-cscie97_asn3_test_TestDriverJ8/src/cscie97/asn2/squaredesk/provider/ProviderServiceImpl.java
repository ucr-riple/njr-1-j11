package cscie97.asn2.squaredesk.provider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cscie97.asn3.squaredesk.renter.Observer;
import cscie97.asn3.squaredesk.renter.Subject;
import cscie97.common.squaredesk.AccessException;
import cscie97.common.squaredesk.Profile;
import cscie97.common.squaredesk.ProfileAlreadyExistsException;
import cscie97.common.squaredesk.ProfileNotFoundException;
import cscie97.common.squaredesk.Rating;
import cscie97.common.squaredesk.RatingAlreadyExistsException;
import cscie97.common.squaredesk.RatingNotFoundExcepion;
import cscie97.common.squaredesk.User;
import cscie97.common.squaredesk.UserBucket;


public class ProviderServiceImpl implements ProviderService, Subject
{

	private UserBucket userBucket;
	
	/** The office space map. */
	private Map<String, OfficeSpace> officeSpaceMap;	
	private Map<String, List<OfficeSpace>> officeSpaceMapByProvider;
	private static ProviderServiceImpl _obj;
	private ArrayList<Observer> observers;
	
    private ProviderServiceImpl ()
    {
		officeSpaceMap = new HashMap<String, OfficeSpace>();
		officeSpaceMapByProvider = new HashMap<String, List<OfficeSpace>>();
		userBucket = UserBucket.getInstance();
		observers = new ArrayList<Observer>();
		notifyObservers();
    }
     
    
    /**
     * A special static method to access the single ProviderServiceImpl instance
     * @return _obj - type: ProviderServiceImpl
     */
    public static ProviderServiceImpl getInstance() 
    {
    	//Checking if the instance is null, then it will create new one and return it
        if (_obj == null)  
        //otherwise it will return previous one.
        {
            _obj = new ProviderServiceImpl();
        }
        return _obj;
    }
    
    
	
	/**
	 * Creates a new provider: add provider to the providerMap;
	 * check if it exists already and throws ProviderAlreadyExistException if it does.
	 *
	 * @param authToken the auth token
	 * @param provider the provider
	 * @throws ProfileAlreadyExistsException 
	 * @throws ProviderAlreadyExistsException the provider already exists exception
	 * @throws ProfileNotFoundException 
	 */
	public String createProvider ( String authToken, Profile  profile ) throws ProfileAlreadyExistsException 
	{
		User user = new User();
		String userId = user.getGuid();
		profile.setGuid( userId );
		user.setAccount( profile.getAccount() );
		user.setContact( profile.getContact() );
		user.setPicture( profile.getPicture() );
		user.addProfile( "provider" , profile );
		userBucket.createUser( user );
		notifyObservers();
		return userId;
	}
	
	/**
	 * Returns provider per passed in providerId,
	 * if there is no match – throws ProfileNotFoundException .
	 *
	 * @param authToken the auth token
	 * @param providerId the provider id
	 * @return the provider
	 * @throws ProfileNotFoundException the provider not found exception
	 */
	public Profile getProvider( String authToken, String providerId ) throws ProfileNotFoundException
	{
		User user = null;
		Profile profile = null;
		user = userBucket.getUser( providerId );
		if ( user == null )
		{
			throw new ProfileNotFoundException ( "no user profile found" );
		}
		else
		{
			profile = user.getProfile( "provider" );
		}
		return profile;
	}
	
	/**
	 * Returns whole list of providers.
	 *
	 * @param authToken the auth token
	 * @return List<OfficeProvider>
	 */
	public List<Profile> getProviderList ( String authToken )
	{
		List<Profile> result = new LinkedList<Profile>();
		Map<String, User> userBucketMap = userBucket.getUserMap();
		Collection<User> tempSet;
		tempSet = userBucketMap.values();	
		for (User u : tempSet )
		{
			try
			{
				result.add( u.getProfile( "provider" ) );
			} 
			catch (ProfileNotFoundException e)
			{
				// continue through the loop, this exception is not critical here
			}
		}
		
		return result;
	}
	
	/**
	 * Updates the provider, new Provider instance has to be passed in.
	 * If providerId not found, throws ProfileNotFoundException.
	 *
	 * @param authToken the auth token
	 * @param providerId the provider id
	 * @param provider the provider
	 * @throws ProfileNotFoundException the provider not found exception
	 */
	public void updateProvider ( String authToken, Profile provider ) throws ProfileNotFoundException
	{
		User user = null;
		String providerId = provider.getGuid();
		user = userBucket.getUser( providerId );
		if ( user != null )
		{
			user.updateProfile( "provider", provider );
			userBucket.updateUser( providerId, user );
		}
		else
		{
			throw new ProfileNotFoundException();
		}
		notifyObservers();
	}
	
	/**
	 * Deleted the provider.
	 * If providerId not found, throws ProfileNotFoundException.
	 *
	 * @param authToken the auth token
	 * @param providerId the provider id
	 * @throws ProfileNotFoundException the provider not found exception
	 * @throws OfficeSpaceNotFoundException 
	 */
	public void deleteProvider ( String authToken, String providerId ) throws ProfileNotFoundException, OfficeSpaceNotFoundException
	{
		User user = null;
		user = userBucket.getUser( providerId );
		if ( user != null )
		{
			for (String id : user.getProfile( "provider" ).getOfficeSpacesIds() )
			{
				removeOfficeSpace( "", providerId, id );
			}
			user.updateProfile( "provider", null );
			userBucket.updateUser( providerId, user );

		}
		else
		{
			throw new ProfileNotFoundException();
		}
		notifyObservers();
	}
	
	/**
	 * Rate the provider. Rating is an integer from 0 to 5. The rating value is added to officeProviderRatingsMap.
	 * if it is found throw RatingAlreadyExistsException. ProviderId is checked as well if it's not found
	 *  - ProfileNotFoundException is thrown 
	 *
	 * @param authToken the auth token
	 * @param providerId the provider id
	 * @param renterId the renter id
	 * @param rating the rating
	 * @throws RatingAlreadyExistsException the rating already exists exception
	 * @throws ProfileNotFoundException the provider not found exception
	 */
	public void rateProvider ( String authToken, String providerId,
			                   String renterId , Rating rating ) throws RatingAlreadyExistsException, ProfileNotFoundException 
	{
		if ( userBucket.getUserMap().containsKey( providerId ) )
		{
			User tempUser = userBucket.getUser( providerId );
			Profile tempProvider = tempUser.getProfile("provider");
			Map<String, Rating>  tempProviderRatingMap = tempProvider.getRatingsMap();
			if ( !tempProviderRatingMap.containsKey( renterId ) )
			{
				tempProviderRatingMap.put( renterId, rating );
				tempProvider.setRatingsMap ( tempProviderRatingMap );
				tempUser.updateProfile("provider", tempProvider);
				userBucket.updateUser( providerId, tempUser );
			}
			else
			{
				throw new RatingAlreadyExistsException();
			}

		}
		else
		{
			throw new ProfileNotFoundException();
		}
		notifyObservers();
	}
	
	/**
	 * The Rating correspondent to renterId is to be removed from officeProviderRatingMap within the officeSpaceMap,
	 * if office space id is not found - ProfileNotFoundException is thrown;
	 * if renterId is not found - RatingNotFoundExcepion is thrown.
	 *
	 * @param authToken the auth token
	 * @param providerId the provider id
	 * @param renterId the renter id
	 * @throws RatingNotFoundExcepion the rating not found excepion
	 * @throws ProfileNotFoundException the provider not found exception
	 */
	public void removeProviderRating ( String authToken, String providerId,
			                           String renterId) throws RatingNotFoundExcepion, ProfileNotFoundException
	{
		if (userBucket.getUserMap().containsKey( providerId ) )
		{
            User tempUser = userBucket.getUser( providerId );
            Profile tempProvider = tempUser.getProfile("provider");
            Map<String, Rating> tempProviderRatingMap = tempProvider.getRatingsMap();
			if ( tempProviderRatingMap.containsKey( renterId ) )
			{
				tempProviderRatingMap.remove( renterId );
			}
			else
			{
				throw new RatingNotFoundExcepion();
			}
			tempProvider.setRatingsMap ( tempProviderRatingMap );
			tempUser.updateProfile("provider", tempProvider);
			userBucket.updateUser( providerId, tempUser );
			notifyObservers();
		}
		else
		{
			throw new ProfileNotFoundException();
		}
	}
	
	/**
	 * Gets the rating list.
	 *
	 * @param authToken the auth token
	 * @param providerId the provider id
	 * @return the rating list
	 * @throws OfficeSpaceNotFoundException the office space not found exception
	 * @throws ProfileNotFoundException 
	 */
	public List<Rating> getRatingList ( String authToken, String providerId ) throws OfficeSpaceNotFoundException, ProfileNotFoundException
	{
		if ( userBucket.getUserMap().containsKey( providerId ) )
		{
			User tempUser = userBucket.getUser( providerId );
			List<Rating> tempProviderRatingList;
			tempProviderRatingList = tempUser.getProfile("provider").getAllRatings();
			return tempProviderRatingList;
		}
		else
		{
			throw new OfficeSpaceNotFoundException();
		}
	}
	
	/**
	 * Creates a new OfficeSpace: add office space to officeSpaceMap; check if it exists already
	 * and throws OfficeSpaceAlreadyExistException if it is. If authentication fails - throw AccessException.
	 * Rating Field is initialized here.
	 * Note: officeSpaceId has to be generated first! Check for officeSpaceIds and providerId in the id buckets,
	 * if this check fails throw the 
	 *
	 * @param authToken the auth token
	 * @param officeSpace the office space
	 * @param guid the guid of the OfficeSpace
	 * @throws OfficeSpaceAlreadyExistException the office space already exist exception
	 * @throws AccessException the access exception
	 */
	public void createOfficeSpace ( String authToken, OfficeSpace officeSpace, String providerId )
			                        throws OfficeSpaceAlreadyExistException, AccessException
	{
		String officeSpaceId = officeSpace.getOfficeSpaceGuid();
		if ( !officeSpaceMap.containsKey( officeSpaceId ) )
		{
			officeSpaceMap.put( officeSpaceId, officeSpace );
		}
		else
		{
			throw new OfficeSpaceAlreadyExistException();
		}
		
		// put the OfficeSpace in the map with a key as a provider ID (officeSpaceMapByProvider)
		
		List<OfficeSpace> tempList;
		if ( !officeSpaceMapByProvider.containsKey( providerId ) )
		{
			tempList = new LinkedList<OfficeSpace>();
			tempList.add( officeSpace );
			officeSpaceMapByProvider.put( providerId, tempList );
		}
		else
		{
			tempList = officeSpaceMapByProvider.get( providerId );
			tempList.add( officeSpace );
			officeSpaceMapByProvider.put( providerId, tempList );
		}
		notifyObservers();
	}
	
	/**
	 * accessor method for officeSpaceMap attribute
	 * if the guid not found in the map, it throws OfficeSpaceNotFoundException exception.
	 *
	 * @param authToken the auth token
	 * @param guid the guid
	 * @return OfficeSpace
	 * @throws OfficeSpaceNotFoundException the office space not found exception
	 */
	public OfficeSpace getOfficeSpace ( String authToken, String guid ) throws OfficeSpaceNotFoundException
	{
		if ( officeSpaceMap.containsKey( guid ) )
		{
			return this.officeSpaceMap.get( guid );
		}
		else
		{
			throw new OfficeSpaceNotFoundException();
		}
	}
	
	/**
	 * Gets the office space list.
	 *
	 * @return all values (office spaces) from officeSpaceMap
	 */
	public List<OfficeSpace> getOfficeSpaceList ()
	{
		List<OfficeSpace> officeSpaceList = null;
		officeSpaceList = (List<OfficeSpace>) officeSpaceMap.values();
		return officeSpaceList;
	}
	
	/**
	 * Gets the office space guid list.
	 *
	 * @return List<String>
	 */
	public List<String> getOfficeSpaceGuidList ()
	{
		Set<String> tempSet;
		tempSet = officeSpaceMap.keySet();
		List<String> officeSpaceGuidList = new ArrayList<String> ( tempSet );
		return officeSpaceGuidList;
	}
	
	/**
	 * updates particular office space in the office space map with a new office space based on guid passed in
	 * if the guid not found in the map, it throws OfficeSpaceNotFoundException exception.
	 *
	 * @param authToken the auth token
	 * @param guid the guid
	 * @param officeSpaceId the office space id
	 * @param updatedOffice the updated office
	 * @throws OfficeSpaceNotFoundException the office space not found exception
	 */
	public void updateOfficeSpace ( String authToken, String providerId,
			                        OfficeSpace updatedOffice) throws OfficeSpaceNotFoundException
	{
		String officeSpaceId = updatedOffice.getOfficeSpaceGuid();
		if ( officeSpaceMap.containsKey( officeSpaceId ) )
		{
			officeSpaceMap.put( officeSpaceId, updatedOffice );
		}
		else
		{
			throw new OfficeSpaceNotFoundException();
		}
		List<OfficeSpace> tempList;
		if ( officeSpaceMapByProvider.containsKey( providerId ) )
		{
			tempList = officeSpaceMapByProvider.get( providerId );
			tempList.add( updatedOffice );
			officeSpaceMapByProvider.put( providerId, tempList );
		}
		else
		{
			throw new OfficeSpaceNotFoundException();
		}
		notifyObservers();
	}
	
	/**
	 * removed particular office space from the office space map based on guid passed in
	 * if the guid not found in the map, it throws OfficeSpaceNotFoundException exception.
	 *
	 * @param authToken the auth token
	 * @param guid the guid
	 * @param officeSpaceId the office space id
	 * @param updatedOffice the updated office
	 * @throws OfficeSpaceNotFoundException the office space not found exception
	 */
	public void removeOfficeSpace ( String authToken, String providerId,
                                    String officeSpaceId ) throws OfficeSpaceNotFoundException
	{
		List<OfficeSpace> tempList;
		OfficeSpace tempOfficeSpace = officeSpaceMap.get( officeSpaceId );
		if ( officeSpaceMapByProvider.containsKey( providerId ) )
		{
			tempList = officeSpaceMapByProvider.get( providerId );
			tempList.remove(tempOfficeSpace);
			officeSpaceMapByProvider.put( providerId, tempList );
		}
		else
		{
			throw new OfficeSpaceNotFoundException();
		}
		
		if ( officeSpaceMap.containsKey( officeSpaceId ) )
		{
			officeSpaceMap.remove( officeSpaceId );
		}
		else
		{
			throw new OfficeSpaceNotFoundException();
		}
		notifyObservers();
	}	
	
	/**
	 * The new Rating is added to officeSpaceRatingMap within the officeSpaceMap,
	 * if the office space id is not found - OfficeSpaceNotFoundException is thrown;
	 * if the rater already provided his/her rating - RatingAlreadyExistsException is thrown.
	 *
	 * @param authToken the auth token
	 * @param renterId the renter id
	 * @param officeSpaceId the office space id
	 * @param rating the rating
	 * @throws OfficeSpaceNotFoundException the office space not found exception
	 * @throws RatingAlreadyExistsException the rating already exists exception
	 */
	public void rateOfficeSpace ( String authToken, String renterId,
			                      String officeSpaceId, Rating rating ) throws OfficeSpaceNotFoundException, RatingAlreadyExistsException 
	{
		if ( officeSpaceMap.containsKey( officeSpaceId ) )
		{
			OfficeSpace tempOfficeSpace;
            Map<String, Rating> tempOfficeSpaceRatingMap;
			tempOfficeSpace = officeSpaceMap.get( officeSpaceId );
			tempOfficeSpaceRatingMap = tempOfficeSpace.getRatings();
			if ( !tempOfficeSpaceRatingMap.containsKey( renterId ) )
			{
				tempOfficeSpaceRatingMap.put(renterId, rating );
			}
			else
			{
				throw new RatingAlreadyExistsException();
			}
			tempOfficeSpace.setRatings( tempOfficeSpaceRatingMap );
			officeSpaceMap.put( officeSpaceId, tempOfficeSpace );
		}
		else
		{
			throw new OfficeSpaceNotFoundException();
		}
		notifyObservers();
	}
	
	/**
	 * The Rating correspondent to renterId is to be removed from officeSpaceRatingMap within the officeSpaceMap,
	 * if office space id is not found - OfficeSpaceNotFoundException is thrown;
	 * if renterId is not found - RatingNotFoundExcepion is thrown.
	 *
	 * @param authToken the auth token
	 * @param renterId the renter id
	 * @param officeSpaceId the office space id
	 * @throws OfficeSpaceNotFoundException the office space not found exception
	 * @throws RatingNotFoundExcepion the rating not found excepion
	 */
	public void removeOfficeSpaceRating ( String authToken, String renterId,
                                          String officeSpaceId ) throws OfficeSpaceNotFoundException, RatingNotFoundExcepion 
    {
		if ( officeSpaceMap.containsKey( officeSpaceId ) )
		{
			OfficeSpace tempOfficeSpace;
            Map<String, Rating> tempOfficeSpaceRatingMap;
			tempOfficeSpace = officeSpaceMap.get( officeSpaceId );
			tempOfficeSpaceRatingMap = tempOfficeSpace.getRatings();
			if ( tempOfficeSpaceRatingMap.containsKey( renterId ) )
			{
				tempOfficeSpaceRatingMap.remove( renterId );
			}
			else
			{
				throw new RatingNotFoundExcepion();
			}
			tempOfficeSpace.setRatings( tempOfficeSpaceRatingMap );
			officeSpaceMap.put( officeSpaceId, tempOfficeSpace );
		}
		else
		{
			throw new OfficeSpaceNotFoundException();
		}
		notifyObservers();
    }
	
	
	/**
	 * Returns the list all Ratings correspondent to office space the officeSpaceMap,
	 * if the office space id is not found - OfficeSpaceNotFoundException is thrown.
	 *
	 * @param authToken the auth token
	 * @param officeSpaceId the office space id
	 * @return List<Rating>
	 * @throws OfficeSpaceNotFoundException the office space not found exception
	 */
	public List<Rating> getOfficeSpaceRatingList  ( String authToken, String officeSpaceId ) throws OfficeSpaceNotFoundException 
	{
		if ( officeSpaceMap.containsKey( officeSpaceId ) )
		{
			OfficeSpace tempOfficeSpace;
			tempOfficeSpace = officeSpaceMap.get( officeSpaceId );
			List<Rating> tempOfficeSpaceRatingList;
			tempOfficeSpaceRatingList = tempOfficeSpace.getAllRatings();
			return tempOfficeSpaceRatingList;
		}
		else
		{
			throw new OfficeSpaceNotFoundException();
		}
		
	}

    /**
     * Observer's pattern method to add Observer to the list of Observers
     */
	public void registerObserver(Observer observer)
	{
		observers.add(observer);
		
	}

    /**
     * Observer's pattern method to remove Observer from the list of Observers
     */
	public void removeObserver(Observer observer) 
	{
		observers.remove(observer);
		
	}

    /**
     * Observer's pattern method to notify all the registered observers
     */
	public void notifyObservers() 
	{
		for (Observer ob : observers)
		{
            ob.syncUpdate();
        }
	}
    	 
}
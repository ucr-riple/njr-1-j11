package cscie97.asn2.squaredesk.provider;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cscie97.common.squaredesk.Capacity;
import cscie97.common.squaredesk.Facility;
import cscie97.common.squaredesk.Features;
import cscie97.common.squaredesk.GuidGenerator;
import cscie97.common.squaredesk.Image;
import cscie97.common.squaredesk.Location;
import cscie97.common.squaredesk.Rate;
import cscie97.common.squaredesk.Rating;
import cscie97.common.squaredesk.RatingNotFoundExcepion;


/**
 * The Class OfficeSpace.
 */
public class OfficeSpace
{
	
	/** The common access. */
	private List<String> commonAccess;
	
	/** The capacity. */
	private Capacity capacity;
	
	/** The facility. */
	private Facility facility;
	
	/** The features. */
	private Features features;
	
	/** The images. */
	private List<Image> images;
	
	/** The location. */
	private Location location;
	
	/** The rates. */
	private List<Rate> rates;
	
	/** The ratings. */
	private Map<String, Rating> ratings;
	
	/** The office space guid. */
	private String officeSpaceGuid;
	//office space name
	/** The name. */
	private String name; 
	
	private String providerId;
	
	/**
	 * Instantiates a new office space.
	 *
	 * @param commonAccess the common access
	 * @param capacity the capacity
	 * @param facility the facility
	 * @param features the features
	 * @param images the images
	 * @param location the location
	 * @param rates the rates
	 * @param ratings the ratings
	 * @param name the name
	 * @param officeSpaceGuid the office space guid
	 */
	public OfficeSpace ( List<String> commonAccess, Capacity capacity, Facility facility,
			            Features features, List<Image> images, Location location,
			            List<Rate> rates, Map<String, Rating> ratings, String name )
	{
		this.capacity = capacity;
		this.commonAccess = commonAccess;
		this.facility = facility;
		this.features = features;
		this.images = images;
		this.location = location;
		this.name = name;
		this.rates = rates;
		this.ratings = ratings;
		this.officeSpaceGuid = GuidGenerator.getInstance().generateProviderGuid();;
	}
	
	
	/**
	 * Instantiates a new office space.
	 */
	public OfficeSpace ()
	{
		this.capacity = new Capacity( null, null, null );
		this.commonAccess = new ArrayList<String>();
		this.facility = new Facility( null, null );
		this.features = new Features();
		this.images = new ArrayList<Image>();
		this.location = new Location(null, null, null);
		this.name = "";
		this.officeSpaceGuid = GuidGenerator.getInstance().generateProviderGuid();
		this.rates =  new ArrayList<Rate>();
		this.ratings = new HashMap<String, Rating>();
	}
	
	/**
	 * accessor method
	 * @return the providerId
	 */
	public String getProviderId()
	{
		return providerId;
	}


	/**
	 * mutator method
	 * @param providerId the providerId to set
	 */
	public void setProviderId(String providerId)
	{
		this.providerId = providerId;
	}
	
	/**
	 * accessor method for officeSpaceGuid attribute.
	 *
	 * @return String
	 */
	public String getOfficeSpaceGuid ()
	{
		return this.officeSpaceGuid;
	}
	
	
	/**
	 * mutator method for commonAccess attribute.
	 *
	 * @param commonAccess the new common access
	 */
	public void setCommonAccess ( List<String> commonAccess )
	{
		this.commonAccess = commonAccess;
	}
	
	/**
	 * accessor method for commonAccess attribute.
	 *
	 * @return commonAccess
	 */
	public List<String> getCommonAccess()
	{
		return this.commonAccess;
	}
	
	public List<String> getTranslatedCommonAccessList()
	{
		List<String> result = new ArrayList<String>();
		String tempCommAccess = "";
		for ( String com:commonAccess )
		{
			tempCommAccess = "hasAccessTo_"+com;
			result.add( tempCommAccess );
		}
		return result;
	}
	
	/**
	 * mutator method for capacity attribute.
	 *
	 * @param capacity the new capacity
	 */
	public void setCapacity ( Capacity capacity )
	{
		this.capacity = capacity;
	}
	
	/**
	 * accessor method for capacity attribute.
	 *
	 * @return Capacity
	 */
	public Capacity getCapacity()
	{
		return this.capacity;
	}
	
	/**
	 * mutator method for facility attribute.
	 *
	 * @param facility the new facility
	 */
	public void setFacility ( Facility facility )
	{
		this.facility = facility;
	}
	
	/**
	 * accessor method for facility attribute.
	 *
	 * @return Facility
	 */
	public Facility getFacility()
	{
		return this.facility;
	}
	
	/**
	 * mutator method for features attribute.
	 *
	 * @param features the new features
	 */
	public void setFeatures ( Features features )
	{
		this.features = features;
	}
	
	/**
	 * accessor method for features attribute.
	 *
	 * @return Features
	 */
	public Features getFeatures()
	{
		return this.features;
	}
	

	/**
	 * mutator method for images attribute.
	 *
	 * @param images the new images
	 */
	public void setImages ( List<Image> images )
	{
		this.images = images;
	}
	
	/**
	 * accessor method for images attribute.
	 *
	 * @return List<Image>
	 */
	public List<Image> getImages()
	{
		return this.images;
	}
	
	/**
	 * mutator method for location attribute.
	 *
	 * @param location the new location
	 */
	public void setLocation ( Location location )
	{
		this.location = location;
	}
	
	/**
	 * accessor method for location attribute.
	 *
	 * @return Location
	 */
	public Location getLocation()
	{
		return this.location;
	}
	
	/**
	 * mutator method for rates attribute.
	 *
	 * @param rates the new rates
	 */
	public void setRates ( List<Rate> rates )
	{
		this.rates = rates;
	}
	
	/**
	 * accessor method for rates attribute.
	 *
	 * @return List<Rate>
	 */
	public List<Rate> getRates()
	{
		return this.rates;
	}
	
	/**
	 * mutator method for ratings attribute.
	 *
	 * @param ratings the ratings
	 */
	public void setRatings ( Map<String, Rating> ratings )
	{
		this.ratings = ratings;
	}
	
	/**
	 * accessor method for ratings attribute.
	 *
	 * @return Map<String, Rating>
	 */
	public Map<String, Rating> getRatings()
	{
		return this.ratings;
	}
	
	/**
	 * getter method for all ratings per office space.
	 *
	 * @return List<Rating>
	 */
	public List<Rating> getAllRatings()
	{
		Collection<Rating> tempSet;
		tempSet = ratings.values();
		List<Rating> officeSpaceRatingsList = new ArrayList<Rating> ( tempSet );
		return officeSpaceRatingsList;
	}
	
	public Float getActualAverageRating()
	{
		Float result = (float) 0.0;
		Float accum = (float) 0.0;
		List<Rating> tempRatingList = getAllRatings();
		for (Rating r:tempRatingList)
		{
			accum += (float) r.getStars();
		}
		
		result = accum/(float)tempRatingList.size();

		return result;
	}
	
	public Integer getRoundedAverageRating()
	{
		Integer result = Math.round(getActualAverageRating());
 		return result;
	}
	/**
	 * mutator method for ratings association.
	 *
	 * @param rating the rating
	 * @param authorId the author id
	 * @throws RatingNotFoundExcepion the rating not found excepion
	 */
	public void setRating( Rating rating, String authorId ) throws RatingNotFoundExcepion
	{ 
		if ( ratings.containsKey( authorId ) )
		{
			this.ratings.put( authorId, rating );
		}
		else
		{
			throw new RatingNotFoundExcepion();
		}
	}
	
	/**
	 * accessor method for rating association.
	 *
	 * @param authorId the author id
	 * @return Rating
	 * @throws RatingNotFoundExcepion the rating not found excepion
	 */
	public Rating getRating( String authorId ) throws RatingNotFoundExcepion
	{
		if ( ratings.containsKey( authorId ) )
		{	
			return this.ratings.get( authorId );
		}
		else
		{
			throw new RatingNotFoundExcepion();
		}
	}
	
	/**
	 * mutator method for name attribute.
	 *
	 * @param name the new name
	 */
	public void setName ( String  name )
	{
		this.name = name;
	}
	
	/**
	 * accessor method for name attribute.
	 *
	 * @return String
	 */
	public String getName()
	{
		return name;
	}
	
	
}

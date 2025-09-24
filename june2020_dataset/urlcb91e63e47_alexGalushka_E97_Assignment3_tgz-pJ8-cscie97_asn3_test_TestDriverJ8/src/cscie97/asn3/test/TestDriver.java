package cscie97.asn3.test;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cscie97.common.squaredesk.AccessException;
import cscie97.common.squaredesk.ContactInfo;
import cscie97.common.squaredesk.Address;
import cscie97.common.squaredesk.Gender;
import cscie97.common.squaredesk.Image;
import cscie97.common.squaredesk.Location;
import cscie97.common.squaredesk.Features;
import cscie97.common.squaredesk.Account;
import cscie97.common.squaredesk.Capacity;
import cscie97.common.squaredesk.Facility;
import cscie97.common.squaredesk.Profile;
import cscie97.common.squaredesk.ProfileAlreadyExistsException;
import cscie97.common.squaredesk.ProfileNotFoundException;
import cscie97.asn2.squaredesk.provider.OfficeSpaceNotFoundException;
import cscie97.asn2.squaredesk.provider.Provider;
import cscie97.asn2.squaredesk.provider.ProviderServiceImpl;
import cscie97.asn2.squaredesk.provider.OfficeSpace;
import cscie97.asn2.squaredesk.provider.OfficeSpaceAlreadyExistException;
import cscie97.common.squaredesk.Rate;
import cscie97.common.squaredesk.Rating;
import cscie97.common.squaredesk.RatingAlreadyExistsException;
import cscie97.common.squaredesk.User;
import cscie97.asn3.squaredesk.renter.Booking;
import cscie97.asn3.squaredesk.renter.BookingException;
import cscie97.asn3.squaredesk.renter.Criteria;
import cscie97.asn3.squaredesk.renter.PaymentStatus;
import cscie97.asn3.squaredesk.renter.Renter;
import cscie97.asn3.squaredesk.renter.RenterServiceImpl;
import cscie97.asn3.squaredesk.renter.SchedulingService;


/**
 * The Class TestDriver.
 */
public class TestDriver
{
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws OfficeSpaceAlreadyExistException the office space already exist exception
	 * @throws AccessException the access exception
	 * @throws ProfileAlreadyExistsException 
	 * @throws ProviderAlreadyExistsException the provider already exists exception
	 */
	public static void main(String[] args) throws OfficeSpaceAlreadyExistException, AccessException, ProfileAlreadyExistsException
    {
		// prepare data to load to the squaredesk objects
		User user = null;
		Profile uutOfficeProvider;
		OfficeSpace uutOfficeSpace;
        
		ProviderServiceImpl uutOfficeProviderService = null;
		RenterServiceImpl uutOfficeRenterService = null;
		Criteria uutCriteria  = null;
		Booking uutBooking = null;
		Profile uutRenter = null;
		
		Account uutAccount;
		Address uutAddress;
		Capacity uutCapacity;
		ContactInfo uutContactInfo;
		
		Facility uutFacility;
		Features uutFeatures;
		Image uutImage;
		Location uutLocation;
		Rate uutRate1;
		Rate uutRate2;
		Rating uutRating1;
		Rating uutRating2;
		
		SchedulingService schs; 
		
        // * Data to Office Space * //
			
		String countryCode = "USA";//subject to change to ISO if time permits
		String state = "MA";
		String city = "Cambridge"; //not in the YAML file
		String street1 = "1 story street";
		String street2 = "";
		String zipcode = "02138";
		uutAddress = new Address( countryCode, street1, street2, city, state, zipcode);
		
        Float latitude = (float) 27.175015;
        Float longitude = (float) 78.042155;
		uutLocation = new Location(uutAddress, longitude, latitude);
		
		String officeSpaceName = "bills office space";
		
		Integer numberOfPeople = 1;
		Float squarefeet = (float) 25;
		Integer workSpaces = 1;
		uutCapacity = new Capacity ( numberOfPeople, workSpaces, squarefeet );

		List<String> uutCommonAccess = new ArrayList<String> ();
		uutCommonAccess.add( "kitchen" );
		uutCommonAccess.add( "backyard" );
		uutCommonAccess.add( "pool" );
		
		String category = "kitchen table";
	    String type = "home";
	    uutFacility = new Facility( category, type );
	    
		List<String> uutFeaturesList = new ArrayList<String> ();
		uutFeaturesList.add( "Whiteboard" );
		uutFeaturesList.add( "WIFI" );
		uutFeaturesList.add( "Windows" );
		uutFeaturesList.add( "Parking" );
		uutFeaturesList.add( "Cofee" );
		uutFeatures = new Features ( uutFeaturesList );
		
		String imageDescription = "spacious kitchen table work area";
		String  imageName = "kitchen area";
		URI imageUri = URI.create ("http://www.designdazzle.com/wp-content/uploads/2013/05/66_9thave_KitchenEatingArea-600x763.jpg");
		
		uutImage = new Image ( imageDescription, imageName, imageUri );
		List<Image> uutImageList = new ArrayList<Image>();
		uutImageList.add( uutImage );
		
		Float cost1 = (float) 40;
		String period1 = "week";
		uutRate1 = new Rate ( period1, cost1 );
		Float cost2 = (float) 10;
		String period2 = "day";
		uutRate2 = new Rate ( period2, cost2 );
		List<Rate> uutRateList = new ArrayList<Rate>();
		uutRateList.add( uutRate1 );
		uutRateList.add( uutRate2 );
		
        String authorsName1 = "Frank"; // ID is wrong
        String comment1 = "very quiet and easy to focus on work";
        String date1 = "9/19/2014"; //change the date
        Integer stars1 = 5;
		uutRating1 = new Rating( authorsName1, comment1, date1, stars1 );
        String authorsName2 = "Shirley"; // ID is wrong
        String comment2 = "nice place to work, very productive";
        String date2 = "10/1/2014"; //change the date
        Integer stars2 = 5;
        uutRating2 = new Rating( authorsName2, comment2, date2, stars2 );
        
        
        Map<String, Rating> uutRatingMap = new HashMap<String, Rating>();
        //unique author's id is required
        uutRatingMap.put( "00000001", uutRating1 );
        uutRatingMap.put( "00000002", uutRating2 );
        
		uutOfficeSpace = new OfficeSpace();
		//use mutator methods to populate office space
		uutOfficeSpace.setCapacity( uutCapacity );
		uutOfficeSpace.setCommonAccess( uutCommonAccess );
		uutOfficeSpace.setFacility( uutFacility );
		uutOfficeSpace.setFeatures( uutFeatures );
		uutOfficeSpace.setImages( uutImageList );
		uutOfficeSpace.setRates( uutRateList );
		uutOfficeSpace.setLocation( uutLocation );
		uutOfficeSpace.setRatings( uutRatingMap );
		uutOfficeSpace.setName( officeSpaceName );
			
		// *********************** //
		
		// * Data User * //
		
		Integer payPalAccountNumber = 777888999;
		uutAccount = new Account ( payPalAccountNumber );
		
		String firstName = "Fred";
		String lastName = "";
		String email = "fred@gmail.com";
		String phoneNumber = "";
		String dateOfBirth = "";
		uutContactInfo = new ContactInfo ( firstName, lastName, email, phoneNumber, dateOfBirth, null);
		URI profilePictureUri = URI.create ( "http://www.wiredforbooks.org/images/FredRogers4.jpg" );

		// *********************** //
		
		// create a new Provider with unique guid
		
		uutOfficeProvider = new Provider();
		uutOfficeProvider.setPicture( profilePictureUri );
		uutOfficeProvider.setContact( uutContactInfo );
		uutOfficeProvider.setAccount( uutAccount );

		
		// Instantiate ProviderService object
		uutOfficeProviderService = ProviderServiceImpl.getInstance();
		
		String providerId = uutOfficeProviderService.createProvider( "", uutOfficeProvider );
		uutOfficeSpace.setProviderId( providerId ); 
		
		uutOfficeProviderService.createOfficeSpace( "", uutOfficeSpace, providerId );
		
		HashMap<String, OfficeSpace> uutOfficeSpaceMap = new HashMap<String, OfficeSpace>();
		uutOfficeSpaceMap.put( providerId, uutOfficeSpace );
		uutOfficeProvider.setOfficeSpaces( uutOfficeSpaceMap );

		
		// ############################################## //
		//....................RENTER..................... //
		
		countryCode = "USA";//subject to change to ISO if time permits
		state = "MA";
		city = "Cambridge"; //not in the YAML file
		street1 = "1 Brattle Street";
		street2 = "";
		zipcode = "02138";
		uutAddress = new Address( countryCode, street1, street2, city, state, zipcode);
		
        latitude = (float) 42.375;
        longitude = (float) -71.1061111;
		uutLocation = new Location(uutAddress, longitude, latitude);
		
		category = "";
	    type = "home";
	    uutFacility = new Facility( category, type );
	    
		uutFeaturesList = new ArrayList<String> ();
		uutFeaturesList.add( "TV" );
		uutFeaturesList.add( "WIFI" );
		uutFeaturesList.add( "Parking" );
		uutFeaturesList.add( "Cofee" );
		uutFeatures = new Features ( uutFeaturesList );
		
        authorsName1 = "Fred"; // ID is wrong
        comment1 = "great renter";
        date1 = "9/19/2014"; //change the date
        stars1 = 5;
		uutRating1 = new Rating( authorsName1, comment1, date1, stars1 );

        uutRatingMap = new HashMap<String, Rating>();
        //unique author's id is required
        uutRatingMap.put( providerId, uutRating1 ); // provider Id is already exist
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy M d");

		Calendar cal1 = Calendar.getInstance();
		cal1.set(2014, Calendar.OCTOBER, 31);
		Date startDate = cal1.getTime();
		
		Calendar cal2 = Calendar.getInstance();
		cal2.set(2014, Calendar.NOVEMBER, 2);
		Date endDate = cal2.getTime();
		
		uutCriteria = new Criteria();
		
        uutCriteria.setStartDate( startDate );
        uutCriteria.setEndDate( endDate );
        uutCriteria.setFacility( uutFacility );
        uutCriteria.setLocation( uutLocation ); 
        uutCriteria.setMinAverageRating( (float) 5.0 );
        uutCriteria.setPreferredFeatures( uutFeaturesList );
        
		payPalAccountNumber = 777888777;
		uutAccount = new Account ( payPalAccountNumber );
		
		firstName = "Katie";
		lastName = "Brown";
		email = "katie@gmail.com";
		phoneNumber = "";
		dateOfBirth = "";
		uutContactInfo = new ContactInfo ( firstName, lastName, email, phoneNumber, dateOfBirth, null );
		profilePictureUri = URI.create ( "http://assets-s3.usmagazine.com/uploads/assets/article_photos/katie-467.jpg" );
                
        uutRenter = new Renter();
        uutRenter.setAccount(uutAccount);
        uutRenter.setContact(uutContactInfo);
        uutRenter.setCriteria(uutCriteria);
        uutRenter.setGender(Gender.FEMALE);
        uutRenter.setPicture(profilePictureUri);
        uutRenter.setRatingsMap(uutRatingMap);
        
        // GET RenterServiceImpl instance
        uutOfficeRenterService = RenterServiceImpl.getInstance();
        
        String renterId;
        renterId = uutOfficeRenterService.createRenter ( "" , uutRenter );
        
        System.out.println ( "***RENTER SERVICE TESTING***" );
        // TEST
        boolean testStatus;
        Rate rate = new Rate();
        schs = SchedulingService.getInstance() ; 
        
        System.out.println ( "\n--Testing Booking"  );
        try
        {
        	testStatus = uutOfficeRenterService.bookOfficeSpace("", uutRenter, rate, PaymentStatus.DUE);
        	if (!testStatus)
        	{
        		System.out.println ( "As expected Renter Katie's criteria doesn't match information provided by Office Space provider Fred\n"
        				           + "Let's update Katie's search criteria" );
        	}
			
		}
        catch (BookingException e1) 
        {

			String errorMessage = "ERROR: Booking Exception has occurred";
			System.out.println ( errorMessage );
		}
        
        // update search Criteria
        uutFeaturesList = new LinkedList<String>();
		uutFeaturesList.add( "Whiteboard" );
		uutFeaturesList.add( "WIFI" );
		uutFeaturesList.add( "Windows" );
		uutFeaturesList.add( "Parking" );
		uutFeaturesList.add( "Cofee" );
		
        latitude = (float) 27.175015;
        longitude = (float) 78.042155;
		uutLocation = new Location(uutAddress, longitude, latitude);
		
		try 
		{
			uutRenter =  uutOfficeRenterService.getRenter("", renterId);
			uutCriteria = uutRenter.getCriteria();
			uutCriteria.setPreferredFeatures(uutFeaturesList);
			uutCriteria.setLocation(uutLocation);
			uutRenter.setCriteria(uutCriteria);
			uutOfficeRenterService.updateRenter("", uutRenter);
		} 
		catch (ProfileNotFoundException e1)
		{
			
			String errorMessage = "ERROR: Booking Exception has occurred";
			System.out.println ( errorMessage );
		}
		
        try
        {
        	testStatus = uutOfficeRenterService.bookOfficeSpace("", uutRenter, rate, PaymentStatus.DUE);
        	if (!testStatus)
        	{
        		System.out.println ( "As expected Renter Katie's criteria doesn't match information provided by Office Space provider Fred\n"
        				           + "Let's update Katie's search criteria" );
        	}
        	else
        	{
        		System.out.println ( "\n--Testing Scheduling Service"  );
        		System.out.println ("The new Criteria has returned the OfficeSpace which has been booked by Katie\n"
        				            + "Let's Display the Booked Office Space ID");
        		
        		Set<Booking> bookingSet = schs.listBookings();
        		if ( !bookingSet.isEmpty() )
        		{
        			String out;
        			for (Booking b : bookingSet)
        			{
        				 out = b.getOfficespace().getOfficeSpaceGuid();
        				 System.out.println ( out +"\n");
        			}
        		}
        		
        	}
			
		}
        catch (BookingException e1) 
        {

			String errorMessage = "ERROR: Booking Exception has occurred";
			System.out.println ( errorMessage );
		}
		
		// Now let's change the provider information (violating search criteria), to make sure that synchronization between ProviderService implementation
        // and Knowledge graph works. This time booking should return false.
        
        System.out.println ( "--Testing synch between ProviderServiceImpl and KnowledgeGraph..."  );
		uutFeaturesList.add( "Xbox" );
		uutFeaturesList.add( "Laptop" );
		uutFeaturesList.add( "Lamp" );
		uutFeaturesList.add( "Snacks" );
		uutFeaturesList.add( "Guitar" );
		uutFeatures = new Features ( uutFeaturesList );
		uutOfficeSpace.setFeatures( uutFeatures );
		
		try 
		{
			uutOfficeProviderService.updateOfficeSpace("", providerId, uutOfficeSpace);
		} 
		catch (OfficeSpaceNotFoundException e1)
		{
			String errorMessage = "\nERROR: Booking Exception has occurred";
			System.out.println ( errorMessage );
		}
		finally
		{
			System.out.println ( "OfficeSpace features have been updated" );
		}
		
		boolean testStatus1 = true;
		try 
		{
			testStatus1 = uutOfficeRenterService.bookOfficeSpace("", uutRenter, rate, PaymentStatus.DUE);
		} 
		catch ( BookingException e1 ) 
		{
			String errorMessage = "\nERROR: Booking Exception has occurred";
			System.out.println ( errorMessage );
		}
		finally
		{
			System.out.println ( "Provider has been updated" );
		}
    	if ( !testStatus1 )
    	{
    		System.out.println ( "Synch is working. Updated Provider's Info doesn't match Renter's search criteria" );
    	}
    	else
    	{
    		System.out.println ( "\nSynch is not working" );
    	}
        
    	System.out.println ( "\n***REGRESSION TESTING***\n" );
		// TESTS:
		Collection<Rating> ratingList = null;
		Collection<Rate> rateList = null;
		Collection<OfficeSpace> officeSpaceList = null;
		List<String> listOfFeatures = null;

		Profile provider = null;
		try
		{
			provider = uutOfficeProviderService.getProvider( "auth" , providerId);
		}
		catch (ProfileNotFoundException e)
		{
			String errorMessage = "ERROR: Provider with ID # "+ providerId + " not found.";
			System.out.println ( errorMessage );
		}
		finally
		{
			officeSpaceList = provider.getOfficeSpaces().values();
			
			for (OfficeSpace o : officeSpaceList) 
			{
				//TEST 1: Get office space ratings
				String id = o.getOfficeSpaceGuid();
				ratingList = o.getAllRatings();
				int listCount = ratingList.size();
				Integer ratingAccumulator = 0;
				for ( Rating r : ratingList )
				{
					ratingAccumulator += r.getStars();
				}
				
				Float averageRating = ratingAccumulator/ (float) listCount;
				String printOutMsg = "";
				System.out.println ( "Expected rating:" );
				printOutMsg = "Office Space ID # " + id + " has an average rating of 5.0 stars";
				System.out.println ( printOutMsg );
				System.out.println ( "Calculated rating:" );
			    printOutMsg = "Office Space ID # " + id + " has an average rating of " + averageRating  +" stars";
			    System.out.println ( printOutMsg );
			    
			    //TEST 2: What rate is cheaper if renter decides to rent office for 3 days knowing the fact that only 2 rates (weekly and daily) available?
			    rateList = o.getRates();
			    Float weeklyRate = (float) 0;
			    Float dailyRate = (float) 0;
				for ( Rate r : rateList )
				{
					if (r.getPeriod().equals("week"))
					{
						weeklyRate = r.getRate();
					}
					else
					{
						dailyRate = r.getRate();
					}
				}
				//consider only business days, so the normalization multiplier for daily rate is 5 
				if ( weeklyRate < dailyRate*5 )
				{
					printOutMsg = "for 5 business days weekly rate is cheaper!";
				}
				else
				{
					printOutMsg = "for 5 business days daily rate is cheaper!";
				}
				 System.out.println ( "\nWhat rate is cheaper if renter decides to rent office for 3 days knowing the fact that only 2 rates (weekly and daily) available?");
				 System.out.println ( "Expected answer:" );
				 System.out.println ( "for 5 business days weekly rate is cheaper!" );
				 System.out.println ( "Derived answer:" );
				 System.out.println (printOutMsg);
				 
				//TEST 3: How many features does the OfficeSpace have?
				 listOfFeatures = o.getFeatures().getAllFeatures();
				 Integer numFeatures = listOfFeatures.size();
				 System.out.println ( "\nHow many features does the OfficeSpace have?");
				 System.out.println ( "Expected answer: 5" );
				 System.out.println ( "Derived answer: "+numFeatures );
				 
			}
			
			
			//Display and Update:
			//1. Non of the providers have been rated yet. Let's rate one (the only one :-) )
			//create the new Rating object with correspondent rating fields
			String author = "";
	        String authorsNameProvider = "Alexander Galushka"; // ID is wrong
	        String commentProvider = "gave me a great discount";
	        String dateProvider= "10/08/2014";
	        Integer starsProvider = 5;
			Rating uutProviderRating = new Rating( authorsNameProvider, commentProvider, dateProvider, starsProvider );
			try
			{
				uutOfficeProviderService.rateProvider( "auth", providerId, "77777777", uutProviderRating);
			} 
			catch (RatingAlreadyExistsException e)
			{
				System.out.println ( "ERROR: The Rating from this renter already exists." );
			} 
			catch (ProfileNotFoundException e)
			{
				System.out.println ( "ERROR: Provider with ID # "+ providerId + " not found." );
			}
			finally
			{
				System.out.println ( "\nWho has recently rated this provider?" );
				try
				{
					Profile tempProvider = uutOfficeProviderService.getProvider("auth", providerId);
					
					for (Rating r : tempProvider.getAllRatings())
					{
						author = r.getAuthorsName();
					}
				} 
				catch (ProfileNotFoundException e)
				{
					System.out.println ( "ERROR: Provider with ID # "+ providerId + " not found." );
				}
				finally
				{
					System.out.println ( author );
				}
			}
		}	
    }
}


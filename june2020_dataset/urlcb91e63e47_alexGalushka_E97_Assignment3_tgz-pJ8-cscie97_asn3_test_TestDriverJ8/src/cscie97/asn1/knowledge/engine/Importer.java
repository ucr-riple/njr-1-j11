package cscie97.asn1.knowledge.engine;

import java.util.ArrayList;
import java.util.List;

import cscie97.common.squaredesk.Features;
import cscie97.asn2.squaredesk.provider.OfficeSpace;
import cscie97.asn2.squaredesk.provider.ProviderService;
import cscie97.asn3.squaredesk.renter.Observer;
import cscie97.common.squaredesk.Profile;

public class Importer implements Observer
{
	private List<Profile> provUserList;

	private Predicate locationPredicate;
	private Predicate featurePredicate;
	private Predicate facilityAndCategoryPredicate;
	private Predicate ratingPredicate; 
	List<Triple> resultTripleList;
	
	// start and end date specified by Provider - feature deferred.
	// private Map<String, String[]> datesForRentMap;
	
	
	public Importer(ProviderService providerService)
	{
		provUserList = providerService.getProviderList( "" );
		// datesForRentMap = new HashMap<String, String[]>();
		locationPredicate = new Predicate ("has_lat_long");
		featurePredicate = new Predicate ("has_feature");
		facilityAndCategoryPredicate = new Predicate ("has_facility_type_category");
		ratingPredicate = new Predicate ("has_average_rating");
		resultTripleList = new ArrayList<Triple>();
	}
	
	
	/**
	 * collect all required information from the OfficeSpaceImpl object matching the specified search criteria
	 * (location, facility type and category, feature, minimum average rating) and present to the Renter Service
	 *  Knowledge graph in the format of Triples
	 *  Note: subject is concatenated string "provId&officeId"
	 * @return List<Triple>
	 */
	public void collectSquareDeskInfoForSearch ()
	{
		try
		{
	        Profile tempProvider;
	        String provId;
	        String officeId;
	        String provAndOfficeId;
	        List<OfficeSpace> officeList;
	        
	    	// subject
	    	Node subjId;
	    	
	        // feature
	    	Node objFeature;
	    	Node objTraslatedCommonAccessFeat;
	    	Triple resultingFeatTriple;
	    	// location
	    	Node objLocation;
	    	Triple resultingLocTriple;
	    	// facility type, and category
	    	Node objFacility;
	    	Triple resultingFacTriple;
	    	// minimum average rating
	    	Node objRating;
	    	Triple resultingRatTriple; 
	        Features tempFeatures;
	        
	 
			for ( Profile provUser: provUserList )
			{
				tempProvider = provUser;//.getProfile( "provider" );
				provId = tempProvider.getGuid().trim().toLowerCase();
				officeList = tempProvider.getOfficeSpacesList();
                String[] tempFacTypeCat = {"",""};
                //String[] tempDates = {"",""};
				for ( OfficeSpace office:officeList )
				{
					officeId = office.getOfficeSpaceGuid().trim().toLowerCase();
					provAndOfficeId = provId+"&"+officeId;	
					subjId = new Node(provAndOfficeId);
					
					objLocation = new Node(office.getLocation().getSearchableLocation().trim().toLowerCase());
					resultingLocTriple = new Triple( subjId, locationPredicate, objLocation );
					resultTripleList.add(resultingLocTriple); //add *
					
					tempFeatures = office.getFeatures();
					
					for (String feature:tempFeatures.getAllFeatures())
					{
						objFeature  = new Node ( feature.trim().toLowerCase() );
						resultingFeatTriple = new Triple( subjId, featurePredicate, objFeature );
						resultTripleList.add(resultingFeatTriple); //add *
					}
					
					//common access is considered as a Feature as well, translated to the type such "hasAccessTo_..."
					for (String com:office.getTranslatedCommonAccessList())
					{
						objTraslatedCommonAccessFeat = new Node ( com.trim().toLowerCase() );
						resultingFeatTriple = new Triple( subjId, featurePredicate, objTraslatedCommonAccessFeat );
						resultTripleList.add(resultingFeatTriple); //add *
						
					}
					
					objRating = new Node( office.getRoundedAverageRating().toString().trim().toLowerCase() );
					resultingRatTriple = new Triple( subjId, ratingPredicate, objRating );
					resultTripleList.add( resultingRatTriple ); //add *
					
					tempFacTypeCat = office.getFacility().getTraslatedCategoryAndType();
					if ( tempFacTypeCat[0] != null )
					{
						if ( !tempFacTypeCat[0].equals("") )
						{
							objFacility = new Node ( tempFacTypeCat[0].trim().toLowerCase() );
					    	resultingFacTriple = new Triple( subjId, facilityAndCategoryPredicate, objFacility );
					    	resultTripleList.add( resultingFacTriple ); //add *
						}
					}
					if ( tempFacTypeCat[1] != null )
					{
						if( !tempFacTypeCat[1].equals("") )
						{
							objFacility = new Node ( tempFacTypeCat[1].trim().toLowerCase() );
					    	resultingFacTriple = new Triple( subjId, facilityAndCategoryPredicate, objFacility );
					    	resultTripleList.add( resultingFacTriple ); //add *
						}
					}
					
					//get the start and end dates
					//tempDates[0]
					//tempDates[1]
				}
			}
		}
		finally
		{
			//pass the TripleList to KnowledgeGraph
			KnowledgeGraph.getInstance().importTriples ( resultTripleList );
		}
	}
	
	
	/**
	 * Observer synchronization method
	 */
	
	public void syncUpdate()
	{
		collectSquareDeskInfoForSearch();
	}


}

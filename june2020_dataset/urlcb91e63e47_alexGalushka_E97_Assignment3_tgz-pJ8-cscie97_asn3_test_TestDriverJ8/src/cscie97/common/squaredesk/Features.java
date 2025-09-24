package cscie97.common.squaredesk;
import java.util.ArrayList;
import java.util.List;


/**
 * The Class Features.
 */
public class Features
{

	/** The all features. */
	private List<String> allFeatures;
	
	/**
	 * Instantiates a new features.
	 *
	 * @param allFeatures the all features
	 */
	public Features ( List<String> allFeatures )
	{
		this.allFeatures = allFeatures;
	}
	
	/**
	 * Instantiates a new features.
	 */
	public Features ()
	{
		this.allFeatures = new ArrayList<String>();
	}
	
	/**
	 * accessor method for allFeatures attribute.
	 *
	 * @return List<String>
	 */
	public List<String> getAllFeatures ()
	{
		return this.allFeatures;
	}
	
	/**
	 * this method adds multiple features at a time to the list of all features.
	 *
	 * @param someFeatures the some features
	 */
	public void addMultipleFeatures ( List<String> someFeatures )
	{
		for (String feature: someFeatures)
		{
			this.allFeatures.add( feature );
		}
	}
	
	/**
	 * this method adds a feature to the list of all features.
	 *
	 * @param feature the feature
	 */
	public void addFeature( String feature )
	{
		this.allFeatures.add( feature );
	}
	
	/**
	 * this method removes a feature from the list of all features.
	 *
	 * @param feature the feature
	 * @throws FeatureNotFoundException the feature not found exception
	 */
	public void removeFeature( String feature ) throws FeatureNotFoundException
	{
		if ( this.allFeatures.contains( feature ) )
		{
			this.allFeatures.remove( feature );
		}
		else
		{
			throw new FeatureNotFoundException();
		}
	}
		
}

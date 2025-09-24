package cscie97.common.squaredesk;

import java.util.LinkedList;
import java.util.List;




/**
 * issues new GUID for anyone in need
 * this class assures that duplicate GUID will not be issued
 * The Class GuidGenerator.
 */
public class GuidGenerator
{
	
	/** The existing guids list */
	private List<String> lisofExistingGuids;
	
	private static GuidGenerator _obj;
	
	/**
	 * Instantiates a new guid generator.
	 */
	private GuidGenerator()
	{
		this.lisofExistingGuids = new LinkedList<String>();
	}
	
    /**
     * A special static method to access the single GuidGenerator instance
     * @return _obj - type: GuidGenerator
     */
    public static GuidGenerator getInstance() 
    {
    	//Checking if the instance is null, then it will create new one and return it
        if (_obj == null)  
        //otherwise it will return previous one.
        {
            _obj = new GuidGenerator();
        }
        return _obj;
    }
    
	
	/**
	 * The method generates guid and adds to the list of existing guid
	 * Guid is generated using a cryptographically strong pseudo random number generator.
	 * @return generatedId: String
	 */
	public String generateProviderGuid( )
	{
		String generatedId = "";
		Boolean generateFlag = false;
		while ( !generateFlag )
		{
			generatedId = java.util.UUID.randomUUID().toString();
			if ( !this.lisofExistingGuids.contains( generatedId )  )
			{
				this.lisofExistingGuids.add( generatedId );
				generateFlag = true;
			}
		}
		return generatedId;
	}
	
}

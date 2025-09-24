package cscie97.common.squaredesk;

import cscie97.asn2.squaredesk.provider.Provider;
import cscie97.asn3.squaredesk.renter.Renter;
import cscie97.common.squaredesk.Profile;

public class ProfileFactory 
{
	
	/**
	 * use getShape method to get object of type Profile
	 * @param profileType
	 * @return
	 */
   public Profile getProfile(String profileType)
   {
      if(profileType == null)
      {
         return null;
      }		
      if(profileType.equalsIgnoreCase("PROVIDER"))
      {
         return new Provider();
      } else if(profileType.equalsIgnoreCase("RENTER"))
      {
         return new Renter();
      }   
      return null;
   }
   
}

package cscie97.common.squaredesk;

import java.util.HashMap;
import java.util.Map;


public class UserBucket
{
	Map<String, User> userMap;
	
	private static UserBucket _obj;
	
    private UserBucket ()
    {
    	userMap = new HashMap<String,User>();
    }
	
	
    /**
     * A special static method to access the single UserBucket instance
     * @return _obj - type: UserBucket
     */
    public static UserBucket getInstance() 
    {
    	//Checking if the instance is null, then it will create new one and return it
        if (_obj == null)  
        //otherwise it will return previous one.
        {
            _obj = new UserBucket();
        }
        return _obj;
    }
    
    /**
     * creates a new User
     * @param user
     */
    public void createUser (User user)
    {
    	String userId = user.getGuid();
    	if ( !userMap.containsKey( userId ) )
		{
			userMap.put( userId, user );
		}
    }
    
    /** 
     * get the user by ID
     * @param userId
     * @return
     */
    public User getUser (String userId)
    {
    	User result = null;
    	if ( userMap.containsKey( userId ) )
		{
    		result = userMap.get( userId );
		}
    
    	return result;
    }
    
    /**
     * update the specifc user in the userMap
     * @param userId
     * @param user
     */
    public void updateUser( String userId, User user )
    {
    	if ( userMap.containsKey( userId ) )
		{
			userMap.put( userId, user );
		}
    }
    
    /**
     * delete the user by ID from the userMap
     * @param userId
     */
    public void deleteUser ( String userId )
    {
    	if ( userMap.containsKey( userId ) )
		{
    		userMap.remove( userId );
		}
    }


	/**
	 * @return the userMap
	 */
	public Map<String, User> getUserMap()
	{
		return userMap;
	}
    
}

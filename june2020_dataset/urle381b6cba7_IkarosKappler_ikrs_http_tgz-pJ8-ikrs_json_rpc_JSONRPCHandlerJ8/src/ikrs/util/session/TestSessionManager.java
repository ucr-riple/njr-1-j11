package ikrs.util.session;

import java.util.TreeMap;

import ikrs.typesystem.*;
import ikrs.util.*;

/**
 * @author Ikaros Kappler
 * @date 2012-09-07
 **/

public class TestSessionManager {



    /**
     * For testing.
     **/
    public static void main( String[] argv ) {

	try {
	    int sessionMaxAge = 10;

	    System.out.println( "Create a new map factory to use for the environment creation." );
	    ikrs.util.MapFactory<String,BasicType> 
		mapFactory                          = new ModelBasedMapFactory<String,BasicType>( new TreeMap<String,BasicType>() );
	
	    System.out.println( "Create a new environment factory to use for the session creation." );
	    EnvironmentFactory<String,BasicType>      
		environmentFactory                  = new ikrs.util.DefaultEnvironmentFactory<String,BasicType>( mapFactory );

	    System.out.println( "Create a new session factory to use for the session manager." );
	    SessionFactory<String,BasicType,Integer> 
		sessionFactory                      = new DefaultSessionFactory<String,BasicType,Integer>( environmentFactory );

	    System.out.println( "Creating the session manager (sessionMaxAge=" + sessionMaxAge + ").");
	    SessionManager<String,BasicType,Integer> 
		sessionManager                      = new DefaultSessionManager<String,BasicType,Integer>( sessionFactory, 
													   sessionMaxAge  // max-age for sessions in seconds
													   );

	
	    System.out.println( "Make some test calls ..." );
	    Session<String,BasicType,Integer> session;
	    Integer userID = new Integer(6);
	    //System.out.println( "Try to fetch session by userID " + userID + " ..." );
	    //Session<String,BasicType,Integer> session = sessionManager.get( new Integer(6) );
	    //System.out.println( "session=" + session );

	    System.out.println( "Binding userID " + userID +" ..." );
	    session = sessionManager.bind( userID );
	    System.out.println( "session=" + session );

	    System.out.println( "Destroying session with SID " + session.getSessionID() + " ..." );
	    boolean destroyed = sessionManager.destroy( session.getSessionID() );
	    System.out.println( "Session destroyed: " + destroyed );


	    System.out.println( "Re-binding userID " + userID +" ..." );
	    session = sessionManager.bind( userID );
	    System.out.println( "session=" + session );

	    int wait = (sessionMaxAge + 1);
	    System.out.println( "Waiting " + wait + " seconds to trigger session timeout ... " );
	    try {
		Thread.sleep( wait*1000L );
	    } catch( InterruptedException e ) {
		e.printStackTrace();    
		System.exit(1);
	    }

	    System.out.println( "Trying to fetch session ... " );
	    session = sessionManager.get( session.getSessionID() );
	    System.out.println( "session=" + session );


	} catch( RuntimeException e ) {
	    StackTraceElement[] trace = e.getStackTrace();
	    for( int i = 0; i < trace.length; i++ )
		System.out.println( "trace["+i+"] " + trace[i] );
	}
	
    }



}
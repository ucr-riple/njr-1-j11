package ikrs.util.session;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

/**
 * The DefaultSessionManager is a very simple SessionManager implementation that uses a
 * treemap to find sessions by their ID.
 *
 * The search for user IDs is linear!
 *
 * All methods are synchronized (this manager implementation acts like a monitor).
 *
 *
 * @author Ikaros Kappler
 * @date 2012-09-06
 * @version 1.0.0
 **/


public class DefaultSessionManager<K,V,U>
    extends AbstractSessionManager<K,V,U> {

    /**
     * Create a new DefaultSessionManager (not thread safe).
     *
     * @param sessionFactory         The session factory to use to create new sessions.
     * @param sessionTimeout_seconds The number of seconds a session may be untouched before it dies.
     *
     * @throws NullPointerException If the sessionFactory is null.
     * @throws IllegalArgumentException If sessionTimeout_seconds <= 0.
     **/
    public DefaultSessionManager( SessionFactory<K,V,U> sessionFactory,
				  int sessionTimeout_seconds ) 
	throws NullPointerException,
	       IllegalArgumentException {

	super( sessionFactory, sessionTimeout_seconds );
    }


    /**
     * Create a new DefaultSessionManager.
     *
     * @param sessionFactory         The session factory to use to create new sessions.
     * @param sessionTimeout_seconds The number of seconds a session may be untouched before it dies.
     * @param threadSafe             If set to true the internal session map will be 
     *                               synchronized (thread safe).
     *
     * @throws NullPointerException If the sessionFactory is null.
     * @throws IllegalArgumentException If sessionTimeout_seconds <= 0.
     **/
    public DefaultSessionManager( SessionFactory<K,V,U> sessionFactory,
				  int sessionTimeout_seconds,
				  boolean threadSafe ) 
	throws NullPointerException,
	       IllegalArgumentException {

	super( sessionFactory, sessionTimeout_seconds, threadSafe );
    }

    
    //--- BEGIN --------------------------- SessionManager ----------------------------------------
    /**
     * Retrieve the session with the given SID.
     *
     * If the session cannot be found (does not exist or timed out) the method returns null.
     *
     * @param sessionID The desired session's unique ID.
     * @return The session with the given ID or null if no such session can be found.
     **/
    public Session<K,V,U> get( UUID sessionID ) {
	
	// Remove sessions BEFORE any read/write operations are done.
	this.removeTimedOutSessions();

	return super.get( sessionID );
	
    }

    
    
    /**
     * Thie methos destroys the session with the specified SID. 
     * That means that all session data will be removed and the session itself becomes invalid. It
     * will not be accessible or retrievable any more using on of this interface's methods.
     *
     * @param sessionID The unique ID of the session you want to destroy.
     * @return True if the session was found (and so destroyed) or false otherwise.
     **/
    public boolean destroy( UUID sessionID ) {

	// Remove sessions BEFORE any read/write operations are done.
	this.removeTimedOutSessions();
	
	return super.destroy( sessionID );
	
    }



    /**
     * This method tries to create a new session for the given user (ID). If there is already
     * a session for the given user no new session will be created but the existing one returned.
     *
     * @param userID The user (ID) to create the new session for.
     **/
    public Session<K,V,U> bind( U userID ) {
	
	// Remove sessions BEFORE any read/write operations are done.
	this.removeTimedOutSessions();
	
	return super.bind( userID );

    }
    //--- END ----------------------------- SessionManager ----------------------------------------


    /**
     * This method removes all sessions from the map that haven't been accessed (read OR write)
     * for at least the configured timeout interval.
     *
     * Note: this method is _NOT_ synchronized!
     *       It must be called from synchronized methods only!
     **/
    private void removeTimedOutSessions() {

	Iterator<Map.Entry<UUID,Session<K,V,U>>> iter = this.getSessionIDMap().entrySet().iterator();
	Date minAliveDate = new Date( System.currentTimeMillis() - (this.getSessionTimeout() * 1000L) );
	
	while( iter.hasNext() ) {

	    // Get next entry
	    Map.Entry<UUID,Session<K,V,U>> entry = iter.next();
	    UUID sid                             = entry.getKey();
	    Session<K,V,U> session               = entry.getValue();

	    
	    Date lastAccess = session.getLastAccessTime();
	    
	    // Session is too old?
	    if( lastAccess.before(minAliveDate) ) {

		// The removal is safe IF performed through the _iterator_'s remove-method! :)
		session.clear();
		iter.remove();

		// Also remove from session-by-userID map
		this.getSessionUserMap().remove( session.getUserID() );

	    }
	    
	}
	
    }



    /**
     * For testing.
     **/
    /*
    public static void main( String[] argv ) {

	// Create a new map factory to use for the environment creation.
	ikrs.util.MapFactory<String,Integer> mapFactory   = new ikrs.util.ModelBasedMapFactory<String,Integer>( new TreeMap<String,Integer>() );
	
	// Create a new environment factory to use for the session creation.
	ikrs.util.EnvironmentFactory<String,ikrs.typesystem.BasicType>      
	    environmentFactory                  = new ikrs.util.DefaultEnvironmentFactory<String,ikrs.typesystem.BasicType>( mapFactory );

	// Create a new session factory to use for the session manager.
	ikrs.util.session.SessionFactory<String,ikrs.typesystem.BasicType,Integer> 
	    sessionFactory                      = new ikrs.util.session.DefaultSessionFactory<String,ikrs.typesystem.BasicType,Integer>( environmentFactory );
	

	    }*/

}
package ikrs.util.session;

import java.util.Collections;
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


public abstract class AbstractSessionManager<K,V,U>
    implements SessionManager<K,V,U> {

    /**
     * The internal session factory used to create new sessions.
     **/
    private SessionFactory<K,V,U> sessionFactory;

    /**
     * The max age for all sessions (after inactivity).
     **/
    private int sessionTimeout_seconds;

    /**
     * A map containing the sessions.
     **/
    private Map<UUID,Session<K,V,U>> sessionIDMap;

    /**
     * A map containing the sessions, accessible by their user IDs.
     **/
    private Map<U,Session<K,V,U>> sessionUserMap;


    /**
     * Create a new AbstractSessionManager (not thread safe).
     *
     * @param sessionFactory         The session factory to use (must not be null).
     * @param sessionTimeout_seconds The session timeout to be used (seconds).
     **/
    protected AbstractSessionManager( SessionFactory<K,V,U> sessionFactory,
				      int sessionTimeout_seconds ) 
	throws NullPointerException {

	this( sessionFactory, sessionTimeout_seconds, false );
    }

    /**
     * Create a new AbstractSessionManager.
     *
     * @param sessionFactory         The session factory to use (must not be null).
     * @param sessionTimeout_seconds The session timeout to be used (seconds).
     * @param threadSafe             If set to true the internal session map will be 
     *                               synchronized (thread safe).
     **/
    protected AbstractSessionManager( SessionFactory<K,V,U> sessionFactory,
				      int sessionTimeout_seconds,
				      boolean threadSafe ) 
	throws NullPointerException {

	super();

	if( sessionFactory == null )
	    throw new NullPointerException( "Cannot create an AbstractSessionManager with a null-sessionFactory." );

	this.sessionFactory         = sessionFactory;
	this.sessionTimeout_seconds = sessionTimeout_seconds;


	this.sessionIDMap           = new TreeMap<UUID,Session<K,V,U>>();
	this.sessionUserMap         = new TreeMap<U,Session<K,V,U>>();

	// Synchronize?
	if( threadSafe ) {

	    this.sessionIDMap   = Collections.synchronizedMap( this.sessionIDMap );
	    this.sessionUserMap = Collections.synchronizedMap( this.sessionUserMap );

	}
    }

    /**
     * Subclasses can use this method to gain direct access to the internal session-by-ID map.
     *
     * Handle with care.
     *
     * @return The internal session-by-ID map.
     **/
    protected Map<UUID,Session<K,V,U>> getSessionIDMap() {
	return this.sessionIDMap;
    }


    /**
     * Subclasses can use this method to gain direct access to the internal session-by-userID map.
     *
     * Handle with care.
     *
     * @return The internal session-by-userID map.
     **/
    protected Map<U,Session<K,V,U>> getSessionUserMap() {
	return this.sessionUserMap;
    }

    
    //--- BEGIN --------------------------- SessionManager ----------------------------------------
    /**
     * This method can be used to retrieve the manager's internal session factory.
     *
     * @return The manager's internal session factory.
     **/
    public SessionFactory<K,V,U> getSessionFactory() {
	return this.sessionFactory;
    }

    /**
     * This method returns the session timeout (seconds) currently set for this manager.
     *
     * @return The session timeout (seconds) currently set for this manager.
     **/
    public int getSessionTimeout() {
	return this.sessionTimeout_seconds;
    }

    /**
     * This method sets the manager's session timeout to the new value (must be larger than 0).
     *
     * @param The number of seconds the manager's session will die after not being accessed.
     * @throws IllegalArgumentException If the passed timeout is less or equals zero.
     **/
    public void setSessionTimeout( int seconds )
	throws IllegalArgumentException {

	if( seconds <= 0 )
	    throw new IllegalArgumentException( "Cannot set the session timeout to " + seconds + " second(s)." );

	this.sessionTimeout_seconds = seconds;
    }


    /**
     * Retrieve the session with the given SID.
     *
     * If the session cannot be found (does not exist or timed out) the method returns null.
     *
     * @param sessionID The desired session's unique ID.
     * @return The session with the given ID or null if no such session can be found.
     **/
    public Session<K,V,U> get( UUID sessionID ) {
	    return this.sessionIDMap.get( sessionID );
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

	    // Try to remove the session from inside the map
	    Session<K,V,U> session = this.sessionIDMap.remove( sessionID );
	    
	    // Found?
	    if( session == null )
		return false;


	    session.clear();
	    return true;	    
    }



    /**
     * This method tries to create a new session for the given user (ID). If there is already
     * a session for the given user no new session will be created but the existing one returned.
     *
     * @param userID The user (ID) to create the new session for.
     **/
    public Session<K,V,U> bind( U userID ) {
	
	    // Try to locate the session by userID (check if already exists)
	    Session<K,V,U> session = this.sessionUserMap.get( userID );
	    
	    // Found?
	    if( session != null )
		return session;

	    // else: create
	    session = this.sessionFactory.create( userID );
	    

	    // Store newly created session in BOTH maps.
	    this.sessionUserMap.put( userID, session );
	    this.sessionIDMap.put( session.getSessionID(), session );


	    return session;

    }
    //--- END ----------------------------- SessionManager ----------------------------------------


}
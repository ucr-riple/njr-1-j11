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



public class SynchronizedSessionManagerDelegation<K,V,U>
    extends AbstractSessionManager<K,V,U> {

    /**
     * The basic session manager (should be unsynchronized) that is to be synchronized.
     **/
    private SessionManager<K,V,U> coreManager;

    /**
     * Create a new SynchronizedSessionManagerDelegation.
     *
     **/
    public SynchronizedSessionManagerDelegation( SessionManager<K,V,U> coreManager ) 
	throws NullPointerException {

	super( coreManager.getSessionFactory(), coreManager.getSessionTimeout() );

	if( coreManager == null )
	    throw new NullPointerException( "Cannot create a SessionManagerDelegation with a null-core." );

	this.coreManager = coreManager;

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
	synchronized( this.coreManager ) { 
	    return this.coreManager.get( sessionID );
	}
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

	synchronized( this.coreManager ) {
	    return this.coreManager.destroy( sessionID );
	}
    }



    /**
     * This method tries to create a new session for the given user (ID). If there is already
     * a session for the given user no new session will be created but the existing one returned.
     *
     * @param userID The user (ID) to create the new session for.
     **/
    public Session<K,V,U> bind( U userID ) {
	
	synchronized( this.coreManager ) {
	    return this.coreManager.bind( userID );
	}
    }
    //--- END ----------------------------- SessionManager ----------------------------------------


}
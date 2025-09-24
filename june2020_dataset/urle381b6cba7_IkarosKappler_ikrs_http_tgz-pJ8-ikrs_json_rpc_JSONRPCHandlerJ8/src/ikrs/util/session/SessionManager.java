package ikrs.util.session;

import java.util.UUID;


/**
 * This is a basic session handler interface.
 *
 * The actual handling policy is up to the individual implementation.
 *
 *
 * @author Ikaros Kappler
 * @date 2012-09-06
 * @version 1.0.0
 **/


public interface SessionManager<K,V,U> {
    
    /**
     * This method can be used to retrieve the manager's internal session factory.
     *
     * @return The manager's internal session factory.
     **/
    public SessionFactory<K,V,U> getSessionFactory();

    /**
     * This method returns the session timeout (seconds) currently set for this manager.
     *
     * @return The session timeout (seconds) currently set for this manager.
     **/
    public int getSessionTimeout();

    /**
     * This method sets the manager's session timeout to the new value (must be larger than 0).
     *
     * @param The number of seconds the manager's session will die after not being accessed.
     * @throws IllegalArgumentException If the passed timeout is less or equals zero.
     **/
    public void setSessionTimeout( int seconds )
	throws IllegalArgumentException;
    
    /**
     * Retrieve the session with the given SID.
     *
     * If the session cannot be found (does not exist or timed out) the method returns null.
     *
     * @param sessionID The desired session's unique ID.
     * @return The session with the given ID or null if no such session can be found.
     **/
    public Session<K,V,U> get( UUID sessionID );

    
    
    /**
     * This method destroys the session with the specified SID. 
     * That means that all session data will be removed and the session itself becomes invalid. It
     * will not be accessible or retrievable any more using on of this interface's methods.
     *
     * @param sessionID The unique ID of the session you want to destroy.
     * @return True if the session was found (and so destroyed) or false otherwise.
     **/
    public boolean destroy( UUID sessionID );



    /**
     * This method tries to create a new session for the given user (ID). If there is already
     * a session for the given user no new session will be created but the existing one returned.
     *
     * @param userID The user (ID) to create the new session for.
     **/
    public Session<K,V,U> bind( U userID );


}
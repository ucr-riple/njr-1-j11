package ikrs.util.session;

import java.util.Date;
import java.util.UUID;

import ikrs.util.Environment;

/**
 * A simple session interface.
 *
 * Basically a session is nothing more than a named environment that is bound to a specific
 * system user (remote or local). 
 *
 * Each session is identified by its unique session ID.
 *
 *
 * @author Ikaros Kappler
 * @date 2012-09-06
 * @version 1.0.0
 **/



public interface Session<K,V,U>
    extends Environment<K,V> {

    /**
     * Get the session's unique ID.
     *
     * The returned ID must never be null and it must never change.
     * 
     * @return The session's ID.
     **/
    public UUID getSessionID();

    
    /**
     * Get the session's user ID.
     *
     * The user ID must not be null and must not change.
     *
     * @return The session's user ID.
     **/
    public U getUserID();


    /**
     * This method return the date/time of the session's creation.
     *
     * @return The date/time of the session's creation.
     **/
    public Date getCreationTime();


    /**
     * This method returns the date/time of the last write access to this session.
     *
     * @return The date/time of the last write access to this session.
     **/
    public Date getLastModified();


    /**
     * This method returns the date/time of the last read OR write access to this session.
     *
     * @return The date/time of the last read OR write access to this session.
     **/
    public Date getLastAccessTime();
	
    
}

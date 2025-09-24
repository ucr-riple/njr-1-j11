package ikrs.util.session;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import ikrs.util.Environment;
import ikrs.util.EnvironmentDelegation;
import ikrs.util.EnvironmentFactory;

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


public class DefaultSession<K,V,U>
    extends AbstractSession<K,V,U> {



    /**
     * Creates a new DefaultSession.
     *
     * @param userID The session's user ID.
     * @param environment A factory to create the base environment to use.
     *
     * @throws NullPointerException If the userID or the environment factory are null.
     **/
    public DefaultSession( U userID,
			   EnvironmentFactory<K,V> environmentFactory ) 
	throws NullPointerException {

	super( userID,
	       environmentFactory.create() 
	       );
    }

    /**
     * Creates a new DefaultSession.
     *
     * @param userID The session's user ID.
     * @param environment The base environment to use.
     *
     * @throws NullPointerException If the userID or the environment factory are null.
     **/
    public DefaultSession( U userID,
			   Environment<K,V> environment ) 
	throws NullPointerException {

	super( userID,
	       environment );

    }
    
}

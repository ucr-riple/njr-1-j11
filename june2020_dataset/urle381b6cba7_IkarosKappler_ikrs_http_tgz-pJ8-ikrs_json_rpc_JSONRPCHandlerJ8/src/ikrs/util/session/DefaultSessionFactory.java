package ikrs.util.session;

import ikrs.util.EnvironmentFactory;

/**
 * This is the default factory class for creating DefaultSessions.
 *
 * @author Ikaros Kappler
 * @date 2012-09-07
 * @version 1.0.0
 *
 **/

public class DefaultSessionFactory<K,V,U> 
    implements SessionFactory<K,V,U> {
    
    /**
     * The internal environment factory to be used to create new DefaultSessions.
     **/
    private EnvironmentFactory<K,V> environmentFactory;
    


    /**
     * Creates a new DefaultFactory with the given environmentFactory.
     *
     * @param environmentFactory The environment factory to use to create new DefaultSessions (must not be null).
     * @throws NullPointerException If environmentFactory is null.
     **/
    public DefaultSessionFactory( EnvironmentFactory<K,V> environmentFactory ) 
	throws NullPointerException {

	super();

	if( environmentFactory == null )
	    throw new NullPointerException( "Cannot create a DefaultSessionFactory with a null-environmentFactory." );

	this.environmentFactory = environmentFactory;
    }
	
    /**
     * Creates a new Session with the configured type signature.
     *
     * @return A newly created session.
     **/
    public DefaultSession<K,V,U> create( U userID ) {
	return new DefaultSession<K,V,U>( userID,
					  this.environmentFactory
					  );
    }
    
}
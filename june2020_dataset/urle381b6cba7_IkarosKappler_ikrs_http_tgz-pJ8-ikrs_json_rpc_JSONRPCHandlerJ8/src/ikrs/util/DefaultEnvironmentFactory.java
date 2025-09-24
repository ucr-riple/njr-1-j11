package ikrs.util;

/**
 * This is the default environment factory; it creates DefaultEnvironments.
 *
 * @author Ikaros Kappler
 * @date 2012-09-07
 * @version 1.0.0
 **/


public class DefaultEnvironmentFactory<K,V>
    implements EnvironmentFactory<K,V> {

    
    /**
     * The internal map factory to use to create new DefaultEnvironments. Must not be null.
     **/
    private MapFactory<K,V> mapFactory;

    /**
     * This flag will be passed to the environments on creation.
     **/
    private boolean allowsMultipleChildNames;


    /**
     * Creates a new DefaultEnvironmentFactory.
     *
     * @param mapFactory The map factory to use to create new internal maps. Must not be null.
     * @throws NullPointerException If mapfactory is null.
     **/
    public DefaultEnvironmentFactory( MapFactory<K,V> mapFactory )
	throws NullPointerException {

	this( mapFactory, true );

    }

     /**
     * Creates a new DefaultEnvironmentFactory.
     *
     * @param mapFactory The map factory to use to create new internal maps. Must not be null.
     * @param allowsMultipleChildNames The flag will be passed to the environments on creation.
     * @throws NullPointerException If mapfactory is null.
     **/
    public DefaultEnvironmentFactory( MapFactory<K,V> mapFactory,
				      boolean allowsMultipleChildNames )
	throws NullPointerException {

	super();

	if( mapFactory == null )
	    throw new NullPointerException( "Cannot create a new DefaultEnvironmentFactory with a null-mapFactory." );

	this.mapFactory = mapFactory;
	this.allowsMultipleChildNames = allowsMultipleChildNames;
    }


    /**
     * Creates a new and empty environment.
     *
     * @return The new environment.
     **/
    public Environment<K,V> create() {
	return new DefaultEnvironment<K,V>( this.mapFactory, this.allowsMultipleChildNames );
    }


}

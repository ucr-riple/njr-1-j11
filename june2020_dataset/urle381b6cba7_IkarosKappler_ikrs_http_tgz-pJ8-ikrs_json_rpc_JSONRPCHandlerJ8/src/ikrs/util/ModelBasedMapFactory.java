package ikrs.util;

import java.util.Map;

/**
 * This is a simple model based map factory implementation.
 * It uses the Map-class's newInstance()-method to create new instanced.
 *
 * @author Ikaros Kappler
 * @date 2012-09-07
 * @version 1.0.0
 **/ 

public class ModelBasedMapFactory<K,V>
    implements MapFactory<K,V> {

    /** 
     * The acutal map model (must not be null).
     **/ 
    private Map<K,V> model;


    /**
     * Creates a new ModelBasedMapFactory.
     *
     * @param model The map model to clone new instances from (must not be null).
     * @throws NullPointerException If model is null.
     **/
    public ModelBasedMapFactory( Map<K,V> model )
	throws NullPointerException {

	super();

	if( model == null )
	    throw new NullPointerException( "Cannot create a ModelBasedMapFactory with a null-model." );

	this.model = model;
    }


    /**
     * Creates a new empty map.
     **/
    public Map<K,V> createMap() {

	Class<? extends Map> mapClass = this.model.getClass();
	try {

	    return mapClass.newInstance();

	} catch( InstantiationException e ) {

	    throw new RuntimeException( "Cannot create a new instance from class "+mapClass.getName()+": " + e.getMessage(), 
					e 
					);
	} catch( IllegalAccessException e ) {

	    throw new RuntimeException( "Cannot create a new instance from class "+mapClass.getName()+": " + e.getMessage(), 
					e 
					);
	}
    }
    

}

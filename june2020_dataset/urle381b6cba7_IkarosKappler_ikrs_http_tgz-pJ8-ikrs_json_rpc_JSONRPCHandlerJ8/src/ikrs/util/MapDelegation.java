package ikrs.util;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Set;

/**
 * The map delegation has a (previously passed) core map and all Map's method calls
 * will be directly forwarded to the core map.
 *
 * @author Ikaros Kappler
 * @date 2012-09-06
 * @version 1.0.0
 **/

public class MapDelegation<K,V>
    extends AbstractMap<K,V> {

    /**
     * The actual core map.
     **/
    private Map<K,V> coreMap;


    /**
     * Creates a new MapDelegation from the given core map.
     *
     * @param coreMap The actual core map (must not be null).
     *
     * @throws NullPointerException If coreMap is null.
     **/
    public MapDelegation( Map<K,V> coreMap ) 
	throws NullPointerException {

	super();

	if( coreMap == null )
	    throw new NullPointerException( "Cannt create a new MapDelegation with a null core map." );

	this.coreMap = coreMap;
	
    }

    //--- BEGIN ----------------------- OVERRIDE/implement AbstractMap implementation ------------------------
    public void clear() {
	this.coreMap.clear();
    }

    public Set<Map.Entry<K,V>> entrySet() {
	return this.coreMap.entrySet();
    }

    public V put(K key, V value) {
	return this.coreMap.put( key, value );
    }
 
    public void putAll(Map<? extends K,? extends V> m) { 
	this.coreMap.putAll( m );
    }

    public V remove(Object key) {
	return this.coreMap.remove( key );
    }
    //--- END ------------------------- OVERRIDE/implement AbstractMap implementation ------------------------

    public String toString() {
	return this.coreMap.toString();
    }

}
package ikrs.util;

import java.util.*;

/**
 *
 * @author Ikaros Kappler
 * @date 2012-05-02
 * @version 1.0.0
 **/


public abstract class AbstractMapDelegation<K,V>
    implements Map<K,V> {

    protected Map<K,V>
	baseMap;


    public AbstractMapDelegation( MapFactory<K,V> mapFactory ) {
	super();

	this.baseMap = mapFactory.createMap();
    }


    //---BEGIN------------------------- AbstractMapDelegation -----------------------------
    public void	clear() {
	this.baseMap.clear();
    }

    public boolean containsKey(Object key) {
	return this.baseMap.containsKey( key );
    }

    public boolean containsValue(Object value) {
	return this.baseMap.containsValue( value );	
    }

    public Set<Map.Entry<K,V>> entrySet() {
	return this.baseMap.entrySet();	
    }

    public boolean equals(Object o) {
	return this.baseMap.equals( o );	
    }

    public V get(Object key) {
	return this.baseMap.get( key );	
    }

    public int hashCode() {
	return this.baseMap.hashCode();	
    }

    public boolean isEmpty() {
	return this.baseMap.isEmpty();		
    }
    
    public Set<K> keySet() {
	return this.baseMap.keySet();	
    }

    public V put(K key, V value) {
	return this.baseMap.put( key, value );	
    }

    public void putAll(Map<? extends K,? extends V> m) {
	this.baseMap.putAll( m );	
    }

    public V remove(Object key) {
	return this.baseMap.remove( key );	
    }

    public int size() {
	return this.baseMap.size();	
    }

    public Collection<V> values() {
	return this.baseMap.values();	
    }
    //---END-------------------------- AbstractMapDelegation -----------------------------

    public StringBuffer toString( StringBuffer b ) {
	AbstractMapDelegation.toString( this, b );
	return b;
    }

    public static <K extends Object, V extends Object> void toString( Map<K,V> map,
								      StringBuffer b ) {
	toString( map, b, 0 );
    }

    public static <K extends Object, V extends Object> void toString( Map<K,V> map,
								      StringBuffer b,
								      int indent ) {

	  //makeIndent( b, indent, ' ' ).append( map.getClass().getName() ).append( "={" );
	  makeIndent( b, indent, ' ' ).append( "{" );
	
	// print map
	
	Iterator<K> keyIter = map.keySet().iterator();
	int i = 0;
	while( keyIter.hasNext() ) {

	    K key   = keyIter.next();
	    V value = map.get( key );

	    if( i > 0 )
		b.append( "," );
	    b.append( " " ).append( key.toString() ).append( "=" ).append( value );
	 
	    i++;
	}

	b.append( " }" );
    }

    public static StringBuffer makeIndent( StringBuffer b,
					   int length, 
					   char c ) {

	while( length > 0 ) {
	    b.append( c );
	    length--;
	}

	return b;
	
    }

}
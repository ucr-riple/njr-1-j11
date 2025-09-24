package ikrs.httpd.datatype;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import ikrs.util.CaseInsensitiveComparator;
import ikrs.util.KeyValueStringPair;

/**
 * This is the default FormData implementation.
 *
 * @author Ikaros Kappler
 * @date 2012-10-04
 * @version 1.0.0
 **/


public class DefaultFormData 
    extends AbstractFormData {

    /**
     * An internal list to store the FormDataItems.
     **/
    private List<FormDataItem> list;

    /**
     * A set holding track of all used keys and indices.
     **/
    private Map<String,Integer> indexMap;


    /**
     * Constructs a new DefaultFormData instance.
     **/
    public DefaultFormData() {
	this( null );
    }

    /**
     * Constructs a new DefaultFormData instance with an initially set
     * boundary (may be null).
     **/
    public DefaultFormData( String boundary ) {
	super( boundary );

	this.list     = new ArrayList<FormDataItem>();
	this.indexMap = new TreeMap<String,Integer>( CaseInsensitiveComparator.sharedInstance );
    }

    //--- BEGIN ------------------- AbstractFormData implementation --------------------
    /**
     * This method returns the FormDataItem with the given map key.
     * 
     * @param name The item's name.
     * @return The item with the given name or null if no such key
     *         can be found.
     * @throws NullPointerException If the passed name is null.
     **/
    public FormDataItem get( String name ) {
	
	// Search 
	// !!! (uuhhmmm ... eeehhmmm ... a linear search!?? this can be optimized!)
	/*
	for( int i = 0; i < this.list.size(); i++ ) {
	    
	    if( this.list.get(i).getKey().equalsIgnoreCase(name) )
		return this.list.get(i);

	}
	// Not found.
	return null;

	*/

	/*
	Integer index = this.indexMap.get( name ); // case insensitive
	if( index == null )
	    return null;
	else
	    return this.list.get( index.intValue() );
	*/
	
	return null;
    }

    public FormDataItem get( int index )
	throws ArrayIndexOutOfBoundsException {
	
	return this.list.get( index );

    }

    /**
     * Get the internal map's key set.
     * Note that the returned set will be immutable!
     *
     * @return The set of all keys mapped in the internal map.
     *         The returned set is never null.
     **/
    public Set<String> keySet() {
	return Collections.unmodifiableSet( this.indexMap.keySet() );
    }

    /**
     * Get the nunber of keys that are mapped inside this form data wrapper.
     *
     * @return The number of mapped keys.
     **/
    public int size() {
	return this.list.size();
    }

    /**
     * Add a new FormDataItem.
     *
     * If a different item with the same key already exists it will be
     * overwritten.
     *
     * @param item The form data item to be added.
     * @throws NullPointerException If the passed item is null.
     **/
    public void add( FormDataItem item ) 
	throws NullPointerException {

	if( item == null )
	    throw new NullPointerException( "Cannot add null-items to FormData instances." );

	Integer insertIndex = new Integer( this.list.size() );
	this.list.add( item );
	// this.indexMap.put( item.getKey(), insertIndex );
    }
    //--- END --------------------- AbstractFormData implementation --------------------

}

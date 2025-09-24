package ikrs.httpd.datatype;

import java.util.Set;

/**
 * This is the default abstract FormData super class.
 *
 * @author Ikaros Kappler
 * @date 2012-10-04
 * @modified 2013-03-20 Ikaros Kappler (method 'keySet()' added).
 * @modified 2013-05-13 Ikaros Kappler (multipart boundary re-added).
 * @version 1.0.2
 **/



public abstract class AbstractFormData 
    implements FormData {
   
    private String multipartBoundary;
    
    //private FormData successor;

    /**
     * Constructs a new AbstractFormDate instance.
     **/
    public AbstractFormData() {
	super();

    }

    /**
     * Constructs a new AbstractFormDate instance with an intitially set
     * boundary (may be null).
     **/
    protected AbstractFormData( String boundary ) {
	super();

	this.setMultipartBoundary( boundary );
    }

    //--- BEGIN ---------------------- FormData implementation -------------------
    /**
     * This method returns the FormDataItem with the given map key.
     * 
     * @param name The item's name.
     * @return The item with the given name or null if no such key
     *         can be found.
     * @throws NullPointerException If the passed name is null.
     **/
    public abstract FormDataItem get( String name );

    public abstract FormDataItem get( int index )
	throws ArrayIndexOutOfBoundsException;

    /**
     * Get the internal map's key set.
     * Note that the returned set will be immutable!
     *
     * @return The set of all keys mapped in the internal map.
     *         The returned set is never null.
     **/
    public abstract Set<String> keySet();

    /**
     * Get the nunber of keys that are mapped inside this form data wrapper.
     *
     * @return The number of mapped keys.
     **/
    public abstract int size();

    /**
     * Add a new FormDataItem.
     *
     * If a different item with the same key already exists it will be
     * overwritten.
     *
     * @param item The form data item to be added.
     * @throws NullPointerException If the passed item is null.
     **/
    public abstract void add( FormDataItem item ) 
	throws NullPointerException;

    /**
     * If the post data was sent via Content-Type=multipart/form-data
     * there must be a multipart boundary.
     *
     * @return The multipart boundary, if present; null otherwise.
     **/
    public String getMultipartBoundary() {
    	return this.multipartBoundary;
    }

    /**
     * If the whole post data was sent via Content-Type=multipart/form-data
     * there are several form data items available, all seperated by the
     * passed multipart boundady.
     *
     * If this is the case and the current (this) form data instance is NOT
     * the last one, this method will return its non-null successor (null
     * otherwise).
     *
     * @return The next form data successor if available.
     **/
    //public FormData getSuccessor() {
    //	return this.successor;
    //}
    //--- END ------------------------ FormData implementation -------------------

    /**
     * Sets the multipart boundary to the new value (may also be null).
     **/
    protected void setMultipartBoundary( String boundary) {
	this.multipartBoundary = boundary;
    }

}
package ikrs.util;

/**
 * A very simple key-value-pair implementation.
 *
 * @author Ikaros Kappler
 * @date 2012-12-12
 * @version 1.0.0
 **/

public class KeyValuePair<K,V> {

    /**
     * The key (may be null).
     **/
    private K key;
    
    /**
     * The value (may be null).
     **/
    private V value;

    /**
     * Construct a new key-value-pair. Both params may be null.
     *
     * @param key   The key (may be null).
     * @param value The value (may be null).
     **/
    public KeyValuePair( K key, V value ) {
	super();

	this.key   = key;
	this.value = value;
    }


    /**
     * Get the key from this pair.
     *
     * @return The key from this pair.
     **/
    public K getKey() {
	return this.key;
    }

    /**
     * Get the value from this pair.
     *
     * @return The value from this pair.
     **/
    public V getValue() {
	return this.value;
    }

    
    /**
     * Converts this key-value-pair into a human readable form.
     **/
    public String toString() {

	return key + "=" + value;

    }


    /**
     * This method splits the given string into a key-value tuple using the passed separator.
     *
     * If the line is null the method returns null.
     * If the line is empty the method returns (null, null).
     * If the separator is null or empty the method returns a key-value-pair with null entries.
     * If there is no separator present in the string the method returns a key-value-pair with key==line and value==null.
     * If there is a separator the key/value entries will have the respective value or be an empty string.
     *
     * Examples:
     *   - a=b results in (a,b)
     *   - a=  results in (a, "")
     *   - =b  results in ("", b)
     *   - null results in null
     *   - "" results in ("", null)
     *   - a results in (a, "")
     *   - = results in ("", "")
     *
     * @param line The line to split.
     * @param separator The separator string that separates the key and its value.
     * @param trim If set to true the method will trim keys and values (remove whitespace from
     *             the begining and end).
     * @return A pair containing the split key and values from the passed string.
     **/
    public static KeyValuePair<String,String> splitLine( String line,
							 String separator,
							 boolean trim ) {

	if( line == null )
	    return null;


	KeyValuePair<String,String> pair = new KeyValuePair<String,String>( null, null );

	if( line.length() == 0 )
	    return pair;


	if( separator == null || separator.length() == 0 ) {

	    pair.key = line;
	    return pair;

	}


	int index = line.indexOf( separator );
	if( index == -1 ) {

	    pair.key = line;

	} else if( index == 0 ) {
	    
	    // separator at beginning   
	    pair.key = "";
	    if( line.length() > separator.length() ) 
		pair.value = line.substring(separator.length());
	    else
		pair.value = "";

	} else if( index >= line.length()-separator.length() ) {

	    // separator at end of line
	    pair.key = line.substring(0,index);
	    pair.value = "";

	} else {
	    // separator anywhere in the middle
	    pair.key = line.substring(0, index).trim();
	    pair.value = line.substring(index+separator.length());

	}
	

	if( trim ) {
	    if( pair.key != null ) 
		pair.key = pair.key.trim();
	    if( pair.value != null ) 
		pair.value = pair.value.trim();
	}
	
	return pair;

    }


}
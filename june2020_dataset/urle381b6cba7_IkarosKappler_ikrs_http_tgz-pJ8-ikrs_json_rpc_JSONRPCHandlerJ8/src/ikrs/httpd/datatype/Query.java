package ikrs.httpd.datatype;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import ikrs.util.CaseInsensitiveComparator;


/**
 * The Query class is a simple parser that splits a given (raw) request query string
 * and stores it's tokens into a key-value map.
 *
 * Example:
 * The query string 
 *   my_text_A=This+is+my+very+nice+test+%28B%29.&my_text_B=+++++++++++++Ein+ganz+toller+Text+mit+%C4+Umlauten+und+%DFonderzeichen.%0D%0A%09+++++Sogar+Zeilenumbr%FCche+gibt%27s.%0D%0A%09+++++Und+%26-Zeichen.%0D%0A++&my_file=favicon.ico
 *
 * would be splitted and mapped into
 *   my_file:   "favicon.ico"
 *   my_text_A: "This is my very nice test (B)."
 *   my_text_B: "           Ein ganz toller Text mit Ä Umlauten und ßonderzeichen.
 *                          Sogar Zeilenumbrüche gibt's.
 *                          Und &-Zeichen."
 *
 *
 * @author Ikaros Kappler
 * @date 2012-10-04
 * @modified 2013-03-20 Ikaros Kappler [method 'keySet()' added].
 * @version 1.0.1
 **/


public class Query {

    /**
     * This fields stores the original raw query string.
     **/
    private String rawQueryString;

    private Map<String,String> paramMap;
    
    /**
     * Constructs a new case insensitive Query using UTF-8 encoding.
     *
     * @param rawQueryString The query string (usually retrieved from the HTTP headers' request URI string).
     **/
    public Query( String rawQueryString ) 
	throws NullPointerException,
	       UnsupportedEncodingException {

	this( rawQueryString, java.nio.charset.StandardCharsets.UTF_8.name(), false );
    }


    /**
     * Constructs a new Query.
     *
     * @param rawQueryString The query string (usually retrieved from the HTTP headers' request URI string).
     * @param encoding       The encoding to use.
     * @param caseSensitive  If set to true the key comparator will be case sensitive.
     * @throws UnsupportedEncodingException If the passed encoding is not supported.
     **/
    public Query( String rawQueryString,
		  String encoding,
		  boolean caseSensitive ) 
	throws NullPointerException,
	       UnsupportedEncodingException {


	if( rawQueryString == null )
	    throw new NullPointerException( "Cannot create Queries from null-strings." );

	if( caseSensitive )
	    this.paramMap = new TreeMap<String,String>();
	else
	    this.paramMap = new TreeMap<String,String>( CaseInsensitiveComparator.sharedInstance );
	

	Query.parse( rawQueryString,
		     encoding,
		     this.paramMap );
    }

    /**
     * Get the param value with the given key (name).
     * If the 
     *
     * Null-keys will result in a null-value.
     *
     * @param key The desired param (name).
     * @return The param's value or null if not present in the query.
     **/
    public String getParam( String key ) {
	if( key == null )
	    return null;
	return this.paramMap.get( key );
    }

    /**
     * Adds a new key/value tuple.
     *
     * If the value is null the tuple will be removed from the internal mapping.
     * 
     * @param key   The key for the new tuple.
     * @param value The value for the new tuple.
     * @return The old value in this query for the give key or null if not exists.
     * @throws NullPointerException If the key is null.
     **/
    public String addParam( String key, 
			    String value ) 
	throws NullPointerException {

	if( key == null )
	    throw new NullPointerException( "Cannot add null-keys to queries." );
	if( value == null )
	    return this.paramMap.remove( key );
	else
	    return this.paramMap.put( key, value );
    }

    /**
     * Get the (unmodifiable!) key set for this query.
     *
     * @return The (unmodifiable!) key set for this query.
     **/
    public Set<String> keySet() {
	return Collections.unmodifiableSet( this.paramMap.keySet() );
    }

    /**
     * Get the number of query params stored in the internal map.
     *
     * @return The number of query params stored in the internal map.
     **/
    public int size() {
	return this.paramMap.size();
    }

    /**
     * Get an iterator for the key set. Note that the iterator is backed to the 
     * underlying key set to the remove-methods will affect the query's param set.
     *
     * @return An iterator for the key set.
     **/
    public Iterator<String> keyIterator() {
	return this.paramMap.keySet().iterator();
    }

    public String toString() {
	return "Query=" + this.paramMap.toString();
    }

    public static void parse( String queryString,
			      String encoding,
			      Map<String,String> destination ) 
	throws NullPointerException,
	       UnsupportedEncodingException {


	if( queryString == null )
	    throw new NullPointerException( "Cannot parse null-query." );
	
	
	String[] split = queryString.split( "\\&" );
	
	for( int i = 0; i < split.length; i++ ) {

	    
	    // Split is empty? 
	    // (should not happen in well-formed query strings, but this might be more secure)
	    if( (split[i] = split[i].trim()).length() == 0 )
		continue;


	    String key = null;
	    String value = null;
	    int index = split[i].indexOf("=");

	    // Split consists of '=' only?
	    if( split[i].length() == 1 )
		continue;


	    if( index == 0 ) {

		// Key is empty
		key   = "";
		value = split[i].substring( 1, split[i].length() ).trim();

	    } else if( index == -1 ) {

		// No '=' at all (key only)
		key   = split[i];
		value = "";

	    } else if( index+1 >= split[i].length() ) {

		// value is empty
		key   = split[i].substring( 0, split[i].length()-1 ).trim();
		value = "";

	    } else {

		// Both are present
		key   = split[i].substring( 0, index ).trim();
		value = split[i].substring( index+1, split[i].length() ).trim();

	    }

	    // Decode both
	    // (might throw UnsupportedEncodingException)
	    key    = URLDecoder.decode( key, encoding );
	    value  = URLDecoder.decode( value, encoding );


	    // Store in map
	    destination.put( key, value );
	}

    }



    public static void main( String[] argv ) {

	String encoding = java.nio.charset.StandardCharsets.ISO_8859_1.name(); // "ISO-8859-1"; // "UTF-8"
	String str = "my_text_A=This+is+my+very+nice+test+%28B%29.&my_text_B=+++++++++++++Ein+ganz+toller+Text+mit+%C4+Umlauten+und+%DFonderzeichen.%0D%0A%09+++++Sogar+Zeilenumbr%FCche+gibt%27s.%0D%0A%09+++++Und+%26-Zeichen.%0D%0A++&my_file=favicon.ico";
	
	try {

	    Query query = new Query( str, 
				     encoding,
				     false  // not case sensitive
				     );

	    System.out.println( "map=" + query.paramMap );

	} catch( Exception e ) {

	    e.printStackTrace();

	}

    }

}
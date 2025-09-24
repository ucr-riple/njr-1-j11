package ikrs.httpd;


import java.io.EOFException;
import java.io.InputStream;
import java.io.IOException;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;


import ikrs.util.CaseInsensitiveComparator;


/**
 * This class wraps HTTPHeaderLines together into a list like searchable structure.
 *
 * @author Ikaros Kappler
 * @date 2012-05-21
 * @version 1.0.0
 **/


public class HTTPHeaders {

    // Reqeuest/response headers.
    public static final String NAME_ACCEPT                = "Accept";
    public static final String NAME_ACCEPT_CHARSET        = "Accept-Charset";
    public static final String NAME_ACCEPT_ENCODING       = "Accept-Encoding";
    public static final String NAME_ACCEPT_LANGUAGE       = "Accept-Language";
    public static final String NAME_CONNECTION            = "Connection";
    public static final String NAME_CONTENT_DISPOSITION   = "Content-Disposition";
    public static final String NAME_CONTENT_LENGTH        = "Content-Length";
    public static final String NAME_CONTENT_RANGE         = "Content-Range";
    public static final String NAME_CONTENT_TYPE          = "Content-Type";
    public static final String NAME_COOKIE                = "Cookies";
    public static final String NAME_HOST                  = "Host";
    public static final String NAME_REFERER               = "Referer";
    public static final String NAME_USER_AGENT            = "User-Agent";

    // Response headers.
    public static final String NAME_ALLOW              = "Allow";
    public static final String NAME_WWW_AUTHENTICATE   = "WWW-Authenticate";

    //--- BEGIN --------------------------- Request header fields ---------------------------
    /**
     * The method field of the request line.
     **/
    private String requestMethod;
    
    /**
     * The protocol field ot the request line.
     **/
    private String requestProtocol;
    
    /**
     * The version field of the request line.
     **/
    private String requestVersion;

    /**
     * The URI field of the request line.
     **/
    private String requestURI;
    //--- END ----------------------------- Request header fields ---------------------------


    //--- BEGIN --------------------------- Response header fields ---------------------------
    /**
     * This field is used to store the status code from the 'HTTP/1.1 [STATUS] [REASON_PHRASE]' line (if exists).
     * Note: this is affects the response (!) headers.
     **/
    private String responseStatus;

    /**
     * This field is used to store the status code from the 'HTTP/1.1 [STATUS] [REASON_PHRASE]' line (if exists).
     * Note: this is affects the response (!) headers.
     **/
    private String responseReasonPhrase;
    //--- END ----------------------------- Response header fields ---------------------------


    private Comparator<String> keyComparator;
    private Map<String,Set<HTTPHeaderLine>> map;
    private ArrayList<HTTPHeaderLine> list;


    /**
     * Creates a new and empty HTTPHeaders instance.
     **/
    public HTTPHeaders() {
	super();


	this.keyComparator  =  CaseInsensitiveComparator.sharedInstance;
	this.list           = new ArrayList<HTTPHeaderLine>(4);
	this.map            = new TreeMap<String,Set<HTTPHeaderLine>>( this.keyComparator );
    }

    public String getStringValue( String key ) {
	if( key == null )
	    return null;

	HTTPHeaderLine hl = this.get( key );
	if( hl == null )
	    return null;

	return hl.getValue();
    }

    public Long getLongValue( String key ) {
	if( key == null )
	    return null;

	HTTPHeaderLine hl = this.get( key );
	if( hl == null )
	    return null;

	if( hl.getValue() == null || hl.getValue().length() == 0 )
	    return null;

	try {
	    return new Long( hl.getValue() );
	} catch( NumberFormatException e ) {
	    return null;
	}
    }

    /**
     * This method checks if the headers represent a HTTP GET request.
     *
     * If no respective header is present (should not happen) the method returns false.
     *
     * @return true If the representing request is a HTTP GET request, false otherwise.
     **/
    public boolean isGETRequest() {
	// Call at least once the getRequestMethod() method to determine the method internally!
	return this.getRequestMethod() != null 
	    && this.requestMethod.equals( Constants.HTTP_METHOD_GET );
    }

    /**
     * This method checks if the headers represent a HTTP POST request.
     *
     * If no respective header is present (should not happen) the method returns false.
     *
     * @return true If the representing request is a HTTP POST request, false otherwise.
     **/
    public boolean isPOSTRequest() {
	// Call at least once the getRequestMethod() method to determine the method internally!
	return this.getRequestMethod() != null 
	    && this.requestMethod.equals( Constants.HTTP_METHOD_POST );
    }

    /**
     * This method checks if the headers represent a HTTP OPTIONS request.
     *
     * If no respective header is present (should not happen) the method returns false.
     *
     * @return true If the representing request is a HTTP OPTIONS request, false otherwise.
     **/
    public boolean isOPTIONSRequest() {
	// Call at least once the getRequestMethod() method to determine the method internally!
	return this.getRequestMethod() != null 
	    && this.requestMethod.equals( Constants.HTTP_METHOD_OPTIONS );
    }

    /**
     * This method checks if the headers represent a HTTP HEAD request.
     *
     * If no HTTP* header is present the method returns false.
     *
     * @return true If the representing request is a HTTP HEAD request, false otherwise.
     **/
    public boolean isHEADRequest() {
	// Call at least once the getRequestMethod() method to determine the method internally!
	return this.getRequestMethod() != null 
	    && this.requestMethod.equals( Constants.HTTP_METHOD_HEAD );
    }

    /**
     * This method checks if the headers represent a HTTP TRACE request.
     *
     * If no respective header is present (should not happen) the method returns false.
     *
     * @return true If the representing request is a HTTP TRACE request, false otherwise.
     **/
    public boolean isTRACERequest() {
	// Call at least once the getRequestMethod() method to determine the method internally!
	return this.getRequestMethod() != null 
	    && this.requestMethod.equals( Constants.HTTP_METHOD_TRACE );
    }

    /**
     * This method registers the given HTTPHeader line into the search map.
     * Note that this structure allows multiple elements with the same key.
     *
     * @param element The new header line to add (must not be null).
     * @throws NullPointerException If element is null.
     **/
    private void registerToMap( HTTPHeaderLine element ) 
	throws NullPointerException {
	
	if( element == null )
	    throw new NullPointerException( "Cannot add null-HeaderLines." );

	Set<HTTPHeaderLine> set = this.map.get( element.getKey() );
	if( set == null ) {
	    // Container not found -> create new
	    set = new TreeSet<HTTPHeaderLine>();
	    this.map.put( element.getKey(), set );
	}
	
	set.add( element );
    }

    /**
     * This method releases the given HTTPHeaderLine from the search map.
     * If the element is null this method just does nothing but returning false.
     *
     * @param element The header line to be removed.
     * @return True if and only if the passed element was found and removed.
     **/
    private boolean releaseFromMap( HTTPHeaderLine element ) {

	if( element == null )
	    return false;
	
	Set<HTTPHeaderLine> set = this.map.get( element.getKey() );
	if( set == null ) {
	    // Container not found
	    return false;
	}

	boolean removed = set.remove( element );
	if( !removed ) {
	    // Not found in container
	    return false;
	}

	// Fully remove container?
	if( set.size() == 0 ) {

	    this.map.remove( element.getKey() );

 	} 

	return true;
    }

    private int locateStatusLine() {

	// Search current header line beginning at the end (!) 
	for( int i = this.list.size()-1; i >= 0; i-- ) {
	    
	    HTTPHeaderLine line = this.list.get(i);
	    if( line.isResponseStatus() ) { 
		
		return i;
	    }
	}
	
	return -1;
    }

    private int locateLineByKey( String key ) {

	if( key == null )
	    return -1;


	// Search current line.
	for( int i = 0; i < this.list.size(); i++ ) {
	    
	    HTTPHeaderLine line = this.list.get(i);
	    if( line.getKey() != null && line.getKey().equalsIgnoreCase(key) ) {
		
		return i;
	    }
	}
	
	return -1;
    }

    private void replaceLine( int index, 
			      HTTPHeaderLine replacement ) {

	HTTPHeaderLine oldStatusLine = this.list.get(index);
	this.releaseFromMap( oldStatusLine );
	this.list.set( index, replacement );
	this.registerToMap( replacement ); 

    }

    public boolean replaceResponseLine( HTTPHeaderLine statusLine ) 
	throws NullPointerException,
	       HeaderFormatException {

	if( statusLine == null )
	    throw new NullPointerException( "Cannot replace the response status line with null line." );
	
	if( statusLine.getKey() == null )
	    throw new HeaderFormatException( "Cannot replace the response status line with this header line. Key is null: " + statusLine, statusLine );
	
	if( !statusLine.getKey().startsWith("HTTP") )
	    throw new HeaderFormatException( "Cannot replace the response status line with this header line. Key does not start with 'HTTP': " + statusLine, statusLine );
	

	// Response line seems OK (perform deep check?).
	int index = this.locateStatusLine();



	// Reset the respone fields for later re-evaluation!
	this.responseStatus       = null;
	this.responseReasonPhrase = null;



	// Replace?
	if( index != -1 ) {

	    this.replaceLine( index, statusLine );

	    // Indicates that the old one existed and was reaplaced.
	    return true;

	} else {

	    // And add the new line to the beginning :)
	    this.list.add( 0, statusLine );
	    this.registerToMap( statusLine );

	    // Indicates that is was added.
	    return false;
	}
	
    }

    /**
     * This method returns a set containing all header lines with the given key (name).
     * The returned set is a full copy and is not backed up the the underlying
     * structure, so modifications on the set have no effect to this HTTPHeaders 
     * instance.
     *
     * @param name The desired headers' name (the key; must not be null).
     * @return A newly created set containing all header lines with the given name.
     * @throws NullPointerException If name is null.
     **/
    public Set<HTTPHeaderLine> getAll( String name ) 
	throws NullPointerException {

	if( name == null )
	    throw new NullPointerException( "Cannot retrieve header lines if the passed key (name) is null." );
	
	// Get the set widht the elements
	Set<HTTPHeaderLine> set = this.map.get( name );
       

	// make a copy
	Set<HTTPHeaderLine> result = new TreeSet<HTTPHeaderLine>();

	if( set == null )
	    return result;


	Iterator<HTTPHeaderLine> iter = set.iterator();
	while( iter.hasNext() ) {
	    
	    // Hint: HTTPHeaderLines are immutable -> no need to clone
	    result.add( iter.next() );

	}
	
	return result;
    }

    /**
     * This method returns a random header line with the given key or null
     * if no such element can be found.
     *
     * If there are multiple header lines with the same key one element is
     * randomly picked.
     *
     *
     * @param name The desired header line's name (key; must not be null).
     * @return A header line with the given name.
     * @throws NullPointerException If name is null.
     **/
    public HTTPHeaderLine get( String name ) 
	throws NullPointerException {

	// Get the set widht the elements
	Set<HTTPHeaderLine> set = this.map.get( name );
       
	if( set == null || set.size() == 0 )
	    return null;

	Iterator<HTTPHeaderLine> iter = set.iterator();

	return iter.next();
    }

    /**
     * This method adds a new header line to this headers object.
     *
     * This method just calls add( new HTTPHeaderLine(key,value) ).
     *
     * @param key The new line's key.
     * @param value Thw new line's value.
     * @throws NullPointerException If the key is null.
     **/
    public boolean add( String key, 
			String value 
			) 
	throws NullPointerException {

	if( key == null )
	    throw new NullPointerException( "Cannot add header with null-key." );

	return this.add( new HTTPHeaderLine(key,value) );
    }

    /**
     * This method adds a new header line to this headers object.
     *
     * If the passed line is the HTTP response status line, the old line will
     * be replaced.
     *
     * @param key The new line's key.
     * @throws NullPointerException If the line is null.
     **/
    public boolean add( HTTPHeaderLine e ) 
	throws NullPointerException {

	if( e == null )
	    throw new NullPointerException( "Cannot add null line to HTTP headers." );


	if( e.isResponseStatus() ) {
	    
	    try {
		this.replaceResponseLine( e );
		return true;
	    } catch( HeaderFormatException exc ) {
		throw new RuntimeException( "Unexpected HeaderFormatException when replacing an (existing) response status line! replacement=" + e.toString() + ", this=" + this.toString() + ". Error message: "+ exc.getMessage(), 
					    exc );
	    }

	} else {

	    boolean b = this.list.add( e );
	    if( b )
		this.registerToMap( e );
	    
	    return b;

	}
    } 

    public boolean add( HTTPHeaderLine e,
			boolean replaceIfExists ) {

	if( !replaceIfExists ) {

	    // Just add to the end of the list.
	    return add( e );

	} else if( e.isResponseStatus() ) {
	    
	    try {
		this.replaceResponseLine( e );
	    } catch( HeaderFormatException exc ) {
		throw new RuntimeException( "Unexpected HeaderFormatException when replacing an (existing) response status line! replacement=" + e.toString() + ", this=" + this.toString() + ". Error message: "+ exc.getMessage(), 
					    exc );
	    }
	    
	    return true;

	} else {

	    // Search a replacement.
	    int index = locateLineByKey( e.getKey() );
	    if( index == -1 ) {

		// No such line exists so far.
		// -> add to the end of the list.
		this.add( e );
		
	    } else {

		// Line with the same key found: replace.
		this.replaceLine( index, e );

	    }

	    // Added or replaced.
	    return true;
	}
    }

    /**
     * This method removes max. one header line with the given key (name).
     *
     * If no such line can be found the internal list is left unchanged and the method
     * return false (true otherwise).
     *
     * @param name The header line's name (key).
     * @return True if (and only if) the passed header-line's name was found and removed, 
     *         false otherwise.
     * @throws NullPointerException If the passed name is null.
     **/
    public boolean remove( String name ) 
	throws NullPointerException {

	if( name == null )
	    throw new NullPointerException( "Cannot remove header-lines using a null-key." );
	
	// Try to retrieve one random headerline
	HTTPHeaderLine line = this.get( name );

	// Exists?
	if( line == null )
	    return false;  // None exist -> none can be removed

	// Remove (the 'releaseFromMap()' method will return true in this case).
	return this.releaseFromMap( line );
    }

    
    /**
     * This method removes *all* header lines with the given key (name).
     *
     * The returned integer indicates the number of removed lines.
     *
     * @param name The header line's name (key).
     * @return The number of removed header lines.
     * @throws NullPointerException If the passed name is null.
     **/
    public int removeAll( String name ) 
	throws NullPointerException {
	
	int count = 0;
	while( this.remove(name) ) {

	    count++;
	}

	return count;
    }

    /**
     * This method replaces all existing header lines matching the passed line's key
     * by the passed line itself.
     *
     * After calling this method this HTTPHeaders-instance contains _exactly_ one element
     * with the passed line's key.
     * 
     * The returned integer indicates the number of lines that have been removed (old element
     * count).
     *
     * @param element The header line you want to use as replacement (replacing all existing
     *                lines with the same key).
     * @return The number of elements that were replaced (old element count).
     * @throws NullPointerException If the passed element is null.
     **/
    public int replaceAll( HTTPHeaderLine element ) 
	throws NullPointerException {

	if( element == null ) 
	    throw new NullPointerException( "Cannot replace header lines with null-element." );

	int removed = removeAll( element.getKey() );
	
	// This call should always return true
	if( !this.add(element) )
	    throw new RuntimeException( "Failed to add a new header line element ('"+element.getKey()+"') to the underlying list (unknown reason)." );

	return removed;	
    }
     

    /**
     *
     **/
    public HTTPHeaderLine get( int index ) 
	throws IndexOutOfBoundsException {
	return this.list.get( index );
    }

    public Iterator<HTTPHeaderLine> iterator() {
	return this.list.iterator();
    }
    //---END--------------------------- Override List's access methods ------------------

    public int size() {
	return this.list.size();
    }

    public String getRequestMethod() {
	if( this.requestMethod == null )
	    this.parseRequestLine();

	return this.requestMethod;
    }

    public String getRequestProtocol() {
	if( this.requestProtocol == null )
	    this.parseRequestLine();

	return this.requestProtocol;
    }

    public String getRequestVersion() {
	if( this.requestVersion == null )
	    this.parseRequestLine();

	return this.requestVersion;
    }

    public String getRequestURI() {
	if( this.requestURI == null )
	    this.parseRequestLine();

	return this.requestURI;
    }


    public String getResponseStatus() {
	if( this.responseStatus == null )
	    this.parseResponseStatusLine();
	
	return this.responseStatus;
    }

    public String getResponseReasonPhrase() {
	if( this.responseReasonPhrase == null )
	    this.parseResponseStatusLine();
	
	return this.responseStatus;
    }


    private boolean parseRequestLine() {

	if( this.list.size() == 0 )
	    return false;
	
	HTTPHeaderLine first = this.list.get(0);
	String key = first.getKey();
	if( key == null )
	    return false;


	// The first line should have this format (or like that):
	// GET / HTTP/1.1
	String[] split = key.split( "(\\s)++" );
	//print( split );
	if( split.length < 3 )
	    return false;
	

	this.requestMethod = split[0];
	this.requestURI = split[1];

	// Parse request HTTP-Version
	String[] split2 = split[2].split("(/)");
	if( split2.length >= 1 ) {
	    this.requestProtocol = split2[0];	
	    if( split2.length >= 2 )
		this.requestVersion = split2[1];
	}


	return true;
    }


    private boolean parseResponseStatusLine() {

	// Are there header lines at all?
	if( this.list.size() == 0 )
	    return false;
	
	// Try to locate the header beginning AT THE END
	// (usually the latest headers were added to the end of the list, and so
	//  if there are duplicates the latest one override the predecessor(s)).
	HTTPHeaderLine statusLine = null;
	String key = null;
	for( int i = this.list.size()-1; 
	     i >= 0 && statusLine == null; 
	     i-- ) {

	    HTTPHeaderLine tmp = this.list.get(0);
	    key = tmp.getKey();
	    if( key == null )
		continue;
	    
	    // Is this a response status header?
	    if( key.startsWith("HTTP") ) {

		// This will cause the loop to stop
		statusLine = tmp;

	    }
	} // END for

	if( statusLine == null )
	    return false;  // Not found



	// The first line should have this format (or like that):
	// HTTP/1.1 501 Internal Server Error
	// WHERE
	//  - statusCode := "501"
	//  - reasonPhrase := "Internal Server Error"

	// Find first two spaces
	key = key.trim();
	int indexA = key.indexOf( " " );
	//int indexB = key.indexOf( " ", indexA+1 );

	if( indexA == -1 ) {

	    // No spaces at all (only "HTTP/1.x")
	    return false;

	} else {

	    // Remove "HTTP/1.x" from the beginning, then trim.
	    key = key.substring(indexA+1, key.length()).trim();
	    int indexB = key.indexOf( " " );

	    if( indexB == -1 ) {

		// Has the format "[HTTP/1.x ] <status_code>"
		this.responseStatus = key;
		this.responseReasonPhrase = null;

	    } else {

		// Has the format "[HTTP/1.x ] <status_code> <reason_phrase>"
		this.responseStatus       = key.substring( 0, indexB ).trim();
		this.responseReasonPhrase = key.substring( indexB+1, key.length() ).trim();

	    } 

	    return true;
	}
	
    }


    public static HTTPHeaders read( InputStream in )
	throws EOFException,
	IOException {

	
	HTTPHeaders headers = new HTTPHeaders();


	HTTPHeaderLine header = null;
	// Read line by line; the first occurence of an empty line indicates the end-of-headers, which means
	// the HTTPHeaderLine.read(...) returns null.
	while( (header = HTTPHeaderLine.read(in)) != null ) {
	    //System.out.println( "   HTTPHeaders.read(..) header line: " + header );
	    
	    if( header.getKey() != null )
		headers.add( header );
	}

	
	return headers;
    }


    public String toString() {
	return toString( new StringBuffer() ).toString();
    }

    public StringBuffer toString( StringBuffer b ) {

	b.append( "Headers={ " );
	for( int i = 0; i < this.list.size(); i++ ) {
	    if( i > 0 )
		b.append( ", " );
	    
	    HTTPHeaderLine line = this.list.get(i);
	    b.append( line.getKey() ).append( ": " ).append( line.getValue() );
	}
	b.append( " }" );
	return b;
    }
    /*private static void print( String[] str ) {

	for( int i = 0; i < str.length; i++ ) {
	    System.out.print( str[i] );
	    System.out.print( " | " );
	}

	}*/

}
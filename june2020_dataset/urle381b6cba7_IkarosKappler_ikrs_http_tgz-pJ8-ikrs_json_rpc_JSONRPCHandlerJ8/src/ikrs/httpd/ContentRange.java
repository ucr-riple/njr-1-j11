package ikrs.httpd;

import ikrs.util.KeyValuePair;


/**
 * This is a wrapper/parser class for the HTTP Content-Range header.
 *
 * Note that the HTTP specs expect the last-byte-position to be inclusive
 * which is pretty unlikely in Java (unsually read limits are specified
 * the exclusive way). Don't get confused with that!
 *
 * Specs:
 * (see http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html)
 *      Content-Range = "Content-Range" ":" content-range-spec
 *
 *      content-range-spec      = byte-content-range-spec
 *      byte-content-range-spec = bytes-unit SP
 *                                byte-range-resp-spec "/"
 *                                ( instance-length | "*" )
 *
 *      byte-range-resp-spec = (first-byte-pos "-" last-byte-pos)
 *                                     | "*"
 *      instance-length           = 1*DIGIT
 *
 * Examples:
 *    . The first 500 bytes:
 *      bytes 0-499/1234
 *
 *    . The second 500 bytes:
 *      bytes 500-999/1234
 *
 *    . All except for the first 500 bytes:
 *      bytes 500-1233/1234
 *
 *    . The last 500 bytes:
 *      bytes 734-1233/1234
 *
 *
 * @author Ikaros Kappler
 * @date 2013-02-27
 * @version 1.0.0
 **/


public class ContentRange {
    
    public static final String NAME_BYTESUNIT_BYTES = "bytes";

    /**
     * This has usually the format 'bytes'.
     **/
    private String bytesUnit;

    /**
     * The first byte position, absolute (inclusive).
     **/
    private long firstBytePosition;

    /**
     * The last byte position, absolute (inclusive, too!).
     **/
    private long lastBytePosition;

    /**
     * The whole instance length (if known by the requester).
     *
     * If unknown this field contains -1L.
     **/
    private long instanceLength;

    
    /**
     * Create a new 'Content-Range' wrapper with the given values.
     *
     * @param bytesUnit    The bytes-unit value (the only sipported value is NAME_BYTESUNIT_BYTES so far).
     * @param firstBytePos The first byte position (absolute abd inclusive).
     * @param lastBytePos  The last byte position (absolute and inclusive).
     * @param instanceLen  The length of the whole instance.
     * @throws NullPointerException If 'bytesUnit' is null.
     * @throws IllegalArgumentException If any of the passed arguments is out of range.
     **/
    public ContentRange( String bytesUnit,
			 long firstBytePos,
			 long lastBytePos,
			 long instanceLen ) 
	throws NullPointerException,
	       IllegalArgumentException {

	super();

	if( bytesUnit == null )
	    throw new NullPointerException( "Cannot create Content-Range wrappers with null-bytesUnit." );
	if( !bytesUnit.equalsIgnoreCase(ContentRange.NAME_BYTESUNIT_BYTES) )
	    throw new IllegalArgumentException( "Unknown/unsupported value for bytesUnit: " + bytesUnit + "." );
	if( firstBytePos < 0 )
	    throw new IllegalArgumentException( "firstBytePosition must not be less than zero (" + firstBytePos + ")." );
	if( instanceLen >= 0 ) {

	    if( firstBytePos >= instanceLen )
		throw new IllegalArgumentException( "firstBytePosition cannot be larger than instanceLength (" + firstBytePos + " >= " + instanceLen + ")." );

	    if( lastBytePos >= instanceLen )
		throw new IllegalArgumentException( "lastBytePosition cannot be larger than instanceLength (" + lastBytePos + " >= " + instanceLen + ")." );

	}
	if( firstBytePos > lastBytePos )
	    throw new IllegalArgumentException( "firstBytePosition must not be larger than lastBytePosition (" + firstBytePos + " > " + lastBytePos + ")." );


	this.bytesUnit         = bytesUnit;
	this.firstBytePosition = firstBytePos;
	this.lastBytePosition  = lastBytePos;
	this.instanceLength    = instanceLen;

    }

    /**
     * Get the 'bytes-unit' value.
     * Currently the only supported value is "bytes".
     *
     * @return The value of the 'bytes-unit'; the returned string is never null.
     **/
    public String getBytesUnit() {
	return this.bytesUnit;
    }

    /**
     * Get the 'first-byte-position' value.
     *
     * @return The value of 'first-byte-position'; the returned value is never negative.
     **/
    public long getFirstBytePosition() {
	return this.firstBytePosition;
    }

    /**
     * Get the 'last-byte-position' value.
     *
     * @return The value of 'last-byte-position'; the returned value is never negative
     *         and always greater than or equal to 'first-byte-position'.
     **/
    public long getLastBytePosition() {
	return this.lastBytePosition;
    }
     
    public long calculateLength() {
	return this.lastBytePosition - this.firstBytePosition + 1;
    }

    /**
     * Get the 'instance-length' value; the returned value is larger than or equal to
     * 'last-byte-position' OR it is -1L, which means that the instance-length is currently
     * unknown.
     *
     * @return The value of 'instance-length'.
     **/
    public long getInstanceLength() {
	return this.instanceLength;
    }


    /**
     * This static method parses the given 'Content-Range' header field value.
     * The passed string must not be null and match the specification in RFC 2616/sec14.
     **/ 
    public static ContentRange parse( String headerValue ) 
	throws NullPointerException,
	       MalformedRequestException {
    

	// Split at [space]
	KeyValuePair<String,String> pair = KeyValuePair.splitLine( headerValue.trim(), 
								   " ",    // separator
								   true    // trim
								   );
	
	if( pair.getKey() == null || pair.getKey().length() == 0 )
	    throw new MalformedRequestException( "'Content-Range' value misses bytes-unit: " + headerValue + "." );
	
	if( pair.getValue() == null || pair.getValue().length() == 0 )
	    throw new MalformedRequestException( "'Content-Range' value misses byte-content-range-spec: " + headerValue + "." );

	String bytesUnit = pair.getKey();
	

	// Split value at '/' into 'byte-range-resp-spec' and 'instance-length'
	KeyValuePair<String,String> valuePair = KeyValuePair.splitLine( pair.getValue(), 
									"/",    // separator
									true    // trim
									);
	
	if( valuePair.getKey() == null || valuePair.getKey().length() == 0 )
	    throw new MalformedRequestException( "'Content-Range' value misses 'byte-range-resp-spec': " + headerValue + "." );
	if( valuePair.getValue() == null || valuePair.getValue().length() == 0 )
	    throw new MalformedRequestException( "'Content-Range' value misses 'instance-length': " + headerValue + "." );

	
	// Split 'byte-range-resp-spec' at '-' into 'first-byte-pos' and 'last-byte-pos'
	KeyValuePair<String,String> rangePair = KeyValuePair.splitLine( valuePair.getKey(), 
									"-",    // separator
									true    // trim
									);

	if( rangePair.getKey() == null || rangePair.getKey().length() == 0 )
	    throw new MalformedRequestException( "'Content-Range' value misses 'first-byte-position': " + headerValue + "." );
	if( rangePair.getValue() == null || rangePair.getValue().length() == 0 )
	    throw new MalformedRequestException( "'Content-Range' value misses 'last-byte-position': " + headerValue + "." );

	
	try {

	    long firstBytePos = Long.parseLong( rangePair.getKey() );
	    long lastBytePos  = Long.parseLong( rangePair.getValue() );

	    long instanceLen = -1;
	    if( !valuePair.getValue().equals("*") )
		instanceLen = Long.parseLong( valuePair.getValue() );


	    return new ContentRange( bytesUnit,
				     firstBytePos,
				     lastBytePos,
				     instanceLen );

	} catch( NumberFormatException e ) {

	    throw new MalformedRequestException( "'Content-Range' value contains non-numerical/non-integer values (" + e.getMessage() + ") where not allowed: " + headerValue + "." );

	} catch( IllegalArgumentException e ) {

	    throw new MalformedRequestException( "'Content-Range' value contains illegal/unsupported values: " + e.getMessage() + " " + headerValue + "." );

	}
    }

    
    public String toString() {
	return this.bytesUnit + " " + this.firstBytePosition + "-" + this.lastBytePosition + "/" + this.instanceLength;
    }

    /**
     * This is just for testing.
     **/
    public static void main( String[] argv ) {

	String str = "bytes 0 - 499 / 1234";
	try {

	    System.out.println( "Going to parse '" + str + "' ..." );
	    ContentRange cr = ContentRange.parse( str );
	    System.out.println( "Done: " + cr.toString() );

	} catch( Exception e ) {
	    
	    e.printStackTrace();
	    
	}

    }
}
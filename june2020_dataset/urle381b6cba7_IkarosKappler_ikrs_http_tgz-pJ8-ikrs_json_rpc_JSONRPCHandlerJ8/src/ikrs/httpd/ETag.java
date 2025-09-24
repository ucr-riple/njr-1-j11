package ikrs.httpd;

import java.io.IOException;
import java.net.URI;
import java.security.NoSuchAlgorithmException;


/**
 * The ETag (entity tag) is sort of a resource hash. It is built of the 
 * resource's name, the resource's size (if available) and the 
 * resource's date of last modification; if the modification date is 
 * unknown the current time stamp is used, so each time it is called a 
 * new hash is generated (the resource might have changed in between).
 *
 * 
 * For details see 
 * http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html
 *
 *
 * @author Ikaros Kappler
 * @date 2013-02-28
 * @version 1.0.0
 **/


public class ETag {

    private URI relativeURI;

    private Resource resource;

    /**
     * This class is not meant to be instantiated directly.
     **/
    private ETag() {
	super();
    }

    /**
     * This method just collects the essential information required to be hashed
     * and concatenates it into a string.
     *
     * Note that the returned string does not contain any (explicit) random
     * data; this should cause the hash bases to be equal if two equal resources
     * were passed.
     *
     * The returned string is never null.
     **/
    private String createHashBase() 
	throws IOException {
	
	if( !this.resource.isOpen() )
	    throw new IOException( "Cannot create an ETag from an un-opened resource!" );

	StringBuffer buffer = new StringBuffer();

	// Add the request path 
	// (better than the inode number, I think)
	if( this.relativeURI != null ) {
	    
	    if( this.relativeURI.getPath() != null )
		buffer.append( this.relativeURI.getPath() );
	    
	    if( this.relativeURI.getQuery() != null )
		buffer.append( "?" ).append( this.relativeURI.getQuery() );
	    
	}

	// Add a delimiter
	buffer.append( "#" );

	
	// Add the date of last modification 
	if( this.resource.getMetaData().getLastModified() != null ) {

	    buffer.append( this.resource.getMetaData().getLastModified().getTime() );

	} else {
	    
	    // If modification date is not available: use current time stamp
	    buffer.append( System.currentTimeMillis() );

	}

	// Add one more delimiter
	buffer.append( "#" );


	// Add the size of the resource.
	// Warning: this might throw an exception.
	buffer.append( this.resource.getLength() ); // might be -1, which is OK for the hash

	return buffer.toString();
    }

    /**
     * This method creates the raw hash bytes from the passed resource information.
     *
     * The returned byte array contains 16 bytes of an MD5 sum.
     *
     * @return The MD5 hash in a 16-byte-array.
     **/
    public byte[] createRawHash() 
	throws IOException,
	       NoSuchAlgorithmException {

	
	String hashBase = this.createHashBase();
	byte[] md5      = MD5.md5( hashBase.getBytes(java.nio.charset.StandardCharsets.UTF_8.name()) );
	return md5;

    }


    /**
     * This method creates the hexadecimal representation of the hashed resource
     * information.
     *
     * The returned string is never null.
     *
     * @return A string in hex representation containing the bytes of the MD5 hash
     *         for the passed resource information.
     **/
    public String createHexHash() 
	throws IOException,
	       NoSuchAlgorithmException {

	return CustomUtil.bytes2hexString( this.createRawHash() );

    }

    /**
     * This method creates a fully qualified ETag HTTP header value. The returned
     * string can be directly used for the 'ETag' response header.
     *
     * Note that this ETag implementation only creates weak (!) hashes, so the
     * return string always begins with "W/".
     *
     * @return A fully qualified ETag value for the response headers.
     **/
    public String createHeaderValue() 
	throws IOException,
	       NoSuchAlgorithmException {
	
	// This ETag is _not_ strong, it is WEAK (that indicates the 'W')!
	return "W/\"" + this.createHexHash() + "\"";
    }


    /**
     * Create a new ETag from the given credentials.
     *
     * @param resource    The resource to use (must not be null).
     * @param relativeURI The resource's request URI (relative; may be null).
     *
     * @throws NullPointerException If the passed resource is null.
     **/
    public static ETag create( Resource resource,
			       URI relativeURI ) 
	throws NullPointerException {

	if( resource == null )
	    throw new NullPointerException( "Cannot create an ETag from a null-resource." );

	
	ETag etag        = new ETag();
	etag.resource    = resource;
	etag.relativeURI = relativeURI;

	return etag;

    }


}
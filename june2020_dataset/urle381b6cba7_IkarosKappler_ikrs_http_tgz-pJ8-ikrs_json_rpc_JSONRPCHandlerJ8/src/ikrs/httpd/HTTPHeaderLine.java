package ikrs.httpd;

import java.io.EOFException;
import java.io.InputStream;
import java.io.IOException;
import java.nio.charset.Charset;


import ikrs.util.CRLFLineReader;
import ikrs.util.KeyValuePair;
import ikrs.util.KeyValueStringPair;


/**
 * The HTTPHeaderLine class is very simple datastructure to hold single
 * HTTP header lines (each line represents a key/value pair).
 *
 * It can also be used to parse one header line after the other.
 *
 * Note: instances of this class are and have to be immutable!
 *
 *
 * @author Ikaros Kappler
 * @date 2012-05-21
 * @modified 2013-03-15 Ikaros Kappler (class now extends KeyValuePair).
 * @version 1.0.0
 **/



public final class HTTPHeaderLine 
    extends KeyValueStringPair
    implements Comparable<HTTPHeaderLine> {


    private String rawLine;
    //private String key;
    //private String value;

    /**
     * This is a private constructor and not meant to be public.
     **/
    /*private HTTPHeaderLine( String rawLine ) {
	super();

	this.rawLine = rawLine;
	}*/


    public HTTPHeaderLine( String key,
			   String value 
			   ) {
	super( key, value );

	//this.key = key;
	//this.value = value;
    }

    /**
     * Get the key of this header line.
     **/
    //public String getKey() {
    //	return this.key;
    //}

    /**
     * Get the value of this header line.
     **/
    //public String getValue() {
    //	return this.value;
    //}

    public boolean isResponseStatus() {
	return this.getKey() != null && this.getKey().startsWith("HTTP");
    }

    
    /**
     * This method implements Comparable.compareTo(...).
     *
     * It returns zero if this and 'element' are equal.
     * It returns a negative value if this is smaller than 'element'.
     * It returns a positive value if this is larger than 'element'.
     *
     * @param element The HTTPHeaderLine to compare with.
     **/
    public int compareTo( HTTPHeaderLine element ) {

	int cmp = this.getKey().compareTo(element.getKey());
	if( cmp != 0 )
	    return cmp;

	// Warning: the value might be null!
	if( this.getValue() == null )
	    return -1;
	else if( element.getValue() == null )
	    return 1;
	else
	    return this.getValue().compareTo(element.getValue());
					 
    }
    
    /**
     * Get the raw byte data for this header line.
     **/
    public byte[] getRawBytes( Charset charset ) {
	
	String tmp = this.getKey();
	if( this.getValue() != null )
	    tmp += ": " + this.getValue();
	
	tmp += "\n";
	// tmp += ((char)Constants.CR + (char)Constants.LF);

	return tmp.getBytes( charset );
    }


    /**
     * This method returns true if (and only if) this and 'element' are equal.
     *
     * Equal means that both keys and both values are equal.
     **/
    public boolean equals( HTTPHeaderLine element ) {
	return compareTo(element)==0;
    }

    /**
     * This method returns true if (and only if) this and the passed element are equal.
     *
     * If 'element' is not a HTTPHeaderLine instance the method returns false.
     **/
    public boolean equals( Object element ) {
	try {
	    return this.equals( (HTTPHeaderLine)element );
	} catch( ClassCastException e ) {
	    return false;
	}
    }

    /**
     * Read the next header line from the input stream. 
     * Note that only CR-LF is accepted as a HTTP entity line break! Single CR
     * single LF will _not_ instruct the reader to stop!
     * 
     * If the read line is empty this method will return null!
     *
     * You should use it to read the whole set of http headers from a given
     * input:
     *
     * InputStream in =  ...;
     * HTTPHeaderLine hl;
     * while( (hl = read(in)) != null ) {
     *     // Handle header line
     * }
     * // End of headers reached
     *
     * 
     * @param in The InputStream to read from.
     **/
    public static HTTPHeaderLine read( InputStream in )
	throws EOFException,
	       IOException {

	// System.out.println( " ----------- Bla" );

	/* input must not be null */
	if( in == null )
	    throw new NullPointerException( "Cannot read from null." );

	String line = CRLFLineReader.readLine( in );

	//System.out.println( "HTTPHeaderLine.read(InputStream) CRLF line read: "+line );
	
      
	return parse( line );
    }

    /**
     * This method parses header lines.
     * Usually a header line consists of a key- and a value-part, separated
     * by a colon (':'). As the value part may contain colons itself, the first
     * appearance of ':' is taken as the separator.
     *
     * If null is passed, the method returns a null header line!
     *
     * If the key and/or value are empty, a header line with the empty string ""
     * as key and/or value is returned.
     *
     * Keys and values are trimmed (whitespace at beginning and end are cut off).
     *
     * @param line The header line from the HTTP headers.
     * @return The parsed HTTPHeaderLine.
     **/
    public static HTTPHeaderLine parse( String line ) {

	

	
	if( line == null )
	    return null;
	
	KeyValuePair<String,String> pair = KeyValuePair.splitLine( line, 
								   ":",    // separator
								   true    // trim
								   );

	HTTPHeaderLine header = new HTTPHeaderLine( pair.getKey(), pair.getValue() );
	return header;

    }

    public String toString() {
	if( this.getValue() == null )
	    return this.getKey();
	else
	    return this.getKey() + ": " + this.getValue();
    }

}
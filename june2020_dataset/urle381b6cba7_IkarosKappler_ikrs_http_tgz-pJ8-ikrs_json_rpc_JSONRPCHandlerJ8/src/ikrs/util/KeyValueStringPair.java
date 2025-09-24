package ikrs.util;

/**
 * This class was once located in ikrs.http.datatype package.
 * The new implementation extents KeyValuePair class.
 *
 * @author Ikaros Kappler
 * @date 2012-10-10
 * @modified 2013-01-03 [moved from package ikrs.http.datatype to ikrs.util].
 * @version 1.0.0
 **/

public class KeyValueStringPair 
    extends KeyValuePair<String,String> {

    
    /**
     * Constructs a new Key-Value-Pair.
     *
     * @param k The key.
     * @param v The value.
     **/
    public KeyValueStringPair( String k, String v ) {
	super( k, v );
    }

    public static KeyValueStringPair split( String line ) {
	return KeyValueStringPair.split( line, true );
    }

    public static KeyValueStringPair split( String line,
					    boolean removeQuotes ) {
	
	return KeyValueStringPair.split( line, removeQuotes, "=" );
    }

    public static KeyValueStringPair split( String line,
					    boolean removeQuotes,
					    String  separator ) {

	if( line == null )
	    return null;

	line = line.trim();
	if( line.length() == 0 )
	    return new KeyValueStringPair( null, null );

	
	int index = line.indexOf( separator ); // "=" );
	String key = null;
	String value = null;
	
	if( index == -1 ) {

	    // A key only.
	    key   = line;

	} else if( index == 0 ) {

	    // A value with an empty key.
	    key   = "";     // The key SHOULD NOT be null if the key is not null.
	    value = line.substring(1).trim();

	} else if( index+1 >= line.length() ) {

	    // A key with an empty value
	    key   = line.substring(0, line.length()-1).trim();
	    value = "";

	} else {

	    // Key and value are present.
	    key   = line.substring( 0, index ).trim();
	    value = line.substring( index+1, line.length() ).trim();

	}

	// Remove quotes?
	if( removeQuotes ) {
	    key   = KeyValueStringPair.removeQuotes(key);
	    value = KeyValueStringPair.removeQuotes(value);
	}


	return new KeyValueStringPair( key, value );
    }

    private static String removeQuotes( String str ) {

	if( str == null )
	    return str;

	if( str.length() < 2 )
	    return str;

	if( (str.startsWith("\"") && str.endsWith("\""))
	    || (str.startsWith("'") && str.endsWith("'")) )  {

	    return str.substring( 1, str.length()-1 );

	} else {
	    
	    return str;

	}

    }

    
    /**
     * For testing only!
     **/
    public static void main( String[] argv ) {
    
	String[] arr = new String[] {
	    null,
	    "",
	    "testA",
	    "testB=",
	    "=X",
	    "testC=X",
	    "testD=\"Y\"",
	    "testE='Z'",
	    "testF='R\"",
	    "testG=\"S'",
	    "testH='T",
	    "testI=\"U",
	    "testJ=V\"",
	    "\"testK\"=W"
	};

	
	for( int i = 0; i < arr.length; i++ ) {

	    System.out.println( "[" + i +"] input=" + arr[i] +", parsed=" + KeyValueStringPair.split( arr[i], true ) );
	    
	}

    }

}
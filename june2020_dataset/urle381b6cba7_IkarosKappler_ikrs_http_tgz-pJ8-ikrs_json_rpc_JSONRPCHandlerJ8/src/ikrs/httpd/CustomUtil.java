package ikrs.httpd;


import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ikrs.typesystem.*;
import ikrs.util.CustomLogger;
import ikrs.util.CaseInsensitiveComparator;

/**
 * This class holds some helper methods.
 *
 * @author Ikaros Kappler
 * @date 2012-09-11
 * @version 1.0.0
 *
 **/


public class CustomUtil {



    public static boolean getBoolean( CustomLogger logger,
				      BasicType wrapper,
				      boolean nullSubstitute ) {
	
	if( wrapper == null )
	    return nullSubstitute;
	else
	    return wrapper.getBoolean();
    }


    public static String getFileExtension( File file ) 
	throws NullPointerException {
	
	int index = file.getName().lastIndexOf(".");
	if( index == -1 || index == 0 )
	    return null;
	else if( index == file.getName().length()-1 )
	    return "";
	else
	    return file.getName().substring( index+1, file.getName().length() );
	
    }

    public static String processCustomizedFilePath( String filePath ) {
	if( filePath == null )
	    return null;

	filePath = filePath.replaceAll( "\\{USER_HOME\\}", System.getProperty("user.home") );
	// ...
	
	return filePath;
    }

    public static boolean equalFileExtensions( String extensionA,
					       String extensionB,
					       boolean caseSensitive,
					       boolean dotSensitive ) {

	if( extensionA == extensionB )
	    return true; // same instance

	if( extensionA == null && extensionB != null )
	    return false;

	if( extensionA != null && extensionB == null )
	    return false;

	// At this point: both are NOT null
	// (otherwise the first case would have matched).

	// Remove dots?
	if( !dotSensitive && extensionA.startsWith(".") )
	    extensionA = extensionA.substring(1);
	
	if( !dotSensitive && extensionB.startsWith(".") )
	    extensionB = extensionB.substring(1);

	if( caseSensitive )
	    return extensionA.equals(extensionB);
	else
	    return extensionA.equalsIgnoreCase(extensionB);

    }

    public static boolean classImplementsInterface( Class<?> c,
						    String interfaceName,
						    boolean includeSuperClasses ) {
	
	Class<?>[] ifs = c.getInterfaces();
	boolean isFileHandler = false;
	for( int i = 0; i < ifs.length && !isFileHandler; i++ ) {
	    
	    if( ifs[i].getName().equals(interfaceName) ) {

		// Interface found!
		return true;

	    }
	    
	}

	// Recursive search?
	if( includeSuperClasses && c.getSuperclass() != null ) 
	    return CustomUtil.classImplementsInterface( c.getSuperclass(), interfaceName, includeSuperClasses );
	else
	    return false;

    }

    /**
     * This method checks if the passed string represents an MD5 checksum.
     *
     * The passed string must not be null.
     **/
    public static boolean isMD5( String str ) 
	throws NullPointerException {


	// MD5 hashes are 32 hex-characters long (representing 16 bytes)
	if( str.length() != 32 )
	    return false;


	// Each char must be a hex-character
	try {

	    for( int i = 0; i < str.length(); i++ ) {

		int tmp = hex2int( str.charAt(i) );

	    }
	    return true;

	} catch( NumberFormatException e ) {

	    return false;

	}
    }


    /**
     * This method checks if the passed string is in the Apache-MD5 format (such as hashes are
     * stored in apache's htaccess-files).
     *
     * The passed string must not be null.
     **/
    public static boolean isApacheMD5( String str ) 
	throws NullPointerException {

	// Format (example):
	// $apr1$Px0gsAPk$JW2APdbEhGlT5bLfqua2C1

	String[] split = str.split( "(\\$)" );
	

	if( split.length != 4 )
	    return false;

	if( split[0].length() != 0 )
	    return false;

	if( !split[1].equals("apr1") )
	    return false;

	if( split[2].length() != 8 )
	    return false;

	if( split[3].length() != 32 )
	    return false;

	return true;
    }

    
    /**
     * This method implodes the given String-array with the given separator by the use
     * of a StringBuffer.
     *
     * @param values    The array to implode.
     * @param separator The separator string to use.
     *
     * @return A new String with the format: values[0] + separator + values[1] + separator + ... + separator + values[n].
     **/
    public static String implode( String[] values,
				  String separator ) {
	
	StringBuffer buffer = new StringBuffer();
	for( int i = 0; i < values.length; i++ ) {

	    if( i > 0 )
		buffer.append( separator );
	    
	    buffer.append( values[i] );

	}
	
	return buffer.toString();
    }

    /**
     * This method implodes the given String-list with the given separator by the use                                                   
     * of a StringBuffer.                                                                                                                  
     *                                                                                                                                     
     * @param values    The list to implode.
     * @param separator The separator string to use.
     *                                                                                                                                     
     * @return A new String with the format: values.get(0) + separator + values.get(1) + separator + ... + separator + values.get(n).
     **/
    public static String implode( List<String> values,
                                  String separator ) {

        StringBuffer buffer = new StringBuffer();
        for( int i = 0; i < values.size(); i++ ) {

            if( i > 0 )
                buffer.append( separator );

            buffer.append( values.get(i) );

        }

        return buffer.toString();
    }


    /**
     * This method repeats the given token by concatenating it 'count' times with itself.
     *
     * return 'count' times tehe given token in a new string.
     **/
    public static String repeat( String token,
				 int count ) {
	StringBuffer buffer = new StringBuffer();
	while( count > 0 ) {

	    buffer.append( token );
	    count--;
	}
	
	return buffer.toString();
    }


    /**
     * This method parses the "Digest" authorization response (sent by the client).
     *
     * Such a response line has the format:
     *  [ pair0 [ "," pair1 [ "," pair2 [...] ] ] ]
     *
     * pair          := key "=" value
     * key           := token
     * value         := token | quoted_string
     * token         := <any alphanumeric string without spaces, "=" and ",">
     * quoted_string := '"' <any character sequence without quotes> '"' | "'" <any character sequence without quotes> "'"
     *
     * Example: 
     * username="keel", realm="Password Required", nonce="123456", uri="/secret", algorithm=MD5, response="c21340f3fdd7194ab8e397ff1a9a750f"
     *
     * The returned map will contain the parsed key-tuple-pairs. The key comparator is not case-sensitive.
     *
     **/
    public static Map<String,String> parseDigestAuthorizationChallenge( String line ) {

	Map<String,String> map = new TreeMap<String,String>( CaseInsensitiveComparator.sharedInstance );

	// This regex works but includes the quotes in the final result.
	// The line will be splitted into the single '<key>=<value>' elements by this regex.
	Pattern regex = Pattern.compile( "[^\\s]+=([^\\s\"']+|\"[^\"]*\"|'[^']*')" );


	Matcher regexMatcher = regex.matcher( line );
	while( regexMatcher.find() ) {

	    String token = regexMatcher.group();

	    // Remove comma?
	    if( token.endsWith(",") )
		token = token.substring(0,token.length()-1);

	    // Split at '='
	    int index = token.indexOf("=");
	    if( index == -1 ) {

		// A single '<key>' (without '=<value>')
		map.put( token, null );

	    } else if( index+1 >= token.length() ) {

		// A '<key>' with '=' but without '<value>'
		map.put( token.substring(0,index).trim(), "" );

	    } else {

		// A full '<key>=<value>' pair.

		String key   = token.substring(0,index).trim();
		String value = token.substring(index+1,token.length()).trim();
		
		// Remove quotes?	    
		/*if( value.length() >= 2 
		    && ( ( value.startsWith("\"") && value.endsWith("\"") )
			 ||
			 ( value.startsWith("'") && value.endsWith("'") ) 
			 ) ) {

		    value = value.substring( 1, value.length()-1 );
		}
		*/
		value = stripQuotes( value );

		map.put( key, value );
	    
	    }


	} 
	
	return map;
    }

    public static String stripQuotes( String str ) {
	if( str == null )
	    return null;
	
	if( str.length() >= 2 
	    && ( ( str.startsWith("\"") && str.endsWith("\"") )
		 ||
		 ( str.startsWith("'") && str.endsWith("'") ) 
		 ) ) {
	    
	    return str.substring( 1, str.length()-1 );
	} else {
	    return str;
	}
    }

    /*public static String[] splitAtWhitespace( String str ) {
	if( str == null )
	    return null;
	return str.split( "\\s+" );
	}*/

    public static int[] string2int( String[] splits ) 
	throws NullPointerException,
	       NumberFormatException {

	if( splits == null )
	    return null;
	int[] arr = new int[ splits.length ];
	for( int i = 0; i < splits.length; i++ ) {

	    if( splits[i] == null )
		throw new NullPointerException( "Token at index " + i + " is null and cannot be converted to int." );
	    
	    try {
		arr[i] = Integer.parseInt( splits[i].trim() );
	    } catch( NumberFormatException e ) {
		throw new NullPointerException( "Token at index " + i + " does not represent an int." );
	    }
	}
	
	return arr;
    }
    
    public static byte[] hex2bytes( String hex ) 
	throws NumberFormatException {

	if( hex == null )
	    return null;

	if( hex.length()%2 != 0 )
	    throw new NumberFormatException( "Cannot convert hex strings with odd numbers of characters to byte arrays." );

	byte[] arr = new byte[ hex.length() / 2 ];

	int hi, lo;
	char c_hi, c_lo;
	for( int i = 0; i < arr.length; i++ ) {

	    c_hi = hex.charAt( i*2 );
	    c_lo = hex.charAt( i*2 + 1);
	    
	    hi = hex2int( c_hi );
	    lo = hex2int( c_lo );
	    //System.out.println( "hi=" + c_hi + "(" + hi + "), lo=" +  c_lo + "(" + lo + ")" );

	    arr[i] = (byte)((hi << 4) | lo);

	    
	}

	return arr;
    }

 
    public static int hex2int( char hex ) 
	throws NumberFormatException {

	// '0' to '9' in ASCII: 48 to 57 (dec)
	// 'A' to 'F' in ASCII: 65 to 70 (dec)
	// 'a' to 'f' in ASCII: 97 to 102 (dec)
	
	if( hex >= 48 && hex <= 57 ) {

	    // '0' to '9'
	    return (hex-48);

	} else if( hex >= 97 && hex <= 102 ) {

	    // 'a' to 'f'
	    return (10 + hex-97);

	} else if( hex >= 65 && hex <= 70 ) {

	    // 'A' to 'F'
	    return (10 + hex-65);

	} else {

	    throw new NumberFormatException( "Cannot convert char '"+hex+"' to hex-nibble (not a hexadecimal character)." );

	}

    }

    public static String bytes2hexString( byte[] arr ) {
	
	StringBuffer b = new StringBuffer();

	for( int i = 0; i < arr.length; i++ ) {

	    b.append( upperNibble2hexChar(arr[i]) );
	    b.append( lowerNibble2hexChar(arr[i]) );

	}

	return b.toString();
    }

    public static char lowerNibble2hexChar( byte b ) {
	return Integer.toHexString( (b & 0x0F) ).charAt(0);
    }

    public static char upperNibble2hexChar( byte b ) {
	return Integer.toHexString( ((b>>4) & 0x0F) ).charAt(0);
    }
    

    public static long transfer( InputStream in,
				 OutputStream out,
				 long maxReadLength,
				 int bufferSize ) 
	throws IllegalArgumentException,
	       IOException {

	byte[] buffer = new byte[ bufferSize ];
	int len;
	long totalLength = 0;
	long bytesLeft = maxReadLength; // Math.max( maxReadLength, bufferSize );

	while( (maxReadLength == -1 || totalLength < maxReadLength)
	       //&& (len = in.read(buffer,0,(int)bytesLeft)) > 0 ) {
	       && (len = in.read(buffer,0,(int)Math.max( maxReadLength, bufferSize ))) != -1 ) {

	    //for( int i = 0; i < len; i++ )
	    //	System.out.print( "X" + (char)buffer[i] );

	    out.write( buffer, 0, len );
	    out.flush();
	    
	    totalLength += len;
	    bytesLeft -= len;
	}

	return totalLength;
    }

    // For testing only
    public static void main( String[] argv ) {

	String challenge = "username=\"keel\", realm=\"Password Required\", nonce=\"123456\", uri=\"/secret\", algorithm=MD5, response=\"c21340f3fdd7194ab8e397ff1a9a750f\"";
	Map<String,String> map = CustomUtil.parseDigestAuthorizationChallenge( challenge );
	System.out.println( "map=" + map );
	

    }
    /*public static void main( String[] argv ) {

	String hex = "098f6bcd4621d373cade4e832627b4f6";
	if( argv.length != 0 )
	    hex = argv[0];

	System.out.println( "Going to convert string '" + hex + "' to [-xxx-] ..." );
	
	try {

	    for( int i = 0; i < hex.length(); i++ ) {

		char c = hex.charAt(i);
		System.out.println( "Value of '" + c + "' is " + hex2int(c) );

	    }

	    
	    byte[] arr = hex2bytes( hex );
	    System.out.println( "Bytes: " );
	    for( int i = 0; i < arr.length; i++ )
		System.out.println( "["+i+"] " + arr[i] );
	    

	    


	} catch( NumberFormatException e ) {

	    e.printStackTrace();

	}

	}*/

}
package ikrs.httpd;

import java.io.Console;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * This a customized MD5 wrapper.
 *
 * @author Ikaros Kappler
 * @date 2012-09-27
 * @version 1.0.0
 **/

public class MD5 {

    public static final String IDENTIFIER_STRING = "{ikrs.http.MD5}";
 

    /**
     * This class is not meant to be instantiated.
     **/
    private MD5() {


    }


    


    /**
     * This method checks if the passed string is in the IKRS-MD5 format (such as hashes are
     * stored in MY htaccess-files).
     *
     * The passed string must not be null.
     **/
    public static boolean isIKRSMD5( String str ) 
	throws NullPointerException {

	try {
	    
	    String[] tmp = splitIKRSMD5(str);
	    return true;

	} catch( NumberFormatException e ) {
	    
	    return false;

	}

    }

    /**
     * This method splits the passed password-encrypted text line into 4 segments.
     *
     * The line must have the format:
     *  ${ikrs.http.MD5}$ssssssss$xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
     *
     * Where 's' is the salt (8 characters!) 
     * and 'x' is the MD5-encrypted user, password and realm data (32 hex chars).
     *
     * If the passed lines does not match the expected format a NumberFormatException will 
     * be thrown.
     **/
    public static String[] splitIKRSMD5( String str ) 
	throws NumberFormatException {

	// Format (example):
	// keel:${ikrs.http.MD5}$12345678$xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

	String[] split = str.split( "(\\$)" );
	

	if( split.length != 4 )
	    throw new NumberFormatException( "The string is not an ikrs-MD5 sum (code=0)." );

	if( split[0].length() != 0 )
	    throw new NumberFormatException( "The string is not an ikrs-MD5 sum (code=1)." );

	if( !split[1].equals(MD5.IDENTIFIER_STRING) )
	    throw new NumberFormatException( "The string is not an ikrs-MD5 sum (code=2)." );

	if( split[2].length() != 8 )
	    throw new NumberFormatException( "The string is not an ikrs-MD5 sum (code=3)." );

	if( split[3].length() != 32 )
	    throw new NumberFormatException( "The string is not an ikrs-MD5 sum (code=4)." );

	return split;
    }


    /**
     * This method encodes the MD5 hash of 'user + realm + passData' and builts the
     * encrypted password line (to store in a .htpassword file).
     *
     * The returned string can be splitted by the use of the splitIKRSMD5 method.
     *
     * @param salt      The salt must be a 8-character string; I encourage you to use base64 chars only (for later
     *                  compatibility with apache-MD5).
     * @param user      The username, must not be empty and should consist of alpha-numeric chars. No colons ':' are
     *                  allowed.
     * @param realm     The realm name. This can be any non-empty string and should match _exactly_ (!) the realm
     *                  name from your .htaccess file.
     * @param passData  The actual password as a char-array.
     * @see MD5.splitIKRSMD5( String )
     **/ 
    public static String ikrsMD5( String salt,
				  String user,
				  String realm,
				  char[] passData ) 
	throws NoSuchAlgorithmException,
	       UnsupportedEncodingException {

	
	String rawData              = user + ":" + realm + ":" + new String(passData);
	// The md5 bytes contain 16 raw MD5 bytes (!), NOT characters!
	byte[] md5Bytes             = MD5.md5( rawData.getBytes("UTF-8") ); // messageDigest.digest( rawData.getBytes("UTF-8") ); 

       	
	return 
	    "$" + MD5.IDENTIFIER_STRING + 
	    "$" + salt +
	    "$" + CustomUtil.bytes2hexString( md5Bytes );
    }


    /**
     * This method uses the default MD5 message digest algorithm to encode the passed byte data.
     *
     * The returned byte array contains exactly 16 bytes.
     *
     * @param data The data to encrypt.
     **/ 
    public static byte[] md5( byte[] data ) 
	throws NoSuchAlgorithmException {

	
	MessageDigest messageDigest = MessageDigest.getInstance( "MD5" );
	byte[] md5Bytes             = messageDigest.digest( data ); 

	return md5Bytes;
    }


    /**
     * This main-method can be used to run this program and create new .htpasswd entries.
     *
     * Just type:
     *  java ikrs.http.MD5
     *
     **/
    public static void main( String[] argv ) {

	
	// Usage: java ikrs.http.MD5 -r <realm> [-s <salt>] -u <user> [-p <password>]
	
	String realm = null;
	String user  = null;
	String salt  = null;
	char[] pass  = null;

	for( int i = 0; i < argv.length; i++ ) {


	    if( argv[i].equals("-r") )
		realm = argv[ ++i ];
	    else if( argv[i].equals("-s") )
		salt  = argv[ ++i ];
	    else if( argv[i].equals("-u") )
		user  = argv[ ++i ];
	    else if( argv[i].equals("-p") ) 
		pass  = argv[ ++i ].toCharArray();
	    else {

		System.out.println( "Unknown argument: " + argv[i] );
		printUsage();
		System.exit(1);

	    }

	}

	if( realm == null ) {

	    //user == null ) {
	    System.err.println( "Missing realm!" );
	    printUsage();
	    System.exit(1);
	}

	if( user == null ) {

	    //user == null ) {
	    System.err.println( "Missing user!" );
	    printUsage();
	    System.exit(1);
	}

	if( salt != null && salt.length() != 8 ) {
	    printUsage();
	    System.exit(1);
	}

	if( salt == null ) {

	    System.out.println( "Generating random salt ... " );

	    // Make a random Base64 representation for later compatibility.
	    // (8 chars represent 6 bytes, to there are no fill-bits required)
	    String seed = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

	    // Make random salt
	    char[] tmp = new char[8];
	    for( int i = 0; i < tmp.length; i++ ) {

		double random = Math.random();
		
		tmp[i] = seed.charAt( (int)(random * seed.length()) );
		

	    }

	    salt = new String(tmp);
	}


	if( pass == null ) {
	    Console cons = null;

	    if ((cons = System.console()) != null &&
		(pass = cons.readPassword("[%s] ", "Password:")) != null) {
		//
		//java.util.Arrays.fill(passwd, ' ');
	    }
	}

	
	try {

	    String ikrsMD5 = ikrsMD5( salt, user, realm, pass );
	    System.out.println( "Your hashed password (line for .htpasswd): ");
	    System.out.println( user + ":" + ikrsMD5 );

	} catch( NoSuchAlgorithmException e ) {

	    e.printStackTrace();

	} catch( UnsupportedEncodingException e ) {

	    e.printStackTrace();

	}


    }

    private static void printUsage() {
	System.out.println( "Usage: java ikrs.httpd.MD5 -r <realm> [-s <salt>] -u <user> [-p <password>]" );
	System.out.println( " - salt must be 8 characters long." );
    }

}
package ikrs.httpd.resource;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.Map;


import ikrs.io.fileio.htaccess.HypertextAccessFile;
import ikrs.io.fileio.htaccess.HypertextPasswordFile;
import ikrs.util.CustomLogger;
import ikrs.httpd.Constants;
import ikrs.httpd.ConfigurationException;
import ikrs.httpd.CustomUtil;
import ikrs.httpd.HTTPHeaders;
import ikrs.httpd.MD5;
import ikrs.httpd.MissingParamException;

import ikrs.typesystem.BasicType;
import ikrs.typesystem.BasicStringType;

/**
 * This class reads and processes the .htaccess and .htpasswd configuration for resource access.
 *
 * @author Ikaros Kappler
 * @date 2012-09-13
 * @version 1.0.0
 **/


public class HypertextAccessHandler {

    /**
     * A logger to write log messages to.
     **/
    private CustomLogger logger;

    /**
     * This flag indicates if .htaccess and .htpasswd file should be parsed in strict mode.
     **/
    private boolean strictMode;


    /**
     * Create a new HypertextAccessHandler.
     * 
     * @param logger     A logger to write log messages to.
     * @param strictMode If set to true small errors or irregularities will _not_ be ignored
     *                   while parsing the .htaccess or .htpasswd files.
     *
     **/
    public HypertextAccessHandler( CustomLogger logger,
				   boolean strictMode ) {
	super();

	this.logger = logger;
	this.strictMode = strictMode;
    }

    public boolean isStrictMode() {
	return this.strictMode;
    }


    /**
     * This method checks whether the passed user has .htaccess-driven access to teh given directory (the
     * directory the .htaccess is located in).
     *
     * @param htaccessFile
     * @param authMethod
     * @param authUser
     * @param authPassword
     **/
    public boolean accessGranted( URI requestURI,
				  HTTPHeaders headers,
				  HypertextAccessFile htaccess,
				  String authMethod,

				  Map<String,BasicType> additionalSettings,
				  Map<String,BasicType> optionalReturnSettings
				  ) 
	throws IOException,
	       MissingParamException,
	       ConfigurationException {


	// Switch "Requires" settings ...
	if( htaccess.requiresValidUser() ) {

	    //return checkValidUser( htaccess, authMethod, authUser, authPassData );
	    return checkValidUser( requestURI, headers, htaccess, authMethod, additionalSettings, optionalReturnSettings );
	    
	} else if( htaccess.getRequiredUsers().size() != 0 ) {

	    throw new ConfigurationException( "The \"Require User\" directive is not yet supported. Use \"Require valid-user\" instead." );

	} else if( htaccess.getRequiredGroups().size() !=0 ) {

	    throw new ConfigurationException( "The \"Require Group\" directive is not yet supported. Use \"Require valid-user\" instead." );

	} else {

	    throw new ConfigurationException( "The htaccess file has no \"Require\" directive." );

	}

    }



     /**
     * This method checks whether the passed user has .htaccess-driven access to teh given directory (the
     * directory the .htaccess is located in).
     *
     * @param requestURI
     * @param headers
     * @param htaccess
     * @param authMethod
     * @param authUser
     * @param authPassword
     **/
    private boolean checkValidUser( URI requestURI,
				    HTTPHeaders headers,
				    HypertextAccessFile htaccess,
				    String authMethod,
				    //String authUser,
				    //char[] authPassData
				    
				    Map<String,BasicType> additionalSettings,
				    Map<String,BasicType> optionalReturnSettings
				    ) 
	throws IOException,
	       MissingParamException,
	       ConfigurationException {


	// The sent auth type and the AuthType from the htaccess file must be equal!
	// [A user cann authorize using 'Basic' when the file requires 'Digest', ...]
	if( htaccess.getAuthType() != null && !authMethod.equalsIgnoreCase(htaccess.getAuthType()) ) {

	    this.logger.log( Level.INFO,
			     getClass().getName() + ".checkValidUser(...)",
			     "User is not allowed to auth because the sent method is '"+authMethod+"' but the configured htaccess file requires '"+htaccess.getAuthType()+"'."
			     );
		
	    return false;
	}



	File authUserFile = htaccess.getAuthUserFile();
	if( authUserFile == null ) {

	    // Auto use ".htpasswd" as default ???
	    if( this.strictMode ) {

		this.logger.log( Level.WARNING,
				 getClass().getName() + ".checkValidUser(...)",
				 "No AuthUserFile specified in htaccess file. Stopping (strictMode="+strictMode+")."
				 );
		throw new ConfigurationException( "No AuthUserFile specified in htaccess file." );

	    } else {

		this.logger.log( Level.WARNING,
				 getClass().getName() + ".checkValidUser(...)",
				 "No AuthUserFile specified in htaccess file. Ignoring by the use of default '.htpasswd' file (strictMode="+strictMode+")." );
		authUserFile = new File( htaccess.getSourceFile().getParentFile(), ".htpasswd" );

	    }

	}


	// .htpasswd file exists?
	if( !authUserFile.exists() ) {

	    throw new ConfigurationException( "Cannot read AuthUserFile: file not found." );

	}



	// Switch authorization method
	if( authMethod.equalsIgnoreCase("Basic") ) {

	    return checkValidUser_basicAuthentication( requestURI,
						       headers,
						       htaccess,
						       authMethod,

						       authUserFile,

						       additionalSettings,
						       optionalReturnSettings
						       );

	} else if( authMethod.equalsIgnoreCase("Digest") ) {

	    
	    return checkValidUser_digestAuthentication( requestURI,
							headers,
							htaccess,
							authMethod,

							authUserFile,

							additionalSettings,
							optionalReturnSettings
							);

	} else {	    

	    this.logger.log( Level.SEVERE,
			     getClass().getName() + ".checkValidUser(...)",
			     "Oooops, the authentication method \""+authMethod+"\" is not supported. Usually this case should be detected much earlier!" );
	    throw new ConfigurationException( "The authentication method \""+authMethod+"\" is not supported." );
	}
	
    }

    //Constannts.KEY_AUTHORIZATION_DIGESTCHALLENGE


    private boolean checkValidUser_basicAuthentication( URI requestURI,
							HTTPHeaders headers,
							HypertextAccessFile htaccess,
							String authMethod,
							File authUserFile,
							Map<String,BasicType> additionalSettings,
							Map<String,BasicType> optionalReturnSettings
							) 
	throws IOException,
	       ConfigurationException {


	BasicType wrp_authUser   = additionalSettings.get( Constants.KEY_AUTHORIZATION_USER );
	BasicType wrp_authPass   = additionalSettings.get( Constants.KEY_AUTHORIZATION_PASS );
	if( wrp_authUser == null || wrp_authPass == null ) 
	    return false; // No authorization credentials passed (at least one missing)

	String authUser          = wrp_authUser.getString();
	char[] authPassData      = wrp_authPass.getString().toCharArray();
	

	// Locate user in the AuthUserFile
	char[] filePassData = null;
	
	try {
	    filePassData = HypertextPasswordFile.getPasswordData( authUserFile, 
								  authUser, 
								  this.strictMode );
	    // Found in file?
	    if( filePassData == null ) 
		return false;       // Not in the user list

	    // Password data exists -> store into returnSettings
	    this.storeAuthUserSettings( filePassData, optionalReturnSettings );
	    
	    
	} catch( ParseException e ) {

	    this.logger.log( Level.WARNING,
			     getClass().getName() + ".checkValidUser_basicAuthentication(...)",
			     "Cannot verify user. Malformed AuthUserFile: " + e.getMessage() );
	    throw new ConfigurationException( "Cannot verify user. Malformed AuthUserFile.", e );

	} 


 
	return compareMD5Passwords( filePassData, authPassData );
	    
    }


    private boolean checkValidUser_digestAuthentication( URI requestURI,
							 HTTPHeaders headers,
							 HypertextAccessFile htaccess,
							 String authMethod,
							 File authUserFile,
							 Map<String,BasicType> additionalSettings,
							 Map<String,BasicType> optionalReturnSettings
							) 
	throws IOException,
	       MissingParamException,
	       ConfigurationException {


	// The challenge should be something in this format:
	// username="keel", realm="Password Required", nonce="123456", uri="/secret", algorithm=MD5, response="c21340f3fdd7194ab8e397ff1a9a750f"
	BasicType wrp_challenge = additionalSettings.get( Constants.KEY_AUTHORIZATION_CHALLENGE );


	if( wrp_challenge == null || wrp_challenge.getString().length() == 0 )
	    return false; // No authentication credentials passed!


	// Explode the passed challenge to retrieve the single key-value-pairs
	// The essential values are:
	//  - algorithm (should be "MD5", also possible would be "MD5-sess" - but that's not implemented yet)
	//  - nonce     (any string value, previously generated by this server)
	//  - realm     (from the htaccess file)
	//  - response  (the MD5 sum, hex-string, 32 chars / 16 bytes)
	//  - uri       (the access uri, previously sent by this server)
	//  - username 
	Map<String,String> challengeMap = CustomUtil.parseDigestAuthorizationChallenge( wrp_challenge.getString() );
	this.logger.log( Level.INFO,
			 getClass().getName() + ".checkValidUser(...)",
			 "challengeMap=" + challengeMap 
			 );


	// Note: the map's key comparator is case-insensitive.
	String chl_algorithm = challengeMap.get("algorithm");
	String chl_realm     = challengeMap.get("realm");
	String chl_response  = challengeMap.get("response");
	String chl_uri       = challengeMap.get("uri");
	String chl_username  = challengeMap.get("username");
	String chl_nonce     = challengeMap.get("nonce");



	// All params must exist.
	if( chl_algorithm   == null || chl_algorithm.equals("") 
	    || chl_realm    == null || chl_realm.equals("") 
	    || chl_response == null || chl_response.equals("") 
	    || chl_uri      == null || chl_uri.equals("") 
	    || chl_username == null || chl_username.equals("") 
	    || chl_nonce    == null || chl_nonce.equals("")
	    ) {

	    this.logger.log( Level.INFO,
			     getClass().getName() + ".checkValidUser(...)",
			     "Some required authorization essentials are missing; alrorithm=" + chl_algorithm + ", realm="+chl_realm+", response="+chl_response+", uri="+chl_uri+", username="+chl_username + ", nonce=" + chl_nonce );
	    
	    throw new MissingParamException( "Cannot complete authorization process. The response misses some required essentials." );
	    
	}


	// The passed nonce and the local nonce MUST BE EQUAL! (the same counts for the realm?!)
	// ...
	

	// ...
	// Here: all credentials are present.

	
	this.logger.log( Level.INFO,
			 getClass().getName() + ".checkValidUser_digestAuthentication_(...)",
			 "challengeMap=" + challengeMap );

	return checkValidUser_digestAuthentication_processCredentials( requestURI, headers, htaccess, authMethod, authUserFile, 
								       additionalSettings, optionalReturnSettings,
								       challengeMap,
								       chl_algorithm,
								       chl_realm,
								       chl_response,
								       chl_uri,
								       chl_username,
								       chl_nonce
								       );

    }


    private boolean checkValidUser_digestAuthentication_processCredentials(  URI requestURI,
									     HTTPHeaders headers,
									     HypertextAccessFile htaccess,
									     String authMethod,
									     File authUserFile,
									     Map<String,BasicType> additionalSettings,
									     Map<String,BasicType> optionalReturnSettings,

									     Map<String,String> challengeMap,

									     String chl_algorithm,
									     String chl_realm,
									     String chl_response,
									     String chl_uri,
									     String chl_username,
									     String chl_nonce
									     ) 
	throws IOException,
	       MissingParamException,
	       ConfigurationException {

	
	// Calculate the final MD5 checksum (hopefully the same as specified in the htaccess file).
	// See: http://blog.smartbear.com/software-quality/bid/169684/How-to-Use-Digest-HTTP-Authentication-in-soapUI
	// ------------------------------------------------------
	// [...]
	// In the first version ( RFC 2069 ) the algorithm is:
	//
	// A1 = username:realm:password
	// A2 = method:digestURI
	// HA1 = MD5( A1 )
	// HA2 = MD5( A2 )
	// response = MD5( HA1:nonce:HA2 )
	// [...]
	// ------------------------------------------------------
	// For more details see 
	//  - http://tools.ietf.org/html/rfc2617
	// and
	//  - http://tools.ietf.org/html/rfc2069



	char[] filePassData = null;
	try {
	    filePassData = HypertextPasswordFile.getPasswordData( authUserFile, 
								  chl_username, 
								  this.strictMode );
	    // Found in file?
	    if( filePassData == null ) 
		return false;       // Not in the user list

	    // Password data exists -> store into returnSettings
	    this.storeAuthUserSettings( filePassData, optionalReturnSettings );
	    
	    
	    String[] filePassData_split = null;
	    //if( !MD5.isIKRSMD5(new String(filePassData)) ) {
	    try {

		filePassData_split = MD5.splitIKRSMD5( new String(filePassData) );


	    } catch( NumberFormatException e ) {

		this.logger.log( Level.INFO,
				 getClass().getName() + ".checkValidUser_digestAuthentication_processCredentials(...)",
				 "The hashed data from the AuthUserFile '" + authUserFile.getPath() + "' is not an IKRS-MD5 (user=" + chl_username + "): " + e.getMessage() );
		throw new ConfigurationException( "The hashed data from the AuthUserFile is not an IKRS-MD5." );

	    }

	    
	    // The splits are: { "", <identifier>, <salt>, <md5-hash> }
	    String file_salt       = filePassData_split[2];
	    String file_md5        = filePassData_split[3];



	    // The MD5 loaded from the file is already HA1:
	    //    HA1 := MD5( chl_username + ":" + chl_realm + ":" + chl_password ) = MD5( H1 )
	    // so we do not need to re-calculate anything for HA1! :)
	    // H1 is unknown here (no one wants to store plain passwords on their servers!)
	    String HA1             = file_md5;  


    	    // On Unix systems usually the password's hashes are MD5 encrypted (what about windows?)
	    MessageDigest messageDigest = MessageDigest.getInstance( chl_algorithm );
	    String A2              = headers.getRequestMethod() + ":" + chl_uri;
	    // The HA2_bytes contains 16 raw MD5 bytes (!), NOT characters!
	    byte[] HA2_bytes       = messageDigest.digest( A2.getBytes(java.nio.charset.StandardCharsets.UTF_8.name()) ); 


	    String HA2             = CustomUtil.bytes2hexString(HA2_bytes);
	    String finalHash_seed  = HA1 + ":" + chl_nonce + ":" + HA2;
	    byte[] finalHash_bytes = messageDigest.digest( finalHash_seed.getBytes(java.nio.charset.StandardCharsets.UTF_8.name()) );
	    String finalHash       = CustomUtil.bytes2hexString(finalHash_bytes);


	    
	    // Now: compare both hashes
	    try {

		this.logger.log( Level.INFO,
				 getClass().getName() + ".checkValidUser_digestAuthentication_processCredentials(...)",
				 "Going to compare hashes/response; HA1=" + HA1 + ", A2="+ A2 + ", HA2=" + HA2 + ", finalHash_seed=" + finalHash_seed + ", finalHash=" + finalHash );

		byte[] response_bytes = CustomUtil.hex2bytes( chl_response );

		boolean equal = java.util.Arrays.equals( response_bytes, finalHash_bytes );

		this.logger.log( Level.INFO,
				 getClass().getName() + ".checkValidUser_digestAuthentication_processCredentials(...)",
				 "chl_response=" + chl_response + ", finalHash=" + finalHash + ", equal=" + equal );
		

		return equal;

	    } catch( NumberFormatException e ) {

		// The passed response (from the client!) seems NOT to be a valid MD5 checksum.
		this.logger.log( Level.INFO,
			     getClass().getName() + ".checkValidUser_digestAuthentication_processCredentials(...)",
			     "The passed response (from the client!) seems NOT to be a valid MD5 checksum: " + chl_response );

		// Auth failed.
		return false; 

	    }

	    


	} catch( IOException e ) {

	    this.logger.log( Level.SEVERE,
			     getClass().getName() + ".checkValidUser_digestAuthentication_processCredentials(...)",
			     "[IOException] " + e.getMessage() );
	    throw e;

	} catch( ParseException e ) {

	    this.logger.log( Level.WARNING,
			     getClass().getName() + ".checkValidUser_digestAuthentication_processCredentials(...)",
			     "Cannot verify user. Malformed AuthUserFile: " + e.getMessage() );
	    throw new ConfigurationException( "Cannot verify user. Malformed AuthUserFile.", e );


	} catch( NoSuchAlgorithmException e ) {
	    
	    this.logger.log( Level.SEVERE,
			     getClass().getName() + ".checkValidUser_digestAuthentication_processCredentials(...)",
			     "[NoSuchAlgorithmException] Failed to retrieve hashing algorithm '" + chl_algorithm +"': " + e.getMessage() );
	    throw new ConfigurationException( "Failed to retrieve hashing algorithm '" + chl_algorithm + "'." );
	    
	} 
	
    }


    /**
     * This method compares the two passwords and checks if they have the same MD5 checksum.
     **/
    private boolean compareMD5Passwords( char[] filePassData,
					 char[] authPassData ) 
	throws ConfigurationException {


	String hashAlgorithm = "MD5";
	try {

	    // On Unix systems usually the password's hashes are MD5 encrypted (what about windows?)
	    MessageDigest messageDigest = MessageDigest.getInstance( hashAlgorithm );
	    // The incomingMD5Bytes contains 16 raw MD5 bytes (!), NOT characters!
	    byte[] incomingMD5Bytes     = messageDigest.digest( new String(authPassData).getBytes(java.nio.charset.StandardCharsets.UTF_8.name()) ); 

	
	    String fileMD5     = new String( filePassData );


	    if( CustomUtil.isApacheMD5(fileMD5) ) {

		this.logger.log( Level.INFO,
				 getClass().getName() + ".compareMD5Passwords(...)",
				 "It seems that you htaccess configuration contains an Apache MD5 encrypted hash. This type is not yet supported, sorry." );
		throw new ConfigurationException( "It seems that you htaccess configuration contains an Apache MD5 encrypted hash. This type is not yet supported, sorry." );
		
	    } else if( MD5.isIKRSMD5(fileMD5) ) {

		this.logger.log( Level.INFO,
				 getClass().getName() + ".compareMD5Passwords(...)",
				 "It seems that you htaccess configuration contains an IKRS MD5 encrypted hash. This type is not yet supported, sorry." );
		throw new ConfigurationException( "It seems that you htaccess configuration contains an IKRS MD5 encrypted hash. This type is not yet supported, sorry." );


	    } else if( CustomUtil.isMD5(fileMD5) ) {

		// Are both equal?
		byte[] fileMD5Bytes = CustomUtil.hex2bytes( fileMD5 );
		String incomingMD5 = new String( incomingMD5Bytes );
		this.logger.log( Level.INFO,
				 getClass().getName() + ".compareMD5Passwordsr(...)",
				 "Comparing passwords ..." ); // : incomingPass=" + new String(authPassword) + ", incomingMD5="+ incomingMD5 + ", incomingMD5[HEX]=" + CustomUtil.bytes2hexString(incomingMD5Bytes)+ ", fileMD5="+fileMD5+", equal="+(incomingMD5.equals(fileMD5)) );

		
		return java.util.Arrays.equals( fileMD5Bytes, incomingMD5Bytes );

	    } else {

		this.logger.log( Level.INFO,
				 getClass().getName() + ".compareMD5Passwords(...)",
				 "It seems that you htaccess configuration does not contain a valid MD5 hash." );
		throw new ConfigurationException( "It seems that you htaccess configuration does not contain a valid MD5 hash." );

	    }

	} catch( NoSuchAlgorithmException e ) {
	    
	    this.logger.log( Level.SEVERE,
			     getClass().getName() + ".compareMD5Passwords(...)",
			     "[NoSuchAlgorithmException] Failed to retrieve hashing algorithm '" + hashAlgorithm +"': " + e.getMessage() );
	    throw new ConfigurationException( "Failed to retrieve hashing algorithm '" + hashAlgorithm + "'." );
	    
	} catch( UnsupportedEncodingException e ) {
	    
	    this.logger.log( Level.WARNING,
			     getClass().getName() + ".compareMD5Passwords(...)",
			     "[UnsupportedEncodingException] Unsupported password encoding: " + e.getMessage() );
	    throw new ConfigurationException( "Unsupported encoding." );
	    
	} 
	
    } // END method
    

    private void storeAuthUserSettings( char[] filePass, 
					Map<String,BasicType> optionalReturnSettings ) {

	
	optionalReturnSettings.put( Constants.KEY_HTPASSWD_ENCRYPTEDLINE, new BasicStringType( new String(filePass) ) );

    }
}
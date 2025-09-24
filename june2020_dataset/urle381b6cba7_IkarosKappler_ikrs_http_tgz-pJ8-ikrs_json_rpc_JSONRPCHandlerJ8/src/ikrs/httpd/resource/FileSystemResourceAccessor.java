package ikrs.httpd.resource;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.ParseException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.UUID;
import java.util.logging.Level;

import ikrs.httpd.AuthorizationException;
import ikrs.httpd.ConfigurationException;
import ikrs.httpd.Constants;
import ikrs.httpd.CustomUtil;
import ikrs.httpd.DataFormatException;
import ikrs.httpd.FileHandler;
import ikrs.httpd.HeaderFormatException;
import ikrs.httpd.HTTPConnectionUserID;
import ikrs.httpd.HTTPHandler;
import ikrs.httpd.HTTPHeaders;
import ikrs.httpd.MD5;
import ikrs.httpd.MissingParamException;
import ikrs.httpd.PostDataWrapper;
import ikrs.httpd.Resource;
import ikrs.httpd.ResourceAccessor;
import ikrs.httpd.UnsupportedFormatException;


import ikrs.typesystem.BasicNumberType;
import ikrs.typesystem.BasicStringType;
import ikrs.typesystem.BasicType;
import ikrs.util.CustomLogger;
import ikrs.util.DefaultEnvironment;
import ikrs.util.Environment;
import ikrs.util.MIMEType;
import ikrs.util.session.Session;

import ikrs.io.fileio.htaccess.HypertextAccessFile;

/**
 * The FileSystemResourceAccessor is the default implementation of ResourceAccessor that handles
 * requests to the document root respective to the file system (the document root is part of the
 * file system).
 *
 * @autor Ikaros Kappler
 * @date 2012-07-23
 * @version 1.0.0
 **/


public class FileSystemResourceAccessor
    extends AbstractResourceAccessor 
    implements ResourceAccessor {


    private HypertextAccessHandler hypertextAccessHandler;
    
    private CustomLogger logger;

    /**
     * ... 
     **/
    public FileSystemResourceAccessor( HTTPHandler handler,
				       CustomLogger logger ) {
	super( handler );
	
	
	this.hypertextAccessHandler = new HypertextAccessHandler( logger, 
								  false    // strictMode
								  );
	this.logger = logger;
    }

    private CustomLogger getLogger() {
	return this.logger;
    }

    /**
     * This method locates the desired resource addressed by the given URI.
     *
     * @throws ResouceMissingException If the specified resource cannot be found.
     * @throws AuthorizationException If the requested resource requires authorization.
     * @throws HeaderFormatException If the passed headers are malformed.
     * @throws DataFormatException If the passed data is malformed.
     * @throws ConfigurationException If the system encounters any server configuration issues.
     * @throws SecurityException If the requested resource is not accessible (forbidden).
     * @throws IOException If any IO errors occur.
     **/
    public Resource locate( URI uri,
			    HTTPHeaders headers,
			    PostDataWrapper postData,
			    Map<String,BasicType> additionalSettings,
			    Map<String,BasicType> optionalReturnSettings,
			    UUID sessionID
			    )
	throws MissingResourceException,
	       AuthorizationException,
	       HeaderFormatException,
	       DataFormatException,
	       UnsupportedFormatException,
	       ConfigurationException,
               SecurityException,
	       IOException {

	
	this.getHTTPHandler().getLogger().log( Level.INFO,
					       getClass().getName() + ".locate(...)",
					       "URI="+uri+", host="+uri.getHost()+", path="+uri.getPath()+", query="+uri.getQuery() );

	
       


	//Environment<String,BasicType> session = new DefaultEnvironment<String,BasicType>();
	Session<String,BasicType,HTTPConnectionUserID> session = this.getHTTPHandler().getSessionManager().get( sessionID );

	// Well ... this should not happen. At this point the accessor MUST have a valid session.
	if( session == null ) {
	    
	   this.getHTTPHandler().getLogger().log( Level.SEVERE,
						  getClass().getName() + ".locate(...)",
						  "[Internal error] The current accessor has no valid session! (SID="+ sessionID +"). Continuing with an empty fake session" ); 
	   throw new RuntimeException( "[Internal error] The current accessor has no valid session! (SID="+ sessionID +")." );

	}



	String path = uri.getPath();	
	File requestedFile = new File( this.getHTTPHandler().getDocumentRoot(), path );

	// The request URI might be handled by a customized handler, so the file
	// does not nesessarily exist! (imagine a virtual access path such as in RAILS)
	/*
	if( !requestedFile.exists() ) {

	    throw new MissingResourceException( "File '"+path+"' not found.",
						requestedFile.getClass().getName(),
						path 
						);

						}*/
	

	// String extension = CustomUtil.getFileExtension( requestedFile ); 

	HypertextAccessFile htaccess = null;
	try {

	    htaccess = this.loadHypertextAccessFile( requestedFile, uri, session );


	    if( htaccess != null && optionalReturnSettings != null ) {

		this.mapHypertextAccessRequirements( htaccess, requestedFile, optionalReturnSettings );

	    }

	} catch( ParseException e ) {

	    this.getHTTPHandler().getLogger().log( Level.INFO,
						   getClass().getName() + ".locate(...)",
						   "Failed to read .htaccess configuration (parse error). path="+uri.getPath() + ". Error message: " + e.getMessage() );
	    throw new ConfigurationException( "Failed to read .htaccess configuration (parse error).",
					      e );

	}


	
	if( htaccess != null ) {
 
	    if( requestedFile.isDirectory() ) {

		// Hint: if the 'DirectoryIndex' directive is set in the Hypertext Access File
		//       the directory listing is switched off!
		// Seach an EXISTING (!) file from the DirectoryIndex list.
		requestedFile = this.htaccess_resolveDirectoryIndex( requestedFile, htaccess );

	    }	   
	    
	} 


	// If no DirectoryIndex was found the request expects directory listing to be
	// generated. 
	// This MUST NOT happen if the htaccess disabled the directory listing using
	// the option:
	// Option -Indexes
	if( requestedFile.exists() 
	    && requestedFile.isDirectory() 
	    && !this.htaccess_checkDirectoryListingAllowed(requestedFile,htaccess) ) {

	    // This will cause a 403 (Forbidden) status in the ResponseBuilder.
	    throw new SecurityException( "Directory listing disabled." );

	}

	

	// Fetch the file extension from the FINAL file!
	// The file extension is used to determine the MIME-type/Content-Type.
	String extension = CustomUtil.getFileExtension( requestedFile ); 
	// The file handler can be null!
	FileHandler fileHandler = this.htaccess_resolveFileHandler( uri, 
								    requestedFile, 
								    extension, 
								    htaccess ); // this.getHTTPHandler().getFileHandler( extension );

	this.getHTTPHandler().getLogger().log( Level.INFO,
					       getClass().getName() + ".locate(...)",
					       "Resolved file handler for requested file? fileHandler=" + fileHandler );  // may be null

	if( fileHandler != null 
	    && fileHandler.requiresExistingFile() 
	    && !requestedFile.exists() ) {

	    throw new MissingResourceException( "File '"+path+"' not found.",
						requestedFile.getClass().getName(),
						path 
						);

	}



	// Perform some security checks.
	if( !isAccessible(requestedFile,uri) ) {

	    throw new SecurityException( "File '" + path + "' is not accessible due to security reasons." );

	} else if( !accessGranted(uri,headers,htaccess,requestedFile,additionalSettings,optionalReturnSettings,session) ) {

	    String authType = htaccess.getAuthType();
	    if( authType == null )
		authType = "Basic";

	    // This will store some basic session data to be able to perform later auth steps such
	    // as DIGEST AUTHENTICATION (nonce, domain, ...)
	    this.mapFileReAccessRequirements( htaccess, uri, requestedFile, optionalReturnSettings, session, authType );

	    throw new AuthorizationException( AuthorizationException.AUTHORIZATION_REQUIRED, authType + " authorization required." );

	} else if( fileHandler != null ) { // extension != null && fileHandler != null ) { 

	    // Note: there might be a file handler defined by the use of htaccess/SetHandler
	   	   
										      
	    Resource resource = fileHandler.process( sessionID,
						     headers,
						     postData,
						     requestedFile,
						     uri );

	    // Resource has already a generated Content-Type header?
	    if( resource.getMetaData().getMIMEType() == null ) {
		MIMEType mimeType = MIMEType.getByFileExtension( "txt" );
		resource.getMetaData().setMIMEType( mimeType );
	    }
	    
	    

	    // ... is this all ???

	    return resource; 
	
	    
	} else if( !requestedFile.exists() ) {

	    // Re-check existence? (the file path might have changed internally).
	    throw new MissingResourceException( "File '"+path+"' not found.",
						requestedFile.getClass().getName(),
						path 
						);

	} else if( requestedFile.isDirectory() ) {


	    
	    // Let's have some fun. 
	    // Try to fetch the 'format' param from the query --- if exists :)
	    String outputFormat = this.fetchFormatFromQuery( uri, "HTML" );  // Use html as default value
	    
	    
	    Resource resource = new DefaultDirectoryResource( this.getHTTPHandler(),
							      this.getHTTPHandler().getLogger(),
							      this.getHTTPHandler().getFileFilter(),
							      requestedFile,
							      uri,
							      sessionID,
							      outputFormat,   // HTML or TXT
							      true
							      );
	    
	    /*
	     // This was a try of generating the directory listing via PHP script, but this is a bad idea
	     //  - due to security reasons (any external code might be executed!).
	     //  - due to the fact that PHP is not necesarily installed on all systems.
	    ikrs.http.filehandler.PHPDirectoryResource resource = new ikrs.http.filehandler.PHPDirectoryResource( this.getHTTPHandler(),
														  this.getHTTPHandler().getLogger(),
														  this.getHTTPHandler().getFileFilter(),
														  requestedFile,
														  uri,
														  sessionID,
														  outputFormat,   // HTML or TXT
														  true
														  );*/
	    
	    

	    resource.getMetaData().setMIMEType( MIMEType.getByFileExtension( outputFormat ) );
	    return resource;


	    //else if( extension != null && fileHandler != null ) { 
	    /*} else if( fileHandler != null ) {
		
	    // THIS IS TRICKY ...
	    // ... think about this one more time ...

	    // !!! FETCH THIS FROM A GLOBAL MAP LATER !!!
	    //ikrs.http.FileHandler fileHandler = new ikrs.http.filehandler.PHPHandler( this.getHTTPHandler(),
	    //									      this.getLogger() );
	    
	   
										      
	    Resource resource = fileHandler.process( sessionID,
						     headers,
						     postData,
						     requestedFile,
						     uri );

	    // Determine the MIME type from the generated PHP headers
	    // ... !!! ???
	    MIMEType mimeType = MIMEType.getByFileExtension( "txt" );
	    resource.getMetaData().setMIMEType( mimeType );
	    
	    

	    // ... is this all ???

	    return resource;
	    */

	} else {


	    Resource resource = new FileResource( this.getHTTPHandler(),
						  this.getHTTPHandler().getLogger(),
						  requestedFile, 
						  true   // use fair locks?
						  );

	    // Determine MIME type
	    //int index = requestedFile.getName().lastIndexOf(".");
	    //if( index != -1 && index+1 < requestedFile.getName().length() ) {
	    
	    
	    if( extension != null && extension.length() != 0 ) {


		MIMEType mimeType = MIMEType.getByFileExtension( extension );
		if( mimeType != null ) {

		    resource.getMetaData().setMIMEType( mimeType );
		    this.getHTTPHandler().getLogger().log( Level.FINEST,
							   getClass().getName() + ".locate(...)",
							   "Determined MIME type of file '"+requestedFile.getPath()+"': " + mimeType.getContentType() );

		} else {

		    resource.getMetaData().setMIMEType( new MIMEType("application/octet-stream") );
		    this.getHTTPHandler().getLogger().log( Level.WARNING,
							   getClass().getName() + ".locate(...)",
							   "Cannot determine the MIME type of file '"+requestedFile.getPath()+"' (using default MIME type application/octet-stream)." );
		
		}

	    }

	    return resource;
	}
    
    } // END [locate()]


    /**
     * This method checks if the given file is accessible by matching it by the use of the
     * global file filter.
     **/
    public boolean isAccessible( File file,
				 URI requestURI ) {

	// If a file is accessible depends on the file filter.
	/*if( fileHandler == null
	    ||
	    ( fileHandler != null && fileHandler.requiresExistingFile() && file.exists() ) )
	    return this.getHTTPHandler().getFileFilter().acceptAccess( file );
	else
	    return true;
	*/
	return !file.exists() 
	    || ( file.exists() && this.getHTTPHandler().getFileFilter().acceptAccess( file ) );
    }
				 


    /**
     * This method checks if the current user (where get the ID?) is allowed to access the file.
     *
     * @param file       The file inside the local file system. The passed file must not necessarly exist.
     * @param requestUDI The request URI itself.
     **/
    private boolean accessGranted( URI requestURI,
				   HTTPHeaders headers,
				   HypertextAccessFile htaccess,
				   File file,  
				   Map<String,BasicType> additionalSettings,
				   Map<String,BasicType> optionalReturnSettings,
				   Environment<String,BasicType> session ) 
	throws IOException,
	       AuthorizationException,
	       ConfigurationException {


	// MUST NOT be null
	/*
	boolean sessionIsAlive = CustomUtil.getBoolean( this.getHTTPHandler().getLogger(),
							session.get(Constants.SKEY_ISALIVE),
							false 
							);
	*/

	
	this.getHTTPHandler().getLogger().log( Level.INFO,
					       getClass().getName() + ".accessGranted(...)",
					       "Going to check whether the user is authorized to access '" + requestURI.getPath() + "'." );
	

	// If no .htaccess given at all
	// -> Free access for all.
	if( htaccess == null )
	    return true;

	// ... check htaccess granted
	if( !htaccessGranted(requestURI,headers,htaccess,file.getParentFile(),additionalSettings,optionalReturnSettings,session) )
	    return false;

	
	// No htaccess found -> access granted (public access)
	return true;
	
    } // END accessGranted()


    /**
     * This method checks whether the access to the passed directory/URI is granted by the
     * configured htaccess file.
     *
     * @param directory
     * @param htaccess
     * @param additionalSettings
     * @param optionalReturnSettings
     * @param session
     **/
    private boolean htaccessGranted( URI requestURI,
				     HTTPHeaders headers,
				     HypertextAccessFile htaccess,
				     File directory, 
				     Map<String,BasicType> additionalSettings,
				     Map<String,BasicType> optionalReturnSettings,
				     Environment<String,BasicType> session ) 
	throws IOException,
	       AuthorizationException,
	       ConfigurationException {

	
	// Does the .htaccess have an AuthType directive at all?
	// (there are also .htaccess file for basic configuration purposes)
	if( htaccess.getAuthType() == null )
	    return true;


	BasicType authMethod = additionalSettings.get( Constants.KEY_AUTHORIZATION_METHOD );
	if( authMethod == null ) { // || authUser == null || authPass == null ) 


	    return false; // No authorization credentials passed (at least one missing)

	}

	    
       
	try {
	    return this.hypertextAccessHandler.accessGranted( requestURI,
							      headers,
							      htaccess,
							      authMethod.getString(),   // Wrapper cannot be null here
							      additionalSettings,
							      optionalReturnSettings
							      );
	} catch( MissingParamException e ) {

	    // The client did not send all essential credentials.
	    throw new AuthorizationException( AuthorizationException.MISSING_PARAM, e.getMessage() );

	}


    }


    /*
    private HypertextAccessFile loadHypertextAccessFile( File file,
							 URI requestURI,
							 Environment<String,BasicType> session ) 
    	throws IOException,
	       ConfigurationException,
	       ParseException {

	// Check if the passed file/uri is accessible
	File uriFile = new File( requestURI.getPath() );

	while( uriFile != null && !file.equals(this.getHTTPHandler().getDocumentRoot()) ) {
	
	    // Only look a directories (the last item in the path might be a normal file)
	    if( file.isDirectory() ) {


		// Check if there is a .httaccess file
		File htaccessFile = new File( file, // is a directory!
					      ".htaccess"
					      );
		if( htaccessFile.exists() ) {

		    // We found a htaccess file! -> Load and return


		    // The use must re-authorize IF the session timed out.
		    // (if the child env already exists, the method just returns it)
		    // Environment<String,BasicType> fsAccessEnv = session.createChild( Constants.EKEY_FILESYSTEMPRIVILEGUES );
		    

		    HypertextAccessFile htaccess = HypertextAccessFile.read( htaccessFile, 
									     this.hypertextAccessHandler.isStrictMode() );
		    

		    // Found! :)
		    return htaccess;
		}

	    }

	    // The parent might not exist -> returns null
	    uriFile = uriFile.getParentFile();
	    file = file.getParentFile();
	}

	// No htaccess file found.
	return null;

    }
    */

    
    private HypertextAccessFile loadHypertextAccessFile( File file,
							 URI requestURI,
							 Environment<String,BasicType> session ) 
    	throws IOException,
	       ConfigurationException,
	       ParseException {

	// Check if the passed file/uri is accessible
	File uriFile = new File( requestURI.getPath() );

	
	return loadHypertextAccessFile( file, uriFile, session );
    }

    private HypertextAccessFile loadHypertextAccessFile( File fsFile,
							 File uriFile,
							 Environment<String,BasicType> session ) 
    	throws IOException,
	       ConfigurationException,
	       ParseException {

	
	// Document root reached? (end of recursion)
	//if( uriFile == null || fsFile.equals(this.getHTTPHandler().getDocumentRoot().getParent()) ) 
	if( uriFile == null || fsFile == null || !this.getHTTPHandler().isInsideDocumentRoot(fsFile) )
	    return null;
	

	HypertextAccessFile parentHTA = loadHypertextAccessFile( fsFile.getParentFile(),
								 uriFile.getParentFile(),
								 session 
								 );

	// Only look at directories (the last item in the path might be a normal file)
	if( fsFile.isDirectory() ) {


	    // Check if there is a .httaccess file
	    File htaccessFile = new File( fsFile, // is a directory!
					  ".htaccess"
					  );
	    if( htaccessFile.exists() ) { // && htaccessFile.isFile() ) {

		// We found a htaccess file! -> Load and return


		// The user must re-authorize IF the session timed out.
		// (if the child env already exists, the method just returns it)
		// Environment<String,BasicType> fsAccessEnv = session.createChild( Constants.EKEY_FILESYSTEMPRIVILEGUES );
		    

		HypertextAccessFile localHTA = HypertextAccessFile.read( htaccessFile, 
									 this.hypertextAccessHandler.isStrictMode() );

		// Override settings inside the parent htaccess file?
		if( parentHTA != null )
		    parentHTA.merge( localHTA );
		else
		    parentHTA = localHTA;
		    
	    }

	}

	// The parent might not exist -> returns null
	//uriFile = uriFile.getParentFile();
	//file = file.getParentFile();
	
	return parentHTA;

    }

    /**
     * If the htaccess driven authentication fails the HTTP request distributor might require some configuration
     * details to send a fully qualified error reply.
     *
     * This method just maps the most importan htaccess settings from the file into the return-map. Both, not the 
     * htaccess nor the return-map must be null.
     *
     * @param htaccess               The hypertext access file to read from.
     * @param optionalReturnSettings The map to write to.
     **/
    private void mapHypertextAccessRequirements( HypertextAccessFile htaccess,
						 File requestedFile,
						 // Map<String,BasicType> additionalSettings,
						 Map<String,BasicType> optionalReturnSettings ) {

	
	// Extension might be null or ""
	String fileExtension = CustomUtil.getFileExtension( requestedFile );


	String authType = htaccess.getAuthType();

	// Not each .htaccess file contains an AuthType!
	if( authType != null ) {

	    // Store for later use and/or error retrieval 
	    // (reminder: WWW-Authenticate requires some htaccess settings)
	    optionalReturnSettings.put( Constants.KEY_HTACCESS_AUTHTYPE, new BasicStringType(authType) );

	}

	if( htaccess.getAuthName() != null )
	    optionalReturnSettings.put( Constants.KEY_HTACCESS_AUTHNAME, new BasicStringType(htaccess.getAuthName()) );



	// Check whether the htaccess has a custom MIME type for this file type defined
	if( fileExtension != null && fileExtension.length() != 0 ) {

	    //System.out.println( "AddedTypes=" + htaccess.getAddedTypes() );
	    String mimeType = htaccess.getAddedTypes().get(fileExtension);
	    
	    if( mimeType != null )
		optionalReturnSettings.put( Constants.KEY_HTACCESS_ADDEDTYPE, new BasicStringType(mimeType) );

	}



	// Determine the charset to use.		
	// Check if there is a mapping for the extension
	if( fileExtension == null ) {

	    // Unknown file type -> DefaultCharset defined?
	    if( htaccess.getDefaultCharset() != null )
		optionalReturnSettings.put( Constants.KEY_HTACCESS_CHARSET, new BasicStringType(htaccess.getDefaultCharset()) );
	    else
		; // NOOP (none available)

	} else {		
		    
	    String definedCharset = htaccess.getAddedCharsets().get( fileExtension );
	    // Any charset defined for the file type? Or use default charset?
	    if( definedCharset != null )
		optionalReturnSettings.put( Constants.KEY_HTACCESS_CHARSET, new BasicStringType(definedCharset) );
	    else if( htaccess.getDefaultCharset() != null )
		optionalReturnSettings.put( Constants.KEY_HTACCESS_CHARSET, new BasicStringType(htaccess.getDefaultCharset()) );
	    else
		; // NOOP (none available)

	}



	// Map error codes?
	if( optionalReturnSettings != null ) {
	    Iterator<Map.Entry<Integer,String>> errorDocIter = htaccess.getErrorDocumentMap().entrySet().iterator();
	    while( errorDocIter.hasNext() ) {
		
		Map.Entry<Integer,String> entry = errorDocIter.next();
		String errorDocKey = Constants.AKEY_HTACCESS_ERROR_DOCUMENT_BASE.replaceAll( "\\{STATUS_CODE\\}",
											     entry.getKey().toString() );
		optionalReturnSettings.put( errorDocKey,
					    new BasicStringType( entry.getValue() ) 
					);
		    
	    }
	}
    }



    private void mapFileReAccessRequirements( HypertextAccessFile htaccess,
					      URI requestedURI,
					      File requestedFile,
					      Map<String,BasicType> optionalReturnSettings,
					      Environment<String,BasicType> session,

					      String authType ) {


	// Finally: store some session data for the case the client tries to access the file again later?
	if( authType.equalsIgnoreCase("Basic") ) {

	    // NOOP (Basic Authentication does not require any additional data to be stored)

	} else if( authType.equalsIgnoreCase("Digest") ) {

	    // The nonce should be generated using a secret salt value.
	    // Unfortunately we do not know the username at this point of authentication, so cannot lookup the
	    // salt from the AuthUserFile.
	    String nonce_salt  = 
		Long.toString( System.currentTimeMillis() ) + ":" +
		requestedURI.getPath() + ":" + 
		authType + ":" + 
		// session.getSessionID() +
		Integer.toString( (int)(Math.random()*Integer.MAX_VALUE) );
	    String nonce = null;

	    try {
		byte[] nonce_bytes = MD5.md5( nonce_salt.getBytes() );
		nonce              = CustomUtil.bytes2hexString( nonce_bytes );
		this.getHTTPHandler().getLogger().log( Level.INFO,
						       getClass().getName() + ".accessGranted(...)",
						       "Using nounce salt value: " + nonce_salt + ", generated nonce=" + nonce );
	    } catch( java.security.NoSuchAlgorithmException e ) {
		String tmpTS = Long.toString( System.currentTimeMillis() );
		nonce = CustomUtil.bytes2hexString( tmpTS.getBytes() );
		this.getHTTPHandler().getLogger().log( Level.WARNING,
						       getClass().getName() + ".accessGranted(...)",
						       "Failed to encode nonce_salt value; using a timestamp based nonce instead. nonce=" + nonce );
	    }
		
	    String domain      = requestedURI.getPath();
	    String algorithm   = "MD5";         // Fetch from htaccess?!


	    // These settins will be sent to the client
	    optionalReturnSettings.put( Constants.KEY_AUTHENTICATION_NONCE,     new BasicStringType( nonce ) );   
	    optionalReturnSettings.put( Constants.KEY_AUTHENTICATION_DOMAIN,    new BasicStringType( domain ) );
	    optionalReturnSettings.put( Constants.KEY_AUTHENTICATION_ALGORITHM, new BasicStringType( algorithm ) ); 

	    // These settings will be internally stored for later verification
	    session.put( Constants.KEY_AUTHENTICATION_NONCE,     new BasicStringType( nonce ) );  
	    session.put( Constants.KEY_AUTHENTICATION_DOMAIN,    new BasicStringType( domain) );
	    session.put( Constants.KEY_AUTHENTICATION_ALGORITHM, new BasicStringType( algorithm ) );  


	} else {

	    // Unknown auth type. Nothing to store (unexpected case)

	}

    }

    /**
     * This method searches for the DirectoryIndex file in the given directory.
     *
     * If there are no index files specified in the htaccess OR no such index file
     * can be found, the method just returns the passed directory file itself.
     *
     **/
    private File htaccess_resolveDirectoryIndex( File requestedDir,
						 HypertextAccessFile htaccess ) {

	// This works only with directories.
	if( !requestedDir.isDirectory() )
	    return requestedDir;
	

	
	List<String> directoryIndexList = htaccess.getDirectoryIndexList();
	if( directoryIndexList.size() == 0 )
	    return requestedDir;
	
	

	File indexFile;
	for( int i = 0; i < directoryIndexList.size(); i++ ) {

	    indexFile = new File( requestedDir, directoryIndexList.get(i) );
	    if( indexFile.exists() ) {
	
		this.getHTTPHandler().getLogger().log( Level.INFO,
						       getClass().getName() + ".resolveDirectoryIndex(...)",
						       "DirectoryIndex file found: '" + indexFile.getPath() + "'." );
		return indexFile;

	    }

	}

	// None of the index files found.
	// Proceed with the directory file.
	this.getHTTPHandler().getLogger().log( Level.INFO,
					       getClass().getName() + ".resolveDirectoryIndex(...)",
					       "No DirectoryIndex file found! Proceeding with requested directory file." );
	return requestedDir;
    }


    private boolean htaccess_checkDirectoryListingAllowed( File requestedFile, 
							   HypertextAccessFile htaccess ) {

	if( htaccess == null )
	    return this.getHTTPHandler().isDirectoryListingAllowed();


	Boolean option = htaccess.getOptions().get("Indexes");

	// If no option is set at all, use the global setting.
	if( option == null )
	    return this.getHTTPHandler().isDirectoryListingAllowed();
	
	// Otherwise return the configured value.
	return option.booleanValue();
    }


    private FileHandler htaccess_resolveFileHandler( URI requestURI,
						     File requestedFile,
						     String fileExtension,
						     HypertextAccessFile htaccess ) 
	throws ConfigurationException {

	// Use this order:
	//  (i)    The 'AddHandler' settings (extension-specific; overrides directory-wide 'SetHandler' directive).
	//  (ii)   The 'SetHandler' setting (overrides the global default handler)

	if( htaccess != null ) {

	    this.getHTTPHandler().getLogger().log( Level.INFO,
						   getClass().getName() + ".resolveFileHandler(...)",
						   "Looking up fileExtension='"+ fileExtension + "' in htaccess file (checking for 'AddHandler' directive)." );

	    if( fileExtension != null ) {
	    	    
		Map<String,List<String>> htaccess_addedHandlers = htaccess.getAddedHandlers();
		Iterator<Map.Entry<String,List<String>>> iter = htaccess_addedHandlers.entrySet().iterator();
		while( iter.hasNext() ) {

		    Map.Entry<String,List<String>> entry = iter.next();
		    String handlerName                   = entry.getKey();
		    List<String> handlerExtensions       = entry.getValue();

		    // Check if the file's extension is inside the current handler's extension list.
		    for( int i = 0; i < handlerExtensions.size(); i++ ) {

			// Note from the apache specs: 
			//   "The extension argument is case-insensitive and can be specified with or without a leading dot"
			String handlerExt                    = handlerExtensions.get(i);
			boolean equalFileExtensions          = CustomUtil.equalFileExtensions( fileExtension,
											       handlerExt,
											       false,      // not case sensitive
											       false       // not dot sensitive
											       );
			
			if( equalFileExtensions ) {
			    
			    // The extensions are equal. 
			    // Resolve associated handler from the global config.
			    FileHandler fileHandler = this.getHTTPHandler().getFileHandlerByName( handlerName );

			    if( fileHandler == null ) {

				this.getHTTPHandler().getLogger().log( Level.SEVERE,
								       getClass().getName() + ".resolveFileHandler(...)",
								       "The htaccess file contains an 'AddHandler' directive for the file extension '" + fileExtension + "' but the retrieved file handler name '"+ handlerName +"' was not found in the global configuration! requestedURI=" + requestURI.getPath() );
				throw new ConfigurationException( "A requested file handler was not found [0].", null );

			    } else {

				return fileHandler;

			    }
			}
		    }

		}

	    } // END if [fileExtension != null]
	    
	    
	    // Obviously no FileHandler was found by the use of 'AddHandler' here.	    
	    // Continue with 'SetHandler'. 
	    String handlerName = htaccess.getSetHandler();
	    if( handlerName != null ) {
		
		FileHandler fileHandler = this.getHTTPHandler().getFileHandlerByName( handlerName );

		if( fileHandler == null ) {

		    this.getHTTPHandler().getLogger().log( Level.SEVERE,
							   getClass().getName() + ".resolveFileHandler(...)",
							   "The htaccess file contains an 'SetHandler' directive for the file extension '" + fileExtension + "' but the retrieved file handler name '" + handlerName + "' was not found in the global configuration! requestedURI=" + requestURI.getPath() );
		    throw new ConfigurationException( "A requested file handler was not found [0].", null );

		} else {

		    return fileHandler;

		}

	    }
	} // END if [htaccess != null]

	// System.out.println( "File extension: " + fileExtension );

	// It seems there is no 'AddHandler' and no 'SetHandler' directive in the htaccess file.
	// Use default global setting (if available).
	return this.getHTTPHandler().getFileHandlerByExtension( fileExtension );
    }

    private String fetchFormatFromQuery( URI requestedURI,
					 String defaultFormat ) {

	if( requestedURI.getQuery() == null )
	    return defaultFormat;
	
	String encoding = java.nio.charset.StandardCharsets.UTF_8.name(); // "UTF-8";
	String key      = "format";
	try {

	    ikrs.httpd.datatype.Query query = new ikrs.httpd.datatype.Query( requestedURI.getQuery(),
									   encoding,   // Query encoding
									   false       // Case sensitive?
									   );
	    String format                  = query.getParam( key );  // Param lookup is not case sensitive
	    if( format == null || format.length() == 0 )
		return defaultFormat;
	    else
		return format.toUpperCase();
	
	} catch( java.io.UnsupportedEncodingException e ) {

	    // Ooops. This method is just for fun. Don't raise any errors (but log the error).
	    this.getLogger().log( Level.INFO,
				  getClass().getName() + ".fetchFormatFromQuery(...)",
				  "UnsupportedEncodingException when trying to fetch '" + key + "' param from query '" + requestedURI.getQuery() + "': " + e.getMessage() + ". Continue with default format setting."  );
	    return defaultFormat;

	}
    }

}


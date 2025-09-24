package ikrs.httpd.filehandler;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import java.util.logging.Level;

import ikrs.httpd.AbstractFileHandler;
import ikrs.httpd.Constants;
import ikrs.httpd.DataFormatException;
import ikrs.httpd.DefaultPostDataWrapper;
import ikrs.httpd.FileHandler;
import ikrs.httpd.HeaderFormatException;
import ikrs.httpd.HTTPHandler;
import ikrs.httpd.HTTPHeaderLine;
import ikrs.httpd.HTTPHeaders;
import ikrs.httpd.PostDataWrapper;
import ikrs.httpd.Resource;
import ikrs.httpd.UnsupportedFormatException;
import ikrs.httpd.resource.InterruptableResource;
import ikrs.httpd.resource.ProcessableResource;
import ikrs.httpd.datatype.FormData;
import ikrs.io.BytePositionInputStream;
import ikrs.util.CaseInsensitiveComparator;
import ikrs.util.CustomLogger;
import ikrs.util.Environment;
import ikrs.util.KeyValueStringPair;
import ikrs.util.session.Session;
import ikrs.typesystem.BasicType;

/**
 * The CGI handler is an abstract class implementing some basic methods for 
 * the Common Gateway Interface.<br/>
 * <br/>
 * Subclasses must implement these methods:<br/>
 *  - List<String> buildCGISystemCommand( ... )<br/>
 *  - void buildAdditionalCGIEnvironmentVars( ... )<br/>
 *  - Resource handleCGIOutput( ... )<br/>
 *
 *
 *
 * @author Ikaros Kappler
 * @date 2012-10-12
 * @version 1.0.0
 **/


public abstract class CGIHandler
    extends AbstractFileHandler {

    

    public static final String CGI_ENV_AUTH_TYPE              = "AUTH_TYPE";
    public static final String CGI_ENV_CONTENT_LENGTH         = "CONTENT_LENGTH";
    public static final String CGI_ENV_CONTENT_TYPE           = "CONTENT_TYPE";
    public static final String CGI_ENV_DOCUMENT_ROOT          = "DOCUMENT_ROOT";
    public static final String CGI_ENV_GATEWAY_INTERFACE      = "GATEWAY_INTERFACE";
    public static final String CGI_ENV_PATH_INFO              = "PATH_INFO";
    public static final String CGI_ENV_PATH_TRANSLATED        = "PATH_TRANSLATED";
    public static final String CGI_ENV_QUERY_STRING           = "QUERY_STRING";
    public static final String CGI_ENV_REMOTE_ADDR            = "REMOTE_ADDR";
    public static final String CGI_ENV_REMOTE_HOST            = "REMOTE_HOST";
    public static final String CGI_ENV_REMOTE_IDENT           = "REMOTE_IDENT";
    public static final String CGI_ENV_REMOTE_USER            = "REMOTE_USER";
    public static final String CGI_ENV_REQUEST_METHOD         = "REQUEST_METHOD";
    public static final String CGI_ENV_REQUEST_URI            = "REQUEST_URI";
    public static final String CGI_ENV_SCRIPT_FILENAME        = "SCRIPT_FILENAME";
    public static final String CGI_ENV_SCRIPT_NAME            = "SCRIPT_NAME";
    public static final String CGI_ENV_SERVER_NAME            = "SERVER_NAME";
    public static final String CGI_ENV_SERVER_PORT            = "SERVER_PORT";
    public static final String CGI_ENV_SERVER_PROTOCOL        = "SERVER_PROTOCOL";
    public static final String CGI_ENV_SERVER_SOFTWARE        = "SERVER_SOFTWARE";

    public static final String CGI_ENV_HTTP_                  = "HTTP_";



    private Set<String> defaultIncludeHeadersSet;


    public CGIHandler() 
	throws NullPointerException {

	super();

	this.defaultIncludeHeadersSet = new TreeSet<String>( CaseInsensitiveComparator.sharedInstance );

	// Init the HTTP header set that should be included into the CGI environment by default.
	this.defaultIncludeHeadersSet.add( HTTPHeaders.NAME_ACCEPT );
	this.defaultIncludeHeadersSet.add( HTTPHeaders.NAME_ACCEPT_CHARSET );
	this.defaultIncludeHeadersSet.add( HTTPHeaders.NAME_ACCEPT_ENCODING );
	this.defaultIncludeHeadersSet.add( HTTPHeaders.NAME_ACCEPT_LANGUAGE );
	this.defaultIncludeHeadersSet.add( HTTPHeaders.NAME_CONNECTION );
	this.defaultIncludeHeadersSet.add( HTTPHeaders.NAME_COOKIE );
	this.defaultIncludeHeadersSet.add( HTTPHeaders.NAME_HOST );
	this.defaultIncludeHeadersSet.add( HTTPHeaders.NAME_REFERER );
	this.defaultIncludeHeadersSet.add( HTTPHeaders.NAME_USER_AGENT );
	
    }

    /**
     * There is a default set of HTTP headers that should be included into the CGI environment. Due to
     * security reasons not all headers should be included!
     *
     * This set contains the headers that are allowed; all others will not be mapped.
     * By default this set contains:
     *  - Accept
     *  - Accept-Charset
     *  - Accept-Encoding
     *  - Accept-Language
     *  - Connection
     *  - Cookie
     *  - Host
     *  - Referer
     *  - User-Agent
     *
     * It's up to your own risk to modify this list. Some application might not be working if some of
     * these essential headers are missing.
     *
     * @return The HTTP-include-into-CGI set with HTTP headers that should be mapped into the CGI environment.
     *         The returned set is never null.
     **/
    protected Set<String> getDefaultIncludeHeadersSet() {
	/*
	Set<String> tmp = this.getHTTPHandler().getCGIMapHeadersSet();
	if( tmp == null )
	    return this.defaultIncludeHeadersSet;
	else
	    return this.getHTTPHandler().getCGIMapHeadersSet();  
	*/
	
	return this.defaultIncludeHeadersSet;
    }
    

    private boolean mapHeaderToCGIEnvironment( String headerName ) {
	Boolean b = this.getHTTPHandler().mapHeaderToCGIEnvironment( headerName );  // may return null!
	
	// If null, no configured header section was found
	if( b == null )
	    return this.defaultIncludeHeadersSet.contains( headerName );
	else
	    return b.booleanValue();
    }

    //--- BEGIN --------- These methods must be implemented by subclasses --------------------------
    /**
     * Subclasses implementing this method must return a valid system command that can be executed
     * directly using Java's ProcessBuilder.
     *
     * The first list element must be the command name itself, all following elements are the command
     * line arguments.
     *
     *
     * @param headers    The current request's HTTP headers.
     * @param postData   The current request's post data (a data wrapper holding the input stream).
     * @param file       The requested file (in the local file system).
     * @param requestURI The request's URI (from headers.getRequestURI()).
     *
     * @return A list representing the system command.
     **/
    public abstract List<String> buildCGISystemCommand( HTTPHeaders headers,
							PostDataWrapper postData,
							File file, 
							URI requestURI );


    /**
     * Subclasses implementing the method may define additional/optional CGI environment settings.
     * Note: there is no need to define the standard CGI environment as it is already contained
     *       in the handler's default mapping.
     *
     *       The default vars are:
     *          - AUTH_TYPE
     *          - CONTENT_LENGTH
     *          - CONTENT_TYPE
     *          - GATEWAY_INTERFACE
     *          - HTTP_*
     *          - PATH_INFO
     *          - PATH_TRANSLATED
     *          - QUERY_STRING
     *          - REMOTE_ADDR
     *          - REMOTE_HOST
     *          - REMOTE_IDENT
     *          - REMOTE_USER
     *          - REQUEST_METHOD
     *          - SCRIPT_NAME
     *          - SERVER_NAME
     *          - SERVER_PORT
     *          - SERVER_PROTOCOL
     *          - SERVER_SOFTWARE
     *
     * See CGI specs or http://graphcomp.com/info/specs/cgi11.html for details.
     *
     *
     * If the handler requires to overwrite pre-defined environment vars the method may change/remove
     * the value in the given mapping. Handle with care.
     *
     * If the implementing handler has no additional environment vars the method may just do nothing.
     *
     * @param headers     The current request's HTTP headers.
     * @param file        The requested file (in the local file system).
     * @param requestURI  The request's URI (from headers.getRequestURI()).
     * @param environment The current environment settings and the target map.
     *
     **/
    public abstract void buildAdditionalCGIEnvironmentVars( HTTPHeaders headers,
							    File file,
							    URI requestURI,
							    
							    Map<String,String> environment );


    /**
     * After the CGI handler performed the system command the resulting resource must be handled.
     * The way the CGI output is handled differs from handler to handler as the underlying ran
     * command produces different types of output.
     *
     * So it's up the the handler to process the generated data.
     *
     * @param headers         The current request's HTTP headers.
     * @param file            The requested file (in the local file system).
     * @param requestURI      The request's URI (from headers.getRequestURI()).
     * @param cgiOutput       The actual CGI output; use cgiOutput.getEcitValue() to determine the
     *                        return code of the CGI program.
     * @param postDataWrapper The sent post data (a wrapper object containing the input stream).
     *
     * @return After the output was processed the returned resource should contain (optional)
     *         header replacements and returned script data.
     **/
    public abstract Resource handleCGIOutput( HTTPHeaders headers,
					      File file,
					      URI requestURI,
					      PostDataWrapper postData,
					      
					      ProcessableResource cgiOutput )
	throws IOException;					  
    //--- END ----------- These methods must be implemented by subclasses --------------------------


    //--- BEGIN ------------------------ FileHandler implementation ------------------------------
    /**
     * Most file handlers operate on existing files that are located inside the local file 
     * system (such as the default handler does for simple file delivery).
     *
     * Some file handlers operate on virtual file systems, which means that the request URI does
     * not necessarily address an existing file but a symbol only the handler may know. 
     *
     * The global HTTP handler needs to know if to throw a MissingResourceException (resulting
     * in a 404) if a requested file does not exists --- or if to ignore that fact and simply 
     * continue.
     *
     * This method tells how to proceed.
     *
     * If your implementation returns true this handler will not be called at all; the request
     * processing will directly stop raising an HTTP status 404.
     *
     * @return true if this file handler definitely requires existing files. The process(...) 
     *              method will never be called if the requested file does not exist in that case.
     **/
    public boolean requiresExistingFile() {
	
	// The CGI script definitely has to exist
	return true;
    }

     /**
     * The 'process' method is very generic. It depends on the underlying implementation how the passed
     * file should be processed.
     *
     * @param sessionID   The current session's ID.
     * @param headers     The HTTP request headers.
     * @param postData    The HTTP post data; if the method is not HTTP POST the 'postData' should be null
     *                    (or empty).
     * @param file        The requested file itself (inside the local file system).
     * @param requestURI  The requested URI (relative to DOCUMENT_ROOT).
     **/
    public Resource process( UUID sessionID,
			     HTTPHeaders headers,
			     PostDataWrapper postData,
			     File file,
			     URI requestURI )
	throws IOException,
	       HeaderFormatException,
	       DataFormatException,
               UnsupportedFormatException {
	    

	// Fetch the system command (specified and constructed in the sub-class).
	List<String> command =  this.buildCGISystemCommand( headers, postData, file, requestURI );

	try {
	    getLogger().log( Level.INFO,
			     getClass().getName() + ".process(...)",
			     "Processing. requestURI=" + requestURI + ". file=" + file.getAbsolutePath() );	
	   
	    ProcessBuilder pb = new ProcessBuilder( command );
	
	    this.buildCGIEnvironment( pb, headers, file, requestURI, sessionID );

	    Map<String,String> environment = pb.environment();
	    this.buildAdditionalCGIEnvironmentVars( headers, file, requestURI, environment );



	    this.getLogger().log( Level.INFO,
				  getClass().getName() + ".process(...)",
				  "Creating a processable resource using the CGI file '" + file.getPath() + "'. System command: " + command.toString() );
	
	    ProcessableResource cgiOutput = 
		new ProcessableResource( getHTTPHandler(),
					 getLogger(),
					 pb,
					 postData,  // Should be null if HTTP method is not POST
					 false      // useFairLocks not necessary here; there will be one more resource wrapper
					 );

	    // The system command was NOT executed yet!
	    /*
	    if( cgiOutput.getExitValue() != 0 ) {

		this.getLogger().log( Level.WARNING,
				      getClass().getName() + ".process(...)",
				      "The execution of the processable resource using the CGI file '" + file.getPath() + "' with the system command: " + command.toString() + " failed (exit code " + cgiOutput.getExitValue() + "). Continue though." );

	    }
	    */

	    return this.handleCGIOutput( headers,
					 file,
					 requestURI,
					 postData,
				     
					 cgiOutput );

	} catch( IOException e ) {

	    // This class is an interface between the software and the system it is running on.
	    // If the system is misconfigured here might occur some IO errors.
	    // Better log a SEVERE error here.
	    this.getLogger().log( Level.SEVERE,
				  getClass().getName() + ".process(...)",
				  "Failed to execute system command: " + command.toString() );

	    throw e;
	}
		
    }

    //--- END -------------------------- FileHandler implementation ------------------------------


    private void buildCGIEnvironment( ProcessBuilder pb,
				      HTTPHeaders headers,
				      File file,
				      URI requestURI,
				      UUID sessionID ) {


	// Bind CGI environment settings
	// Note: some settings come from the current session.
	Environment<String,BasicType> session         = this.getHTTPHandler().getSessionManager().get( sessionID );
	Environment<String,BasicType> internalSession = session.getChild( Constants.SESSION_NAME_INTERNAL );
	
	// Fetch the 'Host' header field.
	// Format: host [ ":" port ]
	KeyValueStringPair host_port_pair = KeyValueStringPair.split( headers.getStringValue( HTTPHeaders.NAME_HOST ),
								      false,  // don't tryo to remove quotes
								      ":"     // The separator
								      );


	BasicType wrp_remoteAddr    = internalSession.get( Constants.SKEY_REMOTE_ADDRESS );
	BasicType wrp_remoteHost    = internalSession.get( Constants.SKEY_REMOTE_HOST );
	BasicType wrp_remoteIdent   = internalSession.get( Constants.SKEY_REMOTE_IDENT );
	BasicType wrp_remoteUser    = internalSession.get( Constants.SKEY_REMOTE_USER );
	


	this.bindCGIEnvironmentVar( headers, requestURI, pb, CGIHandler.CGI_ENV_AUTH_TYPE,         null );
	this.bindCGIEnvironmentVar( headers, requestURI, pb, CGIHandler.CGI_ENV_CONTENT_LENGTH,    headers.getStringValue(HTTPHeaders.NAME_CONTENT_LENGTH) );
	this.bindCGIEnvironmentVar( headers, requestURI, pb, CGIHandler.CGI_ENV_CONTENT_TYPE,      headers.getStringValue(HTTPHeaders.NAME_CONTENT_TYPE) );
	this.bindCGIEnvironmentVar( headers, requestURI, pb, CGIHandler.CGI_ENV_GATEWAY_INTERFACE, "CGI/1.1" );


	this.bindCGIEnvironmentVar( headers, requestURI, pb, CGIHandler.CGI_ENV_DOCUMENT_ROOT,     this.getHTTPHandler().getDocumentRoot().getAbsolutePath() );


	this.bindCGIEnvironmentVar( headers, requestURI, pb, CGIHandler.CGI_ENV_PATH_INFO,         requestURI.getPath() ); 
	this.bindCGIEnvironmentVar( headers, requestURI, pb, CGIHandler.CGI_ENV_PATH_TRANSLATED,   requestURI.getPath() );  // ??!


	this.bindCGIEnvironmentVar( headers, requestURI, pb, CGIHandler.CGI_ENV_QUERY_STRING,      requestURI.getRawQuery() ); // url-encoded!
	if( wrp_remoteAddr != null )
	    this.bindCGIEnvironmentVar( headers, requestURI, pb, CGIHandler.CGI_ENV_REMOTE_ADDR,       wrp_remoteAddr.getString() );

	if( wrp_remoteHost != null )
	    this.bindCGIEnvironmentVar( headers, requestURI, pb, CGIHandler.CGI_ENV_REMOTE_HOST,       wrp_remoteHost.getString() );

	if( wrp_remoteIdent != null )
	    this.bindCGIEnvironmentVar( headers, requestURI, pb, CGIHandler.CGI_ENV_REMOTE_IDENT,      wrp_remoteIdent.getString() );

	if( wrp_remoteUser != null )
	    this.bindCGIEnvironmentVar( headers, requestURI, pb, CGIHandler.CGI_ENV_REMOTE_USER,       wrp_remoteUser.getString() );


	this.bindCGIEnvironmentVar( headers, requestURI, pb, CGIHandler.CGI_ENV_REQUEST_METHOD,    headers.getRequestMethod() );

	String str_requestURI = requestURI.getPath();
	if( requestURI.getQuery() != null )
	    str_requestURI += ("?" + requestURI.getQuery());
	this.bindCGIEnvironmentVar( headers, requestURI, pb, CGIHandler.CGI_ENV_REQUEST_URI,       str_requestURI );

	this.bindCGIEnvironmentVar( headers, requestURI, pb, CGIHandler.CGI_ENV_SCRIPT_FILENAME,   file.getAbsolutePath() );
	this.bindCGIEnvironmentVar( headers, requestURI, pb, CGIHandler.CGI_ENV_SCRIPT_NAME,       requestURI.getPath() );


	if( host_port_pair != null && host_port_pair.getKey() != null )
	    this.bindCGIEnvironmentVar( headers, requestURI, pb, CGIHandler.CGI_ENV_SERVER_NAME,       host_port_pair.getKey() );

	if( host_port_pair != null && host_port_pair.getValue() != null )
	    this.bindCGIEnvironmentVar( headers, requestURI, pb, CGIHandler.CGI_ENV_SERVER_PORT,       host_port_pair.getValue() );

	this.bindCGIEnvironmentVar( headers, requestURI, pb, CGIHandler.CGI_ENV_SERVER_PROTOCOL,   headers.getRequestProtocol() + "/" + headers.getRequestVersion() ); // "HTTP/1.1" );	
	this.bindCGIEnvironmentVar( headers, requestURI, pb, CGIHandler.CGI_ENV_SERVER_SOFTWARE,   this.getHTTPHandler().getSoftwareName() );
	

	// Add some additional header fields beginning with 'CGI_ENV_HTTP_' ...
	// Start at i=1: exclude request line
	for( int i = 1; i < headers.size(); i++ ) {

	    HTTPHeaderLine hl = headers.get(i);
	    String key        = hl.getKey();
	    String value      = hl.getValue();

	    // Not key nor value must be null
	    if( key == null || value == null )
		continue;

	    // The header must be explicitly allowed to be included into the CGI environment! (security reason)
	    //if( !this.getIncludeHeadersSet.contains(key) )
	    if( !this.mapHeaderToCGIEnvironment(key) )
		continue;

	    key               = key.toUpperCase();
	    key               = key.replaceAll( "-", "_" );
	    key               = CGIHandler.CGI_ENV_HTTP_ + key;
	    
	    this.bindCGIEnvironmentVar( headers, 
					requestURI, 
					pb, 
					key, 
					value );

	}



    }

    private void bindCGIEnvironmentVar( HTTPHeaders headers,
					URI requestURI,
					ProcessBuilder pb,
					String key,
					String value ) {

	if( value == null )
	    value = "";

	
	this.getLogger().log( Level.INFO,
			      getClass().getName() + ".bindCGIEnvironmentVar(...)",
			      "[requestedPath=" + requestURI.getPath() + "] Mapping '" + key +"' into CGI environment: " + value );
	pb.environment().put( key, value );

    }


}

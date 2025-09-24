package ikrs.httpd;

/**
 * @author Ikaros Kappler
 * @date 2012-07-16
 * @version 1.0.0
 **/

import java.io.IOException;
import java.net.Socket;
import java.util.MissingResourceException;
import java.util.UUID;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;

import ikrs.httpd.resource.ByteArrayResource;
import ikrs.httpd.response.GeneralPreparedResponse;
import ikrs.httpd.response.successful.OK;

import ikrs.typesystem.*;


public class DefaultResponseBuilder
    extends AbstractResponseBuilder
    implements ResponseBuilder {

    
    /**
     * For the case the request cannot be processed it will be forwarded to the internal
     * error response builder.
     **/
    private ErrorResponseBuilder errorResponseBuilder;


    /**
     * The constructor.
     **/
    public DefaultResponseBuilder( HTTPHandler handler ) {
	super( handler );	

	this.errorResponseBuilder = new ErrorResponseBuilder(handler);
    }

    /**
     * The default response builder uses a nested response builder for error processing.
     * In some special cases it can be helpful to access the ErrorResponseBuilder directy (such as
     * the HTTPHandler does if the threaded execution is rejected by the ThreadPoolExecutor).
     *
     * This method simply returns the internal error response builder.
     *
     * @return The internal error response builder.
     **/
    protected ErrorResponseBuilder getErrorResponseBuilder() {
	return this.errorResponseBuilder;
    }


    //---BEGIN------------------------------ ResponseBuilder implementaion -----------------------
    /**
     * This method translates the given headers and socket into an executable 
     * PreparedResponse object.
     *
     * The method does not throw any exceptions as the error reporting is part of HTTP
     * itself.
     *
     * @param headers  The previously processed headers.
     * @param postData The actual sent non-header data (if available; may be null).
     * @param socketID The unique socket ID.
     * @param socket   The acutual socket.
     * @param additionals A map containing non-essential builder params. The expected 
     *                    map contents depends on the underlying implementation; some
     *                    builders even allow null-additionals.
     *
     * @return A new HTTPRequest built from the HTTP headers.
     *
     **/
    public PreparedHTTPResponse create( HTTPHeaders headers,
					PostDataWrapper postData,
					UUID socketID,
					Socket socket,
					UUID sessionID,     // ConnectionUserID userID,
					Map<String,BasicType> additionalSettings
					) {
	

	String command      = headers.getRequestMethod();
	String protocol     = headers.getRequestProtocol();
	String version      = headers.getRequestVersion();
	String uri          = headers.getRequestURI();

	HTTPHeaderLine host = headers.get( HTTPHeaders.NAME_HOST );
	    


	Map<String,BasicType> optionalReturnSettings = new TreeMap<String,BasicType>();
	try {

	    // Validate header PROTOCOL
	    if( protocol == null || !protocol.equals(Constants.HTTP) )
		throw new HeaderFormatException( "The server only accepts " + Constants.HTTP + " requests. Protocol '" + protocol + "' is not allowed." );


	    // Validate header VERSION
	    if( version == null || (!version.equals("1.0") && !version.equals("1.1")) )
		throw new UnsupportedVersionException( "The HTTP version '" + version + "' is not supported. Use version 1.0 or 1.1 instead." );


	    // See RFC 2616, page 171:
	    //   "Servers MUST report a 400 (Bad Request) error if an HTTP/1.1
	    //    request does not include a Host request-header."
	    if( version.equals("1.1") && host == null )
		throw new MalformedRequestException( "HTTP version 1.1 requires the '" + HTTPHeaders.NAME_HOST + "' header to be present." );


	    PreparedHTTPResponse response = new OK( this.getHTTPHandler(),
						    headers,
						    postData,
						    socketID,
						    socket,
						    sessionID );

	    // This is a critical step that might raise lots of different errors.
	    // It is IMPORTANT that this method is called INSIDE the builder!
	    // (errors should be handled INSIDE the HTTP protocol)
	    response.prepare( optionalReturnSettings );

	    
	    return response;

	} catch( UnknownMethodException e ) {
	    
	    this.getHTTPHandler().getLogger().log( Level.INFO,
						   getClass().getName() + ".create(...)",
						   "UnknownMethodException: " + e.getMessage()
						   );  

	    // Return an apropriate error response	    
	    return buildPreparedErrorResponse( headers, postData, socketID, socket, sessionID,
					       e,
					       Constants.HTTP_STATUS_CLIENTERROR_METHOD_NOT_ALLOWED,
					       "Method Not Allowed",
					       e.getMessage(),
					       additionalSettings,
					       optionalReturnSettings
					       );	    
	    

	} catch( ConfigurationException e ) {
	    
	    this.getHTTPHandler().getLogger().log( Level.INFO,
						   getClass().getName() + ".create(...)",
						   "ConfigurationException: " + e.getMessage()
						   );

	    // Return an apropriate error response
	    return buildPreparedErrorResponse( headers, postData, socketID, socket, sessionID,
					       e,
					       Constants.HTTP_STATUS_SERVERERROR_INTERNAL_SERVER_ERROR,
					       "Internal Server Error",
					       e.getMessage(),
					       additionalSettings,
					       optionalReturnSettings
					       );

	} catch( UnsupportedVersionException e ) {
	    
	    this.getHTTPHandler().getLogger().log( Level.INFO,
						   getClass().getName() + ".create(...)",
						   "UnsupportedVersionException: " + e.getMessage()
						   ); 
 
	    // Return an apropriate error response	    
	    return buildPreparedErrorResponse( headers, postData, socketID, socket, sessionID,
					       e,
					       Constants.HTTP_STATUS_SERVERERROR_HTTP_VERSION_NOT_SUPPORTED,
					       "HTTP Version Not Supported",
					       e.getMessage(),
					       additionalSettings,
					       optionalReturnSettings
					       );
	} catch( UnsupportedMethodException e ) {
	    
	    this.getHTTPHandler().getLogger().log( Level.INFO,
						   getClass().getName() + ".create(...)",
						   "UnsupportedMethodException: " + e.getMessage()
						   ); 
 
	    // Return an apropriate error response	    
	    return buildPreparedErrorResponse( headers, postData, socketID, socket, sessionID,
					       e,
					       Constants.HTTP_STATUS_CLIENTERROR_METHOD_NOT_ALLOWED,
					       "Method Not Allowed",
					       e.getMessage(),
					       additionalSettings,
					       optionalReturnSettings
					       );

	} catch( AuthorizationException e ) { 

	    this.getHTTPHandler().getLogger().log( Level.INFO,
						   getClass().getName() + ".create(...)",
						   "AuthorizationException: " + e.getMessage()
						   ); 
 
	    // Return an apropriate error response	    
	    return buildPreparedErrorResponse( headers, postData, socketID, socket, sessionID,
					       e,
					       Constants.HTTP_STATUS_CLIENTERROR_UNAUTHORIZED,
					       "Authorization required",
					       e.getMessage(),
					       additionalSettings,
					       optionalReturnSettings
					       );
	} catch( HeaderFormatException e ) { 

	    this.getHTTPHandler().getLogger().log( Level.INFO,
						   getClass().getName() + ".create(...)",
						   "HeaderFormatException: " + e.getMessage()
						   ); 
 
	    // Return an apropriate error response	    
	    return buildPreparedErrorResponse( headers, postData, socketID, socket, sessionID,
					       e,
					       Constants.HTTP_STATUS_CLIENTERROR_BAD_REQUEST,
					       "Bad Request",
					       "Bad header(s): " + e.getMessage(),
					       additionalSettings,
					       optionalReturnSettings
					       );  

	} catch( DataFormatException e ) { 

	    this.getHTTPHandler().getLogger().log( Level.INFO,
						   getClass().getName() + ".create(...)",
						   "DataFormatException: " + e.getMessage()
						   ); 
 
	    // Return an apropriate error response	    
	    return buildPreparedErrorResponse( headers, postData, socketID, socket, sessionID,
					       e,
					       Constants.HTTP_STATUS_CLIENTERROR_BAD_REQUEST,
					       "Bad Request",
					       "Bad data sent: " + e.getMessage(),
					       additionalSettings,
					       optionalReturnSettings
					       ); 

	} catch( UnsupportedFormatException e ) { 

	    this.getHTTPHandler().getLogger().log( Level.INFO,
						   getClass().getName() + ".create(...)",
						   "UnsupportedFormatException: " + e.getMessage()
						   ); 
 
	    // Return an apropriate error response	    
	    return buildPreparedErrorResponse( headers, postData, socketID, socket, sessionID,
					       e,
					       Constants.HTTP_STATUS_CLIENTERROR_UNSUPPORTED_MEDIA_TYPE,
					       "Unsupported Media Type",
					       "Unsupported media type: " + e.getMessage(),
					       additionalSettings,
					       optionalReturnSettings
					       );   

	} catch( ParametrizedHTTPException e ) { 

	    this.getHTTPHandler().getLogger().log( Level.INFO,
						   getClass().getName() + ".create(...)",
						   "ParametrizedHTTPException: " + e.getMessage()
						   ); 
 
	    // Return an apropriate error response	    
	    return buildPreparedErrorResponse( headers, postData, socketID, socket, sessionID,
					       e,
					       e.getStatusCode(),
					       e.getReasonPhrase(),
					       e.getReasonPhrase() + ": " + e.getMessage(),
					       additionalSettings,
					       optionalReturnSettings
					       );  

	} catch( SecurityException e ) {
	    
	    this.getHTTPHandler().getLogger().log( Level.INFO,
						   getClass().getName() + ".create(...)",
						   "MalformedRequestException: " + e.getMessage()
						   ); 

	    // Return an apropriate error response	    
	    return buildPreparedErrorResponse( headers, postData, socketID, socket, sessionID,
					       e,
					       Constants.HTTP_STATUS_CLIENTERROR_FORBIDDEN,
					       "Forbidden",
					       e.getMessage(),
					       additionalSettings,
					       optionalReturnSettings
					       );

	} catch( MalformedRequestException e ) {
	    
	    this.getHTTPHandler().getLogger().log( Level.INFO,
						   getClass().getName() + ".create(...)",
						   "MalformedRequestException: " + e.getMessage()
						   );  
	    
	    // Return an apropriate error response	    
	    return buildPreparedErrorResponse( headers, postData, socketID, socket, sessionID,
					       e,
					       Constants.HTTP_STATUS_CLIENTERROR_BAD_REQUEST,
					       "Bad Request",
					       e.getMessage(),
					       additionalSettings,
					       optionalReturnSettings
					       );

	} catch( MissingResourceException e ) {
	    
	    this.getHTTPHandler().getLogger().log( Level.INFO,
						   getClass().getName() + ".create(...)",
						   "MissingResourceException: " + e.getMessage()
						   );  
	    
	    
	    // Return an apropriate error response	    
	    return buildPreparedErrorResponse( headers, postData, socketID, socket, sessionID,
					       e,
					       Constants.HTTP_STATUS_CLIENTERROR_NOT_FOUND,
					       "Not Found",
					       e.getMessage(),
					       additionalSettings,
					       optionalReturnSettings
					       );	

	} catch( IOException e ) {
	    
	    this.getHTTPHandler().getLogger().log( Level.WARNING,
						   getClass().getName() + ".create(...)",
						   "IOException: " + e.getMessage()
						   ); 

	    
	    // Return an apropriate error response	    
	    return buildPreparedErrorResponse( headers, postData, socketID, socket, sessionID,
					       e,
					       Constants.HTTP_STATUS_SERVERERROR_INTERNAL_SERVER_ERROR, 
					       "Interal Server Error",
					       e.getMessage(),
					       additionalSettings,
					       optionalReturnSettings
					       );	
	}

    }
    //---END-------------------------------- ReplyBuilder implementaion -----------------------


    protected PreparedHTTPResponse buildPreparedErrorResponse( HTTPHeaders headers,
							       PostDataWrapper postData,
							       UUID socketID,
							       Socket socket,
							       UUID sessionID,  
							       Exception e,
							       int statusCode,
							       String reasonPhrase,
							       String errorMessage,
							       
							       Map<String,BasicType> additionalSettings,
							       Map<String,BasicType> optionalReturnSettings
							     ) {

	this.getHTTPHandler().getLogger().log( Level.INFO,
					       getClass().getName() +  ".buildPreparedErrorResponse(...)",
					       "additionalSettings="+additionalSettings+", optionalReturnSettings=" + optionalReturnSettings );

	// Join all error fields into the additional settings :)
	Map<String,BasicType> newAdditionals = new TreeMap<String,BasicType>();
	if( additionalSettings != null )
	    newAdditionals.putAll( additionalSettings );
	if( optionalReturnSettings != null )
	    newAdditionals.putAll( optionalReturnSettings );

	// Map<String,BasicType> additionals = new TreeMap<String,BasicType>();  // Huh?
	newAdditionals.put( "statusCode",    new BasicNumberType(statusCode) );
	newAdditionals.put( "reasonPhrase",  new BasicStringType(reasonPhrase) );
	newAdditionals.put( "errorMessage",  new BasicStringType(errorMessage) );


	return this.errorResponseBuilder.create( headers,
						 postData,
						 socketID,
						 socket,
						 sessionID, 
						 newAdditionals 
						 );
    }

}

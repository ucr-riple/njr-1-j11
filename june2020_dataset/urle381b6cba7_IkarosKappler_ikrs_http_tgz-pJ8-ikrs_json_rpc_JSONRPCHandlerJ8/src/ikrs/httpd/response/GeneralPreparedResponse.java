package ikrs.httpd.response;

import java.io.IOException;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.UUID;
import java.util.logging.Level;

import ikrs.httpd.AbstractPreparedResponse;
import ikrs.httpd.AuthorizationException;
import ikrs.httpd.ConfigurationException;
import ikrs.httpd.Constants;
import ikrs.httpd.DataFormatException;
import ikrs.httpd.HeaderFormatException;
import ikrs.httpd.HTTPHandler;
import ikrs.httpd.HTTPHeaders;
import ikrs.httpd.MalformedRequestException;
import ikrs.httpd.ParametrizedHTTPException;
import ikrs.httpd.Resource;
import ikrs.httpd.UnsupportedFormatException;
import ikrs.httpd.UnsupportedMethodException;
import ikrs.httpd.UnsupportedVersionException;
import ikrs.httpd.UnknownMethodException;
import ikrs.httpd.resource.ByteArrayResource;


import ikrs.typesystem.BasicType;
import ikrs.typesystem.BasicTypeException;

/**
 * This class holds general prepared responses.
 * The response requires:
 *   - STATUS_CODE
 *   
 * Optional is the RESOURCE.
 *
 * @author Ikaros Kappler
 * @date 2012-07-29
 * @version 1.0.0
 **/


public class GeneralPreparedResponse
    extends AbstractPreparedResponse {


    private int statusCode;

    /**
     * Create a new general prepared response.
     *
     * @param handler    The top level HTTP handler.
     * @param headers    The request (!) headers.
     * @param socketID   The (server) socket ID.
     * @param socket     The connection's socket.
     * @param statusCode The response's status code.
     * @param reasonPhrase The reason phrase (must not contain line breaks!).
     *
     * @throws IllegalArgumentException If the reaons phrase contains line breaks.
     **/
    public GeneralPreparedResponse( HTTPHandler handler,
				    HTTPHeaders headers,
				    UUID socketID,
				    Socket socket,
				    UUID sessionID, 

				    int statusCode,
				    String reasonPhrase
				    ) 
	throws IllegalArgumentException {

	super( handler, headers, socketID, socket, sessionID, statusCode, reasonPhrase );
    }
    


    //---BEGIN------------------------- AbstractPreparedResponse implementation ------------------------------
    /**
     * This method must be implemented by all subclasses. It must prepare the HTTP response.
     * This means that all required ressources must be acquired (use locks), all headers prepared (by the use
     * of addResponseHeader(String,String) or getResponseHeaders()) and perform all necessary security checks.
     *
     * Subclasses implementing this method should call the setPrepared() method when ready.
     *
     * @param optionalReturnSettings This (optional, means may be null) map can be used to retrieve internal values
     *                               for error recovery.
     *
     * @throws MalformRequestException If the passed HTTP request headers are malformed and cannot be processed.
     * @throws UnsupportedVersionException If the headers' HTTP version is not supported (supported versions are
     *                                     1.0 and 1.1).
     * @throws UnsupportedMethodException If the request method is valid but not supported (status code 405).
     * @throws UnknownMethodException If the headers' method (from the request line) is unknown.
     * @throws ConfigurationException If the was a server configuration issue the server cannot work properly with.
     * @throws MissingResourceException If the requested resource cannot be found.
     * @throws AuthorizationException If the requested resource requires authorization.
     * @throws HeaderFormatException If the passed headers are malformed.
     * @throws DataFormatException If the passed data is malformed.
     * @throws SecurityException If the request cannot be processed due to security reasons.
     * @throws IOException If any IO errors occur.
     **/
    public void prepare( Map<String,BasicType> optionalReturnSettings ) 
	throws MalformedRequestException,
	       UnsupportedVersionException,
	       UnsupportedMethodException,
	       UnknownMethodException,
	       ConfigurationException,
	       MissingResourceException,
	       AuthorizationException,
	       HeaderFormatException,
	       DataFormatException,
	       UnsupportedFormatException,
	       ParametrizedHTTPException,
	       SecurityException,
	       IOException {


	// Simply build a plain response

	if( this.getResponseDataResource() != null ) {

	    this.getResponseDataResource().getReadLock().lock();
	    this.getResponseDataResource().open( true ); // Open in read-only mode 

	}
	    
	
	super.addResponseHeader( "Server",            this.getHTTPHandler().getSoftwareName() );

	if( this.getResponseDataResource() != null ) {
	    super.addResponseHeader( "Content-Length",    Long.toString(this.getResponseDataResource().getLength()) ); 
	    super.addResponseHeader( "Content-Language",  "en" );
	    super.addResponseHeader( "Content-Type",      this.getResponseDataResource().getMetaData().getMIMEType().getContentType() );
	} 

	super.addResponseHeader( "Connection",        "close" );
	
	
    }


    /**
     * This method will be called in the final end - even if the execute() method failed.
     *
     * Subclasses implementing this method should call the setDisposed() method when done.
     *
     *
     * It has to clean up, release resources and all locks!
     **/
    public void dispose() {

	if( this.getResponseDataResource() == null )
	    return; // There is nothing to release



	try {
	    
	    // Close the resource.
	    this.getResponseDataResource().close();

	} catch( IOException e ) {

	    this.getHTTPHandler().getLogger().log( Level.WARNING,
						   getClass().getName() + ".dispose()",
						   "Faile to close resource: " + e.getMessage() );

	} finally {

	    // Release the read-lock
	    this.getResponseDataResource().getReadLock().unlock();

	}

	

    }
    //---END--------------------------- AbstractPreparedResponse implementation ------------------------------


}

package ikrs.httpd;

import java.net.Socket;
import java.nio.charset.Charset;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.UUID;
import java.util.logging.Level;


import ikrs.httpd.AuthorizationException;
import ikrs.httpd.HeaderFormatException;
import ikrs.httpd.DataFormatException;
import ikrs.typesystem.BasicType;

/**
 * This interface is meant to wrap prepared HTTP reply objects.
 *
 * @author Ikaros Kappler
 * @date 2012-07-16
 * @version 1.0.0
 **/


public abstract class AbstractPreparedResponse
    implements PreparedHTTPResponse {

    
    /**
     * The top level HTTP handler.
     **/
    private HTTPHandler httpHandler;

    /**
     * The response's HTTP request (!) headers.
     **/
    private HTTPHeaders requestHeaders;

    /**
     * The response's socketID (server side).
     **/
    private UUID socketID;

    /**
     * The response's actual connection socket.
     **/
    private Socket socket;

    /**
     * The connection's/user's sessionID.
     **/
    private UUID sessionID;

    /**
     * This is an _internal_ field to buffer the actual response headers.
     **/
    private HTTPHeaders responseHeaders;

    
    private Resource responseDataResource;


    private boolean isPrepared;
    private boolean isExecuted;
    private boolean isDisposed;

    //private int statusCode;
    private String statusCode;
    private String reasonPhrase;


    /**
     * Create a new prepared response.
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
    public AbstractPreparedResponse( HTTPHandler handler,
				     HTTPHeaders requestHeaders,
				     UUID socketID,
				     Socket socket,
				     UUID sessionID,

				     int statusCode,
				     String reasonPhrase ) 
	throws IllegalArgumentException {
	
	super();

	this.httpHandler = handler;
	this.requestHeaders = requestHeaders;
	this.socketID = socketID;
	this.socket = socket;
	this.sessionID = sessionID;

	this.responseHeaders = new HTTPHeaders();


	setStatusCode( Integer.toString(statusCode) );
	setReasonPhrase( reasonPhrase );
    }

    /**
     * Get this response's HTTP handler.
     **/
    protected HTTPHandler getHTTPHandler() {
	return this.httpHandler;
    }

    /**
     * Get this respone's HTTP request headers.
     **/
    protected HTTPHeaders getRequestHeaders() {
	return this.requestHeaders;
    }

    /**
     * Get the server side socket ID.
     **/
    protected UUID getSocketID() {
	return this.socketID;
    }

    /**
     * Get the actual connection socket.
     **/
    protected Socket getSocket() {
	return this.socket;
    }

    /**
     * Get the connection's session ID
     **/
    protected UUID getSessionID() {
	return this.sessionID;
    }

    /**
     * These headers are initially empty and need to be filled with the response header data.
     **/
    public HTTPHeaders getResponseHeaders() {
	return this.responseHeaders;
    }

    /**
     * Add a new line (key-value-pair) to the response headers.
     **/
    public void addResponseHeader( String key, String value ) {
	this.responseHeaders.add( key, value );
    }


    public void setResponseDataResource( Resource resource ) {
	this.responseDataResource = resource;
    }

    public Resource getResponseDataResource() {
	return this.responseDataResource;
    }


    protected void setPrepared() {
	this.isPrepared = true;
    }

    /**
     * Instance of this class execute themselves :)
     * It's not necessary to make this method public/protected.
     **/
    private void setExecuted() {
	this.isExecuted = true;
    }

    protected void setDisposed() {
	this.isDisposed = true;
    }


    /**
     * Get the status code of this prepared response.
     **/
    public String getStatusCode() {
	return this.statusCode;
    }

    /**
     * Set the status code to a new value.
     **/
    protected void setStatusCode( String statusCode ) {
	this.statusCode = statusCode;
    }

    /**
     * Get the currently set reason phrase.
     **/
    public String getReasonPhrase() {
	return this.reasonPhrase;
    }

    /**
     * Set the reason phrase to a new value.
     **/
    protected void setReasonPhrase( String phrase ) 
	throws IllegalArgumentException {

	if( phrase != null && phrase.indexOf("\n") != -1 )
	    throw new IllegalArgumentException( "Reason phrase must not contain line breaks." );

	this.reasonPhrase = phrase;
    }


    //---BEGIN------------------------------ PreparedResponse implementation -----------------------------
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
    public abstract void prepare( Map<String,BasicType> optionalReturnSettings ) 
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
	       IOException;
      
    /**
     * This method executes the prepared reply; this means that all necessary resources will be accessed,
     * the actual reply built and sent back to the client.
     *
     *
     *
     * @throws IOException If any IO errors occur.
     **/
    public final void execute()
	throws IOException {

	setExecuted();

	// Check if the response headers exist
	if( this.getResponseHeaders().size() == 0 ) {

	    this.httpHandler.getLogger().log( Level.SEVERE,
					      getClass().getName()+".execute()",
					      "Cannot send HTTP response: prepared headers are empty." );
	    throw new IOException( "Cannot send HTTP response: prepared headers are empty." );

	}

	
	OutputStream out = this.getSocket().getOutputStream();

	Charset charset = java.nio.charset.StandardCharsets.UTF_8; // Charset.forName(java.nio.charset.StandardCharsets.UTF_8.name());


	// Do the generated headers have a status line set?
	if( this.getResponseHeaders().size() == 0 
	    || !this.getResponseHeaders().get(0).isResponseStatus() ) {

	    // Send auto-generated status line
	    // (The STATUS CODE and REASON PHRASE must be set here!)
	    // ... and don't forget to use the same HTTP version as in the request.
	    String responseHTTPVersion = this.getResponseHeaders().getRequestVersion();
	    if( responseHTTPVersion == null )
		responseHTTPVersion = this.getRequestHeaders().getRequestVersion();
	    if( responseHTTPVersion == null )
		responseHTTPVersion = Constants.SUPPORTED_HTTP_VERSION;

	    HTTPHeaderLine statusLine = new HTTPHeaderLine( "HTTP/" + responseHTTPVersion + " "+this.getStatusCode()+" " + this.getReasonPhrase(), null );
	    this.getHTTPHandler().getLogger().log( Level.INFO,
						   getClass().getName() + ".execute()",
						   "Sending hard coded status line (none in the headers present): " + statusLine.toString() );
	    byte[] b = statusLine.getRawBytes( charset );	    
	    // Write bytes to output stream
	    out.write( b );

	} 


	
	// First: write headers
	Iterator<HTTPHeaderLine> iter = this.getResponseHeaders().iterator();
	// There is at least one line
	byte[] b;
	do {

	    HTTPHeaderLine line = iter.next();
	    this.getHTTPHandler().getLogger().log( Level.INFO,
						   getClass().getName() + ".execute()",
						   "Sending: " + line.toString() );

	    b = line.getRawBytes( charset );	    
	    // Write header line bytes to output stream
	    out.write( b );
	    

	} while( iter.hasNext() );
	
	// Send an empty line that implies 'end-of-headers'
	//out.write( (byte)'\n' );
	out.write( (char)Constants.CR );
	out.write( (char)Constants.LF );

	// Flush the header data
	out.flush();


	// Send data? (resource MUST be locked!)
	try {
	    
	    // Resource must already be opened!
	    if( this.responseDataResource != null && this.responseDataResource.getLength() > 0 ) {
		
		// Hard coded switch for debugging output
		boolean ALSO_PRINT_ON_STDOUT = false;

		InputStream resourceIn = this.responseDataResource.getInputStream();
		
		this.getHTTPHandler().getLogger().log( Level.INFO,
						       getClass().getName() + ".execute()",
						       "Sending data ... [also printing a hex dump of the first 10KB to stdout]" );
		
		byte[] buffer = new byte[ 1024 ];
		int len = -1;
		// Print the data on stdout?
		ikrs.util.HexDumpOutputStream hexOut = this.getHTTPHandler().createHexDumpOutputStream();
		long maxHexOutput = 1024*10;  // 10 KB
		// Read the resource's data chunk by chunk
		long totalLength = 0L;
		while( (len = resourceIn.read(buffer)) > 0 ) {

		    // Print the data on stdout?
		    if( ALSO_PRINT_ON_STDOUT ) {
			if( totalLength < maxHexOutput ) {
			    for( int i = 0; i < len; i++ )
				hexOut.write( buffer[i] );
			    hexOut.flush();
			}
		    }
		    
		    // And write each chunck into the socket's output stream
		    out.write( buffer, 0, len );

		    totalLength += len;		    
		}
		
		// And flush data
		out.flush();
		
	    }


	    // It cannot be said if the underlying implementations correctly read all incoming
	    // data (POST and PUT method). Be sure everything was consumed.
	    if( this.getRequestHeaders().getRequestMethod() != null 
		&& ( this.getRequestHeaders().getRequestMethod().equals("POST") 
		     || this.getRequestHeaders().getRequestMethod().equals("PUT") ) ) {
		
		this.httpHandler.getLogger().log( Level.INFO,
						  getClass().getName()+".execute()",
						  "The incoming request used HTTP method '" + this.getRequestHeaders().getRequestMethod() + "'; trying to comsume excessing data ..." );
		long length = this.consumeIncomingData();
		if( length > 0 ) {
		    this.httpHandler.getLogger().log( Level.SEVERE,
						  getClass().getName()+".execute()",
						  "I consumed " + length + " additional bytes that were not read by the underlying handler!" );
		} else {
		    this.httpHandler.getLogger().log( Level.FINE,
						  getClass().getName()+".execute()",
						  "No additional bytes were read." );
		}
		// Done.

	    }

	} catch( IOException e ) {

	    // Catch this exception to be sure the socket was closed
	    this.httpHandler.getLogger().log( Level.SEVERE,
					      getClass().getName()+".execute()",
					      "Cannot send HTTP response (data). The passed resource threw an IOException: " + e.getMessage() );

	} finally {

	    // Is this correct here?
	    // The socket itself was passed though the constructor, so the calling instance should also
	    // close the socket (and all its streams).
	    // out.close();

	}


	// Data sent.
    }


    /**
     * This method will be called in the final end - even if the execute() method failed.
     *
     * Subclasses implementing this method should call the setDisposed() method when done.
     *
     *
     * It has to clean up, release resources and all locks!
     **/
    public abstract void dispose();



    /**
     * This method return true if (and only if) this response is already prepared.
     * In the true-case the prepare()-method should not have any effects.
     **/
    public boolean isPrepared() {
	return this.isPrepared;
    }


    /**
     * The method returns true if (and only if) this response was already executed.
     **/
    public boolean isExecuted() {
	return this.isExecuted;
    }


    /**
     * The method returns true if (and only if) this response already disposed.
     **/
    public boolean isDisposed() {
	return this.isDisposed;
    }
    //---END-------------------------------- PreparedResponse implementation -----------------------------



    private long consumeIncomingData() 
	throws IOException {

	InputStream in = this.getSocket().getInputStream();
	int length;
	long total_length = 0;
	byte[] buffer = new byte[256];  // Not too large here
	while( in.available() > 0 
	       && (length = in.read(buffer)) > 0 ) {

	    // Print to stdout?
	    for( int i = 0; i < length; i++ )
		System.out.print( (char)buffer[i] );
  
	    total_length += length; // NOOP (just read)
	}

	if( total_length > 0L )
	    System.out.println( "" );
	    
	return total_length;
    }

}

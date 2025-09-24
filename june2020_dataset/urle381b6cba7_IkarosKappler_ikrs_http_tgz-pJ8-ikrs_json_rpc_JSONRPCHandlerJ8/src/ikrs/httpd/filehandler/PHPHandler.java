package ikrs.httpd.filehandler;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import ikrs.httpd.AbstractFileHandler;
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
import ikrs.util.CustomLogger;
import ikrs.util.MIMEType;

/**
 * This is a PHP file handler. It passes the requested file to the PHP (php-cgi) interpreter
 * and stores the generated data inside a buffered resource.
 *
 * Future implementations might send the generated data in runtime (without a buffer), but
 * that would require a HTTP handler that does not expect the given data length when the
 * network output starts.
 *
 *
 * @author Ikaros Kappler
 * @date 2012-09-29
 * @version 1.0.0
 **/


public class PHPHandler
    extends CGIHandler {



    public PHPHandler() 
	throws NullPointerException {

	super();

	
    }

 
     /**
     * Create a new PHPHandler.
     * 
     * @param logger A logger to write log messages to (must not be null).
     **/
    /*public PHPHandler( HTTPHandler handler, CustomLogger logger ) 
	throws NullPointerException {

	super( handler, logger );

	
	}*/



    //--- BEGIN ----------------------- CGIHandler implementation ----------------------------------
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
    public List<String> buildCGISystemCommand( HTTPHeaders headers,
					       PostDataWrapper postData,
					       File file,
					       URI requestURI ) {
	List<String> command = new LinkedList<String>();

	// WARNING: php-cgi must be installed on the system
	command.add( "php-cgi" );
	// command.add( "-n" );  // Param '-n' means: no php.ini will be used
	command.add( file.getAbsolutePath() );  // This is the file argument for the PHP interpreter :)

	return command;
    }


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
    public void buildAdditionalCGIEnvironmentVars( HTTPHeaders headers,
						   File file,
						   URI requestURI,
						   
						   Map<String,String> environment ) {

	// This is an UNDOCUMENTED field (respective not part of the 'official' CGI specs).
	// It is required to override php-cgi's security rules.
	// In the php-cgi/php.ini there must be the 'cgi.force_redirect = 0' is declared!
	environment.put( "REDIRECT_STATUS", "1" );

    }


    /**
     * After the CGI handler performed the system command the resulting resource must be handled.
     * The way the CGI output is handled differs from handler to handler as the underlying ran
     * command produces different types of output.
     *
     * So it's up the the handler to process the generated data.
     *
     * @param headers     The current request's HTTP headers.
     * @param file        The requested file (in the local file system).
     * @param requestURI  The request's URI (from headers.getRequestURI()).
     * @param cgiOutput   The actual CGI output; use cgiOutput.getEcitValue() to determine the
     *                    return code of the CGI program.
     * @param postDataWrapper The sent post data (a wrapper object containing the input stream).
     *
     * @return After the output was processed the returned resource should contain (optional)
     *         header replacements and returned script data.
     **/
    public Resource handleCGIOutput( HTTPHeaders headers,
				     File file,
				     URI requestURI,
				     PostDataWrapper postData,
					      
				     ProcessableResource cgiOutput )
	throws IOException {


	// The processable resource stores the system-process's output inside an internal buffer.
	// Now we can read the PHP's generated header data using an InterruptableResource.
	InterruptableResource ir = new InterruptableResource( this.getHTTPHandler(),
							      this.getLogger(),
							      cgiOutput,
							      true    // useFairLocks (this will be the returned instance)
							      );
	

	this.getLogger().log( Level.INFO,
			      getClass().getName() + ".handleCGIOutput(...)",
			      "CGI output received. Reading generated HTTP headers from InterruptableResource ..." );

	// Note that the InterruptableResource allows to simulate the inputstream to be closed,
	// which is nothing more than a byte position reset.
	ir.open( true ); // open in read-only mode


	// Warning: even if the process was executed without any exceptions the system process may have failed!
	// -> check the return code
	if( cgiOutput.getExitValue() == 0 ) {
	    
	    // ???
	    // Store exit code in the resource's meta data?

	} else {

	    /*
	    if( cgiOutput.getExitValue() != 0 ) {
	    */
	    
	    this.getLogger().log( Level.WARNING,
				  getClass().getName() + ".handleCGIOutput(...)",
				  "The execution of the processable resource using the CGI file '" + file.getPath() + "' failed (exit code " + cgiOutput.getExitValue() + "). Continue though." );
	    
	}
	    


	// Continue ...
	BytePositionInputStream in = ir.getInputStream();
	HTTPHeaders phpHeaders = HTTPHeaders.read( in );


	if( phpHeaders.size() == 0 ) {

	    this.getLogger().log( Level.INFO,
				  getClass().getName() + ".handleCGIOutput(...)",
				  "The InterruptableResource has no HTTP headers." );
	}



	try {

	    // Store the generated HTTP headers into the resource's MetaData object.
	    for( int i = 0; i < phpHeaders.size(); i++ ) {


		// PHP creates a special 'Status' line indictating the HTTP respones status.
		// This status has the format "<status_code> <reason_phrase>" (hopefully) and must be converted into the 
		// HTTP-conform header line "HTTP/1.x <status_code> <reason_phrase>".
		HTTPHeaderLine headerLine = phpHeaders.get(i);
		if( headerLine.getKey() != null && headerLine.getKey().equals("Status") ) {

		    // Convert the 'Status' line into the 'HTTP/1.1 <status> <reason_phrase>'
		    // Note: This might throw a HeaderFormatException!
		    HTTPHeaderLine newResponseLine = new HTTPHeaderLine( "HTTP/" + headers.getRequestVersion() + " " + headerLine.getValue(), 
									 null 
									 );
		    this.getLogger().log( Level.INFO,
					  getClass().getName() + ".handleCGIOutput(...)",
					  "Converting PHP-generated HTTPHeaders["+i+"] to a new status line and adding to the resource's meta data: " + phpHeaders.get(i) + ", status line replacement: " + newResponseLine );

		    ir.getMetaData().getOverrideHeaders().replaceResponseLine( newResponseLine );

		} else {

		    // A 'normal' key-value-tuple.
		    this.getLogger().log( Level.INFO,
					  getClass().getName() + ".handleCGIOutput(...)",
					  "Adding PHP-generated HTTPHeaders["+i+"] to the resource's meta data: " + phpHeaders.get(i) );

		    ir.getMetaData().getOverrideHeaders().add( headerLine );


		    // Override resource's MIME type?
		    if( headerLine.getKey() != null && headerLine.getKey().equals(HTTPHeaders.NAME_CONTENT_TYPE) ) {

			ir.getMetaData().setMIMEType( new MIMEType(headerLine.getValue()) );

		    }
		    
		}
	    }

	} catch( HeaderFormatException e ) {

	    ir.close();
	    throw new IOException( "Cannot replace response status line due to HeaderFormatException: " + e.getMessage(),
				   e );
		

	} 

	
	// Now reset the input stream!
	// This will tell the next instance accessing the resource that it's still at the beginning of the 
	// input stream (and the second 'open()' call will not fail).
	ir.resetBytePosition();
	
	    
	return ir;
    }
    //--- END ------------------------- CGIHandler implementation ----------------------------------





}

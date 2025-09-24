/**
 * This is an example handler demonstrating how to use the JSON-RPC
 * package for building up a HTTP-JSON-RPC handler for the server.
 *
 * See the HTML/javascript document at /tests_and_examples/json_rpc/index.html
 * in the document root directory.
 *
 *
 * @author Ikaros Kappler
 * @date 2013-06-20
 * @version 1.0.0
 **/

import java.io.*;
import java.net.URI;
import java.util.UUID;
import java.util.logging.Level;

import ikrs.httpd.*;
import ikrs.httpd.resource.*;
import ikrs.io.BytePositionInputStream;
import ikrs.util.*;
import ikrs.util.session.Session;
import ikrs.typesystem.BasicType;

import ikrs.json.rpc.JSONRPCHandler;
import ikrs.json.rpc.JSONRPCResponse;
import ikrs.json.rpc.RPCInvocationTarget;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;



public class JSONRPCExampleHandler
    extends AbstractFileHandler
    implements RPCInvocationTarget {


    /**
     * The constructor without arguments (!) MUST exist to allow the system
     * to instantiate the class by the use of the Class.newInstance() method.
     **/
    public JSONRPCExampleHandler() {
	super();

	
    }

    
    /**
     * This is a test method which can be called via JSON-RPC later.
     *
     * Note that the method's signature (name and parameter types) will
     * be used to resolve the passed JSON-RPC request's method name.
     **/
    public String doAnything( Integer i, String s, Boolean b ) {
	System.out.println( "DOING ANYTHING" );
	System.out.println( " integer param: " + i );
	System.out.println( " string param:  " + s );
	System.out.println( " boolean param: " + b );
	
	return "This is the method result: a string. System time when called: " + System.currentTimeMillis() + "ms. The integer param was " + i + ", the boolean param was " + b + ", and the string param was: " + s;
    }


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

	// Requested files do not necessarily have to exist :)
	return false;
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
	    

	getLogger().log( Level.INFO,
			 getClass().getName() + ".process(...)",
			 "Processing. requestURI=" + requestURI + ". file=" + file.getAbsolutePath() );

	
	
	// Print POST data on stdout?
	/*
	byte[] tmpBuf = new byte[ 256 ];
	int tmpLen;
	System.out.println( "BEGIN: POST data" );
	while( (tmpLen = postData.getInputStream().read(tmpBuf)) != -1 ) {
	    for( int i = 0; i < tmpLen; i++ )
		System.out.print( (char)tmpBuf[i] );
	}
	System.out.println( "\nEND: POST data" );
	*/

	
	// The JSON-RPC handler only works with POST data
	if( postData == null )  
	    throw new DataFormatException( "You MUST send your JSON-RPC request via HTTP POST!" );
	    
       

	// Prepare an output buffer.
	StringBuffer buffer    = new StringBuffer();
	
	// Process the request.
	this.processJSONRPCRequest( postData, buffer );
	
	// Convert the buffered data to a resource.
	String data = buffer.toString();
	buffer.delete( 0, buffer.length() ); // Clear buffer
	ByteArrayResource resource = new ByteArrayResource( this.getHTTPHandler(),
							    this.getLogger(),
							    data.getBytes(),
							    false   // no need to use fair locks
							    );
	resource.getMetaData().setMIMEType( new MIMEType( "application/json" ) );
	resource.getMetaData().setCharsetName( "UTF-8" );
		
	return resource;
    }
    //--- END -------------------------- FileHandler implementation ------------------------------


    //--- BEGIN ------------------------ RPCInvocationTarget -------------------------------------
    /**
     * This is a default implementation that allows ONLY this method
     * itself and all public methods to be invocated.
     *
     * @param method The method which shall be checked if invocation
     *               is allowed.
     * @return true if the method is allowed to be called, false 
     *              otherwise.
     **/
    public boolean checkMethodInvocation( Method method ) {

	if( method == null )
	    return false;    // result not specified

	return method.getName().equals("doAnything");
    }
    //--- END -------------------------- RPCInvocationTarget -------------------------------------

    /**
     * This private method processes the actual JSON-RPC request.
     *
     * The retrieved output will be written into the passed string buffer (will
     * be a JSON string, or to be more accurate: it will be the JSON-RPC response
     * value).
     **/
    private void processJSONRPCRequest( PostDataWrapper postData,
					StringBuffer buffer ) {


	try {

	    System.out.println( "Initializing the JSONRPC request ..." );	    
	    
	    System.out.println( "Creating JSONRPC ..." );
	    JSONRPCHandler rpc = new JSONRPCHandler();
	    rpc.addInvocationTarget( "this", 
				     this, 
				     true    // Add 'this' as a default invocation target
				     );


	    System.out.println( "Executing the request ..." );
	    JSONRPCResponse response = rpc.call( new InputStreamReader(postData.getInputStream()) );
	    System.out.println( "Response: " + response.toJSONString() );

	    buffer.append( response.toJSONString() );
	    
	    System.out.println( "Done." );
	
	} catch( Exception e ) {
	    e.printStackTrace();
	    buffer.append( e.toString() );
	}


    }

     
}

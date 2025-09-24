package ikrs.json.rpc;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import ikrs.json.JSONArray;
import ikrs.json.JSONException;
import ikrs.json.JSONNumber;
import ikrs.json.JSONObject;
import ikrs.json.JSONString;
import ikrs.json.JSONValue;
import ikrs.json.parser.*;


/**
 * This is the RPC handler which translates JSONRPC requests into method calls.
 *
 * The handler holds a variable set of anonymous objects; each must implement
 * the RPCInvocationTarget interface.
 *
 *
 * @author Ikaros Kappler
 * @date 2013-06-03
 * @modified 2013-07-16 Ikaros Kappler (JSONObject and JSONArray param type support added).
 * @modified 2013-06-16 Ikaros Kappler (unwrapJSONValues flag added).
 * @version 1.0.2
 **/



public class JSONRPCHandler {
    
    /**
     * Each RPC method is part of an invocation target.
     * Invocations on anonymous targets (no object name given) require
     * a default target to be defined (may be null).
     **/
    private String defaultInvocationTargetName;

    /**
     * It is allowed to address different objects to perform the call on.
     * This map holds all available objects.
     **/
    private Map<String,RPCInvocationTarget> targetMap;
    
    /**
     * This flag indicates if the handler should use exact type matching to
     * locate rpc-methods (unwrap JSON values) or just look for methods that
     * expect JSOValue params.
     **/
    private boolean unwrapJSONValues;
    
    /**
     * Create a new empty JSON-RPC handler.
     *
     * By default the JSONRPCHandler _unwraps_ the passed JSONValue params to
     * match requested methods.
     *
     **/
    public JSONRPCHandler() {
	super();
	
	this.targetMap                   = new TreeMap<String,RPCInvocationTarget>();
	this.defaultInvocationTargetName = null;
	this.unwrapJSONValues( true );
    }

    
    /**
     * This method set the 'unwrapJSONValues' flag.
     *
     * If set to true the handler will unwrap the passed JSON-RPC params
     * to their actual Java types to try to match the requested method.
     *
     * If set to false all params are considered to be of type 'JSONValue'
     * and all methods only accept JSONValue params.
     *
     * @params b The new value for the 'unwrapJSONValues' flag.
     **/
    public void unwrapJSONValues( boolean b ) {
	this.unwrapJSONValues = b;
    }
    
    /**
     * This method returns the value of the 'unwrapJSONValues' flag.
     *
     * @return The value of the 'unwrapJSONValues' flag.
     **/
    public boolean unwrapJSONValues() {
	return this.unwrapJSONValues;
    }

    /**
     * Add a new invocation target.
     * If the name already exists the associated target will be overriden.
     *
     * @param name               The name to use.
     * @param target             The target to add.
     * @param useAsDefaultTarget If true passed the target will be used as the default target.
     * @return true if the target name already existed before (and was overriden), false otherwise.
     * @throws NullPointerException If name or target is null.
     **/
    public boolean addInvocationTarget( String name, 
					RPCInvocationTarget target,
					boolean useAsDefaultTarget
					) 
	throws NullPointerException {

	if( name == null )
	    throw new NullPointerException( "Cannot add invocation targets with null name." );
	if( target == null )
	    throw new NullPointerException( "Cannot add null invocation targets." );

	boolean existed = this.targetMap.containsKey( name );

	this.targetMap.put( name, target );
	if( useAsDefaultTarget )
	    this.defaultInvocationTargetName = name;
	
	return existed;
    }

    /**
     * Removes the invocation target with the given name.
     *
     * @param name The target's name.
     * @return true if the target (name) existed and was removed, false otherwise.
     **/
    public boolean removeInvocationTarget( String name ) {

	if( name == null )
	    return false;

	return (this.targetMap.remove(name) != null);
    }


    /**
     * A helper method to throw canonical exceptions.
     **/
    private void throwJSONRPCException( String msg ) 
	throws JSONRPCException {
	throw createJSONRPCException( msg );
    }
    
    /**
     * A helper method to create canonical exceptions.
     **/
    private JSONRPCException createJSONRPCException( String msg ) {
	return new JSONRPCException( "Cannot handle JSON RPC request: " + msg );
    }

    /**
     * This method creates an JSON-RPC error response from the given params.
     *
     * @param e         The exception that was caught.
     * @param message   The message for the 'message' member in the error response.
     * @param errorCode The JSON eror code to use (hint: use JSONRPCError.CODE_*).
     * @param id        The original request-ID; may be null.
     **/
    private JSONRPCResponse createErrorResponse( Exception e, String message, int errorCode, JSONValue id ) {
	// System.out.println( message );
		
	// TODO: make a stack-trace object
	JSONValue data = new JSONString( e.toString() );
	
	JSONRPCError error = new DefaultJSONRPCError( new JSONNumber( new Integer(errorCode) ), 
						      new JSONString(message), 
						      data );
	JSONRPCResponse errorResponse = new DefaultJSONRPCResponse( null,   // no result
								    error,
								    ( id == null ? JSONValue.NULL : id )
								    );
	
	return errorResponse;
    }

    /**
     * Calls the method as specified in the JSON-RPC request string.
     *
     * @param jsonString The JSON-RPC string to execute.
     * @return The JSON response (may be an error response).
     * @throws NullPointerException If the json string is null.
     **/
    public JSONRPCResponse call( String jsonString ) 
	throws NullPointerException {

	if( jsonString == null )
	    throw new NullPointerException( "Cannot handle RPC request with null JSON string." );

	
	StringReader reader = new StringReader( jsonString );
	JSONRPCResponse response = this.call( reader );
	reader.close();

	return response;
    }

    /**
     * Calls the method as specified in the JSON-RPC request from the reader.
     *
     * @param reader A reader to read the JSON-RPC request from.
     * @return The JSON response (may be an error response).
     * @throws NullPointerException If the reader is null.
     **/
    public JSONRPCResponse call( Reader reader ) 
	throws NullPointerException {

	if( reader == null )
	    throw new NullPointerException( "Cannot handle RPC request from null reader." );


	try {

	    //Reader reader = new StringReader( jsonString );
	    JSONRPCRequest request = this.buildRPCRequest( reader );
	    //reader.close();	    
	    return this.execute( request );

	} catch( IOException e ) {
	    //throw new JSONRPCException( "Cannot read from json string: " + e.getMessage() );
	    return createErrorResponse( e, 
					"IO error: " + e.getMessage(), 
					JSONRPCError.CODE_SERVER_ERROR_MIN,
					null   // no ID
					);
	} catch( JSONSyntaxException e ) {
	    return createErrorResponse( e, 
					"Parse error: " + e.getMessage(), 
					JSONRPCError.CODE_PARSE_ERROR,
					null   // no ID
					);
	} catch( JSONException e ) {
	    return createErrorResponse( e, 
					"Invalid request: " + e.getMessage(), 
					JSONRPCError.CODE_INVALID_REQUEST,
					null   // no ID
					);
	} catch( JSONRPCException e ) {
	    return createErrorResponse( e, 
					"JSON-RPC error: " + e.getMessage(), 
					JSONRPCError.CODE_SERVER_ERROR_MIN,
					null   // no ID
					);
	}

    }
    
    
    /**
     * Calls the method as specified in the JSON-RPC request.
     *
     * @param request The request to execute.
     * @return The JSON response (may be an error response).
     * @throws NullPointerException If the request is null.
     **/
    public JSONRPCResponse call( JSONRPCRequest request ) 
	throws NullPointerException {

	if( request == null )
	    throw new NullPointerException( "Cannot execute null-requests." );



	try {
	    
	    return this.execute( request );

	} catch( JSONRPCException e ) {

	    return createErrorResponse( e, 
					"Unexpected JSON-RPC error: " + e.getMessage(), 
					JSONRPCError.CODE_SERVER_ERROR_MIN,
					request.getID()
					);

	} catch( JSONException e ) {
	    
	    return createErrorResponse( e, 
					"Unexpected JSON error: " + e.getMessage(), 
					JSONRPCError.CODE_SERVER_ERROR_MIN,
					request.getID()
					);

	}
    }
    

    /**
     * This is the actual method executing JSON-RPC requests.
     *
     * It may still throw some exceptions.
     *
     * @param request The request to execute.
     * @return A JSONRPCResponse - if no fundamental exceptions occured.
     * @param JSONException    Indicates unexpected JSON errors.
     * @param JSONRPCException Indicates unexpected JSON RPC errors.
     **/
    private JSONRPCResponse execute( JSONRPCRequest request )
	throws JSONException,
	       JSONRPCException {
	
	
	this.checkVersion(request);
	
	if( request.getMethod() == null || request.getMethod().isNull() )
	    throwJSONRPCException( "method argument is null." );
	if( !request.getMethod().isString() )
	    throwJSONRPCException( "method argument is not a string." );

	
	JSONValue jsonClass = request.asJSONObject().getObject().get("__jsonclass__");
	if( jsonClass != null && !jsonClass.isNull() )
	    throw new JSONRPCException( "The __jsonclass__ param is not supported." );

	
	String objectName    = null;
	String methodName    = null;
	try {
	    // May throw JSONException
	    Class<?>[] paramClasses = this.createParamClassArray( request );
	    Object[] paramObjects   = this.createParamObjectArray( request );

	    /*
	    System.out.println( "Param classes: " );
	    if( paramClasses != null ) {
		for( int i = 0; i < paramClasses.length; i++ )
		    System.out.println( "[" + i + "] " + paramClasses[i].getName() );
	    }
	    */


	    String request_name = request.getMethod().getString();
	    int pointIndex      = request_name.lastIndexOf(".");

	    
	    if( pointIndex == -1 ) {
		methodName = request_name;
		// no class name
	    } else {
		objectName  = request_name.substring(0,pointIndex);
		methodName = request_name.substring(pointIndex+1);	
	    }
	    
	    if( methodName.length() == 0 ) {
		
		//throw new JSONRPCException( "Method name is empty." );
		return this.createErrorResponse( new JSONRPCException( "No method name given." ), 
						 "No method name given.", 
						 JSONRPCError.CODE_METHOD_NOT_FOUND, 
						 request.getID()   
						 );
	    }


	    
	    // Locate the object to operate on
	    RPCInvocationTarget invocationTarget = null;
	    if( objectName == null ) {
		
		// There is no object/class name passed. Use default name (if set)
		if( this.defaultInvocationTargetName != null ) {
		    invocationTarget = this.targetMap.get( defaultInvocationTargetName );
		} 
		
		if( invocationTarget == null ) {
		    return this.createErrorResponse( new JSONRPCException( "Method not found: no default object name configured." ), 
						     "Method not found: no default object name configured.", 
						     JSONRPCError.CODE_METHOD_NOT_FOUND, 
						     request.getID()   
						     );
		}

	    } else {

		// The call includes an object name -> try to locate target
		invocationTarget = this.targetMap.get( objectName );
		//if( invocationTarget == null )
		//    throw new JSONRPCException( "Object '" + objectName + "' not found." );
		// Object found?
		if( invocationTarget == null ) {
		    
		    // throw new JSONRPCException( "Object '" + objectName + "' not found." );
		    return this.createErrorResponse( new JSONRPCException( "Object '" + objectName + "' not found." ), 
						     "Object '" + objectName + "' not found.", 
						     JSONRPCError.CODE_METHOD_NOT_FOUND, 
						     request.getID()   
						 );
		    
		}
		
	    }
	    
	    
	   
      


	    // Resolve method (may throw NoSuchMethodException or SecurityException)
	    Method method = invocationTarget.getClass().getMethod( methodName,
								   paramClasses 
								   );
	    
	    if( !Modifier.isPublic(method.getModifiers()) ) {
		return this.createErrorResponse( new JSONRPCException( "Calling '" + request_name + "' is not allowed (method is not public)." ), 
						 "Calling '" + request_name + "' is not allowed (method is not public).", 
						 JSONRPCError.CODE_INVALID_REQUEST, 
						 request.getID()   
						 );
	    }
	    if( !invocationTarget.checkMethodInvocation(method) ) {
		//throw new SecurityException( "Calling '" + request_name + "' is forbidden. " );
		return this.createErrorResponse( new JSONRPCException( "Calling '" + request_name + "' is forbidden." ), 
						 "Calling '" + request_name + "' is forbidden.", 
						 JSONRPCError.CODE_INVALID_REQUEST, 
						 request.getID()   
						 );

	    }
	    

	    Object resultObject = method.invoke( invocationTarget,
						 paramObjects
						 );
	    
	    // TOTO: the result object needs to be converted to its proper type
	    JSONValue result;
	    if( resultObject == null )
		result = JSONValue.NULL;
	    else
		result = new JSONString( resultObject.toString() );

	    return new DefaultJSONRPCResponse( result,
					       null,   // no error
					       request.getID()
					       );

	} catch( NoSuchMethodException e ) {
	    return createErrorResponse( e, 
					"Method not found: " + e.getMessage(), 
					JSONRPCError.CODE_METHOD_NOT_FOUND,
					request.getID()
					);
	} catch( IllegalAccessException e ) {
	    return createErrorResponse( e, 
					"Illegal Access: " + e.getMessage(), 
					JSONRPCError.CODE_SERVER_ERROR_MIN,
					request.getID()
					);
	} catch( IllegalArgumentException e ) {
	    return createErrorResponse( e, 
					"Illegal params: " + e.getMessage(), 
					JSONRPCError.CODE_INVALID_PARAMS,
					request.getID()
					);
	} catch( InvocationTargetException e ) {
	    return createErrorResponse( e, 
					"Invocation error: " + e.getMessage(), 
					JSONRPCError.CODE_SERVER_ERROR_MIN,
					request.getID()
					);
	}

    }

    /**
     * This method checks the request's version string for validity.
     *
     * @param request The request to check (must not be null).
     * @throws JSONRPCException If the version is not present or not valid.
     * @throws JSONException    If the version has the wrong datatype.
     **/
    private void checkVersion( JSONRPCRequest request ) 
	throws JSONRPCException,
	       JSONException {
	
	// Check version
	if( request.getVersion() == null )
	    throwJSONRPCException( "version is not present." );
	
	if( request.getVersion().isNull() )
	    throwJSONRPCException( "version is not specified." );
      
	
	// Due to the specification the version number should be EXACTLY "2.0" (a string!)
	try {
	    if( !request.getVersion().asJSONString().getString().equals("2.0") ) {
		
		// Catch a regular JSON exception (=type exception)
		throwJSONRPCException( "bad version: '" + request.getVersion().getNumber().toString() + "'." );
		
	    }
	} catch( JSONException e ) {
	    throwJSONRPCException( "bad version datatype." );
	}
	
    }

    /**
     * This method builds up the parameter class array from the passed
     * JSON-RPC request.
     *
     * Only the array param type is supported, not objects!
     *
     *
     * @param params A JSON-RPC request.
     * @return A class-array matching the passed params (returned in the same order).
     * @throws JSONRPCException 
     **/
    private Class<?>[] createParamClassArray( JSONRPCRequest request ) 
	throws JSONException,
	       JSONRPCException {

	if( request.getParams() == null || request.getParams().isNull() )
	    return null;


	if( request.getParams().isArray() ) {

	    return this.createParamClassArrayFromJSONArray( request.getParams().asJSONArray() );

	} else if( request.getParams().isObject() ) {
	    
	    return this.createParamClassArrayFromJSONObject( request.getParams().asJSONObject() );

	} else {
	    throw createJSONRPCException( "params must be an array or an object." );	    
	}
    }


    /**
     * This method builds up the parameter class array from the passed
     * JSON array param.
     *
     * This type mapping will be used:
     *   - JSON number:   Integer or Double
     *   - JSON boolean:  Boolean
     *   - JSON string:   String
     *   - JSON null:     Object
     *   - JSON array:    JSONArray
     *   - JSON object:   JSONObject
     *
     * Since version 1.0.1 all JSON param types (including JSONArray and 
     * JSONObject) are supported in addition to String, Number, Boolean and Null.
     *
     * @param params A JSON array containing the method call params.
     * @return A class-array matching the passed params (returned in the same order).
     * @throws JSONRPCException 
     **/
    private Class<?>[] createParamClassArrayFromJSONArray( JSONArray params ) 
	throws JSONRPCException, 
	       JSONException {

	// List<JSONValue> paramList = request.getParams().getArray();
	Class<?>[] paramClasses = new Class<?>[ params.getArray().size() ];

	for( int i = 0; i < params.getArray().size(); i++ ) {
	    JSONValue param = params.getArray().get(i);

	    if( !this.unwrapJSONValues() )
		paramClasses[i] = JSONValue.class;
	    else if( param.isNull() )
		paramClasses[i] = Object.class;
	    else if( param.isBoolean() )
		paramClasses[i] = Boolean.class;
	    else if( param.isNumber() ) 
		paramClasses[i] = param.getNumber().getClass(); // Double or Integer
	    else if( param.isString() )
		paramClasses[i] = String.class;
	    else if( param.isArray() )
		paramClasses[i] = JSONArray.class;
	    else if( param.isObject() )
		paramClasses[i] = JSONObject.class;
	    else 
		throw new JSONRPCException( "datatype at param " + i +" is not supported in the param list." );
	}
	
	return paramClasses;

    }
	
    /**
     * Using JSON objects as params DOES NOT WORK!
     *
     * See http://stackoverflow.com/questions/2237803/can-i-obtain-method-parameter-name-using-java-reflection
     **/
    private Class<?>[] createParamClassArrayFromJSONObject( JSONObject params ) 
	throws JSONException,
	       JSONRPCException {


	// Check param types
	// PROBLEM:
	//  - getting parameter names is possible if debug information is included during compilation. See this answer for more details
	//  - otherwise getting parameter names is not possible
	// See http://stackoverflow.com/questions/2237803/can-i-obtain-method-parameter-name-using-java-reflection

	// It MIGHT be possible to convert the object into an array, IF the
	// member names are numbers that address the array indices.
	try {
	    
	    return this.createParamClassArrayFromJSONArray( params.asJSONArray() );

	} catch( JSONException e ) {
	    
	    // Not convertible
	    throw new JSONRPCException( "Sorry, param type 'object' is not supported in this JSON-RPC implementation. Please use arrays instead." );

	}

    }
    
    /**
     * This method converts the JSON params passed in the request to
     * an array of Java objects.
     *
     * Since version 1.0.1 all JSON param types (including JSONArray and 
     * JSONObject) are supported in addition to String, Number, Boolean and Null.
     *
     * @param request The request to fetch the params from (must not be null).
     * @return The passed params as an array of Objects (basic types).
     * @throws NullPointerException If the request is null.
     * @throws JSONException If the passed request contains invalid params.
     * @throws JSONRPCException If the passed params do not match the type restrictions 
     *                          (no arrays or objects) or if the param entity is not
     *                          array compatible.
     **/
    private Object[] createParamObjectArray( JSONRPCRequest request ) 
	throws NullPointerException,
	       JSONException,
	       JSONRPCException {
	
	if( request.getParams() == null || request.getParams().isNull() )
	    return null;

	Object[] paramObjects = null;
	if( request.getParams().isArray() ) {
	    // List<JSONValue> paramList = request.getParams().getArray();
	    paramObjects = new Object[ request.getParams().getArray().size() ];
	    for( int i = 0; i < request.getParams().getArray().size(); i++ ) {
		JSONValue param = request.getParams().getArray().get(i);
		if( !this.unwrapJSONValues() )
		    paramObjects[i] = param;
		else if( param.isNull() )
		    paramObjects[i] = null;
		else if( param.isBoolean() )
		    paramObjects[i] = param.getBoolean();
		else if( param.isNumber() ) 
		    paramObjects[i] = param.getNumber(); // Double or Integer
		else if( param.isString() )
		    paramObjects[i] = param.getString();
		else if( param.isArray() ) 
		    paramObjects[i] = param.asJSONArray();
		else if( param.isObject() )
		    paramObjects[i] = param.asJSONObject();
		else
		    throw new JSONRPCException( "datatype at param " + i +" is not supported in the param list." );
	    }
	    
	    return paramObjects;
	} else if( request.getParams().isObject() ) {
	    throw new JSONRPCException( "Sorry, param type 'object' is not yet implemented." );
	} else {
	    throw createJSONRPCException( "params must be an array or an object." );
	}
    }


    /**
     * This method builds a JSONRPCRequest from the JSON data provided by the given reader.
     * 
     * @param reader The reader to read from (must not be null).
     * @return The next JSONRPCRequest from the reader (will not read more).
     * @throws NullPointerException If reader is null.
     * @throws JSONSyntaxException If the provided data does not represent a valid JSON value.
     * @throws JSONRPCException If the provided JSON value does not represent a valid JSON-RPC request.
     * @throws JSONException If any type errors occur.
     * @throws IOException If any IO errors occur while reading from the reader.
     **/
    public JSONRPCRequest buildRPCRequest( Reader reader ) 
	throws NullPointerException,
	       JSONSyntaxException,
	       JSONRPCException,
	       JSONException,
	       IOException {

	if( reader == null )
	    throw new NullPointerException( "Cannot read JSONRPCRequest from null reader." );


	// Create JSONRPCValueFactory 
	JSONValueFactory factory = new JSONRPCValueFactory();	    


	// Initialising parser/builder 
	ConfigurableJSONBuilder b  = new ConfigurableJSONBuilder( reader, false, factory );

	
	// start the pasrer
	b.parse();

	JSONValue json = b.getResult();
	
	if( json == null )
	    throw new JSONSyntaxException( "The passed data is empty and contains no JSON value." );
	

	//System.out.println( "JSON object: " + json.toString() );	
	if( !(json instanceof JSONRPCRequest) )
	    throw new JSONRPCException( "Retrieved value is NOT an instance of JSONRPCRequest. Found: " + json.getClass().getName() );
	

	// Explicit type cast :(
	return (JSONRPCRequest)json;
    }

    /**
     * For testign purposes only.
     **/
    public static void main( String[] argv ) {

	try {
	    System.out.println( "Initializing the JSONRPC request ..." );

	    JSONRPCResponse response = null;
	    
	    ikrs.json.JSONArray params = new ikrs.json.JSONArray();
	    params.getArray().add( new ikrs.json.JSONNumber(new Integer(1)) );
	    params.getArray().add( new ikrs.json.JSONString("test_A") );
	    params.getArray().add( new ikrs.json.JSONBoolean(true) );
	    JSONRPCRequest request = new DefaultJSONRPCRequest( "doAnything",
								params,
								new Integer(1234)   // id
								);
	    System.out.println( request.toJSONString() );
	    
	    
	    System.out.println( "Creating JSONRPC ..." );
	    JSONRPCHandler rpc = new JSONRPCHandler();
	    rpc.addInvocationTarget( "x", new TestInvocationTarget(), true );
	    //rpc.addInvocationTarget( "this", new TestInvocationTarget() );


	    System.out.println( "Executing the request ..." );
	    response = rpc.call( request );
	    System.out.println( "Response: " + response.toJSONString() );

	    String requestString = "{'jsonrpc' : '2.0', 'method': 'doAnything', 'params' : [ 2, 'test_B', false ], 'id' : 1234 }";
	    response = rpc.call( requestString );
	    System.out.println( "Response: " + response.toJSONString() );


	    requestString = "{'jsonrpc' : '2.0', '__jsonclass__': {}, 'method': 'doAnything', 'params' : [ 2, 'test_B', false ], 'id' : 1234 }";	
	    System.out.println( "This request should cause an error, because the '__jsonclass__' param is not supported: " + requestString );
	    response = rpc.call( requestString );
	    System.out.println( "Response: " + response.toJSONString() );


	    requestString = "{'jsonrpc' : '2.0', 'method': 'y.doAnything', 'params' : [ 2, 'test_B', false ], 'id' : 1234 }";	
	    System.out.println( "This request should cause an error, because the object 'y' is unknown: " + requestString );
	    response = rpc.call( requestString );
	    System.out.println( "Response: " + response.toJSONString() );



	    requestString = "{'jsonrpc' : '2.0', 'method': 'doAnything', 'params' : [ 2, 'test_B', false ], 'id' : 1234 } }";	
	    System.out.println( "This request should proceed though there is a trailing '}' at the end. The parser should not read that far: " + requestString );
	    response = rpc.call( requestString );
	    System.out.println( "Response: " + response.toJSONString() );

	    
	    // Since version 1.0.1 also param types 'Array' ...
	    requestString = "{'jsonrpc' : '2.0', 'method': 'printJSONArray', 'params' : [ [ 1, 2, 3, 4, 5 ] ], 'id' : 1234 } }";	
	    System.out.println( "This request should print a JSONArray on stdout: " + requestString );
	    response = rpc.call( requestString );
	    System.out.println( "Response: " + response.toJSONString() );

	    
	    // ... and 'Object' are supported.
	    requestString = "{'jsonrpc' : '2.0', 'method': 'printJSONObject', 'params' : [ { 'A' : 3, 'B': 2, 'C': 1, 'D': 0 } ], 'id' : 1234 } }";	
	    System.out.println( "This request should print a JSONArray on stdout: " + requestString );
	    response = rpc.call( requestString );
	    System.out.println( "Response: " + response.toJSONString() );

	    
	    System.out.println( "Done." );
	
	} catch( Exception e ) {
	    e.printStackTrace();
	}
	
	
    }
}
package ikrs.json.rpc;

/**
 * The JSON-RPC error object interface relating to the JSON-RPC specification.
 * http://www.jsonrpc.org/specification
 *
 * @author Ikaros Kappler
 * @date 2013-06-13
 * @version 1.0.0
 **/

import ikrs.json.JSONValue;


public interface JSONRPCError
    extends JSONValue {

    /**
     * Parse error. 	Invalid JSON was received by the server.
     * An error occurred on the server while parsing the JSON text.
     **/
    public static final int CODE_PARSE_ERROR         = -32700; 
    
    /**
     * 	Invalid Request. 	The JSON sent is not a valid Request object.
     **/
    public static final int CODE_INVALID_REQUEST     = -32600;
    
    /**
     * Method not found. 	The method does not exist / is not available.
     **/
    public static final int CODE_METHOD_NOT_FOUND    = -32601;
    
    /**
     * Invalid params. 	Invalid method parameter(s).
     **/
    public static final int CODE_INVALID_PARAMS      = -32602;
    
    /**
     * Internal error. 	Internal JSON-RPC error.
     **/
    public static final int CODE_INTERNAL_JSON_ERROR = -32603;

    /**
     * Server error. 	Reserved for implementation-defined server-errors.
     **/
    public static final int CODE_SERVER_ERROR_MIN    = -32000;   // -32000 to -32099;


    

    /**
     * This method returns the value of the 'jsonrpc' field from this
     * request.
     *
     * @return null if the request has no 'jsonrpc' field.
     **/
    public JSONValue getCode();

    /**
     * Get the RPC result of the call.
     * Due to the specification the result MUST be null if there were errors.
     *
     * @return The RPC result or JSON-null/Java-null on errors.
     **/
    public JSONValue getMessage();

    /**
     * Get the error object from the response (if result is null).
     * Due to the specification the error object MUST be null if there were no errors.
     *
     * @return The error result if there were errors.
     **/
    public JSONValue getData();
   

}

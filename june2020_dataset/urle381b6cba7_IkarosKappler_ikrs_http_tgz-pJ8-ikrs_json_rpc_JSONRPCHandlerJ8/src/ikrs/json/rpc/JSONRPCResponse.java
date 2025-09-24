package ikrs.json.rpc;

/**
 * The JSON-RPC response interface relating to the JSON-RPC specification.
 * http://www.jsonrpc.org/specification
 *
 * @author Ikaros Kappler
 * @date 2013-06-03
 * @version 1.0.0
 **/

import ikrs.json.JSONValue;


public interface JSONRPCResponse 
    extends JSONValue {

    

    /**
     * This method returns the value of the 'jsonrpc' field from this
     * request.
     *
     * @return null if the request has no 'jsonrpc' field.
     **/
    public JSONValue getVersion();

    /**
     * Get the RPC result of the call.
     * Due to the specification the result MUST be null if there were errors.
     *
     * @return The RPC result or JSON-null/Java-null on errors.
     **/
    public JSONValue getResult();

    /**
     * Get the error object from the response (if result is null).
     * Due to the specification the error object MUST be null if there were no errors.
     *
     * @return The error result if there were errors.
     **/
    public JSONValue getError();
    
    /**
     * Get the ID of this response. Each response identifies the related request
     * by the use of it's unique ID.
     *
     * Note that 'notifications' are special requests that expect no response.
     * A notification is a request without an ID. Server MUST NOT response to
     * notifications.
     *
     * @return The ID of this response (must not be null).
     **/
    public JSONValue getID();

}

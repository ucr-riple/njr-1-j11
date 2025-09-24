package ikrs.json.rpc;

/**
 * The JSON-RPC request interface relating to the JSON-RPC specification.
 * http://www.jsonrpc.org/specification
 *
 * @author Ikaros Kappler
 * @date 2013-06-03
 * @version 1.0.0
 **/

import ikrs.json.JSONValue;


public interface JSONRPCRequest 
    extends JSONValue { 

    /**
     * This method returns the value of the 'jsonrpc' field from this
     * request.
     *
     * @return null if the request has no 'jsonrpc' field.
     **/
    public JSONValue getVersion();
   
    /**
     * Get the request's method name.
     * Note that method names are case sensitive in RPC requests!
     *
     * @return null if no method field is present.
     **/ 
    public JSONValue getMethod();

    /**
     * Get the request's method params.
     * Due to the specification there are two possible ways to pass params:
     *
     *   (A) as JSON array (addressed by array indices).
     *   (B) as JSON object (addressed by member names).
     *
     * @return The RPC params of this request (must be array or object). 
     **/
    public JSONValue getParams();
    
    /**
     * Get the ID of this resquest. Each response identifies the related request
     * by the use of it's unique ID.
     *
     * Note that 'notifications' are special requests that expect no response.
     * A notification is a request without an ID. Server MUST NOT response to
     * notifications.
     *
     * @return The ID of this request. The ID is null if this request is a 
     *         notification.
     **/
    public JSONValue getID();

}

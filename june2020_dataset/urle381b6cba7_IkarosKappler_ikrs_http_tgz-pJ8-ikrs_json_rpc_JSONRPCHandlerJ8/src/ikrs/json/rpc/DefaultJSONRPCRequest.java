package ikrs.json.rpc;

/**
 * The default JSONRPCRequest implementation.
 *
 * @author Ikaros Kappler
 * @date 2013-06-04
 * @modified 2013-06-07 Ikaros Kappler (Constructor with empty param list added).
 * @version 1.0.1
 **/

import ikrs.json.JSONArray;
import ikrs.json.JSONNumber;
import ikrs.json.JSONObject;
import ikrs.json.JSONString;
import ikrs.json.JSONValue;


public class DefaultJSONRPCRequest 
    extends JSONObject
    implements JSONRPCRequest { 

    public DefaultJSONRPCRequest() {
	super();
    }

    public DefaultJSONRPCRequest( String method,
				  JSONArray params,
				  Number id 
				  ) {
	super();
	
	this.getMap().put( "jsonrpc", new JSONString("2.0") ); // Due to the specification this MUST be a string
	this.getMap().put( "method",  new JSONString(method) );
	this.getMap().put( "params",  params );
	this.getMap().put( "id",      new JSONNumber(id) );
    }

    //--- BEGIN ---------------- JSONRPCRequest implementation ------------
    /**
     * This method returns the value of the 'jsonrpc' field from this
     * request.
     *
     * @return null if the request has no 'jsonrpc' field.
     **/
    public JSONValue getVersion() {
	return this.getMap().get( "jsonrpc" );
    }
   
    /**
     * Get the request's method name.
     * Note that method names are case sensitive in RPC requests!
     *
     * @return null if no method field is present.
     **/ 
    public JSONValue getMethod() {
	return this.getMap().get( "method" );
    }

    /**
     * Get the request's method params.
     * Due to the specification there are two possible ways to pass params:
     *
     *   (A) as JSON array (addressed by array indices).
     *   (B) as JSON object (addressed by member names).
     *
     * @return The RPC params of this request (must be array or object). 
     **/
    public JSONValue getParams() {
	return this.getMap().get( "params" );
    }
    
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
    public JSONValue getID() {
	return this.getMap().get( "id" );
    }
    //--- BEGIN ---------------- JSONRPCRequest implementation ------------

}

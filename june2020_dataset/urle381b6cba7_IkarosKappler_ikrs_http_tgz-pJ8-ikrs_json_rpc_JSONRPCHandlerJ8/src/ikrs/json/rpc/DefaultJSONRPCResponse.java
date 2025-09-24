package ikrs.json.rpc;

/**
 * The default JSONRPCResponse implementation.
 *
 * @author Ikaros Kappler
 * @date 2013-06-11
 * @version 1.0.0
 **/

import ikrs.json.JSONArray;
import ikrs.json.JSONNumber;
import ikrs.json.JSONObject;
import ikrs.json.JSONString;
import ikrs.json.JSONValue;


public class DefaultJSONRPCResponse 
    extends JSONObject
    implements JSONRPCResponse { 

    public DefaultJSONRPCResponse( JSONValue result,
				   JSONValue error,
				   JSONValue id 
				   ) {
	super();

	this.getMap().put( "jsonrpc", new JSONString("2.0") ); // Due to the specification this MUST be a string
	this.getMap().put( "result",  result );
	this.getMap().put( "error",   error );
	this.getMap().put( "id",      id );
    }
    


    //--- BEGIN ---------------- JSONRPCRequest implementation ------------
    /**
     * This method returns the value of the 'jsonrpc' field from this
     * request.
     *
     * @return null if the request has no 'jsonrpc' field.
     **/
    public JSONValue getVersion() {
	return this.getMap().get( "version" );
    }

    /**
     * Get the RPC result of the call.
     * Due to the specification the result MUST be null if there were errors.
     *
     * @return The RPC result or JSON-null/Java-null on errors.
     **/
    public JSONValue getResult() {
	return this.getMap().get( "result" );
    }

    /**
     * Get the error object from the response (if result is null).
     * Due to the specification the error object MUST be null if there were no errors.
     *
     * @return The error result if there were errors.
     **/
    public JSONValue getError() {
	return this.getMap().get( "error" );
    }
    
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
    public JSONValue getID() {
	return this.getMap().get( "id" );
    }
    //--- BEGIN ---------------- JSONRPCRequest implementation ------------

}

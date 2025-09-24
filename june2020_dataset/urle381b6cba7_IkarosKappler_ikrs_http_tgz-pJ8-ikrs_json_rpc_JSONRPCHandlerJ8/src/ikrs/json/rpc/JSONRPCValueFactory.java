package ikrs.json.rpc;

import ikrs.json.JSONObject;
import ikrs.json.parser.DefaultJSONValueFactory;


/**
 * This class has only one purpose: create a JSONRPCRequest instead of a normal
 * JSONValue when the outer parent object was read.
 *
 * @author Ikaros Kappler
 * @date 2013-06-05
 * @version 1.0.0
 **/

public class JSONRPCValueFactory
    extends DefaultJSONValueFactory {
    
    
    /**
     * This method creates a non-embedded JSON object (first single 
     * value from the input).
     *
     * @return A single JSON object.
     **/
    @Override
    public JSONObject createObject() {
	// System.out.println( "PARENT ELEMENT (OBJECT) FOUND." );
	return new DefaultJSONRPCRequest();
    }
    


}
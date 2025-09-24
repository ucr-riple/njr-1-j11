package ikrs.json.parser;

import ikrs.json.*;


/**
 * A default JSONValueFactory implementation which is meant to be subclassed.
 *
 *
 * All methods just create new JSONValues, except createNull(...) which returns
 * JSONValue.NULL:
 *
 *  - createArray(...) returns 'new JSONArray()'
 *  - createNumber(...) returns 'new JSONumber(...)'
 *  - createBoolean(...) returns 'new JSONBoolean(...)'
 *  - ... and so on ...
 * 
 *
 * @author Ikaros Kappler
 * @date 2013-06-05
 * @modified 2013-06-10 Ikaros Kappler (createString methods were still missing).
 * @version 1.0.1
 **/

public class DefaultJSONValueFactory
    implements JSONValueFactory {

    /**
     * Creates a new default JSONValueFactory.
     **/
    public DefaultJSONValueFactory() {
	super();
    }

    /**
     * This method creates a non-embedded JSON number (first single 
     * value from the input).
     *
     * @return A single JSON number.
     **/
    public JSONNumber createNumber( Number number ) {
	return new JSONNumber( number );
    }

    /**
     * This method creates a JSON number embedded inside a 
     * JSON array. The passed integer is the element's array index.
     *
     * @param arrayIndex The superior array's index where the new 
     *                   element is located.
     * @return A JSON number inside a different array.
     **/
    public JSONNumber createNumber( Number number, int arrayIndex ) {
	return new JSONNumber( number ); // this.createNumber( number );
    }
    
    /**
     * This method creates a JSON number embedded inside a
     * JSON object. The passed string is the element's member name.
     *
     * @param memberName The new number's member name inside the
     *                   superior object.
     * @return A JSON number inside a object.
     **/
    public JSONNumber createNumber( Number number, String memberName ) {
	return new JSONNumber( number ); // this.createNumber( number );
    }


    /**
     * This method creates a non-embedded JSON boolean (first single 
     * value from the input).
     *
     * @return A single JSON bolean.
     **/
    public JSONBoolean createBoolean( Boolean bool ) {
	return new JSONBoolean( bool );
    }

    /**
     * This method creates a JSON boolean embedded inside a 
     * JSON array. The passed integer is the element's array index.
     *
     * @param arrayIndex The superior array's index where the new 
     *                   element is located.
     * @return A JSON boolean inside an array.
     **/
    public JSONBoolean createBoolean( Boolean bool, int arrayIndex ) {
	return new JSONBoolean( bool ); // this.createBoolean( bool );
    }
    
    /**
     * This method creates a JSON boolean embedded inside a
     * JSON object. The passed string is the element's member name.
     *
     * @param memberName The new boolean's member name inside the
     *                   superior object.
     * @return A JSON boolean inside a object.
     **/
    public JSONBoolean createBoolean( Boolean bool, String memberName ) {
	return new JSONBoolean( bool ); // this.createBoolean( bool );
    }

    /**
     * This method creates a non-embedded JSON string (first single 
     * value from the input).
     *
     * @return A single JSON string.
     **/
    public JSONString createString( String str ) {
	return new JSONString( str );
    }

    /**
     * This method creates a JSON string embedded inside a 
     * JSON array. The passed integer is the element's array index.
     *
     * @param arrayIndex The superior array's index where the new 
     *                   element is located.
     * @return A JSON string inside an array.
     **/
    public JSONString createString( String str, int arrayIndex ) {
	return new JSONString( str ); // this.createString( str );
    }
    
    /**
     * This method creates a JSON string embedded inside a
     * JSON object. The passed string is the element's member name.
     *
     * @param memberName The new boolean's member name inside the
     *                   superior object.
     * @return A JSON string inside a object.
     **/
    public JSONString createString( String str, String memberName ) {
	return new JSONString( str ); // this.createString( str );
    }

    /**
     * This method creates a non-embedded JSON null (first single 
     * value from the input).
     *
     * @return A single JSON null.
     **/
    public JSONNull createNull() {
	return JSONValue.NULL;
    }

    /**
     * This method creates a JSON null embedded inside a
     * JSON array. The passed integer is the element's array index.
     *
     * @param arrayIndex The superior array's index where the new 
     *                   element is located.
     * @return A JSON null inside an array.
     **/
    public JSONNull createNull( int arrayIndex ) {
	return JSONValue.NULL; // this.createNull();
    }

    /**
     * This method creates a JSON null embedded inside a
     * JSON object. The passed string is the element's member name.
     *
     * @param memberName The new null's member name inside the
     *                   superior object.
     * @return A JSON null inside a object.
     **/
    public JSONNull createNull( String memberName ) {
	return JSONValue.NULL; // this.createNull();
    }

    /**
     * This method creates a non-embedded JSON array (first single 
     * value from the input).
     *
     * @return A single JSON array.
     **/
    public JSONArray createArray() {
	return new JSONArray();
    }

    /**
     * This method creates a JSON array embedded inside a different
     * JSON array. The passed integer is the element's array index.
     *
     * @param arrayIndex The superior array's index where the new 
     *                   element is located.
     * @return A JSON array inside a different array.
     **/
    public JSONArray createArray( int arrayIndex ) {
	return new JSONArray(); // this.createArray();
    }
    
    /**
     * This method creates a JSON array embedded inside a different
     * JSON object. The passed string is the element's member name.
     *
     * @param memberName The new array's member name inside the
     *                   superior object.
     * @return A JSON array inside a object.
     **/
    public JSONArray createArray( String memberName ) {
	return new JSONArray(); // this.createArray();
    }


    /**
     * This method creates a non-embedded JSON object (first single 
     * value from the input).
     *
     * @return A single JSON object.
     **/
    public JSONObject createObject() {
	return new JSONObject();
    }
    
    /**
     * This method creates a JSON object embedded inside a different
     * JSON object. The passed integer is the element's array index.
     *
     * @param arrayIndex The superior array's index where the new 
     *                   element is located.
     * @return A JSON object inside an array.
     **/
    public JSONObject createObject( int arraIndex ) {
	return new JSONObject(); // this.createObject();
    }

    /**
     * This method creates a JSON object embedded inside a different
     * JSON object. The passed string is the element's member name.
     *
     * @param memberName The new object's member name inside the
     *                   superior object.
     * @return A JSON object inside a different object.
     **/
    public JSONObject createObject( String memberName ) {
	return new JSONObject(); // this.createObject();
    }

}
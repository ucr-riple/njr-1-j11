package ikrs.json;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;


/**
 * @author Ikaros Kappler
 * @date 2013-05-31
 * @modified 2013-06-10 Ikaros Kappler (Added the asJSON* methods for explicit type conversion).
 * @version 1.0.1
 **/


public abstract class AbstractJSONValue 
    implements JSONValue {

    /**
     * The type of this value.
     **/
    private int type;

    
    protected AbstractJSONValue( int type ) 
	throws IllegalArgumentException {

	super();

	if( !this.isValidTypeID(type) )
	    throw new IllegalArgumentException( "Invalid JSON type ID (" + type + ")." );
	
	this.type = type;
    }
    
    /**
     * This method indicates if this JSON value is a number.
     *
     * @return true if this value represents a number.
     **/
    public boolean isNumber() {
	return this.type == JSONValue.TYPE_NUMBER;
    }

    /**
     * This method indicates if this JSON value is a boolean.
     *
     * @return true if this value represents a boolean value.
     **/
    public boolean isBoolean() {
	return this.type == JSONValue.TYPE_BOOLEAN;
    }
    
    /**
     * This method indicates if this JSON value is a string.
     *
     * @return true if this value represents a string.
     **/
    public boolean isString() {
	return this.type == JSONValue.TYPE_STRING;
    }
    
    /**
     * This method indicates if this JSON value is an array.
     *
     * @return true if this value represents an array.
     **/
    public boolean isArray() {
	return this.type == JSONValue.TYPE_ARRAY;
    }

    /**
     * This method indicates if this JSON value is an object.
     *
     * @return true if this value represents an array.
     **/
    public boolean isObject() {
	return this.type == JSONValue.TYPE_OBJECT;
    }
    
    //--- BEGIN ------------------- Prepare override methods ---------------------------
    /**
     * This method indicates if this is JSON NULL.
     *
     * @return true if this JSON value is NULL (null).
     **/
    public boolean isNull() {
	return this.type == JSONValue.TYPE_NULL;
    }

    /**
     * Get the number from this JSON value.
     *
     * @return The number from this JSON value.
     * @throws JSONException If this value does not represent a number.
     **/
    public Number getNumber()
	throws JSONException {
	
	throw new JSONException( "JSON value is not a number." );
    }
    
    /**
     * Get the boolean from this JSON value.
     *
     * @return The boolean value from this JSON value.
     * @throws JSONException If this value does not represent a boolean value.
     **/
    public Boolean getBoolean()
	throws JSONException {
	
	throw new JSONException( "JSON value is not a boolean value." );
    }

    /**
     * Get the string from this JSON value.
     *
     * @return The string value from this JSON value.
     * @throws JSONException If this value does not represent a string.
     **/
    public String getString()
	throws JSONException {
	
	throw new JSONException( "JSON value is not a string." );
    }

    /**
     * Get the array value as a List from this JSON value.
     *
     * @return The array from this JSON value. The array is returned as a Mist.
     * @throws JSONException If this value does not represent an array.
     **/
    public List<JSONValue> getArray()
	throws JSONException {
	
	throw new JSONException( "JSON value is not an array." );
    }

    /**
     * Get the object value as a Map from this JSON value.
     *
     * @return The object from this JSON value. The object is returned as a Map.
     * @throws JSONException If this value does not represent an object.
     **/
    public Map<String,JSONValue> getObject()
	throws JSONException {
	
	throw new JSONException( "JSON value is not an object." );
    }



    /**
     * This method tries to convert this JSONValue into a JSONBoolean.
     *
     * If that is not possible (because the contained value does not represent
     * a boolean in any way) the method will throw an JSONException.
     *
     * @return This JSON value as a JSON boolean.
     * @throws JSONException If this value is not convertible to a boolean.
     **/
    public JSONBoolean asJSONBoolean()
	throws JSONException {
	
	throw new JSONException( "Cannot convert this value (" + this.getTypeName() + ") to a JSON boolean (incompatible types)." );
    }

    /**
     * This method tries to convert this JSONValue into a JSONNumber.
     *
     * If that is not possible (because the contained value does not represent
     * a number in any way) the method will throw an JSONException.
     *
     * @return This JSON value as a JSON number.
     * @throws JSONException If this value is not convertible to a number.
     **/
    public JSONNumber asJSONNumber()
	throws JSONException {

	throw new JSONException( "Cannot convert this value (" + this.getTypeName() + ") to a JSON number (incompatible types)." );
    }


    /**
     * This method tries to convert this JSONValue into a JSONString.
     *
     * If that is not possible (because the contained value does not represent
     * a string in any way) the method will throw an JSONException.
     *
     * Note: due to the fact that JSON is usually represented by a string
     *       EACH JSON VALUE SHOULD BE CONVERTIBLE TO A STRING.
     *
     * @return This JSON value as a JSON string.
     * @throws JSONException If this value is not convertible to a boolean.
     **/
    public JSONString asJSONString()
	throws JSONException {

	throw new JSONException( "Cannot convert this value (" + this.getTypeName() + ") to a JSON string (incompatible types)." );
    }

    
    /**
     * This method tries to convert this JSONValue into a JSONArray.
     *
     * If that is not possible (because the contained value does not represent
     * an array in any way) the method will throw an JSONException.
     *
     * @return This JSON value as a JSON array.
     * @throws JSONException If this value is not convertible to an array.
     **/
    public JSONArray asJSONArray()
	throws JSONException {

	throw new JSONException( "Cannot convert this value (" + this.getTypeName() + ") to a JSON array (incompatible types)." );
    }


    /**
     * This method tries to convert this JSONValue into a JSONObject.
     *
     * If that is not possible (because the contained value does not represent
     * an object in any way) the method will throw an JSONException.
     *
     * @return This JSON value as a JSON object.
     * @throws JSONException If this value is not convertible to an object.
     **/
    public JSONObject asJSONObject()
	throws JSONException {

	throw new JSONException( "Cannot convert this value (" + this.getTypeName() + ") to a JSON array (incompatible types)." );
    }


    

    /**
     * This method MUST write a valid JSON value to the passed writer.
     *
     * @param writer The writer to write to.
     * @throws IOException If any IO errors occur.
     **/
    public abstract void write( Writer writer )
	throws IOException;
    //--- END --------------------- Prepare override methods ---------------------------

    /**
     * Converts this JSON value into a valid JSON string.
     *
     * @return This value as a JSON string.
     **/
    public String toJSONString() {

	try {
	    Writer out = new CharArrayWriter( 4 );
	    this.write( out );
	    return out.toString(); // new String( out.toCharArray(), "UTF-8" );
	} catch( IOException e ) {
	    // This MUST NOT happen
	    throw new RuntimeException( "Failed to convert object to string by the use of a buffered writer.", e );
	}
    }

    /**
     * This method indicates if the passed type ID is supported by this class.
     *
     * @param type The type ID.
     * @return true If the passed type ID is supported by this class
     **/
    public boolean isValidTypeID( int type ) {
	return (type >= JSONValue.TYPE_NULL && type <= JSONValue.TYPE_OBJECT);
    }

    public String getTypeName() {
	switch( this.type ) {
	case JSONValue.TYPE_NULL:    return "NULL";
	case JSONValue.TYPE_BOOLEAN: return "Boolean";
	case JSONValue.TYPE_NUMBER:  return "Number";
	case JSONValue.TYPE_ARRAY:   return "Array";
	case JSONValue.TYPE_OBJECT:  return "Object"; 
	case JSONValue.TYPE_STRING:  return "String";
	default: return "unknown_type";
	}
    }

}
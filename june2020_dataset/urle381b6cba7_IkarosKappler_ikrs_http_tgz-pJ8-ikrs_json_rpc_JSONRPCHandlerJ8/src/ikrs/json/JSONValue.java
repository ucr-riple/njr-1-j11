package ikrs.json;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;


/**
 * @author Ikaros Kappler
 * @date 2013-05-31
 * @modified 2013-06-04 Ikaros Kappler (added the write method for JSON serialisation).
 * @modified 2013-06-10 Ikaros Kappler (Added the asJSON* methods for explicit type conversion).
 * @version 1.0.2
 **/


public interface JSONValue {

    /**
     * This is a public shared instance that should be used for JSON null.
     **/
    public static final JSONNull NULL     = new JSONNull();
    
    /**
     * This is a public shared instance that should be used for JSON true.
     **/
    public static final JSONBoolean TRUE  = new JSONBoolean(true);
    
    /**
     * This is a public shared instance that should be used for JSON false.
     **/
    public static final JSONBoolean FALSE = new JSONBoolean(false);


    /**
     * The constant indicating null.
     **/
    public static final int TYPE_NULL     = 0;

    /**
     * The type constant for numbers.
     **/
    public static final int TYPE_NUMBER   = 1;
    
    /**
     * The type constant for boolean values.
     **/
    public static final int TYPE_BOOLEAN  = 2;
    
    /**
     * The type constant for strings.
     **/
    public static final int TYPE_STRING   = 3;

    /**
     * The type constant for arrays.
     **/
    public static final int TYPE_ARRAY    = 4;

    /**
     * The type constant for objects.
     **/
    public static final int TYPE_OBJECT   = 5;




    
    /**
     * This method indicates if this is JSON NULL.
     *
     * @return true if this JSON value is NULL (null).
     **/
    public boolean isNull();
    
    /**
     * This method indicates if this JSON value is a number.
     *
     * @return true if this value represents a number.
     **/
    public boolean isNumber();

    /**
     * This method indicates if this JSON value is a boolean.
     *
     * @return true if this value represents a boolean value.
     **/
    public boolean isBoolean();
    
    /**
     * This method indicates if this JSON value is a string.
     *
     * @return true if this value represents a string.
     **/
    public boolean isString();
    
    /**
     * This method indicates if this JSON value is an array.
     *
     * @return true if this value represents an array.
     **/
    public boolean isArray();

    /**
     * This method indicates if this JSON value is an object.
     *
     * @return true if this value represents an array.
     **/
    public boolean isObject();
    




    /**
     * Get the number from this JSON value.
     *
     * @return The number from this JSON value.
     * @throws JSONException If this value does not represent a number.
     **/
    public Number getNumber()
	throws JSONException;
    
    /**
     * Get the boolean from this JSON value.
     *
     * @return The boolean value from this JSON value.
     * @throws JSONException If this value does not represent a boolean value.
     **/
    public Boolean getBoolean()
	throws JSONException;

    /**
     * Get the string from this JSON value.
     *
     * @return The string value from this JSON value.
     * @throws JSONException If this value does not represent a string.
     **/
    public String getString()
	throws JSONException;

    /**
     * Get the array value as a List from this JSON value.
     *
     * @return The array from this JSON value. The array is returned as a Mist.
     * @throws JSONException If this value does not represent an array.
     **/
    public List<JSONValue> getArray()
	throws JSONException;

    /**
     * Get the object value as a Map from this JSON value.
     *
     * @return The object from this JSON value. The object is returned as a Map.
     * @throws JSONException If this value does not represent an object.
     **/
    public Map<String,JSONValue> getObject()
	throws JSONException;
   



 

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
	throws JSONException;

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
	throws JSONException;


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
	throws JSONException;

    
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
	throws JSONException;


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
	throws JSONException;
    




    /**
     * This method MUST write a valid JSON value to the passed writer.
     *
     * @param writer The writer to write to.
     * @throws IOException If any IO errors occur.
     **/
    public void write( Writer writer )
	throws IOException;


    /**
     * Converts this JSON value into a valid JSON string.
     *
     * @return This value as a JSON string.
     **/
    public String toJSONString();
    
}
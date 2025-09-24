package ikrs.json;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;


/**
 * This is the JSON number subclass.
 *
 * @author Ikaros Kappler
 * @date 2013-05-31
 * @modified 2013-06-04 Ikaros Kappler (added the write method for JSON serialisation).
 * @version 1.0.0
 **/

public class JSONNumber
    extends AbstractJSONValue {


    /**
     * The numeric value if this JSON value (must not be null).
     **/
    private Number number;



    /**
     * Create a new JSON number.
     * 
     * @param number The numeric value this JSON value should have.
     * @throws NullPointerException If number is null.
     **/
    public JSONNumber( Number number ) 
	throws NullPointerException {

	super( JSONValue.TYPE_NUMBER );

	if( number == null )
	    throw new NullPointerException( "Cannot create a JSON number from null." );
	
	this.number = number;
    }


    /**
     * Get the number from this JSON value.
     *
     * @return The number from this JSON value.
     * @throws JSONException If this value does not represent a number.
     **/
    @Override
    public Number getNumber()
	throws JSONException {
	
	return this.number;
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
    @Override
    public JSONNumber asJSONNumber()
	throws JSONException {

	return this;
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
	
	return new JSONString( this.number.toString() );
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
    @Override
    public JSONArray asJSONArray()
	throws JSONException {

	//throw new JSONException( "Cannot convert this value (" + this.getTypeName() + ") to a JSON array (incompatible types)." );
	List<JSONValue> list = new ArrayList<JSONValue>(1);
	list.add( this );
	return new JSONArray( list );
    }


    /**
     * This method MUST write a valid JSON value to the passed writer.
     *
     * @param writer The writer to write to.
     * @throws IOException If any IO errors occur.
     **/
    @Override
    public void write( Writer writer )
	throws IOException {
	
	writer.write( this.number.toString() );
    }


    public String toString() {
	return this.number.toString();
    }

    
    /**
     * This static method is the equivalent to Java's Integer.parseInt(String)
     * respective Double.parseDouble(String). It expects a String containing
     * a number and converts it into a Java Number object.
     *
     * If the passed string does not represent a number the method throws
     * a NumberFormatException.
     *
     * @param str The string to be parsed.
     * @return The parsed number value.
     * @throws NullPointerException If the passed string is null.
     * @throws JSONException If the passed string does not represent a number.
     **/
    public static Number parseNumber( String str )
	throws NullPointerException,
	       NumberFormatException {

	if( str == null )
	    throw new NullPointerException( "Cannot parse null to a number." );
	
	try {

	    // Try to parse an integer first
	    return Integer.parseInt( str );

	} catch( NumberFormatException e ) {

	    // Was not an integer. Try to parse floating point number then.
	    return Double.parseDouble( str );

	}
	
    }

    /**
     * This static method is the equivalent to Java's Integer.parseInt(String)
     * respective Double.parseDouble(String). It expects a String containing
     * a number and converts it into a JSONNumber object.
     *
     * If the passed string does not represent a number the method throws
     * a JSONException (would be NumberFormatException in Java).
     *
     * @param str The string to be parsed.
     * @return The parsed JSON number value.
     * @throws NullPointerException If the passed string is null.
     * @throws JSONException If the passed string does not represent a number.
     **/
    public static JSONNumber parseJSONNumber( String str )
	throws NullPointerException,
	       JSONException {

	
	try {
	    // Try to convert string into a java value.
	    Number number = JSONNumber.parseNumber( str );

	    return new JSONNumber( number );
	    
	} catch( NumberFormatException e ) {

	    throw new JSONException( "The passed string does not represent a valid JSON number: '" + str + "'." );

	}
	
    }
}
package ikrs.json;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.ArrayList;

/**
 * This is the JSON boolean subclass.
 *
 * @author Ikaros Kappler
 * @date 2013-05-31
 * @modified 2013-06-04 Ikaros Kappler (added the write method for JSON serialisation).
 * @version 1.0.0
 **/


public class JSONBoolean
    extends AbstractJSONValue {


    /**
     * The boolean value if this JSON value (must not be null).
     **/
    private Boolean bool;



    /**
     * Create a new JSON boolean.
     * 
     * @param bool The boolean value this JSON value should have.
     **/
    public JSONBoolean( Boolean bool ) 
	throws NullPointerException {

	super( JSONValue.TYPE_BOOLEAN );

	if( bool == null )
	    throw new NullPointerException( "Cannot create a JSON boolean from null." );
	
	this.bool = bool;
    }


    /**
     * Get the boolean from this JSON value.
     *
     * @return The boolean value from this JSON value.
     * @throws JSONException If this value does not represent a boolean value.
     **/
    @Override
    public Boolean getBoolean()
	throws JSONException {
	
	return this.bool;
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
    @Override
    public JSONBoolean asJSONBoolean()
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
	
	if( this.bool.booleanValue() )
	    return new JSONString( "true" );
	else
	    return new JSONString( "false" );
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
	
	// Write 'true' or 'false' due to the specification
	if( this.bool.booleanValue() )
	    writer.write( "true" );
	else
	    writer.write( "false" );
    }
    

    public String toString() {
	return this.bool.toString();
    }



    /**
     * This static method is the equivalent to Java's Boolean.parseBoolean(String).
     * It expects a String containing a number and converts it into a Java Number 
     * object.
     *
     * If the passed string does not represent a boolean the method throws
     * a NumberFormatException.
     *
     * @param str The string to be parsed.
     * @return The parsed boolean value.
     * @throws NullPointerException If the passed string is null.
     * @throws JSONException If the passed string does not represent a boolean.
     **/
    public static Boolean parseBoolean( String str, 
					boolean caseSensitive )
	throws NullPointerException,
	       NumberFormatException {

	if( str == null )
	    throw new NullPointerException( "Cannot parse null to a boolean." );
	
	if( caseSensitive )
	    str = str.toLowerCase();

	
	// This method only recognizes "true" and "false"!
	if( str.equals("true") )
	    return new Boolean( true );
	else if( str.equals("false") )
	    return new Boolean( false );
	else
	    throw new NumberFormatException( "The passed string does not represent a boolean value: '" + str + "'." );
    }

    /**
     * This static method is the equivalent to Java's Boolean.parseBoolean(String). 
     * It expects a String containing a number and converts it into a JSONBoolean 
     * object.
     *
     * If the passed string does not represent a boolean the method throws
     * a JSONException.
     *
     * @param str The string to be parsed.
     * @return The parsed JSON number value.
     * @throws NullPointerException If the passed string is null.
     * @throws JSONException If the passed string does not represent a number.
     **/
    public static JSONBoolean parseJSONBoolean( String str, boolean caseSensitive )
	throws NullPointerException,
	       JSONException {

	
	try {
	    // Try to convert string into a java value.
	    Boolean bool = JSONBoolean.parseBoolean( str, caseSensitive );

	    //return new JSONBoolean( bool );
	    if( bool.booleanValue() )
		return JSONValue.TRUE;
	    else
		return JSONValue.FALSE;
	    
	} catch( NumberFormatException e ) {

	    throw new JSONException( "The passed string does not represent a valid JSON boolean: '" + str + "'." );

	}
	
    }
    
    
}
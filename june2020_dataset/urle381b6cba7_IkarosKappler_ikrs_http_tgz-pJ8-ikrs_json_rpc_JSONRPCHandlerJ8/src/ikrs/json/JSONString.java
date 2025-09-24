package ikrs.json;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;


/**
 * This is the JSON string subclass.
 *
 * @author Ikaros Kappler
 * @date 2013-05-31
 * @modified 2013-06-04 Ikaros Kappler (added the write method for JSON serialisation).
 * @version 1.0.0
 **/

public class JSONString
    extends AbstractJSONValue {


    /**
     * The string value if this JSON value (must not be null).
     **/
    private String string;



    /**
     * Create a new JSON string.
     * 
     * @param string The string value this JSON value should have.
     **/
    public JSONString( String string ) 
	throws NullPointerException {

	super( JSONValue.TYPE_STRING );

	if( string == null )
	    throw new NullPointerException( "Cannot create a JSON string from null." );
	
	this.string = string;
    }


    /**
     * Get the string from this JSON value.
     *
     * @return The string value from this JSON value.
     * @throws JSONException If this value does not represent a string.
     **/
    @Override
    public String getString()
	throws JSONException {
	
	return this.string;
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
	
	// throw new JSONException( "Cannot convert this value (" + this.getTypeName() + ") to a JSON boolean (incompatible types)." );
	return JSONBoolean.parseJSONBoolean( this.string,
					     true         // parse case sensitive?
					     );
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

	// throw new JSONException( "Cannot convert this value (" + this.getTypeName() + ") to a JSON number (incompatible types)." );
	return JSONNumber.parseJSONNumber( this.string );
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
    @Override
    public JSONString asJSONString()
	throws JSONException {

	return this;
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

	JSONString.writeJSON( writer, this.string );
	
    }


    /**
     * Note: this method does not (!) return a valid JSON string value!
     **/
    public String toString() {
	return this.string;
    }


    public static void writeJSON( Writer writer, String string )
	throws IOException {

    	writer.write( '"' );
	
	for( int i = 0; i < string.length(); i++ ) {
	    
	    char c = string.charAt(i);
	    switch( c ) {
		// There are double quotation marks, so there is no need to escape single quotes.
		// Additionally escaping ' is not part of the JSON specification!
		// case '\'': writer.write( "\\'" ); break;
	    case '"': writer.write( "\\\"" ); break;
	    case '\n': writer.write( "\\n" ); break;
	    case '\t': writer.write( "\\t" ); break;
	    case '\b': writer.write( "\\b" ); break; 
		// Escape unicode chars?
	    default: writer.write( c ); break;
	    }

	}
	
	writer.write( '"' );
    }
    
}

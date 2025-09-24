package ikrs.json;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;


/**
 * This is the JSON array subclass.
 *
 * @author Ikaros Kappler
 * @date 2013-05-31
 * @modified 2013-06-04 Ikaros Kappler (added the write method for JSON serialisation).
 * @modified 2013-06-04 Ikaros Kappler (added the constructor with empty param list).
 * @modified 2013-06-13 Ikaros Kappler (silently converting Java null to JSON null during serialisation).
 * @version 1.0.3
 **/


public class JSONArray
    extends AbstractJSONValue {


    /**
     * The array value (as a List) if this JSON value (must not be null).
     **/
    private List<JSONValue> list;


    /**
     * Creates a new empty JSON array backed by an internal ArrayList instance.
     **/
    public JSONArray() {
	super( JSONValue.TYPE_ARRAY );

	this.list = new ArrayList<JSONValue>(1);
    }

    /**
     * Create a new JSON array.
     * 
     * @param array The array value this JSON value should have.
     **/
    public JSONArray( List<JSONValue> array ) 
	throws NullPointerException {

	super( JSONValue.TYPE_ARRAY );

	if( array == null )
	    throw new NullPointerException( "Cannot create a JSON array from null." );
	
	this.list = array;
    }


    /**
     * Get the array value as a List from this JSON value.
     *
     * @return The array from this JSON value. The array is returned as a Mist.
     * @throws JSONException If this value does not represent an array.
     **/
    @Override
    public List<JSONValue> getArray()
	throws JSONException {
	
	return this.list;
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

	return this;
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
    @Override
    public JSONObject asJSONObject()
	throws JSONException {

	// throw new JSONException( "Cannot convert this value (" + this.getTypeName() + ") to a JSON array (incompatible types)." );
	
	// There is a way to convert an array to an object:
	//   convert the array indices to object member names.
	JSONObject obj = new JSONObject();

	// The list iterator returns the list elements in the proper order
	ListIterator<JSONValue> iter = this.list.listIterator();
	int i = 0;
	while( iter.hasNext() ) {

	    JSONValue elem = iter.next();
	    obj.getMap().put( Integer.toString(i),    // member name
			      elem                    // member value
			      );
	    
	    i++;
	}
	
	return obj;
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

	writer.write( '[' );

	ListIterator<JSONValue> iter = this.list.listIterator();
	int i = 0;
	while( iter.hasNext() ) {

	    JSONValue v = iter.next();

	    if( i > 0 )
		writer.write( "," );
	    
	    writer.write( " " );
	    
	    // Silently convert Java null to JSON null
	    if( v == null )
		JSONValue.NULL.write( writer );
	    else
		v.write( writer );

	    i++;
	}
	
	if( i > 0 )
	    writer.write( " " );

	writer.write( ']' );
    }


    
    /**
     * This method is a copy of getArray(), but with two differences:
     * it is protected (only for subclasses) and does not thrown any exceptions.
     *
     * Note that this method does not create a copy of the internal list, so
     * changes to the list reflect this JSONArray!
     *
     * @return The internal list, which is never null.
     **/
    protected List<JSONValue> getList() {
	return this.list;
    }


    public String toString() {
	return this.getClass().getName() + "=" + this.list.toString();
    }

}
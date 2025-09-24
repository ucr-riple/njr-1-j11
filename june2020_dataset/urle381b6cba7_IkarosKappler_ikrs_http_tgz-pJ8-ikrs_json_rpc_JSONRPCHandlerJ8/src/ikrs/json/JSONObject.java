package ikrs.json;

/**
 * This is the JSON object subclass.
 *
 * @author Ikaros Kappler
 * @date 2013-05-31
 * @modified 2013-06-04 Ikaros Kappler (added the write method for JSON serialisation).
 * @modified 2013-06-04 Ikaros Kappler (added constructor with empty param list).
 * @modified 2013-06-13 Ikaros Kappler (silently converting Java null values to JSON null during serialisation).
 * @version 1.0.3
 **/

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class JSONObject
    extends AbstractJSONValue {


    /**
     * The array value (as a List) if this JSON value (must not be null).
     **/
    private Map<String,JSONValue> map;

    
    /**
     * Create a new JSON object.
     *
     * The constructor creates a new Map (TreeMap).
     **/
    public JSONObject() {
	super( JSONValue.TYPE_OBJECT );
	
	this.map = new TreeMap<String,JSONValue>();
    }

    /**
     * Create a new JSON object.
     * 
     * @param object The object value this JSON value should have.
     **/
    public JSONObject( Map<String,JSONValue> object ) 
	throws NullPointerException {

	super( JSONValue.TYPE_OBJECT );

	if( object == null )
	    throw new NullPointerException( "Cannot create a JSON object from null." );
	
	this.map = object;
    }


    /**
     * Get the object value as a Map from this JSON value.
     *
     * @return The object from this JSON value. The object is returned as a Map.
     * @throws JSONException If this value does not represent an object.
     **/
    @Override
    public Map<String,JSONValue> getObject()
	throws JSONException {
	
	return this.map;
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

	//throw new JSONException( "Cannot convert this value (" + this.getTypeName() + ") to a JSON array (incompatible types)." );
	
	// As it is possible to convert an array into an object (indices become member names)
	// it should be possible to re-convert such an object.
	// This requires the member names to address array indices in a half closed interval [0, n).
	
	// Prepare the list with the exact capacity
	int size = this.getMap().size();
	Map<Integer,JSONValue> indexMap = new TreeMap<Integer,JSONValue>();
	
	// Iterate through all elements
	Iterator<Map.Entry<String,JSONValue>> iter = this.getMap().entrySet().iterator();
	while( iter.hasNext() ) {

	    // The tuple (memberName, memberValue)
	    Map.Entry<String,JSONValue> entry = iter.next();
	    String memberName = entry.getKey();
	    if( memberName == null ) // This should not happen!
		super.asJSONArray(); // this call will raise the default exception
	    
	    try {	
		
		int index = Integer.parseInt( memberName );

		// Check bounds
		if( index < 0 || index >= size )
		    super.asJSONArray();

		// Check if array index is already in use.
		if( indexMap.get(new Integer(index)) != null )
		    super.asJSONArray();
		
		// Seems OK.
		indexMap.put( new Integer(index), 
			      entry.getValue() 
			      );
		
	    } catch( NumberFormatException e ) {
		
		// The element name is not a number.
		// This call will throw the default exception.
		super.asJSONArray();
	    }

	}
	


	// The member name set is valid.
	// Now convert the index map to an array/list
	List<JSONValue> list = new ArrayList<JSONValue>( Math.max(size,1) );
	Iterator<Integer> indexIter = indexMap.keySet().iterator(); // This is a SORTED SET :)
	while( indexIter.hasNext() ) {

	    Integer index   = indexIter.next();
	    JSONValue value = indexMap.get( index );
	    
	    list.add( value );
	    
	}

	return new JSONArray( list );
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

	return this;
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

	writer.write( "{" );

	Iterator<Map.Entry<String,JSONValue>> iter = this.map.entrySet().iterator();
	int i = 0;
	while( iter.hasNext() ) {
	    
	    if( i > 0 )
		writer.write( "," );
	    writer.write( " " );

	    Map.Entry<String,JSONValue> entry = iter.next();	    
	    JSONString.writeJSON( writer, entry.getKey() );

	    writer.write( ": " );

	    // Convert Java NULL to JSON NULL silently
	    if( entry.getValue() == null )
		JSONValue.NULL.write( writer );
	    else
		entry.getValue().write( writer );

	    i++;
	}

	if( i > 0 )
	    writer.write( " " );

	writer.write( "}" );
	
    }

    /**
     * This method is a copy of getObject(), but with two differences:
     * it is protected (only for subclasses) and does not thrown any exceptions.
     *
     * Note that this method does not create a copy of the internal map, so
     * changes to the map reflect this JSONObject!
     *
     * @return The internal map, which is never null.
     **/
    protected Map<String,JSONValue> getMap() {
	return this.map;
    }

    public String toString() {
	return this.getClass().getName() + "=" + this.map.toString();
    }


    /**
     * For testing purposes only.
     **/
    public static void main( String[] argv ) {

	// Test is array -> object -> array conversion works
	try {
	    JSONArray arr_A = new JSONArray();
	    arr_A.getList().add( new JSONString("A") );
	    arr_A.getList().add( new JSONString("B") );
	    arr_A.getList().add( new JSONString("C") );
	    arr_A.getList().add( new JSONString("D") );
	    arr_A.getList().add( new JSONString("E") );
	    arr_A.getList().add( new JSONString("F") );
	    
	    System.out.println( "Array created: " + arr_A.toJSONString() );
	    
	    System.out.println( "Converting array to object ..." );
	    JSONObject obj_A = arr_A.asJSONObject();
	    System.out.println( "Object retrieved: " + obj_A.toJSONString() );
	    
	    System.out.println( "Re-converting object to array ... " );
	    JSONArray arr_B = obj_A.asJSONArray();	    
	    System.out.println( "Re-retrieved array: " + arr_B.toJSONString() );
	    

	    System.out.println( "\nRunning one more test ...\n" );
	    JSONObject obj_X = new JSONObject();
	    obj_X.getMap().put( "1", new JSONString("a") );
	    obj_X.getMap().put( "2", new JSONString("b") );
	    // ... dismiss ('3','c')
	    obj_X.getMap().put( "4", new JSONString("d") );
	    System.out.println( "This objec should not be convertible into an array: " + obj_X.toJSONString() );
	    
	    
	    JSONArray arr_Y = obj_X.asJSONArray();
	    
	    
	} catch( Exception e ) {
	    e.printStackTrace();
	}

    }

}
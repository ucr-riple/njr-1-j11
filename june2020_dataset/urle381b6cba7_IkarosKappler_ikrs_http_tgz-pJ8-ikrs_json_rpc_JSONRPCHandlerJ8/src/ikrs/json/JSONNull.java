package ikrs.json;

/**
 * This is the JSON null subclass.
 *
 * @author Ikaros Kappler
 * @date 2013-05-31
 * @modified 2013-06-04 Ikaros Kappler (added the write method for JSON serialisation).
 * @version 1.0.0
 **/

import java.io.IOException;
import java.io.Writer;
import java.util.Map;


public class JSONNull
    extends AbstractJSONValue {


    /**
     * Create a new JSON null.
     * 
     * @param object The object value this JSON value should have.
     **/
    public JSONNull() 
	throws NullPointerException {

	super( JSONValue.TYPE_NULL );
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
	
	writer.write( "null" );
    }


    public String toString() {
	return "null";
    }

}
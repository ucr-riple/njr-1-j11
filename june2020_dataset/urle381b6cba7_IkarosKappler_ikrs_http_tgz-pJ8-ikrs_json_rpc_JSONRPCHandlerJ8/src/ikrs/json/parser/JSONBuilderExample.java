package ikrs.json.parser;

/**
 * A JSON builder example that extends the JSONParser.
 *
 * The generated object is either a String (=basic JSON value), or a Map (=JSON object) or
 * a List (=JSON array) or null (=JSON null).
 *
 *
 * Warning: to keep this example simple the class uses unsafe operations and type casts!
 *
 *
 * @author Ikaros Kappler
 * @date 2013-05-30
 * @version 1.0.0
 * @deprecated This class was just for presentation purposes and should not be used in production.
 **/

import java.io.IOException;
import java.io.Reader;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Stack;

import ikrs.json.*;


public class JSONBuilderExample
    extends JSONParser {

    /**
     * A constant (for internal use) for the JSON-Object type.
     **/
    private static final int JSONTYPE_OBJECT   = 0;
    
    /**
     * A constant (for internal use) for the JSON-Array type.
     **/
    private static final int JSONTYPE_ARRAY    = 1;

    /**
     * This attribute stores the object that was recently popped from the object stack.
     **/
    private Object lastPoppedObject;

    /**
     * This field stores the current JSON type (Array, Object, anything else).
     **/
    private int currentType;
    
    /**
     * If the current JSON type is an object, this variable stores the object as a Map.
     **/
    private Map<String,Object> currentObject;

    /**
     * If the current JSON type is an array, this variable stores the array as a List.
     **/
    private List<Object> currentArray;
    
    /**
     * The current member name (for JSON object type).
     **/
    private String currentMemberName;
    
    /**
     * The current member value (for JSON object type).
     **/
    private Object currentMemberValue;

    /**
     * The first of three stacks: this stores the JSON type.
     * All three stacks always have the same size.
     **/
    private Stack<Integer> typeStack;
    
    /**
     * The second of three stacks: this stores the JSON value.
     * All three stacks always have the same size.
     **/
    private Stack<Object> objectStack;
    
    /**
     * The third of three stacks: this stores the member names (for object type).
     * All three stacks always have the same size.
     **/
    private Stack<String> memberNameStack;


    /**
     * Creates a new JSONBuilderExample (not case sensitive).
     *
     * @param reader The reader to read from (must not be null).
     * @throws NullPointerException If reader is null.
     **/
    public JSONBuilderExample( Reader reader ) 
	throws NullPointerException {
	
	this( reader, false );
    }

    /**
     * Creates a new JSONBuilderExample.
     *
     * @param reader The reader to read from (must not be null).
     * @param caseSensitive A flag indicating if the underlying parser should be case sensitive.
     * @throws NullPointerException If reader is null.
     **/
    public JSONBuilderExample( Reader reader,
			       boolean caseSensitive
			       ) 
	throws NullPointerException {
	
	super( reader, caseSensitive );
	
	this.objectStack      = new Stack<Object>();
	this.typeStack        = new Stack<Integer>();
	this.memberNameStack  = new Stack<String>();
	
	this.objectStack.push( new Object() ); // dummy
	this.typeStack.push( new Integer(-1) );
	this.memberNameStack.push( null );
	this.currentType = -1;
    }    
    
    private Object popFromStack() {

	// Remove from stack
	this.lastPoppedObject = this.objectStack.pop();
	this.typeStack.pop();	
	this.currentMemberName = this.memberNameStack.pop();

	this.currentType = this.typeStack.peek().intValue();

	
	this.currentArray = null;
	this.currentObject = null;
	if( this.currentType == JSONTYPE_ARRAY ) 
	    this.currentArray = (List<Object>)this.objectStack.peek();
	else if( this.currentType == JSONTYPE_OBJECT ) 
	    this.currentObject = (Map<String,Object>)this.objectStack.peek();    
	else 
	    ; // NOOP

	this.currentMemberValue = this.lastPoppedObject;

	return this.lastPoppedObject;
    }

    //--- BEGIN --------- Override parser event methods ---------- //
    @Override
    protected void fireJSONBegin() {	
    }

    @Override
    protected void fireJSONEnd() {	
    }

    @Override
    protected void fireArrayBegin() {	
	this.currentType = JSONTYPE_ARRAY;
	this.currentArray = new LinkedList<Object>();
	this.objectStack.push( this.currentArray );
	this.typeStack.push( new Integer(JSONTYPE_ARRAY) );
	this.memberNameStack.push( this.currentMemberName );
    }

    @Override
    protected void fireArrayElementEnd() {
	this.currentArray.add( this.currentMemberValue );
    }
    
    @Override
    protected void fireArrayEnd() {
	this.popFromStack();	
    }

    @Override
    protected void fireObjectBegin() {
	this.currentType = JSONTYPE_OBJECT;
	this.currentObject = new TreeMap<String,Object>();
	this.objectStack.push( this.currentObject );
	this.typeStack.push( new Integer(JSONTYPE_OBJECT) );
	this.memberNameStack.push( this.currentMemberName );
    }

    @Override
    protected void fireObjectEnd() {
	this.popFromStack();	
    }

    @Override
    protected void fireMemberBegin() {
	// NOOP
    }

    @Override
    protected void fireMemberNameRead( String token ) {
	this.currentMemberName = token;
    }

    @Override
    protected void fireMemberEnd() {
	this.currentObject.put( currentMemberName, 
				this.currentMemberValue
				);
	
    }

    @Override
    protected void fireNumberRead( String number ) {
	try {
	    try {
		// Try to parse Integer
		this.currentMemberValue = new Integer(number);
	    } catch( NumberFormatException e ) {
		this.currentMemberValue = new Double(number);
	    }
	} catch( NumberFormatException e ) {
	    throw new RuntimeException( "Ooops, this number is invalid: " + number );
	}
    }

    @Override
    protected void fireStringRead( String token ) {
	this.currentMemberValue = token;
    }
    
    @Override
    protected void fireTrueRead( String value ) {
	this.currentMemberValue = new Boolean(true); // value
    }

    @Override
    protected void fireFalseRead( String value ) {
	this.currentMemberValue = new Boolean(false); // value
    }

    @Override
    protected void fireNullRead( String value ) {
	this.currentMemberValue = null; // value;
    }
    //--- END ----------- Override parser event methods ---------- //
   

    /**
     * For testing purposes only.
     **/
    public static void main( String[] argv ) {

	if( argv.length == 0 ) {
	    System.err.println( "Please pass a JSON input file name." );
	    System.exit( 1 );
	}

	try {

	    System.out.println( "Initialising reader ... " );
	    Reader reader = new java.io.FileReader( argv[0] );
	    System.out.println( "Initialising parser/builder ... " );
	    JSONBuilderExample b  = new JSONBuilderExample( reader, false );
	    System.out.println( "Starting the parser ..." );
	    b.parse();
	    System.out.println( "Closing the reader ..." );
	    reader.close();
	    System.out.println( "Done." );

	    Object json = b.lastPoppedObject;
	    System.out.println( "JSON object: " + json.toString() );

	} catch( Exception e ) {
	    e.printStackTrace();
	}

    }
}
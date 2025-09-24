package ikrs.json.parser;

/**
 * A JSON builder example that extends the JSONParser.
 *
 * The generated object is either a String (=basic JSON value), or a Map (=JSON object) or
 * a List (=JSON array) or null (=JSON null).
 *
 *
 *
 * @author Ikaros Kappler
 * @date 2013-05-30
 * @version 1.0.0
 **/

import java.io.IOException;
import java.io.Reader;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Stack;

import ikrs.json.*;



public class JSONBuilder
    extends JSONParser {

    /**
     * This attribute stores the object that was recently popped from the object stack.
     **/
    private JSONValue lastPoppedValue;
    
    /**
     * The current member name (for JSON object type).
     **/
    private String currentMemberName;
    
    /**
     * The current member value (for JSON object type).
     **/
    private JSONValue currentMemberValue;
    
    /**
     * The first of two stacks: this stores the JSON value.
     * Both stacks always have the same size.
     **/
    private Stack<JSONValue> valueStack;
    
    /**
     * The second of two stacks: this stores the member names (for object type).
     * Both stacks always have the same size.
     **/
    private Stack<String> memberNameStack;


    /**
     * Creates a new JSONBuilder (not case sensitive).
     *
     * @param reader The reader to read from (must not be null).
     * @throws NullPointerException If reader is null.
     **/
    public JSONBuilder( Reader reader ) 
	throws NullPointerException {
	
	this( reader, false );
    }

    /**
     * Creates a new JSONBuilder.
     *
     * @param reader The reader to read from (must not be null).
     * @param caseSensitive A flag indicating if the underlying parser should be case sensitive.
     * @throws NullPointerException If reader is null.
     **/
    public JSONBuilder( Reader reader,
			boolean caseSensitive
			       ) 
	throws NullPointerException {
	
	super( reader, caseSensitive );
	
	this.valueStack       = new Stack<JSONValue>();
	this.memberNameStack  = new Stack<String>();
	
	this.valueStack.push( new JSONNull() ); 
	this.memberNameStack.push( null );
    }    
    
    private JSONValue popFromStack() {

	// Remove from stack
	this.lastPoppedValue = this.valueStack.pop();
	this.currentMemberName = this.memberNameStack.pop();
	this.currentMemberValue = this.lastPoppedValue;
	return this.lastPoppedValue;
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
	this.valueStack.push( new JSONArray( new LinkedList<JSONValue>() ) );
	this.memberNameStack.push( this.currentMemberName );
    }

    @Override
    protected void fireArrayElementEnd() {
	try {
	    this.valueStack.peek().getArray().add( this.currentMemberValue );
	} catch( JSONException e ) {
	    throw new RuntimeException( "Fatal error: unexpected JSONException caught when trying to retrieve JSON array.", e );
	}
    }
    
    @Override
    protected void fireArrayEnd() {
	this.popFromStack();	
    }

    @Override
    protected void fireObjectBegin() {
	this.valueStack.push( new JSONObject( new TreeMap<String,JSONValue>() ) );
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
	try {
	    this.valueStack.peek().getObject().put( this.currentMemberName, 
						    this.currentMemberValue
						    );
	} catch( JSONException e ) {
	    throw new RuntimeException( "Fatal error: unexpected JSONException caught when trying to retrieve JSON object.", e );
	}	
    }

    @Override
    protected void fireNumberRead( String number ) {
	try {
	    try {
		// Try to parse Integer
		this.currentMemberValue = new JSONNumber( new Integer(number) );
	    } catch( NumberFormatException e ) {
		this.currentMemberValue = new JSONNumber( new Double(number) );
	    }
	} catch( NumberFormatException e ) {
	    // Store as a string??? Throw exception??? 
	    // Actually this means there is a type error in the parser, so this excption should NEVER occur
	    //throw new RuntimeException( "Ooops, this number is invalid: " + number );
	    this.currentMemberValue = new JSONString( number );
	}
    }

    @Override
    protected void fireStringRead( String token ) {
	this.currentMemberValue = new JSONString( token );
    }
    
    @Override
    protected void fireTrueRead( String value ) {
	this.currentMemberValue = new JSONBoolean( new Boolean(true) );  // Also store raw value?
    }

    @Override
    protected void fireFalseRead( String value ) {
	this.currentMemberValue = new JSONBoolean( new Boolean(false) ); // Also store raw value?
    }

    @Override
    protected void fireNullRead( String value ) {
	this.currentMemberValue = new JSONNull();                        // Also store raw value?
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
	    JSONBuilder b  = new JSONBuilder( reader, false );
	    System.out.println( "Starting the parser ..." );
	    long time_start = System.currentTimeMillis();
	    b.parse();
	    long time_end   = System.currentTimeMillis();
	    System.out.println( "Closing the reader ..." );
	    reader.close();
	    System.out.println( "Done." );
	    System.out.println( "Parsing and building took " + (time_end - time_start) + " ms." );

	    JSONValue json = b.lastPoppedValue;
	    System.out.println( "JSON object: " + json.toJSONString() );

	} catch( Exception e ) {
	    e.printStackTrace();
	}

    }
}
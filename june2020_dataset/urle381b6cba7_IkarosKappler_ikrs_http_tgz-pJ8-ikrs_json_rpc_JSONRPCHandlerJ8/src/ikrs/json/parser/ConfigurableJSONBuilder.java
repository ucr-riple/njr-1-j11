package ikrs.json.parser;

/**
 * A configurable JSON builder that extends the JSONParser.
 *
 * The generated object is either a String (=basic JSON value), or a Map (=JSON object) or
 * a List (=JSON array) or null (=JSON null).
 *
 *
 *
 * @author Ikaros Kappler
 * @date 2013-06-05
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


public class ConfigurableJSONBuilder
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
     * ...
     **/
    private Integer currentArrayIndex;
    
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
     * A third stack holding the array indices (if in array).
     **/
    private Stack<Integer>arrayIndexStack;

    
    private JSONValueFactory factory;

    /**
     * Creates a new JSONBuilder (not case sensitive).
     *
     * @param reader The reader to read from (must not be null).
     * @throws NullPointerException If reader is null.
     **/
    public ConfigurableJSONBuilder( Reader reader,
				    JSONValueFactory factory 
				    ) 
	throws NullPointerException {
	
	this( reader, false, factory );
    }

    /**1
     * Creates a new JSONBuilder.
     *
     * @param reader The reader to read from (must not be null).
     * @param caseSensitive A flag indicating if the underlying parser should be case sensitive.
     * @throws NullPointerException If reader is null.
     **/
    public ConfigurableJSONBuilder( Reader reader,
				    boolean caseSensitive,
				    JSONValueFactory factory
			       ) 
	throws NullPointerException {
	
	super( reader, caseSensitive );
	
	this.factory          = factory;
	
	this.valueStack       = new Stack<JSONValue>();
	this.memberNameStack  = new Stack<String>();
	this.arrayIndexStack  = new Stack<Integer>();
	
	this.valueStack.push( new JSONNull() ); 
	this.memberNameStack.push( null );
	this.arrayIndexStack.push( null );
    }    
    
    private JSONValue popFromStack() {

	// Remove from stack
	this.lastPoppedValue    = this.valueStack.pop();
	this.currentMemberName  = this.memberNameStack.pop();
	this.currentArrayIndex  = this.arrayIndexStack.pop();
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
	JSONArray arr = null;
	if( this.currentMemberName != null )
	    arr = this.factory.createArray( this.currentMemberName );
	else if( this.currentArrayIndex != null )
	    arr = this.factory.createArray( this.currentArrayIndex );
	else
	    arr = this.factory.createArray();
	this.valueStack.push( arr ); // new JSONArray( new LinkedList<JSONValue>() ) );
	this.memberNameStack.push( this.currentMemberName );  
	
	this.currentArrayIndex = new Integer(0);
	this.arrayIndexStack.push( this.currentArrayIndex );
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
	JSONObject obj = null;
	if( this.currentMemberName != null )
	    obj = this.factory.createObject( this.currentMemberName );
	else if( this.currentArrayIndex != null )
	    obj = this.factory.createObject( this.currentArrayIndex );
	else
	    obj = this.factory.createObject();

	this.valueStack.push( obj ); // new JSONObject( new TreeMap<String,JSONValue>() ) );
	this.memberNameStack.push( this.currentMemberName );
	this.arrayIndexStack.push( this.currentArrayIndex );
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

	    // May throw NumberFormatException
	    Number num =  JSONNumber.parseNumber(number);

	    if( this.currentMemberName != null )
		currentMemberValue = this.factory.createNumber( num, this.currentMemberName );
	    else if( this.currentArrayIndex != null )
		this.currentMemberValue = this.factory.createNumber( num, this.currentArrayIndex );
	    else
		this.currentMemberValue = this.factory.createNumber( num );

	} catch( NumberFormatException e ) {
	    // Store as a string??? Throw exception??? 
	    // Actually this means there is a type error in the parser, so this excption should NEVER occur
	    throw new RuntimeException( "Ooops, this number is invalid: " + number );
	    //this.currentMemberValue = new JSONString( number );
	}
	
    }

    @Override
    protected void fireStringRead( String token ) {
	if( this.currentMemberName != null )
	    this.currentMemberValue = this.factory.createString( token, this.currentMemberName );
	else if( this.currentArrayIndex != null )
	    this.currentMemberValue = this.factory.createString( token, this.currentArrayIndex );
	else
	    this.currentMemberValue = this.factory.createString( token );
    }
    
    @Override
    protected void fireTrueRead( String value ) {
	if( this.currentMemberName != null )
	    currentMemberValue = this.factory.createBoolean( new Boolean(true), this.currentMemberName );
	else if( this.currentArrayIndex != null )
	    this.currentMemberValue = this.factory.createBoolean( new Boolean(true), this.currentArrayIndex );
	else
	    this.currentMemberValue = this.factory.createBoolean( new Boolean(true) );
	// this.currentMemberValue = new JSONBoolean( new Boolean(true) );  // Also store raw value?
    }

    @Override
    protected void fireFalseRead( String value ) {
	if( this.currentMemberName != null )
	    currentMemberValue = this.factory.createBoolean( new Boolean(false), this.currentMemberName );
	else if( this.currentArrayIndex != null )
	    this.currentMemberValue = this.factory.createBoolean( new Boolean(false), this.currentArrayIndex );
	else
	    this.currentMemberValue = this.factory.createBoolean( new Boolean(false) );
	// this.currentMemberValue = new JSONBoolean( new Boolean(false) ); // Also store raw value?
    }

    @Override
    protected void fireNullRead( String value ) {
	if( this.currentMemberName != null )
	    currentMemberValue = this.factory.createNull( this.currentMemberName );
	else if( this.currentArrayIndex != null )
	    this.currentMemberValue = this.factory.createNull( this.currentArrayIndex );
	else
	    this.currentMemberValue = this.factory.createNull();
	//this.currentMemberValue = new JSONNull();                        // Also store raw value?
    }
    //--- END ----------- Override parser event methods ---------- //
   
    public JSONValue getResult() {
	return this.lastPoppedValue;
    }

    /**
     * For testing purposes only.
     **/
    public static void main( String[] argv ) {

	if( argv.length == 0 ) {
	    System.err.println( "Please pass a JSON input file name." );
	    System.exit( 1 );
	}

	try {

	    System.out.println( "Creating JSONValueFactory ... ");
	    JSONValueFactory factory = new DefaultJSONValueFactory();	    
	    System.out.println( "Initialising reader ... " );
	    Reader reader = new java.io.FileReader( argv[0] );
	    System.out.println( "Initialising parser/builder ... " );
	    ConfigurableJSONBuilder b  = new ConfigurableJSONBuilder( reader, false, factory );
	    System.out.println( "Starting the parser ..." );
	    long time_start = System.currentTimeMillis();
	    b.parse();
	    long time_end   = System.currentTimeMillis();
	    System.out.println( "Closing the reader ..." );
	    reader.close();
	    System.out.println( "Done." );
	    System.out.println( "Parsing and building took " + (time_end - time_start) + " ms." );

	    JSONValue json = b.lastPoppedValue;
	    System.out.println( "JSON object: " + json.toString() );

	} catch( Exception e ) {
	    e.printStackTrace();
	}

    }
}
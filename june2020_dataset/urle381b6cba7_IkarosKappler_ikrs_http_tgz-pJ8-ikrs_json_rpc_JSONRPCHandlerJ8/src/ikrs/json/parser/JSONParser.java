package ikrs.json.parser;

/**
 * This is a simple JSON parser.
 *
 * It refers to RFC 4627.
 *
 *
 * @author Ikaros kappler
 * @date 2013-05-29
 * @modified 2013-05-31 Ikaros Kappler (fixed the nested array issue).
 * @version 1.0.1
 **/

import java.io.IOException;
import java.io.Reader;

import ikrs.json.JSONException;

public class JSONParser {

    /**
     * The underlying reader to read from.
     **/
    private Reader reader;

    /**
     * The token that was just read from the reader (char value).
     **/
    private char currentChar;
    
    /**
     * The token that was just read from the reader (int value).
     **/
    private int currentValue;

    /**
     * My read position begins at 0.
     **/
    private int readPosition;
    
    /**
     * My line numbers begin at 0.
     **/
    private int lineNumber; 
    
    /**
     * My column numbers begin at 0.
     **/
    private int columnNumber;

    /**
     * This flag indicates if JSON names should be matched case sensitive or not.
     **/
    private boolean caseSensitive;


    /**
     * Creates a new JSONParser (in case-insensitive mode).
     *
     * @param reader The reader to read tokens from.
     * @throws NullPointerException If the passed reader is null.
     **/
    public JSONParser( Reader reader ) 
	throws NullPointerException {
	
	this( reader, false );
    }

    /**
     * Creates a new JSONParser.
     *
     * @param reader The reader to read tokens from.
     * @throws NullPointerException If the passed reader is null.
     **/
    public JSONParser( Reader reader,
		       boolean caseSensitive 
		   ) 
	throws NullPointerException {

	super();

	if( reader == null )
	    throw new NullPointerException( "Cannt create a new JSON parser with null reader." );

	this.reader        = reader;
	this.caseSensitive = caseSensitive;
	this.readPosition  = 0;
	this.lineNumber    = 0;
	this.columnNumber  = 0;
    }
    
    //--- BEGIN --------- These methods are meant to be overridden by subclasses ---------- //
    protected void fireJSONBegin() {
	System.out.println( "### BEGIN JSON" );
    }

    protected void fireJSONEnd() {
	System.out.println( "### END JSON" );
    }
    
    protected void fireArrayBegin() {
	System.out.println( "--- Array BEGIN" );
    }

    protected void fireArrayElementEnd() {
	System.out.println( "--- Array element End" );
    }
    
    protected void fireArrayEnd() {
	System.out.println( "--- Array END" );
    }

    protected void fireObjectBegin() {
	System.out.println( "--- Object BEGIN" );
    }

    protected void fireObjectEnd() {
	System.out.println( "--- Object END" );
    }

    protected void fireMemberBegin() {
	System.out.println( "--- Member BEGIN" );
    }

    protected void fireMemberNameRead( String token ) {
	System.out.println( "JSON member name read: " + token );
    }

    protected void fireMemberEnd() {
	System.out.println( "--- Member END" );
    }

    protected void fireNumberRead( String number ) {
	System.out.println( "JSON number read: " + number );
    }

    protected void fireStringRead( String token ) {
	System.out.println( "JSON string read: " + token );
    }
    
    protected void fireTrueRead( String value ) {
	System.out.println( "JSON true read: " + value );
    }

    protected void fireFalseRead( String value ) {
	System.out.println( "JSON false read: " + value );
    }

    protected void fireNullRead( String value ) {
	System.out.println( "JSON null read: " + value );
    }
    //--- END ----------- These methods are meant to be overridden by subclasses ---------- //

    /**
     * Get the parser's current read position inside the input stream.
     *
     * @return The byte offset inside the stream.
     **/
    public int getReadPosition() {
	return this.readPosition;
    }

    /**
     * Get the parser's current line number.
     * The initial line number is 0.
     *
     * @return The parser's current line number.
     **/
    public int getLineNumber() {
	return this.lineNumber;
    }

    /**
     * Get the parser's current column number.
     * The initial column number is 0.
     *
     * @return The parser's current column number.
     **/
    public int getColumnNumber() {
	return this.columnNumber;
    }

    /**
     * Start the parser.
     *
     * @return True if and only if the next JSON value was successfully read
     *         from the underlying stream without reaching EOI before completion.
     * @throws IOException If any IO errors occur.
     * @throws JSONException If the JSON code is not valid. The thrown exception
     *                       contains the error offset and line- and column number.
     **/
    public boolean parse()
	throws IOException,
	       JSONSyntaxException {

	this.fireJSONBegin();
	boolean jsonValueRead = readJSON( false,  // no object end expected
					  false   // no array end expected
					  );
	if( jsonValueRead )
	    fireJSONEnd();
	
	return jsonValueRead;
    }
    
    /**
     * A default method to throw a customized JSONException that contains the
     * current error offset (and line- and column number).
     **/
    private void throwJSONException( String message ) 
	throws JSONSyntaxException {
	throw new JSONSyntaxException( message, this.readPosition, this.lineNumber, this.columnNumber );
    }

    /**
     * Read the next JSON value from the underlying reader.
     *
     * Specified in RFC 4627 a JSON value is:
     *   value = false / null / true / object / array / number / string
     *
     * As this is a recursive parser the method expects two arguments that
     * indicate if the parser just processes an object or an array (or neither).#
     *
     * @param objectEndExpected If set to true the method will raise NO exception
     *                          if the end-of-object ('}') is read.
     * @param arrayEndExpected  If set to true the method will raise NO exception
     *                          if the end-of-array (']') is read.
     *
     * @return true if and only if EOI was not reached before completing the
     *         next JSON value.
     **/
    private boolean readJSON( boolean objectEndExpected, boolean arrayEndExpected ) 
	throws IOException,
	       JSONSyntaxException {
	
	if( !this.skipWhitespace() )
	    return false;
	
	StringBuffer b = null;
	if( this.isDigit(this.currentChar) ) {
	    b = new StringBuffer();
	    b.append( this.currentChar );
	    return this.readNumber( true, b );  // fire event, is not negative
	} else {
	    switch( this.currentChar ) {
	    case '[':
		this.fireArrayBegin(); 
		return readArray();
	    case '{':
		this.fireObjectBegin(); 
		return readObject();
	    case 'f': 
		b = new StringBuffer();
		b.append( this.currentChar );
		return readFalse( b );
	    case 't':
		b = new StringBuffer();
		b.append( this.currentChar );
		return readTrue( b );
	    case 'n': 
		b = new StringBuffer();
		b.append( this.currentChar );
		return readNull( b );
	    case '-': 
		b = new StringBuffer();
		b.append( this.currentChar );
		return readNumber( true, b ); // fire event
	    case '.': 
		b = new StringBuffer();
		b.append( this.currentChar );
		return readFloatingNumberFrac( true, b );      // fire event
	    case '"': 
		return readString( true, new StringBuffer() ); // true -> fire event
	    case '\'': 
		return readString( true, new StringBuffer() ); // single quote support is not required by RFC 4627
	    default: 
		// If configured case-INsensitive try to match constant tokens again.
		if( !this.caseSensitive ) {
		    switch( this.currentChar ) {
		    case 'F': 
			b = new StringBuffer();		
			b.append( this.currentChar );
			return readFalse( b );
		    case 'T': 
			b = new StringBuffer();		
			b.append( this.currentChar );
			return readTrue( b );
		    case 'N': 
			b = new StringBuffer();
			b.append( this.currentChar );
			return readNull( b );
		    default: ;
		    }
		}
		if( (objectEndExpected && this.currentChar != '}') ||
		    (arrayEndExpected && this.currentChar != ']') )
		    throwJSONException( "Unexpected token: " + this.describeCurrentToken()  + ". Expected Array, object or basic value." );
	    }
	}
	    
	return 
	    !(
	      (objectEndExpected && this.currentChar == '}') 
	      || 
	      (arrayEndExpected && this.currentChar == ']') // true;
	      );
    }

    /**
     * This method expects the first digit of the next number already to
     * be read. The digit must be stored inside the string buffer.
     *
     * @param fireEvent Nested readNumber()-calls (such as in 
     *                  readFloatingNumberFrac()) may want to suppress the
     *                  method to raise an event.
     * @param b         The string buffer that contains the prefix of the
     *                  number that was previously read ('-', first digit,
     *                  leading floating number part).
     * @return true if and only if the number was completely read without
     *         reaching EOI (but EOI may be the next byte, of course).
     * @throws IOException If any IO error occurs.
     * @throws JSONException If the parser detects a JSON syntax error.
     **/
    private boolean readNumber( boolean fireEvent, StringBuffer b  ) 
	throws IOException,
	       JSONSyntaxException {

	// Detect any sequence of digits
	while( this.readChar() && this.isDigit(this.currentChar) )
	    b.append( this.currentChar );
	
	// Floating point following?
	if( this.currentChar == '.' ) {

	    b.append( this.currentChar );
	    this.readFloatingNumberFrac( false, b );

	} 

	if( fireEvent ) 
	    this.fireNumberRead( b.toString() );
	
	
	return (this.currentValue != -1);
    }
    
    /**
     * This method reads the floating number frac part.
     *
     * Since RFC 4627 a (floating) number is:
     *     number = [ minus ] int [ frac ] [ exp ]
     *
     * @param fireEvent If set to false the method will NOT raise an event.
     * @param b         The string buffer containing the leading number part (the
     *                  part that was previously read).
     * @return true if and only if the floating number frac part was completely
     *         read without reaching EOI before.
     * @throws IOException If any IO error occurs.
     * @throws JSONException If the parser detects a JSON syntax error.
     **/
    private boolean readFloatingNumberFrac( boolean fireEvent, StringBuffer b ) 
	throws IOException,
	       JSONSyntaxException {
	
	// System.out.println( "Read frac: " + b.toString() );
	// Read frac
	while( this.readChar() && this.isDigit(this.currentChar) )
	    b.append( this.currentChar );
	
	// exp following?
	if( this.currentChar == 'e'        
	    || this.currentChar == 'E'     
	    ) {

	    b.append( this.currentChar );
	    this.readChar();
	    
	    // plus or minus read?
	    if( this.currentChar == '-' || this.currentChar == '+' ) {
		b.append( this.currentChar );
		this.readChar();
	    }

	    // At least one digit MUST exist
	    if( !this.isDigit(this.currentChar) )
		throwJSONException( "Unexpected token: " + this.describeCurrentToken() + ". DIGIT expected." );
	    
	    // Read all following digits
	    do {
		b.append( this.currentChar );
	    } while( this.readChar() && this.isDigit(this.currentChar) );
		

	}
	
	if( fireEvent )
	    this.fireNumberRead( b.toString() );
	
	return (this.currentValue != -1);
    }

    /**
     * Reads the next JSON array from the reader. The method expected the
     * current token to be begin-of-array ('[').
     *
     * @return true if and only if the array was completely
     *         read without reaching EOI before.
     * @throws IOException If any IO error occurs.
     * @throws JSONException If the parser detects a JSON syntax error.
     **/
    private boolean readArray()
	throws IOException,
	       JSONSyntaxException {

	// Mind the case of an empty array: []
	/*if( this.currentChar == ']' ) {
	    this.fireArrayEnd();
	    return true;
	    }*/

	// while( this.readJSON(false,true) && this.currentChar != ']' ) {
	boolean stop = false;
	do {
	    
	    boolean jsonValueRead = this.readJSON(false,true);
	    stop = (!jsonValueRead || this.currentChar == ']');
	    if( jsonValueRead )
		fireArrayElementEnd();
	    
	    if( !stop ) {
		// array elements are separated by ','
		if( this.currentChar != ',' )
		    this.skipWhitespace();
		if( this.currentChar == ']' ) {
		    this.fireArrayEnd();
		    this.readChar();
		    return true;
		}
		if( this.currentChar != ',' )
		    throwJSONException( "Unexpected token: " + this.describeCurrentToken() + ". Failed to read array's value or end of array (expected ',')." );
	    }
	} while( !stop );

	if( this.currentChar == ']' ) {
	    this.fireArrayEnd();
	    this.readChar();
	}

	return (this.currentValue != -1);
    }


    /**
     * Reads the next JSON object from the reader. The method expected the
     * current token to be begin-of-object ('{').
     *
     * @return true if and only if the object was completely
     *         read without reaching EOI before.
     * @throws IOException If any IO error occurs.
     * @throws JSONException If the parser detects a JSON syntax error.
     **/
    private boolean readObject()
	throws IOException,
	       JSONSyntaxException {

	// Mind the case of an empty object: {}
	if( this.currentChar == '}' ) {
	    this.fireObjectEnd();
	    return true;
	}
	
	while( this.readObjectMember() && this.currentChar != '}' ) {

	    
	    // object members are separated by ','
	    if( this.currentChar != ',' )
		this.skipWhitespace();
	    if( this.currentChar == '}' ) {
		this.readChar();
		this.fireObjectEnd();
		return true;
	    }
	    if( this.currentChar != ',' )
		throwJSONException( "Unexpected token: " + this.describeCurrentToken() + ". Failed to read object member's value (expected ',')." );
		
  
	} 
	
	if( this.currentValue == '}' ) {
	    this.readChar();
	    this.fireObjectEnd();
	}
	    
	return (this.currentValue != -1);
    }

    /**
     * Reads the next object member from the reader.
     *
     * @return true if and only if the member was completely
     *         read without reaching EOI before.
     * @throws IOException If any IO error occurs.
     * @throws JSONException If the parser detects a JSON syntax error.
     **/
    private boolean readObjectMember()
	throws IOException,
	       JSONSyntaxException {

	if( !this.skipWhitespace() )
	    throwJSONException( "Unexpected token: " + this.describeCurrentToken() + ". Expected object member or end of object '}'." );

	if( this.currentChar == '}' ) {
	    return false;
	}

	this.fireMemberBegin();

	//if( !this.skipWhitespace() )
	//    throwJSONException( "Unexpected token: " + this.describeCurrentToken() + ". Expected object member." );
	
	// Next token must be a quoted string! (RFC 4627 only expects double quoted strings)
	if( this.currentChar != '"' 
	    && this.currentChar != '\'' )
	    throwJSONException( "Unexpected token: " + this.describeCurrentToken() + ". Expected quoted string in object member." );
	StringBuffer nameBuffer = new StringBuffer();
	this.readString( false, nameBuffer );  // read the member's name, fire NO event here
	this.fireMemberNameRead( nameBuffer.toString() );
	
	// Read the name-separator ':'
	this.skipWhitespace();	
	if( this.currentChar != ':' )
	    throwJSONException( "Unexpected token: " + this.describeCurrentToken() + ". Name separator ':' expected." );
	
	// Read the member's value (will fire event)
	nameBuffer.delete( 0, nameBuffer.length()-1 );
	boolean success = this.readJSON(true,false);

	// Now tell observer the member ends here
	this.fireMemberEnd();

	return success;
    }

    /**
     * This method requires that the current char is " (double quotation mark) 
     * or ' (single quotation mark).
     *
     * @return true if and only if the string was completely
     *         read without reaching EOI before.
     * @throws IOException If any IO error occurs.
     * @throws JSONException If the parser detects a JSON syntax error.
     **/
    private boolean readString( boolean fireEvent, StringBuffer b ) 
	throws IOException,
	       JSONSyntaxException {

	// Current token should be '"' (due to RFC 4627),
	// but I will allow any quotation mark (double and single).
	char quotationMark = this.currentChar;
	boolean endReached = false;

	// Seek next quotation mark
	while( !this.readExpected(quotationMark,null) && !endReached ) {
	    
	    if( this.currentValue == -1 )
		throwJSONException( "Failed to read string. Reached EOI." );

	    // Try to read escaped: %x..
	    if( this.currentChar == '\\' ) {
		
		// 'normal' escape char
		if( !this.readChar() )
		    throwJSONException( "Unexpected token: " + this.describeCurrentToken() + ". Escape char expected." );
		if( this.currentChar == quotationMark ) {
		    b.append( '\\' );    // append the escape backslash itself
		    endReached = true;
		} else {
		    switch( this.currentChar ) {
		    case 'n': b.append( '\n' ); break;
		    case 'b': b.append( '\b' ); break;
		    case 't': b.append( '\t' ); break;
		    case 'f': b.append( '\f' ); break;
		    case 'r': b.append( '\r' ); break;
		    case '\\': b.append( '\\' ); break;
		    case '"': b.append( '"' ); break;
		    case '\'': b.append( '\'' ); break;
		    case 'u': 
			String hex = this.readEscapedUnicodeChar( b );
			// If hex is not null, it could not be un-escaped.
			if( hex != null ) {
			    if( hex.indexOf(quotationMark) != -1 )
				throwJSONException( "Illegal escape character: \\u" + hex + "." );
			    b.append( "\\u" ).append( hex ); 
			} // END if
			break;
		    default: 
			// Anything unknown (do not un-escape and ignore)
			b.append( "\\" ).append( this.currentChar ); break;
		    } // END switch
		} // END else
	    } // END elseif [currentChar == '\\']

	    b.append( this.currentChar );
	    
	    //lastChar = this.currentChar;
	}
	
	if( fireEvent ) 
	    this.fireStringRead( b.toString() );
	
	return (this.currentValue != -1);
    }

    /**
     * This method reads four characters from the input and
     * tries to convert them into an integer (base 16).
     *
     * If this fails (due to non-hex symbols), the method
     * returns the read string (four characters) and does not
     * append anything to the string buffer.
     *
     * If this succeeds, the value will be converted into a
     * character which will be appended to the string buffer.
     * The method returns null (!) then.
     **/
    private String readEscapedUnicodeChar( StringBuffer b ) 
	throws IOException {

	// Concatenate at least the next four chars
	String hex = this.readNChars( 4 );
	try {
	    // Convert to number (base 16)
	    int value  = Integer.parseInt( hex, 16 );
	    // And now convert it to a character
	    b.append( (char)value );
	    return null;
	} catch( NumberFormatException e ) {
	    return hex;
	}

    }

    /**
     * This method reads 'true' from the reader.
     *
     * @param b A string buffer to store the read characters in; it should already contain the current char
     * @return true if and only if 'true' was completely
     *         read without reaching EOI before.
     * @throws IOException If any IO error occurs.
     * @throws JSONException If the parser detects a JSON syntax error.
     **/
    private boolean readTrue( StringBuffer b )
	throws IOException,
	       JSONSyntaxException {

	if( !readExpected('r',b) || !readExpected('u',b) || !readExpected('e',b) )
	    throwJSONException( "Unexpected token: " + this.describeCurrentToken() + "; expected true." );
	
	this.fireTrueRead( b.toString() );

	return true;
    }

    /**
     * This method reads 'false' from the reader.
     *
     * @param b A string buffer to store the read characters in; it should already contain the current char
     * @return true if and only if 'false' was completely
     *         read without reaching EOI before.
     * @throws IOException If any IO error occurs.
     * @throws JSONException If the parser detects a JSON syntax error.
     **/
    private boolean readFalse( StringBuffer b )
	throws IOException,
	       JSONSyntaxException {

	if( !readExpected('a',b) || !readExpected('l',b) || !readExpected('s',b) || !readExpected('e',b) )
	    throwJSONException( "Unexpected token: " + this.describeCurrentToken() + "; expected false." );

	this.fireFalseRead( b.toString() );
	
	return true;
    }

    /**
     * This method reads 'null' from the reader.
     *
     * @param b A string buffer to store the read characters in; it should already contain the current char.
     * @return true if and only if 'null' was completely
     *         read without reaching EOI before.
     * @throws IOException If any IO error occurs.
     * @throws JSONException If the parser detects a JSON syntax error.
     **/
    private boolean readNull( StringBuffer b )
	throws IOException,
	       JSONSyntaxException {

	if( !readExpected('u',b) || !readExpected('l',b) || !readExpected('l',b) )
	    throwJSONException( "Unexpected token: " + this.describeCurrentToken() + "; expected null." );

	this.fireNullRead( b.toString() );
	
	return true;
    }
	
    /**
     * This method consumes all white space characters until non-whitespace or EOI is reached.
     *
     * @return true if and only if EOI was not reached.
     * @throws IOException If any IO error occur.
     **/
    private boolean skipWhitespace() 
	throws IOException {
	
	while( this.readChar() && this.isWhitespace(this.currentValue) )
	    ;
	
	return (this.currentValue != -1);
    }

    /**
     * This method simply reads the next character from the reader.
     *
     * Additionally it updates the read positions.
     *
     * @return true if and only if EOI was not reached.
     * @throws IOException If any IO error occur.
     **/
    private boolean readChar()
	throws IOException {
    
	this.currentValue = this.reader.read();
	this.currentChar  = (char)this.currentValue;
	
	// Update read positions
	this.readPosition++;
	if( this.currentChar == 0x0A ) { // line feed character
	    this.lineNumber++;
	    this.columnNumber = 0;
	} else {
	    this.columnNumber++;
	}

	return (this.currentValue != -1);
    }

    /**
     * This method reads up to n character from the reader and returns
     * them as a string.
     *
     * If EOI is reached before n chars were read, the returned string will be shorter than n.
     *
     * @param n The number of characters to read.
     * @return The read characters in a string.
     * @throws IOException If any IO errors occur.
     **/
    private String readNChars( int n ) 
	throws IOException {

	if( n <= 0 )
	    return "";

	char[] buf = new char[n];
	int i = 0;
	for( ; this.readChar() && i < n; i++ ) {	    
	    buf[i] = this.currentChar;
	}
	
	return new String( buf, 0, i );
    }
    
    /**
     * This method reads one character from the reader and compares it
     * with the passed template. Only if the new character and the template
     * match the method returns true.
     *
     * As the parser supports case-INsensitive processing, the match method
     * is capable to ignore the case. For this purpose the read character is
     * stored inside the string buffer (if not null).
     *
     * @param expected The expected character.
     * @param b The string buffer to store the read character in. You may pass null.
     * @return true if and only if the passed character and the next character
     *         match.
     * @throws IOException If any IO errors occur.
     **/
    private boolean readExpected( char expected,
				  StringBuffer b
				  )
	throws IOException {

	if( !readChar() )
	    return false;
	
	boolean matches =
	    ( this.currentChar == expected ||
	      ( !this.caseSensitive 
		&& Character.toLowerCase(this.currentChar) == Character.toLowerCase(expected) 
		)
	      );

	if( matches && b != null )
	    b.append( this.currentChar );

	return matches;
    }

    /**
     * A JSON compatible whitespace check.
     *
     * As described in RFC 4627 whitespace is:
     *  ws = *(
     *           %x20 /              ; Space
     *           %x09 /              ; Horizontal tab
     *           %x0A /              ; Line feed or New line
     *           %x0D                ; Carriage return
     *       )
     **/
    private boolean isWhitespace( int v ) {
	return ( v == 0x20 || v == 0x09 || v == 0x0A || v == 0x0D );
    }

    /**
     * A JSON compatible digit check (will check the current character).
     **/
    private boolean isDigit() {
	return this.isDigit( this.currentChar );
    }
    
    /**
     * A JSON compatible digit check.
     **/
    private boolean isDigit( char c ) {
	// The ASCII code of '0' is 30(hex)
	// the ASCII code of '9' is 39(hex)
	// return (c >= 0x30 && c <= 0x39);

	// This is a much better way
	return Character.isDigit( c );
    }
    
    /**
     * A small helper method to create nice error messages; the returned string
     * is a short description of the current token.
     **/
    private String describeCurrentToken() {
	if( this.currentValue == -1 )
	    return "EOI";
	else
	    return "'" + this.currentChar + "'";
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

	    System.out.println( "Initialising reader ... " );
	    Reader reader = new java.io.FileReader( argv[0] );
	    System.out.println( "Initialising parser ... " );
	    JSONParser p  = new JSONParser( reader );
	    System.out.println( "Starting the parser ..." );
	    p.parse();
	    System.out.println( "Closing the reader ..." );
	    reader.close();
	    System.out.println( "Done." );

	} catch( Exception e ) {
	    e.printStackTrace();
	}

    }
}
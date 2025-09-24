package ikrs.typesystem;

/**
 * @author Ikaros Kappler
 * @date 2012-04-22
 * @version 1.0.0
 **/

import java.util.UUID;


public class BasicStringType
    extends BasicTypeAdapter
    implements BasicType {
	
    private String string;
   
    private BasicNumberType parsedNumberType;
    private BasicType parsedUUIDType;
    private BasicType parsedBooleanType;
    

    public BasicStringType( String str ) {
	super( BasicType.TYPE_STRING );

	this.string = str;

	if( str == null )
	    return;

	// Try to parse number values
	if( !parseIntegerType(str) && !parseDoubleType(str) )
	    ;
	if( !parseBooleanType(str) )
	    ;
	if( !parseUUIDType(str) )
	    ;

    }

    private boolean parseIntegerType( String str ) {
	try {

	    int tmpInt = Integer.parseInt( str );
	    this.parsedNumberType = new BasicNumberType( tmpInt );
	    return true;

	} catch( NumberFormatException e ) {

	    return false;
	    
	}
	
    }

    private boolean parseDoubleType( String str ) {
	try {

	    double tmpDouble = Double.parseDouble( str );
	    this.parsedNumberType = new BasicNumberType( tmpDouble );
	    return true;

	} catch( NumberFormatException e ) {

	    return false;
	    
	}
	
    }

    private boolean parseUUIDType( String str ) {
	try {
	    this.parsedUUIDType = new BasicUUIDType( UUID.fromString(str) );
	    //System.out.println( "UUID parsed: "+this.parsedUUIDType );
	    return false;
	} catch( IllegalArgumentException e ) {
	    //System.out.println( "Could not parse UUID from '"+str+"': "+e.getMessage() );
	    return false;
	}
    }

    private boolean parseBooleanType( String str ) {
	if( str == null )
	    return false;

	str = str.toUpperCase();
	if( str.equals("TRUE") || str.equals("YES") || str.equals("ON") || str.equals("HI") || str.equals("HIGH") ) {
	    
	    this.parsedBooleanType = new BasicBooleanType( true );
	    return true;
	    
	} else if( str.equals("FALSE") || str.equals("NO") || str.equals("OFF") || str.equals("LO") || str.equals("LOW") ) {
	    
	    this.parsedBooleanType = new BasicBooleanType( false );
	    return true;
	    
	} else {

	    return false;

	}

	
    }


    public boolean getBoolean()
	throws BasicTypeException {

	if( this.parsedBooleanType != null )
	    return this.parsedBooleanType.getBoolean();
	else if( this.parsedNumberType != null )
	    return this.parsedNumberType.getBoolean();
	else
	    return super.getBoolean();
    }
    
    public byte getByte()
	throws BasicTypeException {

	if( this.parsedNumberType != null )
	    return this.parsedNumberType.getByte();
	else if( this.parsedBooleanType != null )
	    return this.parsedBooleanType.getByte();
	else
	    return super.getByte();
    }

    public short getShort()
	throws BasicTypeException {

	if( this.parsedNumberType != null )
	    return this.parsedNumberType.getShort();
	else if( this.parsedBooleanType != null )
	    return this.parsedBooleanType.getShort();
	else
	    return super.getShort();
    }

    public int getInt()
	throws BasicTypeException {

	if( this.parsedNumberType != null )
	    return this.parsedNumberType.getInt();
	else if( this.parsedBooleanType != null )
	    return this.parsedBooleanType.getInt();
	else
	    return super.getInt();
    }

    public long getLong()
	throws BasicTypeException {

	if( this.parsedNumberType != null )
	    return this.parsedNumberType.getLong();
	else if( this.parsedBooleanType != null )
	    return this.parsedBooleanType.getLong();
	else
	    return super.getLong();
    }
    
    public float getFloat()
	throws BasicTypeException {

	if( this.parsedNumberType != null )
	    return this.parsedNumberType.getFloat();
	else if( this.parsedBooleanType != null )
	    return this.parsedBooleanType.getFloat();
	else
	    return super.getFloat();
    }

    public double getDouble()
	throws BasicTypeException {
	
	if( this.parsedNumberType != null )
	    return this.parsedNumberType.getDouble();
	else if( this.parsedBooleanType != null )
	    return this.parsedBooleanType.getDouble();
	else
	    return super.getDouble();
    }

    public char getChar()
	throws BasicTypeException {

	if( this.string != null && this.string.length() == 1 )
	    return this.string.charAt( 0 );
	//else if( this.parsedBooleanType != null )
	//    return this.parsedBooleanType.getChar();
	else
	    return super.getChar();
    }

    public String getString() 
	throws BasicTypeException {
	
	return this.string;
    }
    
    public UUID getUUID()
	throws BasicTypeException {

	if( this.parsedUUIDType != null )
	    return this.parsedUUIDType.getUUID();
	else
	    return super.getUUID();
    }


    public String toString() {
	return this.string;
    }

    /**
     * This static method can be used to convert an array of strings into
     * an array of BasicStringType.
     **/
    public static BasicStringType[] buildArray( String[] params ) {
	if( params == null )
	    return null;

	return buildArray( params, 0, params.length );
    }

    /**
     * This static method can be used to convert an array of strings into
     * an array of BasicStringType.
     *
     * Additionally it accepts some bounds for the array:
     * @param start The left bounds (inclusive).
     * @param end The right bound (exclusive).
     **/
    public static BasicStringType[] buildArray( String[] params,
						int start,
						int end ) {
	if( params == null )
	    return null;

	start = Math.max( start, 0 );
	end = Math.min( end, params.length );

	BasicStringType[] t = new BasicStringType[ end-start ];
	for( int i = start; i < end; i++ ) {
	    
	    if( params[i] == null )
		continue;

	    t[i-start] = new BasicStringType( params[i] );
	}
	
	return t;
    }
}
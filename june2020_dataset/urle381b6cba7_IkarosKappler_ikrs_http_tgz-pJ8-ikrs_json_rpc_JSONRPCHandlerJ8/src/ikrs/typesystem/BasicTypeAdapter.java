package ikrs.typesystem;

import java.util.UUID;

/**
 * @author Ikaros Kappler
 * @date 2012-04-22
 * @version 1.0.0
 **/


public abstract class BasicTypeAdapter
    implements BasicType {

    private int type;
    

    public BasicTypeAdapter( int type ) {
	super();
	
	this.type = type;
    }
    

    public int getType() {
	return this.type;
    }

	
    private void throwDefaultAdapterException() 
	throws BasicTypeException {

	throw new BasicTypeException( "This type is not compatible." );
    }

    public boolean getBoolean()
	throws BasicTypeException {

	throwDefaultAdapterException();
	return false;
    }

    public boolean getBoolean( boolean defaultValue ) {
	try {
	    return getBoolean();
	} catch( BasicTypeException e ) {
	    return defaultValue;
	}
    }

    public byte getByte()
	throws BasicTypeException {
	
	throwDefaultAdapterException();
	return 0;
    }

    public byte getByte( byte defaultValue ) {
	try {
	    return getByte();
	} catch( BasicTypeException e ) {
	    return defaultValue;
	}
    }

    public short getShort()
	throws BasicTypeException {
	
	throwDefaultAdapterException();
	return 0;
    }

    public short getShort( short defaultValue ) {
	try {
	    return getShort();
	} catch( BasicTypeException e ) {
	    return defaultValue;
	}
    }

    public int getInt()
	throws BasicTypeException {
	
	throwDefaultAdapterException();
	return 0;
    }

    public int getInt( int defaultValue ) {
	try {
	    return getInt();
	} catch( BasicTypeException e ) {
	    return defaultValue;
	}	
    }

    public long getLong()
	throws BasicTypeException {
	
	throwDefaultAdapterException();
	return 0L;
    }

    public long getLong( long defaultValue ) {
	try {
	    return getLong();
	} catch( BasicTypeException e ) {
	    return defaultValue;
	}
    }
    
    public float getFloat()
	throws BasicTypeException {
	
	throwDefaultAdapterException();
	return 0.0f;
    }

    public float getFloat( float defaultValue ) {
	try {
	    return getFloat();
	} catch( BasicTypeException e ) {
	    return defaultValue;
	}
    }

    public double getDouble()
	throws BasicTypeException {
	
	throwDefaultAdapterException();
	return 0.0;
    }

    public double getDouble( double defaultValue ) {
	try {
	    return getDouble();
	} catch( BasicTypeException e ) {
	    return defaultValue;
	}
    }

    public char getChar()
	throws BasicTypeException {
	
	throwDefaultAdapterException();
	return '0';
    }

    public char getChar( char defaultValue ) {
	try {
	    return getChar();
	} catch( BasicTypeException e ) {
	    return defaultValue;
	}
    }

    public String getString() 
	throws BasicTypeException {
	
	throwDefaultAdapterException();
	return null;
    }

    public String getString( String defaultValue ) {
	try {
	    return getString();
	} catch( BasicTypeException e ) {
	    return defaultValue;
	}
    }

    public UUID getUUID()
	throws BasicTypeException {
	
	throwDefaultAdapterException();
	return null;
    }

    public UUID getUUID( UUID defaultValue ) {
	try {
	    return getUUID();
	} catch( BasicTypeException e ) {
	    return defaultValue;
	}
    }

    /**
     * The method is more or less for implicit type casting.
     * It must return this object itself with the dynamic type 'BasicArrayType'.
     *
     * If this object is NOT a BasicArrayType, it MUST throw a BasicTypeException.
     **/
    public BasicArrayType getArray()
	throws BasicTypeException {

	throwDefaultAdapterException();
	return null;
    }

    /**
     * The method is more or less for implicit type casting.
     * It must return this object itself with the dynamic type 'BasicArrayType'.
     *
     * If this object is NOT a BasicArrayType, it MUST return the passed defaultValue.
     **/
    public BasicArrayType getArray( BasicArrayType defaultValue ) {
	try {
	    return getArray();
	} catch( BasicTypeException e ) {
	    return defaultValue;
	}
    }
    


    public int getArraySize()
	throws BasicTypeException {

	throwDefaultAdapterException();
	return -1;
    }

    public BasicType getArrayElementAt( int index ) 
	throws BasicTypeException,
	       ArrayIndexOutOfBoundsException {

	throwDefaultAdapterException();
	return null;
    }

    /*
    public BasicType setArrayElementAt( int index,
					BasicType item )
	throws BasicTypeException,
	       ArrayIndexOutOfBoundsException {

	throwDefaultAdapterException();
	return null;
    }

    public void addArrayElement( BasicType item )
	throws BasicTypeException {

	throwDefaultAdapterException();
    }
    */



    public boolean equals( BasicType t ) {
	if( this.getType() == TYPE_BOOLEAN ) {

	    try { return this.equals(t.getBoolean()); }
	    catch( BasicTypeException e ) { return false; }

	} else if( this.getType() == TYPE_BYTE ) {

	    try { return this.equals(t.getByte()); }
	    catch( BasicTypeException e ) { return false; }

	} else if( this.getType() == TYPE_SHORT ) {

	    try { return this.equals(t.getShort()); }
	    catch( BasicTypeException e ) { return false; }

	} else if( this.getType() == TYPE_INT ) {

	    try { return this.equals(t.getInt()); }
	    catch( BasicTypeException e ) { return false; }

	} else if( this.getType() == TYPE_LONG ) {

	    try { return this.equals(t.getLong()); }
	    catch( BasicTypeException e ) { return false; }

	} else if( this.getType() == TYPE_FLOAT ) {

	    try { return this.equals(t.getFloat()); }
	    catch( BasicTypeException e ) { return false; }

	} else if( this.getType() == TYPE_DOUBLE ) {

	    try { return this.equals(t.getDouble()); }
	    catch( BasicTypeException e ) { return false; }

	} else if( this.getType() == TYPE_CHAR ) {

	    try { return this.equals(t.getChar()); }
	    catch( BasicTypeException e ) { return false; }

	} else if( this.getType() == TYPE_STRING ) {

	    try { return this.equals(t.getString()); }
	    catch( BasicTypeException e ) { return false; }

	} else if( this.getType() == TYPE_UUID ) {

	    try { return this.equals(t.getUUID()); }
	    catch( BasicTypeException e ) { return false; }

	} else if( this.getType() == TYPE_ARRAY ) {

	    try { return this.equals((BasicArrayType)t); }
	    catch( BasicTypeException e ) { return false; } 
	    catch( ClassCastException e ) { 
		return false;  // throw new RuntimeException( "Failed to compare two BasicArrayTypes: " + e.getMessage(), e );
	    }

	} else {
	    // INVALID TYPE IDENTIFIER???
	    return false;
	}
    }

    public boolean equals( boolean b ) {
	try { return ( this.getBoolean() == b ); }
	catch( BasicTypeException e ) { return false; }
    }

    public boolean equals( byte b ) {
	try { return ( this.getByte() == b ); }
	catch( BasicTypeException e ) { return false; }
    }

    public boolean equals( short s ) {
	try { return ( this.getShort() == s ); }
	catch( BasicTypeException e ) { return false; }
    }

    public boolean equals( int i ) {
	try { return ( this.getInt() == i ); }
	catch( BasicTypeException e ) { return false; }
    }

    public boolean equals( long l ) {
	try { return ( this.getLong() == l ); }
	catch( BasicTypeException e ) { return false; }
    }

    public boolean equals( float f ) {
	try { return ( this.getFloat() == f ); }
	catch( BasicTypeException e ) { return false; }
    }
    
    public boolean equals( double d ) {
	try { return ( this.getDouble() == d ); }
	catch( BasicTypeException e ) { return false; }
    }

    public boolean equals( char c ) {
	try { return ( this.getChar() == c ); }
	catch( BasicTypeException e ) { return false; }
    }

    public boolean equals( String s ) {
	try { 
	    String tmp = this.getString();
	    if( tmp == null && s != null )
		return false;
	    else if( tmp != null && s == null )
		return false;
	    else if( tmp == null && s == null )
		return true;
	    else
		return tmp.equals(s);
	} catch( BasicTypeException e ) { return false; }
    }

    public boolean equals( UUID id ) {
	try { 
	    UUID tmp = this.getUUID();
	    if( tmp == null && id != null )
		return false;
	    else if( tmp != null && id == null )
		return false;
	    else if( tmp == null && id == null )
		return true;
	    else
		return tmp.equals(id);
	} catch( BasicTypeException e ) { return false; }	
    }

    public boolean equals( BasicArrayType a ) {
	try { 
	    BasicArrayType tmp = (BasicArrayType)this;
	    if( tmp == null && a != null )
		return false;
	    else if( tmp != null && a == null )
		return false;
	    else if( tmp == null && a == null )
		return true;
	    else
		return tmp.equals(a);
	} 
	catch( BasicTypeException e ) { return false; }
	catch( ClassCastException e ) { return false; }
	
    }
}
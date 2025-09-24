package ikrs.typesystem;

/**
 * @author Ikaros Kappler
 * @date 2012-04-22
 * @version 1.0.0
 **/



public class BasicNumberType
    extends BasicTypeAdapter
    implements BasicType {
	
    private Number number;
   
    public BasicNumberType( byte b ) {
	super( BasicType.TYPE_BYTE );

	this.number = new Byte( b );
    }

    public BasicNumberType( short s ) {
	super( BasicType.TYPE_SHORT );

	this.number = new Short( s );
    }

    public BasicNumberType( int i ) {
	super( BasicType.TYPE_INT );

	this.number = new Integer( i );
    }

    public BasicNumberType( long l ) {
	super( BasicType.TYPE_LONG );

	this.number = new Long( l );
    }

    public BasicNumberType( float f ) {
	super( BasicType.TYPE_FLOAT );

	this.number = new Float( f );
    }

    public BasicNumberType( double d ) {
	super( BasicType.TYPE_DOUBLE );

	this.number = new Double( d );
    }
    
    public boolean getBoolean()
	throws BasicTypeException {

	return (this.number.doubleValue() != 0.0 );
    }

    public byte getByte() 
	throws BasicTypeException {

	return this.number.byteValue();
    }

    public short getShort() 
	throws BasicTypeException {

	return this.number.shortValue();
    }

    public int getInt() 
	throws BasicTypeException {

	return this.number.intValue();
    }
    
    public long getLong()
	throws BasicTypeException {

	return this.number.longValue();
    }
    
    public float getFloat()
	throws BasicTypeException {

	return this.number.floatValue();
    }

    public double getDouble()
	throws BasicTypeException {

	return this.number.doubleValue();
    }

    public String getString() 
	throws BasicTypeException {
	
	return this.number.toString();
    }

    public String toString() {
	return this.number.toString();
    }

}
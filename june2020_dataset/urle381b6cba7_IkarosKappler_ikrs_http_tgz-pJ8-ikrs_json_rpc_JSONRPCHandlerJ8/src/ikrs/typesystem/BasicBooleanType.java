package ikrs.typesystem;

/**
 * @author Ikaros Kappler
 * @date 2012-04-23
 * @version 1.0.0
 **/



public class BasicBooleanType
    extends BasicTypeAdapter
    implements BasicType {
	
    private boolean bool;
   
    public BasicBooleanType( boolean b ) {
	super( BasicType.TYPE_BOOLEAN );

	this.bool = b;
    }
    
    public boolean getBoolean()
	throws BasicTypeException {

	return this.bool;
    }

    public byte getByte() 
	throws BasicTypeException {

	return (byte)(this.bool ? 1 : 0 );
    }

    public short getShort() 
	throws BasicTypeException {

	return (short)( this.bool ? 1 : 0 );
    }

    public int getInt() 
	throws BasicTypeException {

	return ( this.bool ? 1 : 0 );
    }
    
    public long getLong()
	throws BasicTypeException {

	return ( this.bool ? 1L : 0L );
    }
    
    public float getFloat()
	throws BasicTypeException {

	return ( this.bool ? 1.0f : 0.0f );
    }

    public double getDouble()
	throws BasicTypeException {

	return ( this.bool ? 1.0 : 0.0 );
    }

    public String getString() {

	return Boolean.toString( this.bool );

    }


    public String toString() {
	return Boolean.toString( this.bool );
    }

}
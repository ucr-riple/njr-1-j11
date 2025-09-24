package ikrs.typesystem;

import java.util.ArrayList;

/**
 * The BasicArrayType is a not so basic type class with the capability to wrap a set to BasicType
 * instances together into a linear array-like list.
 *
 * All access methods guarantee constant runtime, so the class does not support item removal.
 *
 * THIS CLASS IS STILL EXPERIMENTAL.
 *
 * @author Ikaros Kappler
 * @date 2012-09-21
 * @version 1.0.0
 **/

public class BasicArrayType
    extends BasicTypeAdapter
    implements BasicType {
	
    private ArrayList<BasicType> items;
   
    public BasicArrayType() {
	super( BasicType.TYPE_ARRAY );

	this.items = new ArrayList<BasicType>(1);
    }

    /**
     * The method is more or less for implicit type casting.
     * It must return this object itself with the dynamic type 'BasicArrayType'.
     *
     * If this object is NOT a BasicArrayType, it MUST throw a BasicTypeException.
     **/
    public BasicArrayType getArray()
	throws BasicTypeException {

	return this; // implicit type cast ;)
    }

    public int getArraySize() {
	return this.items.size();
    }

    public BasicType getArrayElementAt( int index ) 
	throws ArrayIndexOutOfBoundsException {

	return items.get( index );
    }

    
    protected BasicType setArrayElementAt( int index,
					BasicType item )
	throws BasicTypeException,
	       ArrayIndexOutOfBoundsException {

	return this.items.set( index, item );
    }

    protected void addArrayElement( BasicType item )
	throws BasicTypeException {

	this.items.add( item );
    }

    public BasicType[] getObjectArray() {
	return this.items.toArray( new BasicType[this.items.size()] );
    }



    public boolean equals( BasicArrayType a ) {

	if( a == null )
	    return false;
	
	try {

	    if( this.getArraySize() != a.getArraySize() )
		return false;

	    BasicType x, y;
	    for( int i = 0; i < this.getArraySize(); i++ ) {

		x = this.getArrayElementAt(i);
		y = a.getArrayElementAt(i);

		if( x == null && y != null )
		    return false;
		else if( x != null && y == null )
		    return false;
		else if( x != null && y != null && !x.equals(y) )
		    return false;

	    }

	    return true;

	} catch( BasicTypeException e ) {
	    
	    throw new RuntimeException( "Failed to compare two BasicArrayTypes: " + e.getMessage(), e );

	}
    }

   
}
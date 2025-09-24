package ikrs.util;

import java.util.Comparator;

/**
 * A very simple byte array comparator.
 *
 * @author Ikaros Kappler
 * @date 2013-04-10
 * @version 1.0.0
 **/


public class ByteArrayComparator 
    implements Comparator<byte[]> {

    //--- BEGIN ----------------- Comparator ------------------------
    /**
     * Compared the passed arrays.
     *
     * See compareArrays( o1, o2 ).
     *
     * @see compareArrays( byte[], byte[] )
     **/
    public int compare( byte[] o1, byte[] o2 ) {
	return ByteArrayComparator.compareArrays( o1, o2 );
    }
    
    public boolean equals( Object obj ) {
	
	try {
	    return (obj instanceof ByteArrayComparator);
	} catch( ClassCastException e ) {
	    return false;
	}
	
    }
    //--- END ------------------- Comparator ------------------------

    /**
     * Compares the passed byte arrays the way the data would
     * be compared in a string: byte for byte instead of char by char,
     * beginning at index 0.
     *
     * If one array is prefix of the other, the longest is considered
     * to be 'larger'.
     *
     * null is considered to be 'smaller' than non-null.
     *
     * null equals null.
     *
     * If both arrays have the same length and all bytes are equal
     * both arrays are considered equal.
     * 
     *
     * @param o1 The first byte array.
     * @param o2 The second byte array.
     **/
    public static int compareArrays( byte[] o1, byte[] o2 ) {
	
	if( o1 == null && o2 != null )
	    return -1;
	
	if( o1 != null && o2 == null )
	    return 1;

	if( o1 == null && o2 == null )
	    return 0;
	
	if( o1 == o2 )
	    return 0;

    	int minLen = Math.min( o1.length, o2.length );

	for( int i = 0; i < minLen; i++ ) {
	    
	    if( o1[i] > o2[i] )
		return 1;
	    else if( o1[i] < o2[i] )
		return -1;
	}

	// here: all 'min' elements are equal
	if( o1.length > o2.length )
	    return 1;
	else if( o1.length < o2.length )
	    return -1;
	else
	    return o2.length-o1.length;
    }


}
package ikrs.httpd;


import ikrs.yuccasrv.ConnectionUserID;

/**
 * This is the internally used HTTPConnectionUserID implementation.
 *
 * Actually it's just a wrapper for the default ConnectionUserID to enable later mods.
 *
 * @author Ikaros Kappler
 * @date 2012-09-28
 * @version 1.0.0
 **/


public class HTTPConnectionUserID
    implements Comparable {

    private ConnectionUserID<ConnectionUserID> connectionUserID;


    public HTTPConnectionUserID( ConnectionUserID<ConnectionUserID> id ) 
	throws NullPointerException {

	if( id == null )
	    throw new NullPointerException( "Cannot create an HTTPConnectionUserID from a null-ID." );

	this.connectionUserID = id;

    }
    
    /**
     * This method compares this connection user ID with the given object.
     *
     * @see java.lang.Object.equals( Object )
     * @return true if and only if this and the given object are equal.
     **/
    public boolean equals( Object o ) {
	try {
	    //return this.connectionUserID.equals( o );
	    return this.equals( (HTTPConnectionUserID)o );
	} catch( ClassCastException e ) {
	    return false;
	}
    }


    /**
     * This method compares this connection user ID with the given ID.
     *
     * @see java.lang.Object.equals( Object )
     * @return true if and only if this and the given ID are equal.
     **/
    public boolean equals( HTTPConnectionUserID userID ) {
	return this.connectionUserID.equals( userID.connectionUserID );
    }


    /**
     * This method compares this connection user ID with the given ID.
     * If this ID is 'smaller' than the passed ID, the method returns a negative integer.
     * If this ID is 'bigger' than the passed ID, the method returns a positive integer.
     * If this ID equals the passed ID, the method returns 0.
     *
     * @see equals( ConnectionUserID )
     * @return A value indicating the order of the two ID.
     **/
    public int compareTo( HTTPConnectionUserID userID ) {
	return this.connectionUserID.compareTo( userID.connectionUserID );
    }

    
    // From Comparable.compareTo( Object );
    public int compareTo( Object o ) {
	try {
	    //return this.connectionUserID.equals( o );
	    return this.compareTo( (HTTPConnectionUserID)o );
	} catch( ClassCastException e ) {
	    return -1;
	}
    }


    /**
     * Converts this connection user ID to a string, where the strings
     * are equal if (and only if) both ID are equal.
     **/
    public String toString() {
	return this.connectionUserID.toString();
    }
    

}
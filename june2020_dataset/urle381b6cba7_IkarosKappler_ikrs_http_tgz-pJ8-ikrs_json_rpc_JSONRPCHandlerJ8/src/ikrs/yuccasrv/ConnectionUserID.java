package ikrs.yuccasrv;

/**
 * The connection user ID is a wrapper class that holds essential data that is required to
 * identify a single when connecting.
 *
 * The idea is that on a later reconnect the connected user still has the same connection ID (unless the IP changed).
 *
 * Note that this object is not meant for information retrieval but only for comparison.
 *
 * @author Ikaros Kappler
 * @date 2012-09-07
 * @version 1.0.0
 **/

public interface ConnectionUserID<T extends ConnectionUserID>
    extends Comparable<T> {

    
    /**
     * This method compares this connection user ID with the given object.
     *
     * @see java.lang.Object.equals( Object )
     * @return true if and only if this and the given object are equal.
     **/
    public boolean equals( Object o );


    /**
     * This method compares this connection user ID with the given ID.
     *
     * @see java.lang.Object.equals( Object )
     * @return true if and only if this and the given ID are equal.
     **/
    public boolean equals( T userID );


    /**
     * This method compares this connection user ID with the given ID.
     * If this ID is 'smaller' than the passed ID, the method returns a negative integer.
     * If this ID is 'bigger' than the passed ID, the method returns a positive integer.
     * If this ID equals the passed ID, the method returns 0.
     *
     * @see equals( ConnectionUserID )
     * @return A value indicating the order of the two ID.
     **/
    public int compareTo( T userID );
    

}
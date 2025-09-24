package ikrs.yuccasrv;

import java.net.InetAddress;
import java.util.UUID;

/**
 * The DefaultConnectionUserID implements the ConnectionUserID interface for the use of incoming connections.
 *
 * Essentials required to identify users are:
 *  - serverID
 *  - bind address
 *  - bind port
 *  - remote address (when the remote address changes the user is considered 'new' -> session timeout)
 *
 *
 * NOTE: this class DOES NOT implement the ConnectionUserID<DefaultConnectionUserID> interface to allow
 *       subclasses to define custom comparison methods.
 *
 *
 * @author Ikaros Kappler
 * @date 2012-09-07
 * @version 1.0.0
 **/


public class DefaultConnectionUserID {
    //implements ConnectionUserID<DefaultConnectionUserID> {
 
    
    /**
     * The server's unique ID.
     **/
    private UUID serverID;
    
    /**
     * The server's local bind address.
     **/
    private InetAddress localAddress;

    /**
     * The server local bind port.
     **/
    private int localPort;

    /**
     * The connection's remote address.
     **/
    private InetAddress remoteAddress;

    
    public DefaultConnectionUserID( UUID serverID,
				    InetAddress localAddress,
				    int localPort,
				    InetAddress remoteAddress )
	throws NullPointerException {

	super();

	if( serverID == null )
	    throw new NullPointerException( "Cannot create a DefaultConnectionUserID with a null-serverID." );

	if( localAddress == null )
	    throw new NullPointerException( "Cannot create a DefaultConnectionUserID with a null-localAddress." );

	if( remoteAddress == null )
	    throw new NullPointerException( "Cannot create a DefaultConnectionUserID with a null-remoteAddress." );


	this.serverID      = serverID;
	this.localAddress  = localAddress;
	this.remoteAddress = remoteAddress;
	this.localPort     = localPort;

    }


    
    //--- BEGIN --------------------------- ConnectionUserID methods ----------------------------------
    /**
     * This method compares this connection user ID with the given object.
     *
     * @see java.lang.Object.equals( Object )
     * @return true if and only if this and the given object are equal.
     **/
    public boolean equals( Object o ) {
	
	// Just try to cast the object to the required type.
	try {

	    return this.equals( (DefaultConnectionUserID)o );

	} catch( ClassCastException e ) {

	    // Wrong type -> objects cannot be equal.
	    return false;

	}
    }


    /**
     * This method compares this connection user ID with the given ID.
     *
     * @see java.lang.Object.equals( Object )
     * @return true if and only if this and the given ID are equal.
     **/
    public boolean equals( DefaultConnectionUserID userID ) {
	
	// By convention two objects are equal if (and only if) their comparison result is zero.
	return ( this.compareTo( userID ) == 0 );
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
    public int compareTo( DefaultConnectionUserID userID ) {

	int cmp = this.serverID.compareTo( userID.serverID );
	if( cmp != 0 )
	    return cmp;

	cmp = compareAddresses( this.localAddress, userID.localAddress );
	if( cmp != 0 )
	    return cmp;

	cmp = this.localPort - userID.localPort;
	if( cmp != 0 )
	    return 0;

	cmp = compareAddresses( this.remoteAddress, userID.remoteAddress );
	//if( cmp != 0 )
	//    return cmp;
	
	return cmp;
    }
    

    /**
     * As the InetAddress class has no compareTo()-method, we need a custom one.
     **/
    private int compareAddresses( InetAddress first, 
				  InetAddress second ) {

	if( first.equals(second) )
	    return 0; // equal

	byte[] bytesA = first.getAddress();
	byte[] bytesB = second.getAddress();

	if( bytesA.length != bytesB.length )
	    return bytesA.length - bytesB.length;  // not equal

	int cmp;
	for( int i = 0; i < bytesA.length; i++ ) {
	    
	    cmp = bytesA[i] - bytesB[i];
	    if( cmp != 0 )
		return cmp;

	}

	return -1;
	
    }
    //--- END ----------------------------- ConnectionUserID methods ----------------------------------


    public String toString() {
	return toString( new StringBuffer() ).toString();
    }

    public StringBuffer toString( StringBuffer b ) {

	
	b.append( getClass().getName() ).append( "={ " ).
	    append( "serverID=" ).append( this.serverID ).
	    append( ", localAddress=" ).append( this.localAddress ).
	    append( ", localPort=" ).append( this.localPort ).
	    append( ", remoteAddress=" ).append( this.remoteAddress ).
	    append( " }" );
	
	
	return b;
    }

}

package ikrs.yuccasrv;

import java.net.InetAddress;
import java.util.UUID;

/**
 * The TCPConnectionUserID implements the ConnectionUserID interface for the use of incoming TCP connections.
 *
 * Essentials required to identify users are:
 *  - serverID
 *  - bind address
 *  - bind port
 *  - remote address (when the remote address changes the user is considered 'new' -> session timeout)
 *
 *
 * @author Ikaros Kappler
 * @date 2012-09-07
 * @version 1.0.0
 **/


public class UDPConnectionUserID
    extends DefaultConnectionUserID
    implements ConnectionUserID<UDPConnectionUserID> {
 
    
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

    
    public UDPConnectionUserID( UUID serverID,
				InetAddress localAddress,
				int localPort,
				InetAddress remoteAddress )
	throws NullPointerException {

	super( serverID, localAddress, localPort, remoteAddress );


    }


    //--- BEGIN --------------------------- OVERRIDE ConnectionUserID methods ----------------------------------
    /**
     * @override To ensure only TCPConnectionUserIDs will be compared.
     **/
    public boolean equals( Object o ) {
	
	// Just try to cast the object to the required type.
	try {

	    return this.equals( (UDPConnectionUserID)o );

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
    public boolean equals( UDPConnectionUserID userID ) {
	
	// There is no special additional functionality so far.
	// -> user superclasses comparison method
	return super.equals( userID );
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
    public int compareTo( UDPConnectionUserID userID ) {

	// There is no special additional functionality so far.
	// -> user superclasses comparison method
	return super.compareTo( userID );

    }
    //--- END ----------------------------- OVERRIDE ConnectionUserID methods ----------------------------------

}

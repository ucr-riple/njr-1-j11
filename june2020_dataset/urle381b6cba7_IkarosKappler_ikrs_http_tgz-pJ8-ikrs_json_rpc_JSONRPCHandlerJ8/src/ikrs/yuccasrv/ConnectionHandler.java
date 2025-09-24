package ikrs.yuccasrv;

import java.net.Socket;
import java.net.DatagramSocket;
import java.util.UUID;

import ikrs.typesystem.BasicType;
import ikrs.util.Environment;
import ikrs.util.ObjectWithUUID;
import ikrs.yuccasrv.socketmngr.BindListener;
import ikrs.yuccasrv.socketmngr.BindManager;

/**
 * @author Ikaros Kappler
 * @date 2012-05-09
 * @version 1.0.0
 **/

public abstract class ConnectionHandler
    implements BindListener,
	       ObjectWithUUID {

    private UUID uuid;

    public ConnectionHandler() {
	super();

	this.uuid = UUID.randomUUID();
    }

    /**
     * This method will be called after the connection handler was instantiated (usually using the
     * Class.newInstance() method).
     *
     * Both params might be null or empty depending on the underlying interface implementation.
     *
     * This method must throw an InstantiationException if any required params are missing.
     *
     * @param additionalSettings   An environment containing additional initialization params. Might be null or empty.
     * @param optionalReturnValues An environment the method may use to store (optional) return values in. May be null.
     **/
    public abstract void init( Environment<String,BasicType> additionalSettings,
			       Environment<String,BasicType> optionalReturnValues  )
	  throws InstantiationException;

    //--- BEGIN ------------------ BindListener -----------------------------------
    /**
     * @param source The BindManager that reports the event.
     * @param socketID A unique ID to identify the created socket by the use of
     *                 BindManager.getServer*( socketID ).
     **/
    public void serverCreated( BindManager source,
			       UUID socketID ) {

    }
    

    /**
     * @param source The BindManager that reports the event.
     * @param socketID The server's unique ID.
     * @param e The reported exception.
     * @param isTraumatic This flag tell if the server socket can still
     *                    be used or if it's (probably) broken and should be
     *                    restarted. In the second case the BindManager will
     *                    automatically close and remove the socket to free
     *                    the resources.
     **/
    public void serverError( BindManager source,
			     UUID socketID,
			     Exception e,
			     boolean isTraumatic ) {

    }


    /**
     * @param source The BindManager that reports the event.
     * @param sockedID The server socket's unique ID.
     * @param sock The accepted connection socket.
     **/
    public abstract void serverAcceptedTCPConnection( BindManager source,
						      UUID socketID,
						      Socket sock,
						      ConnectionUserID<ConnectionUserID> userID );


    /**
     * @param source The BindManager that reports the event.
     * @param sockedID The server socket's unique ID.
     * @param sock The accepted connection socket.
     **/
    public abstract void serverAcceptedUDPConnection( BindManager source,
						      UUID socketID,
						      DatagramSocket sock,
						      ConnectionUserID<ConnectionUserID> userID );

    
    /**
     * @param source The BindManager that reports the event.
     * @param sockedID The server socket's unique ID.
     **/
    public void serverClosed( BindManager source,
			      UUID socketID ) {

    }
    
    
    /**
     * This method will be called if the SocketManager is going to terminate.
     * All associated BindListener MUST terminate within the given time.
     *
     * @param time The time value all dependent child threads have to terminate in.
     * @param unit The time unit.
     **/
    public abstract void finalize( long time,
				   java.util.concurrent.TimeUnit unit );

    //--- END -------------------- BindListener -----------------------------------

    //---BEGIN---------------------- ObjectWithUUID -------------------------
    /**
     * Get the unique and final UUID for this object.
     *
     * The returned UUID must never change!
     * 
     * @return The UUID of this object.
     **/
    public UUID getUUID() {
	return this.uuid;
    }
    //---END------------------------ ObjectWithUUID -------------------------

}
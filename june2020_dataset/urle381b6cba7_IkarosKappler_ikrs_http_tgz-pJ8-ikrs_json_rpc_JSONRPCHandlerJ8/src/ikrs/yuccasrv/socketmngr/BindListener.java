package ikrs.yuccasrv.socketmngr;

import java.net.Socket;
import java.net.DatagramSocket;
import java.util.UUID;

import ikrs.yuccasrv.ConnectionUserID;

/**
 * This interface declares all listener methods to receive events
 * from the bind manager (server opened, incoming connection, server error,
 * server closed).
 *
 * @author Ikaros Kappler
 * @date 2012-04-23
 * @version 1.0.0
 **/


public interface BindListener {

    
    /**
     * @param source The BindManager that reports the event.
     * @param socketID A unique ID to identify the created socket by the use of
     *                 BindManager.getServer*( socketID ).
     **/
    public void serverCreated( BindManager source,
			       UUID socketID );
    

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
			     boolean isTraumatic );


    /**
     * @param source The BindManager that reports the event.
     * @param sockedID The server socket's unique ID.
     * @param sock The accepted connection socket.
     **/
    public void serverAcceptedTCPConnection( BindManager source,
					     UUID socketID,
					     Socket sock,
					     ConnectionUserID<ConnectionUserID> userID );


    /**
     * @param source The BindManager that reports the event.
     * @param sockedID The server socket's unique ID.
     * @param sock The accepted connection socket.
     **/
    public void serverAcceptedUDPConnection( BindManager source,
					     UUID socketID,
					     DatagramSocket sock,
					     ConnectionUserID<ConnectionUserID> userID );

    
    /**
     * @param source The BindManager that reports the event.
     * @param sockedID The server socket's unique ID.
     **/
    public void serverClosed( BindManager source,
			      UUID socketID );

    
    /**
     * This method will be called if the SocketManager is going to terminate.
     * All associated BindListener MUST terminate within the given time.
     *
     * @param time The time value all dependent child threads have to terminate in.
     * @param unit The time unit.
     **/
    public void finalize( long time,
			  java.util.concurrent.TimeUnit unit );

}
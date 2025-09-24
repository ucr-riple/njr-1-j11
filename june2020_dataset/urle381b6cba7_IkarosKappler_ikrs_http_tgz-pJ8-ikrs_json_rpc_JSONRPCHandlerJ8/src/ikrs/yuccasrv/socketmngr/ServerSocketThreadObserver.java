package ikrs.yuccasrv.socketmngr;

import java.io.IOException;
import java.net.*;
import java.nio.channels.IllegalBlockingModeException;

//import ikrs.typesystem.BasicType;

/**
 * This interface declares all listener methods that a required to receive
 * all important server socket events.
 *
 *
 * @author Ikaros Kappler
 * @date 2012-04-22
 * @version 1.0.0
 **/


public interface ServerSocketThreadObserver {

    /**
     * This method is called if a server socket successfully accepted an incoming
     * TCP connection.
     *
     * @param t The ServerSocketThread that created and holds the server.
     * @param s The socket for the incoming connection.
     **/
    public void incomingTCPConnection( ServerSocketThread t,
				       Socket s );

    /**
     * This method is called if a server socket successfully accepted an incoming
     * UDP 'connection'.
     *
     * @param t The ServerSocketThread that created and holds the server.
     * @param s The socket for the incoming connection.
     **/
    public void incomingUDPConnection( ServerSocketThread t,
				       DatagramSocket s );

    /**
     * This method is called if a socket was closed by user/system request.
     *
     * @param t The ServerSocketThread that created and holds the server.
     **/
    public void serverSocketClosed( ServerSocketThread t );

    /**
     * This method is called if a socket caused an IOException by any
     * reason. Problably the socket will not be working properly if this event was
     * fired.
     *
     * Note that SocketTimeoutExceptions are reported thorugh a different event.
     *
     * @param t The ServerSocketThread that created and holds the server.
     * @param e The Exception that was fetched.
     * @see serverSocketException( ServerSocketThread, SocketTimeoutException )
     **/
    public void serverSocketException( ServerSocketThread t,
				       IOException e );

   
    /**
     * This method is called if a socket caused a SecurityException by any
     * reason. 
     *
     * @param t The ServerSocketThread that created and holds the server.
     * @param e The Exception that was fetched.
     **/
    public void serverSocketException( ServerSocketThread t,
				       SecurityException e );

    /**
     * This method is called if a socket caused a SocketTimeoutException.
     *
     * @param t The ServerSocketThread that created and holds the server.
     * @param e The Exception that was fetched.
     **/
    public void serverSocketException( ServerSocketThread t,
				       SocketTimeoutException e );

    /**
     * This method is called if a socket caused a IllegalBlockingModeException by any
     * reason. 
     *
     * @param t The ServerSocketThread that created and holds the server.
     * @param e The Exception that was fetched.
     **/
    public void serverSocketException( ServerSocketThread t,
				       IllegalBlockingModeException e );

}
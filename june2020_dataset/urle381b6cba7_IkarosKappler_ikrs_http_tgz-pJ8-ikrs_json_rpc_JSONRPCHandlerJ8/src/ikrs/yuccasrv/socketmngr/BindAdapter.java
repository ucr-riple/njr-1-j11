package ikrs.yuccasrv.socketmngr;

import java.net.Socket;
import java.net.DatagramSocket;
import java.util.UUID;

/**
 * This abstract class implements all method from the BindListener interface - but
 * it leaves all methos bodies empty.
 *
 * - Inherit from this class an implement those methods you need by overwriting them.
 *
 * - Install an instance of your class as a listener to the BindManager to receive
 *   socket events.
 *
 *
 * @author Ikaros Kappler
 * @date 2012-04-24
 * @version 1.0.0
 **/


public abstract class BindAdapter 
    implements BindListener {



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
    public void serverAcceptedTCPConnection( BindManager source,
					     UUID socketID,
					     Socket sock ) {

    }


    /**
     * @param source The BindManager that reports the event.
     * @param sockedID The server socket's unique ID.
     * @param sock The accepted connection socket.
     **/
    public void serverAcceptedUDPConnection( BindManager source,
					     UUID socketID,
					     DatagramSocket sock ) {

    }

    
    /**
     * @param source The BindManager that reports the event.
     * @param sockedID The server socket's unique ID.
     **/
    public void serverClosed( BindManager source,
			      UUID socketID ) {

    }

}
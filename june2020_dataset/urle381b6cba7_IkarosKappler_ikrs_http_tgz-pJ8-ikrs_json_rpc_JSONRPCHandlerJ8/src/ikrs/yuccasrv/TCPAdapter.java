package ikrs.yuccasrv;

/**
 * Subclasses must implement the
 * serverAcceptedTCPConnection( BindManager source, UUID socketID, Socket sock )
 * method.
 *
 * @author Ikaros Kappler
 * @date 2012-05-15
 * @version 1.0.0
 **/

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.net.Socket;
import java.net.DatagramSocket;
import java.util.UUID;

import ikrs.typesystem.BasicType;
import ikrs.util.Environment;
import ikrs.yuccasrv.ConnectionUserID;
import ikrs.yuccasrv.socketmngr.BindManager;


public abstract class TCPAdapter
    extends ConnectionHandler {

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

    /**
     * This method does nothing except forwarding an INFO/WARNING to the global logger instance
     * via LogManager.getLogManager().getLogger( Constants.DEFAULT_LOGGER_NAME ).log( ... ).
     *
     * @param source The BindManager that reports the event.
     * @param socketID A unique ID to identify the created socket by the use of
     *                 BindManager.getServer*( socketID ).
     **/
    public void serverCreated( BindManager source,
			       UUID socketID ) {
	
	if( source.getServerSettings(socketID).get(Constants.CONFIG_SERVER_PROTOCOL).getString().equalsIgnoreCase("TCP") ) {
	    
	    LogManager.getLogManager().getLogger( Constants.DEFAULT_LOGGER_NAME ).log( Level.INFO, 
								       "The bind manager reports a new TCP listening server ("+socketID+"): " +
								       source.getServerSettings(socketID).get(Constants.CONFIG_SERVER_ADDRESS).getString()+
								       ":" +
								       source.getServerSettings(socketID).get(Constants.CONFIG_SERVER_ADDRESS).getString()
								       );
	} else {
	    LogManager.getLogManager().getLogger( Constants.DEFAULT_LOGGER_NAME ).log( Level.WARNING, 
								       "The bind manager reports a new "+source.getServerSettings(socketID).get(Constants.CONFIG_SERVER_PROTOCOL).getString()+" listening server ("+socketID+"): " +
								       source.getServerSettings(socketID).get(Constants.CONFIG_SERVER_ADDRESS).getString()+
								       ":" +
								       source.getServerSettings(socketID).get(Constants.CONFIG_SERVER_ADDRESS).getString()
								       );
	}

    }
    

    /**
     * This method simply logs a SEVERE error to
     * the global Logger via LogManager.getLogManager().getLogger( Constants.DEFAULT_LOGGER_NAME ).log( ... ). 
     *
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
	
	LogManager.getLogManager().getLogger( Constants.DEFAULT_LOGGER_NAME ).log( Level.SEVERE, e.getMessage() + " (isTraumatic="+isTraumatic+")" );
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
     * This method is not meant to be called as this is a _TCP_ handler. It simply logs a SEVERE error to
     * the global Logger via LogManager.getLogManager().getLogger( Constants.DEFAULT_LOGGER_NAME ).log( ... ).
     *
     * @param source The BindManager that reports the event.
     * @param sockedID The server socket's unique ID.
     * @param sock The accepted connection socket.
     **/
    public final void serverAcceptedUDPConnection( BindManager source,
						   UUID socketID,
						   DatagramSocket sock,
						   ConnectionUserID<ConnectionUserID> userID ) {
	LogManager.getLogManager().getLogger( Constants.DEFAULT_LOGGER_NAME ).log( Level.SEVERE, "TCPAdapters are not capable to handle UDP requests!" );
    }

    
    /**
     * This method does nothing except forwarding the INFO to the global logger instance
     * via LogManager.getLogManager().getLogger( Constants.DEFAULT_LOGGER_NAME ).log( ... ).
     *
     * @param source The BindManager that reports the event.
     * @param sockedID The server socket's unique ID.
     **/
    public void serverClosed( BindManager source,
			      UUID socketID ) {

	LogManager.getLogManager().getLogger( Constants.DEFAULT_LOGGER_NAME ).log( Level.INFO, "The bind manager reports a closing server ("+socketID+")" );
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
    


}
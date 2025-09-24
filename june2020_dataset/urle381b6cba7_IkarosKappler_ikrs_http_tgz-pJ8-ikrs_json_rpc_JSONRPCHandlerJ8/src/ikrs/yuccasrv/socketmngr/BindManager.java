package ikrs.yuccasrv.socketmngr;

import java.io.IOException;
import java.net.*;
import java.nio.channels.IllegalBlockingModeException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

import ikrs.typesystem.BasicNumberType;
import ikrs.typesystem.BasicStringType;
import ikrs.typesystem.BasicType;
import ikrs.typesystem.BasicUUIDType;

import ikrs.yuccasrv.Constants;
import ikrs.yuccasrv.ConnectionUserID;
import ikrs.yuccasrv.TCPConnectionUserID;
import ikrs.yuccasrv.UDPConnectionUserID;

import ikrs.util.CustomLogger;
import ikrs.util.DefaultCustomLogger;
import ikrs.util.Environment;

/**
 * Ths BindManager is the central entity that manages server sockets and their bindings to
 * the system.
 * It additionally accepts incoming connections and passes them to the installed BindListeners.
 *
 * If a server/socket was closed by any reason the manager frees bound resources and removes 
 * the socket from the inner table.
 *
 *
 * @author Ikaros Kappler
 * @date 2012-03-25
 * @version 1.0.0
 **/


public class BindManager 
    implements Runnable,
	       // BindListener,
	       ServerSocketThreadObserver {

    private HashMap<UUID,ServerSocketThread>
	serverSocketThreads;

    private ArrayList<BindListener> 
	bindListeners;

    /* This might be null */
    private CustomLogger
	logger;

    public BindManager() {
	this( null );
    }

    public BindManager( CustomLogger logger ) {
	super();

	this.serverSocketThreads = new HashMap<UUID,ServerSocketThread>();

	this.bindListeners = new ArrayList<BindListener>();	

	if( logger == null ) {
 
	    // Create a new anonymous logger
	    //Logger tmpLogger= Logger.getAnonymousLogger();
	    //tmpLogger.addHandler( new StreamHandler( System.out,
	    //					     new SimpleFormatter()
	    //					     ) 
	    //			  );
	    this.logger = new DefaultCustomLogger( getClass().getName() );

	} else {

	    this.logger = logger;
	    
	}
    }

    public void setLogger( CustomLogger logger ) 
	throws NullPointerException {
	
	if( logger == null )
	    throw new NullPointerException( "The logger must not be null." );
	
	this.logger = logger;
    }

    protected CustomLogger getLogger() {
	return this.logger;
    }

    private void fireServerCreated( UUID socketID ) {

	//System.out.println( "There are " + this.bindListeners.size()+" listener(s): sever created." );
	for( int i = 0; i < this.bindListeners.size(); i++ ) {

	    this.bindListeners.get(i).serverCreated( this, socketID );

	}
    }

    private void fireServerError( UUID socketID, Exception e, boolean isTraumatic ) {

	for( int i = 0; i < this.bindListeners.size(); i++ ) {

	    this.bindListeners.get(i).serverError( this, socketID, e, isTraumatic );

	}
    }

    //boolean called = false;
    private void fireServerAcceptedTCPConnection( UUID socketID, Socket sock, ConnectionUserID<ConnectionUserID> userID ) {

	//if( called )
	//   throw new RuntimeException( "urgs" );
	
	//called = true;
	for( int i = 0; i < this.bindListeners.size(); i++ ) {

	    //System.out.println( getClass().getName() + ".fireServerAcceptedTCPConnection(...) Telling lister #"+i+" of "+this.bindListeners.size()+" about TCP connection ..." );
	    this.bindListeners.get(i).serverAcceptedTCPConnection( this, socketID, sock, userID );

	}
    }

    private void fireServerAcceptedUDPConnection( UUID socketID, DatagramSocket sock, ConnectionUserID<ConnectionUserID> userID ) {

	for( int i = 0; i < this.bindListeners.size(); i++ ) {

	    this.bindListeners.get(i).serverAcceptedUDPConnection( this, socketID, sock, userID );

	}
    }

    private void fireServerClosed( UUID socketID ) {

	for( int i = 0; i < this.bindListeners.size(); i++ ) {

	    this.bindListeners.get(i).serverClosed( this, socketID );

	}
    }

    

    /**
     * This method adds a new BindListener to this BindManager.
     *
     * @param l The bind listener to be added (must not be null).
     * @return true if the listener was not already in the listener list.
     **/
    public boolean addBindListener( BindListener l ) 
	throws NullPointerException {

	if( l == null )
	    throw new NullPointerException( "Cannot add null listeners." );

	for( int i = 0; i < this.bindListeners.size(); i++ ) {
	    if( this.bindListeners.get(i) == l )
		return false;
	}

	this.bindListeners.add( l );
	return true;
    }

    /**
     * This method removes a BindListener from this BindManager.
     *
     * @param l The bind listener to be removed.
     * @return true if the listener was found and removed from the listener list.
     **/
    public boolean removeBindListener( BindListener l ) {

	for( int i = 0; i < this.bindListeners.size(); i++ ) {
	    if( this.bindListeners.get(i) == l ) {
		this.bindListeners.remove( i );
		return true;
	    }
	}

	return false;
    }

    //-BEGIN---------------------------- ServerSocketObserver ---------------------
    /**
     * This method is called if a server socket successfully accepted an incoming
     * TCP connection.
     *
     * @param t The ServerSocketThread that created and holds the server.
     * @param s The socket for the incoming connection.
     **/
    public void incomingTCPConnection( ServerSocketThread t,
				       Socket s ) {

	ConnectionUserID userID = new TCPConnectionUserID( t.getUUID(),
									     t.getBindAddress(),
									     t.getBindPort(),
									     s.getInetAddress() );
							   

	this.logger.log( Level.INFO,
			 getClass().getName() + ".incomingTCPConnection(...)",
			 "Incoming TCP connection from " + s.toString() );
	fireServerAcceptedTCPConnection( t.getUUID(), s, userID );
    }

    /**
     * This method is called if a server socket successfully accepted an incoming
     * UDP 'connection'.
     *
     * @param t The ServerSocketThread that created and holds the server.
     * @param s The socket for the incoming connection.
     **/
    public void incomingUDPConnection( ServerSocketThread t,
				       DatagramSocket s ) {

	ConnectionUserID userID = new UDPConnectionUserID( t.getUUID(),
									     t.getBindAddress(),
									     t.getBindPort(),
									     s.getInetAddress() );

	this.logger.log( Level.INFO,
			 getClass().getName() + ".incomingUDPConnection(...)",
			 "Incoming UDP datagram from " + s.toString() );
	fireServerAcceptedUDPConnection( t.getUUID(), s, userID );
    }

    /**
     * This method is called if a socket was closed by user/system request.
     *
     * @param t The ServerSocketThread that created and holds the server.
     **/
    public void serverSocketClosed( ServerSocketThread t ) {

	this.logger.log( Level.INFO,
			 getClass().getName() + ".serverSocketClosed(...)",
			 "Server socket closed: " + t );

	// Tell all listeners
	this.fireServerClosed( t.getUUID() );

	// Remove from local map
	serverSocketThreads.remove( t.getUUID() );

    }
    
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
				       IOException e ) {

	this.logger.log( Level.INFO,
			 getClass().getName() + ".serverSocketException(...)",
			 "ServerSocket | IOException: " + e.getMessage() );
	e.printStackTrace();

	// Tell all listeners
	this.fireServerError( t.getUUID(), e, true );
    }

    /**
     * This method is called if a socket caused a SecurityException by any
     * reason. 
     *
     * @param t The ServerSocketThread that created and holds the server.
     * @param e The Exception that was fetched.
     **/
    public void serverSocketException( ServerSocketThread t,
				       SecurityException e ) {

	this.logger.log( Level.INFO,
			 getClass().getName() + ".serverSocketException(...)",
			 "ServerSocket | SecurityException: " + e.getMessage() );

	// Tell all listeners
	this.fireServerError( t.getUUID(), e, true );
    }

    /**
     * This method is called if a socket caused a SocketTimeoutException.
     *
     * @param t The ServerSocketThread that created and holds the server.
     * @param e The Exception that was fetched.
     **/
    public void serverSocketException( ServerSocketThread t,
				       SocketTimeoutException e ) {

	this.logger.log( Level.INFO,
			 getClass().getName(),
			 "ServerSocket | SocketTimeoutException: " + e.getMessage() );
	
	// Tell all listeners
	this.fireServerError( t.getUUID(), e, true );
    }

    /**
     * This method is called if a socket caused a IllegalBlockingModeException by any
     * reason. 
     *
     * @param t The ServerSocketThread that created and holds the server.
     * @param e The Exception that was fetched.
     **/
    public void serverSocketException( ServerSocketThread t,
				       IllegalBlockingModeException e ) {

	this.logger.log( Level.INFO,
			 getClass().getName() + ".serverSocketException(...)",
			 "ServerSocket | IllegalBlockingModeException: " + e.getMessage() );

	// Tell all listeners
	this.fireServerError( t.getUUID(), e, true );
    }
    //-END------------------------------ ServerSocketObserver ---------------------

    /**
     * This method can be used to bind new server sockets to the system.
     *
     * It starts an asynchronous thread!
     * You should install a BindListener before you call this method to
     * be sure not to miss any socket events.
     *
     * Hint: the socket itself is initiated in synchronous mode, so if no
     *       no IOException was thrown when calling this method the binding
     *       process was successful. 
     *       Though it is not guaranteed that the socket is already in listening
     *       mode [Socket.accept()] in the moment this method terminates.
     *       To ensure the socket IS in listening mode, you must have had a BindListener
     *       installed before to be sure not to miss the serverCreated(...) event!
     *       
     *
     * @param address
     * @param port
     * @param bindSettings This map may contain optional settings.
     **/
    public synchronized UUID bind( InetAddress address,
				   int port,
				   //Map<String,BasicType> bindSettings 
				   Environment<String,BasicType> bindSettings
				   ) 
	throws IOException,
	       GeneralSecurityException {

	if( bindSettings.get(Constants.CONFIG_SERVER_PROTOCOL) == null )
	    bindSettings.put( Constants.CONFIG_SERVER_PROTOCOL, new BasicStringType(Constants.NAME_PROTOCOL_TCP) );


	if( bindSettings.get(Constants.CONFIG_SERVER_PROTOCOL).getString(Constants.NAME_PROTOCOL_TCP).equalsIgnoreCase(Constants.NAME_PROTOCOL_TCP) ) {
	    
	    ServerSocketThread server = new ServerSocketThread( this.getLogger(),
								address,
								port,
								bindSettings,
								this  // observer
								);
	    // Register server
	    this.serverSocketThreads.put( server.getUUID(), server );
	    
	    // Also store ID into settings
	    bindSettings.put( Constants.CONFIG_SERVER_ADDRESS, new BasicStringType(address.getHostName()) );
	    bindSettings.put( Constants.CONFIG_SERVER_PORT,    new BasicNumberType(port) );
	    bindSettings.put( Constants.KEY_ID,                new BasicUUIDType(server.getUUID()) );

	    // Tell the listeners about the new server
	    this.fireServerCreated( server.getUUID() );

	    // Then start thread
	    server.start();

	    // Return the new ID
	    return server.getUUID();

	} else if( bindSettings.get(Constants.KEY_PROTOCOL) != null || bindSettings.get(Constants.KEY_PROTOCOL).getString(Constants.NAME_PROTOCOL_UDP).equalsIgnoreCase(Constants.NAME_PROTOCOL_UDP) ) {
	    
	    throw new IOException( "Protocol UDP not yet supported, sorry." );
	    
	} else {

	    throw new IOException( "Unknown protocol: " + bindSettings.get(Constants.KEY_PROTOCOL) );

	}


    }

    public synchronized void release( UUID socketID ) 
	throws IOException {

	ServerSocketThread t = this.serverSocketThreads.get( socketID );

	if( t == null )
	    throw new IOException( "Cannot release server " + socketID + " (socker ID not found)." );

	// This will cause a stack of events to be fired, so there should be nothing more to do
	t.interrupt(); // closeServerSocket(); 
	
    }

    public Map<String,BasicType> getServerSettings( UUID socketID ) {
	return this.serverSocketThreads.get( socketID ).getServerSettings();
    }

    public synchronized void closeAllServerSockets() {
	//private HashMap<UUID,ServerSocketThread>
	//serverSocketThreads;

	// To avoid concurrent modifications (caused by event driven map removals) first
	// store all sockets into a local list
	Iterator<UUID> iter = this.serverSocketThreads.keySet().iterator();
	int errors = 0;
	List<ServerSocketThread> tmpSocketList = new ArrayList<ServerSocketThread>( this.serverSocketThreads.size() );
	while( iter.hasNext() ) {

	    UUID key = iter.next();
	    ServerSocketThread thread = serverSocketThreads.get( key );
	    
	    //thread.closeServerSocket();
	    tmpSocketList.add( thread );

	}

	for( int i = 0; i < tmpSocketList.size(); i++ ) {

	    tmpSocketList.get(i).interrupt();
	    //tmpSocketList.get(i).closeServerSocket();

	}
	
    }

    public void finalize( long time,
			  java.util.concurrent.TimeUnit unit ) {
	closeAllServerSockets();

	for( int i = 0; i < bindListeners.size(); i++ ) {

	    this.logger.log( Level.INFO,			     
			     getClass().getName() + ".finalze(...)",
			     "Going to tell BindListener " + this.bindListeners.get(i) );

	    bindListeners.get(i).finalize( time, 
					   unit );
	    
	}
    }
    
    public void run() {

    }


    public String getStatusString() {
	StringBuffer b = new StringBuffer();
	b.append( "There are " ).append( this.serverSocketThreads.size() ).append( " listening sockets:\n" );
	
	Iterator<Map.Entry<UUID,ServerSocketThread>> iter = this.serverSocketThreads.entrySet().iterator();
	while( iter.hasNext() ) {

	    Map.Entry<UUID,ServerSocketThread> entry = iter.next();
	    b.append( "\n" ).append( entry.getValue().getStatusString() ).append( "\n" );

	}
	

	return b.toString();
    }

    /*
    public static void main( String[] argv ) {


	


    }
    */

}
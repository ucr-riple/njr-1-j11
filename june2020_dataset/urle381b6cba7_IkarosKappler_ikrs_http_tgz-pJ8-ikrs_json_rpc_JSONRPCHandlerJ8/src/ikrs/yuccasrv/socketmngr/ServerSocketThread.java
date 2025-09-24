package ikrs.yuccasrv.socketmngr;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.*;
import java.nio.channels.IllegalBlockingModeException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import javax.net.ServerSocketFactory;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;


import ikrs.typesystem.BasicType;
import ikrs.typesystem.BasicNumberType;
import ikrs.util.CustomLogger;
import ikrs.util.Environment;
import ikrs.yuccasrv.Constants;
import ikrs.yuccasrv.Yucca;

/**
 * The ServerSocketThread class implements a Runnable object to wrap the
 * basic server into a concurrent thread.
 *
 *
 * @author Ikaros Kappler
 * @date 2012-04-22
 * @version 1.0.0
 **/


public class ServerSocketThread
    extends Thread
    implements Runnable {

    public static final int DEFAULT_BACKLOG = 10;

    private InetAddress bindAddress;
    private int bindPort;
    private Environment<String,BasicType> bindSettings;

    private ServerSocket serverSocket;

    private UUID uuid;

    private ServerSocketThreadObserver observer;

    private CustomLogger logger;

    /**
     * The constructor.
     **/
    protected ServerSocketThread( CustomLogger logger,
				  InetAddress bindAddress,
				  int bindPort,
				  Environment<String,BasicType> bindSettings, 
				  ServerSocketThreadObserver observer 				  
				  ) 
	throws IOException,
	       GeneralSecurityException {	

	super();

	this.logger = logger;

	this.bindAddress = bindAddress;
	this.bindPort = bindPort;
	this.bindSettings = bindSettings;
	
	this.observer = observer;

	int backlog = DEFAULT_BACKLOG;
	if( bindSettings.get(Constants.CONFIG_SERVER_BACKLOG) != null )
	    backlog = bindSettings.get(Constants.CONFIG_SERVER_BACKLOG).getInt( DEFAULT_BACKLOG );

	boolean useSSL = false;
	if( bindSettings.get(Constants.CONFIG_SERVER_SSL) != null )
	    useSSL = bindSettings.get(Constants.CONFIG_SERVER_SSL).getBoolean();



	if( useSSL ) {

	    try {
		this.serverSocket = createSSLServerSocket( bindPort,
							   backlog,
							   bindAddress,
							   this.bindSettings 
							   );
	    } catch( NoSuchAlgorithmException e ) {

		throw new GeneralSecurityException( "Internal error: Cannot create SSLServerSocket. Failed to load encryption algorithm.", e );

	    }

	} else {
	    
	    this.serverSocket = new ServerSocket( bindPort,
						  backlog,
						  bindAddress 
						  );
	}

	this.uuid = UUID.randomUUID();

    }

    private SSLServerSocket createSSLServerSocket( int bindPort,
						   int backlog,
						   InetAddress bindAddress,
						   Environment<String,BasicType> bindSettings ) 
	throws IOException,
	       FileNotFoundException,
	       KeyStoreException,
	       KeyManagementException,
	       NoSuchAlgorithmException,
	       CertificateException,
	       UnrecoverableKeyException {


	BasicType wrp_keyStoreName    = getSSLConfigPropertyValue( bindSettings, Constants.CONFIG_SERVER_SSL_KEYSTORE );
	BasicType wrp_keyStorePass    = getSSLConfigPropertyValue( bindSettings, Constants.CONFIG_SERVER_SSL_KEYSTOREPASSWORD );
	BasicType wrp_keyStoreType    = getSSLConfigPropertyValue( bindSettings, Constants.CONFIG_SERVER_SSL_KEYSTORETYPE );

	BasicType wrp_trustStoreName  = getSSLConfigPropertyValue( bindSettings, Constants.CONFIG_SERVER_SSL_TRUSTSTORE );
	BasicType wrp_trustStorePass  = getSSLConfigPropertyValue( bindSettings, Constants.CONFIG_SERVER_SSL_TRUSTSTOREPASSWORD );
	BasicType wrp_trustStoreType  = getSSLConfigPropertyValue( bindSettings, Constants.CONFIG_SERVER_SSL_TRUSTSTORETYPE );

	BasicType wrp_wantClientAuth  = getSSLConfigPropertyValue( bindSettings, Constants.CONFIG_SERVER_SSL_WANTCLIENTAUTH );
	BasicType wrp_needClientAuth  = getSSLConfigPropertyValue( bindSettings, Constants.CONFIG_SERVER_SSL_NEEDCLIENTAUTH );


	KeyStore keyStore = null;
	if( wrp_keyStoreName != null && wrp_keyStoreName.getString().length() != 0 ) {

	    String keyStoreName = Yucca.processCustomizedFilePath( wrp_keyStoreName.getString() );
	    logger.log( Level.INFO,
			getClass().getName() + ".createSSLServerSocket(...)",
			"Loading key store '" + keyStoreName + "' ... " );
	    keyStore = loadKeyStore( keyStoreName,
				     (wrp_keyStorePass==null?new char[0]:wrp_keyStorePass.getString().toCharArray()),
				     (wrp_keyStoreType==null?null:wrp_keyStoreType.getString())
				     );
	    
	}

	
	KeyStore trustStore = null;
	if( wrp_trustStoreName != null && wrp_trustStoreName.getString().length() != 0 ) {
	    
	    String trustStoreName = Yucca.processCustomizedFilePath( wrp_trustStoreName.getString() );
	    logger.log( Level.INFO,
			getClass().getName() + ".createSSLServerSocket(...)",
			"Loading trust store '" + trustStoreName + "' ... " );
	    trustStore = loadKeyStore( trustStoreName,
				       (wrp_trustStorePass==null?new char[0]:wrp_trustStorePass.getString().toCharArray()),
				       (wrp_trustStoreType==null?null:wrp_trustStoreType.getString())
				     );
	    
	}

	
	TrustManagerFactory trustManagerFactory = null;
	if( trustStore != null ) {
	    logger.log( Level.INFO,
			getClass().getName() + ".createSSLServerSocket(...)",
			"Creating trust manager factory ... " );
	    trustManagerFactory = TrustManagerFactory.getInstance( "SunX509" );
	    trustManagerFactory.init( trustStore ); 
	}


	   
	logger.log( Level.INFO,
		    getClass().getName() + ".createSSLServerSocket(...)",
		    "Creating key manager factory ... " );
	KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
	keyManagerFactory.init( keyStore, (wrp_keyStorePass == null ? new char[0] : wrp_keyStorePass.getString().toCharArray()) );

	

	logger.log( Level.INFO,
		    getClass().getName() + ".createSSLServerSocket(...)",
		    "Creating SSL context (SSLv3) ... " );
	SSLContext sslcontext = SSLContext.getInstance("SSLv3");
	if( trustManagerFactory != null )  
	    sslcontext.init( keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);
	else                               
	    sslcontext.init( keyManagerFactory.getKeyManagers(), null, null);


	logger.log( Level.INFO,
		    getClass().getName() + ".createSSLServerSocket(...)",
		    "Creating server socket factory ... " );
	ServerSocketFactory ssf = sslcontext.getServerSocketFactory();


	logger.log( Level.INFO,
		    getClass().getName() + ".createSSLServerSocket(...)",
		    "Creating server socket ... " );
	SSLServerSocket serversocket = (SSLServerSocket)ssf.createServerSocket( bindPort );
	return serversocket;
	

	//return null;

    }
    
    /**
     * This method fetches the 'value' entry from the SSL settings where the 'name' equals the
     * passed key-name.
     *
     * If the key cannot be found, the method returns null.
     **/
    private BasicType getSSLConfigPropertyValue( Environment<String,BasicType> bindSettings,
						 String keyName ) {

	
	List<Environment<String,BasicType>> children = bindSettings.getChildren( Constants.CONFIG_SERVER_LISTEN_PROPERTY );
	Iterator<Environment<String,BasicType>> iter = children.iterator();
	while( iter.hasNext() ) {

	    Environment<String,BasicType> child = iter.next();
	    BasicType property = child.get( Constants.CONFIG_SERVER_LISTEN_PROPERTY_NAME ); 
	    if( property.getString().equalsIgnoreCase(keyName) ) {

		//System.out.println( "--- SSL property '"+keyName+"' loaded: " + child.get("value") );
		return child.get( Constants.CONFIG_SERVER_LISTEN_PROPERTY_VALUE );

	    }

	}
	
	return null;

    }

    private KeyStore loadKeyStore( String fileName,
				   char[] password,
				   String keyStoreType ) 
	throws KeyStoreException,
	       FileNotFoundException,
	       IOException,
	       NoSuchAlgorithmException,
	       CertificateException {

	if( keyStoreType == null )
	    keyStoreType = "JKS";

	/*this.logger.log( Level.INFO,
			   getClass().getName() + ".createSSLServerSocket(...)",
			   "Initializing key store '" + fileName + "' ... " );
	*/
	KeyStore keyStore = KeyStore.getInstance( keyStoreType );
	keyStore.load( new FileInputStream(fileName), 
		       ( password == null ? new char[0] : password )
		       ); 
    
	return keyStore;
    }

    public UUID getUUID() {
	return this.uuid;
    }

    public InetAddress getBindAddress() {
	return this.bindAddress;
    }

    public int getBindPort() {
	return this.bindPort;
    }

    public Map<String,BasicType> getServerSettings() {
	return this.bindSettings;
    }
    

    public void run() {
	
	try {
	    while( !this.isInterrupted() && !this.serverSocket.isClosed() ) {

		Socket sock = this.serverSocket.accept();
		    
		// Increase connection counter
		BasicType count = this.bindSettings.get(Constants.KEY_CONNECTION_COUNT);
		if( count == null )
		    count = new BasicNumberType( 1 );
		else
		    count = new BasicNumberType( count.getInt() + 1 );
		this.bindSettings.put( Constants.KEY_CONNECTION_COUNT, count );
		    
		//System.out.println( getClass().getName() + ".run() Telling observer about incoming TCP connection ..." );
		this.observer.incomingTCPConnection( this, sock );
		//System.out.println( getClass().getName() + ".run() Done." );
		
		
	    }
	    // END while: server was closed or thread was interrupted

	    if( !this.serverSocket.isClosed() ) 
		closeServerSocket();
	    else //if( !this.isInterrupted() )
		this.observer.serverSocketClosed( this );

	} catch( SocketTimeoutException e ) {

	    if( !this.isInterrupted() )
		this.observer.serverSocketException( this, e );

	} catch( IOException e ) {
	    
	    if( !this.isInterrupted() )
		this.observer.serverSocketException( this, e );

	} catch( SecurityException e ) {
	    
	    if( !this.isInterrupted() )
		this.observer.serverSocketException( this, e );

	} catch( IllegalBlockingModeException e ) {

	    if( !this.isInterrupted() )
		this.observer.serverSocketException( this, e );
	    
	}
	
    }

    public void interrupt() {

	super.interrupt();

	try {
	    closeServerSocket();
	} catch( IOException e ) {
	    // Ignore
	}

    }

    private void closeServerSocket() 
	throws IOException {
	if( this.serverSocket.isClosed() )
	    return; // false;

	//try {

	    this.serverSocket.close();
	    //this.observer.serverSocketClosed( this );
	    //return true;
	    
	    //} catch( IOException e ) {
	    
	    //this.observer.serverSocketException( this, e );
	    //return false;

	    //}
    }

    public void finalize() {
	// ..!
    }



    public String getStatusString() {
	StringBuffer b = new StringBuffer();
	b.append( "bindAddress=" ).append(this.bindAddress.toString() ).append( ",\n" ).
	    append( "bindPort=" ).append( this.bindPort ).append( ",\n" );
	this.bindSettingsToSecureStatusString( null, this.bindSettings, b ).append( ",\n" );
	//b.append( "bindSettings=" ).append( this.bindSettings.toString() ).append( ",\n" ).
	b.append( "uuid=" ).append( this.uuid );
	return b.toString();
    }

    private StringBuffer bindSettingsToSecureStatusString( String childName,
							   Environment<String,BasicType> child,
							   StringBuffer b ) {

	// Warning: do NOT print the SSL keyStore- and trustStore passwords!
	//          (otherwise they would appear in the log files in plain text)
	if( childName != null 
	    &&
	    childName.equalsIgnoreCase(Constants.CONFIG_SERVER_LISTEN_PROPERTY) ) {

	    BasicType wrp_propertyName = child.get( Constants.CONFIG_SERVER_LISTEN_PROPERTY_NAME );
	    String propertyName        = wrp_propertyName.getString();
	    if( propertyName.equalsIgnoreCase(Constants.CONFIG_SERVER_SSL_KEYSTOREPASSWORD) 
		|| 
		propertyName.equalsIgnoreCase(Constants.CONFIG_SERVER_SSL_TRUSTSTOREPASSWORD) ) {

		b.append( childName + ": { ****** }" );
		return b;

	    }

	}

	// else: print normal
	if( childName != null )
	    b.append( childName ).append( ": { " );

	Iterator<Map.Entry<String,BasicType>> iter = child.entrySet().iterator();
	int i = 0;
	while( iter.hasNext() ) {

	    Map.Entry<String,BasicType> entry = iter.next();
	    
	    if( entry.getKey() != null ) {

		if( i > 0 )
		    b.append( ", " );

		if( entry.getKey().equalsIgnoreCase(Constants.CONFIG_SERVER_SSL_KEYSTOREPASSWORD) ) {
		    b.append( entry.getKey() ).append( "=" ).append( "******" );
		} else if( entry.getKey().equalsIgnoreCase(Constants.CONFIG_SERVER_SSL_TRUSTSTOREPASSWORD) ) {
		    b.append( entry.getKey() ).append( "=" ).append( "******" );
		} else {
		    b.append( entry.getKey() ).append( "=" ).append( entry.getValue() );
		}

	    } // END if

	    i++;

	} // END while

	if( childName != null )
	    b.append( " }" );


	// Print all 'property' children (recursive call).
	List<Environment<String,BasicType>> properties = child.getChildren(Constants.CONFIG_SERVER_LISTEN_PROPERTY);
	for( i = 0; i < properties.size(); i++ ) {

	    //if( i > 0 )
	          b.append( ",\n" );
	    b.append( " " );
	    bindSettingsToSecureStatusString( Constants.CONFIG_SERVER_LISTEN_PROPERTY,
					      properties.get(i),
					      b );

	}
	    

	return b;
    }

}
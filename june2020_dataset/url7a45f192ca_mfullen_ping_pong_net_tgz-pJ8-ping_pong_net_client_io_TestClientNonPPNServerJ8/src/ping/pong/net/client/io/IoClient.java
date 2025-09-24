package ping.pong.net.client.io;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import javax.net.SocketFactory;
import javax.net.ssl.HandshakeCompletedEvent;
import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.SSLSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ping.pong.net.client.Client;
import ping.pong.net.client.ClientConnectionListener;
import ping.pong.net.connection.Connection;
import ping.pong.net.connection.ConnectionEvent;
import ping.pong.net.connection.DisconnectInfo;
import ping.pong.net.connection.DisconnectState;
import ping.pong.net.connection.config.ConnectionConfigFactory;
import ping.pong.net.connection.config.ConnectionConfiguration;
import ping.pong.net.connection.io.DataReader;
import ping.pong.net.connection.io.DataWriter;
import ping.pong.net.connection.messaging.Envelope;
import ping.pong.net.connection.messaging.MessageListener;
import ping.pong.net.connection.ssl.SSLUtils;

/**
 * The Io Client Implementation of the Client interface.
 *
 * @param <Message>
 * @author mfullen
 */
public class IoClient<Message> implements Client<Message>
{
    /**
     * The logger being user for this class
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(IoClient.class);
    /**
     * Invalid connection id
     */
    private static final int INVALID_CONNECTION_ID = -1;
    /**
     * The Connection for the Client
     */
    protected Connection<Message> connection = null;
    /**
     * The connection Configuration used when attempting to create a connection
     */
    protected ConnectionConfiguration config = null;
    /**
     * The list of Message listeners for this client
     */
    protected List<MessageListener> messageListeners = new ArrayList<MessageListener>();
    /**
     * The list of ConnectionListeners for this client
     */
    protected List<ClientConnectionListener> connectionListeners = new ArrayList<ClientConnectionListener>();
    /**
     * Allows for the use of a custom Data reader
     */
    protected DataReader customDataReader = null;
    /**
     * The custom Data Writer to be used
     */
    protected DataWriter customDataWriter = null;

    /**
     * Constructor for a default IoClient Implementation. Creates it based of
     * defaults for a Connection Configuration
     */
    public IoClient()
    {
        this(ConnectionConfigFactory.createConnectionConfiguration());
    }

    /**
     * Creates a Client Implementation based off the given
     * ConnectionConfiguration
     *
     * @param config the configuration to use
     */
    public IoClient(ConnectionConfiguration config)
    {
        this.config = config;
    }

    /**
     * getConnection creates a new connection if one doesn't exist. Attempts to
     * connect right away. If the connection fails to connect it returns null If
     * a connection is already active it returns that connection
     *
     * @return
     */
    protected final Connection<Message> getConnection()
    {
        if (this.connection == null)
        {
            LOGGER.warn("Creating new connection");
            try
            {
                SocketFactory factory = config.isSsl()
                        ? SSLUtils.createSSLContext(config).getSocketFactory()
                        : SocketFactory.getDefault();

                Socket tcpSocket = factory.createSocket(config.getIpAddress(), config.getPort());

                if (config.isSsl())
                {
                    SSLSocket sslSocket = (SSLSocket) tcpSocket;
                    sslSocket.addHandshakeCompletedListener(new HandshakeCompletedListener()
                    {
                        @Override
                        public void handshakeCompleted(HandshakeCompletedEvent hce)
                        {
                            LOGGER.debug("Handshake Completed");
                        }
                    });
                    sslSocket.startHandshake();
                }

                DatagramSocket udpSocket = null;
                InetSocketAddress localSocketAddress = new InetSocketAddress(0);
                udpSocket = config.getUdpPort() == ConnectionConfiguration.UDP_DISABLED ? null : new DatagramSocket(localSocketAddress);

                //if we have a custom data reader or writer use the ClientIoNonPPNConnection
                boolean customSerialization = (this.customDataReader != null || this.customDataWriter != null);
                this.connection = customSerialization
                        ? ClientIoConnectionFactory.<Message>createNonPPNConnection(config, customDataReader, customDataWriter, tcpSocket, udpSocket)
                        : ClientIoConnectionFactory.<Message>createPPNConnection(config, tcpSocket, udpSocket);

                this.connection.addConnectionEventListener(new ConnectionEventImpl());
            }
            catch (IOException ex)
            {
                LOGGER.error("Error creating Client Socket", ex);
                //todo add error handling to display why it couldnt connect
                return null;
            }
        }

        return this.connection;
    }

    @Override
    public void start()
    {
        this.connection = this.getConnection();
        if (this.connection == null)
        {
            LOGGER.error("Failed to connect to {} on port {}", this.config.getIpAddress(), this.config.getPort());
        }
        else if (this.connection.isConnected())
        {
            LOGGER.warn("Can't start connection it is already running");
        }
        else
        {
            Thread connectionThread = new Thread(this.connection, "IoClientConnection");
            connectionThread.start();
            LOGGER.info("Client connected to server {} on TCP port {}", this.config.getIpAddress(), this.config.getPort());
        }
    }

    @Override
    public void close()
    {
        if (this.connection == null)
        {
            LOGGER.error("Connection is null");
            return;
        }
        this.connection.close();
        LOGGER.info("Client Closed");
        this.connection = null;
    }

    @Override
    public boolean isConnected()
    {
        return this.connection != null && this.connection.isConnected();
    }

    @Override
    public int getId()
    {
        if (isConnected())
        {
            return this.connection.getConnectionId();
        }
        return INVALID_CONNECTION_ID;
    }

    @Override
    public void addMessageListener(MessageListener<? super Client, Message> listener)
    {
        boolean added = false;
        if (listener != null)
        {
            added = this.messageListeners.add(listener);
        }
        LOGGER.trace("Add Message Listener: {}", added);
    }

    @Override
    public void removeMessageListener(MessageListener<? super Client, Message> listener)
    {
        boolean removed = false;
        if (listener != null)
        {
            removed = this.messageListeners.remove(listener);
        }
        LOGGER.trace("Remove Message Listener: {}", removed);
    }

    @Override
    public void addConnectionListener(ClientConnectionListener listener)
    {
        boolean added = false;
        if (listener != null)
        {
            added = this.connectionListeners.add(listener);
        }
        LOGGER.trace("Add Connection Listener: {}", added);
    }

    @Override
    public void removeConnectionListener(ClientConnectionListener listener)
    {
        boolean removed = false;
        if (listener != null)
        {
            removed = this.connectionListeners.remove(listener);
        }
        LOGGER.trace("Remove Connection Listener: {}", removed);
    }

    @Override
    public void sendMessage(Envelope<Message> message)
    {
        if (this.connection == null)
        {
            LOGGER.error("Connection is null. Please start the connection first and try again");
        }
        else if (this.connection.isConnected())
        {
            this.connection.sendMessage(message);
        }
        else
        {
            LOGGER.warn("Cannot Send message, The client is not connected");
        }
    }

    /**
     *
     * @param customDataReader
     */
    public void setCustomDataReader(DataReader customDataReader)
    {
        this.customDataReader = customDataReader;
    }

    /**
     *
     * @param customDataWriter
     */
    public void setCustomDataWriter(DataWriter customDataWriter)
    {
        this.customDataWriter = customDataWriter;
    }

    final class ConnectionEventImpl implements ConnectionEvent<Message>
    {
        public ConnectionEventImpl()
        {
        }

        @Override
        public void onSocketClosed()
        {
            for (ClientConnectionListener clientConnectionListener : connectionListeners)
            {
                clientConnectionListener.clientDisconnected(IoClient.this, new DisconnectInfoImpl());
            }
        }

        @Override
        public void onSocketCreated()
        {
            for (ClientConnectionListener clientConnectionListener : connectionListeners)
            {
                clientConnectionListener.clientConnected(IoClient.this);
            }
        }

        @Override
        public synchronized void onSocketReceivedMessage(Message message)
        {
            for (MessageListener<? super Client<Message>, Message> messageListener : messageListeners)
            {
                messageListener.messageReceived(IoClient.this, message);
            }
        }

        final class DisconnectInfoImpl implements DisconnectInfo
        {
            public DisconnectInfoImpl()
            {
            }

            @Override
            public String getReason()
            {
                return "something is wrong";
            }

            @Override
            public DisconnectState getDisconnectState()
            {
                return DisconnectState.ERROR;
            }
        }
    }
}

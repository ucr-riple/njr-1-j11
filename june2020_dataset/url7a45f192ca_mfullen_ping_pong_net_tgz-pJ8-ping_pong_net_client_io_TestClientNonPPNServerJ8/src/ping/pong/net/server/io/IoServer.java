package ping.pong.net.server.io;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ping.pong.net.connection.Connection;
import ping.pong.net.connection.config.ConnectionConfigFactory;
import ping.pong.net.connection.config.ConnectionConfiguration;
import ping.pong.net.connection.io.DataReader;
import ping.pong.net.connection.io.DataWriter;
import ping.pong.net.connection.messaging.Envelope;
import ping.pong.net.connection.messaging.MessageListener;
import ping.pong.net.server.Server;
import ping.pong.net.server.ServerConnectionListener;

/**
 *
 * @param <MessageType>
 * @author mfullen
 */
public class IoServer<MessageType> implements
        Server<MessageType>
{
    /**
     *
     */
    public static final Logger LOGGER = LoggerFactory.getLogger(IoServer.class);
    /**
     *
     */
    protected Map<Integer, Connection> connectionsMap = new ConcurrentHashMap<Integer, Connection>();
    /**
     *
     */
    protected ServerConnectionManager<MessageType> serverConnectionManager = null;
    /**
     *
     */
    protected ConnectionConfiguration config = null;
    /**
     *
     */
    protected List<MessageListener> messageListeners = new ArrayList<MessageListener>();
    /**
     *
     */
    protected List<ServerConnectionListener> connectionListeners = new ArrayList<ServerConnectionListener>();
    /**
     *
     */
    protected DataReader customDataReader = null;
    /**
     *
     */
    protected DataWriter customDataWriter = null;

    /**
     *
     */
    public IoServer()
    {
        this(ConnectionConfigFactory.createConnectionConfiguration());
    }

    /**
     *
     * @param config
     */
    public IoServer(ConnectionConfiguration config)
    {
        this.config = config;
    }

    @Override
    public void broadcast(Envelope<MessageType> message)
    {
        for (Connection connection : this.connectionsMap.values())
        {
            connection.sendMessage(message);
            LOGGER.trace("Broadcasting Message: {} to Connection ({})", message, connection.getConnectionId());
        }
    }

    @Override
    public void start()
    {
        if (serverConnectionManager != null)
        {
            LOGGER.error("Cannot start server. It is already running");
            return;
        }
        this.serverConnectionManager = new ServerConnectionManager(config, this);
        this.serverConnectionManager.setCustomDataReader(customDataReader);
        this.serverConnectionManager.setCustomDataWriter(customDataWriter);
        new Thread(this.serverConnectionManager).start();
        LOGGER.info("Server started {} on port {}", config.getIpAddress(), config.getPort());
    }

    @Override
    public void shutdown()
    {
        if (serverConnectionManager == null)
        {
            LOGGER.error("Server Connection Manager is null.");
            return;
        }

        this.serverConnectionManager.shutdown();
        this.serverConnectionManager = null;
        LOGGER.info("Server shutdown");
    }

    @Override
    public Connection getConnection(int id)
    {
        Connection connection = this.connectionsMap.get(id);
        if (connection == null)
        {
            LOGGER.error("Connection Id {} not found.", id);
        }
        return connection;
    }

    @Override
    public Collection<Connection> getConnections()
    {
        return Collections.unmodifiableCollection(this.connectionsMap.values());
    }

    @Override
    public boolean hasConnections()
    {
        return !this.connectionsMap.isEmpty();
    }

    @Override
    public boolean isListening()
    {
        return serverConnectionManager != null && serverConnectionManager.isListening();
    }

    @Override
    public void addMessageListener(MessageListener<? super Connection, MessageType> listener)
    {
        boolean added = false;
        if (listener != null)
        {
            added = this.messageListeners.add(listener);
        }
        LOGGER.trace("Add Message Listener: {}", added);
    }

    @Override
    public void removeMessageListener(MessageListener<? super Connection, MessageType> listener)
    {
        boolean removed = false;
        if (listener != null)
        {
            removed = this.messageListeners.remove(listener);
        }
        LOGGER.trace("Remove Message Listener: {}", removed);
    }

    @Override
    public void addConnectionListener(ServerConnectionListener connectionListener)
    {
        boolean added = false;
        if (connectionListener != null)
        {
            added = this.connectionListeners.add(connectionListener);
        }
        LOGGER.trace("Add Connection Listener: {}", added);
    }

    @Override
    public void removeConnectionListener(ServerConnectionListener connectionListener)
    {
        boolean removed = false;
        if (connectionListener != null)
        {
            removed = this.connectionListeners.remove(connectionListener);
        }
        LOGGER.trace("Remove Connection Listener: {}", removed);
    }

    private void logCurrentConnections()
    {
        if (LOGGER.isTraceEnabled())
        {
            LOGGER.trace("Current Connections: {}", this.getConnections());
        }
    }

    /**
     *
     * @param connection
     */
    synchronized void addConnection(Connection<MessageType> connection)
    {
        this.logCurrentConnections();
        this.connectionsMap.put(connection.getConnectionId(), connection);
        LOGGER.trace("Adding Connection ({}) to connectionmap ", connection.getConnectionId());
        this.logCurrentConnections();
        for (ServerConnectionListener serverConnectionListener : this.connectionListeners)
        {
            serverConnectionListener.connectionAdded(this, connection);
        }
    }

    /**
     *
     * @param connection
     */
    synchronized void removeConnection(Connection<MessageType> connection)
    {
        if (connection == null)
        {
            LOGGER.warn("Cannot remove null connection");
            return;
        }

        this.logCurrentConnections();
        int connectionId = connection.getConnectionId();
        this.connectionsMap.remove(connectionId);
        LOGGER.trace("Removing Connection ({}) to connectionmap ", connectionId);
        this.logCurrentConnections();

        LOGGER.info("Connection ({}) has disconnected.", connectionId);

        for (ServerConnectionListener serverConnectionListener : this.connectionListeners)
        {
            serverConnectionListener.connectionRemoved(this, connection);
        }
    }

    /**
     *
     * @param id
     */
    synchronized void removeConnection(int id)
    {
        Connection connection = this.connectionsMap.get(id);
        this.removeConnection(connection);
    }

    @Override
    public synchronized int getNextAvailableId()
    {
        int id = 1;

        idLabel:
        while (true)
        {
            if (!this.connectionsMap.containsKey(id))
            {
                break idLabel;
            }
            id++;
        }
        return id;
    }

    /**
     * Set the custom Data Reader used to read messages from a TCP socket
     *
     * @param customDataReader the reader to set
     */
    public void setCustomDataReader(DataReader customDataReader)
    {
        this.customDataReader = customDataReader;
    }

    /**
     * Set the custom Data writer used to write messages to a TCP socket
     *
     * @param customDataWriter
     */
    public void setCustomDataWriter(DataWriter customDataWriter)
    {
        this.customDataWriter = customDataWriter;
    }
}

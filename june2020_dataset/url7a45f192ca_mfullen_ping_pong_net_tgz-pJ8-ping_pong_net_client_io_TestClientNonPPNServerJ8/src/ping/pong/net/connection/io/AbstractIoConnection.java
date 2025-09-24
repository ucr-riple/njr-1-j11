package ping.pong.net.connection.io;

import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ping.pong.net.connection.Connection;
import ping.pong.net.connection.ConnectionEvent;
import ping.pong.net.connection.DisconnectState;
import ping.pong.net.connection.RunnableEventListener;
import ping.pong.net.connection.config.ConnectionConfiguration;
import ping.pong.net.connection.messaging.DisconnectMessage;
import ping.pong.net.connection.messaging.Envelope;
import ping.pong.net.connection.messaging.MessageProcessor;

/**
 * AbstractIoConnection represents a basic Io Connection
 *
 * @param <MessageType>
 * @author mfullen
 */
public abstract class AbstractIoConnection<MessageType> implements
        Connection<MessageType>,
        MessageProcessor<MessageType>
{
    /**
     * The LOGGER to use in the class
     */
    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractIoConnection.class);
    /**
     * The maximum number of threads that should be spawned per connection
     */
    private static final int MAX_THREAD_POOL_SIZE = 4;
    /**
     * The ConnectionConfiguration this connection is using
     */
    protected ConnectionConfiguration config = null;
    /**
     * Flag for whether this connection is actually connected to a socket
     */
    protected boolean connected = false;
    /**
     * The id of the connection
     */
    protected int connectionId = -1;
    /**
     * A list of Connection Events
     */
    protected List<ConnectionEvent> connectionEventListeners = new ArrayList<ConnectionEvent>();
    /**
     * This connection's TcpReadThread
     */
    protected IoTcpReadRunnable<MessageType> ioTcpReadRunnable = null;
    /**
     * This connection's TcpWriteThread
     */
    protected IoTcpWriteRunnable<MessageType> ioTcpWriteRunnable = null;
    /**
     * This connections queue of received messages to process
     */
    protected BlockingQueue<MessageType> receiveQueue = new LinkedBlockingQueue<MessageType>();
    /**
     * The ExecutorService controls the thread pool for read and write
     */
    protected ExecutorService executorService = Executors.newFixedThreadPool(MAX_THREAD_POOL_SIZE);
    /**
     * The Tcp Socket used for this connection
     */
    protected Socket tcpSocket = null;
    /**
     *
     */
    protected boolean usingCustomSerialization = true;
    /**
     * Flag for whether the connection has been closed
     */
    protected boolean closed = false;
    /**
     * Flag to let the connection know all initialization has passed and it can start
     */
    protected boolean canStart = true;
    /**
     * Flag for whether UDP is enabled or not
     */
    protected boolean udpEnabled = false;
    /**
     *
     */
    protected SocketAddress socketAddress = null;
    /**
     *  Data reader.
     */
    private DataReader dataReader = null;
    /**
     *  Data Writer
     */
    private DataWriter dataWriter = null;

    /**
     *
     * @param config
     * @param dataReader
     * @param dataWriter
     * @param tcpSocket
     */
    public AbstractIoConnection(ConnectionConfiguration config, DataReader dataReader, DataWriter dataWriter, Socket tcpSocket)
    {
        this.config = config;
        this.tcpSocket = tcpSocket;
        this.dataReader = dataReader;
        this.dataWriter = dataWriter;
        boolean initTcp = this.initTcp();
        this.canStart = initTcp;
    }

    /**
     *
     * @param message
     */
    protected abstract void processMessage(MessageType message);

    /**
     * Start Udp Connection
     */
    protected abstract void startUdp();

    /**
     * Close Udp connection. Even though UDP is connectionless we are keeping a state
     */
    protected abstract void closeUdp();

    /**
     * This method should enqueue a Message to the UDP write thread
     *
     * @param msg the message to enqueue for sending
     */
    protected abstract void sendUdpMessage(MessageType msg);

    /**
     * Method to initialize a TCP connection. Creates read and Write threads for
     * TCP
     *
     * @return true if the initiation is a success, false otherwise.
     */
    protected final boolean initTcp()
    {
        boolean successful = true;

        //if the custom reader/writer are null, create default
        boolean hasDataReader = this.dataReader != null;
        boolean hasDataWriter = this.dataWriter != null;

        if (!hasDataReader && !hasDataWriter)
        {
            this.usingCustomSerialization = false;
            dataReader = new ReadObjectDataReader();
            dataWriter = new WriteObjectDataWriter();
            LOGGER.trace("Using default Serialization for TCP reader and writer");
            successful = true;
        }
        else if (hasDataReader && hasDataWriter)
        {
            LOGGER.trace("Using Custom serialization for Tcp reader and writer");
            successful = true;
        }
        else if (hasDataReader && !hasDataWriter)
        {
            LOGGER.warn("A custom DataReader was set but missing custom DataWriter");
            successful = false;
        }
        else if (!hasDataReader && hasDataWriter)
        {
            LOGGER.warn("A custom DataWriter was set but missing custom DataReader");
            successful = false;
        }

        if (this.tcpSocket == null)
        {
            successful = false;
        }
        else
        {
            this.socketAddress = this.tcpSocket.getRemoteSocketAddress();
        }

        this.ioTcpReadRunnable = new IoTcpReadRunnable<MessageType>(this, new RunnableEventListener()
        {
            @Override
            public void onRunnableClosed(DisconnectState disconnectState)
            {
                LOGGER.trace("IoTcpReadRunnable closed {}", disconnectState);
                disconnect();
                ioTcpReadRunnable = null;
            }
        }, dataReader, tcpSocket);
        this.ioTcpWriteRunnable = new IoTcpWriteRunnable<MessageType>(new RunnableEventListener()
        {
            @Override
            public void onRunnableClosed(DisconnectState disconnectState)
            {
                LOGGER.trace("ioTcpWriteRunnable closed {}", disconnectState);
                disconnect();
                ioTcpWriteRunnable = null;
            }
        }, dataWriter, tcpSocket);

        return successful;
    }

    @Override
    public boolean isUsingCustomSerialization()
    {
        return usingCustomSerialization;
    }

    /**
     *
     */
    protected void disconnect()
    {
        this.receiveQueue.add((MessageType) new DisconnectMessage());
    }

    @Override
    public synchronized SocketAddress getSocketAddress()
    {
        if (this.socketAddress == null)
        {
            throw new NullPointerException("Socket not connected yet");
        }
        return this.socketAddress;
    }

    /**
     * Fire the on socket created event for all listeners
     */
    protected synchronized void fireOnSocketCreated()
    {
        for (ConnectionEvent connectionEvent : connectionEventListeners)
        {
            connectionEvent.onSocketCreated();
        }
    }

    /**
     * Fire the on socketMessage Received event for all listeners
     * @param message
     */
    protected synchronized void fireOnSocketMessageReceived(MessageType message)
    {
        for (ConnectionEvent<MessageType> connectionEvent : connectionEventListeners)
        {
            connectionEvent.onSocketReceivedMessage(message);
        }
    }

    /**
     *
     */
    protected synchronized void fireOnSocketClosed()
    {
        for (ConnectionEvent connectionEvent : this.connectionEventListeners)
        {
            connectionEvent.onSocketClosed();
        }
    }

    /**
     *
     */
    protected void startConnection()
    {
        if (this.canStart)
        {
            this.executorService.execute(ioTcpWriteRunnable);
            this.executorService.execute(ioTcpReadRunnable);

            this.startUdp();

            this.connected = true;
            LOGGER.trace("Connection started successfully.");
        }
        else
        {
            LOGGER.error("This connection cannot start because it was not initialized properly.");
        }
    }

    @Override
    public void run()
    {
        startConnection();

        while (this.isConnected())
        {
            try
            {
                LOGGER.trace("({}) About to block to Take message off queue", this.getConnectionId());
                MessageType message = this.receiveQueue.take();

                LOGGER.trace("({}) Message taken to be processed ({})", this.getConnectionId(), message);
                this.processMessage(message);
            }
            catch (InterruptedException ex)
            {
                LOGGER.error("Error processing Receive Message queue", ex);
            }
        }

        //Connection is done, try to properly close and cleanup
        LOGGER.debug("{} Main Connection thread calling close", getConnectionName());
        this.close();
    }

    /**
     *
     * @return
     */
    protected String getConnectionName()
    {
        return "Connection (" + this.getConnectionId() + "):";
    }

    /**
     *
     * @return
     */
    protected boolean isAnyRunning()
    {
        return this.isTcpReadRunning() || this.isTcpWriteRunning();
    }

    /**
     *
     * @return
     */
    protected boolean isTcpWriteRunning()
    {
        return this.ioTcpWriteRunnable != null && this.ioTcpWriteRunnable.isRunning();
    }

    /**
     *
     * @return
     */
    protected boolean isTcpReadRunning()
    {
        return this.ioTcpReadRunnable != null && this.ioTcpReadRunnable.isRunning();
    }

    @Override
    public synchronized void close()
    {
        if (!this.canStart)
        {
            LOGGER.warn("Connection cannot be closed, it never started");
            return;
        }
        if (this.closed)
        {
            LOGGER.warn("Connection is already Closed");
            return;
        }

        this.connected = false;
        if (this.isAnyRunning())
        {
            this.fireOnSocketClosed();
            disconnect();

            if (this.isTcpWriteRunning())
            {
                this.ioTcpWriteRunnable.close();
            }

            if (this.isTcpReadRunning())
            {
                this.ioTcpReadRunnable.close();
            }

            this.closeUdp();
        }

        LOGGER.info("Connection ({}) has been closed", this.getConnectionId());
        this.closed = true;
    }

    @Override
    public synchronized boolean isConnected()
    {
        return this.connected;
    }

    @Override
    public synchronized int getConnectionId()
    {
        return this.connectionId;
    }

    @Override
    public synchronized void setConnectionId(int connectionId)
    {
        this.connectionId = connectionId;
    }

    @Override
    public void sendMessage(Envelope<MessageType> message)
    {
        this.enqueueMessageToWrite(message);
        LOGGER.debug("Preparing to send Message {}", message);
    }

    /**
     * This method enqueues a message on the TcpWrite thread for sending
     *
     * @param msg the message to enqueue for sending
     */
    protected void sendTcpMessage(MessageType msg)
    {
        if (this.ioTcpWriteRunnable != null)
        {
            LOGGER.trace("Enqueued {} TCP Message to write", msg);
            boolean enqueueMessage = this.ioTcpWriteRunnable.enqueueMessage(msg);
            LOGGER.trace("Message Enqueued {}", enqueueMessage);
        }
        else
        {
            LOGGER.trace("IoTcpWrite is null");
        }
    }

    @Override
    public boolean isUdpEnabled()
    {
        return udpEnabled;
    }

    @Override
    public void enqueueReceivedMessage(MessageType message)
    {
        boolean add = this.receiveQueue.add(message);
        LOGGER.trace("Enqueued message {}", add);
    }

    @Override
    public void enqueueMessageToWrite(Envelope<MessageType> message)
    {
        if (message.isReliable())
        {
            sendTcpMessage(message.getMessage());
        }
        else
        {
            sendUdpMessage(message.getMessage());
        }
    }

    @Override
    public void addConnectionEventListener(ConnectionEvent listener)
    {
        this.connectionEventListeners.add(listener);
    }

    @Override
    public void removeConnectionEventListener(ConnectionEvent listener)
    {
        this.connectionEventListeners.remove(listener);
    }
}

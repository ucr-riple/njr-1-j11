package ping.pong.net.server.io;

import java.io.IOException;
import java.io.StreamCorruptedException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.net.ServerSocketFactory;
import javax.net.ssl.HandshakeCompletedEvent;
import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ping.pong.net.connection.*;
import ping.pong.net.connection.config.ConnectionConfiguration;
import ping.pong.net.connection.io.AbstractIoUdpRunnable;
import ping.pong.net.connection.io.DataReader;
import ping.pong.net.connection.io.DataWriter;
import ping.pong.net.connection.messaging.ConnectionIdMessage;
import ping.pong.net.connection.messaging.EnvelopeFactory;
import ping.pong.net.connection.messaging.MessageListener;
import ping.pong.net.connection.ssl.SSLUtils;

/**
 *
 * @author mfullen
 */
class ServerConnectionManager<MessageType> implements Runnable
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerConnectionManager.class);
    protected boolean listening = true;
    protected ConnectionConfiguration configuration;
    protected ServerSocket tcpServerSocket = null;
    protected DatagramSocket udpServerSocket = null;
    protected IoServer<MessageType> server = null;
    protected DataReader customDataReader = null;
    protected DataWriter customDataWriter = null;
    protected Map<SocketAddress, Connection> udpConnections = new ConcurrentHashMap<SocketAddress, Connection>();
    protected AbstractIoUdpRunnable ioUdpReadRunnable = null;

    public ServerConnectionManager(ConnectionConfiguration configuration, IoServer<MessageType> server)
    {
        this.configuration = configuration;
        this.server = server;
    }

    /**
     * Shutdown the connection manager and all connection threads.
     */
    public void shutdown()
    {
        if (!listening)
        {
            //logger.trace("The Connection Manager has already been shut down. This has no effect");
            return;
        }

        for (Connection connection : this.server.connectionsMap.values())
        {
            connection.close();
        }

        this.listening = false;
        this.configuration = null;

        try
        {
            if (tcpServerSocket != null)
            {
                tcpServerSocket.close();
            }
            else
            {
                LOGGER.warn("TCP Socket is null");
            }
        }
        catch (IOException ex)
        {
            LOGGER.error("Error Closing TCP socket");
        }

        if (udpServerSocket != null)
        {
            udpServerSocket.close();
        }
        else
        {
            LOGGER.warn("UDP Socket is null");
        }

        if (ioUdpReadRunnable != null)
        {
            ioUdpReadRunnable.close();
        }

        this.tcpServerSocket = null;
        this.udpServerSocket = null;
        this.ioUdpReadRunnable = null;
    }

    /**
     *
     * @return
     */
    public boolean isListening()
    {
        return listening;
    }

    @Override
    public void run()
    {
        try
        {
            ServerSocketFactory socketFactory = configuration.isSsl()
                    ? SSLUtils.createSSLContext(configuration).getServerSocketFactory()
                    : ServerSocketFactory.getDefault();

            try
            {
                tcpServerSocket = socketFactory.createServerSocket(configuration.getPort());
                tcpServerSocket.setReuseAddress(true);
            }
            catch (IOException ex)
            {
                LOGGER.error("Error creating TCP server socket. " + ex);
                listening = false;
            }

            try
            {
                udpServerSocket = this.configuration.getUdpPort() == ConnectionConfiguration.UDP_DISABLED ? null : new DatagramSocket(new InetSocketAddress(configuration.getUdpPort()));
                if (udpServerSocket != null)
                {
                    ioUdpReadRunnable = new AbstractIoUdpRunnableImpl<MessageType>(null, udpServerSocket, new UdpReceived<MessageType>()
                    {
                        @Override
                        public void UdpPacketReceived(SocketAddress socketAddress, MessageType message)
                        {
                            Connection<MessageType> connection = udpConnections.get(socketAddress);
                            if (connection != null && connection.isConnected())
                            {
                                ServerIoConnection<MessageType> serverC = (ServerIoConnection) connection;
                                serverC.enqueueReceivedMessage(message);
                                LOGGER.trace("UDP packet received, enqueuing message");
                            }
                        }
                    });
                    Thread thread = new Thread(ioUdpReadRunnable);
                    thread.setDaemon(true);
                    thread.start();
                }
            }
            catch (Exception e)
            {
                LOGGER.error("Error creating UDP server socket. " + e);
                listening = false;
                throw e;
            }

            //the UDP threadS need to be moved to the server connection manager. The manager needs to keep track of the connected
            //udp connections in a map
            while (listening)
            {
                LOGGER.trace("ServerConnectionManager about to block until connection accepted.");
                Socket acceptingSocket = null;
                try
                {
                    acceptingSocket = tcpServerSocket.accept();
                    if (configuration.isSsl())
                    {
                        SSLSocket sslAccepted = (SSLSocket) acceptingSocket;
                        sslAccepted.addHandshakeCompletedListener(new HandshakeCompletedListener()
                        {
                            @Override
                            public void handshakeCompleted(HandshakeCompletedEvent hce)
                            {
                                LOGGER.debug("Handshake Completed");
                            }
                        });
                        sslAccepted.startHandshake();
                    }
                }
                catch (IOException ex)
                {
                    ConnectionExceptionHandler.handleException(ex, LOGGER);
                    //TODO: the thread listening for connections probably
                    //shouldn't shut down if there is an exception accepting the
                    //socket
                    this.shutdown();
                }
                if (acceptingSocket != null)
                {
                    LOGGER.info("Accepting Socket info ip:{} port:{}", acceptingSocket.getInetAddress(), acceptingSocket.getPort());
                    final Connection ioServerConnection = new ServerIoConnection<MessageType>(configuration, customDataReader, customDataWriter, acceptingSocket, udpServerSocket);
                    ioServerConnection.setConnectionId(this.server.getNextAvailableId());

                    ioServerConnection.addConnectionEventListener(new ConnectionEventImpl(ioServerConnection));
                    ((ServerIoConnection) ioServerConnection).fireOnSocketCreated();
                    Thread cThread = new Thread(ioServerConnection, "Connection: " + ioServerConnection.getConnectionId());
                    cThread.setDaemon(true);
                    cThread.start();
                    this.server.addConnection(ioServerConnection);
                    this.udpConnections.put(ioServerConnection.getSocketAddress(), ioServerConnection);
                    LOGGER.info("Connection {} started...", ioServerConnection.getConnectionId());
                }
            }
        }
        catch (Exception exception)
        {
            ConnectionExceptionHandler.handleException(exception, LOGGER);
        }
        finally
        {
            this.shutdown();
        }
    }

    public void setCustomDataReader(DataReader customDataReader)
    {
        this.customDataReader = customDataReader;
    }

    public void setCustomDataWriter(DataWriter customDataWriter)
    {
        this.customDataWriter = customDataWriter;
    }

    final class ConnectionEventImpl implements ConnectionEvent<MessageType>
    {
        private final Connection ioServerConnection;

        public ConnectionEventImpl(Connection ioServerConnection)
        {
            this.ioServerConnection = ioServerConnection;
        }

        @Override
        public void onSocketClosed()
        {
            //remove the connection from the server
            if (server.hasConnections())
            {
                Connection connection = server.getConnection(ioServerConnection.getConnectionId());
                if (connection != null)
                {
                    server.removeConnection(ioServerConnection);
                    boolean containsValue = udpConnections.containsValue(ioServerConnection);
                    if (containsValue)
                    {
                        for (Map.Entry<SocketAddress, Connection> entry : udpConnections.entrySet())
                        {
                            if (entry.getValue().equals(ioServerConnection))
                            {
                                udpConnections.remove(entry.getKey());
                            }
                        }
                    }
                }

                LOGGER.trace("OnSocketClosed");
            }
        }

        @Override
        public void onSocketCreated()
        {
            if (!ioServerConnection.isUsingCustomSerialization())
            {
                ioServerConnection.sendMessage(EnvelopeFactory.createTcpEnvelope(new ConnectionIdMessage.ResponseMessage(ioServerConnection.getConnectionId())));
                LOGGER.debug("Using PPN Serialization, sending Id Response");
            }

            LOGGER.trace("OnSocketCreated");
        }

        @Override
        public void onSocketReceivedMessage(MessageType message)
        {
            for (MessageListener<Connection<MessageType>, MessageType> messageListener : server.messageListeners)
            {
                messageListener.messageReceived(ioServerConnection, message);
            }
        }
    }

    interface UdpReceived<MessageType>
    {
        void UdpPacketReceived(SocketAddress socketAddress, MessageType message);
    }

    private class AbstractIoUdpRunnableImpl<MessageType> extends AbstractIoUdpRunnable
    {
        private static final int RECEIVE_BUFFER_SIZE = 1024;
        private UdpReceived<MessageType> udpReceived = null;

        public AbstractIoUdpRunnableImpl(RunnableEventListener runnableEventListener, DatagramSocket udpSocket, UdpReceived<MessageType> udpReceived)
        {
            super(runnableEventListener, udpSocket);
            this.udpReceived = udpReceived;
        }

        @Override
        public void run()
        {
            this.running = true;
            byte[] data = new byte[RECEIVE_BUFFER_SIZE];
            this.setDisconnectState(DisconnectState.NORMAL);
            DatagramPacket packet = null;
            while (this.running)
            {
                MessageType messageType = null;
                byte[] trimmedBuffer = null;
                try
                {
                    packet = new DatagramPacket(data, data.length);
                    udpSocket.receive(packet);
                    byte[] receivedData = packet.getData();

                    LOGGER.trace("Received {} from {} on port {}", new Object[]
                            {
                                receivedData, packet.getAddress(),
                                packet.getPort()
                            });

                    trimmedBuffer = Arrays.copyOf(receivedData, packet.getLength());

                    //If the byte order is LittleEndian convert to BigEndian
                    ByteBuffer byteBuffer = ByteBuffer.allocate(trimmedBuffer.length);
                    LOGGER.trace("Byte order is {}", byteBuffer.order());
                    if (byteBuffer.order().equals(ByteOrder.LITTLE_ENDIAN))
                    {
                        trimmedBuffer = byteBuffer.order(ByteOrder.BIG_ENDIAN).array();
                        LOGGER.trace("Byte order converted to {}", ByteOrder.BIG_ENDIAN);
                    }

                    messageType = ConnectionUtils.<MessageType>getObject(trimmedBuffer);
                    LOGGER.trace("Message deserialized into: " + messageType);

                }
                catch (StreamCorruptedException streamCorruptedException)
                {
                    //The received message isn't an Object so process it as normal byte[]
                    messageType = (MessageType) trimmedBuffer;
                }
                catch (IOException ex)
                {
                    running = false;
                    this.setDisconnectState(ConnectionExceptionHandler.handleException(ex, LOGGER));
                    LOGGER.error("Error receiving UDP packet", ex);
                }
                catch (ClassNotFoundException ex)
                {
                    running = false;
                    this.setDisconnectState(DisconnectState.ERROR);
                    LOGGER.error("Error converting to object");
                }
                finally
                {
                    if (running && messageType != null)
                    {
                        udpReceived.UdpPacketReceived(packet.getSocketAddress(), messageType);
                    }
                }
            }
            this.close();
        }
    }
}

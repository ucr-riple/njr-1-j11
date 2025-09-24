package ping.pong.net.client.io;

import java.net.*;
import ping.pong.net.connection.DisconnectState;
import ping.pong.net.connection.RunnableEventListener;
import ping.pong.net.connection.config.ConnectionConfiguration;
import ping.pong.net.connection.io.*;
import ping.pong.net.connection.messaging.ConnectionIdMessage;
import ping.pong.net.connection.messaging.DisconnectMessage;

/**
 * Client Io Connection class. This class extends from AbstractIoConnection
 *
 * @author mfullen
 */
class ClientIoConnection<MessageType> extends AbstractIoConnection<MessageType>
{
    /**
     * The Udp Socket used for this connection
     */
    protected DatagramSocket udpSocket = null;
    /**
     * This connection's UdpReadThread
     */
    protected IoUdpReadRunnable<MessageType> ioUdpReadRunnable = null;
    /**
     * This connection's UdpWriteThread
     */
    protected IoUdpWriteRunnable<MessageType> ioUdpWriteRunnable = null;

    public ClientIoConnection(ConnectionConfiguration config, DataReader dataReader, DataWriter dataWriter, Socket tcpSocket, DatagramSocket udpSocket)
    {
        super(config, dataReader, dataWriter, tcpSocket);
        this.udpSocket = udpSocket;
    }

    /**
     * ProcessMessage on the client first checks if the message is of type
     * ConnectionIdMessage.ResponseMessage. ConnectionIdMessage.ResponseMessage
     * indicates that the Connection has received an Identifier from the server
     * and can be considered connected
     *
     * @param message the message being processed
     */
    @Override
    protected void processMessage(MessageType message)
    {
        if (message instanceof ConnectionIdMessage.ResponseMessage)
        {
            int id = ((ConnectionIdMessage.ResponseMessage) message).getId();
            this.setConnectionId(id);
            LOGGER.trace("Got Id from server {}", this.getConnectionId());

            //fire client connected event
            this.fireOnSocketCreated();
        }
        else if (message instanceof DisconnectMessage)
        {
            this.connected = false;
        }
        else
        {
            LOGGER.trace("({}) Message taken to be processed ({})", this.getConnectionId(), message);
            this.fireOnSocketMessageReceived(message);
        }
    }

    @Override
    protected void sendUdpMessage(MessageType msg)
    {
        if (this.ioUdpWriteRunnable != null)
        {
            LOGGER.trace("Enqueued {} UDP Message to write", msg);
            boolean enqueueMessage = this.ioUdpWriteRunnable.enqueueMessage(msg);
            LOGGER.trace("Message Enqueued {}", enqueueMessage);
        }
        else
        {
            LOGGER.trace("IoUdpWrite is null");
        }
    }

    @Override
    protected boolean isAnyRunning()
    {
        return super.isAnyRunning() || this.isUdpReadRunning() || this.isUdpWriteRunning();
    }

    protected boolean isUdpReadRunning()
    {
        return this.ioUdpReadRunnable != null && this.ioUdpReadRunnable.isRunning();
    }

    protected boolean isUdpWriteRunning()
    {
        return this.ioUdpWriteRunnable != null && this.ioUdpWriteRunnable.isRunning();
    }

    protected final boolean initUdp()
    {
        boolean successful = true;
        try
        {
            if (this.udpSocket == null || this.config.getUdpPort() == ConnectionConfiguration.UDP_DISABLED)
            {
                LOGGER.info("UDP is disabled for this connection");
                successful = false;
                return successful;
            }
            SocketAddress remoteAddress = new InetSocketAddress(((InetSocketAddress) this.socketAddress).getAddress(), this.config.getUdpPort());
            this.udpSocket.connect(remoteAddress);

            this.ioUdpReadRunnable = new IoUdpReadRunnable<MessageType>(this, new RunnableEventListener()
            {
                @Override
                public void onRunnableClosed(DisconnectState disconnectState)
                {
                    LOGGER.trace("ioUdpReadRunnable closed {}", disconnectState);
                    disconnect();
                    ioUdpReadRunnable = null;
                }
            }, udpSocket);
            this.ioUdpWriteRunnable = new IoUdpWriteRunnable<MessageType>(new InetSocketAddress(this.config.getIpAddress(), this.config.getUdpPort()), new RunnableEventListener()
            {
                @Override
                public void onRunnableClosed(DisconnectState disconnectState)
                {
                    LOGGER.trace("ioUdpWriteRunnable closed {}", disconnectState);
                    disconnect();
                    ioUdpWriteRunnable = null;
                }
            }, udpSocket);

        }
        catch (SocketException ex)
        {
            successful = false;
            LOGGER.error("UDP Init socket", ex);
        }
        finally
        {
            return successful;
        }
    }

    @Override
    protected void startUdp()
    {
        boolean initUdp = this.initUdp();
        if (initUdp)
        {
            this.executorService.execute(ioUdpWriteRunnable);
            this.executorService.execute(ioUdpReadRunnable);
        }
    }

    @Override
    protected void closeUdp()
    {
        if (isUdpWriteRunning())
        {
            this.ioUdpWriteRunnable.close();
        }
        if (isUdpReadRunning())
        {
            this.ioUdpReadRunnable.close();
        }
    }
}

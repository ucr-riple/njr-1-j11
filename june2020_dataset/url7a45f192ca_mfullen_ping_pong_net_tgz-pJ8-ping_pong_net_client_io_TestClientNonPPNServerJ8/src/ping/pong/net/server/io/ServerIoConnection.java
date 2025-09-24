package ping.pong.net.server.io;

import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.Socket;
import ping.pong.net.connection.DisconnectState;
import ping.pong.net.connection.RunnableEventListener;
import ping.pong.net.connection.config.ConnectionConfiguration;
import ping.pong.net.connection.io.AbstractIoConnection;
import ping.pong.net.connection.io.DataReader;
import ping.pong.net.connection.io.DataWriter;
import ping.pong.net.connection.io.IoUdpWriteRunnable;
import ping.pong.net.connection.messaging.DisconnectMessage;

/**
 *
 * @author mfullen
 */
final class ServerIoConnection<MessageType> extends AbstractIoConnection<MessageType>
{
    /**
     * This connection's UdpWriteThread
     */
    protected IoUdpWriteRunnable<MessageType> ioUdpWriteRunnable = null;
    /**
     * The Udp Socket used for this connection
     */
    protected DatagramSocket udpSocket = null;

    public ServerIoConnection(ConnectionConfiguration config, DataReader dataReader, DataWriter dataWriter, Socket tcpSocket, DatagramSocket udpSocket)
    {
        super(config, dataReader, dataWriter, tcpSocket);
        this.udpSocket = udpSocket;
    }

    @Override
    protected void processMessage(MessageType message)
    {
        if (message instanceof DisconnectMessage)
        {
            this.connected = false;
        }
        else
        {
            this.fireOnSocketMessageReceived(message);
        }
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
        finally
        {
            return successful;
        }
    }

    @Override
    protected synchronized void fireOnSocketCreated()
    {
        super.fireOnSocketCreated();
    }

    @Override
    protected void startUdp()
    {
        boolean initUdp = this.initUdp();
        if (initUdp)
        {
            this.executorService.execute(ioUdpWriteRunnable);
        }
    }

    @Override
    protected void closeUdp()
    {
        if (isUdpWriteRunning())
        {
            this.ioUdpWriteRunnable.close();
        }
    }

    @Override
    protected boolean isAnyRunning()
    {
        return super.isAnyRunning() || this.isUdpWriteRunning();
    }

    protected boolean isUdpWriteRunning()
    {
        return this.ioUdpWriteRunnable != null && this.ioUdpWriteRunnable.isRunning();
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
}

package ping.pong.net.connection.io;

import java.net.DatagramSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ping.pong.net.connection.DisconnectState;
import ping.pong.net.connection.RunnableEventListener;

/**
 *
 * @author mfullen
 */
public abstract class AbstractIoUdpRunnable implements Runnable
{
    /**
     * Logger for IoUdpReadRunnable
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractIoUdpRunnable.class);
    /**
     * Flag for whether this thread is running
     */
    protected boolean running = false;
    /**
     * The UdpSocket to write to
     */
    protected DatagramSocket udpSocket = null;
    /**
     * Notifies the listener when this runnable is closed
     */
    protected RunnableEventListener runnableEventListener = null;
    /**
     *
     */
    protected DisconnectState disconnectState = DisconnectState.NORMAL;

    /**
     *
     * @param runnableEventListener
     * @param udpSocket
     */
    public AbstractIoUdpRunnable(RunnableEventListener runnableEventListener, DatagramSocket udpSocket)
    {
        this.udpSocket = udpSocket;
        this.runnableEventListener = runnableEventListener;
    }

    /**
     * Is this thread still running/running?
     * @return
     */
    public synchronized boolean isRunning()
    {
        return this.running;
    }

    /**
     *
     */
    public synchronized void close()
    {
        this.running = false;
        if (this.udpSocket != null)
        {
            LOGGER.trace("attempting to close udp socket");
            //this.udpSocket.close();
        }
        else
        {
            LOGGER.error("UDP SOCKET IS NULL");
        }

        if (this.runnableEventListener != null)
        {
            this.runnableEventListener.onRunnableClosed(disconnectState);
            this.runnableEventListener = null;
            LOGGER.debug("Udp Socket Closed");
        }
    }

    /**
     *
     * @param disconnectState
     */
    public synchronized void setDisconnectState(DisconnectState disconnectState)
    {
        this.disconnectState = disconnectState;
    }

    @Override
    public abstract void run();
}

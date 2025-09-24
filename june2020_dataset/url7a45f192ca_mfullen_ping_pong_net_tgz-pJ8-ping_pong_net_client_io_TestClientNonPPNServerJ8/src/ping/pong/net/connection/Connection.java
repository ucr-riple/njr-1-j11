package ping.pong.net.connection;

import java.net.SocketAddress;
import ping.pong.net.connection.messaging.Envelope;
import ping.pong.net.connection.messaging.MessageSender;

/**
 * A Connection interface represent what an Active connection between sockets
 * is.
 *
 * @param <Message>
 * @author mfullen
 */
public interface Connection<Message> extends
        MessageSender<Envelope<Message>>,
        Runnable
{
    /**
     * Closes the connection
     */
    void close();

    /**
     * If the connection is still connected to a source
     *
     * @return true if connected, false if not connected
     */
    boolean isConnected();

    /**
     * The identification number of the connection
     *
     * @return identification number of the connection
     */
    int getConnectionId();

    /**
     * Set the connection Id
     * @param id
     */
    void setConnectionId(int id);

    /**
     * Get the Socket address
     *
     * @return
     */
    SocketAddress getSocketAddress();

    /**
     * Add a ConnectionEvent listener
     *
     * @param listener the listener to add
     */
    void addConnectionEventListener(ConnectionEvent listener);

    /**
     * Remove a Connection Event listener
     *
     * @param listener the listener to add
     */
    void removeConnectionEventListener(ConnectionEvent listener);

    /**
     * Flag for whether the connection is using Custom Serialization or the Default
     * @return true for custom reader/writer false for default
     */
    boolean isUsingCustomSerialization();

    /**
     * True if UDP is enabled. False if not
     * @return
     */
    boolean isUdpEnabled();
}

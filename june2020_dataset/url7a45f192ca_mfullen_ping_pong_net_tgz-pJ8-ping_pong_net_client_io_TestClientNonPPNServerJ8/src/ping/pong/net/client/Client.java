package ping.pong.net.client;

import ping.pong.net.connection.messaging.Envelope;
import ping.pong.net.connection.messaging.MessageListener;
import ping.pong.net.connection.messaging.MessageSender;

/**
 * Client interface to control a client connection
 * @param <Message>
 * @author mfullen
 */
public interface Client<Message> extends
        MessageSender<Envelope<Message>>
{
    /**
     * Starts the server based off the Current Connection Configuration. The client
     * connection will not start if the connection is already connected
     */
    void start();

    /**
     * Closes the client connection
     */
    void close();

    /**
     * @return true if the connection is not null and is connected
     */
    boolean isConnected();

    /**
     * Get the Id of the Client.
     * @return Integer > 0 if connected, -1 if not connected
     */
    int getId();

    /**
     * Add a listener for incoming messages
     * @param listener the listener to add
     */
    void addMessageListener(MessageListener<? super Client, Message> listener);

    /**
     * Remove a listener for incoming messages
     * @param listener the listener to remove
     */
    void removeMessageListener(MessageListener<? super Client, Message> listener);

    /**
     * Add a listener for connections, client connected and disconnected
     * @param listener the listener to add
     */
    void addConnectionListener(ClientConnectionListener listener);

    /**
     * Remove a conneciton listener
     * @param listener the listener to remove
     */
    void removeConnectionListener(ClientConnectionListener listener);
}

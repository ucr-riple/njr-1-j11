package ping.pong.net.server;

import java.util.Collection;
import ping.pong.net.connection.Connection;
import ping.pong.net.connection.messaging.Envelope;
import ping.pong.net.connection.messaging.MessageListener;

/**
 *
 * @param <Message>
 * @author mfullen
 */
public interface Server<Message>
{
    /**
     * Broadcast Message to all connected clients
     * @param message the message to broadcast
     */
    void broadcast(Envelope<Message> message);

    /**
     * Start the server. Upon being started, clients should be able to connect
     */
    void start();

    /**
     * Shutdown the server and close any active connections
     */
    void shutdown();

    /**
     * Get the connection based on an Identifier.
     * @param id The identifier
     * @return Connection
     */
    Connection getConnection(int id);

    /**
     * Get all the current connections. The Collection is unmodifiable
     * @return Collection of Connections
     */
    Collection<Connection> getConnections();

    /**
     * @return true if the server has connections, false otherwise
     */
    boolean hasConnections();

    /**
     * @return true if the server is listening for connections, false otherwise
     */
    boolean isListening();

    /**
     * Gets the next available ConnectionId
     * @return
     */
    int getNextAvailableId();

    /**
     * Add a MessageListener
     * @param listener the listener to add
     */
    void addMessageListener(MessageListener<? super Connection, Message> listener);

    /**
     * Remove a MessageListener
     * @param listener the listener to remove
     */
    void removeMessageListener(MessageListener<? super Connection, Message> listener);

    /**
     * Add a ServerConnectionListener
     * @param connectionListener the listener to add
     */
    void addConnectionListener(ServerConnectionListener connectionListener);

    /**
     * Remove a ServerConnectionListener
     * @param connectionListener the listener to remove
     */
    void removeConnectionListener(ServerConnectionListener connectionListener);
}

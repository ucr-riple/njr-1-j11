package ping.pong.net.connection;

/**
 * Connection Events happen inside of the connection to alert listeners
 * @param <MessageType>
 */
public interface ConnectionEvent<MessageType>
{
    /**
     * The Socket has been closed
     */
    void onSocketClosed();

    /**
     * The socket has been created
     */
    void onSocketCreated();

    /**
     * The socket received a message
     * @param message the message which was received
     */
    void onSocketReceivedMessage(MessageType message);
}

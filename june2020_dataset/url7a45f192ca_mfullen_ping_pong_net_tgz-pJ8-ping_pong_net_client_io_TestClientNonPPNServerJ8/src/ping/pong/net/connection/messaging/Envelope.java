package ping.pong.net.connection.messaging;

/**
 *
 * @param <Message>
 * @author mfullen
 */
public interface Envelope<Message extends Object>
{
    /**
     * Flag to determine to use TCP or UDP
     * @return
     */
    boolean isReliable();

    /**
     * The message being sent across the wire
     * @return
     */
    Message getMessage();
}

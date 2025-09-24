package ping.pong.net.connection.messaging;

/**
 *
 * @param <S>
 * @param <Message>
 * @author mfullen
 */
public interface MessageListener<S, Message>
{
    /**
     *
     * @param source
     * @param message
     */
    void messageReceived(S source, Message message);
}

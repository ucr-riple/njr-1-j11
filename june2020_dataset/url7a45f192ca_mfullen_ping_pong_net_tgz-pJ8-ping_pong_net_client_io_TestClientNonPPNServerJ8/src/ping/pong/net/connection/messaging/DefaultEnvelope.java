package ping.pong.net.connection.messaging;

/**
 *
 * @param <Message>
 * @author mfullen
 */
public class DefaultEnvelope<Message> implements Envelope<Message>
{
    private boolean reliable = true;
    private Message message;

    /**
     *
     * @param reliable
     */
    public void setReliable(boolean reliable)
    {
        this.reliable = reliable;
    }

    /**
     *
     * @param message
     */
    public void setMessage(Message message)
    {
        this.message = message;
    }

    @Override
    public boolean isReliable()
    {
        return reliable;
    }

    @Override
    public Message getMessage()
    {
        return message;
    }

    @Override
    public String toString()
    {
        return "DefaultEnvelope{" + "reliable=" + reliable + ", message=" + message + '}';
    }
}

package ping.pong.net.connection.messaging;

/**
 *
 * @author mfullen
 */
public final class EnvelopeFactory
{
    private EnvelopeFactory()
    {
    }

    /**
     *
     * @param <MessageType>
     * @param message
     * @return
     */
    public static <MessageType> Envelope createTcpEnvelope(MessageType message)
    {
        DefaultEnvelope<MessageType> envelope = new DefaultEnvelope<MessageType>();
        envelope.setMessage(message);
        envelope.setReliable(true);
        return envelope;
    }

    /**
     *
     * @param <MessageType>
     * @param message
     * @return
     */
    public static <MessageType> Envelope createUdpEnvelope(MessageType message)
    {
        DefaultEnvelope<MessageType> envelope = new DefaultEnvelope<MessageType>();
        envelope.setMessage(message);
        envelope.setReliable(false);
        return envelope;
    }
}

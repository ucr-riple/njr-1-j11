package ping.pong.net.connection.messaging;

/**
 * This interfaces provides methods to process messages to the queues
 * @param <Message>
 * @author mfullen
 */
public interface MessageProcessor<Message>
{
    /**
     * Enqueues a received message
     * @param message the message to enqueue
     */
    void enqueueReceivedMessage(Message message);

    /**
     * Enqueues a message to write
     * @param message the message to enqueue
     */
    void enqueueMessageToWrite(Envelope<Message> message);
}

package ping.pong.net.connection.io;

import java.io.IOException;
import java.io.StreamCorruptedException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ping.pong.net.connection.ConnectionExceptionHandler;
import ping.pong.net.connection.ConnectionUtils;
import ping.pong.net.connection.DisconnectState;
import ping.pong.net.connection.RunnableEventListener;
import ping.pong.net.connection.messaging.MessageProcessor;

/**
 *
 * @param <MessageType>
 * @author mfullen
 */
public class IoUdpReadRunnable<MessageType> extends AbstractIoUdpRunnable
{
    /**
     * Logger for IoUdpReadRunnable
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(IoUdpReadRunnable.class);
    private static final int RECEIVE_BUFFER_SIZE = 1024;
    /**
     * MessageProcessor to process the messages read
     */
    protected MessageProcessor<MessageType> messageProcessor = null;

    /**
     *
     * @param messageProcessor
     * @param runnableEventListener
     * @param udpSocket
     */
    public IoUdpReadRunnable(MessageProcessor<MessageType> messageProcessor, RunnableEventListener runnableEventListener, DatagramSocket udpSocket)
    {
        super(runnableEventListener, udpSocket);
        this.messageProcessor = messageProcessor;
    }

    @Override
    public void run()
    {
        this.running = true;
        boolean hasErrors = false;
        byte[] data = new byte[RECEIVE_BUFFER_SIZE];
        this.setDisconnectState(DisconnectState.NORMAL);
        while (this.running && !hasErrors)
        {
            MessageType messageType = null;
            byte[] trimmedBuffer = null;
            try
            {
                DatagramPacket packet = new DatagramPacket(data, data.length);
                udpSocket.receive(packet);
                byte[] receivedData = packet.getData();

                LOGGER.trace("Received {} from {} on port {}", new Object[]
                        {
                            receivedData, packet.getAddress(),
                            packet.getPort()
                        });

                trimmedBuffer = Arrays.copyOf(receivedData, packet.getLength());

                //If the byte order is LittleEndian convert to BigEndian
                ByteBuffer byteBuffer = ByteBuffer.allocate(trimmedBuffer.length);
                LOGGER.trace("Byte order is {}", byteBuffer.order());
                if (byteBuffer.order().equals(ByteOrder.LITTLE_ENDIAN))
                {
                    trimmedBuffer = byteBuffer.order(ByteOrder.BIG_ENDIAN).array();
                    LOGGER.trace("Byte order converted to {}", ByteOrder.BIG_ENDIAN);
                }

                messageType = ConnectionUtils.<MessageType>getObject(trimmedBuffer);
                LOGGER.trace("Message deserialized into: " + messageType);

            }
            catch (StreamCorruptedException streamCorruptedException)
            {
                //The received message isn't an Object so process it as normal byte[]
                messageType = (MessageType) trimmedBuffer;
            }
            catch (IOException ex)
            {
                hasErrors = true;
                this.setDisconnectState(ConnectionExceptionHandler.handleException(ex, LOGGER));
                LOGGER.error("Error receiving UDP packet", ex);
            }
            catch (ClassNotFoundException ex)
            {
                hasErrors = true;
                this.setDisconnectState(DisconnectState.ERROR);
                LOGGER.error("Error converting to object");
            }
            finally
            {
                if (messageType != null && !hasErrors)
                {
                    this.messageProcessor.enqueueReceivedMessage(messageType);
                }
            }
        }
        this.close();
    }
}

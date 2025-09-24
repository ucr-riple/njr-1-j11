package ping.pong.net.connection.io;

import java.io.IOException;
import java.net.*;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ping.pong.net.connection.DisconnectState;
import ping.pong.net.connection.RunnableEventListener;
import ping.pong.net.connection.messaging.Envelope;
import ping.pong.net.connection.messaging.MessageProcessor;

/**
 *
 * @author mfullen
 */
public class IoUdpReadRunnableTest
{
    private static final Logger logger = LoggerFactory.getLogger(IoUdpReadRunnableTest.class);

    public IoUdpReadRunnableTest()
    {
    }

    @BeforeClass
    public static void setUpClass() throws Exception
    {
    }

    @AfterClass
    public static void tearDownClass() throws Exception
    {
    }

    @Test
    public void testReceive() throws SocketException, UnknownHostException,
                                     IOException, InterruptedException
    {
        final String message = "hello world";
        DatagramSocket udpSocket = new DatagramSocket(7001);
        MessageProcessorImpl messageProcessorImpl = new MessageProcessorImpl();
        IoUdpReadRunnable<byte[]> ioUdpReadRunnable = new IoUdpReadRunnable<byte[]>(messageProcessorImpl, new RunnableEventListener()
        {
            @Override
            public void onRunnableClosed(DisconnectState disconnectState)
            {
                logger.debug("Close called {}", disconnectState);
            }
        }, udpSocket);
        Thread thread = new Thread(ioUdpReadRunnable);
        thread.start();

        DatagramSocket clientUdpSocket = new DatagramSocket();
        DatagramPacket sendPacket =
                new DatagramPacket(message.getBytes(), message.getBytes().length, InetAddress.getLocalHost(), 7001);
        clientUdpSocket.send(sendPacket);
        thread.join(200);
        assertEquals(messageProcessorImpl.myMessage, message);
        ioUdpReadRunnable.close();
    }

    /**
     * Test of close method, of class IoUdpReadRunnable.
     */
    @Test
    public void testCloseWhileNotRunning() throws SocketException,
                                                  InterruptedException
    {
        DatagramSocket udpSocket = new DatagramSocket(7002);
        MessageProcessorImpl messageProcessorImpl = new MessageProcessorImpl();
        IoUdpReadRunnable<byte[]> ioUdpReadRunnable = new IoUdpReadRunnable<byte[]>(messageProcessorImpl, new RunnableEventListener()
        {
            @Override
            public void onRunnableClosed(DisconnectState disconnectState)
            {
                logger.debug("Close called {}", disconnectState);
            }
        }, udpSocket);
        assertFalse(ioUdpReadRunnable.isRunning());
        ioUdpReadRunnable.close();
        assertFalse(ioUdpReadRunnable.isRunning());
    }

    @Test
    public void testCloseWhileRunning() throws SocketException,
                                               InterruptedException
    {
        DatagramSocket udpSocket = new DatagramSocket(7003);
        MessageProcessorImpl messageProcessorImpl = new MessageProcessorImpl();
        final IoUdpReadRunnable<byte[]> ioUdpReadRunnable = new IoUdpReadRunnable<byte[]>(messageProcessorImpl, new RunnableEventListener()
        {
            @Override
            public void onRunnableClosed(DisconnectState disconnectState)
            {
                logger.debug("Close called {}", disconnectState);
            }
        }, udpSocket);
        assertFalse(ioUdpReadRunnable.isRunning());
        Thread thread = new Thread(ioUdpReadRunnable);
        thread.start();
        Thread stopThread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                ioUdpReadRunnable.close();
            }
        });
        thread.join(100);
        assertTrue(ioUdpReadRunnable.isRunning());

        stopThread.start();
        stopThread.join(50);
        assertFalse(ioUdpReadRunnable.isRunning());
    }

    @Test
    public void closeWithNullUdpSocket()
    {
        MessageProcessorImpl messageProcessorImpl = new MessageProcessorImpl();
        final IoUdpReadRunnable<byte[]> ioUdpReadRunnable = new IoUdpReadRunnable<byte[]>(messageProcessorImpl, null, null);
        ioUdpReadRunnable.close();
        assertNull(ioUdpReadRunnable.udpSocket);
        assertFalse(ioUdpReadRunnable.isRunning());
    }

    class MessageProcessorImpl implements MessageProcessor<byte[]>
    {
        public MessageProcessorImpl()
        {
        }
        public String myMessage = null;

        @Override
        public void enqueueReceivedMessage(byte[] byteMessage)
        {
            String string = new String(byteMessage);
            myMessage = string;
            logger.debug("Message Received (mymessage) {}", myMessage);
        }

        @Override
        public void enqueueMessageToWrite(Envelope message)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}

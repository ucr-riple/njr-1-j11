package ping.pong.net.client.io;

import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import ping.pong.net.client.Client;
import ping.pong.net.client.ClientConnectionListener;
import ping.pong.net.connection.DisconnectInfo;
import ping.pong.net.connection.io.ReadFullyDataReader;
import ping.pong.net.connection.io.WriteByteArrayDataWriter;
import ping.pong.net.connection.messaging.EnvelopeFactory;
import ping.pong.net.connection.messaging.MessageListener;
import ping.pong.net.server.io.IoServer;

/**
 *
 * @author mfullen
 */
public class IoClientTest
{
    public IoClientTest()
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

    /**
     * Test of start method, of class IoClient.
     */
    @Test
    public void testStartNoServer()
    {
        IoClient instance = new IoClient();
        assertFalse(instance.isConnected());
        instance.start();
        assertFalse(instance.isConnected());
    }

    @Test
    public void testStartWithServer() throws InterruptedException
    {
        IoServer server = new IoServer();
        server.start();
        final IoClient instance = new IoClient();
        instance.addConnectionListener(new ClientConnectionListener()
        {
            @Override
            public void clientConnected(Client client)
            {
                synchronized (IoClientTest.this)
                {
                    IoClientTest.this.notifyAll();
                }
            }

            @Override
            public void clientDisconnected(Client client, DisconnectInfo info)
            {
            }
        });
        assertFalse(instance.isConnected());
        instance.start();

        synchronized (this)
        {
            this.wait();
        }
        instance.start();
        assertTrue(instance.isConnected());
        instance.close();
        server.shutdown();
    }

    @Test
    public void testStartWithServerNONPPN() throws InterruptedException
    {
        IoServer server = new IoServer();
        server.setCustomDataReader(new ReadFullyDataReader());
        server.setCustomDataWriter(new WriteByteArrayDataWriter());
        server.start();
        final IoClient instance = new IoClient();
        instance.setCustomDataReader(new ReadFullyDataReader());
        instance.setCustomDataWriter(new WriteByteArrayDataWriter());
        instance.addConnectionListener(new ClientConnectionListener()
        {
            @Override
            public void clientConnected(Client client)
            {
                synchronized (IoClientTest.this)
                {
                    IoClientTest.this.notifyAll();
                }
            }

            @Override
            public void clientDisconnected(Client client, DisconnectInfo info)
            {
            }
        });
        assertFalse(instance.isConnected());
        instance.start();

        synchronized (this)
        {
            this.wait();
        }
        instance.start();
        assertTrue(instance.isConnected());
        instance.close();
        //Test calling back to back close
        instance.close();
        server.shutdown();
    }

    /**
     * Test of close method, of class IoClient.
     */
    @Test
    public void testClose() throws InterruptedException
    {
        final IoClient instance = new IoClient();
        IoServer server = new IoServer();
        server.start();
        assertFalse(instance.isConnected());
        instance.close();
        instance.addConnectionListener(new ClientConnectionListener()
        {
            @Override
            public void clientConnected(Client client)
            {
                synchronized (IoClientTest.this)
                {
                    IoClientTest.this.notifyAll();
                }
            }

            @Override
            public void clientDisconnected(Client client, DisconnectInfo info)
            {
            }
        });
        assertFalse(instance.isConnected());
        instance.start();

        synchronized (this)
        {
            this.wait();
        }
        instance.start();
        assertTrue(instance.isConnected());
        instance.close();
        server.shutdown();
    }

    /**
     * Test of isConnected method, of class IoClient.
     */
    @Test
    public void testIsConnected()
    {
        final IoClient instance = new IoClient();
        assertFalse(instance.isConnected());
    }

    /**
     * Test of getId method, of class IoClient.
     */
    @Test
    public void testGetId() throws InterruptedException
    {
        final IoClient instance = new IoClient();
        assertEquals(-1, instance.getId());
        IoServer server = new IoServer();
        server.start();
        assertFalse(instance.isConnected());
        instance.addConnectionListener(new ClientConnectionListener()
        {
            @Override
            public void clientConnected(Client client)
            {
                synchronized (IoClientTest.this)
                {
                    IoClientTest.this.notifyAll();
                }
            }

            @Override
            public void clientDisconnected(Client client, DisconnectInfo info)
            {
            }
        });
        assertFalse(instance.isConnected());
        instance.start();

        synchronized (this)
        {
            this.wait();
        }
        instance.start();
        assertTrue(instance.isConnected());
        assertEquals(1, instance.getId());
        instance.close();
        server.shutdown();
    }

    /**
     * Test of addMessageListener method, of class IoClient.
     */
    @Test
    public void testAddMessageListener()
    {
        final IoClient instance = new IoClient();
        assertEquals(0, instance.messageListeners.size());
        instance.addMessageListener(null);
        assertEquals(0, instance.messageListeners.size());
        MessageListenerImpl messageListenerImpl = new MessageListenerImpl();
        instance.addMessageListener(messageListenerImpl);
        assertEquals(1, instance.messageListeners.size());
    }

    /**
     * Test of removeMessageListener method, of class IoClient.
     */
    @Test
    public void testRemoveMessageListener()
    {
        final IoClient instance = new IoClient();
        assertEquals(0, instance.messageListeners.size());
        instance.addMessageListener(null);
        assertEquals(0, instance.messageListeners.size());
        MessageListenerImpl messageListenerImpl = new MessageListenerImpl();
        instance.addMessageListener(messageListenerImpl);
        assertEquals(1, instance.messageListeners.size());
        instance.removeMessageListener(messageListenerImpl);
        instance.removeMessageListener(null);
        assertEquals(0, instance.messageListeners.size());
    }

    /**
     * Test of addConnectionListener method, of class IoClient.
     */
    @Test
    public void testAddConnectionListener()
    {
        final IoClient instance = new IoClient();
        assertEquals(0, instance.connectionListeners.size());
        instance.addConnectionListener(null);
        assertEquals(0, instance.connectionListeners.size());
        ClientConnectionListenerImpl clientConnectionListenerImpl = new ClientConnectionListenerImpl();
        instance.addConnectionListener(clientConnectionListenerImpl);
        assertEquals(1, instance.connectionListeners.size());
    }

    /**
     * Test of removeConnectionListener method, of class IoClient.
     */
    @Test
    public void testRemoveConnectionListener()
    {
        final IoClient instance = new IoClient();
        assertEquals(0, instance.connectionListeners.size());
        instance.addConnectionListener(null);
        assertEquals(0, instance.connectionListeners.size());
        ClientConnectionListenerImpl clientConnectionListenerImpl = new ClientConnectionListenerImpl();
        instance.addConnectionListener(clientConnectionListenerImpl);
        assertEquals(1, instance.connectionListeners.size());
        instance.removeConnectionListener(clientConnectionListenerImpl);
        instance.removeConnectionListener(null);
        assertEquals(0, instance.connectionListeners.size());
    }

    /**
     * Test of sendMessage method, of class IoClient.
     */
    @Test
    public void testSendMessage() throws InterruptedException
    {
        final IoClient instance = new IoClient();
        instance.addMessageListener(new MessageListener()
        {
            @Override
            public void messageReceived(Object source, Object message)
            {
                synchronized (IoClientTest.this)
                {
                    IoClientTest.this.notifyAll();
                }
            }
        });
        assertNull(instance.connection);
        instance.sendMessage(EnvelopeFactory.createTcpEnvelope("Hello World"));

        IoServer server = new IoServer();
        server.start();
        assertFalse(instance.isConnected());
        instance.addConnectionListener(new ClientConnectionListener()
        {
            @Override
            public void clientConnected(Client client)
            {
                synchronized (IoClientTest.this)
                {
                    IoClientTest.this.notifyAll();
                }
            }

            @Override
            public void clientDisconnected(Client client, DisconnectInfo info)
            {
            }
        });
        assertFalse(instance.isConnected());
        instance.start();
        assertNotNull(instance.connection);
        instance.sendMessage(EnvelopeFactory.createTcpEnvelope("Hello World"));
        synchronized (this)
        {
            this.wait();
        }
        instance.sendMessage(EnvelopeFactory.createTcpEnvelope("Hello World"));
        assertTrue(instance.isConnected());
        assertEquals(1, instance.getId());
        server.broadcast(EnvelopeFactory.createTcpEnvelope("Hello Client"));
        synchronized (this)
        {
            this.wait();
        }
        instance.close();
        server.shutdown();
    }

    private static class MessageListenerImpl implements MessageListener
    {
        public MessageListenerImpl()
        {
        }

        @Override
        public void messageReceived(Object source, Object message)
        {
            fail("Not suppoed to be called");
        }
    }

    private static class ClientConnectionListenerImpl implements
            ClientConnectionListener
    {
        public ClientConnectionListenerImpl()
        {
        }

        @Override
        public void clientConnected(Client client)
        {
            fail();
        }

        @Override
        public void clientDisconnected(Client client, DisconnectInfo info)
        {
            fail();
        }
    }
}

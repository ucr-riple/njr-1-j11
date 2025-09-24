package ping.pong.net.server.io;

import java.net.SocketAddress;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ping.pong.net.connection.Connection;
import ping.pong.net.connection.ConnectionEvent;
import ping.pong.net.connection.config.ConnectionConfigFactory;
import ping.pong.net.connection.messaging.Envelope;
import ping.pong.net.connection.messaging.MessageListener;
import ping.pong.net.server.Server;
import ping.pong.net.server.ServerConnectionListener;

/**
 *
 * @author mfullen
 */
public class IoServerTest
{
    private static final Logger logger = LoggerFactory.getLogger(IoServer.class);
    Envelope<String> tcpMessage = new Envelope<String>()
    {
        @Override
        public boolean isReliable()
        {
            return true;
        }

        @Override
        public String getMessage()
        {
            return "Test";
        }
    };
    Connection connection1 = new Connection<String>()
    {
        @Override
        public void close()
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean isConnected()
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public int getConnectionId()
        {
            return 1;
        }

        @Override
        public void setConnectionId(int id)
        {
            assertTrue(id == 1);
        }

        @Override
        public void sendMessage(Envelope<String> message)
        {
            assertTrue(message.isReliable());
            assertEquals(message.getMessage(), "Test");
        }

        @Override
        public void run()
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void addConnectionEventListener(ConnectionEvent listener)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void removeConnectionEventListener(ConnectionEvent listener)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean isUsingCustomSerialization()
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean isUdpEnabled()
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public SocketAddress getSocketAddress()
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    };

    public IoServerTest()
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
     * Test of broadcast method, of class IoServer.
     */
    @Test
    public void testBroadcast()
    {
        IoServer instance = new IoServer();
        assertNotNull(instance);
        assertNotNull(connection1);
        instance.addConnection(connection1);
        instance.broadcast(tcpMessage);
    }

    /**
     * Test of start method, of class IoServer.
     */
    @Test
    public void testStart()
    {
        IoServer instance = new IoServer();
        instance.start();
        assertTrue(instance.isListening());
    }

    @Test
    public void testConnectionManager()
    {
        IoServer instance = new IoServer();
        assertFalse(instance.isListening());
        assertNull(instance.serverConnectionManager);
        instance.serverConnectionManager = new ServerConnectionManager(ConnectionConfigFactory.createConnectionConfiguration(), instance);
        assertNotNull(instance.serverConnectionManager);
        //assertFalse(instance.isListening());
        instance.start();
    }

    /**
     * Test of shutdown method, of class IoServer.
     */
    @Test
    public void testShutdown()
    {
        IoServer instance = new IoServer();
        instance.shutdown();
        instance.start();
        assertTrue(instance.isListening());
        instance.shutdown();
        assertFalse(instance.isListening());
    }

    /**
     * Test of getConnection method, of class IoServer.
     */
    @Test
    public void testGetConnection()
    {
        IoServer instance = new IoServer();
        assertNotNull(instance);
        assertNotNull(connection1);
        int id = connection1.getConnectionId();
        assertNull(instance.getConnection(id));
        instance.addConnection(connection1);
        assertEquals(connection1, instance.getConnection(id));
    }

    /**
     * Test of getConnections method, of class IoServer.
     */
    @Test
    public void testGetConnections()
    {
        IoServer instance = new IoServer();
        assertTrue(instance.getConnections().isEmpty());
        instance.addConnection(connection1);
        assertEquals(instance.getConnections().size(), 1);
    }

    /**
     * Test of hasConnections method, of class IoServer.
     */
    @Test
    public void testHasConnections()
    {
        IoServer instance = new IoServer();
        assertFalse(instance.hasConnections());
        instance.addConnection(connection1);
        assertTrue(instance.hasConnections());
    }

    /**
     * Test of isListening method, of class IoServer.
     */
    @Test
    public void testIsListening()
    {
        IoServer instance = new IoServer();
        assertFalse(instance.isListening());
        instance.start();
        assertTrue(instance.isListening());
        instance.shutdown();
        assertFalse(instance.isListening());
    }

    @Test
    public void testIsListening2()
    {
        IoServer instance = new IoServer();
        assertFalse(instance.isListening());
        instance.serverConnectionManager = new ServerConnectionManager(ConnectionConfigFactory.createConnectionConfiguration(), instance);
        instance.serverConnectionManager.listening = false;
        assertFalse(instance.isListening());
    }

    /**
     * Test of addMessageListener method, of class IoServer.
     */
    @Test
    public void testAddMessageListener()
    {
        MessageListener<? super Connection, Envelope> listener = null;
        IoServer instance = new IoServer();
        instance.addMessageListener(listener);
        assertEquals(0, instance.messageListeners.size());

        listener = new MessageListener<Connection, Envelope>()
        {
            @Override
            public void messageReceived(Connection source, Envelope message)
            {
                assertNotNull(source);
                assertNotNull(message);
            }
        };

        instance.addMessageListener(listener);
        assertEquals(1, instance.messageListeners.size());
    }

    /**
     * Test of removeMessageListener method, of class IoServer.
     */
    @Test
    public void testRemoveMessageListener()
    {
        MessageListener<? super Connection, Envelope> listener = null;
        IoServer instance = new IoServer();
        instance.removeMessageListener(listener);
        assertEquals(0, instance.messageListeners.size());

        listener = new MessageListener<Connection, Envelope>()
        {
            @Override
            public void messageReceived(Connection source, Envelope message)
            {
                assertNotNull(source);
                assertNotNull(message);
            }
        };

        instance.addMessageListener(listener);
        assertEquals(1, instance.messageListeners.size());
        instance.removeMessageListener(listener);
        assertEquals(0, instance.messageListeners.size());
    }

    /**
     * Test of addConnectionListener method, of class IoServer.
     */
    @Test
    public void testAddConnectionListener()
    {
        ServerConnectionListener listener = null;
        IoServer instance = new IoServer();
        instance.addConnectionListener(listener);
        assertEquals(0, instance.connectionListeners.size());

        listener = new ServerConnectionListener()
        {
            @Override
            public void connectionAdded(Server server, Connection conn)
            {
                assertNotNull(server);
                assertNotNull(conn);
            }

            @Override
            public void connectionRemoved(Server server, Connection conn)
            {
                assertNotNull(server);
                assertNotNull(conn);
            }
        };

        instance.addConnectionListener(listener);
        assertEquals(1, instance.connectionListeners.size());
    }

    /**
     * Test of removeConnectionListener method, of class IoServer.
     */
    @Test
    public void testRemoveConnectionListener()
    {
        ServerConnectionListener listener = null;
        IoServer instance = new IoServer();
        instance.removeConnectionListener(listener);
        assertEquals(0, instance.connectionListeners.size());

        listener = new ServerConnectionListener()
        {
            @Override
            public void connectionAdded(Server server, Connection conn)
            {
                assertNotNull(server);
                assertNotNull(conn);
            }

            @Override
            public void connectionRemoved(Server server, Connection conn)
            {
                assertNotNull(server);
                assertNotNull(conn);
            }
        };

        instance.addConnectionListener(listener);
        assertEquals(1, instance.connectionListeners.size());
        instance.removeConnectionListener(listener);
        assertEquals(0, instance.connectionListeners.size());
    }

    /**
     * Test of getNextAvailableId method, of class IoServer.
     */
    @Test
    public void testGetNextAvailableId()
    {
        IoServer instance = new IoServer();

        int result = instance.getNextAvailableId();
        assertEquals(1, result);
        instance.addConnection(connection1);
        result = instance.getNextAvailableId();
        result = instance.getNextAvailableId();
        assertEquals(2, result);
    }

    @Test
    public void testRemoveConnection()
    {
        IoServer instance = new IoServer();
        ServerConnectionListener listener = new ServerConnectionListener()
        {
            @Override
            public void connectionAdded(Server server, Connection conn)
            {
                assertNotNull(server);
                assertNotNull(conn);
            }

            @Override
            public void connectionRemoved(Server server, Connection conn)
            {
                assertNotNull(server);
                assertNotNull(conn);
            }
        };

        instance.addConnectionListener(listener);
        instance.addConnection(connection1);
        assertEquals(1, instance.getConnections().size());
        assertNotNull(instance.getConnection(connection1.getConnectionId()));

        instance.removeConnection(connection1);
        assertEquals(0, instance.getConnections().size());
        assertNull(instance.getConnection(connection1.getConnectionId()));

        instance.removeConnectionListener(listener);
        instance.addConnection(connection1);
        assertEquals(1, instance.getConnections().size());
        assertNotNull(instance.getConnection(connection1.getConnectionId()));

        instance.removeConnection(connection1.getConnectionId());
        assertEquals(0, instance.getConnections().size());
        assertNull(instance.getConnection(connection1.getConnectionId()));

        instance.removeConnection(null);
        assertEquals(0, instance.getConnections().size());
        assertNull(instance.getConnection(connection1.getConnectionId()));
    }
}

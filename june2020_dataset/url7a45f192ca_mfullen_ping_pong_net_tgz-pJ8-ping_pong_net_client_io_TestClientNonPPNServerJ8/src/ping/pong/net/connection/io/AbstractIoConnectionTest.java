package ping.pong.net.connection.io;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.net.ServerSocketFactory;
import javax.net.SocketFactory;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import ping.pong.net.connection.ConnectionEvent;
import ping.pong.net.connection.config.ConnectionConfiguration;
import ping.pong.net.connection.messaging.Envelope;
import ping.pong.net.connection.messaging.EnvelopeFactory;

/**
 *
 * @author mfullen
 */
public class AbstractIoConnectionTest
{
    public AbstractIoConnectionTest()
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
     * Test of initTcp method, of class AbstractIoConnection.
     */
    @Test
    public void testInitTcpWithAllNullParameters()
    {
        AbstractIoConnection instance = new AbstractIoConnectionImpl();
        assertFalse(instance.initTcp());
    }

    @Test
    public void testInitTcpWithAllNullDataReaderWriter() throws IOException
    {
        Socket tcpSocket = SocketFactory.getDefault().createSocket();
        AbstractIoConnection instance = new AbstractIoConnectionImpl(null, null, null, tcpSocket);
        assertTrue(instance.initTcp());
    }

    /**
     * Test of isUsingCustomSerialization method, of class AbstractIoConnection.
     */
    @Test
    public void testIsUsingCustomSerialization()
    {
        AbstractIoConnection instance = new AbstractIoConnectionImpl();
        assertFalse(instance.isUsingCustomSerialization());
        instance = new AbstractIoConnectionImpl(null, new ReadFullyDataReader(), null, null);
        assertTrue(instance.isUsingCustomSerialization());
        instance = new AbstractIoConnectionImpl(null, new ReadFullyDataReader(), new WriteByteArrayDataWriter(), null);
        assertTrue(instance.isUsingCustomSerialization());
        instance = new AbstractIoConnectionImpl(null, null, new WriteByteArrayDataWriter(), null);
        assertTrue(instance.isUsingCustomSerialization());
    }

    /**
     * Test of fireOnSocketCreated method, of class AbstractIoConnection.
     */
    @Test
    public void testFireOnSocketCreated()
    {
        AbstractIoConnection instance = new AbstractIoConnectionImpl();
        assertEquals(0, instance.connectionEventListeners.size());
        instance.connectionEventListeners.add(new ConnectionEvent<Object>()
        {
            @Override
            public void onSocketClosed()
            {
                fail("Method not suppose to be called here");
            }

            @Override
            public void onSocketCreated()
            {
                assert true;
            }

            @Override
            public void onSocketReceivedMessage(Object message)
            {
                fail("Method not suppose to be called here");
            }
        });
        assertEquals(1, instance.connectionEventListeners.size());
        instance.fireOnSocketCreated();
    }

    @Test
    public void testFireOnSocketClosed()
    {
        AbstractIoConnection instance = new AbstractIoConnectionImpl();
        assertEquals(0, instance.connectionEventListeners.size());
        instance.connectionEventListeners.add(new ConnectionEvent<Object>()
        {
            @Override
            public void onSocketClosed()
            {
                assert true;

            }

            @Override
            public void onSocketCreated()
            {
                fail("Method not suppose to be called here");
            }

            @Override
            public void onSocketReceivedMessage(Object message)
            {
                fail("Method not suppose to be called here");
            }
        });
        assertEquals(1, instance.connectionEventListeners.size());
        instance.fireOnSocketClosed();
    }

    /**
     * Test of fireOnSocketMessageReceived method, of class AbstractIoConnection.
     */
    @Test
    public void testFireOnSocketMessageReceived()
    {
        final String strMessage = "hello world";
        AbstractIoConnection instance = new AbstractIoConnectionImpl();
        assertEquals(0, instance.connectionEventListeners.size());
        instance.connectionEventListeners.add(new ConnectionEvent<String>()
        {
            @Override
            public void onSocketClosed()
            {
                fail("Method not suppose to be called here");
            }

            @Override
            public void onSocketCreated()
            {
                fail("Method not suppose to be called here");
            }

            @Override
            public void onSocketReceivedMessage(String message)
            {
                assert true;
                assertEquals(message, strMessage);
            }
        });
        assertEquals(1, instance.connectionEventListeners.size());
        instance.fireOnSocketMessageReceived(strMessage);
    }

    /**
     * Test of run method, of class AbstractIoConnection.
     */
    @Test
    public void testRunCantStart()
    {
        AbstractIoConnection instance = new AbstractIoConnectionImpl();
        assertFalse(instance.canStart);
        assertFalse(instance.isConnected());
        assertFalse(instance.isAnyRunning());
        instance.run();
        assertFalse(instance.canStart);
        assertFalse(instance.isConnected());
        assertFalse(instance.isAnyRunning());
    }

    @Test
    public void testRunCanStart() throws IOException, InterruptedException
    {
        Runnable server = new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    ServerSocket serverSocket = ServerSocketFactory.getDefault().createServerSocket(8077);
                    serverSocket.accept();
                }
                catch (IOException ex)
                {
                }
            }
        };

        Thread serverThread = new Thread(server);
        serverThread.start();
        Socket tcpSocket = SocketFactory.getDefault().createSocket("localhost", 8077);


        AbstractIoConnection instance = new AbstractIoConnectionImpl(null, new ReadObjectDataReader(), new WriteObjectDataWriter(), tcpSocket);

        assertTrue(instance.canStart);
        assertFalse(instance.isConnected());
        assertFalse(instance.isAnyRunning());
        Thread connectionThread = new Thread(instance);
        connectionThread.start();

        connectionThread.join(20);
        assertTrue(instance.canStart);
        assertTrue(instance.isConnected());
        assertTrue(instance.isAnyRunning());
        serverThread.join(15);

        instance.close();
        assertFalse(instance.isConnected());
        assertFalse(instance.isAnyRunning());
    }

    /**
     * Test of getConnectionName method, of class AbstractIoConnection.
     */
    @Test
    public void testGetConnectionName()
    {
        AbstractIoConnection instance = new AbstractIoConnectionImpl();
        String expectedName = "Connection (" + instance.getConnectionId() + "):";
        assertEquals(expectedName, instance.getConnectionName());
    }

    /**
     * Test of isAnyRunning method, of class AbstractIoConnection.
     */
    @Test
    public void testIsAnyRunning()
    {
        //read and write true
        AbstractIoConnection instance = new AbstractIoConnectionImpl();
        instance.ioTcpReadRunnable = new IoTcpReadRunnable(instance, null, null, null)
        {
            @Override
            public boolean isRunning()
            {
                return true;
            }
        };
        instance.ioTcpWriteRunnable = new IoTcpWriteRunnable(null, null, null)
        {
            @Override
            public boolean isRunning()
            {
                return true;
            }
        };
        assertTrue(instance.isAnyRunning());

        //both false
        instance.ioTcpReadRunnable = new IoTcpReadRunnable(instance, null, null, null)
        {
            @Override
            public boolean isRunning()
            {
                return false;
            }
        };
        instance.ioTcpWriteRunnable = new IoTcpWriteRunnable(null, null, null)
        {
            @Override
            public boolean isRunning()
            {
                return false;
            }
        };
        assertFalse(instance.isAnyRunning());

        //read true write false
        instance.ioTcpReadRunnable = new IoTcpReadRunnable(instance, null, null, null)
        {
            @Override
            public boolean isRunning()
            {
                return true;
            }
        };
        instance.ioTcpWriteRunnable = new IoTcpWriteRunnable(null, null, null)
        {
            @Override
            public boolean isRunning()
            {
                return false;
            }
        };
        assertTrue(instance.isAnyRunning());

        //write true read false
        instance.ioTcpReadRunnable = new IoTcpReadRunnable(instance, null, null, null)
        {
            @Override
            public boolean isRunning()
            {
                return false;
            }
        };
        instance.ioTcpWriteRunnable = new IoTcpWriteRunnable(null, null, null)
        {
            @Override
            public boolean isRunning()
            {
                return true;
            }
        };
        assertTrue(instance.isAnyRunning());

    }

    /**
     * Test of close method, of class AbstractIoConnection.
     */
    @Test
    public void testClose() throws IOException
    {
        Socket tcpSocket = SocketFactory.getDefault().createSocket();
        AbstractIoConnection instance = new AbstractIoConnectionImpl();
        instance.canStart = true;
        instance.ioTcpReadRunnable = new IoTcpReadRunnable(instance, null, null, tcpSocket)
        {
            @Override
            public boolean isRunning()
            {
                return true;
            }
        };
        instance.ioTcpWriteRunnable = new IoTcpWriteRunnable(null, null, null)
        {
            @Override
            public boolean isRunning()
            {
                return false;
            }
        };
        assertTrue(instance.isAnyRunning());
        instance.close();
    }

    /**
     * Test of isConnected method, of class AbstractIoConnection.
     */
    @Test
    public void testIsConnected()
    {
        AbstractIoConnection instance = new AbstractIoConnectionImpl();
        assertFalse(instance.isConnected());
        instance.connected = true;
        assertTrue(instance.isConnected());
    }

    /**
     * Test of getConnectionId method, of class AbstractIoConnection.
     */
    @Test
    public void testGetandSetConnectionId()
    {
        AbstractIoConnection instance = new AbstractIoConnectionImpl();
        assertEquals(-1, instance.getConnectionId());
        instance.setConnectionId(1);
        assertEquals(1, instance.getConnectionId());

    }

    /**
     * Test of sendUdpMessage method, of class AbstractIoConnection.
     */
    @Test
    @Ignore
    public void testSendUdpMessage()
    {
    }

    /**
     * Test of sendTcpMessage method, of class AbstractIoConnection.
     */
    @Test
    public void testSendTcpMessage()
    {
        AbstractIoConnection instance = new AbstractIoConnectionImpl();
        assertNotNull(instance.ioTcpWriteRunnable);
        instance.sendTcpMessage("test");
        instance.ioTcpWriteRunnable = null;
        assertNull(instance.ioTcpWriteRunnable);
        instance.sendTcpMessage("test");
    }

    /**
     * Test of enqueueReceivedMessage method, of class AbstractIoConnection.
     */
    @Test
    public void testEnqueueReceivedMessage()
    {
        AbstractIoConnection instance = new AbstractIoConnectionImpl();
        assertEquals(0, instance.receiveQueue.size());
        instance.enqueueReceivedMessage("test");
        assertEquals(1, instance.receiveQueue.size());
    }

    /**
     * Test of enqueueMessageToWrite method, of class AbstractIoConnection.
     */
    @Test
    public void testEnqueueMessageToWrite()
    {
        AbstractIoConnection instance = new AbstractIoConnectionImpl();
        Envelope createTcpEnvelope = EnvelopeFactory.createTcpEnvelope("TestTcp");
        Envelope createUdpEnvelope = EnvelopeFactory.createUdpEnvelope("TestUdp");
        //uncomment when UDP is added
        //instance.enqueueMessageToWrite(createUdpEnvelope);
        instance.enqueueMessageToWrite(createTcpEnvelope);
    }

    /**
     * Test of addConnectionEventListener method, of class AbstractIoConnection.
     */
    @Test
    public void testAddConnectionEventListener()
    {
        AbstractIoConnection instance = new AbstractIoConnectionImpl();
        assertEquals(0, instance.connectionEventListeners.size());
        ConnectionEventImpl connectionEventImpl = new ConnectionEventImpl();
        instance.addConnectionEventListener(connectionEventImpl);
        assertEquals(1, instance.connectionEventListeners.size());
    }

    /**
     * Test of removeConnectionEventListener method, of class AbstractIoConnection.
     */
    @Test
    public void testRemoveConnectionEventListener()
    {
        AbstractIoConnection instance = new AbstractIoConnectionImpl();
        assertEquals(0, instance.connectionEventListeners.size());
        ConnectionEventImpl connectionEventImpl = new ConnectionEventImpl();
        instance.addConnectionEventListener(connectionEventImpl);
        assertEquals(1, instance.connectionEventListeners.size());
        instance.removeConnectionEventListener(connectionEventImpl);
        assertEquals(0, instance.connectionEventListeners.size());

    }

    public class AbstractIoConnectionImpl<M> extends AbstractIoConnection<M>
    {
        public AbstractIoConnectionImpl()
        {
            super(null, null, null, null);
        }

        public AbstractIoConnectionImpl(ConnectionConfiguration config, DataReader dataReader, DataWriter dataWriter, Socket tcpSocket)
        {
            super(config, dataReader, dataWriter, tcpSocket);
        }

        @Override
        protected void processMessage(M message)
        {
            //proce
        }

        @Override
        protected void startUdp()
        {
        }

        @Override
        protected void closeUdp()
        {
        }

        @Override
        protected void sendUdpMessage(M msg)
        {
        }
    }

    private static class ConnectionEventImpl implements ConnectionEvent<Object>
    {
        public ConnectionEventImpl()
        {
        }

        @Override
        public void onSocketClosed()
        {
            fail("Method not suppose to be called here");
        }

        @Override
        public void onSocketCreated()
        {
            assert true;
        }

        @Override
        public void onSocketReceivedMessage(Object message)
        {
            fail("Method not suppose to be called here");
        }
    }
}

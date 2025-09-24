package ping.pong.net.server.io;

import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import ping.pong.net.connection.ConnectionEvent;
import ping.pong.net.connection.config.ConnectionConfigFactory;
import ping.pong.net.connection.messaging.DisconnectMessage;

/**
 *
 * @author mfullen
 */
public class ServerIoConnectionTest
{
    public ServerIoConnectionTest()
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
     * Test of processMessage method, of class ServerIoConnection.
     */
    @Test
    public void testProcessMessage()
    {
        ServerIoConnection instance = new ServerIoConnection(ConnectionConfigFactory.createConnectionConfiguration(), null, null, null, null);
        instance.addConnectionEventListener(new ConnectionEvent()
        {
            @Override
            public void onSocketClosed()
            {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void onSocketCreated()
            {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void onSocketReceivedMessage(Object message)
            {
                if (message instanceof DisconnectMessage)
                {
                    fail("Message not expceted here");
                }
                else
                {
                    assertNotNull(message);
                }
            }
        });
        assertFalse(instance.isConnected());
        instance.processMessage(new DisconnectMessage());
        assertFalse(instance.isConnected());
        instance.processMessage("Test");
    }

    /**
     * Test of fireOnSocketCreated method, of class ServerIoConnection.
     */
    @Test
    @Ignore
    public void testFireOnSocketCreated()
    {
        System.out.println("fireOnSocketCreated");
        ServerIoConnection instance = null;
        instance.fireOnSocketCreated();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}

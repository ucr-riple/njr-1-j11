package ping.pong.net;

import org.junit.AfterClass;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import org.junit.BeforeClass;
import org.junit.Test;
import ping.pong.net.client.Client;
import ping.pong.net.client.ClientConnectionListener;
import ping.pong.net.connection.messaging.MessageListener;

/**
 *
 * @author mfullen
 */
public class AppTest
{
    public AppTest()
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
     * Test of main method, of class App.
     */
    @Test
    public void clientSetupTest()
    {
        Client client = new Client()
        {
            boolean connected = false;
            boolean running = false;

            @Override
            public void start()
            {
                running = true;
            }

            @Override
            public void close()
            {
                running = false;
            }

            @Override
            public boolean isConnected()
            {
                return connected;
            }

            @Override
            public int getId()
            {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void addMessageListener(MessageListener listener)
            {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void removeMessageListener(MessageListener listener)
            {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void addConnectionListener(ClientConnectionListener listener)
            {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void removeConnectionListener(ClientConnectionListener listener)
            {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void sendMessage(Object message)
            {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
        assertNotNull(client);
        assertFalse(client.isConnected());

        client.start();
        assertFalse(client.isConnected());

        client.close();
        assertFalse(client.isConnected());
    }
}

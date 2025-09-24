package ping.pong.net.client.io;

import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.AfterClass;
import static org.junit.Assert.assertFalse;
import org.junit.BeforeClass;
import org.junit.Test;
import ping.pong.net.connection.config.ConnectionConfiguration;
import ping.pong.net.connection.messaging.DisconnectMessage;

/**
 *
 * @author mfullen
 */
public class ClientIoConnectionTest
{
    public ClientIoConnectionTest()
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
     * Test of processMessage method, of class ClientIoConnection.
     */
    @Test
    public void testProcessMessage()
    {
        System.out.println("processMessage");
        ConnectionConfiguration connConfig = new ConnectionConfiguration()
        {
            @Override
            public int getPort()
            {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void setPort(int port)
            {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public int getUdpPort()
            {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void setUdpPort(int udpport)
            {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public String getIpAddress()
            {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void setIpAddress(String ipAddress)
            {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public boolean isSsl()
            {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void setSsl(boolean sslEnabled)
            {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public String getKeystorePath()
            {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void setKeystorePath(String keystore)
            {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public String getKeystorePassword()
            {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void setKeystorePassword(String password)
            {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
        Socket tcpSocket = new Socket();
        DatagramSocket updSocket = null;
        try
        {
            updSocket = new DatagramSocket();
        }
        catch (SocketException ex)
        {
            Logger.getLogger(ClientIoConnectionTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        ClientIoConnection instance = new ClientIoConnection(connConfig, null, null, tcpSocket, updSocket);
        DisconnectMessage disconnectMessage = new DisconnectMessage();
        instance.processMessage(disconnectMessage);
        assertFalse(instance.isConnected());
    }
}

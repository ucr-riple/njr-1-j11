package ping.pong.net.connection.ssl;

import java.io.FileNotFoundException;
import java.net.ServerSocket;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import javax.net.ssl.*;
import ping.pong.net.connection.config.ConnectionConfigFactory;
import ping.pong.net.connection.config.ConnectionConfiguration;
import ping.pong.net.server.Server;
import ping.pong.net.server.io.IoServer;

/**
 *
 * @author mfullen
 */
public class SSLTestServer
{
    public static void main(String[] args) throws FileNotFoundException,
                                                  NoSuchAlgorithmException,
                                                  KeyManagementException,
                                                  Exception
    {
        usePPNServer();
    }

    public static void usePPNServer()
    {
        Server<String> server = new IoServer<String>(ConnectionConfigFactory.createPPNServerConfig(2011, true));
        server.start();
    }

    public static void createCustomServer() throws FileNotFoundException,
                                                   NoSuchAlgorithmException,
                                                   KeyManagementException,
                                                   Exception
    {
        ConnectionConfiguration config = ConnectionConfigFactory.createPPNServerConfig(2011, true);
        SSLContext ctx = SSLUtils.createSSLContext("SSLv3", config);// SSLContext.getInstance("SSLv3");
        SSLServerSocketFactory factory = ctx.getServerSocketFactory();
        ServerSocket serverSocket = factory.createServerSocket(5011);

        while (true)
        {
            try
            {
                SSLSocket accept = (SSLSocket) serverSocket.accept();
                accept.addHandshakeCompletedListener(new HandshakeCompletedListener()
                {
                    @Override
                    public void handshakeCompleted(HandshakeCompletedEvent hce)
                    {
                        System.out.println("Handshake complete");
                    }
                });
                accept.startHandshake();
                System.out.println("SocketAccepted");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}

package ping.pong.net.client.io;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ServerSocketFactory;
import org.slf4j.LoggerFactory;
import ping.pong.net.client.Client;
import ping.pong.net.client.ClientConnectionListener;
import ping.pong.net.connection.Connection;
import ping.pong.net.connection.ConnectionExceptionHandler;
import ping.pong.net.connection.DisconnectInfo;
import ping.pong.net.connection.config.ConnectionConfigFactory;
import ping.pong.net.connection.config.ConnectionConfiguration;
import ping.pong.net.connection.io.ReadFullyDataReader;
import ping.pong.net.connection.io.WriteByteArrayDataWriter;
import ping.pong.net.connection.messaging.EnvelopeFactory;
import ping.pong.net.connection.messaging.MessageListener;
import ping.pong.net.server.Server;
import ping.pong.net.server.ServerConnectionListener;
import ping.pong.net.server.io.IoServer;

/**
 *
 * @author mfullen
 */
public class TestClientNonPPNServer
{
    public static void main(String[] args) throws InterruptedException
    {
        Runnable server = new Runnable()
        {
            @Override
            public void run()
            {
                launchPPNServer();
                // launchNonPPNServer();

            }
        };

        new Thread(server).start();


        launchClient();
    }

    public static void launchPPNServer()
    {
        ConnectionConfiguration createConnectionConfiguration = ConnectionConfigFactory.createPPNConfig("localhost", 5111, 5666, false);
        IoServer<byte[]> server = new IoServer<byte[]>(createConnectionConfiguration);
        server.setCustomDataReader(new ReadFullyDataReader());
        server.setCustomDataWriter(new WriteByteArrayDataWriter());
        server.addConnectionListener(new ServerConnectionListener()
        {
            @Override
            public void connectionAdded(Server server, Connection conn)
            {
                System.out.println("Connection Added");
                //server.broadcast(EnvelopeFactory.createTcpEnvelope("Test"));
            }

            @Override
            public void connectionRemoved(Server server, Connection conn)
            {
                System.out.println("Connection removed was " + conn.getConnectionId());
            }
        });
        server.addMessageListener(new MessageListener<Connection, byte[]>()
        {
            @Override
            public void messageReceived(Connection source, byte[] message)
            {
                System.out.println("Message Received On Server From: " + source.getConnectionId());
                System.out.println("Message: " + new String(message));
            }
        });

        server.start();
    }

    public static void launchNonPPNServer()
    {
        ServerSocketFactory socketFactory = ServerSocketFactory.getDefault();
        ServerSocket tcpServerSocket = null;
        try
        {
            tcpServerSocket = socketFactory.createServerSocket(5111);
            tcpServerSocket.setReuseAddress(true);
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }


        Socket acceptingSocket = null;
        try
        {
            acceptingSocket = tcpServerSocket.accept();
        }
        catch (IOException ex)
        {
            ConnectionExceptionHandler.handleException(ex, LoggerFactory.getLogger(TestClientNonPPNServer.class));
        }
        if (acceptingSocket != null)
        {
            try
            {
                DataOutputStream dataOutputStream = new DataOutputStream(acceptingSocket.getOutputStream());
                String message = "Hey this is message";
                System.out.println("About to write");
                System.out.println("Length: " + message.getBytes().length);
                dataOutputStream.writeInt(message.getBytes().length);
                dataOutputStream.flush();
                dataOutputStream.write(message.getBytes());
                dataOutputStream.flush();
                System.out.println("Wrote " + message);

                DataInputStream dataInputStream = new DataInputStream(acceptingSocket.getInputStream());
                int size = dataInputStream.readInt();
                byte[] buffer = new byte[size];

                dataInputStream.readFully(buffer);

                System.out.println("Received: " + new String(buffer));
                // dataOutputStream.close();
                // acceptingSocket.close();
            }
            catch (IOException ex)
            {
                Logger.getLogger(TestClientNonPPNServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public static void launchClient()
    {
        ConnectionConfiguration createConnectionConfiguration = ConnectionConfigFactory.createPPNConfig("localhost", 5111, 5666, false);
        IoClient<byte[]> client = new IoClient<byte[]>(createConnectionConfiguration);
        client.setCustomDataReader(new ReadFullyDataReader());
        client.setCustomDataWriter(new WriteByteArrayDataWriter());

        client.addConnectionListener(new ClientConnectionListener()
        {
            @Override
            public void clientConnected(Client client)
            {
                System.out.println("Client connected");
                client.sendMessage(EnvelopeFactory.createTcpEnvelope("hello world".getBytes()));
            }

            @Override
            public void clientDisconnected(Client client, DisconnectInfo info)
            {
                System.out.println("Client disconnected");
            }
        });
        client.addMessageListener(new MessageListener<Client, byte[]>()
        {
            @Override
            public void messageReceived(Client source, byte[] message)
            {
                System.out.println("Message: " + new String(message));
            }
        });
        client.start();
    }
}

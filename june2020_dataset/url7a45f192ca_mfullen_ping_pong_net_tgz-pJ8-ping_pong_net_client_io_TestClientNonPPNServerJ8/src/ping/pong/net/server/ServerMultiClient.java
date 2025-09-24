package ping.pong.net.server;

import java.util.Timer;
import java.util.TimerTask;
import ping.pong.net.connection.Connection;
import ping.pong.net.connection.config.ConnectionConfigFactory;
import ping.pong.net.connection.config.ConnectionConfiguration;
import ping.pong.net.connection.messaging.Envelope;
import ping.pong.net.connection.messaging.MessageListener;
import ping.pong.net.server.io.IoServer;

public class ServerMultiClient
{
    public static void main(String[] args)
    {
         ConnectionConfiguration createPPNServerConfig = ConnectionConfigFactory.createPPNServerConfig(5011, 5012, false);
       // ConnectionConfiguration createPPNServerConfig = ConnectionConfigFactory.createPPNServerConfig(5011);
        final IoServer<String> server = new IoServer<String>(createPPNServerConfig);
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
        server.addMessageListener(new MessageListener<Connection, String>()
        {
            @Override
            public void messageReceived(Connection source, String message)
            {
                System.out.println("Message Received On Server From: " + source.getConnectionId());
                System.out.println("Message: " + message);
            }
        });

        server.start();

        boolean heartBeat = false;

        if (heartBeat)
        {
            Timer timer = new Timer("HeatBeat", true);
            timer.schedule(new TimerTask()
            {
                @Override
                public void run()
                {
                    server.broadcast(new Envelope<String>()
                    {
                        @Override
                        public boolean isReliable()
                        {
                            return true;
                        }

                        @Override
                        public String getMessage()
                        {
                            return "HeartBeat";
                        }
                    });
                }
            }, 500, 1000);
        }

    }
}

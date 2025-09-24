package ping.pong.net.server;

import java.io.IOException;
import java.net.UnknownHostException;
import ping.pong.net.connection.Connection;
import ping.pong.net.connection.config.ConnectionConfigFactory;
import ping.pong.net.connection.messaging.Envelope;
import ping.pong.net.server.io.IoServer;

/**
 *
 * @author mfullen
 */
public class ServerExample
{
    public static void main(String[] args) throws UnknownHostException,
                                                  IOException,
                                                  InterruptedException
    {
        //Server<String> server = new DefaultIoServerConnection<String>(ConnectionConfigFactory.createPPNConfig(), new Socket("localhost", 5011), null);
        //Server<Envelope<String>> server = new IoServer<String>();
        //IoServerImpl<String> server = new IoServer<String>();
        IoServer<String> server = new IoServer<String>(ConnectionConfigFactory.createPPNConfig("localhost", 5011, 5012, false));
        server.addConnectionListener(new ServerConnectionListener()
        {
            @Override
            public void connectionAdded(Server server, Connection conn)
            {
                System.out.println("Connection Added");
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
                        return "Test";
                    }
                });
            }

            @Override
            public void connectionRemoved(Server server, Connection conn)
            {
                System.out.println("Connection removed was " + conn.getConnectionId());
            }
        });

//        server.start();
//
//        Thread.sleep(5000);
//        server.shutdown();

        // Thread.sleep(5000);
        server.start();

//
//        ThreadMXBean threads = ManagementFactory.getThreadMXBean();
//        ThreadInfo[] threadInfos = threads.getThreadInfo(threads.getAllThreadIds());
//        for (int i = 0; i < threadInfos.length; i++)
//        {
//            ThreadInfo info = threadInfos[i];
//            long cpuTimeCumulative = threads.getThreadCpuTime(info.getThreadId()); // in nano seconds
//            System.out.println("Info: " + info);
//
//        }

    }
}

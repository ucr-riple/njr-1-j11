package com.realtimecep.scribe;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;

/**
 * Scribe Server in Java Class.
 * <p/>
 * <p/>This server can receive messages from Scribe central server.
 * <p/>It Also can be applied to integrating with Scribe and Java Application.
 * <p/>For instance, Esper real-time Application and Scribe Log Aggregator.
 *
 * @author <a href=mailto:tedd824@gmail.com">Ted Won</a>
 * @version 1.0
 */
public class ScribeJavaServer {

    public static ScribeLogImpl handler;

    public static scribe.thrift.scribe.Processor processor;

    public static int PORT = 2463;

    public void runServer() {

        try {

            handler = new ScribeLogImpl();
            processor = new scribe.thrift.scribe.Processor(handler);

            TNonblockingServerSocket socket = new TNonblockingServerSocket(PORT);

            final TServer server = new THsHaServer(processor, socket,
                    new TFramedTransport.Factory(), new TBinaryProtocol.Factory());

            System.out.println("Starting the ScribeJavaServer... on PORT: " + PORT);

            server.serve();

        } catch (Exception x) {
            x.printStackTrace();
        }
    }

    public static void main(String[] args) {

        ScribeJavaServer server = new ScribeJavaServer();
        server.runServer();

    }
}

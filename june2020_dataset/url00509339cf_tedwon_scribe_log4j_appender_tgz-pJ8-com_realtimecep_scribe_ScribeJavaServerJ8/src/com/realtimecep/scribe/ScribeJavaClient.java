package com.realtimecep.scribe;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import scribe.thrift.LogEntry;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Scribe Client in Java Class.
 * <p/>
 * <p/>This server can receive messages from Scribe central server.
 * <p/>It Also can be applied to integrating with Scribe and Java Application.
 * <p/>For instance, Esper real-time Application and Scribe Log Aggregator.
 *
 * @author <a href=mailto:tedd824@gmail.com">Ted Won</a>
 * @version 1.0
 */
public class ScribeJavaClient {

    private static String scribeCategory = "default";

    public static void main(String[] args) {

        try {

            TSocket sock = new TSocket(new Socket("localhost", ScribeJavaServer.PORT));
            TFramedTransport transport = new TFramedTransport(sock);
            TBinaryProtocol protocol = new TBinaryProtocol(transport, false, false);

            scribe.thrift.scribe.Client client = new scribe.thrift.scribe.Client(protocol);

            perform(client);

            transport.close();

        } catch (Exception x) {
            x.printStackTrace();
        }
    }

    private static void perform(scribe.thrift.scribe.Client client) throws TException {

        List<LogEntry> logEntries = new ArrayList<LogEntry>(1);

        String message = String.format("%s", "Message from My Test Client Application");
        LogEntry entry = new LogEntry(scribeCategory, message);
        logEntries.add(entry);

        client.Log(logEntries);

    }
}

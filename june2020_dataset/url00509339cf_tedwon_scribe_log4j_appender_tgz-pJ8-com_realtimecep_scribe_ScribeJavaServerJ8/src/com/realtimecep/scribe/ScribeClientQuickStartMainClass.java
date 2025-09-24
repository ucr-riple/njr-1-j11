package com.realtimecep.scribe;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import scribe.thrift.LogEntry;
import scribe.thrift.scribe;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Scribe Client Quick Start Class.
 * <p/>See https://github.com/alexlod/scribe-log4j-appender/blob/master/ScribeAppender.java
 *
 * @author <a href="mailto:tedd824@gmail.com">Ted Won</a>
 * @version 1.0
 */
public class ScribeClientQuickStartMainClass {

    private static String scribeHost = "localhost";
    private static int scribePort = 1463;
    private static String scribeCategory = "test";

    public static void main(String[] args) throws Exception {

        // Thrift boilerplate code
        List<LogEntry> logEntries = new ArrayList<LogEntry>(1);
        TSocket sock = new TSocket(new Socket(scribeHost, scribePort));
        TFramedTransport transport = new TFramedTransport(sock);
        TBinaryProtocol protocol = new TBinaryProtocol(transport, false, false);

        scribe.Client client = new scribe.Client(protocol, protocol);

        String message = String.format("%s", "Message from My Test Client Application");
        LogEntry entry = new LogEntry(scribeCategory, message);
        logEntries.add(entry);
        client.Log(logEntries);

        transport.close();
    }
}
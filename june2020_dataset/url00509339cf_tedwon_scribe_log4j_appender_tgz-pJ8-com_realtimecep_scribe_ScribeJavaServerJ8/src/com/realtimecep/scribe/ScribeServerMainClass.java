package com.realtimecep.scribe;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TBinaryProtocol.Factory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import scribe.thrift.scribe;

/**
 * Scribe Server in Java.
 *
 * @author Ted Won
 */
public class ScribeServerMainClass {

    public static void main(String[] args) {

        try {

            TServerSocket serverTransport = new TServerSocket(1463);
            scribe.Processor processor = new scribe.Processor(new ScribeLogImpl());
            Factory protFactory = new TBinaryProtocol.Factory(false, false);
            TServer server = new TThreadPoolServer(processor, serverTransport, protFactory);

            System.out.println("Starting server on port 1463 ...");

            server.serve();


        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

}

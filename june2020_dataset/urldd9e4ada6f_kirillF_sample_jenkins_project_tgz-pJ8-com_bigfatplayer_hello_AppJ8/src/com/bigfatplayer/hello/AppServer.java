package com.bigfatplayer.hello;

import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;

/**
 * User: viktor
 * Date: 6/19/13
 * Time: 3:25 PM
 */
public class AppServer implements Runnable {
    @Override
    public void run() {
        CalculatorServer cs = new CalculatorServer();
        Calculator.Processor processor = new Calculator.Processor(cs);
        TServerTransport serverTransport = null;
        try {
            serverTransport = new TServerSocket(9090);
        } catch (TTransportException e) {
            e.printStackTrace();
        }
        TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));
        server.serve();
    }
}

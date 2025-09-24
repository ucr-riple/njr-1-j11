package com.bigfatplayer.hello;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

/**
 * User: viktor
 * Date: 6/19/13
 * Time: 3:26 PM
 */
public class AppClient implements Runnable {

    @Override
    public void run() {
        System.out.println("client started");
        TTransport transport = new TSocket("localhost", 9090);
        try {
            transport.open();

            TProtocol protocol = new TBinaryProtocol(transport);
            Calculator.Client client = new Calculator.Client(protocol);
            client.ping();
            Thread.yield();
            int result = client.add(8, 8);
            System.out.println(result);

        } catch (TTransportException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
        } finally {
            transport.close();
        }
    }
}

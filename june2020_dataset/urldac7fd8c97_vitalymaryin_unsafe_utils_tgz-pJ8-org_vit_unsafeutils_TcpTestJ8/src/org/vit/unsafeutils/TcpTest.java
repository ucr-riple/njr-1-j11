package org.vit.unsafeutils;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.vit.unsafeutils.api.OrderData;
import org.vit.unsafeutils.api.UnsafeSerializable;
import org.vit.unsafeutils.serializer.UnsafeSerializer;
import org.vit.unsafeutils.tcp.TcpReceiveBuffer;
import org.vit.unsafeutils.tcp.TcpStreamConsumer;
import org.vit.unsafeutils.tcp.TcpStreamProcessor;
import org.vit.unsafeutils.tcp.TcpStreamProducer;

import java.util.ArrayList;

/*
Copyright 2014 Vitaly Maryin

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
public class TcpTest {
    private static final String host = "localhost";
    private static final int port = 12345;

    private static void log4j(){
        Logger rootLogger = Logger.getRootLogger();
        rootLogger.setLevel(Level.DEBUG);
        PatternLayout layout = new PatternLayout("%d{ISO8601} [%t] %-5p %c %x - %m%n");
        rootLogger.addAppender(new ConsoleAppender(layout));
    }

    public static void main(String[] args){
        log4j();

        UnsafeSerializer.init("org.vit.unsafeutils.api");


        // setup client
        TcpStreamConsumer client = new TcpStreamConsumer();
        TcpReceiveBuffer cltBuff = new TcpReceiveBuffer(100000000);
        client.setHost(host);
        client.setPort(port);
        client.setReceiveBuffer(cltBuff);
        client.setProcessor(new TcpStreamProcessor() {
            @Override
            public void process(ArrayList<UnsafeSerializable> data) {

            }

            @Override
            public void run() {

            }
        });

        // setup server
        TcpStreamProducer srv = new TcpStreamProducer();
        TcpReceiveBuffer srvBuff = new TcpReceiveBuffer(10000000);
        srv.setHost(host);
        srv.setPort(port);
        srv.setReceiveBuffer(srvBuff);
        srv.setSelectionOpts(1);


        srv.start();
        sleep(1000);

        client.start();
        sleep(1000);

        while (!client.isConnected()){
            sleep(1000);
        }

        long sendStart = System.nanoTime();
        OrderData md = new OrderData();
        md.setRic("VOD.L");

        for (int t=0;t<10;t++){
            for (int i = 0;i<20000;i++){
                md.setLast(Math.random()*i);
                srv.sendData(md);
            }
            System.out.println("sent 20K in " + (System.nanoTime() - sendStart)/1000 + "us");
            sleep(1000 * t);
        }

        client.stop();
        srv.stop();
    }


    private static void sleep(long tm){
        try {
            Thread.sleep(tm);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

package org.vit.unsafeutils.tcp;

import org.apache.log4j.Logger;
import org.vit.unsafeutils.api.UnsafeSerializable;
import org.vit.unsafeutils.serializer.UnsafeSerializer;
import org.vit.unsafeutils.service.Service;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

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
public class TcpStreamProducer extends TcpGenericManager implements Service, Runnable {
    private static Logger logger = Logger.getLogger(TcpStreamProducer.class);
    private static boolean debugEnabled = logger.isDebugEnabled();
    private int selectionOpts = SelectionKey.OP_WRITE;

    @Override
    public void stop() {
        started = false;
        if (serverChannel != null && serverChannel.isOpen())
            try {
                serverChannel.close();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
    }

    @Override
    public void start() {
        try {
            logger.info("starting up tcp producer ...");
            selector = initSelector();
            new Thread(this).start();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public boolean shouldStart() {
        return true;
    }

    private Selector initSelector() throws IOException {
        Selector socketSelector = SelectorProvider.provider().openSelector();
        serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);
        serverChannel.socket().setReceiveBufferSize(so_RCVBUF);
        InetSocketAddress isa = new InetSocketAddress(InetAddress.getByName(host), port);
        serverChannel.socket().bind(isa);
        serverChannel.register(socketSelector, SelectionKey.OP_ACCEPT);

        return socketSelector;
    }

    @Override
    public void run() {
        while (started) {
            try {
                selector.select();
                Iterator selectedKeys = selector.selectedKeys().iterator();
                while (selectedKeys.hasNext()) {
                    SelectionKey key = (SelectionKey) selectedKeys.next();
                    selectedKeys.remove();

                    if (!key.isValid()) {
                        logger.error("selection key is not valid. Closing client connection!");
                        key.channel().close();
                        key.cancel();
                        continue;
                    }
                    if (key.isAcceptable()) {
                        accept(key);
                    } else if (key.isWritable()) {
                        if (debugEnabled) logger.debug("have writeOp for: " + key.channel().toString());
                        write(key);
                    } else if (key.isReadable()) {
                        if (debugEnabled) logger.debug("have readOp for: " + key.channel().toString());
                        read(key);
                    }
                }
            }catch (CancelledKeyException cke){
                logger.error(cke.getMessage(), cke);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    private void accept(SelectionKey key) throws IOException {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        SocketChannel socketChannel = serverSocketChannel.accept();
        if (socketChannel != null){
            socketChannel.configureBlocking(false);
            socketChannel.socket().setKeepAlive(true);
            socketChannel.socket().setTcpNoDelay(true);
            socketChannel.register(selector, selectionOpts);
            pendingData.put(socketChannel, new ConcurrentLinkedQueue<byte[]>());
            logger.info("accepted connection from "
                    + socketChannel.socket().getInetAddress().getHostAddress()
                    + ":" + socketChannel.socket().getRemoteSocketAddress());
        }
    }

    @Override
    public void sendData(UnsafeSerializable data){
        try {
            byte[] arr = UnsafeSerializer.write(data);
/*            if (debugEnabled){
                logger.debug("barr size:" + arr.length);
                logger.debug("barr :" + Arrays.toString(arr));
            }*/

            Iterator<SocketChannel> iterator = pendingData.keySet().iterator();
            while (iterator.hasNext()){
                SocketChannel channel = iterator.next();
                pendingData.get(channel).offer(arr); // todo need to rethink that memory waste
                try {
                    channel.register(selector, SelectionKey.OP_WRITE);
                } catch (ClosedChannelException cce){
                    iterator.remove();
                    logger.error("channel was closed.");
                }
            }
            selector.wakeup();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }


    public void setSelectionOpts(int selectionOpts) {
        this.selectionOpts = selectionOpts;
    }
}

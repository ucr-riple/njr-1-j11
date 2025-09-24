package org.vit.unsafeutils.tcp;

import org.apache.log4j.Logger;
import org.vit.unsafeutils.api.UnsafeSerializable;
import org.vit.unsafeutils.serializer.UnsafeSerializer;
import org.vit.unsafeutils.service.Service;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.ConnectionPendingException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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
public class TcpStreamConsumer extends TcpGenericManager implements Service, Runnable {
    private static Logger logger = Logger.getLogger(TcpStreamConsumer.class);
    private static boolean debugEnabled = logger.isDebugEnabled();
    private static final Lock lock = new ReentrantLock();
    private int selectionOpts = SelectionKey.OP_READ;
    private boolean connected = false;

    @Override
    public void stop()  {
        started = false;
        if (socket != null && socket.isOpen())
            try {
                socket.close();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
    }

    @Override
    public void start(){
        makeConnection();
    }

    @Override
    public boolean shouldStart() {
        return true;
    }

    private void makeConnection(){
        try {
            hostAddress = InetAddress.getByName(host);
            selector = SelectorProvider.provider().openSelector();
            while (socket == null
                    || ((!socket.isOpen() || !socket.isConnected()
                    || !socket.socket().isConnected())
                    && !socket.isConnectionPending())
                    ){
                try {
                    if(socket == null)
                        socket = openSocket();
                    else if (!socket.isOpen() || !socket.isConnected()){
                        socket.close();
                        socket = openSocket();
                    }
                    logger.info("connecting to:" + hostAddress.getHostName() + ":" + port);
                    boolean success = socket.connect(new InetSocketAddress(hostAddress, port));
                    if (!success){
                        while (!success){
                            success = socket.finishConnect();
                        }
                    }
                    logger.info("connected to:" + hostAddress.getHostName() + ":" + port);
                    socket.register(selector, selectionOpts);
                    pendingData.put(socket, new ConcurrentLinkedQueue<byte[]>());
                    connected = true;
                }catch (ConnectException e) {
                    logger.error(e.getMessage(), e);
                } catch (ClosedChannelException e) {
                    logger.error(e.getMessage(), e);
                } catch (ConnectionPendingException e) {
                    logger.error(e.getMessage(), e);
                    break;
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    logger.error(e.getMessage(), e);
                }
            }
            new Thread(this, "TcpStreamConsumer").start();
        } catch (UnknownHostException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    protected SocketChannel openSocket() throws IOException {
        if (debugEnabled)logger.debug("Opening socket");
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.socket().setKeepAlive(true);
        socketChannel.socket().setTcpNoDelay(true);
        socketChannel.socket().setReceiveBufferSize(so_RCVBUF);
        if (debugEnabled) {
            logger.debug("SO_RCVBUF = "+so_RCVBUF);
            logger.debug("Socket opened");
        }
        return socketChannel;
    }

    @Override
    public void sendData(UnsafeSerializable data){
        throw new UnsupportedOperationException();
    }

    public void requestAsync(UnsafeSerializable data){
        if (!connected)
            return;
        try {
            pendingData.get(socket).offer(UnsafeSerializer.write(data));
            if (selectionOpts == SelectionKey.OP_WRITE){
                socket.register(selector, SelectionKey.OP_WRITE);
            }
            if (debugEnabled) logger.debug("notifying selector");
            selector.wakeup();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void requestSync(UnsafeSerializable data){
        if (!connected)
            return;
        try {
            pendingData.get(socket).offer(UnsafeSerializer.write(data));
            if (selectionOpts == SelectionKey.OP_WRITE){
                socket.register(selector, SelectionKey.OP_WRITE);
            }
            selector.wakeup();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        final String id = data.getObjectId();
        Object sync = new Object();
        if (debugEnabled) logger.debug("sent request for:" + id);
        long start = System.nanoTime();
        syncRequestQueue.put(id, sync);
        synchronized (sync){
            try {
                sync.wait(syncReqTMOUT);
                long stop = System.nanoTime();
                if (debugEnabled && (stop - start) <= syncReqTMOUT*1000000L) logger.debug("reply for:" + id + " came back in " + (stop-start) + "ns");
            } catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    @Override
    public void run() {
        try {
            while (started){
                selector.select();

                Set backKeys = selector.selectedKeys();
                synchronized (backKeys){
                    Iterator selectedKeys = backKeys.iterator();
                    while (selectedKeys.hasNext()) {
                        SelectionKey key = (SelectionKey) selectedKeys.next();
                        selectedKeys.remove();

                        if (!key.isValid()) {
                            logger.error("Selection key is not valid");
                            continue;
                        }
                        boolean success=false;
                        if (key.isReadable()) {
                            if (debugEnabled) logger.debug("have readOp for: " + key.channel().toString());
                            success = read(key);
                        }else if (key.isWritable()) {
                            if (debugEnabled) logger.debug("have writeOp for: " + key.channel().toString());
                            success = write(key);
                        }
                        if (!success){
                            throw new IOException("cannot read from socket");
                        }
                    }
                }
            }
        } catch (ClosedChannelException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        if (started)
            makeConnection();
    }

    public void setSelectionOpts(int selectionOpts) {
        this.selectionOpts = selectionOpts;
    }

    public boolean isConnected(){
        return connected;
    }
}

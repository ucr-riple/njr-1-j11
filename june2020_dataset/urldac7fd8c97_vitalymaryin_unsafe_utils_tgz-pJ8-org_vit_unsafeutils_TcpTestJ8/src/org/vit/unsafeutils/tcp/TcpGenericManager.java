package org.vit.unsafeutils.tcp;

import org.apache.log4j.Logger;
import org.vit.unsafeutils.api.UnsafeSerializable;
import org.vit.unsafeutils.serializer.UnsafeSerializer;

import java.io.IOException;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
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
public class TcpGenericManager {
    private static Logger logger = Logger.getLogger(TcpGenericManager.class);
    private static boolean isDebugEnabled = logger.isDebugEnabled();
    protected TcpStreamProcessor processor;
    protected String host;
    protected int port;

    protected InetAddress hostAddress;
    protected Selector selector;
    protected TcpReceiveBuffer receiveBuffer;
    protected Map<SocketChannel, ConcurrentLinkedQueue<byte[]>> pendingData = new HashMap<SocketChannel, ConcurrentLinkedQueue<byte[]>>();
    protected static final ConcurrentHashMap<String, Object> syncRequestQueue = new ConcurrentHashMap<String, Object>(1);
    protected SocketChannel socket;
    protected int so_RCVBUF = 65536;
    protected ServerSocketChannel serverChannel;
    protected volatile boolean started = true;
    protected boolean workInSync = false;
    protected long syncReqTMOUT = 1000L;

    public void sendData(UnsafeSerializable data){
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setReceiveBuffer(TcpReceiveBuffer receiveBuffer) {
        this.receiveBuffer = receiveBuffer;
    }

    protected boolean read(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        int totalBytes = 0;
        int numRead;
        try {
            ByteBuffer readBuffer = receiveBuffer.getReadBuffer();
            readBuffer.clear();
            while (true){
                numRead = socketChannel.read(readBuffer);
                totalBytes += numRead;
                if (totalBytes != 0 && receiveBuffer.atEnd())
                    break;
                else if (numRead == -1)
                    throw new IOException("peer closed connection");
                else if (processor == null)
                    return false;
                else if (totalBytes == this.receiveBuffer.getCapacity()){
                    throw new Exception("!!! Read buffer overflow !!!");
                }
            }
            if (isDebugEnabled) logger.debug("got bytes:" + totalBytes);
            processor.process(UnsafeSerializer.read(readBuffer, totalBytes));
            if (workInSync){
                ArrayList<UnsafeSerializable> result = UnsafeSerializer.read(readBuffer, totalBytes);
                for (UnsafeSerializable obj : result){
                    String id = obj.getObjectId();
                    Object sync = syncRequestQueue.remove(id);
                    if (sync != null)
                        synchronized (sync){
                            sync.notifyAll();
                        }
                }
            }
            return true;
        } catch (IOException e) {
            key.cancel();
            socketChannel.close();
            logger.error(e.getMessage(), e);
            return false;
        } catch (Exception ex){
            key.cancel();
            socketChannel.close();
            logger.error(ex.getMessage(), ex);
            return false;
        }
    }

    protected boolean write(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        try{
            while (!pendingData.get(socketChannel).isEmpty()){
                long start = System.nanoTime();
                ByteBuffer buf = ByteBuffer.wrap(pendingData.get(socketChannel).poll());
                while (buf.hasRemaining()){
                    socketChannel.write(buf);
                }
                if (isDebugEnabled){
                    String info = socketChannel.socket().getInetAddress().getHostAddress()
                            + ":" + socketChannel.socket().getRemoteSocketAddress();
                    logger.debug("wrote " + buf.limit() + " bytes to " + info + " in " + (System.nanoTime() - start) + "ns");
                }
            }
            key.interestOps(SelectionKey.OP_READ);
            return true;
        } catch (IOException ioe){
            pendingData.remove(socketChannel);
            key.cancel();
            socketChannel.close();
            logger.error("peer connection has been closed");
            return false;
        } catch (Exception ex){
            logger.error(ex.getMessage(), ex);
            pendingData.remove(socketChannel);
            key.cancel();
            socketChannel.close();
            return false;
        }
    }

    public void setProcessor(TcpStreamProcessor processor) {
        this.processor = processor;
    }

    public void setWorkInSync(boolean workInSync) {
        this.workInSync = workInSync;
    }

    public void setSyncReqTMOUT(long syncReqTMOUT) {
        this.syncReqTMOUT = syncReqTMOUT;
    }
}

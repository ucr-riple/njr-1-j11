/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package minewebsocket.handlers;

import java.util.logging.Level;
import java.util.logging.Logger;
import minewebsocket.interfaces.JSONListener;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import minewebsocket.interfaces.ConnectedCallback;
import minewebsocket.org.java_websocket.client.WebSocketClient;
import minewebsocket.org.java_websocket.drafts.Draft;
import minewebsocket.org.java_websocket.drafts.Draft_10;
import minewebsocket.org.java_websocket.handshake.ServerHandshake;

/**
 *
 * @author charles
 */
public class MessageHandler implements ConnectedCallback , Runnable{
    
    private static Connection connection = null;
    private LinkedList<JSONListener> listeners = new LinkedList();
    private BlockingQueue<String> queue = null;
    
    public MessageHandler(String hostname, int port) throws URISyntaxException, InterruptedException {
        System.out.println(hostname);
        queue = new ArrayBlockingQueue<String>(100);
        connection = new Connection(new URI("ws://" + hostname + ":" + port), new Draft_10());
    }
    
    //Hook classes in that want to receive the data.
    public void registerListener(JSONListener conn) {
        listeners.addLast(conn);
    }
    
    public boolean removeListener(JSONListener conn) {
        return listeners.remove(conn);
    }
    
    //Take an arbitrary number of pins, construct a JSON message and send it to 
    //the connection for a read request from all of the pins
    
    public synchronized boolean getFromPins(int ... pins) {
        if (pins.length == 0 || pins.length > 8) return false; //Safety check
        HashMap<String, Integer[]> reader = new HashMap();
        
        Integer pinNums[] = new Integer[pins.length];
        
        for (int i = 0; i < pins.length; i++) {
            pinNums[i] = Integer.valueOf(pins[i]);
        }
        
        reader.put("read", pinNums);
        Gson gson = new Gson();
        String message = gson.toJson(reader);
        return queue.offer(message);
    }
    
    //Write simple command to pin, response will be expected, ping will be triggered for 1 second
    //response will not be broadcast.
    
    public synchronized boolean sendToPin(int value, int pin) {
        //Build write map
        HashMap <String, Object> write = new HashMap();//Top level object
        HashMap <Integer, Object> values = new HashMap();//Store the write values
        HashMap<String, Object> vars = new HashMap();//Store the parameters
        vars.put("value", value);
        vars.put("broadcast", false);
        vars.put("response", true);
        vars.put("trigger", 1000);
        
        values.put(Integer.valueOf(pin), vars);
        write.put("write", values);
        
        Gson gson = new Gson();
        String messageToSend = gson.toJson(write);
        return queue.offer(messageToSend);
    }
    
    //Send a value to a pin, how long to trigger the pin, whether a response is expected, whether to broad
    //cast that response and read from pins.
    public synchronized boolean sendToPin(int value, int pin, long triggerTime, boolean response, boolean broadcast
            , int ... read) {
        if (read.length > 8) return false;
        
        //Build write map
        HashMap <String, Object> write = new HashMap();//Top level object
        HashMap <Integer, Object> values = new HashMap();//Store the write values
        HashMap<String, Object> vars = new HashMap();//Store the write parameters
        
        
        vars.put("value", value);
        vars.put("response", response);
        vars.put("broadcast", response);
        vars.put("trigger", triggerTime);
        
        values.put(Integer.valueOf(pin), vars);
        write.put("write", values);
        
        //Build read map
        Integer pinNums[] = new Integer[read.length];
        
        for (int i = 0; i < read.length; i++) {
            pinNums[i] = Integer.valueOf(read[i]);
        }
        write.put("read", pinNums);
        
        
        Gson gson = new Gson();
        String messageToSend = gson.toJson(write);
        return queue.offer(messageToSend);
    }
    
    //Send a message to write to a log
    public boolean sendLogMessage(String message) {
        JsonObject jobj = new JsonObject();
        jobj.addProperty("log", message);
        String value  = jobj.toString();
        return queue.offer(message);
    }
    
    //Send a broadcast message
    public boolean broadcastMessage(String message) {
        JsonObject jobj = new JsonObject();
        jobj.addProperty("broadcast", message);
        String value = jobj.toString();
        return queue.offer(message);
    }

    //Test to make sure that connection is open
    @Override
    public boolean isConnected() {
        if (connection.isOpen()) return true;
        return false;
    }

    @Override
    public void closeConnection() {
        for (JSONListener c: listeners) {
            c.connectionClosed();
        }
        connection.close();
    }

    
    
    
    /*
     * The connection class that actually creates and handles the websocket
     * connection.  This is also where the registered listeners get their messages
     * and where they get their connection information.
     */
    public class Connection extends WebSocketClient {
    
        public Connection(URI serverUri, Draft draft) {
            super(serverUri, draft);
        }

        public Connection(URI serverURI) {
            super(serverURI);
        }

        //Let all registered listeners know that a connection established
        @Override
        public void onOpen(ServerHandshake handshakedata) {
            System.out.println( "opened connection");
            for (JSONListener c: listeners) {
                c.connected(true);
            }
        }

        @Override
        public void onMessage(String message) {
            for (JSONListener c : listeners) {
                c.messageReceived(message);
            }
        }

        @Override
        public void onClose(int code, String reason, boolean remote) {
            for (JSONListener c: listeners) {
                c.connectionClosed();
            }
            System.out.println( "Connection closed by " + ( remote ? "remote peer" : "me" ) );
        }

        @Override
        public void onError(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    //Start the thread that locks on the queue.  Started here for safety
    @Override
    public void run() {
        Thread t = new Thread(new QueueManager());
        t.start();
    }
    
    //A separate thread the removes messages from the queue and sends them to the connection
    private class QueueManager implements Runnable {
        @Override
        public void run() {
            try {
                openConn();//Blocks
            } catch (InterruptedException ex) {
                Logger.getLogger(MessageHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            while (true) {
                try {
                    String message = queue.take();
                    if (connection.isClosing() || connection.isClosed()) break;
                    connection.send(message);
                } catch (InterruptedException ex) {
                    Logger.getLogger(MessageHandler.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception e) {
                    //Logger.getLogger(MessageHandler.class.getName()).log(Level.SEVERE, null, e);
                }
            }
            
        }
        
        //A simple waitlock so that I'm not trying to send messaged to a connection that's not ready
        private synchronized boolean openConn() throws InterruptedException {
            return connection.connectBlocking();
        }
    }
}

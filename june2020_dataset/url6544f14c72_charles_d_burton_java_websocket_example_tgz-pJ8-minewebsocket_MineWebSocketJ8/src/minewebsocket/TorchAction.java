/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package minewebsocket;

import java.net.URISyntaxException;
import minewebsocket.handlers.MessageHandler;
import minewebsocket.interfaces.JSONListener;

/**
 *
 * @author charles
 */
public class TorchAction implements JSONListener{
    Thread t = null;
    MessageHandler mh = null;
    public TorchAction(String hostname, int port) throws URISyntaxException, InterruptedException {
        mh = new MessageHandler(hostname, port);
        t = new Thread(mh);
        mh.registerListener(this);
        BaconListener bl = new BaconListener(mh);
        EggListener el = new EggListener(mh);
    }
    
    public void startTest(){
        t.start();
        mh.getFromPins(0,1,2,3,4,5,6,7);
        mh.sendLogMessage("Log Test");
        mh.broadcastMessage("Broadcast Test");
        //mh.closeConnection();

        //mh.sendToPin("Pin test", 1, true);
        
        mh.sendToPin(1, 1, 1000, true, true, 1,2,3,4,5);
        for (int i = 0; i < 8; i++) {
            long timeout = 0;
            if (i == 0) {
                timeout = 1000;
            } else {
                timeout = 1000 * (i);
            }
            mh.sendToPin(1, i, timeout, true, true);
        }
        //mh.sendLogMessage("Log Test");
        //mh.broadcastMessage("Broadcast Test");
        //mh.closeConnection();

        
    }

    @Override
    public void messageReceived(String message) {
        System.out.println("From Listener: " + message);
    }

    @Override
    public void connected(boolean status) {
        System.out.println("Connection Confirmed");
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void connectionClosed() {
        System.out.println("Connection Closed");
    }
}

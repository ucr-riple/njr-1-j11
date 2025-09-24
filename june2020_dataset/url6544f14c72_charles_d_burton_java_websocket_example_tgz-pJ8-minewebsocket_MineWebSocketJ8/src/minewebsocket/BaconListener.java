/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package minewebsocket;

import minewebsocket.handlers.MessageHandler;
import minewebsocket.interfaces.JSONListener;

/**
 *
 * @author charles
 */
public class BaconListener implements JSONListener {
    
    public BaconListener(MessageHandler mh) {
        mh.registerListener(this);
    }

    @Override
    public void messageReceived(String message) {
        System.out.println("From Bacon: " + message);
    }

    @Override
    public void connected(boolean status) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void connectionClosed() {
        //throw new UnsupportedOperationException("Not supported yet.");
    }
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package minewebsocket.interfaces;

/**
 *
 * @author charles
 */
public interface JSONListener {
    public void messageReceived(String message);//Message Received From Socket
    public void connected(boolean status); //Callback to notify of connection status
    public void connectionClosed();  //Callback to notify of connection closure
}

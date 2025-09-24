/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package minewebsocket.interfaces;

/**
 *
 * @author charles
 */
public interface ConnectedCallback {
    public boolean isConnected();
    public void closeConnection();
}

package com.gesoftware.venta.network;

import com.gesoftware.venta.network.model.Message;

/* *
* Connection interface definition
* */
public interface IConnection {
    /* *
     * METHOD: Establishes connection to server
     * RETURN: True if success, False otherwise
     * AUTHOR: Eliseev Dmitry
     * */
    public boolean connect();

    /* *
     * METHOD: Determines if connection established
     * RETURN: True if connection established, False otherwise
     * AUTHOR: Eliseev Dmitry
     * */
    public boolean isConnected();

    /* *
     * METHOD: Closes connection
     * AUTHOR: Eliseev Dmitry
     * */
    public void disconnect();

    /* *
     * METHOD: Sends message to server
     * RETURN: True if success, False otherwise
     *  PARAM: [IN] message - message to send
     * AUTHOR: Eliseev Dmitry
     * */
    public boolean send(final Message message);
} /* End of 'IConnection' interface */

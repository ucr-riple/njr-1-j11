package com.gesoftware.venta.network.handlers;

import com.gesoftware.venta.network.model.Message;
import com.gesoftware.venta.network.model.ServerResponse;

import java.net.InetAddress;

/* Server handler interface declaration */
public interface IServerHandler {
    /* *
     * METHOD: Will be called right after new client connected
     * RETURN: True if you accept connected client, false if reject
     *  PARAM: [IN] clientID      - client identifier (store it somewhere)
     *  PARAM: [IN] clientAddress - connected client information
     * AUTHOR: Eliseev Dmitry
     * */
    public abstract boolean onConnect(final String clientID, final InetAddress clientAddress);

    /* *
     * METHOD: Will be called right after server accept message from any connected client
     * RETURN: Response (see ServerResponse class), or null if you want to disconnect client
     *  PARAM: [IN] clientID - sender identifier
     *  PARAM: [IN] message  - received message
     * AUTHOR: Eliseev Dmitry
     * */
    public abstract ServerResponse onReceive(final String clientID, final Message message);

    /* *
     * METHOD: Will be called right after any client disconnected
     *  PARAM: [IN] clientID - disconnected client identifier
     * AUTHOR: Eliseev Dmitry
     * */
    public abstract void onDisconnect(final String clientID);
} /* End of 'IServerHandler' interface */

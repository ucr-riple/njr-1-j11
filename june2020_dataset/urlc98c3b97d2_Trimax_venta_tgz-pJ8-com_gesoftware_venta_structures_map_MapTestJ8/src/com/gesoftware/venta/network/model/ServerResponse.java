package com.gesoftware.venta.network.model;

/* Server response class definition */
public final class ServerResponse {
    public final static ServerResponse NOTHING = new ServerResponse(null);

    /* Here store server response message, that will be send to client(s) */
    private final Message m_Message;

    /* Here stored client (recipient) identifier */
    private String m_ClientID;

    /* It's a broadcast flag. If it's enabled, then response will be send to all connected clients */
    private boolean m_IsBroadcast;

    /* *
     * METHOD: Use this constructor if you want to reply to the sender
     *  PARAM: [IN] message - message to send
     * AUTHOR: Eliseev Dmitry
     * */
    public ServerResponse(final Message message) {
        m_Message = message;
    } /* End of 'ServerResponse::ServerResponse' method */

    /* *
     * METHOD: Use this constructor if you want to reply to all connected clients
     *  PARAM: [IN] message     - message to send
     *  PARAM: [IN] isBroadcast - broadcast flag
     * AUTHOR: Eliseev Dmitry
     * */
    public ServerResponse(final Message message, final boolean isBroadcast) {
        m_Message     = message;
        m_IsBroadcast = isBroadcast;
    } /* End of 'ServerResponse::ServerResponse' method */

    /* *
     * METHOD: Use this constructor if you want to reply specified client
     *  PARAM: [IN] message  - message to send
     *  PARAM: [IN] clientID - recipient ID
     * AUTHOR: Eliseev Dmitry
     * */
    public ServerResponse(final Message message, final String clientID) {
        m_Message     = message;
        m_ClientID    = clientID;
        m_IsBroadcast = false;
    } /* End of 'ServerResponse::ServerResponse' method */

    /* *
     * METHOD: Message getter
     * RETURN: Response message
     * AUTHOR: Eliseev Dmitry
     * */
    public final Message getMessage() {
        return m_Message;
    } /* End of 'ServerResponse::ServerResponse' method */

    /* *
     * METHOD: Client identifier getter
     * RETURN: Response client identifier
     * AUTHOR: Eliseev Dmitry
     * */
    public final String getClientID() {
        return m_ClientID;
    } /* End of 'ServerResponse::ServerResponse' method */

    /* *
     * METHOD: Broadcast flag getter
     * RETURN: Broadcast flag value
     * AUTHOR: Eliseev Dmitry
     * */
    public final boolean isBroadcast() {
        return m_IsBroadcast;
    } /* End of 'ServerResponse::ServerResponse' method */
} /* End of 'ServerResponse' class */

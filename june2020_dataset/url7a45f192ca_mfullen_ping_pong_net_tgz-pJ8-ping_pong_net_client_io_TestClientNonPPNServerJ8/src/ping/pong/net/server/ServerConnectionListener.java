package ping.pong.net.server;

import ping.pong.net.connection.Connection;

/**
 *
 * @author mfullen
 */
public interface ServerConnectionListener
{
    /**
     *
     * @param server
     * @param conn
     */
    void connectionAdded(Server server, Connection conn);

    /**
     *
     * @param server
     * @param conn
     */
    void connectionRemoved(Server server, Connection conn);
}

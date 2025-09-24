package ping.pong.net.connection;

/**
 * Disconnect Information for a connection
 * @author mfullen
 */
public interface DisconnectInfo
{
    /**
     * The reason why the disconnect happened
     * @return
     */
    String getReason();

    /**
     * The state of the disconnect
     * @return
     */
    DisconnectState getDisconnectState();
}

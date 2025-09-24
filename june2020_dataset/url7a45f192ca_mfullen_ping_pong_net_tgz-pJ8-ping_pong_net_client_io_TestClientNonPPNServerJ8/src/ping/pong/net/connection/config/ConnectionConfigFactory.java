package ping.pong.net.connection.config;

/**
 *
 * @author mfullen
 */
public final class ConnectionConfigFactory
{
    private ConnectionConfigFactory()
    {
    }

    /**
     * Create a configuration for a PPN connection. This method provides all possible
     * inputs
     * @param ip the ip to connect to
     * @param tcpPort the tcpport to connect to
     * @param udpPort the udp port to connect to
     * @param ssl use ssl for the tcp connection
     * @return
     */
    public static ConnectionConfiguration createPPNConfig(String ip, int tcpPort, int udpPort, boolean ssl)
    {
        return new DefaultConnectionConfiguration(tcpPort, udpPort, ip, ssl);
    }

    /**
     * Create a Server connection with a TCP and UDP port with the option of SSL
     * @param tcpPort
     * @param udpPort
     * @param ssl
     * @return
     */
    public static ConnectionConfiguration createPPNServerConfig(int tcpPort, int udpPort, boolean ssl)
    {
        return createPPNConfig(ConnectionConfiguration.LOCAL_HOST, tcpPort, udpPort, ssl);
    }

    /**
     * Create a PPN Server Connection with only TCP, UDP is disabled. The option
     * of SSL exists
     * @param tcpPort
     * @param ssl
     * @return
     */
    public static ConnectionConfiguration createPPNServerConfig(int tcpPort, boolean ssl)
    {
        return createPPNConfig(ConnectionConfiguration.LOCAL_HOST, tcpPort, ConnectionConfiguration.UDP_DISABLED, ssl);
    }

    /**
     * Create a PPN Server connection with only tcp. SSL and UDP are disabled
     * @param tcpPort
     * @return
     */
    public static ConnectionConfiguration createPPNServerConfig(int tcpPort)
    {
        return createPPNConfig(ConnectionConfiguration.LOCAL_HOST, tcpPort, ConnectionConfiguration.UDP_DISABLED, false);
    }

    /**
     * Create a PPN Client connection. This method provides all parameters
     * @param ip
     * @param tcpPort
     * @param udpPort
     * @param ssl
     * @return
     */
    public static ConnectionConfiguration createPPNClientConfig(String ip, int tcpPort, int udpPort, boolean ssl)
    {
        return createPPNConfig(ip, tcpPort, udpPort, ssl);
    }

    /**
     * Create a PPN Client connection with UDP disabled
     * @param ip
     * @param tcpPort
     * @param ssl
     * @return
     */
    public static ConnectionConfiguration createPPNClientConfig(String ip, int tcpPort, boolean ssl)
    {
        return createPPNConfig(ip, tcpPort, ConnectionConfiguration.UDP_DISABLED, ssl);
    }

    /**
     * Create a PPN Client connection with UDP and SSL disabled
     * @param ip
     * @param tcpPort
     * @return
     */
    public static ConnectionConfiguration createPPNClientConfig(String ip, int tcpPort)
    {
        return createPPNConfig(ip, tcpPort, ConnectionConfiguration.UDP_DISABLED, false);
    }

    /**
     * Creates a default connection with the following parameters
     * TCP 5011
     * UDP 5012
     * SSL false
     * IP - localhost
     * @return
     */
    public static ConnectionConfiguration createConnectionConfiguration()
    {
        return new DefaultConnectionConfiguration();
    }
}

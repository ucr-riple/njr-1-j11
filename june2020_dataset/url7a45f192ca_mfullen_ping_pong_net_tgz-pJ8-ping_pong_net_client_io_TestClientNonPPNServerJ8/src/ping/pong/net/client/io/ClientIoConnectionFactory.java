package ping.pong.net.client.io;

import java.net.DatagramSocket;
import java.net.Socket;
import ping.pong.net.connection.Connection;
import ping.pong.net.connection.config.ConnectionConfiguration;
import ping.pong.net.connection.io.DataReader;
import ping.pong.net.connection.io.DataWriter;

/**
 *
 * @author mfullen
 */
final class ClientIoConnectionFactory
{
    private ClientIoConnectionFactory()
    {
    }

    public static <Message> Connection<Message> createNonPPNConnection(ConnectionConfiguration configuration,
                                                                       DataReader dataReader, DataWriter dataWriter,
                                                                       Socket tcpSocket, DatagramSocket udpSocket)
    {
        return new ClientIoNonPPNConnection<Message>(configuration, dataReader, dataWriter, tcpSocket, udpSocket);
    }

    public static <Message> Connection<Message> createPPNConnection(ConnectionConfiguration configuration,
                                                                    Socket tcpSocket, DatagramSocket udpSocket)
    {
        return new ClientIoConnection<Message>(configuration, null, null, tcpSocket, udpSocket);
    }
}

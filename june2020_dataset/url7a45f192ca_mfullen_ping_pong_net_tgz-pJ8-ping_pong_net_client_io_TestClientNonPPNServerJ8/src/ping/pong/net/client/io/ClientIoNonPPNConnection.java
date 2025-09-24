package ping.pong.net.client.io;

import java.net.DatagramSocket;
import java.net.Socket;
import ping.pong.net.connection.config.ConnectionConfiguration;
import ping.pong.net.connection.io.DataReader;
import ping.pong.net.connection.io.DataWriter;
import ping.pong.net.connection.messaging.DisconnectMessage;

/**
 * Client Io Connection class. This class extends from AbstractIoConnection
 * @author mfullen
 */
final class ClientIoNonPPNConnection<MessageType> extends ClientIoConnection<MessageType>
{
    public ClientIoNonPPNConnection(ConnectionConfiguration config, DataReader dataReader, DataWriter dataWriter, Socket tcpSocket, DatagramSocket udpSocket)
    {
        super(config, dataReader, dataWriter, tcpSocket, udpSocket);
    }

    /**
     * ProcessMessage on the client first checks if the message is of type
     * ConnectionIdMessage.ResponseMessage. ConnectionIdMessage.ResponseMessage
     * indicates that the Connection has received an Identifier from the server and
     * can be considered connected
     * @param message the message being processed
     */
    @Override
    protected void processMessage(MessageType message)
    {
        if (message instanceof DisconnectMessage)
        {
            this.connected = false;
        }
        else
        {
            LOGGER.trace("({}) Message taken to be processed ({})", this.getConnectionId(), message);
            this.fireOnSocketMessageReceived(message);
        }
    }

    @Override
    protected void startConnection()
    {
        super.startConnection();
        this.fireOnSocketCreated();
    }
}

package ping.pong.net.connection.io;

import java.io.InputStream;
import java.net.Socket;

/**
 * Custom way to read Data from a TCP socket
 * @param <Type>
 * @author mfullen
 */
public interface DataReader<Type>
{
    /**
     * The data read from the socket
     * @return
     */
    Type readData();

    /**
     * The custom initialization of an Inputstream
     * @param socket the socket to base the stream from
     * @return
     */
    InputStream init(Socket socket);
}

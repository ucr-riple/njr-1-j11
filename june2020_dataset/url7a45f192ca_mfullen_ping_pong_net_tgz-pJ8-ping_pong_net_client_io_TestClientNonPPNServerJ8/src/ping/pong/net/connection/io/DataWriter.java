package ping.pong.net.connection.io;

import java.io.OutputStream;
import java.net.Socket;

/**
 * Datawriter for a TCP socket
 * @param <Type>
 * @author mfullen
 */
public interface DataWriter<Type>
{
    /**
     * The custom output stream to initialize
     * @param socket the socket to base the stream from
     * @return
     */
    OutputStream init(Socket socket);

    /**
     * Write data to the stream
     * @param data the data to write
     */
    void writeData(Type data);
}

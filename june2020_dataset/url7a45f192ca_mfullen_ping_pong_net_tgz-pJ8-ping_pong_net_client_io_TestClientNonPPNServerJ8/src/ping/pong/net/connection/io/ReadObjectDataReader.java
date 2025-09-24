package ping.pong.net.connection.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ping.pong.net.connection.ConnectionExceptionHandler;

/**
 *
 * @author mfullen
 */
public class ReadObjectDataReader implements
        DataReader<Object>
{
    private ObjectInputStream inputStream = null;
    private static final Logger LOGGER = LoggerFactory.getLogger(ReadObjectDataReader.class);

    /**
     *
     */
    public ReadObjectDataReader()
    {
    }

    @Override
    public InputStream init(Socket socket)
    {
        try
        {
            this.inputStream = new ObjectInputStream(socket.getInputStream());
        }
        catch (IOException ex)
        {
            LOGGER.error("Tcp Socket Init Error Error ", ex);
        }
        return this.inputStream;
    }

    @Override
    public synchronized Object readData()
    {
        Object readObject = null;
        LOGGER.trace("About to block for read Object");
        try
        {
            readObject = this.inputStream.readObject();
        }
        catch (IOException ex)
        {
            ConnectionExceptionHandler.handleException(ex, LOGGER);
        }
        catch (ClassNotFoundException ex)
        {
            ConnectionExceptionHandler.handleException(ex, LOGGER);
        }
        LOGGER.trace("{Read Object from Stream: {} ", readObject);

        return readObject;
    }
}

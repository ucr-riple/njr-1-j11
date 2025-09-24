package ping.pong.net.connection.io;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author mfullen
 */
public class WriteObjectDataWriter implements DataWriter<Object>
{
    private ObjectOutputStream outputStream = null;
    private static final Logger LOGGER = LoggerFactory.getLogger(WriteObjectDataWriter.class);

    @Override
    public OutputStream init(Socket socket)
    {
        try
        {
            this.outputStream = new ObjectOutputStream(socket.getOutputStream());
            this.outputStream.flush();
        }
        catch (IOException ex)
        {
            LOGGER.error("Tcp Socket Init Error Error ", ex);
        }
        return this.outputStream;
    }

    @Override
    public void writeData(Object data)
    {
        try
        {
            LOGGER.trace("About to write Object to Stream {}", data);
            this.outputStream.writeObject(data);
            this.outputStream.flush();
            LOGGER.trace("Wrote {} to stream", data);
            LOGGER.trace("Flushed Outputstream");
        }
        catch (IOException ex)
        {
            LOGGER.error("Error Writing Object", ex);
        }
    }
}

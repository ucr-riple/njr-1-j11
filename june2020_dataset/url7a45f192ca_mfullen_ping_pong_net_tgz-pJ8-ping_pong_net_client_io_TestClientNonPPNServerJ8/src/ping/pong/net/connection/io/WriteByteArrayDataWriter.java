package ping.pong.net.connection.io;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author mfullen
 */
public class WriteByteArrayDataWriter implements DataWriter<byte[]>
{
    private BufferedOutputStream outputStream = null;
    private static final Logger LOGGER = LoggerFactory.getLogger(WriteByteArrayDataWriter.class);

    @Override
    public OutputStream init(Socket socket)
    {
        try
        {
            this.outputStream = new BufferedOutputStream(socket.getOutputStream());
            this.outputStream.flush();
        }
        catch (IOException ex)
        {
            LOGGER.error("Tcp Socket Init Error Error ", ex);
        }
        return this.outputStream;
    }

    @Override
    public void writeData(byte[] data)
    {
        try
        {
            LOGGER.trace("About to write Object to Stream {}", data);
            byte[] size = ByteBuffer.allocate(4).putInt(data.length).array();
            this.outputStream.write(size);
            this.outputStream.write(data);
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

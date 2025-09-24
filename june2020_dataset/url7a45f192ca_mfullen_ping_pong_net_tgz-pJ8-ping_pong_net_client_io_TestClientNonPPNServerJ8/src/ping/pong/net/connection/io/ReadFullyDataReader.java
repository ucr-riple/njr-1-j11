package ping.pong.net.connection.io;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author mfullen
 */
public class ReadFullyDataReader implements DataReader<byte[]>
{
    private BufferedInputStream inputStream = null;
    private static final Logger LOGGER = LoggerFactory.getLogger(ReadFullyDataReader.class);
    private static final int INVALID_BYTE = -1;
    private static final int SIZE_LENGTH = 4;

    /**
     *
     */
    public ReadFullyDataReader()
    {
    }

    @Override
    public InputStream init(Socket socket)
    {
        try
        {
            this.inputStream = new BufferedInputStream(socket.getInputStream());
        }
        catch (IOException ex)
        {
            LOGGER.error("Tcp Socket Init Error Error ", ex);
        }
        return this.inputStream;
    }

    @Override
    public synchronized byte[] readData()
    {
        byte[] buffer = null;
        int size = 0;
        try
        {
            byte[] sizeBuffer = new byte[SIZE_LENGTH];
            int nextByte = inputStream.read(sizeBuffer);
            if (nextByte != INVALID_BYTE)
            {
                ByteBuffer byteBuffer = ByteBuffer.wrap(sizeBuffer);
                if (byteBuffer.order().equals(ByteOrder.LITTLE_ENDIAN))
                {
                    LOGGER.debug("SizeBuffer was Little endian");
                }
                size = byteBuffer.order(ByteOrder.BIG_ENDIAN).getInt();
                LOGGER.debug("Size: {}", size);
                buffer = new byte[size];
                this.inputStream.read(buffer);
            }
        }
        catch (IOException ex)
        {
            LOGGER.error("Error reading Rest of data", ex);
        }
        return buffer;
    }
}

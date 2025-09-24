package ping.pong.net.connection;

import java.io.*;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author mfullen
 */
public final class ConnectionUtils
{
    private ConnectionUtils()
    {
    }
    private static final int BUFFER_SIZE = 65535;
    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionUtils.class);

    /**
     *
     * @param input
     * @return
     */
    public static byte[] compressfinal(final byte[] input)
    {
        try
        {
            Deflater compressor = new Deflater(Deflater.BEST_COMPRESSION);
            compressor.setInput(input);
            compressor.finish();
            ByteArrayOutputStream bos = new ByteArrayOutputStream(input.length);
            final byte[] buffer = new byte[BUFFER_SIZE];
            while (!compressor.finished())
            {
                int count = compressor.deflate(buffer);
                bos.write(buffer, 0, count);
            }
            bos.close();
            byte[] compressedData = bos.toByteArray();
            return compressedData;
        }
        catch (IOException ex)
        {
            LOGGER.error("Error in compressing bytes", ex);
        }
        return null;
    }

    /**
     *
     * @param input
     * @return
     */
    public static byte[] decompress(final byte[] input)
    {
        Inflater decompressor = new Inflater();
        decompressor.setInput(input);

        ByteArrayOutputStream bos = new ByteArrayOutputStream(input.length);
        final byte[] buffer = new byte[BUFFER_SIZE];
        while (!decompressor.finished())
        {
            try
            {
                int count = decompressor.inflate(buffer);
                bos.write(buffer, 0, count);
            }
            catch (DataFormatException ex)
            {
                LOGGER.error("Error in decompressing bytes", ex);
            }
        }
        try
        {
            bos.close();
        }
        catch (IOException ex)
        {
            LOGGER.error("Error Closing the bytearray", ex);
        }
        byte[] compressedData = bos.toByteArray();
        return compressedData;
    }

    /**
     *
     * @param obj
     * @return
     * @throws java.io.IOException
     */
    public static byte[] getbytes(final Object obj) throws java.io.IOException
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(obj);
        oos.flush();
        oos.close();
        bos.close();
        byte[] data = bos.toByteArray();
        return data;
    }

    /**
     *
     * @param <M> The type of Message or Object to return
     * @param bytes the byte array of data
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static <M> M getObject(final byte[] bytes) throws IOException,
                                                             ClassNotFoundException
    {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        objectInputStream.close();
        byteArrayInputStream.close();
        return (M) objectInputStream.readObject();
    }
}

package ping.pong.net.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URISyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author mfullen
 */
public final class PPNUtils
{
    private static final Logger LOGGER = LoggerFactory.getLogger(PPNUtils.class);

    private PPNUtils()
    {
    }

    /**
     * Gets the Absolute Path to the filename from the Classpath
     *
     * @param filename the filename to find
     * @return A string representing the absolute path. The file must be on the
     * classpath or null is returned
     */
    public static String getAbsolutePath(String filename)
    {
        String path = null;
        try
        {
            path = new File(Thread.currentThread().getContextClassLoader().getResource(filename).toURI()).getAbsolutePath();
        }
        catch (URISyntaxException ex)
        {
            LOGGER.error("Syntax error when looking on the classpath", ex);
        }
        finally
        {
            return path;
        }
    }

    /**
     * Gets an InputStream from the filename (Must be on the classpath)
     *
     * @param filename the file name on the classpath
     * @return
     * @throws FileNotFoundException
     */
    public static InputStream getInputStream(String filename) throws
            FileNotFoundException
    {
        return new FileInputStream(getAbsolutePath(filename));
    }

    /**
     * Gets an Inputstream from the filepath given
     *
     * @param path
     * @return
     * @throws FileNotFoundException
     */
    public static InputStream getInputStreamFromPath(String path) throws
            FileNotFoundException
    {
        return new FileInputStream(path);
    }
}

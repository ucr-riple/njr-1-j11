package ping.pong.net.connection;

import java.io.EOFException;
import java.net.*;
import javax.net.ssl.SSLException;
import org.slf4j.Logger;

/**
 *
 * @author mfullen
 */
public final class ConnectionExceptionHandler
{
    private ConnectionExceptionHandler()
    {
    }

    /**
     *
     * @param ex
     * @param logger
     * @return
     */
    public static DisconnectState handleException(final Exception ex, Logger logger)
    {
        DisconnectState disconnectState = DisconnectState.ERROR;
        //    BindException, ConnectException, NoRouteToHostException, PortUnreachableException
        if (ex instanceof SocketException)
        {
            if ((ex instanceof BindException) || ex.getMessage().contains("JVM_Bind"))
            {
                logger.error("Socket already in use. Try starting the server on another socket.", ex);
            }
            else if (ex instanceof ConnectException)
            {
                logger.error("ConnectException.", ex);
            }
            else if (ex instanceof NoRouteToHostException)
            {
                logger.error("NoRouteToHostException.", ex);
            }
            else if (ex instanceof PortUnreachableException)
            {
                logger.error("PortUnreachableException.", ex);
            }
            else if (ex.getMessage().contains("Connection reset"))
            {
                logger.debug("Connection reset: Closed Connection foricibly.");
            }
            else if (ex.getMessage().contains("socket closed"))
            {
                disconnectState = DisconnectState.NORMAL;
                logger.trace("The Socket was closed.");
            }
            else
            {
                logger.error("Unknown SocketException Error", ex);
            }
        }
        else if (ex instanceof SSLException)
        {
            logger.error("SSL Error: ", ex);
        }
        else if (ex instanceof EOFException)
        {
            disconnectState = DisconnectState.NORMAL;
            logger.error("End of Stream the Socket was closed.", ex);
        }
        else
        {
            logger.error("Unknown Error", ex);
        }
        return disconnectState;
    }
}

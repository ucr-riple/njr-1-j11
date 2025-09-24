/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ping.pong.net.connection;

import java.io.EOFException;
import java.net.*;
import javax.net.ssl.SSLException;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.Marker;

/**
 *
 * @author Adrian
 */
public class ConnectionExceptionHandlerTest
{

    /**
     * Test of handleException method, of class ConnectionExceptionHandler.
     */
    @Test
    public void testHandleException_SocketException_BindException()
    {
        Exception ex = new BindException();
        MyLogger logger = new MyLogger();
        ConnectionExceptionHandler.handleException(ex, logger);
        assertEquals(1, logger.getErrorStringThrowableCounter());
        assertEquals("Socket already in use. Try starting the server on another socket.", logger.getErrorStringThrowableString());
    }

    @Test
    public void testHandleException_SocketException_JVM_Bind()
    {
        Exception ex = new BindException("JVM_Bind");
        MyLogger logger = new MyLogger();
        ConnectionExceptionHandler.handleException(ex, logger);
        assertEquals(1, logger.getErrorStringThrowableCounter());
        assertEquals("Socket already in use. Try starting the server on another socket.", logger.getErrorStringThrowableString());
    }

    @Test
    public void testHandleException_SocketException_ConnectException()
    {
        Exception ex = new ConnectException("msg");
        MyLogger logger = new MyLogger();
        ConnectionExceptionHandler.handleException(ex, logger);
        assertEquals(1, logger.getErrorStringThrowableCounter());
        assertEquals("ConnectException.", logger.getErrorStringThrowableString());
    }

    @Test
    public void testHandleException_SocketException_NoRouteToHostException()
    {
        Exception ex = new NoRouteToHostException("msg");
        MyLogger logger = new MyLogger();
        ConnectionExceptionHandler.handleException(ex, logger);
        assertEquals(1, logger.getErrorStringThrowableCounter());
        assertEquals("NoRouteToHostException.", logger.getErrorStringThrowableString());
    }

    @Test
    public void testHandleException_SocketException_PortUnreachableException()
    {
        Exception ex = new PortUnreachableException("msg");
        MyLogger logger = new MyLogger();
        ConnectionExceptionHandler.handleException(ex, logger);
        assertEquals(1, logger.getErrorStringThrowableCounter());
        assertEquals("PortUnreachableException.", logger.getErrorStringThrowableString());
    }

    @Test
    public void testHandleException_SocketException_Connection_reset()
    {
        Exception ex = new SocketException("Connection reset");
        MyLogger logger = new MyLogger();
        ConnectionExceptionHandler.handleException(ex, logger);
        assertEquals(1, logger.getDebugStringCounter());
        assertEquals("Connection reset: Closed Connection foricibly.", logger.getDebugStringString());
    }

    @Test
    public void testHandleException_SocketException_socket_closed()
    {
        Exception ex = new SocketException("socket closed");
        MyLogger logger = new MyLogger();
        ConnectionExceptionHandler.handleException(ex, logger);
        assertEquals(1, logger.getTraceStringCounter());
        assertEquals("The Socket was closed.", logger.getTraceStringString());
    }

    @Test
    public void testHandleException_SocketException_Unknkown()
    {
        Exception ex = new SocketException("other");
        MyLogger logger = new MyLogger();
        ConnectionExceptionHandler.handleException(ex, logger);
        assertEquals(1, logger.getErrorStringThrowableCounter());
        assertEquals("Unknown SocketException Error", logger.getErrorStringThrowableString());
    }

    @Test
    public void testHandleException_SSLException()
    {
        Exception ex = new SSLException("test msg");
        MyLogger logger = new MyLogger();
        ConnectionExceptionHandler.handleException(ex, logger);
        assertEquals(1, logger.getErrorStringThrowableCounter());
        assertEquals("SSL Error: ", logger.getErrorStringThrowableString());
    }

    @Test
    public void testHandleException_EOFException()
    {
        Exception ex = new EOFException("test msg");
        MyLogger logger = new MyLogger();
        ConnectionExceptionHandler.handleException(ex, logger);
        assertEquals(1, logger.getErrorStringThrowableCounter());
        assertEquals("End of Stream the Socket was closed.", logger.getErrorStringThrowableString());
    }

    @Test
    public void testHandleException_Other()
    {
        Exception ex = new Exception("test msg");
        MyLogger logger = new MyLogger();
        ConnectionExceptionHandler.handleException(ex, logger);
        assertEquals(1, logger.getErrorStringThrowableCounter());
        assertEquals("Unknown Error", logger.getErrorStringThrowableString());
    }

    public static class MyLogger implements Logger
    {
        private int errorStringThrowableCounter;
        private String errorStringThrowableString;
        private int debugStringCounter;
        private String debugStringString;
        private int traceStringCounter;
        private String traceStringString;

        public int getErrorStringThrowableCounter()
        {
            return errorStringThrowableCounter;
        }

        public String getErrorStringThrowableString()
        {
            return errorStringThrowableString;
        }

        public int getDebugStringCounter()
        {
            return debugStringCounter;
        }

        public String getDebugStringString()
        {
            return debugStringString;
        }

        public int getTraceStringCounter()
        {
            return traceStringCounter;
        }

        public String getTraceStringString()
        {
            return traceStringString;
        }

        @Override
        public String getName()
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean isTraceEnabled()
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void trace(String string)
        {
            this.traceStringCounter++;
            this.traceStringString = string;
        }

        @Override
        public void trace(String string, Object o)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void trace(String string, Object o, Object o1)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void trace(String string, Object[] os)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void trace(String string, Throwable thrwbl)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean isTraceEnabled(Marker marker)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void trace(Marker marker, String string)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void trace(Marker marker, String string, Object o)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void trace(Marker marker, String string, Object o, Object o1)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void trace(Marker marker, String string, Object[] os)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void trace(Marker marker, String string, Throwable thrwbl)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean isDebugEnabled()
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void debug(String string)
        {
            this.debugStringCounter++;
            this.debugStringString = string;
        }

        @Override
        public void debug(String string, Object o)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void debug(String string, Object o, Object o1)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void debug(String string, Object[] os)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void debug(String string, Throwable thrwbl)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean isDebugEnabled(Marker marker)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void debug(Marker marker, String string)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void debug(Marker marker, String string, Object o)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void debug(Marker marker, String string, Object o, Object o1)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void debug(Marker marker, String string, Object[] os)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void debug(Marker marker, String string, Throwable thrwbl)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean isInfoEnabled()
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void info(String string)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void info(String string, Object o)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void info(String string, Object o, Object o1)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void info(String string, Object[] os)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void info(String string, Throwable thrwbl)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean isInfoEnabled(Marker marker)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void info(Marker marker, String string)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void info(Marker marker, String string, Object o)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void info(Marker marker, String string, Object o, Object o1)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void info(Marker marker, String string, Object[] os)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void info(Marker marker, String string, Throwable thrwbl)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean isWarnEnabled()
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void warn(String string)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void warn(String string, Object o)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void warn(String string, Object[] os)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void warn(String string, Object o, Object o1)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void warn(String string, Throwable thrwbl)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean isWarnEnabled(Marker marker)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void warn(Marker marker, String string)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void warn(Marker marker, String string, Object o)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void warn(Marker marker, String string, Object o, Object o1)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void warn(Marker marker, String string, Object[] os)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void warn(Marker marker, String string, Throwable thrwbl)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean isErrorEnabled()
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void error(String string)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void error(String string, Object o)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void error(String string, Object o, Object o1)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void error(String string, Object[] os)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void error(String string, Throwable thrwbl)
        {
            this.errorStringThrowableCounter++;
            this.errorStringThrowableString = string;
        }

        @Override
        public boolean isErrorEnabled(Marker marker)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void error(Marker marker, String string)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void error(Marker marker, String string, Object o)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void error(Marker marker, String string, Object o, Object o1)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void error(Marker marker, String string, Object[] os)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void error(Marker marker, String string, Throwable thrwbl)
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

    }
}

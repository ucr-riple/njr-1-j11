package com.gesoftware.venta.network;

import com.gesoftware.venta.logging.LoggingUtility;
import com.gesoftware.venta.network.handlers.IClientHandler;
import com.gesoftware.venta.network.model.Message;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/* Connection class definition */
public final class Connection implements IConnection {
    /* Server connection timeout (ms) */
    private final static int c_NetworkTimeout = 2000;

    /* Client handler (server responses handler) */
    private final IClientHandler m_ClientHandler;

    /* Connected flag */
    private boolean m_IsConnected;

    /* Server address */
    private final String m_Host;

    /* Server port */
    private final int m_Port;

    /* Time difference between server & client */
    private long m_TimeDifference;

    /* I/O streams */
    private ObjectInputStream m_Reader;
    private ObjectOutputStream m_Writer;

    /* Synchonizer for */
    private final Object m_Sync = new Object();

    /* Receiver class definition */
    private final class Receiver implements Runnable {

        /* *
         * METHOD: Calculates difference in client - server times
         *  PARAM: [IN] message - accepted message
         * AUTHOR: Eliseev Dmitry
         * */
        private void calculateTimeDifference(final Message message) {
            if (message != null)
                m_TimeDifference = System.currentTimeMillis() - message.getTimestamp();
        } /* End of 'Receiver::calculateTimeDifference' method */

        @Override
        public final void run() {
            /* While sender works */
            while (m_IsConnected) {
                try {
                    final Message message = (Message) m_Reader.readObject();
                    calculateTimeDifference(message);

                    if (m_ClientHandler != null)
                        m_ClientHandler.onReceive(message);
                } catch (final Exception e) {
                    LoggingUtility.error("Connection lost: " + e.getMessage());

                    /* Execute lost connection callback */
                    if (m_ClientHandler != null)
                        m_ClientHandler.onConnectionLost(e.getMessage());

                    /* Stop listening socket when connection lost */
                    m_IsConnected = false;
                }
            }

            disconnect();
        } /* End of 'Receiver::run' method */
    } /* End of 'Receiver' class */

    /* *
     * METHOD: Connection class constructor
     *  PARAM: [IN] host          - server host
     *  PARAM: [IN] port          - server port
     *  PARAM: [IN] clientHandler - server responses handler
     * AUTHOR: Eliseev Dmitry
     * */
    public Connection(final String host, final int port, final IClientHandler clientHandler) {
        this.m_ClientHandler = clientHandler;
        this.m_Host          = host;
        this.m_Port          = port;
    } /* End of 'Connection::Connection' method */

    @Override
    public final boolean connect() {
        synchronized (m_Sync) {
            if (m_IsConnected)
                return true;

            disconnect();

            try {
                final Socket socket = new Socket();
                socket.connect(new InetSocketAddress(m_Host, m_Port), c_NetworkTimeout);

                m_Reader = new ObjectInputStream(socket.getInputStream());
                m_Writer = new ObjectOutputStream(socket.getOutputStream());

                m_IsConnected = true;

                final Thread receiverThread = new Thread(new Receiver());
                receiverThread.start();

                return true;
            } catch(final UnknownHostException e) {
                LoggingUtility.exception(e);
            } catch (final IOException e) {
                LoggingUtility.exception(e);
            } catch (final Exception e) {
                LoggingUtility.exception(e);
            }

            return false;
        }
    } /* End of 'Connection::connect' method */

    @Override
    public final boolean send(final Message message) {
        synchronized (m_Sync) {
            /* If socket is opened */
            if (m_Writer != null) {
                try {
                    /* send message to socket */
                    if (message.getSize() == 0)
                        return false;

                    m_Writer.writeObject(message);
                    m_Writer.flush();

                    /* Success */
                    return true;
                } catch (final IOException e) {
                    LoggingUtility.error("Error flushing message: " + e.getMessage());
                }
            }

        /* Failed */
            return false;
        }
    } /* End of 'Connection::send' method */

    @Override
    public boolean isConnected() {
        return m_IsConnected;
    } /* End of 'Connection::isConnected' method */

    @Override
    public void disconnect() {
        synchronized (m_Sync) {
            m_IsConnected = false;

            try {
                if (m_Reader != null)
                    m_Reader.close();
            } catch (final Exception ignored) {
            }

            try {
                if (m_Writer != null)
                    m_Writer.close();
            } catch (final Exception ignored) {
            }

            m_Reader = null;
            m_Writer = null;
        }
    } /* End of 'Connection::disconnect' method */

    /* *
     * METHOD: Determines approximate server time
     * RETURN: Approximate server time
     * AUTHOR: Eliseev Dmitry
     * */
    public final long getServerTime() {
        return System.currentTimeMillis() - m_TimeDifference;
    } /* End of 'Connection::getServerTime' method */
} /* End of 'Connection' class */

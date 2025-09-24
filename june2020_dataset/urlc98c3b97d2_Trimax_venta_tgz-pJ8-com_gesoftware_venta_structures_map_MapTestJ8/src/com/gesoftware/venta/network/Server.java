package com.gesoftware.venta.network;

import com.gesoftware.venta.logging.LoggingUtility;
import com.gesoftware.venta.network.handlers.IServerHandler;
import com.gesoftware.venta.network.model.Message;
import com.gesoftware.venta.network.model.ServerResponse;
import com.gesoftware.venta.structures.map.SynchronizedMap;
import com.gesoftware.venta.time.Timer;
import com.gesoftware.venta.utility.HashUtility;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/* Server class definition */
public final class Server implements IServer {
    /* Client timeout - 1 hour (ms) */
    private final static int c_NetworkClientTimeout = 3600 * 1000;

    /* Sleep duration between observe tasks (ms) */
    private final static int c_NetworkSleep = 10000;

    /* Connected clients data structure */
    private final Map<String, Client> m_Clients = new SynchronizedMap<String, Client>();

    /* Server handler (business-logic). Each listener, used separate port, should have it's own instance of handler */
    private IServerHandler m_ServerHandler = null;

    /* Server socket (binds to separate port) */
    private ServerSocket m_ServerSocket = null;

    /* Server activity flag. While flag is enabled, server accepts clients connections and works */
    private boolean m_IsActive = true;

    /* Server port (for listening) */
    private final int m_Port;

    /* Client class definition */
    private final class Client {
        /* Client processing activity flag. While flag is enabled, client sends and receives data) */
        private boolean m_IsClientActive = true;

        /* Last client query */
        private final Timer m_LastQuery;

        /* Unique client identifier */
        private final String m_ID;

        /* Client socket. Single client socket (transferring socket) */
        private final Socket m_Socket;

        /* Client thread for messages receiving */
        private Thread m_ThreadReceiver;

        /* Client thread for messages sending */
        private Thread m_ThreadSender;

        /* Messages to send (each client has it's own sending queue). Server adds message to send to this queue */
        private final BlockingQueue<Message> m_Messages = new LinkedBlockingQueue<Message>();

        /* Sender class instance */
        private ClientSender m_Sender;

        /* Receiver class instance */
        private ClientReceiver m_Receiver;

        /* Synchronization object (we need it for wait-notify sending queue architecture) */
        private final Object m_SenderMonitor = new Object();

        /* Client messages receiver class definition */
        private class ClientReceiver implements Runnable {
            /* Client input stream  */
            private ObjectInputStream m_Reader = null;

            /* *
             * METHOD: Client receiver class constructor
             * AUTHOR: Eliseev Dmitry
             * */
            public ClientReceiver() {
                try {
                    m_Reader = new ObjectInputStream(m_Socket.getInputStream());
                } catch (final IOException e) {
                    LoggingUtility.error("Can't open input stream: " + e.getMessage());
                }
            } /* End of 'ClientReceiver::ClientReceiver' method */

            private Message readMessage() {
                try {
                    return (Message) m_Reader.readObject();
                } catch(final Exception ignored) {}

                return null;
            }

            /* *
             * METHOD: Main server loop
             * AUTHOR: Eliseev Dmitry
             * */
            private void loop() {
                /* While client is active */
                while (m_IsClientActive) {
                    try {
                        final Message message = readMessage();
                        if (message == null) {
                            m_IsClientActive = false;
                            continue;
                        }

                        /* Update last query time */
                        m_LastQuery.measure();

                        /* Process response using handler */
                        final ServerResponse response = m_ServerHandler.onReceive(m_ID, message);

                        /* If there is a response, then send it, disconnect client otherwise */
                        if (response != null) {
                            if (response.getMessage() != null)
                                if (!processResponse(m_ID, response))
                                    LoggingUtility.debug("Can't send response from client: " + m_ID);
                        }
                        else
                            m_IsClientActive = false;
                    } catch (final Exception e) {
                        LoggingUtility.debug("Exception: " + e.getMessage());
                        LoggingUtility.core("Client closed connection: " + m_ID);
                        m_IsClientActive = false;
                    }
                }
            } /* End of 'Server::loop' method */

            @Override
            public final void run() {
                LoggingUtility.core("Receiver thread started for client: " + m_ID);

                try {
                    loop();
                } catch (final Exception e) {
                    LoggingUtility.debug("Error during client loop: " + e.getMessage());
                }

                /* Closing input stream */
                try {
                    m_Reader.close();
                } catch (final Exception e) {
                    LoggingUtility.error("Can't close input stream: " + e.getMessage());
                }

                /* Disconnect client from server */
                disconnectClient();

                /* Execute onDisconnect method of handler */
                m_ServerHandler.onDisconnect(m_ID);
                LoggingUtility.core("Receiver thread closed for client: " + m_ID);
            } /* End of 'ClientReceiver::run' method */

            /* *
             * METHOD: Stops receiver immediately
             * AUTHOR: Eliseev Dmitry
             * */
            public final void stop() {
                try {
                    m_Reader.close();
                } catch (final Exception e) {
                    LoggingUtility.error("Can't close input stream: " + e.getMessage());
                }
            } /* End of 'ClientReceiver::stop' method */
        } /* End of 'ClientReceiver' class */

        /* Client messages sender class definition */
        private final class ClientSender implements Runnable {
            /* Client output stream */
            private ObjectOutputStream m_Writer = null;

            /* *
             * METHOD: Client sender class constructor
             * AUTHOR: Eliseev Dmitry
             * */
            public ClientSender() {
                try {
                    m_Writer = new ObjectOutputStream(m_Socket.getOutputStream());
                } catch(final Exception e) {
                    LoggingUtility.error("Can't open output stream: " + e.getMessage());
                }
            } /* End of 'ClientSender::ClientSender' method */

            /* *
             * METHOD: Blocks running and wait for notification from server
             * AUTHOR: Eliseev Dmitry
             * */
            private void waitForMessages() {
                synchronized (m_SenderMonitor) {
                    try {
                        m_SenderMonitor.wait();
                    } catch (final InterruptedException e) {
                        LoggingUtility.debug("Exception: " + e.getMessage());
                    }
                }
            } /* End of 'ClientSender::waitForMessages' method */

            @Override
            public final void run() {
                LoggingUtility.core("Sender thread started for client: " + m_ID);

                /* While client is active */
                while (m_IsClientActive) {
                    try {
                        /* Waiting for messages at queue */
                        waitForMessages();

                        /* Sending all of them */
                        while (!m_Messages.isEmpty()) {
                            LoggingUtility.debug("Sending (" + m_Messages.size() + ") messages for client: " + m_ID);

                            m_Writer.writeObject(m_Messages.poll());
                            m_Writer.flush();
                        }
                    } catch(final Exception e) {
                        LoggingUtility.error("Client closed connection: " + m_ID);
                        m_IsClientActive = false;
                    }
                }

                /* Closing output stream */
                try {
                    m_Writer.close();
                } catch (final IOException ignored) {}

                /* Disconnect client from server */
                disconnectClient();

                /* That's it */
                LoggingUtility.core("Sender thread closed for client: " + m_ID);
            } /* End of 'ClientSender::run' method */
        } /* End of 'ClientSender' class */

        /* *
         * METHOD: Disconnect client from server
         * AUTHOR: Eliseev Dmitry
         * */
        private void disconnectClient() {
            /* Remove client from internal data structure */
            removeClient(m_ID);

            /* Stop client thread */
            m_IsClientActive = false;

            /* Notify sender thread */
            synchronized (m_SenderMonitor) {
                m_SenderMonitor.notify();
            }

            /* Closing socket */
            if (m_Socket != null)
                synchronized (m_Socket) {
                    try {
                        m_Socket.close();
                    } catch (final IOException ignored) {}
                }
        } /* End of 'Client::disconnectClient' method */

        /* *
         * METHOD: Client class constructor
         *  PARAM: [IN] socket - connected client socket
         * AUTHOR: Eliseev Dmitry
         * */
        public Client(final Socket socket) {
            this.m_ID        = HashUtility.generateHash(socket.toString());
            this.m_Socket    = socket;
            this.m_LastQuery = new Timer();
        } /* End of 'Client::Client' method */

        /* *
         * METHOD: Starts client processing thread
         * AUTHOR: Eliseev Dmitry
         * */
        public final void start() {
            /* Creation and starting sender thread */
            m_Sender       = new ClientSender();
            m_ThreadSender = new Thread(m_Sender);
            m_ThreadSender.start();

            /* Creation and starting receiver thread */
            m_Receiver       = new ClientReceiver();
            m_ThreadReceiver = new Thread(m_Receiver);
            m_ThreadReceiver.start();
        } /* End of 'Client::start' method */

        /* *
         * METHOD: Disconnects client immediately
         * AUTHOR: Eliseev Dmitry
         * */
        public final void disconnect() {
            m_Receiver.stop();
        } /* End of 'Client::disconnect' method */

        /* *
         * METHOD: Sends a message to client
         * RETURN: True if success, false otherwise
         *  PARAM: [IN] message - reference to message to send
         * AUTHOR: Eliseev Dmitry
         * */
        public final synchronized boolean send(final Message message) {
            try {
                m_Messages.put(message);

                /* Inform sender thread about new messages */
                synchronized (m_SenderMonitor) {
                    m_SenderMonitor.notify();
                }

                /* That's it (send successfully) */
                return true;
            } catch (final InterruptedException e) {
                LoggingUtility.error("Exception: " + e.getMessage());
            }

            /* That's it (send failed) */
            return false;
        } /* End of 'Client::send' method */

        /* *
         * METHOD: Gets client identifier
         * RETURN: Client's identifier
         * AUTHOR: Eliseev Dmitry
         * */
        public final String getID() {
            return m_ID;
        } /* End of 'Client::getID' method */

        @Override
        public final boolean equals(final Object o) {
            return o instanceof Client && ((Client) o).getID().equals(m_ID);
        } /* End of 'Client::equals' method */

        @Override
        public final int hashCode() {
            return m_ID.hashCode();
        } /* End of 'Client::hashCode' method */

        /* *
         * METHOD: Determines if client timed out
         * RETURN: True if client is timed out, false otherwise
         * AUTHOR: Eliseev Dmitry
         * */
        public final boolean isTimeout() {
            return m_LastQuery.getTimeSinceLastMeasure() >= c_NetworkClientTimeout;
        } /* End of 'Client::isTimeout' method */
    } /* End of 'Client' class */

    /* Observer class definition */
    private final class Observer implements Runnable {
        /* *
         * METHOD: Pauses worker thread
         * AUTHOR: Eliseev Dmitry
         * */
        private void sleep() {
            try {
                Thread.sleep(c_NetworkSleep);
            } catch (final InterruptedException e) {
                LoggingUtility.error("Worker thread can't sleep: " + e.getMessage());
            }
        } /* End of 'Observer::sleep' method */

        /* *
         * METHOD: Checks if client's time is out, then disconnect it
         *  PARAM: [IN] client - client to check & disconnect
         * AUTHOR: Eliseev Dmitry
         * */
        private void processClient(final Client client) {
            if (client.isTimeout()) {
                LoggingUtility.core("Time is out for <" + client.getID() + "> client");
                client.disconnect();
            }
        } /* End of 'Observer::processClient' method */

        /* *
         * METHOD: Processes all connected clients and check them for time outs
         *  PARAM: [IN] clients - all connected clients
         * AUTHOR: Eliseev Dmitry
         * */
        private void processClients(final Collection<Client> clients) {
            for (final Client client : clients)
                processClient(client);
        } /* End of 'Observer::processClients' method */

        @Override
        public final void run() {
            while (m_IsActive) {
                processClients(m_Clients.values());
                sleep();
            }
        } /* End of 'Observer::run' method */
    } /* End of 'Observer' class' */

    /* *
     * METHOD: Server class constructor
     *  PARAM: [IN] port    - port for listening
     *  PARAM: [IN] handler - reference to clients handler
     * AUTHOR: Eliseev Dmitry
     * */
    public Server(final int port, final IServerHandler handler) {
        m_ServerHandler = handler;
        m_Port = port;
    } /* End of 'Server::Server' method */

    /* *
     * METHOD: Sends a message to client
     * RETURN: True if success, false otherwise
     *  PARAM: [IN] clientID - client identifier
     *  PARAM: [IN] message  - message to send
     * AUTHOR: Eliseev Dmitry
     * */
    public final boolean send(final String clientID, final Message message) {
        LoggingUtility.debug("Sending " + message.getSize() + " bytes to client...");

        final Client client = m_Clients.get(clientID);
        return client != null && client.send(message);
    } /* End of 'Server::send' method */

    /* *
     * METHOD: Disconnects client by it's hash
     *  PARAM: [IN] clientID - client identifier
     * AUTHOR: Eliseev Dmitry
     * */
    public final void disconnect(final String clientID) {
        final Client client = m_Clients.get(clientID);
        if (client != null)
            client.disconnect();
    } /* End of 'Server::disconnect' method */

    /* *
     * METHOD: Processes server handler response
     *  PARAM: [IN] clientID - client identifier
     *  PARAM: [IN] response - handler response
     * AUTHOR: Eliseev Dmitry
     * */
    private boolean processResponse(final String clientID, final ServerResponse response) {
        /* Is it a self-response */
        if ((response.getClientID() == null)&&(!response.isBroadcast()))
            return send(clientID, response.getMessage());

        /* Is it a message for someone */
        if (response.getClientID() != null)
            return send(response.getClientID(), response.getMessage());

        /* Is it a broadcast message */
        boolean isSent = true;
        for (final String recipientID : m_Clients.keySet())
            isSent &= send(recipientID, response.getMessage());

        /* That's it */
        return isSent;
    } /* End of 'Server::processResponse' method */

    /* *
     * METHOD: Opens server socket using selected port
     * RETURN: True if success, false otherwise
     * AUTHOR: Eliseev Dmitry
     * */
    private boolean openServerSocket() {
        try {
            m_ServerSocket = new ServerSocket(m_Port);
            LoggingUtility.core("Server started using port: " + m_Port);
            return true;
        } catch (final IOException e) {
            LoggingUtility.error("Can't listen using port: " + m_Port);
        } catch (final Exception e) {
            LoggingUtility.error("Can't listen using port: " + m_Port);
        }

        return false;
    } /* End of 'Server::openServerSocket' method */

    /* *
     * METHOD: Adds client to internal data structure
     *  PARAM: [IN] client - reference to client to add
     * AUTHOR: Eliseev Dmitry
     * */
    private void addClient(final Client client) {
        m_Clients.put(client.getID(), client);
    } /* End of 'Server::addClient' method */

    /* *
     * METHOD: Removes client from internal data structure by it's identifier
     *  PARAM: [IN] clientID - reference to client ID to remove
     * AUTHOR: Eliseev Dmitry
     * */
    private void removeClient(final String clientID) {
        m_Clients.remove(clientID);
    } /* End of 'Server::removeClient' method */

    /* *
     * METHOD: Accepts incoming client connection
     * RETURN: Client socket if success, null otherwise
     * AUTHOR: Eliseev Dmitry
     * */
    private Socket acceptConnection() {
        try {
            return m_ServerSocket.accept();
        } catch (IOException e) {
            LoggingUtility.error("Can't accept client connection");
            LoggingUtility.debug("Exception " + e.getMessage());
        }

        return null;
    } /* End of 'Server::acceptConnection' method */

    /* *
     * METHOD: Closes client socket
     *  PARAM: [IN] socket - reference to client socket to close
     * AUTHOR: Eliseev Dmitry
     * */
    private void closeConnection(final Socket socket) {
        try {
            socket.close();
        } catch (final IOException ignored) {}
    } /* End of 'Server::closeConnection' method */

    @Override
    public final void run() {
        /* Trying to open client server socket and start listening */
        if (!openServerSocket())
            return;

        /* Starting observer thread */
        new Thread(new Observer()).start();

        /* While server is in active state */
        while (m_IsActive) {
            /* Try to accept client connection */
            final Socket clientSocket = acceptConnection();

            /* If connection was established successfully */
            if (clientSocket != null) {
                /* Create client instance */
                final Client client = new Client(clientSocket);
                LoggingUtility.core("Client connected from: " + clientSocket.getInetAddress().getHostAddress());

                /* Execute server handler and if client was accepted by handler  */
                if (m_ServerHandler.onConnect(client.getID(), clientSocket.getInetAddress())) {
                    LoggingUtility.core("Client approved with ID: " + client.getID());

                    /* Starting client processing thread */
                    client.start();

                    /* Store client into internal data structure */
                    addClient(client);
                } else
                    closeConnection(clientSocket);
            }
        }
    } /* End of 'Server::run' method */

    @Override
    public final void stop() {
        /* Change server activity state */
        m_IsActive = false;

        try {
            m_ServerSocket.close();
        } catch (final IOException e) {
            LoggingUtility.error("Can't stop server properly");
            LoggingUtility.debug("Exception: " + e.getMessage());
        }
    } /* End of 'Server::stop' method */
} /* End of 'Server' class */

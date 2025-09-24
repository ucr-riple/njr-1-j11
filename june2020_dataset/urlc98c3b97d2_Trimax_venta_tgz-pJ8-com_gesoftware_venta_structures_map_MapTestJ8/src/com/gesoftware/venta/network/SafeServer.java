package com.gesoftware.venta.network;

import com.gesoftware.venta.network.handlers.IServerHandler;
import com.gesoftware.venta.network.model.ServerResponse;
import com.gesoftware.venta.network.model.EncryptionKey;
import com.gesoftware.venta.network.model.SafeMessage;
import com.gesoftware.venta.logging.LoggingUtility;
import com.gesoftware.venta.security.model.KeyPair;
import com.gesoftware.venta.network.model.Message;
import com.gesoftware.venta.security.RSA;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class SafeServer implements IServer {
    /* Real server */
    private final Server m_Server;

    /* Server's decryption key */
    private final KeyPair m_ServerKey;
    private final int m_BlockSize;

    /* Client's encryption key */
    private EncryptionKey m_ClientKey;

    /* User-defined handler */
    private final IServerHandler m_ServerHandler;

    /* *
     * METHOD: Concatenates list of byte arrays
     * RETURN: Concatenated buffer
     *  PARAM: [IN] bytesArrays - list of bytes arrays to concatenate
     * AUTHOR: Tverskikh Sergey
     * */
    private byte[] concatenateByteArrays(final List<byte[]> bytesArrays) {
        /* Calculate length of result array: sum of argument arrays lengths */
        int length = 0;
        for (byte[] arr : bytesArrays)
            length += arr.length;

        /* Initialize result array with calculated length */
        final byte[] result = new byte[length];

        /* Copy arrays into result array in the same sequence as they are given in the list */
        int currentOffset = 0;
        for (byte[] arr : bytesArrays) {
            System.arraycopy(arr, 0, result, currentOffset, arr.length);
            currentOffset += arr.length;
        }

        return result;
    } /* End of 'SafeServer::concatenateByteArrays' method */

    /* *
     * METHOD: Splits byte array using encryption block size
     * RETURN: List of blocks
     *  PARAM: [IN] byteArray - byte array to split
     * AUTHOR: Eliseev Dmitry
     * */
    private List<byte[]> splitByteArray(final byte[] byteArray) {
        final List<byte[]> byteArrays = new ArrayList<byte[]>(byteArray.length / m_ClientKey.getBlockSize() + 1);

        int position = 0;
        while (position < byteArray.length) {
            byteArrays.add(Arrays.copyOfRange(byteArray, position, java.lang.Math.min(position + m_ClientKey.getBlockSize(), byteArray.length)));
            position += m_ClientKey.getBlockSize();
        }

        return byteArrays;
    } /* End of 'SafeServer::concatenateByteArrays' method */

    /* *
     * Server handler class definition
     * */
    private final class ServerHandler implements IServerHandler {
        @Override
        public boolean onConnect(final String clientID, final InetAddress clientAddress) {
            return (m_ServerHandler == null) || m_ServerHandler.onConnect(clientID, clientAddress);
        } /* End of 'ServerHandler::onConnect' method */

        /* *
       * METHOD: Unites bytes arrays
       * RETURN: United bytes array
       *  PARAM: [IN] queue - queue of bytes arrays to unite
       * AUTHOR: Tverskikh Sergey
       * */
        public byte[] decodeMessage(final List<byte[]> encodedBlocks) {
            final List<byte[]> bufferList = new ArrayList<byte[]>();

            /* Decode blocks */
            for (final byte[] encodedBlock : encodedBlocks)
                bufferList.add(RSA.decrypt(m_ServerKey.getPrivate(), encodedBlock));

            return concatenateByteArrays(bufferList);
        } /* End of 'ServerHandler::decodeQueue' method */

        /* *
         * METHOD: Encodes message using server's public key
         * RETURN: True if success, False otherwise
         *  PARAM: [IN] message - message to send
         * AUTHOR: Eliseev Dmitry
         * */
        private Message encodeMessage(final Message message) {
            final List<byte[]> messageBlocks = splitByteArray(message.getData());
            final List<byte[]> encryptedBlocks = new ArrayList<byte[]>(messageBlocks.size());

            for (final byte[] messageBlock : messageBlocks)
                encryptedBlocks.add(RSA.encrypt(m_ClientKey.getKey(), messageBlock));

            return new Message(new SafeMessage(encryptedBlocks));
        } /* End of 'ServerHandler::encodeMessage' method */

        /* *
         * METHOD: Decrypts message using self private key and executes client's handler
         * RETURN: Some server response
         *  PARAM: [IN] message - encrypted message
         * AUTHOR: Eliseev Dmitry
         * */
        private ServerResponse processEncryptedMessage(final String clientID, final SafeMessage message) {
            if ((m_ServerHandler == null)||(m_ClientKey == null))
                return ServerResponse.NOTHING;

            /* Get response */
            final ServerResponse response = m_ServerHandler.onReceive(clientID, new Message(decodeMessage(message.getBlocks())));

            /* No response, handler wants to close connection */
            if (response == null)
                return null;

            /* Empty response, handler wont's to answer anything */
            if (response.getMessage() == null)
                return response;

            /* Prepare final answer */
            final Message encodedMessage = encodeMessage(response.getMessage());
            return (response.isBroadcast())?new ServerResponse(encodedMessage, true):new ServerResponse(encodedMessage, clientID);
        } /* End of 'SafeClientHandler::processEncryptedMessage' method */

        @Override
        public ServerResponse onReceive(final String clientID, final Message message) {
            final Object object = message.getObject();

            if (object instanceof EncryptionKey) {
                m_ClientKey = ((EncryptionKey) object);
                return new ServerResponse(new Message(new EncryptionKey(m_ServerKey.getPublic(), m_BlockSize)));
            } else if (object instanceof SafeMessage)
                return processEncryptedMessage(clientID, (SafeMessage) object);
            else
                LoggingUtility.warning("Unknown message type: " + object.getClass().getName());

            return ServerResponse.NOTHING;
        } /* End of 'ServerHandler::onReceive' method */

        @Override
        public void onDisconnect(final String clientID) {
            if (m_ServerHandler != null)
                m_ServerHandler.onDisconnect(clientID);
        } /* End of 'ServerHandler::onDisconnect' method */
    } /* End of 'SafeServer::ServerHandler' class */

    /* *
     * METHOD: Server class constructor
     *  PARAM: [IN] port    - port for listening
     *  PARAM: [IN] keySize - encryption key size
     *  PARAM: [IN] handler - reference to clients handler
     * AUTHOR: Eliseev Dmitry
     * */
    public SafeServer(final int port, final int keySize, final IServerHandler handler) {
        m_ServerKey = RSA.generateKey(keySize);
        m_BlockSize = (keySize / 8) - 11;

        m_ServerHandler = handler;
        m_Server = new Server(port, new ServerHandler());
    } /* End of 'SafeServer::SafeServer' method */

    @Override
    public synchronized void stop() {
        if (m_Server != null)
            m_Server.stop();
    } /* End of 'SafeServer::stop' method */

    @Override
    public synchronized void run() {
        if (m_Server != null)
            m_Server.run();
    } /* End of 'SafeServer::run' method */
} /* End of 'SafeServer' class */


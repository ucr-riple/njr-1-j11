package com.gesoftware.venta.network;

import com.gesoftware.venta.network.handlers.IClientHandler;
import com.gesoftware.venta.network.model.EncryptionKey;
import com.gesoftware.venta.network.model.SafeMessage;
import com.gesoftware.venta.logging.LoggingUtility;
import com.gesoftware.venta.security.model.KeyPair;
import com.gesoftware.venta.network.model.Message;
import com.gesoftware.venta.security.RSA;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Safe connection class definition
 **/
public final class SafeConnection implements IConnection {
    /* Real connection */
    private Connection m_Connection;

    /* Client's decryption key */
    private final KeyPair m_ClientKey;
    private final int m_BlockSize;

    /* Server's encryption key */
    private EncryptionKey m_ServerKey;

    /* User-defined handler */
    private final IClientHandler m_ClientHandler;

    /* Synchronization object */
    private final Object m_Sync = new Object();

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
    } /* End of 'SafeConnection::concatenateByteArrays' method */

    /* *
     * METHOD: Splits byte array using encryption block size
     * RETURN: List of blocks
     *  PARAM: [IN] byteArray - byte array to split
     * AUTHOR: Eliseev Dmitry
     * */
    private List<byte[]> splitByteArray(final byte[] byteArray) {
        final List<byte[]> byteArrays = new ArrayList<byte[]>(byteArray.length / m_ServerKey.getBlockSize() + 1);

        int position = 0;
        while (position < byteArray.length) {
            byteArrays.add(Arrays.copyOfRange(byteArray, position, java.lang.Math.min(position + m_ServerKey.getBlockSize(), byteArray.length)));
            position += m_ServerKey.getBlockSize();
        }

        return byteArrays;
    } /* End of 'SafeConnection::concatenateByteArrays' method */

    /**
     * Safe connection client handler class definition
     **/
    private final class SafeClientHandler implements IClientHandler {
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
                bufferList.add(RSA.decrypt(m_ClientKey.getPrivate(), encodedBlock));

            return concatenateByteArrays(bufferList);
        } /* End of 'SafeClientHandler::decodeQueue' method */

        /* *
         * METHOD: Decrypts message using self private key and executes client's handler
         *  PARAM: [IN] message - encrypted message
         * AUTHOR: Eliseev Dmitry
         * */
        private void processEncryptedMessage(final SafeMessage message) {
            if (m_ClientHandler != null)
                m_ClientHandler.onReceive(new Message(decodeMessage(message.getBlocks())));
        } /* End of 'SafeClientHandler::processEncryptedMessage' method */

        @Override
        public final void onReceive(final Message message) {
            final Object object = message.getObject();

            if (object instanceof EncryptionKey)
                m_ServerKey = ((EncryptionKey) object);
            else if (object instanceof SafeMessage)
                processEncryptedMessage((SafeMessage) object);
            else
                LoggingUtility.warning("Unknown message type: " + object.getClass().getName());
        } /* End of 'SafeClientHandler::onReceive' method */

        @Override
        public final void onConnectionLost(final String message) {
            if (m_ClientHandler != null)
                m_ClientHandler.onConnectionLost(message);
        } /* End of 'SafeClientHandler::onConnectionLost' method */
    } /* End of 'SafeConnection::SafeClientHandler' class */

    /* *
     * METHOD: Connection class constructor
     *  PARAM: [IN] host          - server host
     *  PARAM: [IN] port          - server port
     *  PARAM: [IN] keySize       - size of encryption key
     *  PARAM: [IN] clientHandler - server responses handler
     * AUTHOR: Eliseev Dmitry
     * */
    public SafeConnection(final String host, final int port, final int keySize, final IClientHandler clientHandler) {
        m_ClientKey = RSA.generateKey(keySize);
        m_BlockSize = (keySize / 8) - 11;

        m_ClientHandler = clientHandler;
        m_Connection = new Connection(host, port, new SafeClientHandler());
    } /* End of 'SafeConnection::Connection' method */

    /* *
     * METHOD: Sends generated public key to server
     * RETURN: True if success, False otherwise
     * AUTHOR: Eliseev Dmitry
     * */
    private boolean sendPublicKey() {
        synchronized (m_Sync) {
            return m_Connection.send(new Message(new EncryptionKey(m_ClientKey.getPublic(), m_BlockSize)));
        }
    } /* End of 'SafeConnection::sendPublicKey' method */

    @Override
    public final boolean connect() {
        synchronized (m_Sync) {
            return m_Connection != null && m_Connection.connect() && sendPublicKey();
        }
    } /* End of 'SafeConnection::connect' method */

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
            encryptedBlocks.add(RSA.encrypt(m_ServerKey.getKey(), messageBlock));

        return new Message(new SafeMessage(encryptedBlocks));
    } /* End of 'SafeConnection::encodeMessage' method */

    @Override
    public final boolean send(final Message message) {
        synchronized (m_Sync) {
            return m_Connection != null && m_Connection.isConnected() && m_ServerKey != null && m_Connection.send(encodeMessage(message));
        }
    } /* End of 'SafeConnection::send' method */

    @Override
    public boolean isConnected() {
        return  m_Connection != null && m_Connection.isConnected();
    } /* End of 'SafeConnection::isConnected' method */

    @Override
    public void disconnect() {
        synchronized (m_Sync) {
            if (m_Connection != null)
                m_Connection.disconnect();

            /* Reset variables */
            m_Connection = null;
            m_ServerKey  = null;
        }
    } /* End of 'SafeConnection::disconnect' method */
} /* End of 'SafeConnection' class */

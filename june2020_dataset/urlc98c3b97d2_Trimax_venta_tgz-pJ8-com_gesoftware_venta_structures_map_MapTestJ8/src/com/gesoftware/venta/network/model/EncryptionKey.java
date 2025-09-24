package com.gesoftware.venta.network.model;

import com.gesoftware.venta.security.model.Key;

import java.io.Serializable;

/* *
 * Encryption key class definition
 * */
public final class EncryptionKey implements Serializable {
    /* Encryption key (public key) */
    private final Key m_EncryptionKey;

    /* Block size */
    private final int m_BlockSize;

    /* *
     * METHOD: Encryption key class constructor
     *  PARAM: [IN] key       - public key
     *  PARAM: [IN] blockSize - maximal allowed block size
     * AUTHOR: Eliseev Dmitry
     * */
    public EncryptionKey(final Key key, final int blockSize) {
        m_EncryptionKey = key;
        m_BlockSize     = blockSize;
    } /* End of 'EncryptionKey::EncryptionKey' method */

    /* *
     * METHOD: Gets public key
     * RETURN: Public key
     * AUTHOR: Eliseev Dmitry
     * */
    public final Key getKey() {
        return m_EncryptionKey;
    } /* End of 'EncryptionKey::getKey' method */

    /* *
     * METHOD: Gets block size
     * RETURN: Maximal allowed block size
     * AUTHOR: Eliseev Dmitry
     * */
    public final int getBlockSize() {
        return m_BlockSize;
    } /* End of 'EncryptionKey::getBlockSize' method */
} /* End of 'EncryptionKey' class */

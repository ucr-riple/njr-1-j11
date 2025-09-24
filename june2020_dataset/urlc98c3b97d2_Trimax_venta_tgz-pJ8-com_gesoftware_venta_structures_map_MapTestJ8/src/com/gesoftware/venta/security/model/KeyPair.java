package com.gesoftware.venta.security.model;

/**
 * Key class definition
 */
public final class KeyPair {
    /* Private key */
    private final Key m_Private;

    /* Public key */
    private final Key m_Public;

    /* *
     * METHOD: KeyPair class constructor
     *  PARAM: [IN] privateKey - private key
     *  PARAM: [IN] publicKey  - public key
     * AUTHOR: Dmitry Eliseev
     * */
    public KeyPair(final Key privateKey, final Key publicKey) {
        m_Private = privateKey;
        m_Public  = publicKey;
    } /* End of 'KeyPair::KeyPair' method */

    /* *
     * METHOD: Private key getter
     * RETURN: Private key
     * AUTHOR: Dmitry Eliseev
     * */
    public final Key getPrivate() {
        return m_Private;
    } /* End of 'KeyPair::getPrivate' method */

    /* *
     * METHOD: Public key getter
     * RETURN: Public key
     * AUTHOR: Dmitry Eliseev
     * */
    public final Key getPublic() {
        return m_Public;
    } /* End of 'KeyPair::getPublic' method */
} /* End of 'KeyPair' class */

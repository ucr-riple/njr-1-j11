package com.gesoftware.venta.security;

import com.gesoftware.venta.logging.LoggingUtility;

import javax.crypto.Cipher;
import java.security.*;
import java.security.KeyPair;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

/**
 * RSA class definition
 */
public final class RSA {
    /* Keys generator */
    private static KeyPairGenerator m_Generator;

    /* Keys factory */
    private static KeyFactory m_KeyFactory;

    /* Cipher */
    private static Cipher m_Cipher;

    /* Static members initialization (using RSA algorithm) */
    static {
        try {
            m_Generator  = KeyPairGenerator.getInstance("RSA");
            m_KeyFactory = KeyFactory.getInstance("RSA");
            m_Cipher     = Cipher.getInstance("RSA");
        } catch (Exception e) {
            m_KeyFactory = null;
            m_Cipher = null;

            LoggingUtility.core("RSA initialization failed: " + e.getMessage());
        }
    } /* End of 'RSA::static' method */

    /* *
     * METHOD: Creates internal public key using library's key
     * RETURN: Created public key
     *  PARAM: [IN] key - unique actor's identifier
     * AUTHOR: Dmitry Eliseev
     * */
    private static PublicKey createPublicKey(final com.gesoftware.venta.security.model.Key key) {
        if (m_KeyFactory == null)
            return null;

        try {
            RSAPublicKeySpec keySpec = new RSAPublicKeySpec(key.getModulus(), key.getExponent());
            return m_KeyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            throw new RuntimeException("Spurious serialisation error", e);
        }
    } /* End of 'RSA::createPublicKey' method */

    /* *
     * METHOD: Creates internal private key using library's key
     * RETURN: Created public key
     *  PARAM: [IN] key - unique actor's identifier
     * AUTHOR: Dmitry Eliseev
     * */
    private static PrivateKey createPrivateKey(final com.gesoftware.venta.security.model.Key key) {
        if (m_KeyFactory == null)
            return null;

        try {
            RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(key.getModulus(), key.getExponent());
            return m_KeyFactory.generatePrivate(keySpec);
        } catch (Exception e) {
            throw new RuntimeException("Spurious serialisation error", e);
        }
    } /* End of 'RSA::createPrivateKey' method */

    /* *
     * METHOD: Encrypts data array using public key
     * RETURN: Encrypted data array if success, null otherwise
     *  PARAM: [IN] publicKey - public key for encryption
     *  PARAM: [IN] data      - data array to encrypt (data.length < publicKey.size / 8)
     * AUTHOR: Dmitry Eliseev
     * */
    public static byte[] encrypt(final com.gesoftware.venta.security.model.Key publicKey, final byte data[]) {
        try {
            PublicKey key = createPublicKey(publicKey);
            m_Cipher.init(Cipher.ENCRYPT_MODE, key);
            return m_Cipher.doFinal(data);
        } catch (Exception e) {
            LoggingUtility.core("Error during encryption: " + e.getMessage());
        }

        return null;
    } /* End of 'RSA::encrypt' method */

    /* *
     * METHOD: Decrypts data array using public key
     * RETURN: Decrypted data array if success, null otherwise
     *  PARAM: [IN] privateKey - private key for decryption
     *  PARAM: [IN] data       - data array to encrypt (data.length < publicKey.size / 8)
     * AUTHOR: Dmitry Eliseev
     * */
    public static byte[] decrypt(final com.gesoftware.venta.security.model.Key privateKey, final byte data[]) {
        try {
            PrivateKey key = createPrivateKey(privateKey);
            m_Cipher.init(Cipher.DECRYPT_MODE, key);
            return m_Cipher.doFinal(data);
        } catch (Exception e) {
            LoggingUtility.core("Error during decryption: " + e.getMessage());
        }

        return null;
    } /* End of 'RSA::decrypt' method */

    /* *
     * METHOD: Generates public-private key pair
     * RETURN: Generated pair if success, null otherwise
     *  PARAM: [IN] size - key size (bits)
     * AUTHOR: Dmitry Eliseev
     * */
    public static com.gesoftware.venta.security.model.KeyPair generateKey(final int bits) {
        if (m_Generator == null)
            return null;

        m_Generator.initialize(bits);
        KeyPair keyPair = m_Generator.genKeyPair();

        RSAPrivateKeySpec privateKey;
        RSAPublicKeySpec publicKey;

        try {
            publicKey = m_KeyFactory.getKeySpec(keyPair.getPublic(), RSAPublicKeySpec.class);
            privateKey = m_KeyFactory.getKeySpec(keyPair.getPrivate(), RSAPrivateKeySpec.class);
        } catch (InvalidKeySpecException e) {
            LoggingUtility.core("Can't generate private - public keys pair: " + e.getMessage());
            return null;
        }

        return new com.gesoftware.venta.security.model.KeyPair(new com.gesoftware.venta.security.model.Key(privateKey.getModulus(), privateKey.getPrivateExponent()),
                                                             new com.gesoftware.venta.security.model.Key(publicKey.getModulus(), publicKey.getPublicExponent()));
    } /* End of 'RSA::generateKey' method */
} /* End of 'RSA' class */

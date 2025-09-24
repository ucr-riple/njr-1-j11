package com.gesoftware.venta.security;

import com.gesoftware.venta.security.model.KeyPair;
import com.gesoftware.venta.logging.LoggingUtility;
import com.gesoftware.venta.utility.SerializationUtility;

import java.io.UnsupportedEncodingException;

public final class SecurityTest {
    private final static String c_OriginalMessage = "The quick brown fox jumps over the lazy dog";

    public static void main(final String args[]) {
        LoggingUtility.setLoggingLevel(LoggingUtility.LoggingLevel.LEVEL_DEBUG);

        /* Generate key pair */
        KeyPair keyPair = RSA.generateKey(2048);

        final byte[] encryptedMessage = RSA.encrypt(keyPair.getPublic(), SerializationUtility.pack(c_OriginalMessage));
        final String decryptedMessage = (String)SerializationUtility.unpack(RSA.decrypt(keyPair.getPrivate(), encryptedMessage));

        try {
            LoggingUtility.info(" Original message: " + c_OriginalMessage);
            LoggingUtility.info("Encrypted message: " + new String(encryptedMessage, "UTF-8"));
            LoggingUtility.info("Decrypted message: " + decryptedMessage);
        } catch (UnsupportedEncodingException e) {
            LoggingUtility.error("Exception: " + e.getMessage());
        }
    }
}

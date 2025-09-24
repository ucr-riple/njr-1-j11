package com.gesoftware.venta.utility;

import com.gesoftware.venta.hashing.Base64DecoderException;
import com.gesoftware.venta.logging.LoggingUtility;
import com.gesoftware.venta.hashing.Base64;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

/***
 * Electronic signature verification
 ***/
public final class VerificationUtility {
    private static final String c_SignatureAlgorithm = "SHA1withRSA";
    private static final String c_KeyFactoryAlgorithm = "RSA";

    /* *
     * METHOD: Verifies data using vested signature and well-known public key
     * RETURN: True if data signed with vested signature, False otherwise
     *  PARAM: [IN] signedData - data to verify
     *  PARAM: [IN] signature  - vested signature
     *  PARAM: [IN] publicKey  - base64 encoded public key
     * AUTHOR: Eliseev Dmitry
     * */
    public static boolean verify(final String signedData, final String signature, final String publicKey) {
        return signedData != null && !ValidationUtility.isEmpty(signature) && verify(signedData, signature, generatePublicKey(publicKey));
    }  /* End of 'VerificationUtility::verify' method */

    /* *
     * METHOD: Generates public key object using it's base64 encoded representation
     * RETURN: Public key
     *  PARAM: [IN] encodedPublicKey - base64 encoded public key
     * AUTHOR: Eliseev Dmitry
     * */
    private static PublicKey generatePublicKey(final String encodedPublicKey) {
        try {
            final KeyFactory keyFactory = KeyFactory.getInstance(c_KeyFactoryAlgorithm);
            return keyFactory.generatePublic(new X509EncodedKeySpec(Base64.decode(encodedPublicKey)));
        } catch (final NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (final InvalidKeySpecException e) {
            throw new IllegalArgumentException(e);
        } catch (final Base64DecoderException e) {
            throw new IllegalArgumentException(e);
        }
    }  /* End of 'VerificationUtility::generatePublicKey' method */

    /* *
     * METHOD: Verifies data using vested signature and well-known public key
     * RETURN: True if data signed with vested signature, False otherwise
     *  PARAM: [IN] signedData - data to verify
     *  PARAM: [IN] signature  - vested signature
     *  PARAM: [IN] publicKey  - public key
     * AUTHOR: Eliseev Dmitry
     * */
    private static boolean verify(final String signedData, final String signature, final PublicKey publicKey) {
        Signature sig;
        try {
            sig = Signature.getInstance(c_SignatureAlgorithm);
            sig.initVerify(publicKey);
            sig.update(signedData.getBytes());

            return sig.verify(Base64.decode(signature));
        } catch (final NoSuchAlgorithmException e) {
            LoggingUtility.error("Can't find decoding algorithm");
        } catch (final InvalidKeyException e) {
            LoggingUtility.error("Invalid key specification");
        } catch (final SignatureException e) {
            LoggingUtility.error("Signature exception");
        } catch (final Base64DecoderException e) {
            LoggingUtility.error("Base64 decoding failed");
        }

        return false;
    }  /* End of 'VerificationUtility::verify' method */
} /* End of 'VerificationUtility' class */

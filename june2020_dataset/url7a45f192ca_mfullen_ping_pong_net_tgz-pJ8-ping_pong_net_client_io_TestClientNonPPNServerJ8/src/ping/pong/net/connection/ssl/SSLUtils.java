package ping.pong.net.connection.ssl;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ping.pong.net.connection.config.ConnectionConfiguration;
import ping.pong.net.utils.PPNUtils;

/**
 *
 * @author mfullen
 */
public final class SSLUtils
{
    private static final Logger LOGGER = LoggerFactory.getLogger(SSLUtils.class);
    private static final String DEFAULT_KEYSTORE_TYPE = "JKS";
    private static final String DEFAULT_SSL_ALGO = "TLS";

    private SSLUtils()
    {
    }

    /**
     * Creates an SSLContext based on the config. Uses TLS algorithim
     *
     * @param config the configuration to use
     * @return SSL context created and initialized based on the config
     */
    public static SSLContext createSSLContext(ConnectionConfiguration config)
    {
        return createSSLContext(DEFAULT_SSL_ALGO, config);
    }

    /**
     * Creates an SSLContext based on the config.
     *
     * @param sslAlgorithim the sslAlgorithim to use
     * @param config the configuration to use
     * @return SSL context created and initialized based on the config and the
     * ssl algorithim
     */
    public static SSLContext createSSLContext(String sslAlgorithim, ConnectionConfiguration config)
    {
        SSLContext ctx = null;
        TrustManager[] trustManagers = null;
        KeyManager[] keyManagers = null;
        try
        {
            trustManagers = getTrustManagers(DEFAULT_KEYSTORE_TYPE, config.getKeystorePath(), config.getKeystorePassword());
            keyManagers = getKeyManagers(DEFAULT_KEYSTORE_TYPE, config.getKeystorePath(), config.getKeystorePassword());
        }
        catch (Exception ex)
        {
            LOGGER.error("Something went wrong creating the KeyManager and Trust Manager", ex);
        }

        try
        {
            ctx = SSLContext.getInstance(sslAlgorithim);
            ctx.init(keyManagers, trustManagers, new SecureRandom());
        }
        catch (Exception e)
        {
            LOGGER.warn("Reverting to the default Algorithm");
            ctx = SSLContext.getDefault();
            ctx.init(keyManagers, trustManagers, new SecureRandom());
        }
        finally
        {
            return ctx;
        }
    }

    /**
     *
     * @param keyStoreType
     * @param keyStoreFile
     * @param keyStorePassword
     * @return
     * @throws KeyStoreException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws CertificateException
     * @throws UnrecoverableKeyException
     */
    public static KeyManager[] getKeyManagers(String keyStoreType, InputStream keyStoreFile, String keyStorePassword)
            throws KeyStoreException, IOException, NoSuchAlgorithmException,
                   CertificateException, UnrecoverableKeyException
    {
        KeyStore keyStore = KeyStore.getInstance(keyStoreType);
        keyStore.load(keyStoreFile, keyStorePassword.toCharArray());
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(keyStore, keyStorePassword.toCharArray());
        return kmf.getKeyManagers();
    }

    /**
     *
     * @param keyStoreType
     * @param keyStoreFilePath
     * @param keyStorePassword
     * @return
     * @throws KeyStoreException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws CertificateException
     * @throws UnrecoverableKeyException
     */
    public static KeyManager[] getKeyManagers(String keyStoreType, String keyStoreFilePath, String keyStorePassword)
            throws KeyStoreException, IOException, NoSuchAlgorithmException,
                   CertificateException, UnrecoverableKeyException
    {
        return getKeyManagers(keyStoreType, PPNUtils.getInputStreamFromPath(keyStoreFilePath), keyStorePassword);
    }

    /**
     *
     * @param trustStoreType
     * @param trustStoreFile
     * @param trustStorePassword
     * @return
     * @throws KeyStoreException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws CertificateException
     * @throws UnrecoverableKeyException
     */
    public static TrustManager[] getTrustManagers(String trustStoreType, InputStream trustStoreFile, String trustStorePassword)
            throws KeyStoreException, IOException, NoSuchAlgorithmException,
                   CertificateException, UnrecoverableKeyException
    {
        KeyStore trustStore = KeyStore.getInstance(trustStoreType);
        trustStore.load(trustStoreFile, trustStorePassword.toCharArray());
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(trustStore);
        return tmf.getTrustManagers();
    }

    /**
     *
     * @param trustStoreType
     * @param trustStoreFilePath
     * @param trustStorePassword
     * @return
     * @throws KeyStoreException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws CertificateException
     * @throws UnrecoverableKeyException
     */
    public static TrustManager[] getTrustManagers(String trustStoreType, String trustStoreFilePath, String trustStorePassword)
            throws KeyStoreException, IOException, NoSuchAlgorithmException,
                   CertificateException, UnrecoverableKeyException
    {
        return getTrustManagers(trustStoreType, PPNUtils.getInputStreamFromPath(trustStoreFilePath), trustStorePassword);
    }
}

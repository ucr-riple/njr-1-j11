package ping.pong.net.server;

import java.io.*;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.net.Socket;
import java.net.URISyntaxException;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.*;
import ping.pong.net.client.Client;
import ping.pong.net.client.io.IoClient;
import ping.pong.net.connection.config.ConnectionConfigFactory;
import ping.pong.net.connection.config.ConnectionConfiguration;
import ping.pong.net.connection.messaging.MessageListener;

/**
 *
 * @author mfullen
 */
public class ClientExample
{
    private static class DefaultTrustManager implements X509TrustManager
    {
        @Override
        public void checkClientTrusted(X509Certificate[] arg0, String arg1)
                throws CertificateException
        {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] arg0, String arg1)
                throws CertificateException
        {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers()
        {
            return null;
        }
    }

    public static void ssl1() throws IOException,
                                     InterruptedException,
                                     NoSuchAlgorithmException,
                                     KeyManagementException,
                                     KeyStoreException,
                                     UnrecoverableKeyException,
                                     CertificateException,
                                     URISyntaxException
    {
        {

//            KeyStore ks = KeyStore.getInstance("JKS");
//
//            char[] pw = "pingpong123".toCharArray();
//            ks.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(ConnectionConfiguration.DEFAULT_KEY_STORE), pw);
//            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
//            keyManagerFactory.init(ks, pw);
//            SSLContext ctx = SSLContext.getInstance("SSLv3");
//            ctx.init(keyManagerFactory.getKeyManagers(), null, new SecureRandom());
////            ctx.init(new KeyManager[0], new TrustManager[]
////                    {
////                        new DefaultTrustManager()
////                    }, new SecureRandom());
//            SSLContext.setDefault(ctx);
            // SSLSocketFactory factory = (SSLSocketFactory) ctx.getSocketFactory();


            SSLContext ctx = SSLContext.getInstance("SSLv3");
            ctx.init(null, null, null);


            String absoluteKeyPath = new File(Thread.currentThread().getContextClassLoader().getResource(ConnectionConfiguration.DEFAULT_KEY_STORE).toURI()).getAbsolutePath();
            System.out.println(absoluteKeyPath);
            System.setProperty("javax.net.ssl.keyStore", absoluteKeyPath);
            System.setProperty("javax.net.ssl.keyStorePassword", ConnectionConfiguration.DEFAULT_KEY_STORE_PASSWORD);
            System.setProperty("java.protocol.handler.pkgs", "com.sun.net.ssl.internal.www.protocol");

            String absolutePath = new File(Thread.currentThread().getContextClassLoader().getResource("pingpong.crt").toURI()).getAbsolutePath();
            System.out.println(absolutePath);
            System.setProperty("javax.net.ssl.trustStore", absoluteKeyPath);
            System.setProperty("javax.net.ssl.trustStorePassword", "pingpong123");

            // SSLSocketFactory factory = (SSLSocketFactory) ctx.getSocketFactory();
            SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            // SSLSocket client = (SSLSocket) factory.createSocket("localhost", 5011);
            Socket client = (SSLSocket) factory.createSocket("localhost", 5011);
            //  client.startHandshake();
            // java.security.cert.Certificate[] serverCerts = client.getSession().getPeerCertificates();
            ObjectInputStream inputStream = new ObjectInputStream(client.getInputStream());
            int readInt = inputStream.readInt();
            System.out.println("SSL- I was assigned Client id: " + readInt);
            ObjectOutputStream outputStream = new ObjectOutputStream(client.getOutputStream());
            outputStream.writeObject(new byte[]
                    {
                        1, 3, 2, 3
                    });
            outputStream.flush();
            outputStream.close();
        }
    }

    public static void ssl2() throws IOException,
                                     InterruptedException,
                                     NoSuchAlgorithmException,
                                     KeyManagementException,
                                     KeyStoreException,
                                     UnrecoverableKeyException,
                                     CertificateException,
                                     URISyntaxException
    {
        {



            // SSLSocketFactory factory = HttpsURLConnection.getDefaultSSLSocketFactory();


            KeyStore ks = KeyStore.getInstance("JKS");
            String absolutePath = new File(Thread.currentThread().getContextClassLoader().getResource(ConnectionConfiguration.DEFAULT_KEY_STORE).toURI()).getAbsolutePath();
            ks.load(new FileInputStream(absolutePath), (ConnectionConfiguration.DEFAULT_KEY_STORE_PASSWORD).toCharArray());
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
            tmf.init(ks);
            SSLContext sslcontext = SSLContext.getInstance("SSLv3");
            sslcontext.init(null, tmf.getTrustManagers(), null);

            SSLSocketFactory factory = (SSLSocketFactory) sslcontext.getSocketFactory();
            SSLSocket client = (SSLSocket) factory.createSocket("localhost", 5011);
            client.startHandshake();
            java.security.cert.Certificate[] serverCerts = client.getSession().getPeerCertificates();
            System.out.println("Server Certs: " + serverCerts);
            ObjectInputStream inputStream = new ObjectInputStream(client.getInputStream());
            int readInt = inputStream.readInt();
            System.out.println("SSL- I was assigned Client id: " + readInt);
            ObjectOutputStream outputStream = new ObjectOutputStream(client.getOutputStream());
            outputStream.writeObject(new byte[]
                    {
                        1, 3, 2, 3
                    });
            outputStream.flush();
            outputStream.close();

            client.close();
        }
    }

    public static void SSLFollowedByRegularConnection() throws IOException,
                                                               InterruptedException,
                                                               NoSuchAlgorithmException,
                                                               KeyManagementException,
                                                               KeyStoreException,
                                                               UnrecoverableKeyException,
                                                               CertificateException,
                                                               URISyntaxException
    {
        // ssl2();
        // regularConnectionByteArrayStaysOpen();
        clientApiConnect();
    }

    public static void clientApiConnect() throws InterruptedException
    {
        IoClient<String> client = new IoClient<String>(ConnectionConfigFactory.createConnectionConfiguration());
        client.addMessageListener(new MessageListener<Client, String>()
        {
            @Override
            public void messageReceived(Client source, String message)
            {
                System.out.println("CLient Id: " + source.getId());
                System.out.println("Message: " + message);
            }
        });
        client.start();
        Thread.sleep(2000);
        ThreadMXBean threads = ManagementFactory.getThreadMXBean();
        ThreadInfo[] threadInfos = threads.getThreadInfo(threads.getAllThreadIds());
        for (int i = 0; i < threadInfos.length; i++)
        {
            ThreadInfo info = threadInfos[i];
            long cpuTimeCumulative = threads.getThreadCpuTime(info.getThreadId()); // in nano seconds
            System.out.println("Info: " + info);

        }
    }

    public static void regularConnectionByteArray() throws IOException,
                                                           InterruptedException,
                                                           NoSuchAlgorithmException,
                                                           KeyManagementException,
                                                           KeyStoreException,
                                                           UnrecoverableKeyException,
                                                           CertificateException,
                                                           URISyntaxException
    {
        Socket client = new Socket("localhost", 5011);
        ObjectInputStream inputStream = new ObjectInputStream(client.getInputStream());
        int readInt = inputStream.readInt();
        System.out.println("I was assigned Client id: " + readInt);
        ObjectOutputStream outputStream = new ObjectOutputStream(client.getOutputStream());
        outputStream.writeObject(new byte[]
                {
                    1, 3, 2, 3
                });
        outputStream.flush();
        outputStream.close();
    }

    public static void regularConnectionByteArrayStaysOpen() throws IOException,
                                                                    InterruptedException,
                                                                    NoSuchAlgorithmException,
                                                                    KeyManagementException,
                                                                    KeyStoreException,
                                                                    UnrecoverableKeyException,
                                                                    CertificateException,
                                                                    URISyntaxException
    {
        Socket client = new Socket("localhost", 5011);
        ObjectInputStream inputStream = new ObjectInputStream(client.getInputStream());
        int readInt = inputStream.readInt();
        System.out.println("I was assigned Client id: " + readInt);
        ObjectOutputStream outputStream = new ObjectOutputStream(client.getOutputStream());
        outputStream.writeObject(new byte[]
                {
                    1, 3, 2, 3
                });
        outputStream.flush();
    }

    public static void regularConnectionsHelloworld() throws IOException,
                                                             InterruptedException,
                                                             NoSuchAlgorithmException,
                                                             KeyManagementException,
                                                             KeyStoreException,
                                                             UnrecoverableKeyException,
                                                             CertificateException,
                                                             URISyntaxException
    {

        Socket client = new Socket("localhost", 5011);
        ObjectInputStream inputStream = new ObjectInputStream(client.getInputStream());
        int readInt = inputStream.readInt();
        System.out.println("I was assigned Client id: " + readInt);
        ObjectOutputStream outputStream = new ObjectOutputStream(client.getOutputStream());
        for (int i = 0; i < 5; i++)
        {
            outputStream.writeObject("hello world:" + i);
            Thread.sleep(500);
        }
        outputStream.flush();
        outputStream.close();

    }

    public static void main(String[] args) throws IOException,
                                                  InterruptedException,
                                                  NoSuchAlgorithmException,
                                                  KeyManagementException,
                                                  KeyStoreException,
                                                  UnrecoverableKeyException,
                                                  CertificateException,
                                                  URISyntaxException
    {
        SSLFollowedByRegularConnection();

        while (true)
        {
        }
//
//        Envelope<String> udpEnvelope = new Envelope<String>()
//        {
//            @Override
//            public boolean isReliable()
//            {
//                return false;
//            }
//
//            @Override
//            public String getMessage()
//            {
//                return "This is a UDP message";
//            }
//        };
//        Envelope<String> tcpEnvelope = new Envelope<String>()
//        {
//            @Override
//            public boolean isReliable()
//            {
//                return false;
//            }
//
//            @Override
//            public String getMessage()
//            {
//                return "This is a TCP message";
//            }
//        };
    }
}

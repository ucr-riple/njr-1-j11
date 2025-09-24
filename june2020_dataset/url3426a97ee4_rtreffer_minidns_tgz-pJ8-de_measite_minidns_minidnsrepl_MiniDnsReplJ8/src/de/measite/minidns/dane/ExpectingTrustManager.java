/*
 * Copyright 2015-2016 the original author or authors
 *
 * This software is licensed under the Apache License, Version 2.0,
 * the GNU Lesser General Public License version 2 or later ("LGPL")
 * and the WTFPL.
 * You may choose either license to govern your use of this software only
 * upon the condition that you accept all of the terms of either
 * the Apache License 2.0, the LGPL 2.1+ or the WTFPL.
 */
package de.measite.minidns.dane;

import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class ExpectingTrustManager implements X509TrustManager {
    private CertificateException exception;
    private final X509TrustManager trustManager;

    /**
     * Creates a new instance of ExpectingTrustManager.
     *
     * @param trustManager The {@link X509TrustManager} to be used for verification.
     *                     {@code null} to use the system default.
     */
    public ExpectingTrustManager(X509TrustManager trustManager) {
        this.trustManager = trustManager == null ? getDefaultTrustManager() : trustManager;
    }

    public boolean hasException() {
        return exception != null;
    }

    public CertificateException getException() {
        CertificateException e = exception;
        exception = null;
        return e;
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        try {
            trustManager.checkClientTrusted(chain, authType);
        } catch (CertificateException e) {
            exception = e;
        }
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        try {
            trustManager.checkServerTrusted(chain, authType);
        } catch (CertificateException e) {
            exception = e;
        }
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return trustManager.getAcceptedIssuers();
    }

    private static X509TrustManager getDefaultTrustManager() {
        try {
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init((KeyStore) null);
            for (TrustManager trustManager : tmf.getTrustManagers()) {
                if (trustManager instanceof X509TrustManager)
                    return (X509TrustManager) trustManager;
            }
        } catch (NoSuchAlgorithmException | KeyStoreException e) {
            throw new RuntimeException("X.509 not supported.", e);
        }
        return null;
    }
}

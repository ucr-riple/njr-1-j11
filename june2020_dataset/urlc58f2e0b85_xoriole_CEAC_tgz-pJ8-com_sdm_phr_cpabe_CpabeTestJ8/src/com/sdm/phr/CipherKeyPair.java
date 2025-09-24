package com.sdm.phr;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

public class CipherKeyPair {

    private String key;
    private String ciphertext;

    public CipherKeyPair(byte[] c, byte[] k) {
        ciphertext = Base64.encode(c);
        key = Base64.encode(k);
    }

    public String getCiphertext() {
        return ciphertext;
    }

    public byte[] getCiphertextBinary() {
        return Base64.decode(ciphertext);
    }

    public String getKey() {
        return key;
    }

    public byte[] getKeyBinary() {
        return Base64.decode(key);
    }
}

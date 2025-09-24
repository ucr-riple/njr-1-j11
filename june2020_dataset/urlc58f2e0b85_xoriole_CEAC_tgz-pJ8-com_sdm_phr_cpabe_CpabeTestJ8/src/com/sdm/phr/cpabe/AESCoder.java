package com.sdm.phr.cpabe;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AESCoder {

    private static byte[] getRawKey(byte[] seed) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        sr.setSeed(seed);
        kgen.init(128, sr); // 192 and 256 bits may not be available
        SecretKey skey = kgen.generateKey();
        byte[] raw = skey.getEncoded();
        return raw;
    }

    public static byte[] encrypt(byte[] seed, byte[] plaintext)
            throws Exception {
        byte[] raw = getRawKey(seed);
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(plaintext);
        return encrypted;
    }

    public static byte[] decrypt(byte[] seed, byte[] ciphertext)
            throws Exception {
        byte[] raw = getRawKey(seed);
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] decrypted = cipher.doFinal(ciphertext);

        return decrypted;
    }

    /* test methods 
    public static void main(String[] args) throws Exception {
        String key = "my key";
        String plainMsg = "Hello world.Hello world.Hello world.Hello world.Hello world.Hello world.Hello world.Hello world.";
        AESCoder coder = new AESCoder();
        byte[] keyb = AESCoder.getRawKey(plainMsg.getBytes());
        byte[] cipherMsg = AESCoder.encrypt(key.getBytes(), plainMsg.getBytes());
        byte[] cipherMsg1 = AESCoder.encrypt(keyb, plainMsg.getBytes());

        String ct = ba2str(cipherMsg);
        System.out.println(ct);

//        byte[] plainAgain = AESCoder.decrypt(key.getBytes(), ct.getBytes());
        byte[] plainAgain = AESCoder.decrypt(keyb, ct.getBytes());
        System.out.println(new String(plainAgain));
    }

    public static String ba2str(byte[] ct) {
        return new String(ct);
    }
    
    */
}

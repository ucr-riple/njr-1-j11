package encoder;


import daoo.ioc.MsgEncoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: Tatiana
 * Date: 17/05/13
 * Time: 17:04
 * To change this template use File | Settings | File Templates.
 */
public class CipherEncoder implements MsgEncoder {
    @Override
    public byte[] encode(String msg) {
        String passphrase = "daoooooo";
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA");
            digest.update(passphrase.getBytes());
            SecretKeySpec key = new SecretKeySpec(digest.digest(), 0, 16, "AES");
            Cipher aes = Cipher.getInstance("AES/ECB/PKCS5Padding");
            aes.init(Cipher.ENCRYPT_MODE, key);
            return aes.doFinal(msg.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public String decode(byte[] code) {
        byte[] bytes = code;
        System.out.println("bytes = " + bytes);
        String passphrase = "daoooooo";
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA");
            digest.update(passphrase.getBytes());
            SecretKeySpec key = new SecretKeySpec(digest.digest(), 0, 16, "AES");
            Cipher aes = Cipher.getInstance("AES/ECB/PKCS5Padding");
            aes.init(Cipher.DECRYPT_MODE, key);
            return new String(aes.doFinal(bytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        CipherEncoder c = new CipherEncoder();
        byte[] encode = c.encode("hola");
        System.out.println("encode = " + Arrays.toString(encode));
        System.out.println("decode = "+c.decode(encode));

    }
}

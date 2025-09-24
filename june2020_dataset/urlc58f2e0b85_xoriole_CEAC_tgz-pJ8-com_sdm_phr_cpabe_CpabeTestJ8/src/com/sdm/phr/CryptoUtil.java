package com.sdm.phr;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;

public class CryptoUtil {

    public static String getFileChecksum(String filename, String type) {
        if (filename.equalsIgnoreCase("N/A")) {
            return "null";
        }
        String checksum = null;
        try {
            MessageDigest md = MessageDigest.getInstance(type);
            FileInputStream fis = new FileInputStream(filename);
            byte[] dataBytes = new byte[1024];

            int nread = 0;

            while ((nread = fis.read(dataBytes)) != -1) {
                md.update(dataBytes, 0, nread);
            }

            byte[] mdbytes = md.digest();

            checksum = toHexString(mdbytes);
        } catch (FileNotFoundException ex) {
//            Logger.getLogger(CryptoUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
//            Logger.getLogger(CryptoUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return checksum;
    }

    public static String getStringChecksum(String content, String type) {
        String checksum = null;
        try {
            MessageDigest md = MessageDigest.getInstance(type);
            md.update(content.getBytes());
            checksum = toHexString(md.digest());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return checksum;
    }

    private static String toHexString(byte[] mdbytes) {
        //convert the byte to hex format
        StringBuffer sb = new StringBuffer("");
        for (int i = 0; i < mdbytes.length; i++) {
            sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

}

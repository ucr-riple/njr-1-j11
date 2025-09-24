package com.sdm.phr;

import com.sdm.phr.cpabe.AESCoder;
import com.sdm.phr.cpabe.Cpabe;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class WriterH {
    
    private String publicParameters;
    
    private CipherKeyPair encrypt(String plaintext, String policy) {
        System.out.println("pt " + plaintext);
        System.out.println("pol " + policy);
        
        String key = String.valueOf(new Random().nextInt());
        byte[] ciphertext = null;
        try {
            ciphertext = AESCoder.encrypt(key.getBytes(), plaintext.getBytes());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        byte[] enc_key = enc(publicParameters, policy, key);
        
        CipherKeyPair result = new CipherKeyPair(ciphertext, enc_key);
        return result;
    }

//    public void sendSection(String message, int patientID) {
//        String policy = "patient";
//        CipherKeyPair ckp = encrypt(message, policy);
//        DatabaseClient.getInstance().insertSection(patientID, ckp, policy);
//    }
    public void sendSection(String message, int patientID, int authorID,
            String policy) {
        String token = requestToken(authorID, patientID);
        if (token == null) {
            JOptionPane.showMessageDialog(null, "No token received from server.", "No token", JOptionPane.ERROR_MESSAGE);
            return;
        }
        System.out.println("token:"+token);
        
        CipherKeyPair ckp = encrypt(message, policy);
//        DatabaseClient.getInstance().insertSection(patientID, authorID, ckp, policy);
        String decryptedToken = null;
        if(patientID==authorID){
            decryptedToken = decryptToken(token,true);
        }else{
           decryptedToken = decryptToken(token); 
        }
        if (DatabaseClient.getInstance().insertSection(patientID, authorID, ckp, policy, decryptedToken)) {
            JOptionPane.showMessageDialog(null, "Data record inserted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Couldn't insert data. Make sure you have sufficient rights.", "Failure", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public String aes_enc(String m, String key) {
        return null;
    }
    
    public byte[] enc(String param, String policy, String ptkey) {
        Cpabe test = new Cpabe();
        param = "/home/phoenix/.phr/public_read.key";
        byte[] byteArrayEnc = null;
        try {
            byteArrayEnc = test.enc(param, policy, ptkey);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return byteArrayEnc;
    }
    
    public String requestToken(int uid, int pid) {
        return DatabaseClient.getInstance().generateToken(pid, uid);
    }
    
    public String decryptToken(String token) {
        Cpabe cpabe = new Cpabe();
        String plainToken = null;
        try {
            plainToken = new String(cpabe.dec(KeyConfig.getInstance().getPatientWritePublicKeyPath(), KeyConfig.getInstance().getUserWriteSecretKeyPath(), Base64.decode(token)));
        } catch (FileNotFoundException fnfe) {
            System.out.println("secret write key not found while decrypting token");
        } catch (Exception ex) {
            Logger.getLogger(WriterH.class.getName()).log(Level.SEVERE, null, ex);
        }
        return plainToken;
    }
    
    public String decryptToken(String token, boolean patient) {
        Cpabe cpabe = new Cpabe();
        String plainToken = null;
        try {
            plainToken = new String(cpabe.dec(KeyConfig.getInstance().getPatientWritePublicKeyPath(), KeyConfig.getInstance().getPatientWriteSecretKeyPath(), Base64.decode(token)));
        }  catch (Exception ex) {
            Logger.getLogger(WriterH.class.getName()).log(Level.SEVERE, null, ex);
        }
        return plainToken;
    }
    
}

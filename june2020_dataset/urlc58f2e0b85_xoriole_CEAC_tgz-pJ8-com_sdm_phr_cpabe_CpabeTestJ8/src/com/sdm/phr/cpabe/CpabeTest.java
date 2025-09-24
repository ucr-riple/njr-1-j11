/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sdm.phr.cpabe;

import com.sun.org.apache.xml.internal.security.utils.Base64;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author phoenix
 */
public class CpabeTest {
    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchAlgorithmException, Exception {
        Cpabe cpabe = new Cpabe();
        String masterKey = "msk.key";
        String publicKey = "pub.key";
        String privateKey = "prv.key";
        
        String attribute = "type:doctor";
        
        cpabe.setup(publicKey, masterKey);
        cpabe.keygen(publicKey, privateKey, masterKey, attribute);
        
        String msg = "hello world";
        String policy = "type:doctor";
        byte[] enc = cpabe.enc(publicKey, policy, msg);
        String encStr = Base64.encode(enc);
        byte[] encBin=Base64.decode(encStr);
        byte[] dec = cpabe.dec(publicKey, privateKey, encBin);
        System.out.println("dec:"+new String(dec));
    }
    
}

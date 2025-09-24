package com.sdm.phr;

import com.sdm.phr.cpabe.Cpabe;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class PHR {

    static String doctor1_attr = "type:doctor hospital:A name:Doctor1";
    static String nurse1_attr = "type:nurse hospital:A name:Nurse1";

    static String doctor_policy = "type:doctor";

    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchAlgorithmException, Exception {
        //System.loadLibrary("libpbc");
        //System.loadLibrary("libjpbc-pbc");

        String pubfile = "/tmp/public_key";
        String mskfile = "/tmp/master_key";
        String prvfile = "/tmp/private_key";

        String inputfile = "/tmp/hello.txt";
        String encfile = "/tmp/hello.txt.enc";
        String decfile = "/tmp/hello2.txt";

//        String user_attr = nurse1_attr;
        String user_attr = doctor1_attr;
        String policy = doctor_policy;

        Cpabe test = new Cpabe();
        test.setup(pubfile, mskfile);
        System.out.println("//Setup complete (public key, master key)");

//        test.keygen(pubfile, prvfile, mskfile, doctor1_attr);
        test.keygen(pubfile, prvfile, mskfile, user_attr);
        System.out.println("//Private key for " + user_attr);

        test.enc(pubfile, policy, inputfile, encfile); // change file to string 
        System.out.println("//encrypting with policy:" + policy);

        System.out.println("//Decrypting");
        try {
            test.dec(pubfile, prvfile, encfile, decfile); // modify to exclude pubfile
//        System.out.println("//end to dec");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

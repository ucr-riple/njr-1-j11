package Utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import factory.KitConfig;
import factory.PartType;

/**
 * This class contains the functions needed to read data from files and parse them into objects
 * @author Neetu George
 *
 */

public abstract class ReadSaveData {
    
    /**
     * Attempts to read input from file containg kit configurations and parse it
     * @param File f
     * @return ArrayList<KitConfig>
     */
    public static ArrayList<KitConfig> readKitConfig() {
    	ArrayList<KitConfig> kitConfigs = null;
    	try{
    		InputStream is = new FileInputStream("KitConfigBackup.sav");
    		InputStream buffer = new BufferedInputStream(is);
    		ObjectInput oi = new ObjectInputStream(buffer);
    		try {
    			kitConfigs = (ArrayList<KitConfig>)oi.readObject();
    		}
    		catch (ClassNotFoundException ex) {
    			System.out.println("Can't parse object");
    		}
    		oi.close();
    	}
    	catch (IOException ex) {
    		System.out.println("Cannot Read this file");
    	}
    	
    	return kitConfigs;
    }
    
    /**
     * Attemps to read file containing PartType information and parse it
     * @param File
     * @return ArrayList<PartType>
     */
    public static ArrayList<PartType> readPartType() {
    	ArrayList<PartType> partTypes = null;
    	try{
    		System.out.println("I'm inside ReadPartType()");
    		InputStream is = new FileInputStream("PartTypesBackup.sav");
    		InputStream buffer = new BufferedInputStream(is);
    		ObjectInput oi = new ObjectInputStream(buffer);
    		try {
    			partTypes = (ArrayList<PartType>)oi.readObject();
    			System.out.println("I'm trying to ParsePartTypes()");
    		}
    		catch (ClassNotFoundException ex) {
    			System.out.println("Can't parse object");
    		}
    		oi.close();
    	}
    	catch (IOException ex) {
    		System.out.println("Cannot Read this file");
    	}
    	return partTypes;
    }
    
}

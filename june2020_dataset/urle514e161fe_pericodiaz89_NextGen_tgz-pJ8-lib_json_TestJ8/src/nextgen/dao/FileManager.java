/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nextgen.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import lib.json.JSONObject;


/**
 *
 * @author Rodrigo
 */
public class FileManager {

    public JSONObject loadData(String fileName) throws Exception {
        //http://codigomaldito.blogspot.com/2011/06/como-leer-un-archivo-de-texto-en-java.html
        DataInputStream in = new DataInputStream(new FileInputStream(fileName));
        BufferedReader buffer = new BufferedReader(new InputStreamReader(in));
        StringBuilder jsonFormat = new StringBuilder();
        String strLinea;
        while ((strLinea = buffer.readLine()) != null) {
            jsonFormat.append(strLinea);
        }

        JSONObject obj = new JSONObject(jsonFormat.toString());
        in.close();
        return obj;
    }

    public void saveData(JSONObject object, String fileName) throws Exception {
        try (FileWriter file = new FileWriter(fileName)) {
            file.write(object.toString());
            file.flush();
        }
    }
    
    
    public static boolean generateFolder(String directory){
         return (new File(directory)).mkdirs();     
    }
    
    public static boolean checkFolder(String directory){
        return new File(directory).exists();
    }
    
    public static void generateFile(String directory, String content){
        try {
 
			File file = new File(directory);
 
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();
             //writer.print(content);
        } catch (Exception e) {
        }
    }
    
    public static void copyDir(String src, String dest){
        File srcFolder = new File(src);
    	File destFolder = new File(dest);
       //make sure source exists
    	if(!srcFolder.exists()){
           //just exit
 
        }else{
 
           try{
        	copyFolder(srcFolder,destFolder);
           }catch(IOException e){
        	e.printStackTrace();
        	//error, just exit
           }
        }
 
    }
 
    private static void copyFolder(File src, File dest)
    	throws IOException{
 
    	if(src.isDirectory()){
 
    		//if directory not exists, create it
    		if(!dest.exists()){
    		   dest.mkdir();
    		   System.out.println("Directory copied from " 
                              + src + "  to " + dest);
    		}
 
    		//list all the directory contents
    		String files[] = src.list();
 
    		for (String file : files) {
    		   //construct the src and dest file structure
    		   File srcFile = new File(src, file);
    		   File destFile = new File(dest, file);
    		   //recursive copy
    		   copyFolder(srcFile,destFile);
    		}
 
    	}else{
    		//if file, then copy it
    		//Use bytes stream to support all file types
    		InputStream in = new FileInputStream(src);
    	        OutputStream out = new FileOutputStream(dest); 
 
    	        byte[] buffer = new byte[1024];
 
    	        int length;
    	        //copy the file content in bytes 
    	        while ((length = in.read(buffer)) > 0){
    	    	   out.write(buffer, 0, length);
    	        }
 
    	        in.close();
    	        out.close();
    	        System.out.println("File copied from " + src + " to " + dest);
    	}
    }
}

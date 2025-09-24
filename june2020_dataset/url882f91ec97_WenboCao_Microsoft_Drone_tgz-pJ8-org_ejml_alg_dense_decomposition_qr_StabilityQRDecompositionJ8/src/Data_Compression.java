import java.io.*;
import java.util.zip.*;

public class Data_Compression {
   static final int BUFFER = 2048;
   public static void main (String argv[]) {
      try {
         BufferedInputStream origin = null;
         FileOutputStream dest = new FileOutputStream("/Users/orangemr/Desktop/Compression/gps_folder2");//an onput stream that write bytes to a file
         ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));//an output stream to write in the zip file
         
         byte data[] = new byte[BUFFER];
         
         File f = new File("/Users/orangemr/Desktop/Compression/gps_folder");
         String f1 = f.toString();

            FileInputStream fi = new FileInputStream(f);//an input stream that reads bytes from a file
            origin = new BufferedInputStream(fi, BUFFER);

            ZipEntry entry = new ZipEntry(f1);
            out.putNextEntry(entry);
            int count;
            while((count = origin.read(data, 0, BUFFER)) != -1) {
               out.write(data, 0, count);
               System.out.println(data);
            }
            origin.close();
         
         out.close();
      } catch(Exception e) {
         e.printStackTrace();
      }
   }
} 

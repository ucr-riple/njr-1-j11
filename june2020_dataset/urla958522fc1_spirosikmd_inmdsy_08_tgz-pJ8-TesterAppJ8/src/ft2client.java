import java.io.*;
import java.net.*;
import java.util.*;
public class ft2client {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException {
		{
			 Socket s=null;
             BufferedReader get=null;
             PrintWriter put=null;
             try
             { 
                 s=new Socket("127.0.0.1",8081);
                 get=new BufferedReader(new InputStreamReader(s.getInputStream()));
                 put=new PrintWriter(s.getOutputStream(),true);
             }  
             catch(Exception e)
             {
                 System.exit(0);
             }
             String u,f;
             System.out.println("Enter the file name to transfer from server:");
             DataInputStream dis=new DataInputStream(System.in);
             f=dis.readLine();
             put.println(f);
             File f1=new File(f);
             FileOutputStream  fs=new FileOutputStream(f1);
             while((u=get.readLine())!=null)
             { 
                 byte jj[]=u.getBytes();
                 fs.write(jj);
             } 
             fs.close();
             System.out.println("File received");
             s.close();
         }      
     
	}
}

		

	



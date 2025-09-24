import java.lang.*;
import java.util.*;
import java.io.*;
import java.sql.Time;

class Test{

  public static void main(String args[]){
    
    Date d = new Date();
    Time t1 = new Time(d.getHours(), d.getMinutes(), d.getSeconds());
    Time t2 = new Time((long)0.0);
    
    System.out.print("Hello world!\n");
    
    DataOutput log = new DataOutput("log.txt", true);
    try{
      
      log.writeToFile( String.format("["+t1.toString()+"]; "+t2.toString()+"ms; temperature %.2f; fileSuffix asdasda", 1.234) );
      
    }catch(IOException e){System.out.printf("Couldnt write file\n");}
  
  }


}
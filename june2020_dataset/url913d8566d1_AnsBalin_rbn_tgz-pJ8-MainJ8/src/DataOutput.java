import java.io.*;
import java.util.*;

public class DataOutput{

  private String path;
  private boolean append = false;

  public DataOutput(String path){
    
    this.path = path;
    
  }
  
  public DataOutput(String path, boolean append){
  
    this.path = path;
    this.append = append;
    
  }
  
  public String getPath(){
    
    return path;
    
  }
  
  public void writeToFile(int[] arr) throws IOException{
  
    FileWriter write = new FileWriter(path, append);
    PrintWriter print_line = new PrintWriter(write);
    
    for(int i=0; i<arr.length; i++){
      print_line.printf("%d\t", arr[i]);
    }
    print_line.printf("\n");

    print_line.close();
  
  }
  
  public void writeToFile(double[] arr) throws IOException{
    
    FileWriter write = new FileWriter(path, append);
    PrintWriter print_line = new PrintWriter(write);
    
    for(int i=0; i<arr.length; i++){
      print_line.printf("%f\t", arr[i]);
    }
    print_line.printf("\n");
    
    print_line.close();
    
  }
  
  public void writeToFile(String str) throws IOException{
  
    FileWriter write = new FileWriter(path, append);
    PrintWriter print_line = new PrintWriter(write);
    
    print_line.printf("%s\n", str);
    print_line.close();
  
  }
  
  public void writeToFile(Object obj) throws IOException{
  
    FileOutputStream fos = new FileOutputStream(path, append);
    ObjectOutputStream oos = new ObjectOutputStream(fos);
    
    oos.writeObject(obj);
    
    oos.flush();
    oos.close();
  
  }
  
  public Network readFile() throws IOException, ClassNotFoundException{
    
    FileInputStream fis = new FileInputStream(path);
    ObjectInputStream ois = new ObjectInputStream(fis);
    
    Network net = (Network) ois.readObject();
    
    return net;
    
  }
  
  public void clearFile() throws IOException{
    
    PrintWriter writer = new PrintWriter(path);
    writer.print("");
    writer.close();
    
  }


}
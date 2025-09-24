/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package commands;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import textgame.LibraryMethods;

/**
 *
 * @author Irakiza Krak
 */
public class History {
    private static History history;
    private List <String> command = new ArrayList();
    private boolean isLoad = false;
    //Singleton : constructor private
    private GameState gameState;
    
    private History(){
        
    }
    
    public static History getHistory(){
        if(history == null){
            history = new History();
        }
        return history;
        
    }
    
    public void add (String line){
        command.add(line);
    }
    
    public void clear(){
        
        if(!command.isEmpty())
            command.removeAll(command);
            
    }
    
    public void save(String path){
        
        try{
            LibraryMethods.save(command, path);
            System.out.println("Game saved");
        } catch (IOException ex) {
            Logger.getLogger(History.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Writing in the save file impossible, try a valid path" + ex.getMessage());
        }
    }
    
   public List<String> load(String path){
       
       List <String> commands;
        commands = null;
        try{
            if(!LibraryMethods.load(path).isEmpty()){
                this.isLoad = true;
                commands = LibraryMethods.load(path);
                for (String cmd : LibraryMethods.load(path)){
                    System.out.println(cmd);
                }
                
           }
        } catch (FileNotFoundException ex) {
            System.out.println("The file specified does not exist " + ex.getMessage());
            Logger.getLogger(History.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(History.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Impossible to load from the specified file, try again" + ex.getMessage());
            
        }
       
       return commands;
      
   }
   
   public boolean isLoaded(){
       return this.isLoad;
   }
   
   public void setLoaded(){
       this.isLoad = false;
   }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package commands;

/**
 * Command that shows the version of the game
 * @author Irakiza Krak
 */
public class Version extends AbstractCommand {
    
    //Default Constructor of the class
    public Version(){
        super("VERSION","Displays the version of the game", "ver");
    }
    @Override
    public void execute(Game game, String Params){
        System.out.println("*************************************");
        System.out.println("Version : 4.0 (Final Version)");
        System.out.println("Author : Jc Krakk Irakiza");
        System.out.println("Contact: jean-claude.irakiza@tuke.sk");
        System.out.println("**************************************");
        
    }
    
}

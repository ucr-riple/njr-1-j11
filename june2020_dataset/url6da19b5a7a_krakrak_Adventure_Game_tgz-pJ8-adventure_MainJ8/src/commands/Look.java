/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package commands;

/**
 * Command that shows the description, possible exits and Items that are in the room
 * @author Irakiza Krak
 */
public class Look extends AbstractCommand{
    
    public Look(){
        super("LOOK", "Shows the description of your location", "look");
    }
    @Override
    public void execute(Game game, String params) {
        System.out.println("Your current location is :");
        game.getCurrentRoom().show();
    }
    
}

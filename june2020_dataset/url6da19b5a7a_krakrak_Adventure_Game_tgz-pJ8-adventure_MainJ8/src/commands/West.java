/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package commands;

/**
 * Allows you to go west
 * @author Irakiza Krak
 */
public class West extends AbstractCommand{
    public West(){
        super("WEST", "Goes west", "we");
    }
        @Override
    public void execute(Game game, String params) {
        if(game.getCurrentRoom().getWest() != null){
            game.setCurrentRoom(game.getCurrentRoom().getWest());
            game.getCurrentRoom().show();
            History hist = History.getHistory();
            hist.add(params);
        }
        else {
            System.out.println("You can't go that way !!");
            
        }
    
    }
}

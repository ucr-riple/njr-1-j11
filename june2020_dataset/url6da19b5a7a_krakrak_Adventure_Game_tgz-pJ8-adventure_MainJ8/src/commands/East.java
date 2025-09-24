/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package commands;

/**
 * Command that allows you to go East
 * @author Irakiza Krak
 */
public class East extends AbstractCommand {
    /**
     * Class constructor, sets the name and the description of the command
     */
    public East(){
        super("EAST", "Goes east", "east");
    }
    /**
     * @see commands.Command
     * @param game
     * @param params 
     */
    @Override
    public void execute(Game game, String params) {
        if(game.getCurrentRoom().getEast() != null){
            game.setCurrentRoom(game.getCurrentRoom().getEast());
            game.getCurrentRoom().show();
            History hist = History.getHistory();
            hist.add(params);
        }
        else {
            System.out.println("You can't go that way");
        }
    
    }
}

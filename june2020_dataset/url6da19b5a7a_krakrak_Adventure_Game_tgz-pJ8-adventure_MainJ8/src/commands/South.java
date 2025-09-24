/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package commands;

/**
 * Command that allows you to go south
 * @author Irakiza Krak
 */
public class South extends AbstractCommand{
    public South(){
        super("SOUTH", "Goes south", "so");
        }
    @Override
    public void execute(Game game, String params) {
        if(game.getCurrentRoom().getSouth() != null){
            game.setCurrentRoom(game.getCurrentRoom().getSouth());
            game.getCurrentRoom().show();
            History hist = History.getHistory();
            hist.add(params);
        }
        else {
            System.out.println("You can't go that way !!");
        }
    
    }
    
}

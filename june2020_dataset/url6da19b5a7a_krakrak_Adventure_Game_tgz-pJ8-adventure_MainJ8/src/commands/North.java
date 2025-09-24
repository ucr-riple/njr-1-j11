/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package commands;

/**
 * Command that allows you to go north
 * @author Irakiza Krak
 */
public class North extends AbstractCommand{
    public North(){
        super("NORTH", "Goes north", "no");
    }
        @Override
    public void execute(Game game, String params) {
        if(game.getCurrentRoom().getNorth() != null){
            game.setCurrentRoom(game.getCurrentRoom().getNorth());
            game.getCurrentRoom().show();
            History hist = History.getHistory();
            hist.add(params);
        }
        else {
            System.out.println("You can't go that way !!");
            
        }
    
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package commands;
/**
 * Command that quits the game and shows the menu
 * @author Irakiza Krak
 */
public class Quit extends AbstractCommand {
    
    //Default constructor of the Class
    
    public Quit(){
        super("Quit", "Exits the game and saves the game before quitting","Q");
        
    }
    //Method execute
    /**
     *
     * @param game
     * @param params
     */
    @Override
    public void execute(Game game, String params){
        if(game.getGameState() == GameState.PLAYING){
            Save save = new Save();
            save.execute(game, "**** autofile");
        }
        Menu menu = new Menu();
        menu.execute(game, "quit");
    }

    
}

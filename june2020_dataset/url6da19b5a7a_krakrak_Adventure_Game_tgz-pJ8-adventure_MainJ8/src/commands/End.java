/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package commands;

/**
 * Ends the game without returning
 * @author Irakiza Krak
 */
public class End extends AbstractCommand{
    /**
     * Sets the name and the description of the command
     */
    public End(){
        super("END", "Ends the Game's process", "end");
    }
    /**
     * Method exit() used here, no return possible
     * @param game
     * @param params 
     */
    @Override
    public void execute(Game game, String params) {
        //Temporary Solution
        System.out.println("Thank you for playing Goodbye");
        Version version = new Version();
        version.execute(game, params);
        System.exit(0);
    }

}

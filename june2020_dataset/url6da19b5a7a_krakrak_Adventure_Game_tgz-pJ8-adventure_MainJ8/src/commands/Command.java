/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package commands;

/**
 * Interface implemented by all commands
 * @author Irakiza Krak
 */
public interface Command extends Named {
    /**
     * Method that executes the command typed in the command line
     * @param game
     * @param params 
     */
    public void execute(Game game, String params);
}

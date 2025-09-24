/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package commands;

/**
 * Parses the command line, verify the validity of the parameters in the command line
 * @author Irakiza Krak
 */
public interface Parser {
    /**
     * After verification of the command, returns a reference to the command, so that it can be executed
     * @param command
     * @return 
     */
    public Command getCommand(String command);
}

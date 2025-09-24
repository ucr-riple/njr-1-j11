/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package commands;
import rooms.*;
/**
 * Main Interface of the game, all methods are called after this interface
 * is implemented
 * @author Irakiza Krak
 */
public interface Game {
    /**
     * Gives the state of the game
     * @returnn type GameState
     */
    public GameState getGameState();
    /**
     * Sets the state of the game
     * @param state
     */
    public void setGameState(String state);
    /**
     * Process the command entered in the command line and verified by the parser
     * @param com 
     */
    public void processCommand(String com);
    /**
     * Starts the game (calls the game loop);
     */
    
    public void start();
    /**
     * Initializes all the game with setting rooms, current game, game state...
     */
    
    public void init();
    /**
     * Returns the actual room that we are in
     * @return type Room
     */
    public Room getCurrentRoom();
    
    /**
     * Sets the current room as the room in the parameters
     * @param room
     */
    public void setCurrentRoom(Room room);
    /**
     * Sets the current game as the game in the parameters
     * @param g 
     */

    public void setCurrentGame(Game g);
    /**
     * Gives the reference to the backpack and all its content
     * @return type BackPack
     */
    
    public BackPack getBackPack();
    /**
     * Sets the backpack size
     * @param size 
     */
    public void setBackPack(int size);

}

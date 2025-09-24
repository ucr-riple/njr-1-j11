/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package NPC;

import commands.*;
import items.*;
import java.util.List;

/**
 *
 * @author Irakiza Krak
 */
public interface Npc extends Named{
    public void setStatement(String state);
    public String getStatement();
    public void setName(String named);
    public void setDescription(String description);
    @Override
    public String toString();
    /**
     * Method that returns an array of the Items contained in another object (here Chests)
     * @return type List 
     */
    public List <Item> getItems();
    /**
     * Method that triggers the npc's action to the player or the environment
     * @param game
     * @return 
     */
    public GameState action(Game game);
    /**
     * Give an object to the npc, to keep it or just to lose weight
     * @param item 
     */
    public void addItem(Item item);
}

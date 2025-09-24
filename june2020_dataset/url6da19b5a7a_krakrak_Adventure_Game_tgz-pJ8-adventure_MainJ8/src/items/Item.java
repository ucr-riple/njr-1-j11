/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package items;
import commands.*;
import java.util.List;

/**
 * Interface implemented by all Item like objects of the game (even NPC)
 * @author Irakiza Krak
 */
public interface Item extends Named {
    public void setName(String named);
    public void setDescription(String description);
    public void setShortC(String shortc);
    @Override
    public String toString();
    /**
     * Method that returns an array of the Items contained in another object (here Chests)
     * @return type List 
     */
    public List <Item> getItems();
    /**
     * Method that allows you to use a specific object in a specific way
     * @param game
     * @return 
     */
    public GameState use(Game game);
    /**
     * Adds items to another object (here chests)
     * @param item 
     */
    public void addItem(Item item);
    
    public void removeItem(Item item);
    
}

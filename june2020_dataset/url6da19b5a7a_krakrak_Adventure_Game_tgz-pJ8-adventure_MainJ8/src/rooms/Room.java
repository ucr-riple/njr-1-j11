/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rooms;

import NPC.Npc;
import commands.*;
import items.Item;
import java.util.List;

/**
 * Room Interface that are implemented by all the rooms
 * @author Irakiza Krak
 */
public interface Room extends Named{
    /**
     * Adds the Item to the room
     * @param item 
     */
    void addItem(Item item);
    
    void addNpc(Npc npc);
    
    Npc getNpc(String name);
    
    List getNpcs();
    
    public void removeNpcs(Npc npc);
    /**
     * Gives a reference to the Item specified in the parameters if it is in the room
     * @param name
     * @return type Item
     */
    Item getItem(String name);
    /**
     * Gives the list of Items in the room
     * @return type List
     */
    List getItems();
    /**
     * Sets the current room to the room at the east of the actual room
     * @return type Room
     */
    Room getEast();
    /**
     * Sets the current room to the room at the north of the actual room
     * @return 
     */
    Room getNorth();
    /**
     * Sets the current room to the room at the south of the actual room
     * @return type Room
     */
    Room getSouth();
    /**
     * Sets the current room to the room at the west of the actual room
     * @return 
     */
    Room getWest();
    /**
     * Removes item from the room (taken, or killed objects)
     * @param item 
     */
    void removeItem(Item item);
    /**
     * Sets the description of the current room
     */
    void setDescription(String descr);
    /**
     * Sets the possible exits from a room
     * @param north
     * @param south
     * @param east
     * @param ouest
     */
    void setExits(Room north, Room south, Room east, Room ouest);
    /**
     * Shows what is in the room and possible exits
     */
    void show();
    /**
     * Method for activating special effects in the room (Still to implement)
     */
    public void activate(Game game, Room exit);
    
    public void setNorth(Room north);
    public void setSouth(Room south);
    public void setEast(Room east);
    public void setWest(Room west);
    /**
     *
     * @param game
     * @param exit
     */
    public void setSecretExit(Game game, Room exit);
    /**
     *
     * @return
     */
    public Room getSecretExit();
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package commands;

import items.*;
import java.util.List;

/**
 * Interface backpack, implemented by backpack class
 * @author Irakiza Krak
 */
public interface BackPack {
    /**
     * Gives the capacity of the backpack, parameter of the BackPackImpl constructor
     *
     * @return type Int
     */
    public int getCapacity();
    /**
     * Gives the actual number of Items in the backpack
     * @return tye Int
     */
    public int getCount();
    /**
     * Says if an object has been added or not (if the capacity is reaeched,
     * returns false
     * @param item
     * @return type boolean
     */
    public boolean add(Item item);
    /**
     * Removes Item from the backpack and returns a reference to the removed Item
     * @param name
     * @return type Item
     */
    public Item remove(String name);
    /**
     * Gets the Item precised in the parameter and returns a reference to the specified Item
     * if it exists
     * @param name
     * @return type Item
     */
    public Item getItem(String name);
    /**
     * Returns an Array of all Items contained in the backpack, method called by the
     * command Inventory
     * @return type List
     */
    public List <Item> getItems();
    public void setCapacity(int c);
    
}

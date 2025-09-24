/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package commands;

import items.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Public class that creates the backpack
 * @author Irakiza Krak
 */
public class BackPackImpl implements BackPack {
    private List <Item> backItems = new ArrayList();
    private int volume = 0;
    
    /**
     * Constructor that sets the capacity of the backpack at the same time
     * @param size
     */
    public BackPackImpl(int size){
        volume = size;
    }

    @Override
    public int getCapacity() {
        return volume;
    }
    
    @Override
    public int getCount() {
        return backItems.size();
    }

    @Override
    public boolean add(Item item) {
         if(this.getCount() < this.getCapacity()){
             backItems.add(item);
             System.out.println(item.getName() + " added to your backpack");
             return true;
         }else{
             System.out.println("Your backpack is full");
             return false;
         }    
    }

    @Override
    public Item remove(String name) {
        for(Item object : this.backItems){
            if(name.equalsIgnoreCase(object.getName())){
                if(this.backItems.remove(object) == true){
                    return object;
                }
//            }else{
//                System.out.println("Such object does not exist");
            }
        }
        return null;  
    }

    @Override
    public Item getItem(String name) {
        for(Item object : this.backItems){
            if(name.equalsIgnoreCase(object.getName())){
                return object;
            }
        }
        return null;
    }

    @Override
    public List<Item> getItems() {
        return this.backItems;
    }
    /**
     * Increases the capacity of the backpack by the parameter
     * @param c 
     */
    @Override
    public void setCapacity(int c) {
        if(this.volume+c >= this.backItems.size())
            this.volume = this.volume +c;
    }
}

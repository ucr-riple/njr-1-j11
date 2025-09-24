/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package commands;

import items.*;
import java.util.List;

/**
 * Command that allows you to take an object that is contained in a room
 * @author Irakiza Krak
 */
public class Pick extends AbstractCommand{
    
    //boolean for the if statement below
    private boolean contains = false;
    private boolean containsChest = false;
    private boolean containsChest2 = false;
    public Pick(){
        super("GET", "Insert in your backpack object specified in parameters and located in the current room","get");
    }
    @Override
    public void execute(Game game, String params) {
        //List <Item> roomItems=null;
        List <Item> items;
        items = game.getCurrentRoom().getItems();
        Item item = null;
        Chest itemC = null;
        Chest itemC2 = null;
        //Retrieving the object from the parameters
        
        if(game.getCurrentRoom().getItem("Chest") != null || game.getCurrentRoom().getItem("Opened Chest") != null
                ||game.getCurrentRoom().getItem("Dragon Body") != null || game.getCurrentRoom().getItem("Opened Dragon Body") != null){
            if(game.getCurrentRoom().getItem("Chest") != null)
                itemC = (Chest) game.getCurrentRoom().getItem("Chest");
            if(game.getCurrentRoom().getItem("Opened Chest") != null)
                itemC = (Chest) game.getCurrentRoom().getItem("Opened Chest");
            if(game.getCurrentRoom().getItem("Dragon Body") != null)
                itemC2 = (Chest) game.getCurrentRoom().getItem("Dragon Body");
            if(game.getCurrentRoom().getItem("Opened Dragon Body") != null)
                itemC2 = (Chest) game.getCurrentRoom().getItem("Opened Dragon Body");
//        for(Item chest : items){
//            if(chest instanceof Chest){
//                
//                itemC = (Chest) game.getCurrentRoom().getItem(chest.getName());
            if(itemC != null){
                if(itemC.openedChest()){
                        items.addAll(itemC.getItems());
                        containsChest = true;
                }
                else
                    System.out.println("This chest is closed");
//            
                }
            if(itemC2 != null){
                if(itemC2.openedChest()){
                        items.addAll(itemC2.getItems());
                        containsChest2= true;
                }
                else
                    System.out.println("This chest is closed");
            }
        }
        for(Item object : items){
            if(params.toLowerCase().contains(object.getName().toLowerCase()) || params.toLowerCase().contains(object.getShortC().toLowerCase())){
                contains =true;
                item = object;
            }
        }
        //If the object exists in the room
        if(contains && item != null){
            //The second condtion assures that the item is movable, the first one, the size
            if(game.getBackPack().getCount() < game.getBackPack().getCapacity()){
                //If the object is moveable
                //System.out.println(item.getClass().getSuperclass().toString().toLowerCase());
                if(item instanceof AbstractItemMovable){
                    ///if(game.getBackPack().add(item)) System.out.println(item.getName() +  " now in backpack");
                    //else
                    game.getBackPack().add(item);
                    game.getCurrentRoom().removeItem(item);
                    if(containsChest){ 
                        game.getCurrentRoom().removeItem(itemC);
                        itemC.removeItem(item);
                        game.getCurrentRoom().addItem(itemC);
                        items.removeAll(itemC.getItems());
                        
                    }
                    if(containsChest2){
                        game.getCurrentRoom().removeItem(itemC2);
                        itemC2.removeItem(item);
                        game.getCurrentRoom().addItem(itemC2);
                        items.removeAll(itemC2.getItems());
                    }
                    History hist = History.getHistory();
                    hist.add(params);
                }else
                    System.out.println("You can't take this object");
            }else{
                System.out.println("Sorry, your bag is full");
            }
         //If the object is not in the room 
        }else
            System.out.println("There is no such object in this room");
        contains = false;
        containsChest = false;
        containsChest2 = false;
    }
}

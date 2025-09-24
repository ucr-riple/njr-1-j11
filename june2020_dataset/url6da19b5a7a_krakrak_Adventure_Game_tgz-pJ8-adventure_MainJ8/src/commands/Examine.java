/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package commands;

import NPC.Npc;
import items.*;
import java.util.List;

/**
 * Describes the Item in parameters
 * @author Irakiza Krak
 */
public class Examine extends AbstractCommand{
    //private String param;
    boolean contains = false;
    boolean containsNpc = false;
    //Default Constructor
    /**
     * Sets the name and the description of the command
     */
    public Examine(){
        super("EXAMINE", "Describes the object typed after the command, if it exists","exa");
    }
    /**
     * Verifies that the object is in the room or in backpack before giving description
     * @param game
     * @param params 
     */
    @Override
    public void execute(Game game, String params) {
        List <Item> items;
        items = game.getCurrentRoom().getItems();
        Item item = null;
        List <Item> backItems;
        backItems = game.getBackPack().getItems();
        List <Npc> npcs;
        npcs = game.getCurrentRoom().getNpcs();
        Npc npc = null;
        //Trying to compare the items in the room to a keyword contained in the parameters
        for(Item object : items){
            if(params.toLowerCase().contains(object.getName().toLowerCase()) || params.toLowerCase().contains(object.getShortC().toLowerCase())){
                contains =true;
                item = object;
            }
        }
        
        for(Npc chara : npcs){
            if(params.toLowerCase().contains(chara.getName().toLowerCase()) || params.toLowerCase().contains(chara.getShortC().toLowerCase())){
                containsNpc = true;
                npc = chara;
            }
        }
        
        if(contains  && item != null){
            System.out.println(item.getName()+": \n \t"+item.getDescription());
            if(item instanceof Chest){
                Chest chest = (Chest) item;
                if(chest.openedChest()){
                    if(!chest.getItems().isEmpty()){
                        System.out.println("Items in chest are :");
                        for(Item itemChest : chest.getItems()){
                            System.out.println("\t "+itemChest.getName() + "(" +itemChest.getShortC()+")");
                        }
                    }else 
                        System.out.println("Chest is empty");
                }else
                    System.out.println("Chest is closed");
                    
            }
        }else{
            for (Item object : backItems){
                if(params.toLowerCase().contains(object.getName().toLowerCase()) || params.toLowerCase().contains(object.getShortC().toLowerCase())){
                contains =true;
                item = object;
                }
            }
            if(contains && item != null){
               System.out.println("This Item is in the backpack");
               System.out.println(item.getName()+" :\n \t"+item.getDescription());
            } else if (!containsNpc)
                System.out.println("There is no such object in this room or in your backpack");
        }
        
        if(containsNpc && npc != null){
            System.out.println("********************************");
            System.out.println(npc.getName()+" :\n \t "+npc.getDescription());
        }else if(contains){
            System.out.println("There's nobody who has that name in this room");
        }
    }
    
}

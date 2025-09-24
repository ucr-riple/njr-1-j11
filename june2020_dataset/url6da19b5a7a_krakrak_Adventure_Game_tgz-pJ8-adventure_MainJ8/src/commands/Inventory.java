/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package commands;

import items.Item;
import java.util.List;

/**
 * Command that gives the items in the backpack
 * @author Irakiza Krak
 */
public class Inventory extends AbstractCommand{

    public Inventory(){
        super("INVENTORY", "Gives the elements contained in the backpack", "inv");
    }
    @Override
    public void execute(Game game, String params) {
        List <Item> backItems;
        backItems = game.getBackPack().getItems();
        
       System.out.println("You actual backpack capacity is "+game.getBackPack().getCapacity());
        if(!backItems.isEmpty()){
            System.out.println("You have "+game.getBackPack().getCount()+" Items in your bag");
            System.out.println("-----------------------------------");
            for(Item item : backItems)
                System.out.println("\t " +item.getName()+"(" + item.getShortC() +")" + " - "+item.getDescription());
        }else{
            System.out.println("-----------------------------------");
            System.out.println("Backpack is empty");
        }
            
    }
    
}

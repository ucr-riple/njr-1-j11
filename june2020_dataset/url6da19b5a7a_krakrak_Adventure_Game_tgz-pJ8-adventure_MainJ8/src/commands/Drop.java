/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package commands;

import items.Item;
import java.util.List;

/**
 * Command that allows you to remove an Item from your backpack and leave it in the
 * current room
 * @author Irakiza Krak
 */
public class Drop extends AbstractCommand{

    /**
     * Class constructor, sets the name and the description of the command
     */
    public Drop(){
        super("DROP", "Drops the item from your bag to the current room", "drop");
    }
    /**
     * If item in parameter is in backback, add it to the room and remove it from
     * backpack
     * @see commands.Command
     */
    @Override
    public void execute(Game game, String params) {
        List <Item> backItems;
        backItems = game.getBackPack().getItems();
        Item item = null;
       
        for (Item object : backItems){
            if(params.toLowerCase().contains(object.getName().toLowerCase()) || params.toLowerCase().contains(object.getShortC().toLowerCase())){
                item = object;
            }
        }
        if(item != null){
            //Adding to the room object removed from the backpack
            game.getCurrentRoom().addItem(game.getBackPack().remove(item.getName()));
            System.out.println(item.getName() + " dropped");
            History hist = History.getHistory();
            hist.add(params);
        }else
            System.out.println("You do not possess this item");
    }
}

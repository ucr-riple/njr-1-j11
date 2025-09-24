/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package commands;

import items.Item;
import items.Useable;
import java.util.List;

/**
 * Command that allows you to use the specified Item
 * @author Irakiza Krak
 */
public class Use extends AbstractCommand{
    private boolean contains;

    public Use(){
        super("USE", "Allows you to use an useable object", "use");
    }
    @Override
    public void execute(Game game, String params) {
        List <Item> items;
        items = game.getCurrentRoom().getItems();
        Item item = null;
        List <Item> backItems;
        backItems = game.getBackPack().getItems();
        
        //Trying to compare the items in the room to a keyword contained in the parameters
        for(Item object : items){
            if(params.toLowerCase().contains(object.getName().toLowerCase()) || params.toLowerCase().contains(object.getShortC().toLowerCase())){
                contains =true;
                item = object;
            }
        }
        
        if(contains && item != null){
            if(item instanceof Useable){
                System.out.println("Using " +item.getName() + "...");
                game.setGameState(item.use(game).name());
                System.out.println();
                History hist = History.getHistory();
                hist.add(params);
            }
            
        }else{
            if(!History.getHistory().isLoaded()) System.out.println("Item not in the room, looking in the backpack...");
            for (Item object : backItems){
                if(params.toLowerCase().contains(object.getName().toLowerCase()) || params.toLowerCase().contains(object.getShortC().toLowerCase())){
                contains =true;
                item = object;
                }
            }
            if(contains && item != null){
                //System.out.println(item.getClass().getSuperclass().getInterfaces()[0].toString().toLowerCase());
                if(item instanceof Useable){
                    System.out.println("Using " +item.getName() + "...");
                    game.setGameState(item.use(game).name());
                    History hist = History.getHistory();
                    hist.add(params);
                }else{
                    System.out.println("This object can't be used");
                }
            }else 
                if(!History.getHistory().isLoaded()) System.out.println("There is no such object in this room or in your backpack");
        }
    }
    
}

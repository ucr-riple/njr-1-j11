/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package items;

import commands.Game;
import commands.GameState;
import java.util.List;

/**
 *
 * @author Irakiza Krak
 */
public class Key extends AbstractItemMovable{
    private Chest chest = ChestCreator.createChest();
    
    public Key(){
        super("Key", "Normal key that can open normal chest", "k");
    }
    
    @Override
    public GameState use(Game game) {
        List <Item> items;
        items = game.getCurrentRoom().getItems();
        
//        for(Item item : items){
//            //System.out.println(item.getClass().toString());
//            //getInterfaces, getSuperClass!!
//            if(item.getClass().toString().toLowerCase().contains("chest") || game.getCurrentRoom().getItem("Chest") != null){
//                chest = game.getCurrentRoom().getItem("Chest");
//                for(Item itemChest : chest.getItems()){
//                    /*
//                     * Container, getItems
//                     */
//                    game.getCurrentRoom().addItem(itemChest);
//                    game.getBackPack().remove(this.name);
//                    item.setDescription("Chest opened, Items in it are in the room, look around");
//                    item.setName("Opened cest");
//                    System.out.println(this.name +" used");
//                    return game.getGameState();
//                }
//            }
//            
//        }
        for(Item item : items){
            //item instanceof Chest 
            //String classItem =item.getClass().getInterfaces()[0].toString().toLowerCase();
            //getInterfaces, getSuperClass!!
            if(item instanceof Chest){
                chest = (Chest) game.getCurrentRoom().getItem(item.getName());
                    /*
                     * Container, getItems
                     */
//                    game.getCurrentRoom().addItem(itemChest);
                    if(chest.openedChest()){
                        System.out.println("Chest already opened");
                        chest.closeChest();
                    }    
                    else{
                        chest.openChest();
                        System.out.println(item.getName()+" opened");
                        game.getBackPack().remove(this.getName());
                        game.getCurrentRoom().removeItem(this);
                        //game.getCurrentRoom().addItem(itemChest);
                    }   
                    System.out.println(this.getName() +" used");
                    return game.getGameState();
                }
            }
        System.out.println("You can't use the "+this.getName() +" object here");
        return game.getGameState();
    }

    @Override
    public List <Item> getItems() {
        throw new UnsupportedOperationException("Not possible here "); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addItem(Item item) {
        throw new UnsupportedOperationException("Not possible here"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeItem(Item item) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

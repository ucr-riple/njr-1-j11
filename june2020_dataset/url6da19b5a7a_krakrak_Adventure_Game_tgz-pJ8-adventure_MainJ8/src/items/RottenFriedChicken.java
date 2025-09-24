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
public class RottenFriedChicken extends AbstractItemMovable{

    public RottenFriedChicken(){
        super("Rotten Fried Chicken", "Just a rotten fried chicken", "rot chick");
    }
    @Override
    public List<Item> getItems() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public GameState use(Game game) {
        System.out.println("Your backpack capacity has been decreased by 2");
        game.getBackPack().setCapacity(-2);
        game.getBackPack().remove(this.getName());
        return game.getGameState();
    }

    @Override
    public void addItem(Item item) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeItem(Item item) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

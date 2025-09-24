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
public class CompanionsRing extends AbstractItemMovable{

    public CompanionsRing(){
        super("Companions Ring", "A ring with the Companions sign", "Co ri");
    }
    @Override
    public List<Item> getItems() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public GameState use(Game game) {
        System.out.println("This ring has no specific use, just shows you are part of the Companions");
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

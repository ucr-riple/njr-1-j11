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
public class Scroll extends AbstractItemMovable{

    public Scroll(int num){
        super("Scroll"+num, "A scroll that has that Companions sign again","scro"+num);
    }
    @Override
    public List<Item> getItems() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public GameState use(Game game) {
        System.out.println("Welcome to the start of the Companion's entry ritual, show your valor "
                + "\n and you shall become one of the people's Companion");
        System.out.println("The paper burns in your hand");
        game.getCurrentRoom().removeItem(this);
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

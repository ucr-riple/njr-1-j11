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
public class WuuthradFragment extends AbstractItemMovable{

    public WuuthradFragment(){
        super("Wuuthrad Fragment", "A fragment of wuuthrad axe of the Companions", "frag");
        
    }
    @Override
    public List<Item> getItems() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public GameState use(Game game) {
        if(game.getCurrentRoom().getNpc("Kodlak Whitemane") != null){
            game.getBackPack().remove(this.getName());
        }
        else {
            System.out.println("You can't use this object here");
        }
        
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

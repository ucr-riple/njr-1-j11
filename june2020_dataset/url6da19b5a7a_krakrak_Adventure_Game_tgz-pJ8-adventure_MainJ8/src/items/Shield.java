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
public class Shield extends AbstractWeapon {
    
    public Shield(){
        super("Shield", "This shield is for Aela","Shie");
    }

    @Override
    public List<Item> getItems() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public GameState use(Game game) {
        if(game.getCurrentRoom().getName().toLowerCase().contains("cave")){
            game.getBackPack().remove(this.getName());
            System.out.println("Shield given to Aela");
        }else {
            System.out.println("You can't use this object, it is not yours");
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

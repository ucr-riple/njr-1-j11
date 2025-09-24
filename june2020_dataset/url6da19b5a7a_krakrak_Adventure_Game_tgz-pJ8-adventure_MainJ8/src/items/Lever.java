/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package items;

import commands.Game;
import commands.GameState;
import java.util.List;
import rooms.*;

/**
 *
 * @author Irakiza Krak
 */
public class Lever extends AbstractItemUseable{

    
    public Lever (){
        super("Lever", "Spell book that contains an unlock spell", "le");
    }
    
    @Override
    public List<Item> getItems() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public GameState use(Game game) {
       
       if(game.getCurrentRoom() instanceof Cave || game.getCurrentRoom() instanceof LockedRoom){
                game.getCurrentRoom().activate(game, game.getCurrentRoom().getSecretExit());
                System.out.println("The way to "+game.getCurrentRoom().getSecretExit().getName()+ " has been activated look around");
                game.getCurrentRoom().removeItem(this);
       }else{
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

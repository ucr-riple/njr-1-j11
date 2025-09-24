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
public class ScrollOfSigning extends AbstractItemMovable{

    public ScrollOfSigning(){
        super("Scroll of Passation", "Given by the Companion's leaders to their successors", "pass");
    }
    @Override
    public List<Item> getItems() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public GameState use(Game game) {
        GameState gameState = GameState.SOLVED;
        game.setCurrentRoom(game.getCurrentRoom().getSecretExit());
        game.getCurrentRoom().show();
        game.setGameState(gameState.name());
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

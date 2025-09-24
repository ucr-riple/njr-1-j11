/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package items;

import commands.Game;
import commands.GameState;
import java.util.List;
import rooms.LockedRoom;
import rooms.MagicRoom;

/**
 *
 * @author Irakiza Krak
 */
public class UnlockSpellBookMagic extends AbstractItemMovable{
    
    private String spell;
    
    public UnlockSpellBookMagic (){
        super("Magic Book", "Spell book that contains an unlock spell", "ma b");
        this.spell = "unlockmagic";
    }
    
    
    
    public String getSpell(){
        return this.spell;
    }
    
    public void setSpell(String spellSet){
        this.spell= spellSet;
    }
    @Override
    public List<Item> getItems() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public GameState use(Game game) {
       
       if(game.getCurrentRoom() instanceof MagicRoom){
                game.getCurrentRoom().activate(game, game.getCurrentRoom().getSecretExit());
                System.out.println("The way to "+game.getCurrentRoom().getSecretExit().getName()+ " has been activated look around");
                game.getCurrentRoom().removeItem(this);
                game.getBackPack().remove(this.getName());
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
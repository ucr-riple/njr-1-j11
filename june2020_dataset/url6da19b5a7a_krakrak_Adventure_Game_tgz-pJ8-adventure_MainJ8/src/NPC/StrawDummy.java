/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package NPC;

import commands.Game;
import commands.GameState;
import items.Item;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Irakiza Krak
 */
public class StrawDummy extends AbstractNpcKillable{
    
    private List <Item> items = new ArrayList();
    
    public StrawDummy(){
        super("Straw Dummy", "Straw Dummy for training", "","Straw Dum", 20);
    }
    
    @Override
    public List <Item> getItems() {
        return this.items;
    }

    @Override
    public GameState action(Game game) {
        //Still to adapt, checking the end of game
        System.out.println(this.getStatement());
        System.out.println("Straw dummy don't speak !! If it was a real creature you'll be dead \n"
                + "never do this again");
        return game.getGameState();
    }

    @Override
    public void addItem(Item item) {
        this.items.add(item);
    }
}

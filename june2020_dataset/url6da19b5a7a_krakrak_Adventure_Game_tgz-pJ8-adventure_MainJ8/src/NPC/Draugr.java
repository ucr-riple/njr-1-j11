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
public class Draugr extends AbstractNpcKillable1{
    
    private List <Item> items = new ArrayList();
    
    public Draugr(){
        super("Draugr","An tall skeleton, fearsome and has shiny blue eyes","KKKRRRKKRKRKRKRKRKRKRKRKRKKRKRKRKRKRKRKRKKR","Drg", 70);
    }
   
    @Override
    public List <Item> getItems() {
        return this.items;
    }

    @Override
    public GameState action(Game game) {
        //Still to adapt, checking the end of game
        System.out.println(this.getStatement());
        System.out.println(this.getName()+" don't speak, therefore you die with ignorance, next time hit them with a sword or other weapon");
        game.setGameState(GameState.GAMEOVER.name());
        return game.getGameState();
    }

    @Override
    public void addItem(Item item) {
        this.items.add(item);
    }

}

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
public class Skeleton extends AbstractNpcKillable {
    
    private List <Item> items = new ArrayList();
    
    public Skeleton(){
        super("Skeleton","An eerie Skeleton that came from no where","KKKRRRKKRKRKRKRKRKRKRKRKRKKRKRKRKRKRKRKRKKR","Ske", 30);
    }
   
    @Override
    public List <Item> getItems() {
        return this.items;
    }

    @Override
    public GameState action(Game game) {
        //Still to adapt, checking the end of game
        System.out.println(this.getStatement());
        System.out.println("Skeletons don't speak, therefore you die with ignorance, next time hit them with a sword or other weapon");
        game.setGameState(GameState.GAMEOVER.name());
        return game.getGameState();
    }

    @Override
    public void addItem(Item item) {
        this.items.add(item);
    }

}

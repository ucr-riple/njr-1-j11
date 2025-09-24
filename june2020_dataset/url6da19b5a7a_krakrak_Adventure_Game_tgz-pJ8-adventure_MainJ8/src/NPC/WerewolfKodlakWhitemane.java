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
public class WerewolfKodlakWhitemane extends AbstractNpcKillable3{
     private List <Item> items = new ArrayList();
    public WerewolfKodlakWhitemane(){
        super("Transformed Kodlak Whitemane","One can hardly recognize Kodlak in this form \n He seems determined to keep his leadership","OOOOOUUUUUUUUUUUUUUUUHHHHHHHHHHHHHHOOOOUUUUHHHH","Tr Ko", 130);
    }
    
    @Override
    public List <Item> getItems() {
        return this.items;
    }
    
    @Override
    public GameState action(Game game) {
        //Still to adapt, checking the end of game
        System.out.println(this.getStatement());
        System.out.println("Werewolves don't speak !");
        return game.getGameState();
    }
    
    @Override
    public void addItem(Item item) {
        this.items.add(item);
    }
}

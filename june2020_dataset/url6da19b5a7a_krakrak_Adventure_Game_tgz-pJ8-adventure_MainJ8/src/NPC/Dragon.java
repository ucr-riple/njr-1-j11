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
public class Dragon extends AbstractNpcKillable3{
   
    
    private List <Item> items = new ArrayList();
    
    public Dragon(){
        super("Dragon","It's a dragon sedemhlavy definitely not shaped so that it would be just like a friend to me","RRRRAAAAAAAAAAAAAAAAAAWWWWWWWWWWWWWWWRRRRRRRRRRRRRRRRR",
                "Dra", 120);
    }
   
    @Override
    public List <Item> getItems() {
        return this.items;
    }

    @Override
    public GameState action(Game game) {
        //Still to adapt, checking the end of game
        System.out.println(this.getStatement());
        System.out.println("Don't speak to "+this.getName()+", they kill you without warning");
        game.setGameState(GameState.GAMEOVER.name());
        return game.getGameState();
    }

    @Override
    public void addItem(Item item) {
        this.items.add(item);
    }

   

    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package NPC;

import commands.Game;
import commands.GameState;

/**
 *
 * @author Irakiza Krak
 */
public class Samoth extends AbstractNpc{
    
    public Samoth(){
        super("Samoth","A strange man, in a black long hood despite the hot weather",
                "\"Mmmhhhhhhhhhhhhhh...... \n \n I must warn you young man \n dark clouds are coming \n Watch out up there. You should prepare your mind for what's ahead..... \n Mmmmmmmmhhh.....\""
                , "Sa");
    }

    @Override
    public GameState action(Game game) {
        System.out.println(this.getStatement());
        game.getBackPack().setCapacity(3);
        System.out.println("Samoth just increased the capacity of your backpack");
        return game.getGameState();
    }
    
}

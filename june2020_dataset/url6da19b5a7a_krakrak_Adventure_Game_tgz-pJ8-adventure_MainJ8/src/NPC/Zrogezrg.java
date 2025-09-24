/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package NPC;

import commands.Game;
import commands.GameState;
import items.*;

/**
 *
 * @author Irakiza Krak
 */
public class Zrogezrg extends AbstractNpc{

    
    public Zrogezrg(){
        super("Zrogezrg","The receptionist of the Slovak paradise, weird name he has","\"Welcome to the starting point of the Slovak paradise \n "
                + "As we are at the beginning of Spring, water is very high on some paths, you should take care and I hope you have good shoes \"",
                "Zro");
    }
    
    @Override
    public GameState action(Game game) {
        System.out.println(this.getStatement());
        System.out.println("The guy with a name you can't even pronounce gave you something, check your backpack");
        game.getBackPack().add(new BlackKey());
        return game.getGameState();
    }

}

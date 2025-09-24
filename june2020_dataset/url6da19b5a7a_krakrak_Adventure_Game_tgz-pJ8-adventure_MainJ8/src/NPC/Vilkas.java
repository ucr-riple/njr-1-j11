/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package NPC;

import commands.Game;
import commands.GameState;
import items.WoodenSword;

/**
 *
 * @author Irakiza Krak
 */
public class Vilkas extends AbstractNpc{
    
    public Vilkas(){
        super("Vilkas", "Middle aged man, sharp eyes a little bit scary", "....", "Vi");
    }
    
    @Override
    public GameState action (Game game){
        if(game.getCurrentRoom().getName().contains("Roklina")){
            System.out.println(this.getStatement() + "You know what I think about this ritual Kodlak... Hmmm Very easy !");
        }
        else if(game.getCurrentRoom().getName().equalsIgnoreCase("Cingov")){
            System.out.println(this.getStatement() + "Follow me to Kosiamy Briezok");
        }
        else if(game.getCurrentRoom().getName().equalsIgnoreCase("Kosiamy Briezok")){
            System.out.println("I should say, you suprised me little boy");
        }
        else if(game.getCurrentRoom().getName().equalsIgnoreCase("Skjor's lair 2")){
            System.out.println("I'm suprised that you came this far. This is your day");
        }
        else {
            System.out.println("I'm here to train you, show me what you got and we will see after");
            game.getBackPack().add(new WoodenSword());
        }
        return game.getGameState();
        
    }
    
}

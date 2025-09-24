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
public class Farkas extends AbstractNpc{
 
    public Farkas(){
        super("Farkas","Short and muscular man with a dark sign on his surcoat", "What are you looking at little boy? You're not supposed to be here!", "Fa");
        
    }
    @Override
    public GameState action(Game game){
        if(game.getCurrentRoom().getName().equals("Hrdlo Hornadu")){
            System.out.println(this.getStatement());
        }else if(game.getCurrentRoom().getName().equalsIgnoreCase("Kosiamy Briezok")){
            this.setStatement("I will be always loayal to you ! I will lend you my strength and will");
            System.out.println(this.getStatement());
        }
        else {
            System.out.println("I'm here to help you");
        }
        return game.getGameState();
    }
}

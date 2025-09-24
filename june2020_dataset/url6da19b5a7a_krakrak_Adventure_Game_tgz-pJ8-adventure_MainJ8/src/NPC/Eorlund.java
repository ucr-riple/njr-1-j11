/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package NPC;

import commands.Game;
import commands.GameState;
import items.Shield;
import items.IronCompanionsSword;
import items.SteelCompanionsSword;

/**
 *
 * @author Irakiza Krak
 */
public class Eorlund extends AbstractNpc{
    
    public Eorlund(){
        super("Eorlung Grey-Mane","Dark skin old man with a long white beard and barechest", "I've been forging all type of weapons for the Companions since I"
                + "was old enough to do it", "Eor");
        
    }
    @Override
    public GameState action(Game game){
        if(game.getCurrentRoom().getName().equalsIgnoreCase("Kosiamy Briezok")){
            System.out.println("First of all congratulations");
            System.out.println("Find me in " + game.getCurrentRoom().getNorth().getName() +" and get your reward");
        }else if(game.getCurrentRoom().getName().equalsIgnoreCase("Medvedia Hlava")) {
            System.out.println("Again congratulations :");
            System.out.println("These will make you an accomplished Companion");
            game.getBackPack().add(new SteelCompanionsSword());
            game.getCurrentRoom().removeNpcs(this);
        }else {
            System.out.println(this.getStatement());
            game.getBackPack().add(new IronCompanionsSword());
            game.getBackPack().add(new Shield());
            System.out.println("Here, take this shield and give it to the person you'll find in that entrance down there");
            game.getCurrentRoom().activate(game, game.getCurrentRoom().getSecretExit());
           
        }
         return game.getGameState();
    }
}

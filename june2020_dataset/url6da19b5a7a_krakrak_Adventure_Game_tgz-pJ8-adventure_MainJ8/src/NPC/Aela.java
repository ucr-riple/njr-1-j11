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
public class Aela extends AbstractNpc{
    
    public Aela(){
        super("Aela","A woman refreshing before going to Slovak paradise the second day","\"Hi, It looks like it is a nice day to hike in the slovak paradise \n Hope you'll enjoy \n Have a nice day \"",
                "Ae");
    }
    
    public Aela (String ndescr){
        super("Aela","In her Companion's outfit, she seems less friendly"," \" So we meet again, I hope your day was fine. It's not over yet... \"",
                "Ae");
    }
    
    public Aela(String ndescr, String ndes){
        super("Aela", "Back to normal after transformation", "Thank you for bringing me back", "Ae");
    }
    
    @Override
    public GameState action(Game game) {
        if(game.getCurrentRoom().getName().contains("Hrabusice")){
            System.out.println(this.getStatement());
        }
        else if(game.getCurrentRoom().getName().toLowerCase().contains("cavelet")){
           System.out.println(this.getStatement());
           System.out.println("We the Companions were given this power to be able to protect"
                  + "humans from the creatures underneath. Evil for Evil, so we must be like them sometimes to be able to defeat some creatures");
        }else if(game.getCurrentRoom().getName().equalsIgnoreCase("Kosiamy Briezok")){
            System.out.println("Congratulations, I knew you were special from the first time I set eyes on you");
        }
        else if(game.getCurrentRoom().getName().equalsIgnoreCase("Skjor's lair 2")){
            System.out.println("So, we meet again. But this time, we intend \n"
                    + "making you one of us");
        }
        else{
            System.out.println("Give me the shield, we need to move quickly (Use the command \"use \")");
        }
        return game.getGameState();
    }
    
}

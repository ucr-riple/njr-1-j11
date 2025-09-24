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
public class Ria extends AbstractNpc{
    
    public Ria(){
        super("Ria","Tall and elegant even wearing those battle clothes, warm smile, welcoming unlike her fellows", "Hey you! If you are here there must be a reason \n"
                + "So I can tell you what we do here and maybe give you some explanations about this corpse"
                + "\n We are what we call the Companions, we protect hikers and people living here from the dark creatures underneath \n"
                + "this paradise \n \n Actually we need hands, this place is normally inaccessible to normal people, so that Samoth must have done his way \n"
                + "That's how everybody here was recruited. Ready to join us ?", "Ria");
        
    }
    @Override
    public GameState action(Game game){
        System.out.println(this.getStatement());
        System.out.println("You decide to join them, "+this.getName()+" tells you to go find a man called Kodlak Whitemane");
        return game.getGameState();
    }
}

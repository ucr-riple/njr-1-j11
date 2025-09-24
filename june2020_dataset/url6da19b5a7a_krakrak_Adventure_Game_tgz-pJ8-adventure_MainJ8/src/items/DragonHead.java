/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package items;

import NPC.Npc;
import NPC.WerewolfKodlakWhitemane;
import commands.Game;
import commands.GameState;
import java.util.List;

/**
 *
 * @author Irakiza Krak
 */
public class DragonHead extends AbstractItemMovable{
    
    public DragonHead(){
        super("Dragon head", "Head of the recently killed dragon, allows you to defy the leader of the Companions \n"
                + "If you have the Diamond Companion's Sword", "head");
    }

    @Override
    public List <Item> getItems() {
        throw new UnsupportedOperationException("Not possible here."); //To change body of generated methods, choose Tools | Templates.
    }
    //To DO!!!
    @Override
    public GameState use(Game game) {
        if(game.getCurrentRoom().getName().equalsIgnoreCase("Skjor's lair 2") && game.getCurrentRoom().getNpc("Kodlak Whitemane") != null) {
            System.out.println("Kodlak Whitemane, you who has lost the Diamond Companion's Sword I defy you \n"
                    + "Like states the companion's law : he/she who finds a Diamond Companion's Sword has the right to defy the leader \n "
                    + "of the Companion's \n"
                    + "Since he is not supposed to lose this sword during his leadership \n"
                    + "Therefore, he has to prove his bravery to the one who found the sword \n"
                    + "And prove that he can go on with his duty \n"
                    + "Otherwise, the one who finds the sword become's the new leader \n \n");
            System.out.println(" \t Kodlak Whitemane, angry, transforms himself into a werewolf....");
            Npc kodlak = new WerewolfKodlakWhitemane();
            kodlak.addItem(new ScrollOfSigning());
            game.getCurrentRoom().addNpc(kodlak);
            game.getCurrentRoom().removeNpcs(game.getCurrentRoom().getNpc("Kodlak Whitemane"));
        }else {
            System.out.println("You can't use the "+this.getName()+ " here");
        }
        return game.getGameState();
    }

    @Override
    public void addItem(Item item) {
        throw new UnsupportedOperationException("Not possible here"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeItem(Item item) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

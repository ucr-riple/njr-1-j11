/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package items;

import NPC.Kill;
import NPC.Killable;
import NPC.Killable1;
import NPC.Killable2;
import NPC.Npc;
import commands.Game;
import commands.GameState;
import commands.History;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Irakiza Krak
 */
public class SteelCompanionsSword extends AbstractWeapon {
    
    private Random rdm = new Random();
    private Double removeNbLives;
    private int nbLivesRemain;
    
    public SteelCompanionsSword(){
        super("Steel Companion's Sword", "Large Steel two-handed sword. Typically a sword for an accomplishe Companion", "st s");
    }

    @Override
    public GameState use(Game game) {
        List <Npc> npcs;
        npcs = game.getCurrentRoom().getNpcs();
        Kill killablenpc = null;
        
        for(Npc creature : npcs){
            if(creature instanceof Killable1 || creature instanceof Killable || creature instanceof Killable2){
                killablenpc = (Kill) creature;               
            }
            
        }
        if(killablenpc != null){
            if (!History.getHistory().isLoaded()){
                nbLivesRemain = killablenpc.getNbLives();
                if(nbLivesRemain > 0 ){
                    removeNbLives = rdm.nextDouble()*(100/nbLivesRemain) + 13;
                    killablenpc.setNbLives(nbLivesRemain - removeNbLives.intValue());
                    System.out.println(killablenpc.getStatement());
                    if(killablenpc.getStatement().length() > removeNbLives)
                        killablenpc.setStatement(killablenpc.getStatement().substring(killablenpc.getStatement().length()- removeNbLives.intValue(), killablenpc.getStatement().length()));
                    System.out.println("The "+killablenpc.getName()+" has "+killablenpc.getNbLives() +" lives \n "
                        + "You removed "+removeNbLives.intValue() + " lives");
                    if(killablenpc.getNbLives() <= 0){
                        System.out.println("FINISH THE  "+killablenpc.getName().toUpperCase() +"!!");  
                    }
                }
                else {
                    System.out.println("You overcame "+killablenpc.getName());
                    for(Item reward : killablenpc.getItems()){
                        game.getCurrentRoom().addItem(reward);
                        System.out.println("The "+killablenpc.getName()+ " dropped something look around");
                    }
                    game.getCurrentRoom().removeNpcs(killablenpc);
                }
            }
            else {
                System.out.println("You overcame "+killablenpc.getName());
                for(Item reward : killablenpc.getItems()){
                    game.getCurrentRoom().addItem(reward);
                    System.out.println("The "+killablenpc.getName()+ " dropped something look around");
                }
                game.getCurrentRoom().removeNpcs(killablenpc);
            }
            return game.getGameState();
        }else{
           System.out.println("You can't use this object here");
           return game.getGameState(); 
        }
    }


    @Override
    public void addItem(Item item) {
        throw new UnsupportedOperationException("Not possible here."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List <Item> getItems() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeItem(Item item) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

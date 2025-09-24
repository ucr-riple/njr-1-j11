/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package items;

import NPC.Killable;
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
public class WoodenSword extends AbstractItemMovable{
    private int nbLivesRemain;
    private Random rdm = new Random();
    private Double removeNbLives;

    public WoodenSword(){
        super("Wooden Sword", "wooden sword for training", "Wo sw");
    }
    @Override
    public List<Item> getItems() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public GameState use(Game game) {
        List <Item> tempweap = null;
        List <Npc> npcs;
        npcs = game.getCurrentRoom().getNpcs();
        Killable killablenpc = null;
        
        for(Npc creature : npcs){
            if(creature instanceof Killable){
                killablenpc = (Killable) creature;               
            }
            
        }
        if(killablenpc != null){
            if (!History.getHistory().isLoaded()){
                nbLivesRemain = killablenpc.getNbLives();
                if(nbLivesRemain > 0 ){
                    removeNbLives = rdm.nextDouble()*(100/nbLivesRemain) + 2;
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
                    }
                    game.getCurrentRoom().removeNpcs(killablenpc);
                    System.out.println("Vilkas gave you the companion's sword to go sharpen it at the forge Letanovce");
                    game.getBackPack().remove(this.getName());
                    
                }
            }
            else {
                System.out.println("You overcame "+killablenpc.getName());
                for(Item reward : killablenpc.getItems()){
                    game.getCurrentRoom().addItem(reward);
                    System.out.println("The "+killablenpc.getName()+ " dropped something look around");
                }
                game.getCurrentRoom().removeNpcs(killablenpc);
                game.getBackPack().remove(this.getName());
            }
            return game.getGameState();
        }else{
           System.out.println("You can't use this object here");
           return game.getGameState(); 
        }
    }

    @Override
    public void addItem(Item item) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeItem(Item item) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

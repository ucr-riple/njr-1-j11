/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package commands;

import NPC.Npc;
import java.util.List;

/**
 * Talking to NPC to have information or objects
 * @author Irakiza Krak
 */
public class Talk extends AbstractCommand{
    private boolean contains;
    
    public Talk(){
        super("TALK", "Allows you to talk to other characters and sometimes, you might get lucky", "ta");
    }
    /**
     * Verifies that the person in parameters is in the room
     * And then might execute the use method of the NPC
     * @param game
     * @param params 
     */
    @Override
    public void execute(Game game, String params) {
        List <Npc> npcs;
        npcs = game.getCurrentRoom().getNpcs();
        Npc npc = null;
//        List <Item> backItems;
//        backItems = game.getBackPack().getItems();
        
        //Trying to compare the npcs in the room to a keyword contained in the parameters
        for(Npc charac : npcs){
            if(params.toLowerCase().contains(charac.getName().toLowerCase()) || params.toLowerCase().contains(charac.getShortC().toLowerCase())){
                contains =true;
                npc = charac;
            }
        }
        
        if(contains && npc != null){
            //System.out.println(npc.getClass().getSuperclass().getInterfaces()[0].toString());
            if(npc instanceof Npc){
                System.out.println(npc.getName() + " Says : ");
                game.setGameState(npc.action(game).name());
                History hist = History.getHistory();
                hist.add(params);
            }else{
                System.out.println("This one can't talk");
            }
        }else{
            System.out.println("This person is not in the room");
        }
    }
}
    

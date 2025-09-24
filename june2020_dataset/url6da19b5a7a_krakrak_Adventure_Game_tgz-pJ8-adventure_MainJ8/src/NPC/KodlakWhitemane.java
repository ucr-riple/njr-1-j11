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
public class KodlakWhitemane extends AbstractNpc{
    
    public KodlakWhitemane(){
        super("Kodlak Whitemane", "Old and tall man with long beard, very charismatic, makes you feel very confident yet filled with repsect for him"
                ,"Welcome young man. Long time no see, come over here...","Ko Whi");
    }
    
    @Override
    public GameState action(Game game){
        GameState gameState = GameState.SOLVED;
        System.out.println(this.getStatement());
        if(game.getBackPack().getItem("Companions Ring") != null ){
            System.out.println(" \"Welcome to the Companions more missions awaiting for you \n"
                    + "Before you become a real Companion"
                    + "Here show me these ring \n");
            System.out.println("He looks at the "+game.getBackPack().getItem("Companions Ring").getName()+" then puts it on your finger \n"
                    + "Go my son, may the force be with you");
            game.getCurrentRoom().activate(game, game.getCurrentRoom().getSecretExit());
            game.getBackPack().remove("Companions Ring");
        }else if(game.getCurrentRoom().getName().equalsIgnoreCase("Klastorka Roklina")){
            System.out.println("I think you forgot something in there son, go and get it");
        }else if(game.getCurrentRoom().getName().equalsIgnoreCase("Kosiamy Briezok")){
            if(game.getBackPack().getItem("Wuuthrad Fragment") != null){
                System.out.println("Hand me the wuuthrad fragment (Use the command \"use\" on the fragment)");
                System.out.println("I would like to welcome definitely our friend here to our brotherhood. Welcome this fully accomplished Companion among us \n"
                        + "You can now go and ask Eorlund a new real Companion sword for your future missions");
            }
        }else{
            System.out.println("Finally here you are. Today we are honered to welcome you in the restraint counsel. \n"
                    + "Therefore, we will give you this special power that most of as have. Now you can turn into a werewolf"
                    + "whenever you need the power");
            game.setCurrentRoom(game.getCurrentRoom().getSecretExit());
            game.getCurrentRoom().show();
            game.setGameState(gameState.name());
        }
        return game.getGameState();
    }
    
}

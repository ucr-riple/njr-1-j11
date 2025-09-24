/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package commands;

import java.util.List;

/**
 *
 * @author Irakiza Krak
 */
public class Load extends AbstractCommand{
    
    public Load(){
        super("LOAD", "Loads a game you previously saved, the name of the saving file in the parameters of this command is compulsory", "load");
    }
    @Override
    public void execute(Game game, String params) {
        String param;
        System.out.println("Loading saved game...");
        if(params.length() > 5)
            param = params.substring(5, params.length());
        else 
            param = "file";
        History.getHistory().clear();
        List <String> command = History.getHistory().load(param);
        for(String cmd : command){
            game.processCommand(cmd);
        }
        System.out.println("Game loaded successfully !");
        History.getHistory().setLoaded();
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package commands;

/**
 *
 * @author Irakiza Krak
 */
public class Save extends AbstractCommand{

    
    public Save(){
        super("SAVE","Saves the game to the specified by the parameters, if not specified saves the game to default file","sa"  );
    }
    @Override
    public void execute(Game game, String params) {
        String param;
        System.out.println("Saving...");
        if(params.length() > 4)
            param = params.substring(5, params.length());
        else
            param = "file";
        History hist;
        hist = History.getHistory();
        hist.save(param);
    }
    
}

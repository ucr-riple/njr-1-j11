/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package commands;
import java.util.List;
import java.util.ArrayList;

/**
 * Adds all the commands in the game to an array, so that they can be used
 * @author Irakiza Krak
 */
public class ParserImpl  implements Parser{
    
    private List <Command> command = new ArrayList();
    
    //Method getCommand, String type parameter and return type command
    public ParserImpl(){
        command.add(new Quit());
        command.add(new Version());
        command.add(new Commands());
        command.add(new East());
        command.add(new West());
        command.add(new North());
        command.add(new South());
        command.add(new Look());
        command.add(new Examine()); //Parameter here doesn't matter, just to add the command to the parser
        command.add(new Pick());
        command.add(new Drop());
        command.add(new Inventory());
        command.add(new Use());
        command.add(new Talk());
        command.add(new Save());
        command.add(new Load());
    }
    
    @Override
    public Command getCommand(String com1){
        for(Command cmd : command){
            if (com1.toLowerCase().contains(cmd.getName().toLowerCase()) || com1.toLowerCase().contains(cmd.getShortC().toLowerCase())){
                    return (Command) cmd;
                }
        }
            return null;
    }

}

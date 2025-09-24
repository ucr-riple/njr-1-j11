/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package commands;

import java.util.ArrayList;
import java.util.List;

/**
 * Command commands that gives all the commands available at that moment in the game
 * @author Irakiza Krak
 */
public class Commands extends AbstractCommand{
    //Creation of list of commands, ask if it is possible to get it from ParserImpl
    //Instead of Creating a new one (method not practicle if long project)
    private List <Command> commands = new  ArrayList();
    
    //Variables (the same)
    /**
     * Class constructor that adds commands to an array
     */
    public Commands() {
        //Instatiation of variables
        super("COMMANDS", "Displays the list of commands", "cmd");
        
        //Adding the available commands to the list of commands
        commands.add(new Quit());
        commands.add(new Version());
        commands.add(new East());
        commands.add(new West());
        commands.add(new North());
        commands.add(new South());
        commands.add(new Look());
        commands.add(new Examine()); //Parameter here doesn't matter, just to add the command to the parser
        commands.add(new Pick());
        commands.add(new Drop());
        commands.add(new Inventory());
        commands.add(new Use());
        commands.add(new Talk());
        commands.add(new Save());
        commands.add(new Load());
        
    }

    /**
     *
     * @param game actual game
     * @param params String type parameters
     * @see commands.Command
     */
    @Override
    public void execute(Game game, String params) {
        System.out.println("List of Commands (shortcuts in brackets) :");
        for(Command cmd : commands){
            System.out.println(cmd.getName()+"("+cmd.getShortC()+") - "+cmd.getDescription());
        }
        System.out.println(this.getName()+"("+this.getShortC()+") - "+this.getShortC());
    }
    /**
     * Permits to add commands in special situations
     * @param name 
     */
    public void addCommands(Command name){
        if(!commands.contains(name)){
            commands.add(name);
        }
    }
}


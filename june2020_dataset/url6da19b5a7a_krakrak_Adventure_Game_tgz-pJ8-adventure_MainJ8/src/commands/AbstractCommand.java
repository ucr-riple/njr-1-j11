/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package commands;

/**
 * Abstract class regrouping all commands
 * @author Irakiza Krak
 */
public abstract class AbstractCommand implements Command{
    private String name;
    private String descr;
    private String shortC;
    
    public AbstractCommand(String nname, String ndescr, String nshortC){
        this.name = nname;
        this.descr = ndescr;
        this.shortC = nshortC;
    }
    /**
     * Gives the name of the command
     * @return type String 
     */
    @Override
    public String getName(){
        return this.name;
    }
    /*
     * Check git book
     */
    /**
     * Gives the description of the command
     * @return type String
     */
    @Override
    public String getDescription(){
        return this.descr;
    }
    
    @Override
    public String getShortC(){
        return this.shortC;
    }
    
}

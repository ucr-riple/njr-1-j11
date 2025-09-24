/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package NPC;

import commands.Game;
import commands.GameState;
import items.Item;
import java.util.List;

/**
 *
 * @author Irakiza Krak
 */
public abstract class AbstractNpcKillable implements Killable{
    
    private String name;
    private String descr;
    private String statement;
    private String shortC;
    private int nbLives = 30;
    
    public AbstractNpcKillable(String nname, String ndescr, String nstatement, String nshortC, int nNbLives){
        this.name = nname;
        this.descr=ndescr;
        this.statement = nstatement;
        this.shortC = nshortC;
        this.nbLives =nNbLives;
    }

    @Override
    public void setName(String named) {
        this.name = named;
    }

    @Override
    public void setDescription(String description) {
        this.descr = description;
    }

    @Override
    public List <Item> getItems() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public GameState action(Game game) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addItem(Item item) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return this.descr;
    }

    /**
     * Sets the number of lives left to the NPC
     * @param nb
     */

    @Override
    public void setStatement(String state) {
        this.statement = state;
    }
    
    @Override
    public String getStatement(){
        return this.statement;
    }
    @Override
    public String getShortC() {
        return this.shortC;
    }
    
     @Override
    public void setNbLives(int nb) {
        this.nbLives = nb;
    }

    @Override
    public int getNbLives() {
        return this.nbLives;
    }
    
}

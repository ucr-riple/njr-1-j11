/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package items;

import commands.Game;
import commands.GameState;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Irakiza Krak
 */
public class ChestStateContext implements ChestChangeState, Chest{

    private String name = "Chest";
    private String descr = "Normal chest";
    private String shortC = "Che";
    private List <Item> chestInventory = new ArrayList();
    
    private final OpenedChest openedChest = new OpenedChest();
    private final ClosedChest closedChest = new ClosedChest();
    
    private ChestStatelike state = closedChest;
   
    @Override
    public void setState(ChestStatelike setstate) {
        state = setstate;
    }

    @Override
    public void openChest() {
        this.setName("Opened " + name);
        this.setDescription(descr + "(open)");
        state.openChest(this, "Opened" + name, descr, "op "+ shortC);
        
    }

    @Override
    public void closeChest() {
        this.setName("Closed " + name);
        this.setDescription(descr);
        state.closeChest(this, name, descr, shortC);
        
    }

    @Override
    public boolean openedChest() {
        return (state instanceof OpenedChest);
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
        return this.chestInventory;
    }

    @Override
    public GameState use(Game game) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addItem(Item item) {
        this.chestInventory.add(item);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return this.descr;
    }

    @Override
    public String getShortC() {
        return this.shortC;
    }

    @Override
    public ChestStatelike getState() {
        return this.state;
    }

    @Override
    public ChestStatelike getClosedChest() {
        return this.closedChest;
    }

    /**
     *
     * @return type Chest
     */
    @Override
    public ChestStatelike getOpenedChest() {
        return this.openedChest;
    }
    
    @Override
    public void removeItem(Item item){
        this.chestInventory.remove(item);
    }

    @Override
    public void setShortC(String shortc) {
        this.shortC = shortc;
    }
    
    
}

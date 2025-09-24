/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package items;

/**
 * Abstract class of normal Items
 * @author Irakiza Krak
 */
public abstract class AbstractItem implements Item{
    private String name;
    private String descr;
    private String shortC;
    
    public AbstractItem(String nname, String ndescr, String nshortC){
        this.name = nname;
        this.descr = ndescr;
        this.shortC = nshortC;
    }
    @Override
    public String toString(){
        return this.name +" - "+this.descr;
        
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
    public void setShortC(String shortc){
        this.shortC = shortc;
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
    public String getShortC(){
        return this.shortC;
    }
    
    
}

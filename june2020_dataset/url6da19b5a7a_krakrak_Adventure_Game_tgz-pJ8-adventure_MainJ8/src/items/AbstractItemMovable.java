/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package items;

/**
 * Abstract class of moveable items
 * @author Irakiza Krak
 */
public abstract class AbstractItemMovable implements Useable, Moveable{
    private String name;
    private String descr;
    private String shortC;
    private int weight;
    
    public AbstractItemMovable(String nname, String ndescr, String nshortC){
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
        name = named;
    }

    @Override
    public void setDescription(String description) {
        descr = description;
    }
    
    @Override
    public void setShortC(String shortc){
        this.shortC = shortc;
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return descr; 
    }
    
    @Override
    public String getShortC(){
        return shortC;
    }
    
}

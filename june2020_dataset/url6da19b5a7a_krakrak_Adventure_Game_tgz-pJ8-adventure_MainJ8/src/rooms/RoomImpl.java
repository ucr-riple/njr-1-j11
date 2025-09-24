/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rooms;

import NPC.Npc;
import commands.Game;
import items.Item;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the Interface Room
 * @author Irakiza Krak
 */
public abstract class RoomImpl implements Room {
    
    private Room north;
    private Room south;
    private Room east;
    private Room west;
    private Room secretExit;
    private String name;
    private String descr;
    private String shortC;
    //Counter for the creation of rooms with the description only
    private int counter;
    
    public RoomImpl(String nname, String ndescr){
        this.name = nname;
        this.descr = ndescr;
    }
    private List <Item> items = new ArrayList();
    private List <Npc> npcs = new ArrayList();
    
    

    @Override
    public void addItem(Item item) {
        this.items.add(item);
    }
    //getItem, returns a reference to the object if it exists in the room
    //If it doesn't exist in the room, returns null
    @Override
    public Item getItem(String name) {
        for(Item object : items){
            if(name.toLowerCase().contains(object.getName().toLowerCase())){
                return (Item) object;
            }
            else{
                //System.out.println("There is no " + name + " in this room");
            }
        }
        return null;
    }
    
    @Override
    public List getItems(){
        return this.items;
    }

    //Removes the Item from the room
    @Override
    public void removeItem(Item item) {
        this.items.remove(item);
    }

    @Override
    public void setDescription(String descr) {
        this.descr = descr;
    }

    @Override
    public void setExits(Room north, Room south, Room east, Room west) {
         this.setEast(east);
         this.setSouth(south);
         this.setWest(west);
         this.setNorth(north);
    }

    @Override
    public void show() {
        System.out.println(this.getName() + " \n " + this.getDescr());
        System.out.println("Possible exits are : ");
        if(this.getEast()!= null){
            System.out.println(" \t EAST : "+this.getEast().getName());
        }
        if(this.getWest()!= null){
           System.out.println("\t WEST : "+this.getWest().getName());
        }
        if(this.getNorth()!= null){
            System.out.println("\t NORTH : " +this.getNorth().getName());
        }
        if(this.getSouth()!= null){
            System.out.println("\t SOUTH : " +this.getSouth().getName());
        }
        if(!items.isEmpty()){
            System.out.println("In "+ this.getName() +" you can see: ");
            for(Item object : items){
                System.out.println("\t " + object.getName()+"("+object.getShortC()+")");
            }
            System.out.println("*****************************");
        }else {
            System.out.println("You don't see anything special");
            System.out.println("*****************************");
        }
        if(!npcs.isEmpty()){
            System.out.println("People present "+ this.getName() + " are :");
            for(Npc chara : npcs){
                System.out.println("\t " + chara.getName() +"(" +chara.getShortC()+")");
            }
        }else{
            System.out.println("There is nobody in "+ this.getName());
            System.out.println("*****************************");
        }
        
    }


    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return this.getDescr();
    }

    /**
     * @param north the north to set
     */
    @Override
    public void setNorth(Room north) {
        this.north = north;
    }

    /**
     * @param south the south to set
     */
    @Override
    public void setSouth(Room south) {
        this.south = south;
    }

    /**
     * @param east the east to set
     */
    @Override
    public void setEast(Room east) {
        this.east = east;
    }

    /**
     * @param west the west to set
     */
    @Override
    public void setWest(Room west) {
        this.west = west;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the descr
     */
    public String getDescr() {
        return descr;
    }

    /**
     * @param descr the descr to set
     */
    public void setDescr(String descr) {
        this.descr = descr;
    }

    /**
     * @return the counter
     */
    public int getCounter() {
        return counter;
    }

    /**
     * @param counter the counter to set
     */
    public void setCounter(int counter) {
        this.counter = counter;
    }

    /**
     * @return the north
     */
    @Override
    public Room getNorth() {
        return north;
    }

    /**
     * @return the south
     */
    @Override
    public Room getSouth() {
        return south;
    }

    /**
     * @return the east
     */
    @Override
    public Room getEast() {
        return east;
    }

    /**
     * @return the west
     */
    @Override
    public Room getWest() {
        return west;
    }

    @Override
    public String getShortC() {
        return this.shortC;
    }

    @Override
    public void addNpc(Npc npc) {
        this.npcs.add(npc);
    }

    @Override
    public Npc getNpc(String name) {
        for(Npc chara : npcs){
            if(name.contains(chara.getName())){
                return (Npc) chara;
            }
            else{
                System.out.println(name + " doesn't exist in this room");
            }
        }
        return null;
    }

    @Override
    public List getNpcs() {
        return this.npcs;
    }
    
    @Override
    public void removeNpcs(Npc npc){
        this.npcs.remove(npc);
    }
    
    @Override
    public void activate(Game game,Room exit){
        
    }
    
    @Override
    public void setSecretExit(Game game, Room exit){
        this.secretExit = exit;
    }
    
    @Override
    public Room getSecretExit(){
        return this.secretExit;
    }
}

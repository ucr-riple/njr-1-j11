/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rooms;

import commands.Game;

/**
 *
 * @author Irakiza Krak
 */
public class Cave extends RoomImpl{

    public Cave(String name, String descr){
        super(name, descr);
    }

    @Override
    public void activate(Game game, Room exit) {
        this.setExits(null, null, game.getCurrentRoom().getEast(), exit);
    }
    
}

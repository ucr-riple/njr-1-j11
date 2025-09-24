package edu.umw.cpsc.collegediversity;

import ec.util.*;

/**
 * A group of otherwise disconnected {@link Room}s, affiliated only because
 * {@link Student}s have a greater chance of encountering others in the
 * same dorm than they would otherwise.
 */
public class Dorm extends Group {

    private boolean femaleOnly;
    private boolean freshmanOnly;
    private Room[] rooms;
    private String name;


    /**
     * Constructor for new Dorm objects.
     * @param name the name of this Dorm, which should be unique across all 
     * dorms (though this is not enforced.)
     * @param femaleOnly does this dorm have only female rooms? 
     * (<code>false</code> means it has both male-only rooms and female-only 
     * rooms.)
     * @param freshmanOnly is this dorm intended to allow only freshman
     * occupants?
     * @param numRooms the number of rooms in the dorm.
     */
    public Dorm(String name, boolean femaleOnly, boolean freshmanOnly, 
        int numRooms){

        this.name = name;
        this.femaleOnly = femaleOnly;
        this.freshmanOnly = freshmanOnly;

        rooms = new Room[numRooms];

        for(int i=0; i<numRooms; i++){
            rooms[i]= new Room(this, i, femaleOnly);
        }
    }

    /**
     * Return true only if every room in this dorm is at max occupancy.
     */
    public boolean isFull(){
        for(int x=0; x<rooms.length; x++){
            if(!rooms[x].isFull()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Return a human-readable version of this object, for debugging.
     */
    public String toString() { return name; }

    /**
     * Return the number of {@link Room}s in this dorm.
     */
    public int getNumRooms() {
        return rooms.length;
    }

    /**
     * Return a room in this dorm by <i>index</i> number. (<i>not</i>
     * roomNum). 
     * @throws IndexArrayOutOfBoundsException if there aren't that many
     * rooms, or the passed index is negative.
     */
    public Room getRoomByIndex(int i) {
        return rooms[i];
    }

    /**
     * Return whether or not this dorm is intended to house only female
     * students in all of its rooms.
     */
    public boolean isFemaleOnly() {
        return femaleOnly;
    }

    /**
     * Rid all rooms in this dorm of their occupants, which <i>does</i> 
     * inform each {@link Student} object that they no longer have a room.
     */
    public void emptyAllRooms() {
        for (Room r : rooms) {
            r.empty();
        }
    }
}

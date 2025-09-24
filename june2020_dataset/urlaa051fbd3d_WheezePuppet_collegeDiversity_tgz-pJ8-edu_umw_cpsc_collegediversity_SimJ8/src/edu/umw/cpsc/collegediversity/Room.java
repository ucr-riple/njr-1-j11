package edu.umw.cpsc.collegediversity;

import java.util.*;
import sim.util.*;

/**
 * A temporary housing location for students. Rooms are each part of a
 * {@link Dorm}, and presently hold exactly two occupants. Each year, a
 * Room object will be given different {@link Student}s as occupants.
 */
public class Room {

    private Dorm dorm;
    private int roomNum;
    private int occupancy = Sim.DORM_ROOM_SIZE;
    private Bag occupants;
    private boolean femaleRoom;


    /**
     * Constructor for new Room objects.
     * @param dorm the enclosing dorm room.
     * @param roomNum the number of this room, which should be unique
     * within its enclosing dorm (though this is not enforced).
     * @param femaleRoom does this room allow only female occupants? If
     * false, it allows only <i>male</i> occupants. (Mixed-gender rooms do
     * not exist.)
     */
    public Room(Dorm dorm, int roomNum, boolean femaleRoom){

        occupants = new Bag();

        this.dorm = dorm;
        this.roomNum = roomNum;
        this.femaleRoom = femaleRoom;
    }

    /**
     * Return the occupants of this room.
     */
    public Bag getOccupants(){
        return occupants;
    }

    /**
     * Return the enclosing dorm of this room.
     */
    public Dorm getDorm(){
        return dorm;
    }

    /**
     * Does this room currently have maximum occupancy?
     */
    public boolean isFull(){
        return (occupants.size()==occupancy);
    }

    /**
     * Does this room currently have no assigned students?
     */
    public boolean isEmpty(){
        return (occupants.size()==0);
    }

    /**
     * Rid this room of occupants, which <i>does</i> inform the 
     * {@link Student} objects that they no longer have a room.
     */
    public void empty(){
        while (occupants.size() > 0) {
            ((Student)occupants.get(0)).leaveRoom();
        }
    }

    /**
     * Assign the <i>two</i> {@link Student} objects passed to this room.
     * This <i>does</i> inform the Student objects that they now are
     * assigned to this room. <b>Note</b> that race and gender are not
     * checked here! TODO
     * @throws IllegalArgumentException (sic) if there is not enough room
     * for both occupants. In this case, <i>neither</i> will be assigned.
     */
    public void addResidents(Student s,Student r){
        if(occupants.size()>0){
            throw new IllegalArgumentException("cannot assign two roommates to this room");
        }else{
            s.setRoom(this);
            r.setRoom(this);
        }
    }

    /**
     * Assign the {@link Student} object passed to this room.
     * This <i>does</i> inform the Student object that it now is assigned
     * to this room.
     * @throws IllegalArgumentException (sic) if the room is already at
     * maximum occupancy, or the student is the wrong race or gender.
     * (message string in exception gives details.)
     */
    public void addResident(Student s){

        if(occupants.size()==occupancy){
            throw new IllegalArgumentException("room full");
        }
        //if room empty
        if(occupants.size()==0){
            //if person is correct race to be in this room (dual minority 
            //  rooms must have 2 minorities)
            s.setRoom(this);
            if(s.getGender()==Student.Gender.MALE){
                femaleRoom=false;
            }else{
                femaleRoom=true;
            }
        }else if(occupants.size()<occupancy){

            //room has only one out of two residents
            
            //if the person is the correct gender for this room
            if((s.getGender()==Student.Gender.FEMALE && femaleRoom)
                ||
                (s.getGender()==Student.Gender.MALE && !femaleRoom)){
                s.setRoom(this);
            }
        }else{
            throw new IllegalArgumentException(
                "resident not correct gender for this room");
        }
    }


    /**
     * Return a human-readable version of this object, for debugging.
     */
    public String toString() {
        return "Room " + roomNum + " in " + dorm;
    }

    void addOccupant(Student s) {
        occupants.add(s);
    }

    void removeOccupant(Student s) {
        if (occupants.contains(s)) {
            occupants.remove(s);
        }
    }

    /**
     * Return the number of students currently assigned to this room.
     */
    public int getNumOccupants() {
        return occupants.size();
    }
}

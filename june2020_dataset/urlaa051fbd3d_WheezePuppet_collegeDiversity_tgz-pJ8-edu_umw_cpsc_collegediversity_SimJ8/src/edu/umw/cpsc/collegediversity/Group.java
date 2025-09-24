package edu.umw.cpsc.collegediversity;

import java.util.*;
import ec.util.*;


/**
 * Any venue in which students will have a chance to encounter each other
 * more often than just randomly.
 */
public class Group{

    private static int nextId = 0;
    private int id; 
    protected Hashtable<Student,Membership> members;


    /**
     * Default constructor for new Group objects.
     */
    public Group(){
        id=nextId++;
        members = new Hashtable<Student,Membership>();
    }

    /**
     * Return the integer ID of this group, globally unique across all
     * groups.
     */
    public int getId(){
        return id;
    }

    /**
     * Return the Membership object associated with this group and the
     * Student passed. If the student is not a member of this group, this
     * method will return null.
     */
    public Membership getMembership(Student s){
        return members.get(s);
    }

    /**
     * Return an enumeration over all {@link Student} objects who are members
     * of this group.
     */
    public Enumeration<Student> getMembers() {
        return members.keys();
    }


    void setId(int Id){
        id=Id;
    }

    /**
     * (This method currently does nothing, as Groups are not scheduled.)
     */
    public void step(){

    }

    /**
     * Attempt to add a member to this group.
     * @throws GroupFullException if this group already has its maximum
     * number of members.
     */
    public void addMember(Student s) throws GroupFullException {
        Membership m = new Membership(s, this);
        members.put(s, m);
        s.joinGroup(this);
    }

    /**
     * Remove the student passed from this group's list of members. NOTE:
     * this does <i>not</i> remove this group from the student's list of
     * groups! That must be done separately.
     */
    public void removeMember(Student s){
        members.remove(s);
    }

    /**
     * Remove all members from this group, which <i>does</i> inform each
     * {@link Student} that they are no longer a member of this group.
     */
    public void empty(){
        Enumeration keys = members.keys();
        while( keys.hasMoreElements() ) {
            Student s = (Student) keys.nextElement();
            members.remove(s);
            s.leaveGroup(this);
        }
    }
}

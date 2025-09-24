package edu.umw.cpsc.collegediversity;

import java.util.*;
import ec.util.*;

/** 
 * A Membership is an object that represents one student's participation
 * in some kind of group. Different groups have different levels of
 * importance to a student; hence a Membership has a strength associated
 * with it, where low values indicate less importance to the student than
 * high values. The maximum strength is 1. A strength of 0 is still
 * "stronger" than not having a membership at all with a particular group,
 * however.
 */ 
public class Membership{

    private double strength; 
    private Student student;
    private Group group;

    /**
     * Constructor for a new Membership with random strength. The strength
     * will be uniformly distributed from 0 to 1.
     * <br/>
     * If a membership already exists between the given student and group,
     * this will create a "duplicate" membership (probably an error.)
     */
    public Membership(Student student, Group group){

        this.student = student;
        this.group = group;
        strength = Sim.instance().random.nextDouble();
    }

    /**
     * Return the "strength" of this membership, on a scale of 0 (lowest
     * importance) to 1 (highest).
     */
    public double getStrength(){
        return strength;
    }

}

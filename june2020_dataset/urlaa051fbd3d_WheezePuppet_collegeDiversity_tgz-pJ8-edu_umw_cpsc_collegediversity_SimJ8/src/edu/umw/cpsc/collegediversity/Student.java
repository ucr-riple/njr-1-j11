package edu.umw.cpsc.collegediversity;

import java.util.*;
import sim.engine.*;
import sim.field.continuous.*;
import sim.util.*;
import sim.field.network.*;
import ec.util.*;
import java.io.*;

/**
 * A college student currently enrolled at the University. Student objects
 * are instantiated by the {@link FreshmanFactory} when they matriculate,
 * and remain in existence until they graduate or dropout.
 */
public class Student implements Steppable{

    /**
     * Type for gender variables.
     */
    public enum Gender { 
        MALE { public String toString() { return "male"; } }, 
        FEMALE { public String toString() { return "female"; } }
    };

    /**
     * Type for race variables. Currently all minorities are lumped
     * together into a single category.
     */
    public enum Race { 
        WHITE { public String toString() { return "white"; } }, 
        MINORITY { public String toString() { return "minority"; } }
    };

    // Somewhat arbitrary numbers for scaling. These indicate
    // approximately the lowest and highest similarities empirically
    // observed between students in the simulation.
    private static final double MIN_SIMILARITY = -6.0;
    private static final double MAX_SIMILARITY = 43.0;

    private static int nextId = 1;

    private int id; 
    private Gender gender;
    private int grade; //school year. 1=freshman, 2=sophomore, etc.
    private double gpa; 
    private Race race;
    private Room dormRoom;

    // The weights that *this student* places on various features in
    // perceiving similarity. (Currently, these are all simply drawn from
    // the global values in Sim.)
    private double weightGrade;
    private double weightGpa;
    private double weightRace;

    private ArrayList<Group> groups;
    private ArrayList<Student> connections;
    private ArrayList<Integer> attributes;

    // People who this student has previously met (and may or may not have
    // formed a connection with.)
    private ArrayList<Student> met;


    // Should only be instantiated by FreshmanFactory.
    Student(Gender gender, int grade, double gpa, Race race){

        synchronized (Student.class) {
            id=nextId++;
        }

        this.gender=gender;
        this.grade=grade;
        this.gpa=gpa;
        this.race=race;

        weightGrade=Sim.GLOBAL_WEIGHT_GRADE;
        weightGpa=Sim.GLOBAL_WEIGHT_GPA;
        weightRace=Sim.GLOBAL_WEIGHT_RACE;

        groups = new ArrayList<Group>();
        connections = new ArrayList<Student>();
        attributes = new ArrayList<Integer>();
        met = new ArrayList<Student>();
    }

    /**
     * Return the ID for this student (each student has a unique integer ID.)
     */
    public int getId(){
        return id;
    }

    /**
     * Return this student's gender.
     */
    public Gender getGender(){
        return gender;
    }

    /**
     * Return this student's year in school: 1=freshman, 2=sophomore, etc.
     */
    public int getGrade(){
        return grade;
    }

    /**
     * Return this student's GPA (on a 4.0 scale).
     */
    public double getGpa(){
        return gpa;
    }

    /**
     * Return this student's race.
     */
    public Race getRace(){
        return race;
    }

    private void setId(int Id){
        id=Id;
    }

    private void setGrade(int grade){
        this.grade=grade;
    }

    private void setGpa(double gpa){
        this.gpa=gpa;
    }

    private void setRace(Race race){
        this.race=race;
    }

    /**
     * Assign this student to a new dorm room. This method <i>will</i>
     * inform the corresponding {@link Room} object that it has the new
     * occupant, and if this student previously had a room, that Room
     * object will be told that it no longer has the occupant.
     */
    public void setRoom(Room room){
        if (room == null) {
            //System.out.println("** Student #" + id + " leaving their room.");
        }

        if (dormRoom != null) {
            dormRoom.removeOccupant(this);
        }
        dormRoom = room;
        dormRoom.addOccupant(this);
    }

    // Add attribute number "i" to this student. Should only be done at
    // instantiation time, from FreshmanFactory.
    void addAttribute(int i){
        attributes.add(i);
    }

    private static final double SMALL = 1/1000.0;

    private boolean isSeptember(double time) {
        if (Math.abs(time - Math.floor(time) - 1/12.0) < SMALL) {
            return true;
        }
        return false;
    }

    private boolean isMay(double time) {
        if (Math.abs(time - Math.floor(time) - 9/12.0) < SMALL) {
            return true;
        }
        return false;
    }

    /**
     * Perform monthly activities for this student. In particular:
     * <ol>
     * <li>On Sept. 1st, assign a dorm room, classes (TODO), and, if 
     * freshman (TODO), an orientation group.</li>
     * <li>On Oct., Nov., Dec., Jan., Feb., Mar., Apr. 1st, possibly
     * encounter (and possibly form connections with) other students.</li>
     * <li>On May 1st., increment the academic year, and possibly drop out
     * or graduate. (Note that the {@link StatsPrinter} will run on April
     * 30th, just before this promotion activity, to dump out stats for the
     * just-now-ending academic year's students.)</li>
     * <li>On Jun., Jul., Aug. 1st, do nothing (when performing year-end
     * activities on May 1st, this object will be scheduled for four months
     * away, on Sept. 1st, instead of one month away).</li>
     * </ol>
     */
    public void step(SimState state){
        if (isSeptember(state.schedule.getTime())) {
            performYearBeginningActivities();
        } else if (isMay(state.schedule.getTime())) {
            performYearEndingActivities();
        } else {
            performMonthlyActivities();
        }
    }

    private void performYearBeginningActivities() {

        //Dorm encounters - student has a chance to connect with every 
        // single person in the dorm (not realistic)
        Dorm d = dormRoom.getDorm();
        for(int x=0;x<d.getNumRooms();x++){
            if(!(d.getRoomByIndex(x)==this.dormRoom)){
                Bag o=d.getRoomByIndex(x).getOccupants();
                for(int y=0;y<o.size();y++){
                    Student s = (Student) o.get(y);
                    encounter(s);
                }
            }
            //Automatically adds roommate as a connection
            if(d.getRoomByIndex(x)==this.dormRoom){
                Bag o=d.getRoomByIndex(x).getOccupants();
                for(int y=0;y<o.size();y++){
                    Student s = (Student) o.get(y);
                    if(this!=s){
                        this.connections.add(s);
                    }
                }
            }
        }
        //Group (includes O-group) encounters (to be completed)
        for(int x=0;x<groups.size();x++){
            Enumeration keys = groups.get(x).getMembers();
            while( keys.hasMoreElements() ) {
                Student s = (Student) keys.nextElement();
                if(this!=s && !(connections.contains(s))){
                    encounter(s);
                }
            }
        }

        // Schedule again for next month.
        Sim.instance().schedule.scheduleOnceIn(1/12.0,this);
    }

    private void performYearEndingActivities() {
        this.grade++;
        if(!graduateOrDropout()){
            // Skip ahead four months and schedule again for Sept. 1st 
            //   (start of fall semester but after Aug 1st schedule point.)
            Sim.instance().schedule.scheduleOnceIn(4/12.0,this);
        }
    }

    private void performMonthlyActivities(){

        if (Sim.instance().getNumStudents() - met.size() <
            Sim.NUM_ANNUAL_RANDOM_ENCOUNTERS/8) {
            // There's not enough people left to meet! Get outta here.
            return;
        }

        //Random encounters
        for(int x=0;x<(Sim.NUM_ANNUAL_RANDOM_ENCOUNTERS/8);x++){
            Student meetMe = Sim.instance().getRandomStudent();
            if(this!=meetMe && !(met.contains(meetMe)) && 
                !(connections.contains(meetMe))){

                encounter(meetMe);
            }else{x--;}
        }

        // Schedule again for next month.
        Sim.instance().schedule.scheduleOnceIn(1/12.0,this);
    }

    private void encounter(Student s2){

        if (!met.contains(s2)) {
            met.add(s2);
            s2.met.add(this);
        }

        double similarity = similarityTo(s2) - MIN_SIMILARITY;

        double probConnection = 
            (Sim.HIGH_PROB_RANDOM_CONNECTION-Sim.LOW_PROB_RANDOM_CONNECTION) *
                (similarity/MAX_SIMILARITY) + Sim.LOW_PROB_RANDOM_CONNECTION;

        if(probConnection>Sim.HIGH_PROB_RANDOM_CONNECTION){
            probConnection=Sim.HIGH_PROB_RANDOM_CONNECTION;
        }

        if(probConnection<Sim.LOW_PROB_RANDOM_CONNECTION){
            probConnection=Sim.LOW_PROB_RANDOM_CONNECTION;
        }

        double formConnection = Sim.instance().random.nextDouble();

        if(formConnection < probConnection){
            connections.add(s2);
            s2.connections.add(this);
        }
    }


    /**
     * Compute the perceived similarity (from this student's point of view)
     * of this student to the student passed. This may differ from the
     * other students' point of view because the two students may weigh
     * race, grade-in-school, and/or GPA differently.
     * <br/>
     * There are no guaranteed bounds on the return value from this method; 
     * higher values simply indicate greater similarity than lower values.
     */
    public double similarityTo(Student s){
        double score=0;
        if(this.race==s.race){
            score += 1*weightRace;
        }
        score -= Math.abs(grade-s.grade)*weightGrade; //subtract class standing difference from score
        score -= Math.abs(gpa-s.gpa)*weightGpa; //subtract the difference of gpas from score

        //add similarity for mutual attributes
        int numCommonAttr=0;
        for(Integer a:attributes){
            if(s.attributes.contains(a)){
                numCommonAttr +=1; 
                //make weight attributes
            }
        }
        score+=(numCommonAttr*numCommonAttr);
        //add similarity for any mutual groups
        for(Group group : groups){
            if (s.groups.contains(group)){
                Membership m = group.getMembership(this);
                if(m==null){

                }else{
                    score+=.1*m.getStrength();
                }
                //make ^ .1 a variable
            }
        }
        //make up hypothetical survey numbers and incorporate into similarity
        //avg # connections 
        return score;
    }

    /**
     * Compute this student's perceived similarity to the students in the
     * group that is passed.
     */
    public double similarityTo(Group g){
        return 0.0;
    }

    // Does this student currently have a dorm room assigned? This can be
    // false in the midst of the kerfuffle with assigning dorm rooms at the
    // start of each year.
    boolean hasRoom(){
        return dormRoom!=null;

    }

    void leaveRoom(){
        if (dormRoom != null) {
            dormRoom.removeOccupant(this);
        }
        dormRoom=null;
    }

    /**
     * Add the group passed to this student's set of groups. This does
     * <i>not</i> inform the {@link Group} object that it has a new member.
     */
    public void joinGroup(Group g){
        groups.add(g);
    }

    /**
     * Remove the group passed from this student's set of groups. This does
     * <i>not</i> inform the {@link Group} object that it no longer has this
     * member.
     * <br/>
     * If the student is not a member of the group, this method does
     * nothing.
     */
    public void leaveGroup(Group g){
        groups.remove(g);
    }

    void removeConnection(Student s){
        if(connections.contains(s)){
            //connections.remove(s);
            s.connections.remove(this);
        }
    }

    private boolean graduateOrDropout() {
        // remove from the Sim's bag
        // remove from all friends' connections
        // remove from all groups, etc.,
        if(grade==5){
            //graduate

            for(Group group : groups){
                group.removeMember(this);
            }
            groups.clear();
            for(Student m : met){
                this.removeConnection(m);
            }
            for(Student c : connections){
                this.removeConnection(c);
            }
            Sim.instance().removeMe(this);
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Return a snapshot of this student's state suitable for sending to an 
     * output file to collect statistics.
     */
    public String toString() {
        return id + ","+race + "," + grade + "," + connections.size();
    }

    /**
     * Return the dorm room this student is currently assigned to, which
     * may be null at some points in the midst of the yearly reassignment
     * kerfuffle.
     */
    public Room getDormRoom() {
        return dormRoom;
    }
}

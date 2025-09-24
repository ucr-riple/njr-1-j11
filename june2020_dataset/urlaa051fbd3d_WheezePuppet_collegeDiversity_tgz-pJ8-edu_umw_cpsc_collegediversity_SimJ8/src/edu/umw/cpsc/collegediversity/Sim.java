package edu.umw.cpsc.collegediversity;

import sim.engine.*;
import sim.util.*;
import sim.field.continuous.*;
import sim.field.network.*;
import java.io.*;

/**
 * A simulation of evolving racial diversity on a college campus.
 * Notes on Scheduling:
 * <ul>
 * <li>The time for this simulation is in <i>years</i>. An integer value
 * of time represents <i>August 1st</i> (beginning of school year). For
 * example, if the current time step is 0.5, that represents Feb 1st,
 * 2014.)</li>
 * <li>The {@link FreshmanFactory} runs once every August 1st,
 * instantiating new Student objects to represent that year's crop of
 * freshmen.</li>
 * <li>{@link Student}s are scheduled to run on the first of every month. 
 * (They're scheduled by the {@link FreshmanFactory} to run the first Sept., 
 * and then each month they schedule themselves again for following month;
 * see {@link Student#step} for details.)</li>
 * <li>The {@link StatsPrinter} object will run every April 30th, which is
 * after Students' last activity of the year but before they promote
 * themselves (and/or dropout or graduate) on May 1st.</li>
 * <li>This object itself will run once per year, on June 1st, promoting all
 * students who haven't dropped out, and assigning them to next year's
 * dorms.</li>
 * </ul>
 */
public class Sim extends SimState implements Steppable {

    /**
     * The number of years the simulation will run.
     */
    public final static int NUM_SIM_YEARS = 5;

    /**
     * The number of new freshmen entering the University each year.
     */
    public final static int FRESHMAN_CLASS_SIZE = 1000;

    /**
     * The number of students put together in each Orientation Group.
     */
    public final static int OGROUP_SIZE = 12;

    /**
     * The (constant) number of students in an academic course.
     */
    public final static int CLASS_SIZE = 20;

    /**
     * The number of occupants in each dorm room.
     */
    public final static int DORM_ROOM_SIZE = 2;

    /**
     * The probability that a newly instantiated student will be a
     * minority.
     */
    public final static double PROB_MINORITY = .2;

    /**
     * The number of different "attributes" a student may or may not
     * possess.
     */ 
    public final static int NUM_PERSONALITY_ATTRIBUTES = 100;

    /**
     * The probability that any particular student has any particular
     * personality attribute.
     */ 
    public final static double PROB_ATTRIBUTE = 0.05;

    /**
     * Should the simulation take race into consideration when assigning
     * freshman dorm rooms?
     */
    public static boolean HOUSING_BY_RACE = false;

    /**
     * If {@link #HOUSING_BY_RACE} is true, the probability that a minority
     * student will be assigned a minority roommate. (Strictly speaking
     * this isn't quite true, since if minority student A passes this dice
     * roll, and is assigned minority student B as a roommate, then student
     * B is already allocated a roommate (student A) and therefore won't
     * face this dice roll.)
     */
    public static final double PROB_DUAL_MINORITY=.4;

    /**
     * The name of the output directory into which statistical files
     * will be written.
     */
    public static final String OUTPUT_DIRECTORY = "output";

    /**
     * How much does race matter when forming connections? This is a
     * multiplicative factor; values less than 1 progressively decrease the
     * significance of race in perceived similarity, and values greater
     * than 1 increase it. "Global" means it applies uniformly to all
     * students.
     */
    public static final double GLOBAL_WEIGHT_RACE = 1.0;

    /**
     * How much does GPA matter when forming connections? This is a
     * multiplicative factor; values less than 1 progressively decrease the
     * significance of GPA in perceived similarity, and values greater
     * than 1 increase it. "Global" means it applies uniformly to all
     * students.
     */
    public static final double GLOBAL_WEIGHT_GPA = 1.0;

    /** 
     * How much does grade (<i>i.e.</i>, year in school) matter when
     * forming connections? This is a multiplicative factor; values less 
     * than 1 progressively decrease the significance of grade in perceived
     * similarity, and values greater than 1 increase it. "Global" means it
     * applies uniformly to all students.
     */
    public static final double GLOBAL_WEIGHT_GRADE = 1.0;

    /**
     * The probability of two <i>completely dissimilar</i> students forming
     * a connection if they randomly encounter each other.
     */
    public static final double LOW_PROB_RANDOM_CONNECTION = .002; 

    /**
     * The probability of two <i>"perfectly" similar</i> students forming
     * a connection if they randomly encounter each other.
     */
    public static final double HIGH_PROB_RANDOM_CONNECTION = .006;

    /**
     * The number of "random" encounters (<i>i.e.</i>, outside the context
     * of any class, orientation group, or dorm) that a student makes per
     * year.
     */
    public static final int NUM_ANNUAL_RANDOM_ENCOUNTERS = 100;


    private static Sim theInstance;
    private int academicYear = 2013;

    private Bag enrolledStudents;
    private OrientationGroup[] oGroups;
    private Student[][] courseRoster;



    /**
     * Singleton pattern.
     */
    public synchronized static Sim instance(){
        if(theInstance==null){
            theInstance=new Sim();
        }
        return theInstance;
    }

    private Sim(){
        super(1);

        enrolledStudents = new Bag();

        oGroups = new OrientationGroup[
            (int)Math.ceil(((double)FRESHMAN_CLASS_SIZE)/OGROUP_SIZE)];

        int numCourses = (int)Math.ceil(
            (((double)FRESHMAN_CLASS_SIZE)*5)/CLASS_SIZE);

        courseRoster = new Student[CLASS_SIZE][numCourses];
    }

    /**
     * Add the newly-instantiated students (perhaps new freshmen) to the
     * simulation.
     */
    public void addStudents(Bag newStudents) {
        enrolledStudents.addAll(newStudents);
    }

    void removeMe(Student s) {
        enrolledStudents.remove(s);
    }

    /**
     * Run this simulation "headless"; <i>i.e.</i>, without a GUI.
     * <br/>
     * Command-line arguments:
     * <ol>
     * <li><b>housingByRace</b> -- either the word <code>true</code> or
     * <code>false</code>, depending on whether the simulation should take
     * race into consideration when assigning freshman dorm rooms.</li>
     * </ol>
     */
    public static void main(String args[]){

        doLoop(new MakesSimState() {
            public SimState newInstance(long seed, String[] args) {
                String housingArg;
                if (args.length > 0) {
                    housingArg = args[0];
                    System.out.println("args[0] = " + housingArg);
                    if(housingArg.equals("true")){
                        instance().HOUSING_BY_RACE = true;
                    }else{
                        instance().HOUSING_BY_RACE = false;
                    }
                }
                return instance();
            }
            public Class simulationClass() {
                return Sim.class;
            }
        }, args);

        System.exit(0);
    }

    private void createAndEmptyOutputDir() {
        File dir = new File(OUTPUT_DIRECTORY);
        if (!dir.exists()) {
            dir.mkdir();
        }
        String[] outputFiles;
        if(dir.isDirectory()){
            outputFiles = dir.list();  
            for (int i=0; i<outputFiles.length; i++) {  
                File outputFile = new File(dir, outputFiles[i]);   
                outputFile.delete();  
            } 
        }
    }

    /**
     * Begin the simulation, including everything necessary on the
     * schedule. See {@link Sim} class comments for scheduling particulars.
     */
    public void start(){
        super.start();

        //clear out all output files
        createAndEmptyOutputDir();

        //create O groups
        for(int x=0; x<oGroups.length; x++){
            OrientationGroup o=new OrientationGroup(OGROUP_SIZE);
            oGroups[x]=o;
        }
        
        // Schedule the FreshmanFactory to run each August 1st.
        schedule.scheduleOnce(Schedule.EPOCH + 1, FreshmanFactory.instance());

        // Schedule the StatsPrinter to run each April 30th.
        schedule.scheduleOnce(Schedule.EPOCH + 1 + 271/365.0, 
            StatsPrinter.instance());

        // Schedule this simulation object to run each June 1st, starting
        //  with *next* June.
        schedule.scheduleOnce(Schedule.EPOCH + 1 + 10/12.0,this);
    }

    /**
     * Perform year-end maintenance on the simulation. This will occur
     * every June 1st. In particular:
     * <ul>
     * <li>Assign all upperclassmen to dorms.</li>
     * <li>Increment the academic year.</li>
     * </ul>
     */
    public void step(SimState state) {
        System.out.println("Year "+academicYear+"-"+(academicYear+1) +
            " completed!!!");
        UpperclassHousingSelection.instance().assign(enrolledStudents);
        academicYear++;

        if (academicYear - 2013 >= NUM_SIM_YEARS) {
            System.exit(1);
        }

        state.schedule.scheduleOnceIn(1,this);
    }

    /**
     * Get the year in which the simulation's current academic year started.
     * For instance, if the current time step is 2.5, that means it's
     * currently Feb. 1st, 2016, and this method would return 2015.
     */
    public int getYear() {
        return academicYear;
    }

    /**
     * Return a uniformly chosen random student from the entire student
     * body.
     */
    public Student getRandomStudent() {
        return (Student) enrolledStudents.get(
            random.nextInt(enrolledStudents.size()));
    }

    /**
     * Return the number of students currently at the university
     * (matriculated, but neither graduated nor dropped out.)
     */
    public int getNumStudents() {
        return enrolledStudents.size();
    }

    Bag getStudents() {
        return enrolledStudents;
    }

    /**
     * Empty out all Orientation Groups (for instance, at the start of a
     * new academic year.)
     */
    public void clearOGroups() {
        for(int x=0; x<oGroups.length; x++){
            oGroups[x].empty();
        }
    }
    
    /**
     * Assign the student passed to one Orientation Group, subject to
     * assignment policies and constraints in place.
     */
    public void assignOGroup(Student s) {
        int o=0;
        boolean hasOGroup=false;
        while(o<oGroups.length && !hasOGroup){
            try{
                oGroups[o].addMember(s);
                hasOGroup=true;
            }catch(GroupFullException e){  /* permitted */}
            catch(Exception e2) {
                e2.printStackTrace();
            }
            o++;
        }
        if (!hasOGroup) {
            System.out.println(s + " not assigned to O-Group! :-O");
        }
    }
}

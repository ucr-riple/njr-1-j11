package edu.umw.cpsc.collegediversity;

import sim.engine.*;
import sim.util.Bag;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileWriter;

/**
 * A utility class to dump periodic statistical output about the
 * simulation.
 */
public class StatsPrinter implements Steppable {

    private static StatsPrinter theInstance;

    /**
     * Singleton pattern.
     */
    public static synchronized StatsPrinter instance() {
        if (theInstance == null) {
            theInstance = new StatsPrinter();
        }
        return theInstance;
    }

    private StatsPrinter() {
    }

    /**
     * Dump statistical output about the simulation at the end of every
     * academic year, immediately before students promote/dropout/graduate.
     * This will run every April 30th. In particular:
     * <ul>
     * <li>Create an output file with filename of the form
     * <code>year2023.csv</code>, where the number is one greater than the
     * current value returned from {@link Sim#getYear()}. This line will
     * contain a header line, then one line per active student in the 
     * academic year ending in that number. (For instance, the file just 
     * named would include all students enrolled during the 2022-2023 
     * academic year.) Each line will be of the following form:
     *     <p><code>243,white,2,239</code><p/>
     * which says "The student with ID 243, who was a white sophomore, had
     * 239 connections with other students." (See 
     * {@link Student#toString}).)</li>
     * </ul>
     */
    public void step(SimState state) {
        try{
            PrintWriter out = new PrintWriter(new FileWriter(
                Sim.OUTPUT_DIRECTORY + File.separator + "year" + 
                (Sim.instance().getYear()+1) + ".csv", true)); 
            out.println("\"id\",\"race\",\"grade\",\"numConn\"");
            Bag enrolledStudents = Sim.instance().getStudents();
            int numStudents = enrolledStudents.size();
            for (int i=0; i<numStudents; i++) {
                out.println(enrolledStudents.get(i)); 
            }
            out.close();
        }catch(java.io.IOException e){ e.printStackTrace();}

        state.schedule.scheduleOnceIn(1,this);
    }
}



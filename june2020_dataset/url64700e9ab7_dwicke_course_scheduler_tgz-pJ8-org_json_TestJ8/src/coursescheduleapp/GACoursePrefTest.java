/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package coursescheduleapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import org.junit.Test;
import static org.junit.Assert.*;
import coursescheduleapp.model.*;
/**
 *
 * @author Drew
 */
public class GACoursePrefTest {

    GACoursePref gaPref;
    CoursePref pref;
    EquivCourseSelection equiv;
    String prefInstruc = "TEST";

    CourseSelection select;
    Set<String> instructors;
    List<Course> courses;



    Map<String, Object> info;
    Map<String, Object> info2;
    ArrayList<String> ins;
    ArrayList<String> ins2;
    String day;
    String time;
    Integer CRN;

    public GACoursePrefTest() {
       pref = new CoursePref(1,1);

       courses = new ArrayList<Course>();


        ins = new ArrayList<String>();
        ins2 = new ArrayList<String>();
        ins.add("David Hovemeyer");
        ins.add("Gregory Link");
        ins2.add("David Babcock");

        day = "MWF";
        time = "3:00-4:00";
        CRN = 1231;

        info = new TreeMap<String,Object>();
        info.put(CourseData.TIME.toString(), time);
        info.put(CourseData.DAY.toString(), day);
        info.put(CourseData.INSTRUCTOR.toString(), ins);
        info.put(CourseData.CRN.toString(), CRN);


        info2 = new TreeMap<String,Object>();
        info2.put(CourseData.TIME.toString(), time);
        info2.put(CourseData.DAY.toString(), day);
        info2.put(CourseData.INSTRUCTOR.toString(), ins2);
        info2.put(CourseData.CRN.toString(), CRN);

        for (int i = 0; i < 4; i++)
        {
            courses.add(new Course(info));
        }

        courses.add(new Course(info2));

        instructors = new TreeSet<String>();
        instructors.add(ins.get(0));

        select = new CourseSelection(courses, instructors);


        pref.setCourseSelection(0, 0, select);

        gaPref = new GACoursePref(pref);



    }



    /**
     * Test of makeIndividual method, of class GACoursePref.
     */
    @Test
    public void testMakeIndividual() {
        System.out.println("makeIndividual");
        ArrayList<Integer> test = gaPref.makeIndividual();
    
        assertEquals(1, test.size());
        
        
        GACoursePref ga = new GACoursePref(new CoursePref(1,3));
        ArrayList<Integer> testblank = ga.makeIndividual();
        assertEquals(testblank.size(), 1);
        assertEquals(testblank.get(0).intValue(), -1);
    }

    /**
     * Test of decode method, of class GACoursePref.
     */
    @Test
    public void testDecode() {
        System.out.println("decode");
        ArrayList<Integer> t = new ArrayList<Integer>();
        t.add(1);

        Schedule ts = gaPref.decode(t);
        
        assertEquals(1, ts.getNumCourses());
       
        assertEquals(courses.get(1), ts.getCourses().get(0));
    }

    /**
     * Test of mutate method, of class GACoursePref.
     */
    @Test
    public void testMutate() {
        System.out.println("mutate");
        int ts = gaPref.mutate(0);
        System.out.println(ts);
        assertTrue(ts >=0 && ts < courses.size());
    }

}
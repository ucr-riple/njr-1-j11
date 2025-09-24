/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package coursescheduleapp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.junit.Test;
import static org.junit.Assert.*;
import coursescheduleapp.model.*;
/**
 *
 * @author Drew
 */
public class ScheduleTest {

    Schedule testSched;
    List<Course> courses;
    
    Map<String, Object> info;
    ArrayList<String> ins;
    String day;
    String time;
    Integer CRN;
    
    public ScheduleTest() {
        courses = new ArrayList<Course>();
        
        info = new TreeMap<String, Object>();
        
        ins = new ArrayList<String>();
        ins.add("David Hovemeyer");
        ins.add("David Babcock");

        day = "MWF";
        time = "3:00-4:00";
        CRN = 1231;

        info = new TreeMap<String,Object>();
        info.put(CourseData.TIME.toString(), time);
        info.put(CourseData.DAY.toString(), day);
        info.put(CourseData.INSTRUCTOR.toString(), ins);
        info.put(CourseData.CRN.toString(), CRN);
        
        
        for (int i = 0; i < 4; i++)
        {
            courses.add(new Course(info));
        }
        
        testSched = new Schedule(courses);
    }

    /**
     * Test of getNumCourses method, of class Schedule.
     */
    @Test
    public void testGetNumCourses() {
        System.out.println("getNumCourses");

        assertEquals(courses.size(), testSched.getNumCourses());
        
    }
   
    /**
     * Test of addCourse method, of class Schedule.
     */
    @Test
    public void testAddCourse() {
        System.out.println("addCourse");
        courses.add(new Course(info));
        testSched.addCourse(courses.get(courses.size() - 1));
        assertEquals(courses.size(), testSched.getNumCourses());
        
        
    }

    /**
     * Test of removeCourse method, of class Schedule.
     */
    @Test
    public void testRemoveCourse() {
        System.out.println("removeCourse");
        courses.add(new Course(info));
        testSched.addCourse(courses.get(courses.size() - 1));
        assertEquals(true, testSched.removeCourse(courses.get(courses.size() - 1)));
        courses.remove(courses.size() - 1);
        assertEquals(courses.size(), testSched.getNumCourses());

    }
    
    /**
     * Test of removeCourse by CRN method, of class Schedule.
     */
    @Test
    public void testRemoveCourseByCRN() {
        System.out.println("removeCourse By CRN");
        courses.add(new Course(info));
        testSched.addCourse(courses.get(courses.size() - 1));
        assertEquals(true, testSched.removeCourse(courses.get(courses.size() - 1).getValue(CourseData.CRN.toString())));
        courses.remove(courses.size() - 1);
        assertEquals(courses.size(), testSched.getNumCourses());

    }



    /**
     * Test of getCourses method, of class Schedule.
     */
    @Test
    public void testGetCourses() {
        System.out.println("getCourses");
        List<Course> schedCourses = testSched.getCourses();
        assertEquals(courses.size(), schedCourses.size());

        Iterator<Course> i = schedCourses.iterator();
        Iterator<Course> ci = courses.iterator();
        while(i.hasNext() && ci.hasNext())
        {
            assertEquals(ci.next(), i.next());
        }
    }

    /**
     * Test of setCourses method, of class Schedule.
     */
    @Test
    public void testSetCourses() {
        System.out.println("setCourses");

        courses.clear();
        for (int i = 0; i < 5; i++)
        {
            courses.add(new Course(info));
        }

       testGetCourses();

    }
    /**
     * Test of setCourses method, of class Schedule.
     */
    @Test
    public void testSetCoursesAll() {
    	Schedule blank = new Schedule();
    	blank.setCourses(courses);
    	assertEquals(blank.getNumCourses(), courses.size());
    }
    

}
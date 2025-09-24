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

import junit.framework.TestCase;

import org.junit.Test;

import coursescheduleapp.model.Course;
import coursescheduleapp.model.CourseData;
import coursescheduleapp.model.CourseSelection;

/**
 *
 * @author Drew
 */
public class CourseSelectionTest extends TestCase {

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

    public CourseSelectionTest() {

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




    }

   

    /**
     * Test of setCourse method, of class CourseSelection.
     */
    @Test
    public void testSetCourse() {
        System.out.println("setCourse");
        
        CourseSelection instance = new CourseSelection();
        assertEquals(0, instance.getNumCourses());

        instance.setCourse(courses);
        assertEquals(courses.size(), instance.getNumCourses());
    }

    /**
     * Test of getCourse method, of class CourseSelection.
     */
    @Test
    public void testGetCourse() {
        System.out.println("getCourse");
        int index = 0;
        Course course = select.getCourse(index);
        assertEquals(courses.get(index), course);

       assertEquals(null, select.getCourse(1222));
        
        assertEquals(true, select.getCourse(index).isHasPrefInstructor());
    }

    /**
     * Test of getNumCourses method, of class CourseSelection.
     */
    @Test
    public void testGetNumCourses() {
        System.out.println("getNumCourses");
        assertEquals(courses.size(), select.getNumCourses());
    }

    /**
     * Test of setInstructorPref method, of class CourseSelection.
     */
    @Test
    public void testSetInstructorPref() {

        instructors.clear();
        instructors.add(ins2.get(0));
        select.setInstructorPref(instructors);

        assertEquals(instructors.size(), select.getNumInstructorPref());
        
    }

     /**
     * Test of getInstrucPref method, of class CourseSelection.
     */
    @Test
    public void testGetInstrucPref() {
        System.out.println("getInstrucPref");
        assertEquals(instructors, select.getInstrucPref());
    }

    /**
     * Test of getNumInstructorPref method, of class CourseSelection.
     */
    @Test
    public void testGetNumInstructorPref() {
        System.out.println("getNumInstructorPref");
        assertEquals(instructors.size(), select.getNumInstructorPref());
    }

    /**
     * Test of addInstructorPref method, of class CourseSelection.
     */
    @Test
    public void testAddInstructorPref() {
        System.out.println("addInstructorPref");

        instructors.add(ins.get(0));
        select.addInstructorPref(ins.get(0));
        assertEquals(true, select.getInstrucPref().contains(ins.get(0)));


    }

    /**
     * Test of removeInstructorPref method, of class CourseSelection.
     */
    @Test
    public void testRemoveInstructorPref() {
        System.out.println("removeInstructorPref");

         int numInst = select.getNumInstructorPref();

        select.removeInstructorPref(ins.get(0));
        assertEquals(false, select.getInstrucPref().contains(ins.get(0)));

        assertEquals(numInst - 1, select.getNumInstructorPref());

    }

    

   /**
     * Test of removeCourse method, of class CourseSelection.
     */
    @Test
    public void testRemoveCourse_CRN() {
        System.out.println("removeCourse");
        select.removeCourse(courses.get(0).getValue(CourseData.CRN.toString()));
        courses.remove(courses.size() - 1);
        assertEquals(courses.size(), select.getNumCourses());
    }

    /**
     * Test of clearSelection method, of class CourseSelection.
     */
    @Test
    public void testClearSelection() {
        System.out.println("clearSelection");
        CourseSelection instance = new CourseSelection(courses, instructors);
        instance.clearSelection();
        assertTrue(instance.getNumCourses() == 0);
        assertTrue(instance.getNumInstructorPref() == 0);
    }
    
    @Test
    public void testHasCourse()
    {
    	assertTrue(select.hasCourse(courses.get(0)));
    }

    /**
     * Test of addCourse method, of class CourseSelection.
     */
    @Test
    public void testAddCourse() {
        System.out.println("addCourse");
        Course course = courses.get(0);
        CourseSelection instance = new CourseSelection();
        assertEquals(0, instance.getNumCourses());
        instance.addCourse(course);

        assertEquals(1, instance.getNumCourses());
        
    }

     /**
     * Test of removeCourse method, of class CourseSelection.
     */
    @Test
    public void testRemoveCourse_Course() {
        System.out.println("removeCourse");
        int numCourses = select.getNumCourses();
        assertEquals(courses.size(), numCourses);
        select.removeCourse(courses.get(0));

        courses.remove(courses.get(0));
        numCourses = select.getNumCourses();
        assertEquals(courses.size(), numCourses);
        
    }



}
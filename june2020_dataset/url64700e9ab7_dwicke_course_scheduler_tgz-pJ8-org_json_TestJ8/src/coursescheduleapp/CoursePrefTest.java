package coursescheduleapp;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

import coursescheduleapp.model.CoursePref;
import coursescheduleapp.model.CourseSelection;
import coursescheduleapp.model.EquivCourseSelection;

/**
 *
 * @author Drew
 */
public class CoursePrefTest extends TestCase {

    CoursePref pref;
    public CoursePrefTest() {
        pref = new CoursePref(3,5);

    }

   
    /**
     * Test of setCourseSelection method, of class CoursePref.
     */
    @Test
    public void testSetCourseSelection() {
        System.out.println("setCourseSelection");
        int equivIndex = 0;
        int selectIndex = 0;
        CourseSelection selection = null;
        CoursePref instance = new CoursePref(3,5);
        instance.setCourseSelection(equivIndex, selectIndex, selection);
       
        assertEquals(null, instance.getCourseSelection(equivIndex, selectIndex));
    }
    
    @Test
    public void testConstructor()
    {
    	List<EquivCourseSelection> allCourses = new ArrayList<EquivCourseSelection>();
    	CoursePref listPref = new CoursePref(allCourses);
    	
    	assertEquals(0, listPref.getAllCourses().size());
    }

    /**
     * Test of getEquivCourses method, of class CoursePref.
     */
    @Test
    public void testGetEquivCourses() {
        System.out.println("getEquivCourses");

        pref.getEquivCourses(0).setCourseSelection(0, null);

        assertEquals(null, pref.getEquivCourses(0).getCourseSelection(0));

    }

    /**
     * Test of getCourseSelection method, of class CoursePref.
     */
    @Test
    public void testGetCourseSelection() {
        System.out.println("getCourseSelection");


        pref.getEquivCourses(0).setCourseSelection(0, null);

        assertEquals(null, pref.getEquivCourses(0).getCourseSelection(0));
    }

    /**
     * Test of removeCourseSelection method, of class CoursePref.
     */
    @Test
    public void testRemoveCourseSelection() {
        System.out.println("removeCourseSelection");

        
        pref.addInstructorPref(0, 0, "Test");

        pref.removeCourseSelection(0, 0);
        assertEquals(0, pref.getEquivCourses(0).getCourseSelection(0).getNumInstructorPref());
    }

    /**
     * Test of addInstructorPref method, of class CoursePref.
     */
    @Test
    public void testAddInstructorPref() {
        System.out.println("addInstructorPref");
        assertEquals(0, pref.getEquivCourses(0).getCourseSelection(0).getNumInstructorPref());
        pref.addInstructorPref(0, 0, "Test");
        assertEquals(1, pref.getEquivCourses(0).getCourseSelection(0).getNumInstructorPref());
    }

    /**
     * Test of removeInstructorPref method, of class CoursePref.
     */
    @Test
    public void testRemoveInstructorPref() {
        System.out.println("removeInstructorPref");
        assertEquals(0, pref.getEquivCourses(0).getCourseSelection(0).getNumInstructorPref());
        pref.addInstructorPref(0, 0, "Test");
        assertEquals(1, pref.getEquivCourses(0).getCourseSelection(0).getNumInstructorPref());

        pref.removeInstructorPref(0, 0, "Test");
        assertEquals(0, pref.getEquivCourses(0).getCourseSelection(0).getNumInstructorPref());
    }

    /**
     * Test of getInstructorPref method, of class CoursePref.
     */
    @Test
    public void testGetInstructorPref() {
        System.out.println("getInstructorPref");
        assertEquals(0, pref.getEquivCourses(0).getCourseSelection(0).getNumInstructorPref());
        pref.addInstructorPref(0, 0, "Test");
        assertEquals(1, pref.getEquivCourses(0).getCourseSelection(0).getNumInstructorPref());
        assertEquals(true, pref.getInstructorPref(0, 0).contains("Test"));

    }

    /**
     * Test of getAllCourses method, of class CoursePref.
     */
    @Test
    public void testGetAllCourses() {
        System.out.println("getAllCourses");
        assertEquals(3, pref.getAllCourses().size());
    }

}
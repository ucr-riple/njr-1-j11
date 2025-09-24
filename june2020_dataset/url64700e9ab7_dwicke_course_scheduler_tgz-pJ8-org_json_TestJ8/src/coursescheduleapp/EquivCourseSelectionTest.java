/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package coursescheduleapp;

import org.junit.Test;
import static org.junit.Assert.*;
import coursescheduleapp.model.*;

/**
 *
 * @author Drew
 */
public class EquivCourseSelectionTest {

    EquivCourseSelection equiv;
    String pref = "TEST";
    String pref2 = "TEST2";

    public EquivCourseSelectionTest() {
        equiv = new EquivCourseSelection(3);
        equiv.addInstructorPref(0, pref);
    }

    /**
     * Test of setCourseSelection method, of class EquivCourseSelection.
     */
    @Test
    public void testSetCourseSelection() {
        System.out.println("setCourseSelection");
        int index = 0;
        CourseSelection courses = null;
        EquivCourseSelection instance = new EquivCourseSelection(3);
        instance.setCourseSelection(index, courses);

        assertEquals(null, instance.getCourseSelection(index));
        
    }

    /**
     * Test of getCourseSelection method, of class EquivCourseSelection.
     */
    @Test
    public void testGetCourseSelection() {
        System.out.println("getCourseSelection");
        int index = 0;
        CourseSelection courses = null;
        EquivCourseSelection instance = new EquivCourseSelection(3);
        instance.setCourseSelection(index, courses);

        assertEquals(null, instance.getCourseSelection(index));
    }
    /**
     * Test of getCourseSelectionList method, of class EquivCourseSelection.
     */
    @Test
    public void testGetCourseSelectionList() {
    	int index = 0;
        CourseSelection courses = new CourseSelection();
        EquivCourseSelection instance = new EquivCourseSelection(3);
        instance.setCourseSelection(index, courses);
        
        assertEquals(courses, instance.getCourseSelectionList().get(0));
        
    }
    
      /**
     * Test of getNumCourseSelection method, of class EquivCourseSelection.
     */
    @Test
    public void testGetNumCourseSelection() {
        System.out.println("getNumCourseSelection");
         EquivCourseSelection instance = new EquivCourseSelection(3);
         assertEquals(3, instance.getNumCourseSelection());
    }


    /**
     * Test of addInstructorPref method, of class EquivCourseSelection.
     */
    @Test
    public void testAddInstructorPref() {
        System.out.println("addInstructorPref");
        int index = 0;
        

        equiv.addInstructorPref(index, pref);
        equiv.addInstructorPref(index, pref2);

        assertEquals(2, equiv.getCourseSelection(index).getNumInstructorPref());

        assertEquals(true, equiv.getCourseSelection(index).getInstrucPref().contains(pref));
        assertEquals(true, equiv.getCourseSelection(index).getInstrucPref().contains(pref2));

        equiv.removeInstructorPref(index, pref);
        assertEquals(1, equiv.getCourseSelection(index).getNumInstructorPref());
        

    }

    /**
     * Test of removeInstructorPref method, of class EquivCourseSelection.
     */
    @Test
    public void testRemoveInstructorPref() {
        System.out.println("removeInstructorPref");
        int index = 0;

       equiv.addInstructorPref(index, pref);
        equiv.addInstructorPref(index, pref2);

        assertEquals(2, equiv.getCourseSelection(index).getNumInstructorPref());

        assertEquals(true, equiv.getCourseSelection(index).getInstrucPref().contains(pref));
        assertEquals(true, equiv.getCourseSelection(index).getInstrucPref().contains(pref2));

        equiv.removeInstructorPref(index, pref);
        assertEquals(1, equiv.getCourseSelection(index).getNumInstructorPref());

        
    }

    /**
     * Test of removeCourseSelection method, of class EquivCourseSelection.
     */
    @Test
    public void testRemoveCourseSelection() {
        System.out.println("removeCourseSelection");
        int index = 0;
        assertEquals(1, equiv.getCourseSelection(index).getNumInstructorPref());
        equiv.removeCourseSelection(index);
        assertEquals(0, equiv.getCourseSelection(index).getNumInstructorPref());


        
    }

    

    

}
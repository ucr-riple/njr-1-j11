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
public class PreferenceSetTest {

    public PreferenceSetTest() {
    }

  

    /**
     * Test of setCoursePref method, of class PreferenceSet.
     */
    @Test
    public void testSetCoursePref() {
        System.out.println("setCoursePref");
        CoursePref coursePref = null;
        PreferenceSet instance = new PreferenceSet(2,3);
        instance.setCoursePref(coursePref);
        assertEquals(null, instance.getCoursePref());
    }

    /**
     * Test of setSchedulePref method, of class PreferenceSet.
     */
    @Test
    public void testSetSchedulePref() {
        System.out.println("setSchedulePref");
        SchedulePref schedPref = null;
        PreferenceSet instance = new PreferenceSet(2,3);
        instance.setSchedulePref(schedPref);

        assertEquals(null, instance.getSchedulePref());
    }

    /**
     * Test of getCoursePref method, of class PreferenceSet.
     */
    @Test
    public void testGetCoursePref() {
        System.out.println("getCoursePref");
        PreferenceSet instance = new PreferenceSet(2,3);
        
        CoursePref result = instance.getCoursePref();
        instance.setCoursePref(result);

        assertEquals(result, instance.getCoursePref());
       
    }

    /**
     * Test of getSchedulePref method, of class PreferenceSet.
     */
    @Test
    public void testGetSchedulePref() {
        System.out.println("getSchedulePref");
         PreferenceSet instance = new PreferenceSet(2,3);

        SchedulePref result = instance.getSchedulePref();
        instance.setSchedulePref(result);

        assertEquals(result, instance.getSchedulePref());
    }

}
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package coursescheduleapp;

import java.util.Map;
import java.util.TreeMap;
import org.junit.Test;
import static org.junit.Assert.*;
import coursescheduleapp.model.*;
/**
 *
 * @author Drew
 */
public class SchedulePrefTest {

    public SchedulePrefTest() {
    }



    /**
     * Test of setPref method, of class SchedulePref.
     */
    @Test
    public void testSetPref_String_Object() {
        System.out.println("setPref");
        SchedulePref instance = new SchedulePref();
        
      //  instance.setPref(TimePref.TimePref.toString(), TimePref.AFTERNOON);

        assertEquals(0, instance.getAllPrefs().size());
        
    }

    /**
     * Test of setPref method, of class SchedulePref.
     */
    @Test
    public void testSetPref_Map() {
        System.out.println("setPref");
       SchedulePref instance = new SchedulePref();
       instance.setPref(TimePref.TimePref.toString(), new TimePrefs());
       
       assertEquals(true, instance.getAllPrefs().containsKey(TimePref.TimePref.toString()));

    }

    /**
     * Test of getAllPrefs method, of class SchedulePref.
     */
    @Test
    public void testGetAllPrefs() {
        System.out.println("getAllPrefs");
        SchedulePref instance = new SchedulePref();
        instance.setPref(TimePref.TimePref.toString(), new TimePrefs());
        
        assertEquals(true, instance.getAllPrefs().containsKey(TimePref.TimePref.toString()));
 
    }

    /**
     * Test of getPref method, of class SchedulePref.
     */
    @Test
    public void testGetPref() {
        System.out.println("getPref");
        SchedulePref instance = new SchedulePref();
        TimePrefs d =  new TimePrefs();

        instance.setPref(TimePref.TimePref.toString(), d);
        
        assertEquals(d, instance.getPref(TimePref.TimePref.toString()));
 
    }
    
    @Test
    public void testSetPref()
    {
    	 SchedulePref instance = new SchedulePref();
         Map<String,Preference> expResult = new TreeMap<String,Preference>();
         TimePrefs d =  new TimePrefs();
         expResult.put(TimePref.TimePref.toString(), d);
         instance.setPref(expResult);
         assertEquals(expResult, instance.getAllPrefs());
    }


}
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package coursescheduleapp;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import org.junit.Test;

import coursescheduleapp.model.Course;
import coursescheduleapp.model.CourseData;
import static org.junit.Assert.*;

/**
 *
 * @author Drew
 */
public class CourseTest {

    Course blank;
    Course filled;
    ArrayList<String> ins;
    String day;
    String time;
    String CRN;
    TreeMap<String, Object> info;

    public CourseTest() {

        blank = new Course();


        ins = new ArrayList<String>();
        ins.add("David Hovemeyer");
        ins.add("David Babcock");

        day = "MWF";
        time = "3:00-4:00";
        CRN = "1234";

        info = new TreeMap<String,Object>();
        info.put(CourseData.TIME.toString(), time);
        info.put(CourseData.DAY.toString(), day);
        info.put(CourseData.INSTRUCTOR.toString(), ins);
        info.put(CourseData.CRN.toString(), CRN);

        filled = new Course(info);
    }

 

    /**
     * Test of getCourseInfo method, of class Course.
     */
    @Test
    public void testGetCourseInfo() {
        System.out.println("getCourseInfo");

        Map<String, Object> courseData = blank.getCourseInfo();
        assertEquals(true, courseData.isEmpty());
        
        courseData = filled.getCourseInfo();
        assertEquals(info.values().size(), courseData.values().size());

    }

    /**
     * Test of setCourseInfo method, of class Course.
     */
    @Test
    public void testSetCourseInfo() {
        System.out.println("setCourseInfo");

        CRN = "1233";
        filled.putValue(CourseData.CRN.toString(), CRN);
        info.put(CourseData.CRN.toString(), CRN);

        assertEquals(CRN,filled.getValue(CourseData.CRN.toString()));
        
        blank.setCourseInfo(filled.getCourseInfo());
        assertEquals(filled.getCourseInfo(), blank.getCourseInfo());

    }

    /**
     * Test of getInstructor method, of class Course.
     */
    @Test
    public void testGetInstructor() {
        System.out.println("getInstructor");
        String key = CourseData.INSTRUCTOR.toString();
        
        
        Object result = filled.getValue(key);



        @SuppressWarnings("unchecked")
        ArrayList<String> instructors = (ArrayList<String>)result;


        assertEquals(ins.get(0),instructors.get(0));
        assertEquals(ins.get(1), instructors.get(1));

        


        result = blank.getValue(key);

        assertEquals(null, result);


    }

    /**
     * Test of getDays method, of class Course.
     */
    @Test
    public void testGetDays() {
        System.out.println("getDays");
        String key = CourseData.DAY.toString();
        
        
        String result = (String)filled.getValue(key);
        
        assertEquals(day, result);
        
        result = (String)blank.getValue(key);
        
        assertEquals(null, result);

    }

    /**
     * Test of getTime method, of class Course.
     */
    @Test
    public void testGetTime() {
        System.out.println("getTime");
        String key = CourseData.TIME.toString();

         String result = (String)filled.getValue(key);

        assertEquals(time, result);

        result = (String)blank.getValue(key);

        assertEquals(null, result);
       
    }

     /**
     * Test of getCRN method, of class Course.
     */
    @Test
    public void testGetCRN() {
        System.out.println("getCRN");
        String key = CourseData.CRN.toString();

        String result = (String)filled.getValue(key);

        assertEquals(CRN, result);

        result = (String)blank.getValue(key);

        assertEquals(null, result);

    }
    
     /**
     * Test of isHasPrefInstructor method, of class Course.
     */
    @Test
    public void testIsHasPrefInstructor() {
        System.out.println("isHasPrefInstructor");
        
        assertEquals(false, filled.isHasPrefInstructor());
    }
    
    /**
     * Test of setHasPrefInstructor method, of class Course.
     */
    @Test
    public void testSetHasPrefInstructor() {
        System.out.println("setHasPrefInstructor");
        filled.setHasPrefInstructor(true);
        assertEquals(true, filled.isHasPrefInstructor() );
    }
    
    @Test
    public void testEquals()
    {
    	assertEquals(false, filled.equals(blank));
    	
    	
    	
    	 CRN = "1233";
         filled.putValue(CourseData.CRN.toString(), CRN);
         info.put(CourseData.CRN.toString(), CRN);

         assertEquals(CRN,filled.getValue(CourseData.CRN.toString()));
         
         blank.setCourseInfo(filled.getCourseInfo());
         assertEquals(filled.getCourseInfo(), blank.getCourseInfo());
         
         
         assertEquals(true, filled.equals(blank));
         
         
    }



}
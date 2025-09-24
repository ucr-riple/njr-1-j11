

package coursescheduleapp;

import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

import coursescheduleapp.model.Course;
import coursescheduleapp.model.CourseDatabase;


public class DatabaseTest extends TestCase {
	CourseDatabase db;

    //init stuff
    public DatabaseTest() throws Exception {
        db = new CourseDatabase();

    }
    
    @Test
    public void test_getAllSemesters() throws Exception {
    	List<String> results=db.getAllSemesters();
    	System.out.println("Total semesters: "+results.size());
    	assertFalse(results.isEmpty());
    }
    
    @Test
    public void test_getAllCourseNames() throws Exception {
    	List<String> semesters=db.getAllSemesters();
    	db.setSemester(semesters.get(0));
    	
    	List<String> results=db.getAllCourseNames();
    	System.out.println("Total course names: "+results.size());
    	assertFalse(results.isEmpty());
    }
    
    @Test
    public void test_getEquivCourses() throws Exception {
    	List<String> semesters=db.getAllSemesters();
    	db.setSemester(semesters.get(0));
    	
    	//FIXME: Should make sure data is valid
    	List<String> courses=db.getAllCourseNames();
    	String courseName=courses.get(0);
    	List<Course> results=db.getEquivCourses(courseName);
    	System.out.println("Equivs for '"+courseName+"': "+results.size());
    	assertFalse(results.isEmpty());
    }
    
    @Test
    public void test_getInstructors() throws Exception {
    	List<String> semesters=db.getAllSemesters();
    	db.setSemester(semesters.get(0));
    	
    	List<String> courses=db.getAllCourseNames();
    	String courseName=courses.get(0);
    	List<String> results=db.getInstructors(courseName);
    	System.out.println("Instructors for '"+courseName+"': "+results.size());
    	assertFalse(results.isEmpty());
    }
   
   //test stuff
    /*
    @Test
    public void testSomethying() throws Exception {
		
        ResultSet rs = db.stat.executeQuery("SELECT DISTINCT(`title`) FROM `classes`;");
        while (rs.next()) {
          System.out.println("name = " + rs.getString("title"));
          //System.out.println("job = " + rs.getString("occupation"));
        }
        rs.close();
       System.out.println("hi");
    //    System.out.println("setCourse");
        
        
    //    instance.setCourse(courseList);
    //    assertEquals(courseList.size(), instance.getNumCourses());
    }*/



}
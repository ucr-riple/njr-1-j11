/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package coursescheduleapp.model;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Drew
 */
public interface CourseCollectionDB extends Publisher{

	public List<String> getAllSemesters() throws SQLException;
	public void setSemester(String semesterName);
	
	public List<String> getAllCourseNames() throws Exception;
	public List<String> getInstructors(String courseName) throws Exception;
	public List<Course> getEquivCourses(String courseName) throws Exception;
	
	
//    public List<String> getDept();
//    public String getDBName();
//    public List<String> getCourses(String dept);
     //public List<String> getAllUniqueCourses();
    
    
}

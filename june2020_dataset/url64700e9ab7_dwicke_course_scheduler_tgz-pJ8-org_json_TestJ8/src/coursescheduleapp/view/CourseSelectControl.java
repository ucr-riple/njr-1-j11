package coursescheduleapp.view;

import java.sql.SQLException;
import java.util.Set;

import coursescheduleapp.model.CourseCollectionDB;
import coursescheduleapp.model.CourseSelection;

public class CourseSelectControl {
	
	private CourseSelection courseSelect;
	private int whichCourse, whichEquiv;

	private CourseCollectionDB courseDB;
	
	public CourseSelectControl(CourseSelection courseSelect, int whichCourse, int whichEquiv, CourseCollectionDB courseDB)
    {
    	this.courseSelect = courseSelect;
    	this.whichCourse = whichCourse;
    	this.whichEquiv = whichEquiv;
    	this.courseDB = courseDB;
    }
	
	public void setCourse(String courseName) throws SQLException
	{
		try {
			courseSelect.setCourse(courseDB.getEquivCourses(courseName));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	public void removeCourse(String courseName)
	{
		courseSelect.clearSelection();
	}
	public void clearCourseSelection()
	{
		courseSelect.clearSelection();
	}
	
	public void setInstructor(Set<String> name)
	{
		courseSelect.setInstructorPref(name);
	}
	

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package coursescheduleapp.model;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Drew
 */
public class Course {

    private Map<String, Object> courseInfo;
    private boolean hasPrefInstructor;

    /**
     * Creates a blank Course with no courseInfo data.
     */
    public Course()
    {
        courseInfo = new TreeMap<String, Object>();
        this.hasPrefInstructor = false;
    }

    /**
     * Used to instantiate Course with a courseInfo.
     * @param courseInfo The info about the course.
     */
    public Course(Map<String, Object> courseInfo)
    {
        this.courseInfo = courseInfo;
        this.hasPrefInstructor = false;
    }
    
    /**
     * Gets the CourseInfo map (the data about the course) as
     * String Object pairs.
     * @return
     * Returns a unmodifiableMap of the CourseInfo
     */
    public Map<String, Object> getCourseInfo()
    {
        return Collections.unmodifiableMap(courseInfo);
    }

    /**
     * This sets the current courseInfo to the new courseInfo for the course.
     * 
     * @param courseInfo
     * The info of the course.
     */
    public void setCourseInfo(Map<String, Object> courseInfo)
    {
        this.courseInfo = courseInfo;
    }


    /**
     * 
     *
     * @param key The key for the info
     * @return
     * Returns the object associated with that key.
     */
    public Object getValue(String key)
    {
        return courseInfo.get(key);
    }
    /**
     * Adds (or if key is already used replaces) the key object pair
     * to the map containing the info about the course.
     * @param key The String that is associated with this value.
     * @param value The Object value associated with the key.
     */
    public void putValue(String key, Object value)
    {
        courseInfo.put(key, value);
    }

    /**
     *
     * @return A boolean representing if this course has a prefered instructor.
     */
    public boolean isHasPrefInstructor() {
        return hasPrefInstructor;
    }

    /**
     * Set whether this course has (true) a prefered instructor or this
     * course does not (false) have a preferd instructor.
     * @param hasPrefInstructor A boolean true or false does or doesn't have a
     * prefered instructor.
     */
    public void setHasPrefInstructor(boolean hasPrefInstructor) {
        this.hasPrefInstructor = hasPrefInstructor;
    }
    
    public boolean equals(Object c)
    {
    	if (c == null || !(c instanceof Course)) {
    	      return false;
    	    }

    	if (this == c ||(this.getValue(CourseData.CRN.toString()) != null && this.getValue(CourseData.CRN.toString()).equals(((Course) c).getValue(CourseData.CRN.toString()))))
    	{
    		return true;
    	}
    	return false;
    }



}

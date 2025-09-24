/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package coursescheduleapp.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Drew
 */
public class Schedule {
    private List<Course> courses;

    
    public Schedule()
    {
        courses = new ArrayList<Course>();
    }
    
    public Schedule(List<Course> courses)
    {
        this.courses = courses;
    }


    /**
     * Gets the number of courses in the schedule.
     * @return Number of courses.
     */
    public int getNumCourses()
    {
        return courses.size();
    }

    /**
     * Add a course to the schedule.
     * @param course The Course to add.
     */
    public void addCourse(Course course)
    {
        courses.add(course);
    }

    /**
     * Removes the specified course object from the schedule.
     * @param course Course object to remove.
     * @return Returns true if the Course was found, false if not found.
     */
    public boolean removeCourse(Course course)
    {
        return courses.remove(course);
    }

    /**
     * Removes the course with the value associated with
     * CourseData.CRN.toString() tha is equal to the provided object
     * representing the CRN.  This only is usefull if each course has
     * an unique CRN.
     *
     * @param CRN Object representing the CRN number (String, Integer...)
     * @return
     * Returns true if the Course was removed, false if not found.
     * 
     */
    public boolean removeCourse(Object CRN)
    {
        boolean wasRemoved = false;
        Iterator<Course> i = courses.iterator();
        while(i.hasNext() && wasRemoved == false )
        {
            Course cur = i.next();
            if (cur.getValue(CourseData.CRN.toString()).equals(CRN))
            {
                i.remove();
                wasRemoved = true;
            }
        }
        return wasRemoved;
    }

    /**
     * Gets the list of courses in the schedule.
     * @return Returns the list of courses in the schedule.
     */
    public List<Course> getCourses()
    {
        return courses;
    }
    /**
     * This sets the list of courses in the schedule to courses.
     * @param courses The list of courses that are to replace previous courses
     * in the schedule.
     */
    public void setCourses(List<Course> courses)
    {
        this.courses = courses;
    }

}

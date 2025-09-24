/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package coursescheduleapp.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author Drew
 */
public class CourseSelection {

    private List<Course> courseList;
    private Set<String> instructorPref;

    /**
     * Creates a CourseSelection with blank fields.
     */
    public CourseSelection()
    {
        courseList = new ArrayList<Course>();
        instructorPref = new TreeSet<String>();

    }

    /**
     * Creates a CourseSelection with course and instructor lists
     * @param courseList This is the list of courses that are the same in name,
     * but differ in respect to different instructors, time etc...
     * @param instructorPref This is the list of the names of the Instructors
     * the user prefers for this course selection.
     */
    public CourseSelection(List<Course> courseList, Set<String> instructorPref )
    {
        this.courseList = courseList;
        this.instructorPref = instructorPref;
        checkInstrucPref();
    }

    public boolean hasCourse(Course course)
    {
    	return courseList.contains(course);
    }
    /**
     * This changes the courseList to the provided courseList
     * @param courseList The new courseList
     */
   public void setCourse(List<Course> courseList)
   {
       this.courseList = courseList;
       checkInstrucPref();
   }

   /**
    * This gets the Course at the specified index
    * @param index The index in the list of courses that the desired course
    * is at.
    * @return The course at the specified index.  Null if index is out of bounds
    * or if the course is null at that index.
    */
   public Course getCourse(int index)
   {
       if (index >= courseList.size() || index < 0)
       {
           return null;
       }

       return courseList.get(index);
   }

   /**
    * This gets the number of courses in the course selection.
    * @return Returns the number of courses.
    */
   public int getNumCourses()
   {
       return courseList.size();
   }

   /**
    * This sets the instructor preference to the provided Set of instructors
    * @param instructorPref List of prefered instructors.
    */
   public void setInstructorPref(Set<String> instructorPref)
   {
       this.instructorPref = instructorPref;
       checkInstrucPref();
   }

   /**
    * Gets the number of prefered instructors.
    * @return The number of prefered instructors.
    */
   public int getNumInstructorPref()
   {
       return instructorPref.size();
   }

   /**
    * Adds the instructor pref to the list of instructors that are prefered in
    * this course selection.
    * @param pref Name of instructor that is to be added to prefered list of
    * instructors.
    */
   public void addInstructorPref(String pref)
   {
       instructorPref.add(pref);
       checkInstrucPref();
   }

   /**
    * Removes the Instructor pref from the Set
    * @param pref The name of the instructor to remove from the
    * instructorPref Set.
    */
   public void removeInstructorPref(String pref)
   {
       instructorPref.remove(pref);
       checkInstrucPref();
   }

   /**
    * Clears the CourseSelection data so that both courseList and instructorPref
    * are empty.
    */
   public void clearSelection()
   {
       courseList.clear();
       instructorPref.clear();
   }

   /**
    * Removes the specified course from the courseList.
    * @param course The course to be removed.
    */
   public boolean removeCourse(Course course)
   {
       return courseList.remove(course);
   }

   /**
     * Removes the course with the value associated with
     * CourseData.CRN.toString() that is equal to the provided object
     * representing the CRN.  This only is useful if each course has
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
        Iterator<Course> i = courseList.iterator();
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
    * Adds the course to the courseList.
    * @param course The course to be added.
    */
   public void addCourse(Course course)
   {
       courseList.add(course);
       checkInstrucPref();
   }

   /**
    *
    * @return Returns the instructorPref as an unmodifiable Set.
    */
   public Set<String> getInstrucPref()
   {
       return Collections.unmodifiableSet(instructorPref);
   }


   private void checkInstrucPref()
   {
       Iterator<Course> i = courseList.iterator();

       while(i.hasNext())
       {
           Course cur = i.next();

            @SuppressWarnings("unchecked")
            Collection<String> list = (Collection<String>) cur.getValue(CourseData.INSTRUCTOR.toString());


            Iterator<String> courseI = list.iterator();

            here:
            while(courseI.hasNext())
            {
                Iterator<String> prefI = instructorPref.iterator();
                String currInstr = courseI.next();
                while(prefI.hasNext())
                {
                    String prefInstr = prefI.next();
                    if (currInstr.equals(prefInstr))
                    {
                        cur.setHasPrefInstructor(true);
                        break here;
                    }
                    else
                    {
                        cur.setHasPrefInstructor(false);
                    }
                }
            }
            


       }
   }




}

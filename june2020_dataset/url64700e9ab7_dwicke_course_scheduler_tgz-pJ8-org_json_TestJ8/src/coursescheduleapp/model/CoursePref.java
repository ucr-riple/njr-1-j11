/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package coursescheduleapp.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Drew
 */
public class CoursePref {

    private List<EquivCourseSelection> allCourses;
    
    

    /**
     * This constructor creates a list of equivCourseSelections based on
     * numSelection.
     * @param numEquivCourses
     * numEquivCourses is the number of courses possible in the final schedule.
     * @param numSelection
     * numSelection is the number of alternate courses the user can add.
     */
    public CoursePref(int numEquivCourses, int numSelection)
    {
            allCourses = new ArrayList<EquivCourseSelection>();

            for (int i = 0; i < numEquivCourses; i++)
            {
                allCourses.add(new EquivCourseSelection(numSelection));
            }
    }

    /**
     * This constructor sets the list to the provided list.
     * @param allCourses the list of equiv courses
     */
    public CoursePref(List<EquivCourseSelection> allCourses)
    {
    	this.allCourses = allCourses;
    }
    
    public CoursePref()
    {
    	allCourses = new ArrayList<EquivCourseSelection>();
    }
    
    public void addEquivCourse(EquivCourseSelection sel)
    {
    	allCourses.add(sel);
    }
    
    /**
     * Sets the CourseSelection at the specified place.
     * @param equivIndex equivIndex is the index of the course in the final schedule
     * @param selectIndex selectIndex is the index of the alternate course
     * @param selection
     */
    public void setCourseSelection(int equivIndex, int selectIndex, CourseSelection selection)
    {
        allCourses.get(equivIndex).setCourseSelection(selectIndex, selection);
    }

    /**
     * This gets the object containing the equivalent courses at the specified
     * index in the final schedule.
     * @param equivIndex equivIndex is the index of the course in the final schedule
     * @return Returns the EquivCourseSelection object associated at the specified
     * index.
     */
    public EquivCourseSelection getEquivCourses(int equivIndex)
    {
        return allCourses.get(equivIndex);
    }

    /**
     * This method gets the object containing the course selection at the
     * specified position.
     * @param equivIndex equivIndex is the index of the course in the final schedule
     * @param selectIndex selectIndex is the index of the alternate course
     * @return Returns the object containing the course selection at the
     * specified position.
     */
    public CourseSelection getCourseSelection(int equivIndex, int selectIndex)
    {
        return allCourses.get(equivIndex).getCourseSelection(selectIndex);
    }

    /**
     * Removes the courseSelection at the specified location.
     * @param equivIndex equivIndex is the index of the course in the final schedule
     * @param selectIndex selectIndex is the index of the alternate course
     */
    public void removeCourseSelection(int equivIndex, int selectIndex)
    {
        allCourses.get(equivIndex).removeCourseSelection(selectIndex);
    }
    
    

    /**
     * Adds the instructor preference to to specified location.
     * @param equivIndex equivIndex is the index of the course in the final schedule
     * @param selectIndex selectIndex is the index of the alternate course
     * @param pref
     */
    public void addInstructorPref(int equivIndex, int selectIndex, String pref)
    {
        allCourses.get(equivIndex).addInstructorPref(selectIndex, pref);
    }

    /**
     * Removes the instructor pref at specified location.
     * @param equivIndex equivIndex is the index of the course in the final schedule
     * @param selectIndex selectIndex is the index of the alternate course
     * @param pref Instructor's name to remove.
     */
    public void removeInstructorPref(int equivIndex, int selectIndex, String pref)
    {
        allCourses.get(equivIndex).removeInstructorPref(selectIndex, pref);
    }

    /**
     * Gets the instructor pref associated with the specified location.
     * @param equivIndex equivIndex is the index of the course in the final schedule
     * @param selectIndex selectIndex is the index of the alternate course
     * @return The Set of Instructor preferences.
     */
    public Set<String> getInstructorPref(int equivIndex, int selectIndex)
    {
        return allCourses.get(equivIndex).getCourseSelection(selectIndex).getInstrucPref();
    }

    /**
     * Gets all of the couses.
     * @return A unmodifiable list of the equivCourseSelcecion objects.
     */
    public List<EquivCourseSelection> getAllCourses()
    {
        return Collections.unmodifiableList(allCourses);
    }

}

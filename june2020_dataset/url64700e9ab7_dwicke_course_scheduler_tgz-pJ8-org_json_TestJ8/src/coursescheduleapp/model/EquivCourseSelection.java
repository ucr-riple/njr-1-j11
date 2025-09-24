/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package coursescheduleapp.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Drew
 */
public class EquivCourseSelection {

    private List<CourseSelection> selections;


    /**
     * This is the constructor for EquivCourseSelections
     * @param numSelection This is the number of alternate courses the user may
     * select.
     */
    public EquivCourseSelection(int numSelection)
    {
        selections = new ArrayList<CourseSelection>();

        for (int i = 0; i < numSelection; i++)
        {
            selections.add(new CourseSelection());
        }

    }

    /**
     * This method sets the courseSelection at the specified index.
     * @param index Index of the courseSelection to replace.
     * @param courses The courseSelction to replace the current courseSelecton
     */
    public void setCourseSelection(int index, CourseSelection courses)
    {
        selections.set(index, courses);
    }

    /**
     * This method returns the CourseSelction at the index specified
     * @param index Index of the CourseSelction that is desired
     * @return The CourseSelection at the specified index.
     */
    public CourseSelection getCourseSelection(int index)
    {
        return selections.get(index);
    }


    /**
     * Removes the CourseSelction at the specified index by clearing the
     * CourseSelection data at that index.
     * @param index The index of the courseCourse selection to be removed/reset.
     */
    public void removeCourseSelection(int index)
    {

        selections.get(index).clearSelection();
    }

    /**
     * Adds the instructor pref at the specified index.
     * @param index The index of the courseSelection that the Instructor pref
     * belongs.
     * @param pref The name of the instructor to add to the Instructor pref.
     */
    public void addInstructorPref(int index, String pref)
    {
        selections.get(index).addInstructorPref(pref);
    }

    /**
     *  Removes the Instructor pref at the specified index.
     * @param index The index of the courseSelection that the Instructor pref
     * belongs.
     * @param pref  The name of the instructor to remove from the instructor pref.
     */
    public void removeInstructorPref(int index, String pref)
    {
        selections.get(index).removeInstructorPref(pref);
    }

    /**
     * Gets the number of CourseSelection objects.
     * @return the number of courseSelection objects
     */
    public int getNumCourseSelection()
    {
        return selections.size();
    }
    
    /**
     * Gets the list of courseSelection objects as a unmodifiableList
     * @return Returns the list of courseSelection objects as a unmodifiableList
     */
    public List<CourseSelection> getCourseSelectionList()
    {
    	return Collections.unmodifiableList(selections);
    }



}

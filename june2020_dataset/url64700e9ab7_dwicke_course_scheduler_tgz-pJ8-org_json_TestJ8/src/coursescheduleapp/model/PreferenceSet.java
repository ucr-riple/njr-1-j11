/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package coursescheduleapp.model;

/**
 *
 * @author Drew
 */
public class PreferenceSet {

    private SchedulePref schedPref;
    private CoursePref coursePref;

   
    /**
     * Constructor creates a new PreferenceSet based on provided arguments.
    * @param numEquivCourses
     * numEquivCourses is the number of courses possible in the final schedule.
     * @param numSelection
     * numSelection is the number of alternate courses the user can add.
     *
     */
    public PreferenceSet(int numEquivCourses, int numSelection)
    {
        schedPref = new SchedulePref();
        coursePref = new CoursePref(numEquivCourses, numSelection);
    }

    /**
     * This method sets the CoursePref to the provided course preference.
     * @param coursePref CoursePref to replace current CoursePref.
     */
    public void setCoursePref(CoursePref coursePref)
    {
        this.coursePref = coursePref;
    }

    /**
     * This method sets the schedPref to the provided SchedulePref.
     * @param schedPref The SchedulePref object to replace the current
     * SchedulePref object.
     */
    public void setSchedulePref(SchedulePref schedPref)
    {
        this.schedPref = schedPref;
    }

    /**
     * This method gets the CoursePref object.
     * @return The coursePref object.
     */
    public CoursePref getCoursePref()
    {
        return coursePref;
    }

    /**
     * This method gets the SchedulePref object.
     * @return The SchedulePref object.
     */
    public SchedulePref getSchedulePref()
    {
        return schedPref;
    }

    

}

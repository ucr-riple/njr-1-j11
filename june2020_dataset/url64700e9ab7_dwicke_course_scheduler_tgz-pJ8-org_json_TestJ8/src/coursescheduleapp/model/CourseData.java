/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package coursescheduleapp.model;

/**
 *This enum is for the ability to standardize the keys in the courseInfo map
 * in the Course class.  If there is a field in the data about the course
 * add a standard key for it in this enum.  When using this enum for the
 * key use the toString() method in order to keep consistency.
 * @author Drew
 */
public enum CourseData {

    CRN,CRDT,INSTRUCTOR,TIME,DAY,NAME,DEPT,BLDG;


    @Override
    public String toString()
    {
        switch(this)
        {
            case CRN:
                return "CRN";	
            case INSTRUCTOR:
                return "Instructor";
            case TIME:
                return "Time";	// TimePeriod object
            case DAY:
                return "Day";	// ArrayList of Day enums
            case NAME:
                return "Name";
            case DEPT:
                return "Department";
            case BLDG:
                return "Building";
            case CRDT:
            	return "Credits";
            default:
                return "";
        }
    }
}

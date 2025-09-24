/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package coursescheduleapp.model;

/**
 *
 * @author Drew
 */
public enum CourseSpacing {
TENMIN,
HOUR,
NOPREF,
CourseSpacing;

    @Override
public String toString()
{
       switch(this)
        {
            case CourseSpacing:
                return "Course Spacing";
            case TENMIN:
            	return "Ten Minutes";
            case HOUR:
            	return "One Hour";
            case NOPREF:
            	return "No preference";
            default:
                return "Course Spacing";
        } 
    
}

}

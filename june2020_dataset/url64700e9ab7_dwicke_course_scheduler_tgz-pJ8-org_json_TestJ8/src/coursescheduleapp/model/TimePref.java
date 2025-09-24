/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package coursescheduleapp.model;

/**
 *
 * @author Drew
 */
public enum TimePref {
// The time preference of the user
    MORNING,AFTERNOON,EVENING, NOPREF, TimePref;
    
    @Override
    public String toString()
    {
        switch(this)
        {
            case TimePref:
                 return "TimePref";
            case MORNING:
            	return "Morning";
            case AFTERNOON:
            	return "Afternoon";
            case EVENING:
            	return "Evening";
            case NOPREF:
            	return "No Preference";
            default:
                 return "TimePref";
        }
        
    }
}

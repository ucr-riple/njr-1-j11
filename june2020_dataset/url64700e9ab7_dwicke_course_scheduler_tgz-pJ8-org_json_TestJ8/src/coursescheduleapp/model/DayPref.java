/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package coursescheduleapp.model;

/**
 *
 * @author Drew
 */
public enum DayPref {
	// Days of the week abrivated
	MON,TUES,WED,THUR,FRI,SAT, NOPREF,DayPref;

	@Override
	public String toString()
	{
		switch(this)
		{
		case DayPref:
			return "Day Pref";
		case MON:
			return "Monday";
		case TUES:
			return "Tuesday";
		case WED:
			return "Wednesday";
		case THUR:
			return "Thursday";
		case FRI:
			return "Friday";
		case SAT:
			return "Saturday";
		case NOPREF:
			return "No Preference";
		default:
			return "Day Pref";
		}

	}

}

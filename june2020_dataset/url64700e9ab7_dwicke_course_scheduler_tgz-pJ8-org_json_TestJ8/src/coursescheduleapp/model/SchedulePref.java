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
public class SchedulePref {

	
    private Map<String, Preference> preferences;

    
    /**
     * Creates a SchedulePref with default Pref values.
     */
    public SchedulePref()
    {
        preferences = new TreeMap<String, Preference>();
    }
    
    public void addAll()
    {
    	setPref(TimePref.TimePref.toString(), new TimePrefs());
    	setPref("hour", new LeastHourPref());
    	setPref("day", new LeastDayPref());
    	setPref(DayPref.DayPref.toString(), new DayPrefs());
    	setPref(CourseSpacing.CourseSpacing.toString(), new TimeSpacingPrefs());
    	
    }

   
    /**
     * Set a new Preference.
     * @param key The string representing the value.
     * @param val The value that the key is mapped to.
     * @return
     * Returns true if new preference
     * false if it is replacing existing preference.
     */
    public boolean setPref(String key, Preference val)
    {
    	boolean hasKey = preferences.containsKey(key);
    	preferences.put(key, val);
        return hasKey;
    }

    /**
     * Set the preferences of the Schedule.
     * @param prefs New preferences to replace current preferences.
     */
    public void setPref(Map<String, Preference> prefs)
    {
        preferences = prefs;
    }

    /**
     * Get a Map of the schedule preferences.
     * @return Returns a nunmodifiableMap containing the schedule preferences.
     */
    public Map<String,Preference> getAllPrefs()
    {
        return Collections.unmodifiableMap(preferences);
    }

    /**
     * Get the object associated with the key provided.
     * @param key The key that maps to a preference object.
     * @return Returns the preference object associated with the provided key.
     */
    public Preference getPref(String key)
    {
        return preferences.get(key);
    }

}

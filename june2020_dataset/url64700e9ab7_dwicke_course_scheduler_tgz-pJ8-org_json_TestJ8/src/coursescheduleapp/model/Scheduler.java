/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package coursescheduleapp.model;

/**
 *
 * @author Drew
 */
public interface Scheduler extends Runnable, Publisher{

    public Schedule createSchedule(PreferenceSet prefSet);
	public void setPref(PreferenceSet prefSet);
}

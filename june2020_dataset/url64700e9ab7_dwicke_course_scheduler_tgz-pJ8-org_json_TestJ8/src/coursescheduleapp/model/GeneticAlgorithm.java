/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package coursescheduleapp.model;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Drew
 */
public class GeneticAlgorithm implements Scheduler {

	private GA<Schedule> ga;
	private MGA<Schedule> mga;
	private GACoursePref gaInterface;
	private GASchedulePref gaFit;
	private PreferenceSet prefSet;
	private BasicPublisher bPub;
	private int numPrecessors;
	
	public GeneticAlgorithm(int numProcessors)
	{
		this.numPrecessors = numProcessors;
		bPub = new BasicPublisher();
	}

	public Schedule createSchedule(PreferenceSet prefSet)
	{
		// First look for special cases

		Schedule returnedSched = new Schedule(); 
		Iterator<EquivCourseSelection> listI = prefSet.getCoursePref().getAllCourses().iterator();
		boolean shouldUseGA = false;
		done:
			while(listI.hasNext())
			{
				EquivCourseSelection curr = listI.next();
				Iterator<CourseSelection> currI = curr.getCourseSelectionList().iterator();
				boolean addedCourse = false;
				while(currI.hasNext())
				{
					CourseSelection currSelect = currI.next();
					if (currSelect.getNumCourses() > 1 || (addedCourse == true && currSelect.getNumCourses() >= 1))
					{
						shouldUseGA = true;
						break done;
					}
					else if (currSelect.getNumCourses() == 1 && addedCourse == false)
					{
						returnedSched.addCourse(currSelect.getCourse(0));
						addedCourse = true;
					}
				}
			}

		if (shouldUseGA == true)
		{


			gaInterface = new GACoursePref(prefSet.getCoursePref());
			gaFit = new GASchedulePref(prefSet.getSchedulePref());

			if (numPrecessors < 5)
			{
			ga = new GA<Schedule>(gaInterface, gaFit);
			returnedSched = ga.runGAByTime(15, 1000, true);
			}
			else
			{
				mga = new MGA<Schedule>(gaInterface, gaFit);
				returnedSched = mga.runGAByTime(15, 1000, true, numPrecessors);
			}
		}

		// Check to make sure that the schedule is valid

		TimeDayCollection sched = new TimeDayCollection();
		Iterator<Course> it = returnedSched.getCourses().iterator();

		// Iterate over each course to create a time table for the days and times
		// of the courses.
		while(it.hasNext()) 
		{
			Course curr = it.next();
			// I expect that each course has an ArrayList of Day enums
			// and a TimePeriod object
			if (curr != null)
			{
				boolean wasAdded = sched.addTimeCollection((TimePeriod)curr.getValue(CourseData.TIME.toString()),(ArrayList<Day>)curr.getValue(CourseData.DAY.toString()));
				// If wasAdded is false then two courses occur during or right after the other
				// like if one class starts at 10am and the other class ends at 10am
				if (wasAdded == false)
				{
					return new Schedule();
				}
			}
		}

		return returnedSched;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		bPub.notifySubscribers(this, this.createSchedule(prefSet));
	}

	@Override
	public void setPref(PreferenceSet prefSet) {
		// TODO Auto-generated method stub
		this.prefSet = prefSet;
	}
	


	@Override
	public void addSubscriber(Subscriber s) throws RemoteException {
		// TODO Auto-generated method stub
		bPub.addSubscriber(s);
	}

	@Override
	public void removeAllSubscribers() throws RemoteException {
		// TODO Auto-generated method stub
		bPub.removeAllSubscribers();
	}

	@Override
	public void removeSubscriber(Subscriber s) throws RemoteException {
		// TODO Auto-generated method stub
		bPub.removeSubscriber(s);
	}

}

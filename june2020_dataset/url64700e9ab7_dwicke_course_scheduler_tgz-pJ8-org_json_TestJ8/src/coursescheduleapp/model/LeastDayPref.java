package coursescheduleapp.model;

import java.util.ArrayList;
import java.util.Iterator;

public class LeastDayPref implements Preference {

	@Override
	public double getFitness(Schedule individual) {
		// TODO Auto-generated method stub
		TimeDayCollection sched = new TimeDayCollection();
		Iterator<Course> it = individual.getCourses().iterator();

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
					return 0;
				}
			}
		}
		return getFitness(sched);
	}

	
	private double getFitness(TimeDayCollection sched) {
		// TODO Auto-generated method stub
		
		double fitness = 0; 
		int numDays = sched.numDays();

		if (numDays != 0)
		{
			fitness += (1 / numDays);
		}
		return fitness;
	
	}


	@Override
	public void addPref(String pref) {
		// TODO Auto-generated method stub
		// Does nothing
	}

}

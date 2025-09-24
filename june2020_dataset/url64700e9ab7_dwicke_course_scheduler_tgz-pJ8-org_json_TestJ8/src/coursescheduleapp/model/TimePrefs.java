package coursescheduleapp.model;

import java.util.ArrayList;
import java.util.Iterator;

public class TimePrefs implements Preference {
	
	private String timePref;
	private double score;
	
	public TimePrefs()
	{
		timePref = TimePref.NOPREF.toString();
		score = .25;
	}
	
	public TimePrefs(String timePref)
	{
		this.timePref = timePref;
		score = .25;
	}
	
	public void setTimePref(String timePref)
	{
		this.timePref = timePref;
	}

	@Override
	public double getFitness(Schedule individual) {
		// TODO Auto-generated method stub
		
		if (timePref.equals(TimePref.NOPREF.toString()))
		{
			return 0;
		}
		
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
		double fitness = 0;
		if (timePref != TimePref.NOPREF.toString())
		{
			int morning = 720;
			int afternoon = 1020;
			for (int i = 0; i < Day.values().length; i++)
			{

				Iterator<TimePeriod> schedSet = sched.getCollection(Day.values()[i]).getCollection().iterator();
				while(schedSet.hasNext())
				{
					TimePeriod curr = schedSet.next();
					int end = curr.getEnd().getTime();
					if (end < morning && timePref.equals(TimePref.MORNING.toString()))
					{
						fitness += score;
					}
					else if (end < afternoon && timePref.equals(TimePref.AFTERNOON.toString()))
					{
						fitness += score;
					}
					else if (end > afternoon && timePref.equals(TimePref.EVENING.toString()))
					{
						fitness += score;
					}
				}
			}
		}

		return fitness;
	}

	@Override
	public void addPref(String pref) {
		// TODO Auto-generated method stub
		setTimePref(pref);
	}

}

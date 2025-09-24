package coursescheduleapp.model;

import java.util.ArrayList;
import java.util.Iterator;

public class DayPrefs implements Preference{
	
	private double score;
	private ArrayList<String> dayPref;
	
	public DayPrefs()
	{
		dayPref = new ArrayList<String>();
		score = .9;
	}
	public DayPrefs(String val)
	{
		dayPref = new ArrayList<String>();
		dayPref.add(val);
		score = .9;
	}
	
	public DayPrefs(ArrayList<String> prefs)
	{
		dayPref = prefs;
		score = .9;
	}
	
	public void setDayPrefs(ArrayList<String> newPrefs)
	{
		dayPref = newPrefs;
		score = .9;
	}
	
	@Override
	public String toString()
	{
		String s = dayPref.toString();
		s = s.replace("]", "");
		s = s.replace("[", "");
		return s;
	}

	@SuppressWarnings("unchecked")
	@Override
	public double getFitness(Schedule individual) {
		// TODO Auto-generated method stub
		
		if (dayPref.size() == 0)
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
	
	private double getFitness(TimeDayCollection sched)
	{
		double fitness = 0;
	
				
		if (dayPref.size() > 0)
		{
			ArrayList<Day> schedDays = sched.getDaysInCollection();
			int preferredUsed = 0;

			for (int i = 0; i < schedDays.size(); i++)
			{
				if (dayPref.contains(schedDays.get(i).toString()))
				{
					// The score for the schedule containing at least a course on a preferred day
					//System.out.println(schedDays.get(i).toString());
					preferredUsed++;
					fitness += score;
					
					System.out.println(schedDays.get(i).toString() + "  " + 	preferredUsed);
				}
			}
			// Bonus if whole schedule is on preferred days
			if (preferredUsed == dayPref.size())
			{
				
				fitness += score;
			}
			else
			{
				fitness -= score * (dayPref.size() - preferredUsed);
			}
		}

		return fitness;
	}
	@Override
	public void addPref(String pref) {
		// TODO Auto-generated method stub
		dayPref.add(pref);
		
	}

}

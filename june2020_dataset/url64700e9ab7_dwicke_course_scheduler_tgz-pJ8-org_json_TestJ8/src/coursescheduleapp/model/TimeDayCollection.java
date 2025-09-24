package coursescheduleapp.model;

import java.util.ArrayList;
/**
 * A collection of day and TimePeriods.
 * @author dwicke
 *
 */
public class TimeDayCollection {
	private TimeCollection[] days;

	/**
	 * Creates a TimeDayCollection array of size
	 * Day.values().length.
	 */
	public TimeDayCollection()
	{
		days = new TimeCollection[Day.values().length];
		for (int i = 0; i < days.length; i++)
		{
			days[i] = new TimeCollection();
		}
	}

	/**
	 * Add a timePeriod to each of the days in the arraylist day.
	 * @param per The TimePeriod object to add to the collection
	 * @param day The days that the TimePeriod object should be added to.
	 * @return Returns true if the TimePeriod was successfully added to all
	 * days, false if it was not added to one of the days.
	 */
	public boolean addTimeCollection(TimePeriod per, ArrayList<Day> day)
	{
		
		for (int i = 0; i < day.size(); i++)
		{
			boolean wasAdded = days[day.get(i).ordinal()].addPeriod(per);
			if (wasAdded == false)
			{
				return false;
			}
		}
		return true;
	}

	
	public TimeCollection getCollection(Day d)
	{
		return days[d.ordinal()];
	}

	public double hoursInDay(Day d)
	{
		if ( days[d.ordinal()].getCollection().isEmpty() == false)
		{
			return ((double)days[d.ordinal()].getCollection().last().getEnd().subtract(days[d.ordinal()].getCollection().first().getStart())) / 60;
		}
		return 0;
	}
	public double hoursInWeek()
	{
		double hours = 0;
		for (int i = 0; i < Day.values().length; i++)
		{
			if ( days[Day.values()[i].ordinal()].getCollection().isEmpty() == false)
			{
				hours += ((double) days[Day.values()[i].ordinal()].getCollection().last().getEnd().subtract(days[Day.values()[i].ordinal()].getCollection().first().getStart())) / 60;

			}
		}
		return hours;
	}

	public int numDays()
	{
		int numDays = 0;
		for (int i =0; i < days.length; i++)
		{
			if (days[i].getNumInCollection() > 0)
			{
				numDays++;
			}
		}
		return numDays;
	}

	public ArrayList<Day> getDaysInCollection()
	{
		ArrayList<Day> daysUsed = new ArrayList<Day>();

		for (int i =0; i < days.length; i++)
		{
			if (days[i].getNumInCollection() > 0)
			{
				daysUsed.add(Day.values()[i]);
			}
		}

		return daysUsed;
	}
	


}

package coursescheduleapp.model;

public class CourseTime implements Time {

	private int time;
	public CourseTime(int time)
	{
		this.time = time;
	}
	
	/**
	 * Constructor for hours past midnight and mins past midnight 
	 * @param hours
	 * @param mins
	 */
	public CourseTime(int hours, int mins)
	{
		time = hours * 60 + mins;
		
	}

	/**
	 * Returns the number of minutes between the two
	 * times.
	 */
	@Override
	public int subtract(Time t) {

		int diff = time - t.getTime();

		return diff;
	}
	/**
	 * Returns a string value of 
	 * HH:MM AM/PM
	 */
	@Override
	public String toString()
	{
		String timeSt;
		int hours = time / 60;
		String min = String.valueOf(time - (hours * 60));

		String hour;
		String ampm;

		if(hours > 12)
		{
			hour = String.valueOf(hours - 12);
			ampm = "pm";
		}
		else if (hours == 12)
		{
			ampm = "pm";
			hour = String.valueOf(hours);
		}
		else
		{
			ampm = "am";
			hour = String.valueOf(hours);
		}

		if((time - (hours * 60)==0))	{				
			timeSt = hour + ":" + "00" + " " + ampm;
			return timeSt;
		}
		else
		{				
			timeSt = hour + ":" + min + " " + ampm;
			return timeSt;
		}


	}


	/**
	 * Returns the number of min. past midnight as
	 * an int for the time.
	 */
	@Override
	public int getTime() {
		// TODO Auto-generated method stub
		return time;
	}

}

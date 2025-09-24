package coursescheduleapp.model;

public class TimePeriod implements Comparable<TimePeriod>{
	
	private Time start, end;
	private Object payload;
	
	public TimePeriod(Time start, Time end)
	{
		this.start = start;
		this.end = end;
	}
	
	public TimePeriod(Time start, Time end, Object payload)
	{
		this.start = start;
		this.end = end;
		this.payload = payload;
	}
	
	public Object getPayload()
	{
		return payload;
	}
	public void setPayload(Object payload)
	{
		this.payload = payload;
	}
	
	/**
	 * Returns the number of minutes in the time period.
	 * delta t.
	 * @return
	 * Positive if no overlap and negative if overlap.
	 * 
	 */
	public int timeInPeriod()
	{
		return end.subtract(start);
	}
	
	public Time getStart()
	{
		return start;
	}
	public Time getEnd()
	{
		return end;
	}
	
	
	public boolean equals(Object o)
	{
		if (o == null || !(o instanceof TimePeriod)) {
		      return false;
		    }

		if (compareTo((TimePeriod) o) == 0)
		{
			return true;
		}
		return false;
	}
	
	public int compareTo(TimePeriod t)
	{
		int otherStart = t.getStart().getTime();
		int starti = this.start.getTime();
		int otherEnd = t.getEnd().getTime();
		int endi = this.end.getTime();
		
		// -1 if this time period is before t
		// this means that end is less than 
		// other start.
		if (endi < otherStart)
		{
			return -1;
		}
		// 0 if this time period is during t
		// this means that starti is >= to 
		// otherStart and also less that otherEnd
		// or endi is >= to otherStart and also less
		// than otherEnd
		// Or if starti <= otherStart and endi >= otherEnd
		else if ((starti >= otherStart && starti <= otherEnd) || (endi >= otherStart && endi <= otherEnd) || (starti <= otherStart && endi >= otherEnd))
		{
			return 0;
		}
		
		/*
		else if (starti > otherEnd)
		{
		*/
			// 1 if this time period is after t
			// this means that starti is > than
			// otherEnd
			return 1;
		//}
		
		
		//return 1;
	}
	
	@Override
	public String toString()
	{
		return "Start: " + start.toString() + "  End: " + end.toString();
	}
	

}

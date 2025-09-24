package coursescheduleapp.model;

public enum Day {

	// Days of the week abbreviated
	M,T,W,R,F,S;
	
	@Override
	public String toString()
	{
		switch(this)
		{
		case M:
			return "Monday";
		case T:
			return "Tuesday";
		case W:
			return "Wednesday";
		case R:
			return "Thursday";
		case F:
			return "Friday";
		case S:
			return "Saturday";
		}
		return "";
	}
	
}

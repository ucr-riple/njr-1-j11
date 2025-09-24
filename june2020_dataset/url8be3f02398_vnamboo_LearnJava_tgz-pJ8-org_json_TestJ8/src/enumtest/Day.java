package enumtest;

public enum Day {
	SUNDAY,
	MONDAY,
	TUESDAY,
	WEDNESDAY,
	THURSDAY,
	FRIDAY,
	SATURDAY;
	
	public int getOrdinal(){
		return this.ordinal();
	}
	
	public Day getFirst(){
		return values()[0];
	}
}

package com.sjl.health;

public interface TimePeriod {

	public Instant getStart();
	public Instant getEnd();
	
	public long getMilliseconds();
	
}

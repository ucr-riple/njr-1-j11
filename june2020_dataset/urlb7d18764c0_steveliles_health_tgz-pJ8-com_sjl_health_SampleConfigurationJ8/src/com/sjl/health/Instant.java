package com.sjl.health;

public interface Instant {

	public long getClockTime();
	
	public boolean before(Instant anInstant);
	
	public boolean after(Instant anInstant);
	
}

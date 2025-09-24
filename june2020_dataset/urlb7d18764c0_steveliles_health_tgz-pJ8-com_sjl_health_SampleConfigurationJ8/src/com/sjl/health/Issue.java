package com.sjl.health;

public interface Issue {

	public Instant getWhenFirstOccurred();
	public Instant getMostRecentOccurrence();
	
	public Statistics getStatistics();
	
	public Throwable getCause();
}

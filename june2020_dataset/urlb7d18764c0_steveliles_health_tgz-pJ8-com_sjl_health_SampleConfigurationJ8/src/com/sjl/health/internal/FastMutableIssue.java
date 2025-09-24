package com.sjl.health.internal;

import java.util.concurrent.atomic.*;

import com.sjl.health.*;

/**
 * Note on thread-safety: this class is not strictly thread-safe in
 * that the most-recent occurrence and stats are not updated under
 * a shared lock, resulting in the possibility to read-back values
 * that never actually co-existed. This is a deliberate compromise
 * to remove lock contention.
 * 
 * @author steve
 */
public class FastMutableIssue implements MutableIssue {

	private final Clock clock;
	private final Throwable cause;
	private final Instant first;
	
	private final MutableStatistics stats;
	private final AtomicReference<Instant> recent;
	
	public FastMutableIssue(Throwable aThrowable, Clock aClock) {
		clock = aClock;		
		cause = aThrowable;
		first = clock.now();
		stats = new ThreadSafeMutableStatistics(aClock);
		recent = new AtomicReference<Instant>(first);
	}
	
	@Override
	public Instant getWhenFirstOccurred() {
		return first;
	}

	@Override
	public Instant getMostRecentOccurrence() {
		return recent.get();
	}

	@Override
	public Statistics getStatistics() {
		return stats.snapshot();
	}

	@Override
	public Throwable getCause() {
		return cause;
	}

	@Override
	public void newOccurrence() {
		recent.set(clock.now());
		stats.increment();
	}
}

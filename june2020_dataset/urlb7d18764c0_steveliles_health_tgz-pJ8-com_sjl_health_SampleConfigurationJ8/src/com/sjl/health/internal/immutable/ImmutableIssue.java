package com.sjl.health.internal.immutable;

import com.sjl.health.*;

public final class ImmutableIssue implements Issue {

	public static ImmutableIssue create(Issue anIssue) {
		if (anIssue == null)
			return null;
		if (anIssue instanceof ImmutableIssue)
			return (ImmutableIssue) anIssue;
		
		return new ImmutableIssue(
			ImmutableInstant.create(anIssue.getWhenFirstOccurred()),
			ImmutableInstant.create(anIssue.getMostRecentOccurrence()),
			ImmutableStatistics.create(anIssue.getStatistics()),
			anIssue.getCause());
	}
	
	private final Instant first;
	private final Instant last;
	private final Statistics stats;
	private final Throwable cause;
	
	private ImmutableIssue(
		ImmutableInstant aFirst, ImmutableInstant aLast,
		ImmutableStatistics aStats, Throwable aCause) {
		first = aFirst;
		last = aLast;
		stats = aStats;
		cause = aCause;
	}

	@Override
	public Instant getWhenFirstOccurred() {
		return first;
	}

	@Override
	public Instant getMostRecentOccurrence() {
		return last;
	}

	@Override
	public Statistics getStatistics() {
		return stats;
	}

	@Override
	public Throwable getCause() {
		return cause;
	}
}

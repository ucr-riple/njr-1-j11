package com.sjl.health.internal;

import com.sjl.health.*;
import com.sjl.health.internal.immutable.*;

public class MutableState implements State {

	private final String name;
	private final ImmutableInstant whenChanged;
	private final Issue whyChanged;
	private final IssueTracker issueTracker;
	
	private final MutableStatistics success;
	private final MutableStatistics failure;
	
	private final Transition promoter;
	private final Transition demoter;
	
	public MutableState(
		String aName, Issue aWhyChanged, IssueTracker aTracker, 
		Transition aPromoter, Transition aDemoter, Clock aClock) {
		name = aName;
		whyChanged = ImmutableIssue.create(aWhyChanged);
		whenChanged = ImmutableInstant.create((whyChanged != null) ? 
			whyChanged.getWhenFirstOccurred() : ImmutableInstant.create(aClock.now()));
		issueTracker = aTracker;
		
		success = new ThreadSafeMutableStatistics(aClock);
		failure = new ThreadSafeMutableStatistics(aClock);
		
		promoter = aPromoter;
		demoter = aDemoter;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public Instant getWhenChanged() {
		return whenChanged;
	}

	@Override
	public Issue getWhyChanged() {
		return whyChanged;
	}

	@Override
	public Issues getDistinctIssues() {
		return issueTracker.getDistinctIssues();
	}
	
	@Override
	public Statistics getSuccessStats() {
		return success.snapshot();
	}
	
	@Override
	public Statistics getFailureStats() {
		return failure.snapshot();
	}
	
	@Override
	public Statistics getTotalStats() {
		return ImmutableStatistics.combine(
			success.snapshot(), failure.snapshot());
	}

	@Override
	public State success() {
		try {
			success.increment();
			return (promoter == null) ? 
				this : promoter.attempt(success, failure, null);
		} catch (Throwable aT) {
			aT.printStackTrace(); // TODO: logging
			return this;
		}
	}

	@Override
	public State failure(Throwable aThrowable) {
		try {
			failure.increment();
			issueTracker.log(aThrowable);
			return (demoter == null) ? 
				this : demoter.attempt(success, failure, aThrowable);
		} catch (Throwable aT) {
			aT.printStackTrace(); // TODO: logging
			return this;
		}
	}
	
	public String toString() {
		StringBuilder _sb = new StringBuilder();
		_sb.append(name);
		_sb.append(" @");
		_sb.append(whenChanged);
		_sb.append(" ");
		if (whyChanged != null) {
			_sb.append("(");
			_sb.append(whyChanged.getCause().getMessage());
			_sb.append("), ");
		}
		_sb.append(getFailureStats().getOccurrenceCount());
		_sb.append("/");
		_sb.append(getTotalStats().getOccurrenceCount());
		_sb.append(" errors/invocations");
		return _sb.toString();
	}
}

package com.sjl.health.internal.immutable;

import com.sjl.health.HealthInfo.StateInfo;
import com.sjl.health.*;

public final class ImmutableStateInfo implements StateInfo {

	public static ImmutableStateInfo create(StateInfo anInfo) {
		if (anInfo == null)
			return null;
		if (anInfo instanceof ImmutableStateInfo)
			return (ImmutableStateInfo) anInfo;
		
		return new ImmutableStateInfo(
			anInfo.getName(), ImmutableInstant.create(anInfo.getWhenChanged()),
			ImmutableIssue.create(anInfo.getWhyChanged()),
			ImmutableStatistics.create(anInfo.getSuccessStats()),
			ImmutableStatistics.create(anInfo.getFailureStats()),
			ImmutableIssues.create(anInfo.getDistinctIssues()));
	}
	
	private final String name;
	private final ImmutableInstant when;
	private final ImmutableIssue why;
	private final ImmutableStatistics success;
	private final ImmutableStatistics failure;
	private final ImmutableStatistics total;
	private final ImmutableIssues issues;

	private ImmutableStateInfo(
		String aName, ImmutableInstant aWhen, ImmutableIssue aWhy,
		ImmutableStatistics aSuccess, ImmutableStatistics aFailure,
		ImmutableIssues anIssues) {
		name = aName;
		when = aWhen;
		why = aWhy;
		success = aSuccess;
		failure = aFailure;
		total = ImmutableStatistics.combine(success, failure);
		issues = anIssues;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public Instant getWhenChanged() {
		return when;
	}

	@Override
	public Issue getWhyChanged() {
		return why;
	}

	@Override
	public Statistics getSuccessStats() {
		return success;
	}

	@Override
	public Statistics getFailureStats() {
		return failure;
	}

	@Override
	public Statistics getTotalStats() {
		return total;
	}

	@Override
	public Issues getDistinctIssues() {
		return issues;
	}


}
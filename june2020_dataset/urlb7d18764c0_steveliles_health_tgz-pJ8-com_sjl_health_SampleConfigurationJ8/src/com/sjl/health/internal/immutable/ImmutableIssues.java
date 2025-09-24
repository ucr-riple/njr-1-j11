package com.sjl.health.internal.immutable;

import java.util.*;

import com.sjl.health.*;

public final class ImmutableIssues implements Issues {

	public static ImmutableIssues create(Issues anIssues) {
		if (anIssues == null)
			return null;
		if (anIssues instanceof ImmutableIssues)
			return (ImmutableIssues) anIssues;
		
		final List<ImmutableIssue> _issues = new ArrayList<ImmutableIssue>();
		anIssues.each(new Callback<Issue>() {
			@Override
			public void with(Issue anIssue) {
				_issues.add(ImmutableIssue.create(anIssue));
			}
		});
		return new ImmutableIssues(_issues);
	}
	
	private final List<ImmutableIssue> issues;
	
	private ImmutableIssues(List<ImmutableIssue> anIssues) {
		issues = Collections.unmodifiableList(anIssues);
	}
	
	@Override
	public void each(Callback<Issue> aCallback) {
		for (Issue _i : issues) {
			aCallback.with(_i);
		}
	}	
}

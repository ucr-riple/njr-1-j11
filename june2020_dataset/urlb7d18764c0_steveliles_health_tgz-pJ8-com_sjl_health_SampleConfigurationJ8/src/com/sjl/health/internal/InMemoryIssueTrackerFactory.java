package com.sjl.health.internal;

import java.util.*;

import com.sjl.health.*;

public class InMemoryIssueTrackerFactory implements IssueTrackerFactory {

	private final Clock clock;
	
	public InMemoryIssueTrackerFactory(Clock aClock) {
		clock = aClock;
	}
	
	@Override
	public IssueTracker newTracker() {
		return new InMemoryIssueTracker();
	}
	
	private class InMemoryIssueTracker implements IssueTracker {

		private Map<Throwable, MutableIssue> issues;
		private Object lock = new Object();
		
		public InMemoryIssueTracker() {
			issues = new HashMap<Throwable, MutableIssue>();
		}
		
		@Override
		public Issues getDistinctIssues() {
			Map<Throwable, MutableIssue> _issues;
			synchronized(lock) { // make sure we read the latest one
				_issues = issues;
			}
			
			final Collection<MutableIssue> _result = _issues.values();
			
			return new Issues() {
				@Override
				public void each(Callback<Issue> aCallback) {
					for (Issue _i : _result)
						aCallback.with(_i);
				}
			};
		}

		@Override
		public void log(Throwable aCause) {
			MutableIssue _i = get(aCause);
			_i.newOccurrence();
		}
		
		private MutableIssue get(Throwable aCause) {
			MutableIssue _i = issues.get(aCause);
			if (_i == null) {
				synchronized(lock) {
					_i = issues.get(aCause);
					if (_i == null) {
						_i = new FastMutableIssue(aCause, clock);
						issues.put(aCause, _i);
					}
				}
			}
			return _i;
		}
		
	}
}

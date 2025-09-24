package com.sjl.health;

import com.sjl.health.internal.*;

public class Transitions {

	static class TransitionBuilder {
		private String from;
		private String to;
		private boolean demotion;
		private Condition when;
		
		public TransitionBuilder(String aFrom, boolean anIsDemotion) {
			from = aFrom;
			demotion = anIsDemotion;
		}
		
		public boolean isDemotion() {
			return demotion;
		}
		
		public TransitionBuilder to(String aTo) {
			to = aTo;
			return this;
		}
		
		public TransitionBuilder when(Condition aCondition) {
			when = aCondition;
			return this;
		}
		
		public String getFrom() {
			return from;
		}
		
		public String getTo() {
			return to;
		}
		
		public Condition getWhen() {
			return when;
		}
		
		public Transition build(final Configuration aStates) {
			// TODO: create an immutable-transition ...
			return new Transition() {
				@Override
				public State attempt(Statistics aSuccess, Statistics aFailure, Throwable aMaybeIssue) {		
					return when.test(aSuccess, aFailure) ? 
						aStates.newStateInstance(to, null): // TODO: create promote issue 
						aStates.newStateInstance(from, null); // TODO: create demote issue
				}
				
				@Override
				public String toString() {
					return to + " when:" + when.toString();
				}
			};
		}
	}
	
	public static TransitionBuilder demote(String aStateName) {
		return new TransitionBuilder(aStateName, true);
	}
	
	public static TransitionBuilder promote(String aStateName) {
		return new TransitionBuilder(aStateName, false);
	}
}

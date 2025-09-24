package com.sjl.health;

public interface HealthInfo {
	
	public StateInfo getCurrentState();
	public History getHistory();
	
	public void addListener(HealthListener aListener);
	public void removeListener(HealthListener aListener);
	
	public interface StateInfo {		
		public String getName();
		public Instant getWhenChanged();
		public Issue getWhyChanged();
		public Statistics getSuccessStats();
		public Statistics getFailureStats();
		public Statistics getTotalStats();
		public Issues getDistinctIssues();	
	}
	
	public interface History extends InternallyIterable<StateInfo> {}
}

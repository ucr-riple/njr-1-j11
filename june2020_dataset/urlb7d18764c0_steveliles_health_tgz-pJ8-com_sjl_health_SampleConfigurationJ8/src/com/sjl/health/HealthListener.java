package com.sjl.health;

public interface HealthListener {

	public void onChange(HealthInfo.StateInfo aFrom, HealthInfo.StateInfo aTo);
	
}

package com.sjl.health.internal;

import com.sjl.health.HealthInfo.History;
import com.sjl.health.HealthInfo.StateInfo;

public interface HistoryManager {

	public History get();
	
	public void add(StateInfo aStateInfo);
	
}

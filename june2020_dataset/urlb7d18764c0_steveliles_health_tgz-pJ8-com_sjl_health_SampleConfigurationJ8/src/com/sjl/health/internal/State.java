package com.sjl.health.internal;

import com.sjl.health.*;

public interface State extends HealthInfo.StateInfo {

	public State success();
	
	public State failure(Throwable aThrowable);
	
}

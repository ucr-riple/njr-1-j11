package com.sjl.health.internal;

public interface OperationMonitor {

	public void success();
	public void failure(Throwable aCause);

}

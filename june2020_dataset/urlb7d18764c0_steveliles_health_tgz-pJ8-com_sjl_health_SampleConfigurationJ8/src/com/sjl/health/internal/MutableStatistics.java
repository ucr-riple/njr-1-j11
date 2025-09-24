package com.sjl.health.internal;

import com.sjl.health.*;

public interface MutableStatistics extends Statistics {

	/**
	 * add an occurrence of the event that this statistic is being
	 * used to track.
	 */
	public long increment();
	
	/**
	 * @return a snapshot in time of the state of the statistics
	 * at the time the snapshot is taken.
	 */
	public Statistics snapshot();
	
}

package com.sjl.health.internal;

import com.sjl.health.*;
import com.sjl.health.internal.immutable.*;

public class ThreadSafeMutableStatistics implements MutableStatistics {

	private final Clock clock;
	private final Instant created;
	private final Object lock;
	
	private long counter;
	
	public ThreadSafeMutableStatistics(Clock aClock) {
		clock = aClock;
		created = ImmutableInstant.create(clock.now());
		counter = 0;
		lock = new Object();
	}
	
	@Override
	public long getOccurrenceCount() {
		synchronized(lock) {
			return counter;
		}
	}

	/**
	 * @return a "live" view of the frequency - repeated calls to getHertz
	 * will return updated values. To get a static value at the time of the
	 * call, use snapshot().getFrequency()
	 */
	@Override
	public Frequency getFrequency() {
		return new Frequency() {
			@Override
			public double getHertz() {
				synchronized(lock) {
					return (counter / getPeriod().getMilliseconds()) / 1000d;
				}
			}
		};
	}

	/**
	 * @return a "live" view of the duration - repeated calls to getEnd or
	 * getMilliseconds will return updated values. To get a static value at the
	 * time of the call, use snapshot.getDuration()
	 */
	@Override
	public TimePeriod getPeriod() {
		return new TimePeriod() {
			@Override
			public Instant getStart() {
				return created;
			}

			@Override
			public Instant getEnd() {
				return clock.now();
			}

			@Override
			public long getMilliseconds() {
				return getEnd().getClockTime() - created.getClockTime();
			}
		};
	}

	@Override
	public long increment() {
		synchronized(lock) {
			counter++;
			return counter;
		}
	}

	/**
	 * @return a point in time immutable snapshot of the statistics
	 */
	@Override
	public Statistics snapshot() {
		Instant _end = null;
		long _count = 0;
		
		synchronized(lock) {
			_end = clock.now();
			_count = counter;
		}
		
		return ImmutableStatistics.create(
			ImmutableTimePeriod.create(created, _end), _count);
	}	
}

package com.sjl.health.internal.immutable;

import com.sjl.health.*;

public final class ImmutableStatistics implements Statistics {

	public static ImmutableStatistics create(TimePeriod aPeriod, long aCount) {
		if (aPeriod == null)
			throw new IllegalArgumentException("period cannot be null");
		
		return new ImmutableStatistics(
			ImmutableTimePeriod.create(aPeriod), aCount);
	}
	
	public static ImmutableStatistics create(Statistics aStatistics) {
		if (aStatistics == null)
			return null;
		if (aStatistics instanceof ImmutableStatistics)
			return (ImmutableStatistics) aStatistics;
		
		return new ImmutableStatistics(
			ImmutableTimePeriod.create(aStatistics.getPeriod()), 
			aStatistics.getOccurrenceCount());
	}
	
	public static ImmutableStatistics combine(Statistics aS1, Statistics aS2) {
		Instant _start = ImmutableInstant.earliest(
			aS1.getPeriod().getStart(),
			aS2.getPeriod().getStart());
			
		Instant _end = ImmutableInstant.latest(
			aS1.getPeriod().getEnd(),
			aS2.getPeriod().getEnd());
		
		return ImmutableStatistics.create(
			ImmutableTimePeriod.create(_start, _end),
			aS1.getOccurrenceCount() + aS2.getOccurrenceCount());
	}
	
	private final ImmutableTimePeriod period;
	private final ImmutableFrequency frequency;
	private final long count;
	
	private ImmutableStatistics(
		ImmutableTimePeriod aPeriod, long aCount) {
		period = aPeriod;
		count = aCount;
		frequency = ImmutableFrequency.create(count, aPeriod.getMilliseconds());
	}
	
	@Override
	public long getOccurrenceCount() {
		return count;
	}
	
	@Override
	public Frequency getFrequency() {
		return frequency;
	}
	
	@Override
	public TimePeriod getPeriod() {
		return period;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (count ^ (count >>> 32));
		result = prime * result + period.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ImmutableStatistics other = (ImmutableStatistics) obj;
		if (count != other.count)
			return false;
		if (!period.equals(other.period))
			return false;
		return true;
	}
}

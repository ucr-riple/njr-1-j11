package com.sjl.health.internal.immutable;

import com.sjl.health.*;

public final class ImmutableTimePeriod implements TimePeriod {

	public static final ImmutableTimePeriod create(Instant aStart, Instant anEnd) {
		if (aStart == null)
			throw new IllegalArgumentException("start cannot be null");
		if (anEnd == null)
			throw new IllegalArgumentException("end cannot be null");
		
		return new ImmutableTimePeriod(
			ImmutableInstant.create(aStart), 
			ImmutableInstant.create(anEnd));
	}
	
	public static final ImmutableTimePeriod create(TimePeriod aDuration) {
		if (aDuration == null)
			throw new IllegalArgumentException("duration cannot be null");
		
		if (aDuration instanceof ImmutableTimePeriod) 
			return (ImmutableTimePeriod) aDuration;
		
		return ImmutableTimePeriod.create(
			aDuration.getStart(), aDuration.getEnd());
	}
	
	private final Instant start;
	private final Instant end;
	
	private ImmutableTimePeriod(Instant aStart, Instant anEnd) {
		start = aStart;
		end = anEnd;
	}
	
	@Override
	public Instant getStart() {
		return start;
	}

	@Override
	public Instant getEnd() {
		return end;
	}

	@Override
	public long getMilliseconds() {
		return end.getClockTime() - start.getClockTime();
	}

	@Override
	public int hashCode() {
		final int _prime = 31;
		int _result = 1;
		_result = _prime * _result + end.hashCode();
		_result = _prime * _result + start.hashCode();
		return _result;
	}

	@Override
	public boolean equals(Object anObject) {
		if (this == anObject)
			return true;
		if (anObject == null)
			return false;
		if (getClass() != anObject.getClass())
			return false;
		
		ImmutableTimePeriod _other = (ImmutableTimePeriod) anObject;
		return start.equals(_other.start) && end.equals(_other.end);
	}
}

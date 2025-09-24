package com.sjl.health.internal.immutable;

import com.sjl.health.*;

public final class ImmutableInstant implements Instant {

	public static ImmutableInstant create(long aClockTime) {
		return new ImmutableInstant(aClockTime);
	}
	
	public static ImmutableInstant create(Instant anInstant) {
		if (anInstant == null)
			return null;
		
		if (anInstant instanceof ImmutableInstant)
			return (ImmutableInstant) anInstant;
		
		return new ImmutableInstant(anInstant.getClockTime());
	}
	
	public static ImmutableInstant earliest(Instant... anInstants) {
		if (anInstants.length == 0)
			return null;
		
		Instant _earliest = anInstants[0];
		for (Instant _i : anInstants) {
			if (_earliest.after(_i))
				_earliest = _i;
		}
		
		return ImmutableInstant.create(_earliest);
	}
	
	public static ImmutableInstant latest(Instant... anInstants) {
		if (anInstants.length == 0)
			return null;
		
		Instant _latest = anInstants[0];
		for (Instant _i : anInstants) {
			if (_latest.before(_i))
				_latest = _i;
		}
		
		return ImmutableInstant.create(_latest);
	}
	
	private final long clockTime;
	
	private ImmutableInstant(long aClockTime) {
		clockTime = aClockTime;
	}
	
	@Override
	public long getClockTime() {
		return clockTime;
	}
	
	public boolean before(Instant anInstant) {
		return clockTime < anInstant.getClockTime();
	}
	
	public boolean after(Instant anInstant) {
		return clockTime > anInstant.getClockTime();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (clockTime ^ (clockTime >>> 32));
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
		ImmutableInstant other = (ImmutableInstant) obj;
		if (clockTime != other.clockTime)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return clockTime + "";
	}
}

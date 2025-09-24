package com.sjl.health.internal.immutable;

import com.sjl.health.*;

public final class ImmutableFrequency implements Frequency {

	private static ImmutableFrequency MAX_VALUE = new ImmutableFrequency(Double.MAX_VALUE);
	
	public static ImmutableFrequency create(long aCount, long aDurationMillis) {
		if (aDurationMillis == 0)
			return ImmutableFrequency.MAX_VALUE;
		
		return new ImmutableFrequency((aCount / aDurationMillis) / 1000d);
	}
	
	private final double hertz;
	
	private ImmutableFrequency(double aHertz) {
		hertz = aHertz;
	}
	
	@Override
	public double getHertz() {
		return hertz;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(hertz);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		ImmutableFrequency other = (ImmutableFrequency) obj;
		if (Double.doubleToLongBits(hertz) != Double
				.doubleToLongBits(other.hertz))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return hertz + "";
	}
}

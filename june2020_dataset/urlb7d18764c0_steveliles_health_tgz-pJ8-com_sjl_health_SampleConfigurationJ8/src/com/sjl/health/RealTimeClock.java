package com.sjl.health;

import com.sjl.health.internal.*;
import com.sjl.health.internal.immutable.*;

public class RealTimeClock implements Clock {

	@Override
	public Instant now() {
		return ImmutableInstant.create(System.currentTimeMillis());
	}

}

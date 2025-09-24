package com.sjl.health;

public interface InternallyIterable<T> {

	public void each(Callback<T> aCallback);
	
	interface Callback<T> {
		public void with(T aT);		
	}
}

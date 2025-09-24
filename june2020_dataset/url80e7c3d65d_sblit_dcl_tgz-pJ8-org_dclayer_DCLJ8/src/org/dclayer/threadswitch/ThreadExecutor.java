package org.dclayer.threadswitch;

public interface ThreadExecutor<T> {

	public void exec(ThreadEnvironment<T> threadEnvironment, T object);
	
}

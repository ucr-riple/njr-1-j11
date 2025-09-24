package org.dclayer.threadswitch;

public class ThreadSwitch<T> {
	
	private ThreadEnvironment<T> first;
	
	private Class<? extends T> tClass;
	
	private int envId = 0;
	
	public ThreadSwitch(Class<? extends T> tClass) {
		this.tClass = tClass;
		this.first = new ThreadEnvironment<>(this, envId++);
	}
	
	public synchronized ThreadEnvironment<T> get() {
		
		if(first == null) {
			return new ThreadEnvironment<>(this, envId++);
		}
		
		ThreadEnvironment<T> threadEnvironment = first;
		first = threadEnvironment.next;
		
		return threadEnvironment;
		
	}
	
	public synchronized void requeue(ThreadEnvironment<T> threadEnvironment) {
		
		threadEnvironment.next = first;
		first = threadEnvironment;
		
	}
	
	protected T newT() {
		try {
			return tClass.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
			return null;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

}

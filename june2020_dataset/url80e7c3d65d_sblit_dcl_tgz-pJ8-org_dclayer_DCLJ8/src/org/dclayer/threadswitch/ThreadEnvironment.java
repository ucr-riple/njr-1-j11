package org.dclayer.threadswitch;


public class ThreadEnvironment<T> implements Runnable {
	
	private T object;
	private ThreadSwitch<T> threadSwitch;
	
	protected ThreadEnvironment<T> next;
	
	private ThreadExecutor<T> threadExecutor;
	private Thread thread;
	
	private int id;
	
	public ThreadEnvironment(ThreadSwitch<T> threadSwitch, int id) {
		
		this.threadSwitch = threadSwitch;
		this.id = id;
		
		this.object = threadSwitch.newT();
		
		this.thread = new Thread(this);
		this.thread.start();
		
	}
	
	public T getObject() {
		return object;
	}
	
	public synchronized void exec(ThreadExecutor<T> threadExecutor) {
		
		this.threadExecutor = threadExecutor;
		notify();
		
	}

	@Override
	public void run() {
		
		for(;;) {
		
			synchronized(this) {
				if(threadExecutor == null) {
					try {
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
						return;
					}
				}
			}
			
			threadExecutor.exec(this, object);
			
			synchronized(this) {
				threadExecutor = null;
			}
			
			threadSwitch.requeue(this);
		
		}
			
	}
	
	@Override
	public String toString() {
		return String.format("thread environment %d", id);
	}
	
}

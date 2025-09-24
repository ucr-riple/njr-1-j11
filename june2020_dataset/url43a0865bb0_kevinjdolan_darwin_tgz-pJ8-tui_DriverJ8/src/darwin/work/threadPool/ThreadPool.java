package darwin.work.threadPool;

import java.util.HashMap;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.Vector;

import darwin.work.Job;
import darwin.work.JobRunner;
import darwin.work.BusyException;

/**
 * The ThreadPool uses a finite number of threads to distribute
 * the work
 * @author 
 */
public class ThreadPool extends Thread implements JobRunner {

	private Vector<Object> result;
	private Queue< Pair<Job, Object> > queue;
	private Worker[] workers;
	private boolean running;
	private HashMap<String, Object> shared;
	private boolean alive;
	
	public ThreadPool(int numThreads) {
		super("Pool");
		queue = new ConcurrentLinkedQueue< Pair<Job, Object> >();
		workers = new Worker[numThreads];
		for(int i = 0; i < numThreads; i++)
			workers[i] = new Worker(this);
		running = false;
		alive = true;
		shared = new HashMap<String, Object>();
		start();
	}
	
	@Override
	public void addJob(Job job, Object parameters) throws BusyException {
		if(running)
			throw new BusyException();
		queue.add(new Pair<Job, Object>(job, parameters));
	}

	@Override
	public void putSharedData(String name, Object value) {
		shared.put(name, value);
		for(Worker worker : workers)
			worker.putSharedData(name, value);
	}

	@Override
	public List<Object> dispatch() throws BusyException {
		if(running)
			throw new BusyException();
		
		running = true;
		result = new Vector<Object>();
		
		//Wait for completion
		while(running) {
			try { sleep(100); }
			catch(InterruptedException e) {}
		}
		
		return result;
	}

	/**
	 * Notify this thread pool of a job completion
	 * @param result the result object
	 */
	public void notifyResult(Object result) {
		this.result.add(result);
		interrupt();
	}
	
	/**
	 * Kill this and all worker threads
	 */
	public void killThreads() {
		alive = false;
		for(Worker worker : workers)
			worker.killThread();
	}
	
	@Override
	public void run() {
		while(alive) {
			if(running) {
				//Empty the queue
				while(!queue.isEmpty()) {
					for(Worker worker : workers) {
						if(!queue.isEmpty() && !worker.isBusy()) {
							Pair<Job, Object> current = queue.poll();
							try {
								worker.doWork(current.fst(), current.snd());
							} catch (BusyException e) {
								//Logically inaccessible
								e.printStackTrace();
							}
						}
					}
					try {
						sleep(10);
					}
					catch(InterruptedException e) {}
				}
				
				//Wait for completion
				while(running) {
					boolean busy = false;
					for(Worker worker : workers)
						if(worker.isBusy())
							busy = true;
					if(busy) 
						try { sleep(10); }
						catch(InterruptedException e) {}
					else
						running = false;
				}
			}
			
			try { sleep(10); }
			catch(InterruptedException e) {}
		}
	}
}

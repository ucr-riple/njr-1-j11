package darwin.work.threadPool;

import java.util.HashMap;

import darwin.work.BusyException;
import darwin.work.Job;

/**
 * The worker class contains a single worker thread
 * @author Kevin Dolan
 */
public class Worker extends Thread{
	
	private ThreadPool master;
	private boolean alive, busy;
	private Job job;
	private Object parameters;
	private HashMap<String, Object> shared;
	
	/**
	 * Create a new worker, with a given master
	 * @param master the threadpool to which this reports
	 */
	public Worker(ThreadPool master) {
		super("Worker");
		this.master = master;
		busy = false;
		alive = true;
		shared = new HashMap<String, Object>();
		start();
	}
	
	/**
	 * Put a new value to the shared data object
	 * @param name  the name of the field
	 * @param value the value to put
	 */
	public void putSharedData(String name, Object value) {
		shared.put(name, value);
	}
	
	/**
	 * @return true if this worker is processing something
	 */
	public boolean isBusy() {
		return busy;
	}
	
	/**
	 * Start this worker on a job
	 * @param job 		 the job to be performed
	 * @param parameters the parameters to be passed
	 * @throws WorkerBusyException if this worker is already working
	 */
	public void doWork(Job job, Object parameters) throws BusyException {
		if(busy)
			throw new BusyException();
		busy = true;
		this.job = job;
		this.parameters = parameters;
		interrupt();
	}
	
	@Override
	public void run() {
		while(alive) {
			if(busy) {
				Object result = job.run(parameters, shared);
				master.notifyResult(result);
				busy = false;
			}
			
			try { sleep(10); }
			catch(InterruptedException e) {}
		}
	}
	
	/**
	 * Stop this thread from all activity, after completion of current task
	 */
	public void killThread() {
		alive = false;
	}
	
}

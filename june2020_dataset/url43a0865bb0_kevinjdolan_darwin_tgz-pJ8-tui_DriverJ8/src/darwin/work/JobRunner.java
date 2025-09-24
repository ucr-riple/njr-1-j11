package darwin.work;

import java.util.List;

/**
 * JobRunner is an interface for handling the dispatching of
 * jobs, abstracting everything out so it can easily be modified
 * to work with JPPF
 * @author Kevin Dolan
 */
public interface JobRunner {
	
	/**
	 * Add data that can be accessed by any job,
	 * replace the value if it already exists
	 * @param name  the name of the data field
	 * @param value the value of the data
	 */
	public void putSharedData(String name, Object value);
	
	/**
	 * Add a job to be performed
	 * @param job 		 the job to add
	 * @param parameters whatever parameters may be necessary
	 * @throws BusyException if the runner is currently processing
	 */
	public void addJob(Job job, Object parameters) throws BusyException;
	
	/**
	 * Dispatch the work in the queue
	 * @return a list of all result objects
	 * @throws BusyException 
	 */
	public List<Object> dispatch() throws BusyException;
	
}

package darwin.work;

import java.util.HashMap;

/**
 * The Job abstraction is used to store a single job that
 * can be dispatched, this interface is 
 * @author Kevin Dolan
 */
public interface Job {

	/**
	 * Do whatever this job is supposed to do
	 * @param parameters whatever parameters may be necessary
	 * @param shared	 the shared data object
	 * @return			 the return value
	 */
	public abstract Object run(Object parameters, HashMap<String, Object> shared);
	
}
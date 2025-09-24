package darwin.fitness.multiobjective;

import darwin.work.Job;

/**
 * This interface should be implemented as a means of performing
 * multiobjective calculations
 * @author Kevin Dolan
 */
public interface Multiobjective {

	/**
	 * @return the job object, representing this calculation
	 */
	public Job getJob();
	
	/**
	 * Convert the raw fitness to standardized fitness
	 * @param raw the raw fitness value
	 * @return 	  the standardized fitness value
	 */
	public double standardizeFitness(double raw);
}

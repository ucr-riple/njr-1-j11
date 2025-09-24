package darwin.fitness;

import darwin.population.Individual;

/**
 * Simple class holding the input of a fitness job
 * @author Kevin Dolan
 */
public class FitnessJobInput {

	/**
	 * The index of the population this job accesses
	 */
	public int populationIndex;
	
	/**
	 * The index of the individual this job accesses
	 */
	public int individualIndex;
	
	/**
	 * The reference to the individual this job accesses
	 */
	public Individual individual;
	
	
}

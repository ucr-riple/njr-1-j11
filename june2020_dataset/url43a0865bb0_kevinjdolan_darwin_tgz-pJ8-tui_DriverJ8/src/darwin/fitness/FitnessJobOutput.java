package darwin.fitness;

/**
 * Simple class holding the output of a fitness job
 * @author Kevin Dolan
 */
public class FitnessJobOutput {

	/**
	 * The index of the population this job accesses
	 */
	public int populationIndex;
	
	/**
	 * The index of the individual this job accesses
	 */
	public int individualIndex;
	
	/**
	 * The fitness array result of this calculation
	 */
	public double[] result;
	
	
	/**
	 * The complexity of the individual
	 */
	public int complexity;
}

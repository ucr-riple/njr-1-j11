package darwin.fitness.multiobjective;

/**
 * Simple class holding the output of a domination job
 * @author Kevin Dolan
 */
public class MultiobjectiveJobOutput {

	/**
	 * The index of the population this job accesses
	 */
	public int populationIndex;
	
	/**
	 * The index of the individual this job accesses
	 */
	public int individualIndex;
	
	/**
	 * The number of other individuals this individual dominates
	 */
	public double result;
	
}

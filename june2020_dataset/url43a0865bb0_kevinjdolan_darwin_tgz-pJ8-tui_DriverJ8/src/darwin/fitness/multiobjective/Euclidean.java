package darwin.fitness.multiobjective;

import darwin.work.Job;

/**
 * Calculates multi-objective score by euclidean distance sum of
 * adjusted fitness calculations
 * @author Kevin Dolan
 */
public class Euclidean implements Multiobjective {

	private double max;
	
	/**
	 * @param popSize the total size of the population
	 */
	public Euclidean(int numMetrics) {
		this.max = Math.sqrt(numMetrics);
	}
	
	@Override
	public Job getJob() {
		return new EuclideanJob();
	}

	@Override
	public double standardizeFitness(double raw) {
		return max - raw;
	}

}

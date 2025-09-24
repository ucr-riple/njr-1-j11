package darwin.fitness.multiobjective;

import darwin.work.Job;

/**
 * Calculates pareto-dominations
 * @author Kevin Dolan
 */
public class Pareto implements Multiobjective {

	private int popSize;
	
	/**
	 * @param popSize the total size of the population
	 */
	public Pareto(int popSize) {
		this.popSize = popSize;
	}
	
	@Override
	public Job getJob() {
		return new DominationJob();
	}

	@Override
	public double standardizeFitness(double raw) {
		return popSize - raw;
	}

}

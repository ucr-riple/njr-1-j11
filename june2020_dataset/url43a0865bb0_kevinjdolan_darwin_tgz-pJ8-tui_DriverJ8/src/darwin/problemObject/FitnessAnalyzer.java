package darwin.problemObject;

import darwin.population.Individual;

public abstract class FitnessAnalyzer {

	/**
	 * @return the number of fitness metrics
	 */
	public abstract int getNumberMetrics();
	
	/**
	 * Assess the fitness of a population
	 * @param population the population to assess
	 * @return			 a result array such that result[n] is the n'th fitness metric 
	 */
	public abstract double[] assessFitness(Individual individual, Object environment);
	
	/**
	 * Convert the raw fitness to standardized (positive, lower is better)
	 * @param fitnessMetric the index of the fitness metric
	 * @param fitness		the raw fitness value
	 * @return				the standardized fitness
	 */
	public abstract double standardize(int fitnessMetric, double fitness);
	
	/**
	 * Return the name of a fitness metric, defaults to blank string
	 * @param which index of fitness metric
	 * @return		the fitness metric's name
	 */
	public String getFitnessName(int which) {
		return "";
	}
	
	/**
	 * Invoked when a generation has completed
	 */
	public void generationComplete() {}
	
	public abstract FitnessAnalyzer clone();
	
}
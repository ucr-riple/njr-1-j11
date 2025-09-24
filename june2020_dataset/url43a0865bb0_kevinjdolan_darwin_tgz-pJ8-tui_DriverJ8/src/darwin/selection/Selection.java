package darwin.selection;

import darwin.fitness.Fitness;
import darwin.population.Individual;
import darwin.population.Population;

/**
 * The selection interface should be implemented by any objects used
 * for the fitness-based selection of individuals
 * @author Kevin Dolan
 */
public abstract class Selection {

	protected Fitness fitness;
	protected Population[] universe;
	
	/**
	 * Set the array of populations to reference
	 * @param universe the array of populations
	 */
	public void setUniverse(Population[] universe) {
		this.universe = universe;
	}
	
	/**
	 * Set the fitness object to access
	 * @param fitness the fitness object
	 */
	public void setFitness(Fitness fitness) {
		this.fitness = fitness;
	}
	
	/**
	 * Select an individual from the population
	 * @param populationIndex the population to select from
	 * @return				  the index of the individual
	 */
	public abstract int select(int populationIndex);
	
	/**
	 * Select an individual from the population
	 * @param populationIndex the population to select from
	 * @return				  the reference to the individual
	 */
	public Individual selectIndividual(int populationIndex) {
		int choice = select(populationIndex);
		return universe[populationIndex].getIndividual(choice);
	}
}

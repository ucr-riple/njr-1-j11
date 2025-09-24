package darwin.population;

import java.util.List;

/**
 * Stores information about a single population
 * @author Kevin Dolan
 */
public class Population {

	private List<Individual> individuals;
	private Object environment;
	
	/**
	 * Instantiate a population
	 * @param individuals  the list of individuals
	 * @param environment the problem-specific environment object
	 */
	public Population(List<Individual> individuals, Object environment) {
		this.individuals = individuals;
		this.environment = environment;
	}
	
	/**
	 * @return the list of individuals
	 */
	public List<Individual> getIndividuals() {
		return individuals;
	}
	
	/**
	 * @return the problem-specific environment object
	 */
	public Object getEnvironment() {
		return environment;
	}
	
	/**
	 * @return the size of the population
	 */
	public int getPopulationSize() {
		return individuals.size();
	}
	
	/**
	 * Get an individual
	 * @param i index of the individual to get
	 * @return  the individual object
	 */
	public Individual getIndividual(int i) {
		return individuals.get(i);
	}
	
	/**
	 * Set the list of individuals
	 * @param individuals the list of individuals
	 */
	public void setIndividuals(List<Individual> individuals) {
		this.individuals = individuals;
	}
}

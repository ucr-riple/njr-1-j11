package darwin.initialization;

import darwin.population.Individual;
import darwin.problemObject.NodeFactory;
import java.util.List;

/**
 * Interface for the initial population generation
 * @author Kevin Dolan
 */
public interface Initializer {

	/**
	 * Generate an initial population
	 * @param number 	  the number of individuals to create
	 * @param depth	 	  the depth parameter
	 * @param size		  the size parameter
	 * @param nodeFactory the node factory to use
	 * @return			  the list of individuals
	 */
	public List<Individual> initialize(int number, int depth, int size, NodeFactory nodeFactory);
	
}

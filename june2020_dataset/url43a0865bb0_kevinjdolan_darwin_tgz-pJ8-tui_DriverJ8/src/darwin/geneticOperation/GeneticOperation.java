package darwin.geneticOperation;

import darwin.population.Individual;

/**
 * This interface should be implemented by various
 * genetic operations; you can assume this receives
 * copies, so any modifications are allowed
 * @author Kevin Dolan
 */
public interface GeneticOperation {

	/**
	 * @return the number of individual inputs to this operation
	 */
	public int getNumberInputs();
	
	/**
	 * Perform this operation
	 * @param inputs   array of the individuals to input
	 * @param maxSize  the maximum size any child should be 
	 * @param function whether or not to choose a function, if applicable
	 * @return		   array of individuals returned
	 */
	public Individual[] performOperation(Individual[] inputs, int maxSize, boolean function) throws UnableToPerformOperationException;
	
}

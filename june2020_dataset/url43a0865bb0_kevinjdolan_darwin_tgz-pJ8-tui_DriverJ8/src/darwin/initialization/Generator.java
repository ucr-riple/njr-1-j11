package darwin.initialization;

import darwin.geneticOperation.UnableToPerformOperationException;
import darwin.population.Node;

/**
 * This interface should be implemented by all initial tree generators
 * @author Kevin Dolan
 */
public interface Generator {
	
	/**
	 * Create a new tree
	 * @param depth		 the depth parameter
	 * @param size		 the size parameter
	 * @param returnType the expected return type
	 * @return	   the created tree
	 * @throws UnableToPerformOperationException 
	 */
	public Node generate(int depth, int size, Class<?> returnType) throws UnableToPerformOperationException;
	
}

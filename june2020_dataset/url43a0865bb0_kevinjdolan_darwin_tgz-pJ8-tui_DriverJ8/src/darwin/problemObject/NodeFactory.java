package darwin.problemObject;

import darwin.initialization.NoAvailableNodeException;
import darwin.nodeFilter.Filter;
import darwin.population.Node;

/**
 * Interface to be implemented by all Node creation objects
 * @author Kevin
 */

public interface NodeFactory {

	/**
	 * Return a node applied the given filter
	 * @param filter the filter to be applied
	 * @return		 a node object
	 * @throws NoAvailableNodeException if the filter returns no results
	 */
	public Node getNode(Filter filter) throws NoAvailableNodeException;
	
	/**
	 * @return the expected return types of the trees
	 */
	public Class<?>[] getReturnTypes();
	
}

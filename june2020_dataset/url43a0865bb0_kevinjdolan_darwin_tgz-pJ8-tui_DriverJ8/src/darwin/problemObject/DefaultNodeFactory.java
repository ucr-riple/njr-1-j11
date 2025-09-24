package darwin.problemObject;

import darwin.initialization.NoAvailableNodeException;
import darwin.nodeFilter.Filter;
import java.util.ArrayList;
import java.util.List;

import darwin.population.Node;

public abstract class DefaultNodeFactory implements NodeFactory {

	/**
	 * @return the list of all available nodes 
	 */
	public abstract List<NodeType> getAvailableNodes();
	
	public Node getNode(Filter filter) throws NoAvailableNodeException {
		List<NodeType> available = getAvailableNodes();
		ArrayList<NodeType> accepted = new ArrayList<NodeType>();
		
		for(NodeType current : available)
			if(filter.accept(current))
				accepted.add(current);
		
		if(accepted.size() == 0)
			throw new NoAvailableNodeException();
		
		int choice = (int) (Math.random() * accepted.size());
		
		NodeType pick = accepted.get(choice);
		
		return new Node(pick);
	}

}

package darwin.initialization;

import darwin.geneticOperation.UnableToPerformOperationException;
import darwin.nodeFilter.ReturnType;
import darwin.nodeFilter.Terminals;
import darwin.nodeFilter.Functions;
import darwin.population.Node;
import darwin.population.TypeMismatchException;
import darwin.problemObject.NodeFactory;

/**
 * Generator representing the Full method, as described by Koza
 * @author Kevin Dolan
 */
public class Full implements Generator {

	private NodeFactory nodeFactory;
	
	public Full(NodeFactory nodeFactory) {
		this.nodeFactory = nodeFactory;
	}
	
	@Override
	public Node generate(int depth, int size, Class<?> returnType) throws UnableToPerformOperationException {
		Node current;
		
		try {
			if(depth > 1 && size > 1) 
				current = nodeFactory.getNode(new Functions(returnType));
			else 
				current = nodeFactory.getNode(new Terminals(returnType));
		}
		catch(NoAvailableNodeException e) {
			try {
				current = nodeFactory.getNode(new ReturnType(returnType));
			} catch (NoAvailableNodeException e1) {
				throw new UnableToPerformOperationException();
			}
		}
		
		size--;
		int arity = current.getArity();
		for(int i = 0; i < arity; i++)
			try {
				Node node = generate(depth - 1, size, current.getChildType(i));
				current.setChildNode(i, node);
				size -= node.getSize(true);
			} catch (TypeMismatchException e) {
				//Logically inaccessible
				e.printStackTrace();
			}
		
		return current;
	}

}

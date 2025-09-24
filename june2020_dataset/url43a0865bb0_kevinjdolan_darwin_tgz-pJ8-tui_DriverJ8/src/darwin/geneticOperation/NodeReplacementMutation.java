package darwin.geneticOperation;

import java.util.List;
import darwin.initialization.NoAvailableNodeException;
import darwin.nodeFilter.All;
import darwin.nodeFilter.Signature;
import darwin.population.Individual;
import darwin.population.Node;
import darwin.population.TypeMismatchException;
import darwin.problemObject.NodeFactory;

public class NodeReplacementMutation implements GeneticOperation {

	private NodeFactory nodeFactory;
	
	public NodeReplacementMutation(NodeFactory nodeFactory) {
		this.nodeFactory = nodeFactory;
	}
	
	@Override
	public int getNumberInputs() {
		return 1;
	}

	@Override
	public Individual[] performOperation(Individual[] inputs, int maxSize, boolean function) throws UnableToPerformOperationException {
		Individual father = inputs[0];
		
		List<Node> yList = father.filterTrees(new All());
		int random = (int) (Math.random() * yList.size());
		Node yChromosome = yList.get(random);
		
		Class<?> expectedType = yChromosome.getExpectedType();
		
		Class<?>[] childTypes = new Class<?>[yChromosome.getArity()];
		for(int i = 0; i < yChromosome.getArity(); i++)
			childTypes[i] = yChromosome.getChildNode(i).getReturnType();
		
		Node replacement;
		try {
			replacement = nodeFactory.getNode(new Signature(expectedType, childTypes));
		} catch (NoAvailableNodeException e) {
			//Logically inaccessible
			e.printStackTrace();
			replacement = null;
		}
		
		Node[] childNodes = yChromosome.getChildNodes();
		
		try {
			for(int i = 0; i < childNodes.length; i++)
				replacement.setChildNode(i, childNodes[i]);
			yChromosome.replace(replacement);
		} catch (TypeMismatchException e) {
			//Logically inaccessible
			e.printStackTrace();
		}
		
		return new Individual[] {father};
	}

}
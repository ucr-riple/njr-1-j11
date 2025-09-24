package darwin.geneticOperation;

import java.util.ArrayList;
import java.util.List;
import darwin.initialization.NoAvailableNodeException;
import darwin.nodeFilter.All;
import darwin.nodeFilter.Functions;
import darwin.nodeFilter.Terminals;
import darwin.population.Individual;
import darwin.population.Node;
import darwin.population.TypeMismatchException;
import darwin.problemObject.NodeFactory;

public class ShrinkMutation implements GeneticOperation {

	private NodeFactory nodeFactory;
	
	public ShrinkMutation(NodeFactory nodeFactory) {
		this.nodeFactory = nodeFactory;
	}
	
	@Override
	public int getNumberInputs() {
		return 1;
	}

	@Override
	public Individual[] performOperation(Individual[] inputs, int maxSize, boolean function) throws UnableToPerformOperationException {
		Individual father = inputs[0];
		
		List<Node> yList = new ArrayList<Node>();
		if(function)
			yList = father.filterTrees(new Functions(Object.class));
		if(yList.size() == 0)
			yList = father.filterTrees(new All());
		
		int random = (int) (Math.random() * yList.size());
		Node yChromosome = yList.get(random);
		
		Class<?> expectedType = yChromosome.getExpectedType();
		
		Node replacement;
		try {
			replacement = nodeFactory.getNode(new Terminals(expectedType));
		} catch (NoAvailableNodeException e1) {
			throw new UnableToPerformOperationException();
		}
		
		try {
			yChromosome.replace(replacement);
		} catch (TypeMismatchException e) {
			//Logically inaccessible
			e.printStackTrace();
		}
		
		return new Individual[] {father};
	}

}

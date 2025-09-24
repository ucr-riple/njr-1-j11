package darwin.geneticOperation;

import java.util.List;
import darwin.nodeFilter.Constant;
import darwin.population.Individual;
import darwin.population.Node;

public class ConstantMutation implements GeneticOperation {
	
	@Override
	public int getNumberInputs() {
		return 1;
	}
	
	@Override
	public Individual[] performOperation(Individual[] inputs, int maxSize, boolean function) throws UnableToPerformOperationException {
		Individual father = inputs[0];
		
		List<Node> yList = father.filterTrees(new Constant());
		
		if(yList.size() == 0)
			throw new UnableToPerformOperationException();
		
		int random = (int) (Math.random() * yList.size());
		Node yChromosome = yList.get(random);
		
		yChromosome.setProcessable(yChromosome.getNodeType().getProcessable());
		
		return new Individual[] {father};
	}

}

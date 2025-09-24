package darwin.geneticOperation;

import java.util.ArrayList;
import java.util.List;
import darwin.population.Individual;
import darwin.population.Node;
import darwin.population.TypeMismatchException;
import darwin.nodeFilter.*;

public class SubtreeCrossover implements GeneticOperation {

	@Override
	public int getNumberInputs() {
		return 2;
	}

	@Override
	public Individual[] performOperation(Individual[] inputs, int maxSize, boolean function) throws UnableToPerformOperationException {
		Individual father = inputs[0];
		Individual mother = inputs[1];
			
		List<Node> yList = new ArrayList<Node>();
		if(function)
			yList = father.filterTrees(new Functions(Object.class));
		if(yList.size() == 0)
			yList = father.filterTrees(new All());
		
		int random = (int) (Math.random() * yList.size());
		Node yChromosome = yList.get(random);
		
		int biggest = maxSize - father.getComplexity() + yChromosome.getSize(true);
		
		Class<?> expectedType = yChromosome.getExpectedType();
			
		List<Node> xList = mother.filterTrees(
			new HeightRange(expectedType, 0, yChromosome.getHeight(true) + 1) );
		
		if(xList.size() == 0)
			throw new UnableToPerformOperationException();
		
		random = (int) (Math.random() * xList.size());
		Node xChromosome = xList.get(random);
		
		if(xChromosome.getSize(true) > biggest) {
			List<Node> list = xChromosome.filterSubtree(new SizeRange(expectedType, 0, biggest), true);
			if(list.size() == 0)
				throw new UnableToPerformOperationException();
			xChromosome = list.get((int) (Math.random() * list.size()));
		}
		
		Individual[] result;
		try {
			yChromosome.swap(xChromosome);
			//Normally, two results
			if(mother.getComplexity() > maxSize)
				result = new Individual[] {father};
			else
				result = new Individual[] {father, mother};
		} catch (TypeMismatchException e) {
			//But not guaranteed
			result = new Individual[] {father};
		}
		
		return result;
	}

}
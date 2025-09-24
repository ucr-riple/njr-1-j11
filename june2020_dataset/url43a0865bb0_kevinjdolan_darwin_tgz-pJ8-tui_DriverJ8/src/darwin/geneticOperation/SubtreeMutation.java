package darwin.geneticOperation;

import java.util.ArrayList;
import java.util.List;
import darwin.initialization.Generator;
import darwin.nodeFilter.All;
import darwin.nodeFilter.Functions;
import darwin.population.Individual;
import darwin.population.Node;
import darwin.population.TypeMismatchException;

public class SubtreeMutation implements GeneticOperation {

	private Generator generator;
	
	public SubtreeMutation(Generator generator) {
		this.generator = generator;
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
		
		int biggest = maxSize - father.getComplexity() + yChromosome.getSize(true);
		int height = yChromosome.getHeight(true) + 1;
		Node newNode = generator.generate(height, biggest, expectedType);
		
		/*if(newNode.getSize(true) > biggest) {
			List<Node> list = newNode.filterSubtree(new SizeRange(expectedType, 0, biggest), true);
			if(list.size() == 0)
				throw new UnableToPerformOperationException();
			newNode = list.get((int) (Math.random() * list.size()));
		}*/
			
		
		try {
			yChromosome.replace(newNode);
		} catch (TypeMismatchException e) {
			//Logically inaccessible
			e.printStackTrace();
		}
		
		return new Individual[] {father};
	}

}

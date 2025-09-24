package darwin.geneticOperation;

import darwin.population.Individual;
import darwin.population.Node;
import darwin.population.TypeMismatchException;

public class Swap implements GeneticOperation {

	@Override
	public int getNumberInputs() {
		return 2;
	}

	@Override
	public Individual[] performOperation(Individual[] inputs, int maxSize, boolean function) throws UnableToPerformOperationException {
		Individual father = inputs[0];
		Individual mother = inputs[1];
		
		int which = (int) (Math.random() * father.getNumTrees());
		
		Node yChromosome = father.getTree(which);
		Node xChromosome = mother.getTree(which);
		
		try {
			father.setTree(which, xChromosome);
			mother.setTree(which, yChromosome);
		} catch (TypeMismatchException e) {
			//Logically inaccessible
			e.printStackTrace();
		}
		
		int fatherSize = father.getComplexity();
		int motherSize = mother.getComplexity();
		
		if(fatherSize <= maxSize && motherSize <= maxSize)
			return new Individual[] {father, mother};
		else if(fatherSize <= maxSize)
			return new Individual[] {father};
		else if(motherSize <= maxSize)
			return new Individual[] {mother};
		else
			throw new UnableToPerformOperationException();
	}

}

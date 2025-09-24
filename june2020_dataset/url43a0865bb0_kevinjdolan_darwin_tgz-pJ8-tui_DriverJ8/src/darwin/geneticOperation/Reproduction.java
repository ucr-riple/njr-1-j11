package darwin.geneticOperation;

import darwin.population.Individual;

/**
 * Duplicate an individual exactly
 * @author Kevin
 */
public class Reproduction implements GeneticOperation {

	@Override
	public int getNumberInputs() {
		return 1;
	}

	@Override
	public Individual[] performOperation(Individual[] inputs, int maxSize, boolean function) {
		return new Individual[] {inputs[0]};
	}

}

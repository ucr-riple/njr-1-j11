package darwin.selection;

/**
 * Select an individual with probability equal to its normalized fitness
 * @author Kevin Dolan
 */
public class FitnessProportional extends Selection {

	@Override
	public int select(int populationIndex) {
		double random = Math.random();
		double sum = 0;
		int popSize = universe[populationIndex].getPopulationSize();
		for(int i = 0; i < popSize; i++) {
			sum += fitness.getNormalizedFitness(populationIndex, i);
			if(random <= sum)
				return i;
		}
		//Should not occur, just return last
		return popSize - 1;
	}

}

package darwin.selection;

/**
 * Perform a tournament selection, select n random individuals and choose
 * the best of n
 * @author Kevin Dolan
 */
public class Tournament extends Selection{

	private int tournamentSize;
	
	public Tournament(int tournamentSize) {
		this.tournamentSize = tournamentSize;
	}
	
	@Override
	public int select(int populationIndex) {
		int[] random = new int[tournamentSize];
		int popSize = universe[populationIndex].getPopulationSize();
		for(int i = 0; i < random.length; i++)
			random[i] = (int) (Math.random() * popSize);
		int winner = random[0];
		double winFit = fitness.getAdjustedFitness(populationIndex, random[0]);
		for(int i = 1; i < random.length; i++) {
			double fit = fitness.getAdjustedFitness(populationIndex, random[i]);
			if(fit > winFit) {
				winFit = fit;
				winner = random[i];
			}
		}
		return winner;
	}

	
	
}

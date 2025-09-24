package darwin.fitness.multiobjective;

import java.util.HashMap;

import darwin.fitness.Fitness;
import darwin.work.Job;

/**
 * Represents a single pareto-dominations calculation
 * @author Kevin Dolan
 */
public class DominationJob implements Job {

	@Override
	public Object run(Object parameters, HashMap<String, Object> shared) {
		Fitness fitness = (Fitness) shared.get("fitness");
		MultiobjectiveJobInput input = (MultiobjectiveJobInput) parameters;
		int dominations = 0;
		for(int i = 0; i < fitness.getPopulationSize(); i++)
			if(i != input.individualIndex)
				if(dominates(fitness, input.populationIndex, input.individualIndex, i))
					dominations++;
		MultiobjectiveJobOutput output = new MultiobjectiveJobOutput();
		output.individualIndex = input.individualIndex;
		output.populationIndex = input.populationIndex;
		output.result = dominations;
		return output;
	}
	
	/**
	 * Checks if a dominates b
	 * Domination is defined as
	 * "For all standardized fitness measures a <= b"
	 * @param fitness the fitness object
	 * @param pop	  the index of the population
	 * @param a		  the index of the first object
	 * @param b		  the index of the second object
	 * @return		  true if a dominates b
	 */
	private boolean dominates(Fitness fitness, int pop, int a, int b) {
		boolean dominates = true;
		for(int i = 0; i < fitness.getNumberMetrics(); i++) {
			double aFit = fitness.getStandardFitness(i, pop, a);
			double bFit = fitness.getStandardFitness(i, pop, b);
			if(bFit < aFit) {
				dominates = false;
				break;
			}
		}
		return dominates;
	}

}

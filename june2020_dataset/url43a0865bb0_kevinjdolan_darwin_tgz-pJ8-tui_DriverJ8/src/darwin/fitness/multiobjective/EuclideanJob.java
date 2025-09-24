package darwin.fitness.multiobjective;

import java.util.HashMap;

import darwin.fitness.Fitness;
import darwin.work.Job;

/**
 * Represents a single euclidean distance calculation
 * @author Kevin Dolan
 */
public class EuclideanJob implements Job {

	@Override
	public Object run(Object parameters, HashMap<String, Object> shared) {
		Fitness fitness = (Fitness) shared.get("fitness");
		MultiobjectiveJobInput input = (MultiobjectiveJobInput) parameters;
		double sum = 0;
		for(int i = 0; i < fitness.getNumberMetrics(); i++) {
			double adj = fitness.getAdjustedFitness(i, input.populationIndex, input.individualIndex);
			sum += adj * adj;
		} 
		MultiobjectiveJobOutput output = new MultiobjectiveJobOutput();
		output.individualIndex = input.individualIndex;
		output.populationIndex = input.populationIndex;
		output.result = sum;
		return output;
	}

}

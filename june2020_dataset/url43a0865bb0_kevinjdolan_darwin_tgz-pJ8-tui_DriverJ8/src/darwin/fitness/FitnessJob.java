package darwin.fitness;

import java.util.HashMap;

import darwin.population.Individual;
import darwin.problemObject.FitnessAnalyzer;
import darwin.work.Job;

/**
 * Represents a single fitness calculation
 * @author Kevin Dolan
 */
public class FitnessJob implements Job {

	private FitnessAnalyzer fitnessAnalyzer;
	
	public FitnessJob(FitnessAnalyzer fitnessAnalyzer) {
		this.fitnessAnalyzer = fitnessAnalyzer;
	}
	
	@Override
	public Object run(Object parameters, HashMap<String, Object> shared) {
	
		FitnessJobInput input = (FitnessJobInput) parameters;
		Object environment = shared.get("environment" + input.populationIndex);
		Individual individual = input.individual;
		
		double[] result = fitnessAnalyzer.assessFitness(individual, environment);
		FitnessJobOutput output = new FitnessJobOutput();
		output.populationIndex = input.populationIndex;
		output.individualIndex = input.individualIndex;
		output.result = result;
		output.complexity = individual.getComplexity();
		
		return output;
	}

}

package darwin.fitness;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeMap;
import darwin.fitness.multiobjective.Multiobjective;
import darwin.fitness.multiobjective.MultiobjectiveJobInput;
import darwin.fitness.multiobjective.MultiobjectiveJobOutput;
import darwin.population.Individual;
import darwin.population.Population;
import darwin.problemObject.FitnessAnalyzer;
import darwin.work.BusyException;
import darwin.work.Job;
import darwin.work.JobRunner;

/**
 * Stores fitness values for a Darwinian universe
 * @author Kevin Dolan
 */
public class Fitness {

	private Population[] universe;
	private FitnessAnalyzer fitnessAnalyzer;
	private ArrayList<Double>[][] rawFitness, standardFitness, adjustedFitness, normalizedFitness;
	private ArrayList<Integer>[] complexity;
	private ArrayList<Double>[] multiobjectiveRaw, multiobjectiveStandard, multiobjectiveAdjusted, multiobjectiveNormalized;
	private JobRunner jobRunner;
	private Multiobjective multiobjective;
	
	@SuppressWarnings("unchecked")
	public Fitness(Population[] universe, FitnessAnalyzer fitnessAnalyzer, JobRunner jobRunner, Multiobjective multiobjective) {
		this.universe = universe;
		this.fitnessAnalyzer = fitnessAnalyzer;
		this.jobRunner = jobRunner;
		this.multiobjective = multiobjective;
		
		//Create blank fitness fields
		rawFitness = new ArrayList[fitnessAnalyzer.getNumberMetrics()][universe.length];
		standardFitness = new ArrayList[fitnessAnalyzer.getNumberMetrics()][universe.length];
		adjustedFitness = new ArrayList[fitnessAnalyzer.getNumberMetrics()][universe.length];
		normalizedFitness = new ArrayList[fitnessAnalyzer.getNumberMetrics()][universe.length];
		
		for(int i = 0; i < fitnessAnalyzer.getNumberMetrics(); i++)
			for(int j = 0; j < universe.length; j++) {
				rawFitness[i][j] = new ArrayList<Double>();
				standardFitness[i][j] = new ArrayList<Double>();
				adjustedFitness[i][j] = new ArrayList<Double>();
				normalizedFitness[i][j] = new ArrayList<Double>();
				for(int k = 0; k < universe[0].getIndividuals().size(); k++) {
					rawFitness[i][j].add(0.);
					standardFitness[i][j].add(0.);
					adjustedFitness[i][j].add(0.);
					normalizedFitness[i][j].add(0.);
				}
			}
		
		//Create blank pareto fields
		multiobjectiveRaw = new ArrayList[universe.length];
		multiobjectiveStandard = new ArrayList[universe.length];
		multiobjectiveAdjusted = new ArrayList[universe.length];
		multiobjectiveNormalized = new ArrayList[universe.length];
		complexity = new ArrayList[universe.length];
		for(int i = 0; i < universe.length; i++) {
			multiobjectiveRaw[i] = new ArrayList<Double>();
			multiobjectiveStandard[i] = new ArrayList<Double>();
			multiobjectiveAdjusted[i] = new ArrayList<Double>();
			multiobjectiveNormalized[i] = new ArrayList<Double>();
			complexity[i] = new ArrayList<Integer>();
			for(int j = 0; j < universe[0].getIndividuals().size(); j++) {
				multiobjectiveRaw[i].add(0.);
				multiobjectiveStandard[i].add(0.);
				multiobjectiveAdjusted[i].add(0.);
				multiobjectiveNormalized[i].add(0.);
				complexity[i].add(0);
			}
		}
	}
	
	/**
	 * @return the number of fitness metrics
	 */
	public int getNumberMetrics() {
		return fitnessAnalyzer.getNumberMetrics();
	}
	
	/**
	 * @return the size of the populations
	 */
	public int getPopulationSize() {
		return universe[0].getIndividuals().size();
	}
	
	/**
	 * Return a raw fitness value
	 * @param metric	 the fitness metric index to access
	 * @param population the population index to access
	 * @param individual the individual index to access
	 * @return			 the current raw fitness value
	 */
	public double getRawFitness(int metric, int population, int individual) {
		return rawFitness[metric][population].get(individual);
	}
	
	/**
	 * Return a raw multiobjective fitness value
	 * @param population the population index to access
	 * @param individual the individual index to access
	 * @return			 the current raw fitness value
	 */
	public double getRawFitness(int population, int individual) {
		return multiobjectiveRaw[population].get(individual);
	}
	
	/**
	 * Return a standardized fitness value
	 * @param metric	 the fitness metric index to access
	 * @param population the population index to access
	 * @param individual the individual index to access
	 * @return			 the current standardized fitness value
	 */
	public double getStandardFitness(int metric, int population, int individual) {
		return standardFitness[metric][population].get(individual);
	}
	
	/**
	 * Return a standard multiobjective fitness value
	 * @param population the population index to access
	 * @param individual the individual index to access
	 * @return			 the current raw fitness value
	 */
	public double getStandardFitness(int population, int individual) {
		return multiobjectiveStandard[population].get(individual);
	}
	
	/**
	 * Return an adjusted fitness value
	 * @param metric	 the fitness metric index to access
	 * @param population the population index to access
	 * @param individual the individual index to access
	 * @return			 the adjusted fitness, calculated from dominations
	 */
	public double getAdjustedFitness(int metric, int population, int individual) {
		return adjustedFitness[metric][population].get(individual);
	}
	
	/**
	 * Return a adjusted multiobjective fitness value
	 * @param population the population index to access
	 * @param individual the individual index to access
	 * @return			 the current raw fitness value
	 */
	public double getAdjustedFitness(int population, int individual) {
		return multiobjectiveAdjusted[population].get(individual);
	}
	
	/**
	 * Return a normalized fitness value
	 * @param metric	 the fitness metric index to access
	 * @param population the population index to access
	 * @param individual the individual index to access
	 * @return			 the normalized fitness, calculated from dominations
	 */
	public double getNormalizedFitness(int metric, int population, int individual) {
		return normalizedFitness[metric][population].get(individual);
	}
	
	/**
	 * Return a normalized multiobjective fitness value
	 * @param population the population index to access
	 * @param individual the individual index to access
	 * @return			 the current raw fitness value
	 */
	public double getNormalizedFitness(int population, int individual) {
		return multiobjectiveNormalized[population].get(individual);
	}
	
	/**
	 * Return complexity of an individual
	 * @param population the population index to access
	 * @param individual the individual index to access
	 * @return			 the number of nodes this individual is made of
	 */
	public int getComplexity(int population, int individual) {
		return complexity[population].get(individual);
	}
	
	/**
	 * Return the indexes of the best-of-generation individuals
	 * @param population the population index to access
	 * @param number	 the number of indexes to return
	 * @return			 the indexes of the best individual
	 */
	public Collection<Integer> getBestOfGeneration(int population, int number) {
		TreeMap<Double, Integer> best = new TreeMap<Double, Integer>();
		if(number != 0) {
			best.put(multiobjectiveNormalized[population].get(0), 0);
			for(int i = 1; i < multiobjectiveNormalized[population].size(); i++) {
				double curr = multiobjectiveNormalized[population].get(i);
				if(best.size() < number || curr > best.firstKey())
					best.put(curr, i);
				if(best.size() > number)
					best.remove(best.firstKey());
			}
		}
		return best.values();
	}
	
	/**
	 * Distributes and processes all fitness-related calculations
	 * @throws BusyException
	 */
	public void assessFitness() throws BusyException {	
		//Distribute fitness calculations
		Job job = new FitnessJob(fitnessAnalyzer);
		for(int i = 0; i < universe.length; i++) {
			Population population = universe[i];
			jobRunner.putSharedData("environment" + i, population.getEnvironment());
			List<Individual> list = population.getIndividuals();
			for(int j = 0; j < list.size(); j++) {
				FitnessJobInput input = new FitnessJobInput();
				input.populationIndex = i;
				input.individualIndex = j;
				input.individual = list.get(j);
				jobRunner.addJob(job, input);
			}
		}
		List<Object> result = jobRunner.dispatch();
		double[][] sums = new double[getNumberMetrics()][universe.length];
		//Fill in the fitness fields
		for(Object res : result) {
			FitnessJobOutput output = (FitnessJobOutput) res;
			double[] fitnessResult = output.result;
			for(int i = 0; i < fitnessResult.length; i++) {
				int pop = output.populationIndex;
				int n = output.individualIndex;
				double raw = fitnessResult[i];
				double standard = fitnessAnalyzer.standardize(i, raw);
				double adjusted = 1. / (1. + standard);
				rawFitness[i][pop].set(n, raw);
				standardFitness[i][pop].set(n, standard);
				adjustedFitness[i][pop].set(n, adjusted);
				sums[i][pop] += adjusted;
			}
			complexity[output.populationIndex].set(output.individualIndex, output.complexity);
		}
		//Finally, calculate the normalized fitnesses
		for(int i = 0; i < universe.length; i++) {
			for(int j = 0; j < getPopulationSize(); j++) {
				for(int k = 0; k < getNumberMetrics(); k++) {
					normalizedFitness[k][i].set(j, adjustedFitness[k][i].get(j) / sums[k][i]);
				}
			}
		}

		//Distribute Pareto-domination calculations
		job = multiobjective.getJob();
		jobRunner.putSharedData("fitness", this);
		for(int i = 0; i < universe.length; i++) {
			Population population = universe[i];
			List<Individual> list = population.getIndividuals();
			for(int j = 0; j < list.size(); j++) {
				MultiobjectiveJobInput input = new MultiobjectiveJobInput();
				input.populationIndex = i;
				input.individualIndex = j;
				jobRunner.addJob(job, input);
			}
		}
		result = jobRunner.dispatch();
		//Fill in the dominations field
		double[] sum = new double[universe.length];
		for(Object res : result) {
			MultiobjectiveJobOutput output = (MultiobjectiveJobOutput) res;
			int pop = output.populationIndex;
			int n = output.individualIndex;
			double raw = output.result;
			double standard = multiobjective.standardizeFitness(raw);
			double adjusted = 1. / (1. + standard);
			multiobjectiveRaw[pop].set(n, raw);
			multiobjectiveStandard[pop].set(n, standard);
			multiobjectiveAdjusted[pop].set(n, adjusted);
			sum[pop] += adjusted;
		}
		
		//Finally, calculate the normalized fitness
		for(int i = 0; i < universe.length; i++) {
			for(int j = 0; j < getPopulationSize(); j++) {
				multiobjectiveNormalized[i].set(j, getAdjustedFitness(i, j) / sum[i]);
			}
		}
	}
}

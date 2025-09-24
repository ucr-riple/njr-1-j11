package darwin.control;

import darwin.fitness.Fitness;
import darwin.geneticOperation.GeneticOperation;
import darwin.geneticOperation.UnableToPerformOperationException;
import darwin.population.*;
import darwin.work.BusyException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * The Darwin class is the top-level object of the Darwin project
 * It maintains the entire universe of discourse of the genetic
 * programming process
 * @author Kevin Dolan
 */
public class Darwin {

	private Parameters parameters;
	private Population[] universe;
	private ArrayList<Individual>[] limbo;
	private Fitness fitness;
	private int generation;
	
	public Darwin(Parameters parameters) {
		this.parameters = parameters;
		initializePopulations();
		parameters.selection.setUniverse(universe);
		fitness = new Fitness(universe, parameters.fitnessAnalyzer, parameters.jobRunner, parameters.multiobjective);
		parameters.selection.setFitness(fitness);
		generation = 0;
		try {
			fitness.assessFitness();
		} catch (BusyException e) {
			//Logically inaccessible
			e.printStackTrace();
		}
	}
	
	/**
	 * Initialize all population objects, setting individual list and environment
	 */
	@SuppressWarnings("unchecked")
	private void initializePopulations() {
		universe = new Population[parameters.environments.length];
		limbo = new ArrayList[universe.length];
		for(int i = 0; i < universe.length; i++) {
			List<Individual> current = parameters.initializer.initialize
				(parameters.populationSize, parameters.initialDepth, 
						parameters.maxSize, parameters.nodeFactory);
			universe[i] = new Population(current, parameters.environments[i]);
		}
	}
	
	/**
	 * @return the fitness object
	 */
	public Fitness getFitness() {
		return fitness;
	}
	
	/**
	 * Get a population object
	 * @param index the index of the population to access
	 * @return		the population object
	 */
	public Population getPopulation(int index) {
		return universe[index];
	}
	
	/**
	 * Execute a predetermined number of generations
	 * @param howMany the number of generations to perform
	 */
	public void executeGenerations(int howMany) {
		for(int i = 0; i < howMany; i++) {
			executeGeneration();
			parameters.fitnessAnalyzer.generationComplete();
			parameters.eventListener.generationCompleted(this);
		}
	}
	
	/**
	 * @return the current generation number
	 */
	public int getGeneration() {
		return generation;
	}
	
	/**
	 * Execute a single generational run
	 */
	private void executeGeneration() {
		try {
			//Reset the limbo populations
			for(int i = 0; i < limbo.length; i++)
				limbo[i] = new ArrayList<Individual>();
			//Consider migrations
			for(int i = 0; i < universe.length; i++)
				migrate(i);
			//Copy the elites
			for(int i = 0; i < universe.length; i++) {
				Collection<Integer> best = fitness.getBestOfGeneration(i, parameters.elite);
				for(Integer good : best) {
					limbo[i].add(universe[i].getIndividual(good).clone());
				}
			}
			//Process and replace the populations
			for(int i = 0; i < universe.length; i++)  {
				processPopulation(i);
				universe[i].setIndividuals(limbo[i]);
				limbo[i] = null;
			}	
			
			//Assess the fitness
			fitness.assessFitness();
			generation++;
		} catch (BusyException e) {
			//Logically inaccessible
			e.printStackTrace();
		}
	}
	
	/**
	 * Determine whether or not to perform migration, and do it
	 * @param index the index of population to consider
	 */
	private void migrate(int index) {
		if(Math.random() < parameters.migrationProbability) {
			int amount = (int) (parameters.migrationFactor * universe[index].getPopulationSize());
			for(int i = 0; i < amount; i++) {
				Individual choice = parameters.selection.selectIndividual(index);
				int next = parameters.migration.getDestination(index, universe.length);
				limbo[next].add(choice.clone());
			}
		}
	}
	
	/**
	 * Process a single generation of a single population
	 * @param index the index of population to consider
	 */
	private void processPopulation(int index) {
		while(limbo[index].size() < parameters.populationSize) {
			GeneticOperation operation = null;
			double random = Math.random();
			double sum = 0;
			for(int i = 0; i < parameters.operations.length; i++) {
				sum += parameters.operationProbabilities[i];
				if(random < sum) {
					operation = parameters.operations[i];
					break;
				}
			}
			Individual[] inputs = new Individual[operation.getNumberInputs()];
			for(int i = 0; i < inputs.length; i++)
				inputs[i] = parameters.selection.selectIndividual(index).clone();
			try {
				boolean function = Math.random() < parameters.functionSelectionProbability;
				Individual[] outputs = operation.performOperation(inputs, parameters.maxSize, function);
				for(int i = 0; i < outputs.length && limbo[index].size() < parameters.populationSize; i++) {
					limbo[index].add(outputs[i]);
				}
			} catch (UnableToPerformOperationException e) {
				//If this occurs, just keep going
			}
		}
	}
}
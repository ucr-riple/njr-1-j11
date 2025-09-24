package darwin.control;

import darwin.fitness.multiobjective.Multiobjective;
import darwin.geneticOperation.GeneticOperation;
import darwin.initialization.Initializer;
import darwin.migration.Migration;
import darwin.problemObject.FitnessAnalyzer;
import darwin.problemObject.NodeFactory;
import darwin.selection.Selection;
import darwin.work.JobRunner;

/**
 * The parameters class is a simple object maintaining a set of all
 * parameters which should be passed to Darwin
 * @author Owner
 */
public class Parameters {
	
	/**
	 * The array of problem-specific environment objects
	 */
	public Object[] environments;
	
	/**
	 * The problem-specific node factory
	 */
	public NodeFactory nodeFactory;
	
	/**
	 * The problem-specific fitness analyzer
	 */
	public FitnessAnalyzer fitnessAnalyzer;
	
	/**
	 * The initial depth parameter for individual generation
	 */
	public int initialDepth;
	
	/**
	 * The size of each population
	 */
	public int populationSize;
	
	/**
	 * The probability that migration will occur after a generation
	 */
	public double migrationProbability;

	/**
	 * The number of individuals to migrate during a migration
	 */
	public double migrationFactor;
	
	/**
	 * The migration object to use for migrations
	 */
	public Migration migration;
	
	/**
	 * The Selection mechanism to use
	 */
	public Selection selection;
	
	/**
	 * Array containing the genetic operations to use
	 */
	public GeneticOperation[] operations;
	
	/**
	 * Array containing the probabilities that each genetic operation will be used
	 */
	public double[] operationProbabilities;
	
	/**
	 * The initializer to be used
	 */
	public Initializer initializer;
	
	/**
	 * The job runner to be used
	 */
	public JobRunner jobRunner;
	
	/**
	 * The number of best individuals to duplicate exactly each generation
	 */
	public int elite;
	
	/**
	 * The maximum size any individual can have
	 */
	public int maxSize;
	
	/**
	 * The probability that a function will be forced as an operation point
	 */
	public double functionSelectionProbability;
	
	/**
	 * The event listener object
	 */
	public EventListener eventListener;
	
	/**
	 * The multiobjective object
	 */
	public Multiobjective multiobjective;
}

package darwin.initialization;

import java.util.ArrayList;
import java.util.List;

import darwin.geneticOperation.UnableToPerformOperationException;
import darwin.problemObject.NodeFactory;
import darwin.population.Individual;
import darwin.population.Node;
import darwin.population.TypeMismatchException;

/**
 * Generate about half of the population using Grow method and
 * the other half using Full method; in a range of sizes;
 * prevent duplicates
 * @author Kevin Dolan
 */
public class RampedHalfAndHalf implements Initializer {
	
	@Override
	public List<Individual> initialize(int number, int maxDepth, int maxSize, NodeFactory nodeFactory) {
		Generator full = new Full(nodeFactory);
		Generator grow = new Grow(nodeFactory);
		Class<?>[] returnTypes = nodeFactory.getReturnTypes();
		
		ArrayList<Individual> population = new ArrayList<Individual>();
		while(population.size() < number) {
			Individual individual = new Individual(returnTypes);
			for(int i = 0; i < returnTypes.length; i++) {
				Node node;
				Class<?> returnType = returnTypes[i];
				int depth = (int) ( Math.random() * (maxDepth - 2) ) + 2;
				try {
					if(Math.random() < 0.5)
						node = full.generate(depth, maxSize, returnType);
					else
						node = grow.generate(depth, maxSize, returnType);
				}
				catch(UnableToPerformOperationException e) {
					//Logically inaccessible
					e.printStackTrace();
					node = null;
				}
				try {
					individual.setTree(i, node);
				} catch (TypeMismatchException e) {
					//Logically inaccessible
					e.printStackTrace();
				}
			}
			
			//Check for duplicates
			boolean unique = true;
			for(Individual current : population) 
				if(current.equals(individual)) {
					unique = false;
					break;
				}
			if(unique)
				population.add(individual);
				
		}
		return population;
	}

}

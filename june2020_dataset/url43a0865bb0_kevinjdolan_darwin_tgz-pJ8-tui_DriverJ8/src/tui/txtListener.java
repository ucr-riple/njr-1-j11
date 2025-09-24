package tui;

import java.util.Collection;

import darwin.control.EventListener;
import darwin.control.Parameters;
import darwin.control.Darwin;

public class txtListener implements EventListener {

	Parameters parameters;
	
	public txtListener(Parameters parameters) {
		this.parameters = parameters;
	}
	
	@Override
	public void generationCompleted(Darwin darwin) {
		System.gc();
		System.out.println("***************************************************");
		System.out.println("Generation " + darwin.getGeneration() + " completed.");
		for(int i = 0; i < parameters.environments.length; i++) {
			System.out.println("Population Number " + i + " stats:");
			int max = 0;
			double sum = 0;
			for(int n = 0; n < parameters.populationSize; n++) {
				int size = darwin.getFitness().getComplexity(i, n);
				max = Math.max(max, size);
				sum += size;
			}
			System.out.println("Max Size: " + max);
			System.out.println("Avg Size: " + (sum / parameters.populationSize));
			Collection<Integer> best = darwin.getFitness().getBestOfGeneration(i, 2);
			for(Integer n : best) {
				String s = "Fitness: ";
				for(int j = 0; j < darwin.getFitness().getNumberMetrics(); j++) {
					s += darwin.getFitness().getRawFitness(j, i, n);
					if(j != darwin.getFitness().getNumberMetrics() - 1)
						s += ", ";
				}
				System.out.println(s);
			}
		}
	}

}

package symbolicRegression;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import symbolicRegression.environment.XYValues;
import darwin.population.Individual;
import darwin.population.Node;

public class FitnessAnalyzer extends darwin.problemObject.FitnessAnalyzer {

	@Override
	public String getFitnessName(int which) {
		return "Sum Squared Error";
	}
	
	@Override
	public double[] assessFitness(Individual individual, Object environment) {
		XYValues xy = (XYValues) environment;
		ArrayList<Point2D.Double> coords = xy.getValues();
		Node rpb = individual.getTree(0);
		
		double sum = 0;
		for(Point2D.Double pos : coords) {
			double result = (Double) rpb.getValue(pos.x);
			double error = pos.y - result;
			sum += error * error;
		}
		
		return new double[] {sum};
	}

	@Override
	public int getNumberMetrics() {
		return 1;
	}

	@Override
	public FitnessAnalyzer clone() {
		return new FitnessAnalyzer();
	}

	@Override
	public double standardize(int fitnessMetric, double fitness) {
		return fitness;
	}

}
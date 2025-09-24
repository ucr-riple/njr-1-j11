package symbolicRegression;

import javax.swing.JComponent;

import symbolicRegression.environment.XYValues;

import darwin.population.Individual;

public class Output implements darwin.problemObject.Output {

	@Override
	public JComponent getOutput(Individual individual, Object environment) {
		return new FunctionGraph(individual, (XYValues) environment);
	}

}

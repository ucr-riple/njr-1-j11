package darwin.problemObject;

import javax.swing.JComponent;
import darwin.population.Individual;

/**
 * The Output interface should be implemented by the problem-developer
 * to display the output of a particular individual
 * @author Kevin Dolan
 */
public interface Output {

	/**
	 * Return the JComponent representing the output of this individual
	 * @param individual  the individual being displayed
	 * @param environment the environment the individual exists in
	 * @return			  the JComponent representing the output
	 */
	public JComponent getOutput(Individual individual, Object environment);
	
}

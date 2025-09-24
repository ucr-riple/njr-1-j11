package darwin.problemObject;

/**
 * The DarwinSheet facilitates contact between the problem and Darwin
 * @author Kevin Dolan
 */
public interface DarwinSheet {
	
	/**
	 * @return the problem node factory
	 */
	public NodeFactory getNodeFactory();
	
	/**
	 * @return the fitness analyzer object
	 */
	public FitnessAnalyzer getFitnessAnalyzer();
	
	
	/**
	 * @return the output object
	 */
	public Output getOutput();
}

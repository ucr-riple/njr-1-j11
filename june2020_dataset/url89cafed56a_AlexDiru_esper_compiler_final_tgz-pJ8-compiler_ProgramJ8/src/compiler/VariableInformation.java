package compiler;

/**
 * Class to store information about variables in the code
 */
public class VariableInformation {

	public String name;
	public String type;
	
	/**
	 * Constructor to set parameters
	 * @param vname The variable name
	 * @param vtype The variable type
	 */
	public VariableInformation(String vname, String vtype) {
		name = vname;
		type = vtype;
	}

}
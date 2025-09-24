package darwin.nodeFilter;


/**
 * Accept nodes that are functions
 * @author Kevin Dolan
 */
public class Functions extends Filter {

	private Class<?> returnType;
	
	public Functions(Class<?> returnType) {
		this.returnType = returnType;
	}
	
	@Override
	public boolean accept(Filterable node) {
		return returnType.isAssignableFrom(node.getReturnType()) 
			&& node.getArity() > 0;
	}
}

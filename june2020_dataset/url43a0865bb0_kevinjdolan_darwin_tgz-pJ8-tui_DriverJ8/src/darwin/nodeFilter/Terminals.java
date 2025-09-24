package darwin.nodeFilter;


/**
 * Accept all nodes that are terminals
 * @author Kevin Dolan
 */
public class Terminals extends Filter {

	private Class<?> returnType;
	
	public Terminals(Class<?> returnType) {
		this.returnType = returnType;
	}
	
	@Override
	public boolean accept(Filterable node) {
		return returnType.isAssignableFrom(node.getReturnType()) 
			&& node.getArity() == 0;
	}
}
